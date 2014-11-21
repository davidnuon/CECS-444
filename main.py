#!/usr/bin/env python

import sys
with open("data/example.nfa") as f:
	buffy = f.read()

nfa = map(lambda x : map(int, x.split(',')), buffy.split('\n')[:-1])


class Q:
	def __init__(self):
		self.line = []

	def add(self, n):
		self.line.append(n)

	def next(self):
		first = self.line[0]
		self.line = self.line[1:]
		return first

	def empty(self):
		return len(self.line) == 0

	def get(self):
		return self.line

	def __str__(self):
		return str(self.line)

class SD(object):
	"""docstring for SD"""
	def __init__(self):
		super(SD, self).__init__()
		self.str    = []
		self.num    = []
		self.con    = []
		self.count  = 0

	def add(self,s):
		self.con.append(s)
		self.str.append(str(s))
		self.num.append(self.count)
		self.count += 1

	def has(self, s):
		return str(s) in self.str

	def tonum(self, s):
		return self.str.index(str(s))



def print_g(g):
	for n in g:
		print ' '.join(map(lambda x : "%3d" % x, n))

alphabet = "abc#"
def nfa_to_dfa(nfa):
	num_cols = len(nfa[0])
	num_row  = len(nfa)

	q = Q()
	sd = SD()
	s_count = 0
	dfa = [[0]*num_cols] * num_row

	def cloj(state):
		epsilon = nfa[state][-1]
		if epsilon == -1:
			return [state]

		return [state] + [epsilon]

	alls = zip(range(0, len(nfa)), nfa)
	q.add(cloj(0))
	sd.add(cloj(0))
	current = None

	while not q.empty():
		print q
		current = q.next()
		current_s = sd.tonum(current)

		for state in current:
			row   = alls[state]
			paths = row[1]

			letter = 0
			for path in paths[:-1]:
				if path != -1:
					closure = cloj(path)
					if not sd.has(closure):
						sd.add(closure)
						q.add(closure)

					to_path = sd.tonum(closure)
					print current_s, alphabet[letter], to_path
				
				letter += 1

	for n in q.get():
		print n

	return dfa

print_g(nfa)
print
nfa_to_dfa(nfa)