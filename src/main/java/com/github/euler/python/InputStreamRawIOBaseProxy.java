package com.github.euler.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class InputStreamRawIOBaseProxy extends AbstractRawIOBaseProxy {

    private final InputStream in;
    private final BufferedReader reader;

    public InputStreamRawIOBaseProxy(InputStream in, Charset cs) {
        super();
        this.in = in;
        this.reader = new BufferedReader(new InputStreamReader(in, cs));
    }

    @Override
    public void close() throws IOException {
        this.setClosed(true);
        this.reader.close();
    }

    @Override
    public void flush() {
        throw new RuntimeException(InputStreamRawIOBaseProxy.class.getName() + " cant be flushed.");
    }

    @Override
    public boolean readable() throws IOException {
        return !this.isClosed() && this.reader.ready();
    }

    @Override
    public String readline(int size) throws IOException {
        if (size < 0) {
            return this.reader.readLine();
        } else {
            throw new RuntimeException("Size " + size + " not supported.");
        }
    }

    @Override
    public List<String> readlines(int hint) throws IOException {
        List<String> lines;
        if (hint < 0) {
            lines = new ArrayList<>();
            String line = null;
            while ((line = this.reader.readLine()) != null) {
                lines.add(line);
            }
        } else {
            lines = new ArrayList<>(hint);
            for (int i = 0; i < hint; i++) {
                lines.add(this.reader.readLine());
            }
        }
        return lines;
    }

    @Override
    public void seek(long offset, int whence) throws IOException {
        switch (whence) {
        case 0:
            this.reader.skip(offset);
            break;
        default:
            throw new RuntimeException("Whence " + whence + " not supported.");
        }
    }

    @Override
    public boolean seekable() {
        return true;
    }

    @Override
    public boolean writable() {
        return false;
    }

    @Override
    public void writelines(List<String> lines) {
        throw new RuntimeException(InputStreamRawIOBaseProxy.class.getName() + " cant be written.");
    }

    @Override
    public byte[] read(int size) throws IOException {
        if (size < 0) {
            return this.readall();
        } else {
            return this.in.readNBytes(size);
        }
    }

    @Override
    public byte[] readall() throws IOException {
        return this.in.readAllBytes();
    }

    @Override
    public void readinto(byte[] buff) throws IOException {
        this.in.read(buff);
    }

    @Override
    public void write(byte[] bytes) {
        throw new RuntimeException(InputStreamRawIOBaseProxy.class.getName() + " cant be written.");
    }

}
