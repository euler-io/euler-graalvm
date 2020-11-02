package com.github.euler.python;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public abstract class IOBase implements Closeable {

    private boolean closed = false;

    public abstract void close() throws IOException;

    public abstract int fileno();

    public abstract void flush() throws IOException;

    public abstract boolean isatty();

    public abstract boolean readable() throws IOException;

    public abstract String readline(int size) throws IOException;

    public String readline() throws IOException {
        return readline(-1);
    }

    public abstract List<String> readlines(int hint) throws IOException;

    public List<String> readlines() throws IOException {
        return readlines(-1);
    }

    public abstract void seek(long offset, int whence) throws IOException;

    public void seek(long offset) throws IOException {
        seek(offset, 0);
    }

    public abstract boolean seekable();

    public abstract long teel();

    public abstract IOBase truncate(long size);

    public IOBase truncate() {
        return truncate(teel());
    }

    public abstract boolean writable();

    public abstract void writelines(List<String> lines) throws IOException;

    public void __del__() throws IOException {
        close();
    }

    public boolean isClosed() {
        return closed;
    }

    protected void setClosed(boolean closed) {
        this.closed = closed;
    }

}
