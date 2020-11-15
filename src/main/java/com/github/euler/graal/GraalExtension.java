package com.github.euler.graal;

import java.util.List;

import com.github.euler.configuration.ContextConfigConverter;
import com.github.euler.configuration.EulerExtension;
import com.github.euler.configuration.TaskConfigConverter;
import com.github.euler.configuration.TypeConfigConverter;

public class GraalExtension implements EulerExtension {

    @Override
    public List<ContextConfigConverter> pathConverters() {
        return List.of(new GraalContextConfigConverter());
    }

    @Override
    public List<TaskConfigConverter> taskConverters() {
        return List.of(new GraalScriptTaskConfigConverter());
    }

    @Override
    public List<TypeConfigConverter<?>> typeConverters() {
        return List.of(new GraalContextInContextConfigConverter(), new GraalContextTypeContextConfigConverter());
    }

    @Override
    public String getDescription() {
        return "Extension for GraalVM support.";
    }

}
