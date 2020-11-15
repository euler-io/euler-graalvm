package com.github.euler.graal;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

import com.github.euler.common.StreamFactory;
import com.github.euler.core.Item;
import com.github.euler.core.ProcessingContext;
import com.github.euler.core.ProcessingContext.Builder;
import com.github.euler.file.FileStreamFactory;
import com.github.euler.python.StreamFactoryProxy;

public class PythonEvaluator extends GraalEvaluator {

    public PythonEvaluator(Engine engine, String code, StreamFactory sf, List<String> defaultStatements) {
        super(engine, code, sf, defaultStatements);
    }

    public PythonEvaluator(Engine engine, String code, StreamFactory sf) {
        super(engine, code, sf, List.of("import euler"));
    }

    public PythonEvaluator(Engine engine, String code) {
        super(engine, code, null, Collections.emptyList());
    }

    public PythonEvaluator(String code) {
        super(Engine.create(), code, null, Collections.emptyList());
    }

    @Override
    protected String getLanguage() {
        return "python";
    }

    @Override
    protected void setBindings(Context context, Value bindings, Item item) {
        bindings.putMember("parent_uri", item.parentURI.toString());
        bindings.putMember("item_uri", item.itemURI.toString());
        bindings.putMember("context", new ProcessingContextProxy(item.ctx));
        Value streamFactory;
        if (getStreamFactory() != null) {
            streamFactory = createStreamFactoryProxy(context);
        } else {
            streamFactory = context.eval(getLanguage(), "None");
        }
        bindings.putMember("stream_factory", streamFactory);
    }

    private Value createStreamFactoryProxy(Context context) {
        StreamFactoryProxy streamFactoryProxy = new StreamFactoryProxy(new FileStreamFactory(), Charset.forName("utf-8"));
        return context.eval(getLanguage(), "euler.StreamFactoryWrapper").newInstance(streamFactoryProxy);
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
    protected Object[] buildArguments(Context ctx, Item item) {
        String parentUri = item.parentURI.toString();
        String itemUri = item.itemURI.toString();
        ProcessingContextProxy processingContext = new ProcessingContextProxy(item.ctx);
        Value streamFactory;
        if (getStreamFactory() != null) {
            streamFactory = createStreamFactoryProxy(ctx);
        } else {
            streamFactory = ctx.eval(getLanguage(), "None");
        }
        return new Object[] { parentUri, itemUri, processingContext, streamFactory };
    }

}
