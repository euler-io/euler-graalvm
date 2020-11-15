import euler

def process(parent_uri, item_uri, ctx, stream_factory):

	with stream_factory.open_text_input(item_uri, ctx) as input:
		content = input.read()

	return euler.ProcessingContextBuilder(
		metadata = {
			'content': content
		}
	)

process