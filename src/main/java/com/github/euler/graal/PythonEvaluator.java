package com.github.euler.graal;

import java.util.List;
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

    @SuppressWarnings("unchecked")
    @Override
    protected ProcessingContext toProcessingContext(Value result) {
        Builder builder = ProcessingContext.builder();
        Value get = result.getMember("get");
        Value metadata = get.execute("metadata");
        if (metadata != null) {
            System.out.println(metadata.getMember("keys").execute());
            metadata.getMember("keys").execute().as(List.class).forEach(k -> {
                
            });
            metadata.as(Map.class).forEach((k, v) -> {
                System.out.println(k.toString());
                builder.metadata(k.toString(), v);
            });
        }
        return builder.build();
    }

    @Override
    protected Context createContext(Engine engine, Item item) {
        return super.createContext(engine, item);
    }

}
