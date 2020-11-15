package com.github.euler.graal;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Engine;
import org.junit.Test;

import com.github.euler.core.Item;
import com.github.euler.core.ProcessingContext;
import com.github.euler.file.FileStreamFactory;

public class PythonEvaluatorTest {

    @Test
    public void testProcessFunction() throws Exception {
        Engine engine = Engine.newBuilder()
                .option("python.PythonPath", System.getenv("PYTHONPATH"))
                .build();

        String code = IOUtils.toString(PythonEvaluatorTest.class.getResourceAsStream("/simple_python_processor.py"), "utf-8");
        try (PythonEvaluator ev = new PythonEvaluator(engine, code)) {
            ProcessingContext ctx = ProcessingContext.builder()
                    .metadata("value", 3)
                    .context("value", 3)
                    .build();
            Item item = new Item(new URI("file:///some/path"), new URI("file:///some/path/item"), ctx);
            ctx = ev.process(item);
            assertEquals(4, ctx.metadata("value"));
            assertEquals(4, ctx.context("value"));
        }
    }

    @Test
    public void testProcessFunctionWithStreamFactory() throws Exception {
        URI uri = StreamFactoryProxyTest.class.getResource("/test_file.txt").toURI();
        Engine engine = Engine.newBuilder()
                .option("python.PythonPath", System.getenv("PYTHONPATH"))
                .build();

        String code = IOUtils.toString(PythonEvaluatorTest.class.getResourceAsStream("/simple_python_processor_with_stream_factory.py"), "utf-8");
        try (PythonEvaluator ev = new PythonEvaluator(engine, code, new FileStreamFactory())) {
            ProcessingContext ctx = ProcessingContext.EMPTY;
            Item item = new Item(uri, uri, ctx);
            ctx = ev.process(item);
            assertEquals("Some content", ctx.metadata("content"));
        }
    }

    @Test
    public void testProcessClass() throws Exception {
        Engine engine = Engine.newBuilder()
                .option("python.PythonPath", System.getenv("PYTHONPATH"))
                .build();

        String code = IOUtils.toString(PythonEvaluatorTest.class.getResourceAsStream("/simple_python_processor_class.py"), "utf-8");
        try (PythonEvaluator ev = new PythonEvaluator(engine, code)) {
            ProcessingContext ctx = ProcessingContext.builder()
                    .metadata("value", 3)
                    .context("value", 3)
                    .build();
            Item item = new Item(new URI("file:///some/path"), new URI("file:///some/path/item"), ctx);
            ctx = ev.process(item);
            assertEquals(4, ctx.metadata("value"));
            assertEquals(4, ctx.context("value"));
        }
    }

}
