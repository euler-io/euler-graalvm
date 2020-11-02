package com.github.euler.graal;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Engine;
import org.junit.Test;

import com.github.euler.core.Item;
import com.github.euler.core.ProcessingContext;

public class PythonEvaluatorTest {

    @Test
    public void testProcessMetadata() throws Exception {
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

}
