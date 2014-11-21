#!/usr/bin/env python

import sys
from graphs import *
from queue import Q

def print_g(g):
	for n in g:
		print ' '.join(map(lambda x : "%5s" % x, n))

sigma = {}
alphabet = []
with open("data/sigma.csv") as f:
	rows = filter(lambda k: len(k) > 1, map(lambda x : x.split('\t'), f.read().split('\n')))
	for r in rows:
		alphabet.append(r[2])
		for letter in list(r[2]):
			sigma[letter] = (r[0], letter, r[2])

word_to_number = {}
for n in xrange(0, len(alphabet)):
	word_to_number[alphabet[n]] = n

def nfa_to_dfa(the_nfa):
	edges = []
	nfa = the_nfa.get_table()
	end_states = the_nfa.get_accept_states()
	end_states_names = the_nfa.accept_states_name

	num_cols = len(nfa[0])
	num_row  = len(nfa)

	q = Q()
	sd = StateDictionary(end_states, end_states_names)
	s_count = 0

	def cloj(states):
		return the_nfa.cloj(states)

	q.add(cloj([0]))
	sd.add(cloj([0]))
	current = None

	while not q.empty():
		current = q.next()
		current_s = sd.tonum(current)

		reach = {}
		for n in alphabet:
			reach[n] = []

		for state in current:
			paths = nfa[state]

			letter = 0
			for path in paths[:-1]:
				if path != -1:
					reach[alphabet[letter]].append(path)

				letter += 1
		
		for n in [k for k in reach.items() if len(k[1]) > 0]:
			closure = cloj(n[1])
			if not sd.has(closure):
				sd.add(closure)
				q.add(closure)

			edges.append( (sd.tonum(current), n[0] ,sd.tonum(closure), sd.isend(current)))
	return edges

if __name__ == '__main__':
	with open("data/before_the_storm.csv") as f:
		buffy = f.read()
	
#	print_g(NFA(buffy).get_table())
#	print
	dfa = nfa_to_dfa(NFA(buffy))
	the_graph = []
	all_states = reduce( lambda x, y: x + y, map(lambda x : [x[0]] + [x[2]], dfa))
	for r in xrange(0, max(all_states) + 1):
		the_graph.append([-1] * 30)

	for n in nfa_to_dfa(NFA(buffy)):
		current = n[0]
		next = n[2]
		edge = word_to_number[n[1]]
		the_graph[current][edge] = next

	for n in nfa_to_dfa(NFA(buffy)):
		current = n[0]
		next = n[2]
		edge = word_to_number[n[1]]

		if current == 0 and next != 9:
			the_graph[next] = [2] * 30

	for row in the_graph:
		print "\t".join(map(str, (row)))