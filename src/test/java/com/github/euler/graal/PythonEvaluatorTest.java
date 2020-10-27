package com.github.euler.graal;

import org.apache.commons.io.IOUtils;

public class PythonEvaluatorTest {
    
//    @Test
    public void testProcessMetadata() throws Exception {
        String code = IOUtils.toString(PythonEvaluatorTest.class.getResourceAsStream("/simple_python_processor.py"), "utf-8");
        try (PythonEvaluator ev = new PythonEvaluator(code)) {
//            ev.
        }
    }

}
