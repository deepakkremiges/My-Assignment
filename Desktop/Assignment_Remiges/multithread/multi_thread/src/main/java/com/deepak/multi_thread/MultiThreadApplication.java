package com.deepak.multi_thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.deepak.multi_thread.thread.TextProcessor;

@SpringBootApplication
public class MultiThreadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiThreadApplication.class, args);

		TextProcessor textProcessor = new TextProcessor();
		String filePath = "/home/deepakk/Desktop/Assignment_Remiges/multithread/multi_thread/src/main/java/com/deepak/multi_thread/thread/input2.txt";
		Thread readerThread2 = new Thread(() -> textProcessor.processLines(filePath));
		Thread counterThread = new Thread(textProcessor::countWords);

		readerThread2.start();
		counterThread.start();
	}

}
