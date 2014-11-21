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
