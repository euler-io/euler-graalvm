package com.github.euler.python;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

public class OutputStreamRawIOBaseProxy extends AbstractRawIOBaseProxy {

    private final OutputStream out;
    private final BufferedWriter writer;

    public OutputStreamRawIOBaseProxy(OutputStream out, Charset cs) {
        super();
        this.out = out;
        this.writer = new BufferedWriter(new OutputStreamWriter(out, cs));
    }

    @Override
    public void close() throws IOException {
        setClosed(true);
        this.writer.close();
        this.out.close();
    }

    @Override
    public byte[] read(int size) throws IOException {
        throw new RuntimeException(OutputStreamRawIOBaseProxy.class.getName() + " cant be read.");
    }

    @Override
    public byte[] readall() throws IOException {
        throw new RuntimeException(OutputStreamRawIOBaseProxy.class.getName() + " cant be read.");
    }

    @Override
    public void readinto(byte[] bytes) throws IOException {
        throw new RuntimeException(OutputStreamRawIOBaseProxy.class.getName() + " cant be read.");
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        this.out.write(buffer);
    }

    @Override
    public void flush() throws IOException {
        this.writer.flush();
    }

    @Override
    public boolean readable() throws IOException {
        return false;
    }

    @Override
    public String readline(int size) throws IOException {
        throw new RuntimeException(OutputStreamRawIOBaseProxy.class.getName() + " cant be read.");
    }

    @Override
    public List<String> readlines(int hint) throws IOException {
        throw new RuntimeException(OutputStreamRawIOBaseProxy.class.getName() + " cant be read.");
    }

    @Override
    public void seek(long offset, int whence) throws IOException {
        throw new RuntimeException(OutputStreamRawIOBaseProxy.class.getName() + " cant be read.");
    }

    @Override
    public boolean seekable() {
        return false;
    }

    @Override
    public boolean writable() {
        return !this.isClosed();
    }

    @Override
    public void writelines(List<String> lines) throws IOException {
        for (String line : lines) {
            this.writer.append(line);
        }
    }

}
