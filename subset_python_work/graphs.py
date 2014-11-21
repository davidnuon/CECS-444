class StateDictionary(object):
	"""docstring for StateDictionary"""
	def __init__(self, _end_states, _end_states_names):
		super(StateDictionary, self).__init__()
		self.str    = []
		self.num    = []
		self.con    = []
		self.end    = []
		self.count  = 0
		self.end_states = _end_states
		self.end_states_names = {}
		self.end_name = []

		for n in xrange(0, len(_end_states)):
			self.end_states_names[str(_end_states[n])] = _end_states_names[n]

	def add(self,s, override = True):
		self.con.append(s)
		self.str.append(str(s))
		self.num.append(self.count)
		self.end.append( override and reduce(lambda x,y : x or y, map(lambda x : x in self.end_states, s)) )
		self.count += 1

		v = []
		for n in s:
			if self.end_states_names.has_key(str(n)):
				v.append(self.end_states_names[str(n)])

		self.end_name.append(v)


	def has(self, s):
		return str(s) in self.str

	def tonum(self, s):
		return self.str.index(str(s))

	def isend(self, s):
		return self.end_name[self.tonum(s)]


class NFA(object):
	"""docstring for NFA"""
	def __init__(self, _str):
		super(NFA, self).__init__()
		self._csv_content = _str
		self.graph = []
		self.accept_states = []
		self.accept_states_name = []
		self.make_table()

	def make_table(self):
		def conv(s):
			try:
				if '|' in s:
					return map(int, s.split('|'))
				else:
					return int(s)
			except:
				return None
	
		f = self._csv_content.split('\n')
		self.accept_states_name = list(f[-2].split(','))

		for line in self._csv_content.split('\n'):
			paths = map(conv, line.split(','))
			if len(line) > 0:
				self.graph.append(paths)

		self.accept_states = list(self.graph[-1])
		self.graph = self.graph[:-3]


	def get_accept_states(self):
		return self.accept_states

	def get_table(self):
		return self.graph

	def cloj(self, states):
		epsilon = []

		for state in states:
			if type(self.graph[state][-1]) == int and self.graph[state][-1]:
				epsilon = epsilon + [self.graph[state][-1]]
			else:
				epsilon = epsilon + self.graph[state][-1]

		if len(epsilon) == 1 and epsilon[0] == -1:
			return states

		return states + filter(lambda x : x != -1, epsilon)
