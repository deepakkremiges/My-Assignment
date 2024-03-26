package com.deepak.multi_thread.thread;

public class CounterThread extends Thread {
    private final SharedResource sharedResource;

    public CounterThread(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (sharedResource.lines) {
                    if (sharedResource.lines.isEmpty()) {
                        sharedResource.lines.wait(); // Wait for lines to be added
                    } else {
                        String line = sharedResource.lines.remove(0);
                        int wordCount = line.split("\\s+").length;
                        System.out.println("Words counted: " + wordCount + " in line: " + line);
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
