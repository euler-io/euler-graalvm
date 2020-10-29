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
//    @Test
    public void testEval() throws Exception {
        String code = IOUtils.toString(GraalTest.class.getResourceAsStream("/simple_python_processor.py"), "utf-8");
        graalContext.getPolyglotBindings().putMember("value", 4);
        Value value = graalContext.eval("python", code);
        System.out.println("canExecute " + value.canExecute());
        System.out.println("canInstantiate " + value.canInstantiate());
        System.out.println("canInvokeMember " + value.canInvokeMember("process"));
        for (String key : value.getMemberKeys()) {
            System.out.println("member key:" + key);
        }
        // Value process = value.getMember("process");
        // System.out.println("canExecute " + process.canExecute());
        // System.out.println("canInstantiate " + process.canInstantiate());
        // System.out.println("canInvokeMember " +
        // process.canInvokeMember("process"));
        // assert value.canExecute();
        // Map<String, Object> obj = value.execute(4).as(Map.class);
        Map<String, Object> obj = value.as(Map.class);
        System.out.println(obj.get("metadata"));
    }

    @After
    public void shutdown() {
        graalContext.close(true);
    }

}
