from java.util import HashMap
import io


class ProcessingContextBuilder(object):

    @staticmethod
    def to_map(p_dict):
        map = HashMap()
        for key, value in p_dict.items():
            map.put(key, value)
        return map

    def __init__(self, metadata={}, context={}):
        self.metadata = ProcessingContextBuilder.to_map(metadata)
        self.context = ProcessingContextBuilder.to_map(context)

    def get_metadata(self, key):
        return self.metadata.get(key)

    def set_metadata(self, key, value):
        return self.metadata.put(key, value)

    def get_context(self, key):
        return self.context.get(key)

    def set_context(self, key, value):
        return self.context.put(key, value)


class RawIOBaseWrapper(io.RawIOBase):

    def __init__(self, wrapped):
        self.wrapped = wrapped
        self.closed = False

    def close(self):
        self.closed = True
        self.wrapped.close()

    def fileno(self):
        return self.wrapped.fileno()

    def flush(self):
        self.wrapped.flush()

    def isatty(self):
        return self.wrapped.isatty()

    def readable(self):
        return self.wrapped.readable()

    def readline(self, size=-1):
        return self.wrapped.readline(size)

    def readlines(self, hint=-1):
        return self.wrapped.readlines(hint)

    def seek(self, offset, whence=io.SEEK_SET):
        return self.wrapped.seek(offset, whence)

    def seekable(self):
        return self.wrapped.seekable()

    def tell(self):
        return self.wrapped.tell()

    def truncate(self, size=None):
        self.wrapped.truncate(size)

    def writable(self):
        return self.wrapped.writable()

    def writelines(self, lines):
        self.wrapped.writelines(lines)

    def __del__(self):
        self.wrapped.__del__()

    def read(self, size=-1):
        return self.wrapped.read(size)

    def readall(self):
        return self.wrapped.readall()

    def readinto(self, b):
        self.wrapped.readinto(b)

    def write(self, b):
        self.wrapped.write(b)
