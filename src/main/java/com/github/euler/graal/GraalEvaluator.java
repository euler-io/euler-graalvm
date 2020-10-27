package com.github.euler.graal;

import org.graalvm.polyglot.Context;

import com.github.euler.core.Item;
import com.github.euler.core.ProcessingContext;

public abstract class GraalEvaluator implements AutoCloseable {

    private final Context graalContext;
    private final String code;

    public GraalEvaluator(String code) {
        this(Context
                .newBuilder()
                .allowAllAccess(true)
                .build(), code);
    }

    public GraalEvaluator(Context graalContext, String code) {
        super();
        this.graalContext = graalContext;
        this.code = code;
    }

    protected abstract ProcessingContext process(Context context, Item item);

    protected String getCode() {
        return code;
    }

    @Override
    public void close() throws Exception {
        if (graalContext != null) {
            graalContext.close(true);
        }
    }

}
