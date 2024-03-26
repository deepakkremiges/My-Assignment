package com.deepak.multi_thread.thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReaderThread implements Runnable {
    private final String filePath;
    private final List<String> lines;

    public ReaderThread(String filePath, List<String> lines) {
        this.filePath = filePath;
        this.lines = lines;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                synchronized (lines) {
                    lines.add(line);
                }
            }
            synchronized (lines) {
                lines.add("EOF"); // Indicates the end of file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
