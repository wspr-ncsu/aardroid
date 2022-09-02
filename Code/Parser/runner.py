import re
import os
import pickle
import networkx as nx

dir = "../AAR/Obfuscation/Unobfuscated/"

for name in os.listdir(dir):
	filename = dir+ name
	if(os.path.isfile(filename)):
		print(filename)
		os.system("java -jar Tool.jar "+filename)

		





