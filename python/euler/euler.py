from java.util import HashMap

class ProcessingContextBuilder(object):

	@staticmethod
	def to_map(p_dict):
		map = HashMap()
		for key, value in p_dict.items():
			map.put(key, value)
		return map

	def __init__(self, metadata={}, context={}):
		self.metadata = ProcessingContextBuilder.to_map(metadata)
		self.context = ProcessingContextBuilder.to_map(context)

	def get_metadata(self, key):
		return self.metadata.get(key)

	def set_metadata(self, key, value):
		return self.metadata.put(key, value)

	def get_context(self, key):
		return self.context.get(key)

	def set_context(self, key, value):
		return self.context.put(key, value)