#!/usr/bin/env python

class Alphabet(object):
	def __init__(self, _str):
		self.content  = _str
		self.table    = []
		self.alphabet = []
		self.alphabet_list = []

		lines = _str.split("\n")
		rows = map(lambda x : x.split('\t'), lines)
		self.tables = [r for r in rows if len(r) > 1]

		# Go through the alphabet and make objects for them
		for t in self.tables:
			self._make_letter(len(self.alphabet), t[1], t[2])
		
		# Add end of line character
		self._make_letter(len(self.alphabet), '\n', 'END_OF_LINE')

		for a in self.alphabet:
			self.alphabet_list.append(a['name'])
	
	def get_table(self):
		return self.tables
	
	def get_letters(self):
		return self.alphabet

	def get_letter_index(self, c):
		return self.alphabet_list.index(c)

	def _make_letter(self, idx, char, name):
		self.alphabet.append ({
			'idx' : idx,
			'c' : char,
			'name' : name
		})

def main():
	with open("data/sigma.csv") as f:
		buffy = f.read()

	alpha = Alphabet(buffy)
	for r in alpha.get_table():
		print r

	print
	for l in alpha.get_letters():
		print l

	print
	print alpha.alphabet_list

if __name__ == '__main__':
	main()
