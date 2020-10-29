package com.github.euler.graal;

import java.util.Set;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import com.github.euler.core.ProcessingContext;

public class ProcessingContextProxy implements ProxyObject {

    private static final String ACTION = "action";
    private static final String CONTEXT = "context";
    private static final String METADATA = "metadata";

    private static final Set<String> MEMBERS = Set.of(ACTION, METADATA, CONTEXT);

    private ProcessingContext ctx;

    protected ProcessingContextProxy(ProcessingContext ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public Object getMember(String key) {
        switch (key) {
            case METADATA :
                return ProxyObject.fromMap(ctx.metadata());
            case CONTEXT :
                return ProxyObject.fromMap(ctx.context());
            case ACTION :
            default :
                return null;
        }
    }

    @Override
    public Object getMemberKeys() {
        return MEMBERS.toArray(String[]::new);
    }

    @Override
    public boolean hasMember(String key) {
        return MEMBERS.contains(key);
    }

    @Override
    public void putMember(String key, Value value) {
        throw new IllegalArgumentException(ProcessingContext.class + " is immutable.");
    }

}
