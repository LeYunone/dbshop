package com.leyunone.dbshop.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppender {
    private final File file;
    private final int bufferSize;
    private final boolean append;
    private BufferedWriter writer;

    public FileAppender(File file, int bufferSize, boolean append) {
        this.file = file;
        this.bufferSize = bufferSize;
        this.append = append;
        initWriter();
    }

    private void initWriter() {
        try {
            writer = new BufferedWriter(new FileWriter(file, append), bufferSize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize FileAppender", e);
        }
    }

    public void append(String content) {
        try {
            writer.write(content);
            writer.newLine(); // 如果需要每行追加内容，可以加上这一行
        } catch (IOException e) {
            throw new RuntimeException("Failed to append content to file", e);
        }
    }

    public void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to flush file", e);
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close file", e);
        }
    }
}