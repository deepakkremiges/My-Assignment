package com.deepak.multi_thread.thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReaderThread extends Thread {
    private final String filePath;
    private final SharedResource sharedResource;

    // constructor
    public ReaderThread(String filePath, SharedResource sharedResource) {
        this.filePath = filePath;
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                synchronized (sharedResource.lines) {
                    sharedResource.lines.add(line);
                    sharedResource.lines.notify(); // Notify counter thread
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
