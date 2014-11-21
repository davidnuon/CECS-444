import glob

single = """<
>
[
]
{
}
@
&
#
!
~
'
"
$
;
:
.
,
+
-
/
*
=
^
(
)""".split('\n')

single_CSV = "0\t%s\t1\n1\tAS\t1"
doulbe_CSV = """0\t%s\t1
1\t%s\t2
2\tAS\t2"""


double = """--
++
+-
-+
>=
<=
!=
<>
==
:=
<<
>>
/=
!!""".split('\n')


alphabet = []
sigma = {}
tokens = {}
word_to_number = {}


with open("data/sigma.csv") as f:
	rows = filter(lambda k: len(k) > 1, map(lambda x : x.split('\t'), f.read().split('\n')))
	for r in rows:
		alphabet.append(r[2])
		for letter in list(r[1]):
			sigma[letter] = (r[0], letter, r[2])

for n in xrange(0, len(alphabet)):
	word_to_number[alphabet[n]] = n

for n in single:
	fname = "token_graphs/" + sigma[n][2].lower() + ".csv"
	with open(fname, 'w') as f:
		f.write(single_CSV % sigma[n][2])

for n in double:
	chars =  tuple(map(lambda x : sigma[x][2], list(n)))
	fname = "token_graphs/" + "_".join(map(lambda x : x.lower(), chars)) + ".csv"

	with open(fname, 'w') as f:
		f.write(doulbe_CSV % chars)