package com.github.euler.graal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Engine;

import com.github.euler.common.StreamFactory;
import com.github.euler.common.StreamFactoryContextConfigConverter;
import com.github.euler.configuration.AbstractTaskConfigConverter;
import com.github.euler.configuration.ConfigContext;
import com.github.euler.configuration.TasksConfigConverter;
import com.github.euler.configuration.TypesConfigConverter;
import com.github.euler.core.ItemProcessorTask;
import com.github.euler.core.Task;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

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
        config = config.withFallback(getDefaultConfig(language));
        List<String> defaultStatements = config.getStringList("default-statements");
        String name = getName(config, tasksConfigConverter);
        GraalContext graalContext = typeConfigConverter.convert(GraalContextConfigConverter.GRAAL_CONTEXT, config.getValue("graal"), ctx);
        StreamFactory streamFactory = typeConfigConverter.convert(StreamFactoryContextConfigConverter.STREAM_FACTORY, config.getValue("stream-factory"), ctx);
        String script = getScript(graalContext, config);
        GraalEvaluator evaluator = getEvaluator(language, graalContext.getEngine(), script, streamFactory, defaultStatements);
        return new ItemProcessorTask(name, new GraalProcessor(evaluator));
    }

    protected String getScript(GraalContext graalContext, Config config) {
        if (config.hasPath("script-path")) {
            String scriptPath = config.getString("script-path");
            File scriptFile = new File(graalContext.getPythonPath(), scriptPath);
            try (InputStream in = new BufferedInputStream(new FileInputStream(scriptFile))) {
                return IOUtils.toString(in, "utf-8");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return config.getString("script");
        }
    }

    private GraalEvaluator getEvaluator(String language, Engine engine, String script, StreamFactory streamFactory, List<String> defaultStatements) {
        switch (language) {
        case "python":
            return new PythonEvaluator(engine, script, streamFactory, defaultStatements);
        default:
            throw new IllegalArgumentException(language + " not supported.");
        }
    }

    protected Config getDefaultConfig(String language) {
        URL resource = GraalScriptTaskConfigConverter.class.getClassLoader().getResource("graalscripttask-" + language + ".conf");
        return ConfigFactory.parseURL(resource);
    }

}
