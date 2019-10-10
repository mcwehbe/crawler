package com.crawler.command;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Service
public class WriterServiceImpl {

    void write(RandomAccessFile file, String value) throws IOException {
        FileChannel channel = file.getChannel();
        byte[] strBytes = value.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
        buffer.put(strBytes);
        buffer.flip();
        channel.write(buffer);
    }

    void close(RandomAccessFile file) throws IOException {
        FileChannel channel = file.getChannel();
        channel.close();
        file.close();
    }
}
