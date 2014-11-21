import glob

alphabet = []
sigma = {}
tokens = {}
word_to_number = {}


with open("data/sigma.csv") as f:
	rows = filter(lambda k: len(k) > 1, map(lambda x : x.split('\t'), f.read().split('\n')))
	for r in rows:
		alphabet.append(r[2])
		for letter in list(r[2]):
			sigma[letter] = (r[0], letter, r[2])

for n in xrange(0, len(alphabet)):
	word_to_number[alphabet[n]] = n

for _f in glob.glob("token_graphs/*.csv"):
	with open(_f) as f:
		buffy = f.read()
	name = _f.split('/')[-1].replace('.csv', '')

	tokens[name] =  filter(lambda x : len(x) > 1, map(lambda x : x.split('\t'), buffy.split('\n')))

accept_states = []
e_points = []
all_edges = []
all_states = []

global_state_counter = 0
for t in tokens:
	#print t
	current_edges = []
	graph = tokens[t]
	states = []
	e_points.append(global_state_counter)

	for n in graph:
		n[0] = global_state_counter + int(n[0])
		n[2] = global_state_counter + int(n[2])
		states.append(n[0])
		all_states.append(n[0])

		if n[1].strip() == 'AS':
			accept_states.append((n[0], t))

		if '|' in n[1]:
			for var_edge in n[1].split('|'):
				current_edges.append([n[0], var_edge, n[2]])
		else:
			current_edges.append(n)

	current_edges = sorted(current_edges)
	global_state_counter += len(set(states))

	all_edges = all_edges + current_edges
	"""
	for ec in current_edges:
		print ec
	print len(set(states))
	print 
	"""

the_graph = []
for r in xrange(0, max(all_states) + 1):
	the_graph.append([-1] * 30)

for n in all_edges:
	if n[1] == 'AS':
		continue
	current = n[0]
	edge = word_to_number[n[1]]
	next = n[2]
	
	the_graph[current][edge] = next

the_graph[0][-1] = '|'.join(map(str,e_points))

for line in the_graph:
	print ", ".join(map(str, line))

print
print ", ".join(map(lambda x : str(x[1]), accept_states))
print ", ".join(map(lambda x : str(x[0]), accept_states))