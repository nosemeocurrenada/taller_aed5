NCLAVES = 1000
def mod(i, n):
	return i % n
def keygen(i):
	return NCLAVES * mod((i * i - 100 * i),NCLAVES) + i

print(keygen(2))