package com.deepak.multi_thread.thread;

import java.util.List;

public class CounterThread implements Runnable {
    private final List<String> lines;

    public CounterThread(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public void run() {
        while (true) {
            String line;
            synchronized (lines) {
                if (lines.isEmpty()) {
                    continue;
                }
                line = lines.remove(0);
            }
            if ("EOF".equals(line)) {
                synchronized (lines) {
                    lines.add("EOF"); // Put it back for other counters to see
                }
                break;
            }
            int wordCount = line.split("\\s+").length;
            System.out.println(Thread.currentThread().getName() + " counted " + wordCount + " words in line: " + line);
        }
    }
}
