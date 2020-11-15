package com.github.euler.graal;

import org.graalvm.polyglot.Engine;

public class GraalContext {

    private final Engine engine;
    private final String pythonPathUrl;

    public GraalContext(Engine engine, String pythonPathUrl) {
        super();
        this.engine = engine;
        this.pythonPathUrl = pythonPathUrl;
    }

    public Engine getEngine() {
        return engine;
    }

    public String getPythonPath() {
        return pythonPathUrl;
    }

}
