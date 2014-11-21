#!/usr/bin/env python

import sys
from graphs import *
from queue import Q

def print_g(g):
	for n in g:
		print ' '.join(map(lambda x : "%5s" % x, n))

alphabet = "LD_."
def nfa_to_dfa(the_nfa):
	edges = []
	nfa = the_nfa.get_table()
	end_states = the_nfa.get_accept_states()

	num_cols = len(nfa[0])
	num_row  = len(nfa)

	q = Q()
	sd = StateDictionary(end_states)
	s_count = 0

	def cloj(states):
		return the_nfa.cloj(states)

	q.add(cloj([0]))
	sd.add(cloj([0]), False)
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
	with open("data/adv.csv") as f:
		buffy = f.read()
	
	print_g(NFA(buffy).get_table())
	print
	for n in nfa_to_dfa(NFA(buffy)):
		print n
