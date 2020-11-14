def test(stream_factory, uri, ctx):
	with stream_factory.open_input(uri, ctx) as input:
		buff = input.read()
		return str(buff, 'utf-8')
test