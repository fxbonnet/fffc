package com.an.util;

import org.junit.Ignore;
import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestFileGenerator {

    byte[] buffer = "1970-01-01John           Smith           81.5\n".getBytes();
    int number_of_lines = 4000000 ;

    @Test
    @Ignore
    public void generateTestFile() throws  Exception{
        FileChannel rwChannel = new RandomAccessFile("/Users/anaikare/personal/temp/fixed-file-format-converter/src/main/resources/files/Source_BIG.txt", "rw").getChannel();
        ByteBuffer wrBuf = rwChannel.map(FileChannel.MapMode.READ_WRITE, 0, buffer.length * number_of_lines);
        for (int i = 0; i < number_of_lines; i++)
        {
            wrBuf.put(buffer);
        }
        rwChannel.close();

    }
}
