package com.github.euler.graal;

import java.io.IOException;

import com.github.euler.core.Item;
import com.github.euler.core.ItemProcessor;
import com.github.euler.core.ProcessingContext;

public class GraalProcessor implements ItemProcessor {

    private final GraalEvaluator evaluator;

    public GraalProcessor(GraalEvaluator evaluator) {
        super();
        this.evaluator = evaluator;
    }

    @Override
    public ProcessingContext process(Item item) throws IOException {
        return evaluator.process(item);
    }

}
