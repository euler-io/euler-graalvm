package com.github.euler.graal;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

import com.github.euler.core.Item;
import com.github.euler.core.ProcessingContext;

public abstract class GraalEvaluator implements AutoCloseable {

    private final Engine engine;
    private final String code;

    public GraalEvaluator(String code) {
        this(Engine.create(), code);
    }

    public GraalEvaluator(Engine engine, String code) {
        super();
        this.engine = engine;
        this.code = code;
    }

    public final ProcessingContext process(Item item) {
        try (Context ctx = createContext(item)) {
            return process(ctx, item);
        }
    }

    protected ProcessingContext process(Context ctx, Item item) {
        Value evaluated = eval(ctx);
        if (evaluated.canInstantiate()) {
            Value newInstance = newInstance(evaluated);
            Value processMethod = getProcessMethod(newInstance);
            Value executed = executeProcess(item, processMethod);
            return toProcessingContext(executed);
        } else if (evaluated.canExecute()) {
            Value executed = executeProcess(item, evaluated);
            return toProcessingContext(executed);
        } else {
            return toProcessingContext(evaluated);
        }
    }

    protected Value executeProcess(Item item, Value processMethod) {
        return processMethod.execute(item.parentURI.toString(), item.itemURI.toString(), new ProcessingContextProxy(item.ctx));
    }

    protected Value getProcessMethod(Value newInstance) {
        return newInstance.getMember("process");
    }

    protected Value newInstance(Value evaluated) {
        return evaluated.newInstance();
    }

    protected Value eval(Context ctx) {
        return ctx.eval(getLanguage(), getCode());
    }

    protected String getCode() {
        return code;
    }

    private Context createContext(Item item) {
        return createContext(this.engine, item);
    }

    protected Context createContext(Engine engine, Item item) {
        Context context = Context.newBuilder()
                .allowAllAccess(true)
                .engine(engine)
                .build();
        Value bindings = context.getBindings(getLanguage());
        setBindings(bindings, item);
        return context;
    }

    protected abstract ProcessingContext toProcessingContext(Value result);

    protected abstract void setBindings(Value bindings, Item item);

    protected abstract String getLanguage();

    @Override
    public void close() throws Exception {
        if (engine != null) {
            engine.close(true);
        }
    }

}
