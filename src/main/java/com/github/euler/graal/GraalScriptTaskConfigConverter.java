package com.github.euler.graal;

import java.util.Set;

import org.graalvm.polyglot.Engine;

import com.github.euler.configuration.AbstractTaskConfigConverter;
import com.github.euler.configuration.ConfigContext;
import com.github.euler.configuration.TasksConfigConverter;
import com.github.euler.configuration.TypesConfigConverter;
import com.github.euler.core.ItemProcessorTask;
import com.github.euler.core.Task;
import com.typesafe.config.Config;

public class GraalScriptTaskConfigConverter extends AbstractTaskConfigConverter {

    public static final Set<String> LANGUAGES_SUPPORTED = Set.of("python");

    @Override
    public String type() {
        return "script";
    }

    @Override
    public Task convert(Config config, ConfigContext ctx, TypesConfigConverter typeConfigConverter, TasksConfigConverter tasksConfigConverter) {
        String language = config.getString("language");
        if (!LANGUAGES_SUPPORTED.contains(language)) {
            throw new IllegalArgumentException(language + " not supported.");
        }
        String script = config.getString("script");
        String name = getName(config, tasksConfigConverter);
        Engine engine = typeConfigConverter.convert("engine", config.getValue("engine"), ctx);
        GraalEvaluator evaluator = getEvaluator(language, engine, script);
        return new ItemProcessorTask(name, new GraalProcessor(evaluator));
    }

    private GraalEvaluator getEvaluator(String language, Engine engine, String script) {
        switch (language) {
        case "python":
            return new PythonEvaluator(engine, script);
        default:
            throw new IllegalArgumentException(language + " not supported.");
        }
    }

}
