package com.github.euler.graal;

import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

import com.github.euler.core.Item;
import com.github.euler.core.ProcessingContext;
import com.github.euler.core.ProcessingContext.Builder;

public class PythonEvaluator extends GraalEvaluator {

    protected PythonEvaluator(Engine engine, String code) {
        super(engine, code);
    }

    protected PythonEvaluator(String code) {
        super(code);
    }

    @Override
    protected String getLanguage() {
        return "python";
    }

    @Override
    protected void setBindings(Value bindings, Item item) {
        bindings.putMember("parent_uri", item.parentURI.toString());
        bindings.putMember("item_uri", item.itemURI.toString());
        bindings.putMember("context", new ProcessingContextProxy(item.ctx));
    }

    @Override
    protected ProcessingContext toProcessingContext(Value result) {
        Builder builder = ProcessingContext.builder();

        Map<Object, Object> metadata = result.getMember("metadata").asHostObject();
        metadata.forEach((k, v) -> builder.metadata(k.toString(), v));

        Map<Object, Object> context = result.getMember("context").asHostObject();
        context.forEach((k, v) -> builder.context(k.toString(), v));

        return builder.build();
    }

    @Override
    protected Context createContext(Engine engine, Item item) {
        return super.createContext(engine, item);
    }

}
