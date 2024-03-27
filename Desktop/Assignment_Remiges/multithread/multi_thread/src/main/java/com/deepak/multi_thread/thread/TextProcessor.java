package com.deepak.multi_thread.thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TextProcessor {
    private List<String> lines = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(TextProcessor.class.getName());

    public void processLines(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                synchronized (lines) {
                    lines.add(line);
                    lines.notify(); // Notify the waiting counterThread
                }
                System.out.println("Read line: " + line);
                logger.info("Reading line from the text file");
            }
        } catch (IOException e) {

            logger.info("Filename not found");

            e.printStackTrace();
        }
    }

    public void countWords() {
        while (true) {
            synchronized (lines) {
                while (lines.isEmpty()) {
                    try {
                        lines.wait(); // Wait for new lines to be added
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String line = lines.remove(0); // Remove the first line from the list
                String[] words = line.split("\\s+"); // Split the line into words

                logger.info("counting the no. of words : successfully");

                System.out.println("Number of words in line: " + words.length);
            }
        }
    }
}
