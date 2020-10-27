package com.github.euler.graal;

import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraalTest {

    private Context graalContext;

    @Before
    public void setup() {
        this.graalContext = Context
                .newBuilder()
                .allowAllAccess(true)
                .build();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testEval() throws Exception {
        String code = IOUtils.toString(GraalTest.class.getResourceAsStream("/simple_python_processor.py"), "utf-8");
        Value value = graalContext.eval("python", code);
        assert value.getMember("process").canExecute();
        Map<String,Object>  obj = value.execute().as(Map.class);
        System.out.println(obj.get("metadata"));
    }

    @After
    public void shutdown() {
        graalContext.close(true);
    }

}
