package com.github.euler.python;

public abstract class AbstractRawIOBaseProxy extends RawIOBase {

    @Override
    public int fileno() {
        throw new RuntimeException(InputStreamRawIOBaseProxy.class.getName() + " does not have a file descriptor.");
    }

    @Override
    public boolean isatty() {
        return false;
    }

    @Override
    public long teel() {
        throw new RuntimeException("teel not supported.");
    }

    @Override
    public IOBase truncate(long size) {
        throw new RuntimeException("truncate not supported.");
    }

}
