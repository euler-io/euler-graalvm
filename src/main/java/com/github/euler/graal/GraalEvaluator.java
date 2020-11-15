package com.github.euler.graal;

import java.util.ArrayList;
import java.util.List;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

import com.github.euler.common.StreamFactory;
import com.github.euler.core.Item;
import com.github.euler.core.ProcessingContext;

public abstract class GraalEvaluator implements AutoCloseable {

    private final Engine engine;
    private final String code;
    private final StreamFactory sf;
    private List<String> defaultStatements;

    public GraalEvaluator(Engine engine, String code, StreamFactory sf, List<String> defaultStatements) {
        super();
        this.engine = engine;
        this.code = code;
        this.sf = sf;
        this.defaultStatements = new ArrayList<>(defaultStatements);
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
            Value executed = executeProcess(ctx, item, processMethod);
            return toProcessingContext(executed);
        } else if (evaluated.canExecute()) {
            Value executed = executeProcess(ctx, item, evaluated);
            return toProcessingContext(executed);
        } else {
            Value bindings = ctx.getBindings(getLanguage());
            setBindings(ctx, bindings, item);
            return toProcessingContext(evaluated);
        }
    }

    protected Value executeProcess(Context ctx, Item item, Value processMethod) {
        Object[] arguments = buildArguments(ctx, item);
        return processMethod.execute(arguments);
    }

    protected abstract Object[] buildArguments(Context ctx, Item item);

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
        this.defaultStatements.forEach(stmt -> context.eval(getLanguage(), stmt));
        return context;
    }

    protected abstract ProcessingContext toProcessingContext(Value result);

    protected abstract void setBindings(Context context, Value bindings, Item item);

    protected abstract String getLanguage();

    @Override
    public void close() throws Exception {
        if (engine != null) {
            engine.close(true);
        }
    }

    protected StreamFactory getStreamFactory() {
        return sf;
    }

    public void addDefaultStatement(String stmt) {
        this.defaultStatements.add(stmt);
    }

    public List<String> getDefaultStatements() {
        return defaultStatements;
    }

    public void setDefaultStatements(List<String> defaultStatements) {
        this.defaultStatements = new ArrayList<>(defaultStatements);
    }

}
