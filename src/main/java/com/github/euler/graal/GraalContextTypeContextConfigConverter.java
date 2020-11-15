package com.github.euler.graal;

import org.graalvm.polyglot.Engine;

import com.github.euler.configuration.ConfigContext;
import com.github.euler.configuration.TypesConfigConverter;
import com.typesafe.config.Config;

public class GraalContextTypeContextConfigConverter extends AbstractGraalContextConfigConverter {

    @Override
    public String configType() {
        return "graal";
    }

    @Override
    public GraalContext convert(Config config, ConfigContext configContext, TypesConfigConverter typeConfigConverter) {
        Engine.Builder engineBuilder = Engine.newBuilder();

        engineBuilder.option("python.PythonPath", System.getenv("PYTHONPATH"));
        config.getObject("options").forEach((k, v) -> engineBuilder.option(k, v.unwrapped().toString()));

        Engine engine = engineBuilder.build();
        String pythonPathUrl = config.getString("python-path");
        return new GraalContext(engine, pythonPathUrl);
    }

}
