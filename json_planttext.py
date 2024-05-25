data = '''{"value":"100","left":{"value":"-98999","left":{"value":"-195902","left":{"value":"-290997","left":{"value":"-383904","left":{"value":"-474995","left":{"value":"-563906","left":{"value":"-650993","left":{"value":"-735908","left":{"value":"-818991","left":{"value":"-899910","left":{"value":"-978989","left":null,"right":{"value":"-923926","left":{"value":"-970973","left":{"value":"-978911","left":null,"right":null},"right":{"value":"-970927","left":null,"right":null}},"right":null}},"right":{"value":"-823924","left":{"value":"-874975","left":null,"right":{"value":"-874925","left":null,"right":null}},"right":null}},"right":{"value":"-770977","left":{"value":"-818909","left":null,"right":null},"right":{"value":"-770923","left":null,"right":null}}},"right":{"value":"-658979","left":{"value":"-715922","left":null,"right":null},"right":{"value":"-658921","left":null,"right":null}}},"right":{"value":"-599920","left":{"value":"-650907","left":null,"right":null},"right":null}},"right":{"value":"-475918","left":{"value":"-538981","left":null,"right":{"value":"-490953","left":{"value":"-498951","left":{"value":"-538919","left":null,"right":null},"right":{"value":"-498949","left":null,"right":null}},"right":{"value":"-490947","left":null,"right":null}}},"right":null}},"right":{"value":"-410983","left":{"value":"-418959","left":{"value":"-450957","left":{"value":"-474955","left":null,"right":{"value":"-474945","left":null,"right":{"value":"-474905","left":null,"right":null}}},"right":{"value":"-450943","left":null,"right":null}},"right":{"value":"-418941","left":null,"right":null}},"right":{"value":"-410917","left":null,"right":null}}},"right":{"value":"-343916","left":{"value":"-378961","left":null,"right":{"value":"-378939","left":null,"right":null}},"right":{"value":"-330963","left":null,"right":{"value":"-330937","left":null,"right":null}}}},"right":{"value":"-203914","left":{"value":"-274985","left":{"value":"-290903","left":null,"right":null},"right":{"value":"-210967","left":{"value":"-274965","left":null,"right":{"value":"-274935","left":null,"right":{"value":"-274915","left":null,"right":null}}},"right":{"value":"-210933","left":null,"right":null}}},"right":null}},"right":{"value":"-130987","left":{"value":"-138969","left":{"value":"-175932","left":null,"right":null},"right":{"value":"-138931","left":null,"right":null}},"right":{"value":"-99930","left":{"value":"-130913","left":null,"right":null},"right":null}}},"right":{"value":"-55912","left":{"value":"-58971","left":{"value":"-98901","left":null,"right":null},"right":{"value":"-58929","left":null,"right":null}},"right":{"value":"-15928","left":null,"right":null}}},"right":null}'''

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

obj = json.loads(data)
x = Node(obj)

print('@startuml')
print('hide empty description')
print(x)
print('@enduml')