package com.github.euler.python;

import java.io.IOException;

public abstract class RawIOBase extends IOBase {

    public abstract byte[] read(int size) throws IOException;

    public byte[] read() throws IOException {
        return read(-1);
    }

    public abstract byte[] readall() throws IOException;

    public abstract void readinto(byte[] bytes) throws IOException;

    public abstract void write(byte[] bytes) throws IOException;

}
