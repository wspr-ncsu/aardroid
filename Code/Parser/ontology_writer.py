import re
import os
import pickle
import networkx as nx

dir = "data_ontology_policylint.pickle"

pickle_in = open(dir, "rb")
tdict = pickle.load(pickle_in)

f = open("ontology.xml", "a")
f.write("<ontology>\n")

terms = set()

for t in tdict:
	terms.add(t)
	f.write("	<node term=\""+t+"\">\n")
	print(t)
	for s in tdict[t]:
		terms.add(s)
		print(s)
		f.write("		<child term=\"" + s + "\"></child>\n")
	f.write("	</node>\n")

print(tdict)
f.write("</ontology>")

f.close()





