package com.github.euler.graal;

import com.github.euler.configuration.TypeConfigConverter;

public abstract class AbstractGraalContextConfigConverter implements TypeConfigConverter<GraalContext> {

    @Override
    public String type() {
        return GraalContextConfigConverter.GRAAL_CONTEXT;
    }

}
