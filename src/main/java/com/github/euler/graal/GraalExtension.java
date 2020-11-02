package com.github.euler.graal;

import java.util.List;

import com.github.euler.configuration.ContextConfigConverter;
import com.github.euler.configuration.EulerExtension;
import com.github.euler.configuration.TaskConfigConverter;
import com.github.euler.configuration.TypeConfigConverter;

public class GraalExtension implements EulerExtension {

    @Override
    public List<ContextConfigConverter> pathConverters() {
        // TODO Auto-generated method stub
        return EulerExtension.super.pathConverters();
    }

    @Override
    public List<TaskConfigConverter> taskConverters() {
        // TODO Auto-generated method stub
        return EulerExtension.super.taskConverters();
    }

    @Override
    public List<TypeConfigConverter<?>> typeConverters() {
        // TODO Auto-generated method stub
        return EulerExtension.super.typeConverters();
    }

    @Override
    public String getDescription() {
        return "Extension for GraalVM support.";
    }

}
