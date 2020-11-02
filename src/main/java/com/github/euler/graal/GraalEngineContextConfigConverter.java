package com.github.euler.graal;

import org.graalvm.polyglot.Engine;

import com.github.euler.configuration.ConfigContext;
import com.github.euler.configuration.TypesConfigConverter;
import com.typesafe.config.Config;

public class GraalEngineContextConfigConverter extends AbstractGraalEngineConfigConverter {

    @Override
    public String configType() {
        return "context";
    }

    @Override
    public Engine convert(Config config, ConfigContext configContext, TypesConfigConverter typeConfigConverter) {
        return configContext.get(Engine.class);
    }

}
