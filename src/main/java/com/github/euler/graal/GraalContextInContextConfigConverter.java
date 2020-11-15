package com.github.euler.graal;

import com.github.euler.configuration.ConfigContext;
import com.github.euler.configuration.TypesConfigConverter;
import com.typesafe.config.Config;

public class GraalContextInContextConfigConverter extends AbstractGraalContextConfigConverter {

    @Override
    public String configType() {
        return "context";
    }

    @Override
    public GraalContext convert(Config config, ConfigContext configContext, TypesConfigConverter typeConfigConverter) {
        return configContext.get(GraalContext.class);
    }

}
