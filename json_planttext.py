data = '''{"value":"7","left":{"value":"6","left":{"value":"3","left":{"value":"1","left":null,"right":null},"right":null},"right":null},"right":null}'''

import json

class Node:
	counter = 0
	def new_name():
		Node.counter += 1
		return f'State{Node.counter}'

	def __init__(self, obj):
		self.left = None
		self.right = None
		self.recursion = False

		if 'value' in obj:
			self.value = obj['value']

		if 'recursion' in obj:
			self.recursion = True
			return
		self.name = f'{Node.new_name()}'

		if 'left' in obj and obj['left']:
			self.left = Node(obj['left'])

		if 'right' in obj and obj['right']:
			self.right = Node(obj['right'])

	def __repr__(self):
		res = f'state "{self.value}" as {self.name}'

		if self.recursion:
			res += ':RECURSION'

		if self.left:
			left = self.left.__repr__()
			res += '\n' + left
			res += f'\n{self.name} --> {self.left.name} : l'

		if self.right:
			right = self.right.__repr__()
			res += '\n' + right
			res += f'\n{self.name} --> {self.right.name} : r'
		return res

	def length(self):
		res = 1
		if self.left:
			res += self.left.length()
		if self.right:
			res += self.right.length()
		return res

obj = json.loads(data)
x = Node(obj)

print('@startuml')
print('hide empty description')
print(x)
print('@enduml')
print()
print('https://www.planttext.com/')
print(f'There a total of {Node.counter} nodes')
print(f'The node containing {x.left.left.value} has {x.left.left.length()} elements')