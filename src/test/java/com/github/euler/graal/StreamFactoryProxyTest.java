package com.github.euler.graal;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.junit.Test;

public class StreamFactoryProxyTest {

    @Test
    public void testProcessFunction() throws Exception {
        String uri = File.createTempFile("euler", ".txt").toURI().toString();

        Engine engine = Engine.newBuilder()
                .option("python.PythonPath", System.getenv("PYTHONPATH"))
                .build();

        try (Context context = Context.newBuilder("python")
                .engine(engine)
                .build()) {
            String code = IOUtils.toString(PythonEvaluatorTest.class.getResourceAsStream("/stream_factory_test.py"), "utf-8");
            String content = context.eval("python", code).execute(uri).asString();
            assertEquals("Some content", content);
        }
    }

}
