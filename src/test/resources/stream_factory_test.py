def test(stream_factory, uri, ctx):
	with stream_factory.open_text_output(uri, ctx) as output:
		output.write('Some content')

	with stream_factory.open_text_input(uri, ctx) as input:
		return input.read()

test