import java

class ProcessingContextBuilder(object):

	def __init__(self):
		self.metadata = java.type('java.util.HashMap')
		self.context = java.type('java.util.HashMap')

	def get_metadata(self, key):
		return self.metadata.get(key)

	def set_metadata(self, key, value):
		return self.metadata.put(key, value)

	def get_context(self, key):
		return self.context.get(key)

	def set_context(self, key, value):
		return self.context.put(key, value)