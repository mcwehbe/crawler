package com.crawler.command;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class WriterServiceImplTest {

    private WriterServiceImpl writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write() throws Exception {
        File file = File.createTempFile("temp", null);
        RandomAccessFile stream = new RandomAccessFile(file, "rw");
        writerService.write(stream, "hello ");
        writerService.write(stream, "world");
        writerService.close(stream);

        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        String result = new String(data, "UTF-8");
        assertEquals(result, "hello world");

        file.deleteOnExit();
    }
}