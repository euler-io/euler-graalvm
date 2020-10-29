import euler

def process(parent_uri, item_uri, ctx):
	value = ctx.metadata.value
	return {
		'metadata': {'value': (value + 1)}
	}

process