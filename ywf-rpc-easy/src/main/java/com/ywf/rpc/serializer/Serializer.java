package com.ywf.rpc.serializer;

import java.io.IOException;

public interface Serializer {
    <T> byte[] serialize(T obj) throws IOException;
    <T> T deserialize(byte[] data, Class<T> clazz) throws IOException;
}
