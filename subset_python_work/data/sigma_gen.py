#!/usr/bin/env python

import sys

buffy = sys.stdin.read()
buffy = filter(lambda x : len(x) > 0, buffy.split('\n'))
rest = buffy[-1]
buffy = buffy[:-1]

others = zip(range(0 + 2, len(rest) + 2), list(rest))

for n in buffy:
	print "%s," % n

for n in others:
	print "%s,%s," % n
