import euler

def process(parent_uri, item_uri, ctx, stream_factory):
	value = ctx.metadata.value
	return euler.ProcessingContextBuilder(
		metadata = {
			'value': (value + 1)
		},
		context = {
			'value': (ctx.context.value + 1)
		}
	)

process