package com.github.euler.graal;

import org.graalvm.polyglot.Context;

import com.github.euler.core.Item;
import com.github.euler.core.ProcessingContext;

public class PythonEvaluator extends GraalEvaluator {

    public PythonEvaluator(Context graalContext, String code) {
        super(graalContext, code);
    }

    public PythonEvaluator(String code) {
        super(code);
    }

    @Override
    protected ProcessingContext process(Context context, Item item) {
        return null;
    }

}
