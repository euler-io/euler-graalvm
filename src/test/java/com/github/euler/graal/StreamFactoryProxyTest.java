package com.github.euler.graal;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;
import org.junit.Test;

import com.github.euler.core.ProcessingContext;
import com.github.euler.file.FileStreamFactory;
import com.github.euler.python.StreamFactoryProxy;

public class StreamFactoryProxyTest {

    @Test
    public void testRead() throws Exception {
        String uri = StreamFactoryProxyTest.class.getResource("/test_file.txt").toURI().toString();

        Engine engine = Engine.newBuilder()
                .option("python.PythonPath", System.getenv("PYTHONPATH"))
                .build();

        try (Context context = Context.newBuilder("python")
                .engine(engine)
                .allowAllAccess(true)
                .build()) {
            String code = IOUtils.toString(StreamFactoryProxyTest.class.getResourceAsStream("/stream_factory_read_test.py"), "utf-8");
            context.eval("python", "import euler");
            StreamFactoryProxy streamFactoryProxy = new StreamFactoryProxy(new FileStreamFactory(), Charset.forName("utf-8"));
            Value streamFactory = context.eval("python", "euler.StreamFactoryWrapper").newInstance(streamFactoryProxy);
            Value result = context.eval("python", code).execute(streamFactory, uri, new ProcessingContextProxy(ProcessingContext.EMPTY));
            String content = result.asString();
            assertEquals("Some content", content);
        }
    }

    @Test
    public void testReadAndWrite() throws Exception {
        String uri = File.createTempFile("euler", ".txt").toURI().toString();

        Engine engine = Engine.newBuilder()
                .option("python.PythonPath", System.getenv("PYTHONPATH"))
                .build();

        try (Context context = Context.newBuilder("python")
                .engine(engine)
                .allowAllAccess(true)
                .build()) {
            String code = IOUtils.toString(StreamFactoryProxyTest.class.getResourceAsStream("/stream_factory_test.py"), "utf-8");
            context.eval("python", "import euler");
            StreamFactoryProxy streamFactoryProxy = new StreamFactoryProxy(new FileStreamFactory(), Charset.forName("utf-8"));
            Value streamFactory = context.eval("python", "euler.StreamFactoryWrapper").newInstance(streamFactoryProxy);
            Value result = context.eval("python", code).execute(streamFactory, uri, new ProcessingContextProxy(ProcessingContext.EMPTY));
            String content = result.asString();
            assertEquals("Some content", content);
        }
    }

}
