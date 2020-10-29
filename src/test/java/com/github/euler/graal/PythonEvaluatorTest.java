package com.github.euler.graal;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.github.euler.core.Item;
import com.github.euler.core.ProcessingContext;

public class PythonEvaluatorTest {

    @Test
    public void testProcessMetadata() throws Exception {
        String code = IOUtils.toString(PythonEvaluatorTest.class.getResourceAsStream("/simple_python_processor.py"), "utf-8");
        try (PythonEvaluator ev = new PythonEvaluator(code)) {
            ProcessingContext ctx = ProcessingContext.builder().metadata("value", 3).build();
            Item item = new Item(new URI("file:///some/path"), new URI("file:///some/path/item"), ctx);
            ctx = ev.process(item);
            assertEquals(4, ctx.metadata("value"));
        }
    }

}
