package com.github.euler.graal;

import com.github.euler.configuration.ConfigContext;
import com.github.euler.configuration.ContextConfigConverter;
import com.github.euler.configuration.TypesConfigConverter;
import com.typesafe.config.ConfigValue;

public class GraalContextConfigConverter implements ContextConfigConverter {

    public static final String GRAAL_CONTEXT = "graal";

    @Override
    public String path() {
        return GRAAL_CONTEXT;
    }

    @Override
    public ConfigContext convert(ConfigValue value, ConfigContext configContext, TypesConfigConverter typesConfigConverter) {
        GraalContext graalContext = typesConfigConverter.convert(GRAAL_CONTEXT, value, configContext);
        ConfigContext ctx = ConfigContext.builder().put(GraalContext.class, graalContext).build();
        return ctx;
    }

}
