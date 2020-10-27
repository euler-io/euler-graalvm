
class Item(object):
	def __init__(self, parentURI, itemURI, processingContext):
		self.parentURI = parentURI
		self.itemURI = itemURI
		self.processingContext = processingContext

class ContextProcessor(object):

	def process(self, item):
		return ProcessingContext.EMPTY