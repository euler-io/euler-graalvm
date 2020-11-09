package com.github.euler.python;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.graalvm.polyglot.Value;

import com.github.euler.common.StreamFactory;
import com.github.euler.core.ProcessingContext;

public class StreamFactoryProxy {

    private final StreamFactory sf;
    private final Charset cs;

    public StreamFactoryProxy(StreamFactory sf, Charset cs) {
        super();
        this.sf = sf;
        this.cs = cs;
    }

    public RawIOBase open_input(String uri, Value ctx) throws IOException {
        try {
            InputStream in = sf.openInputStream(new URI(uri), ProcessingContext.EMPTY);
            return new InputStreamRawIOBaseProxy(in, cs);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public RawIOBase open_output(String uri, Value ctx) throws IOException {

        try {
            OutputStream out = sf.openOutputStream(new URI(uri), ProcessingContext.EMPTY);
            return new OutputStreamRawIOBaseProxy(out, cs);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
