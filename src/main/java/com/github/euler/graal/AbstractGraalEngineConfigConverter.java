package com.github.euler.graal;

import org.graalvm.polyglot.Engine;

import com.github.euler.configuration.TypeConfigConverter;

public abstract class AbstractGraalEngineConfigConverter implements TypeConfigConverter<Engine> {

    private static final String TYPE = "engine";

    @Override
    public String type() {
        return TYPE;
    }

}
