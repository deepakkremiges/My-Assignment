package com.deepak.multi_thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.deepak.multi_thread.thread.CounterThread;
import com.deepak.multi_thread.thread.ReaderThread;
import com.deepak.multi_thread.thread.SharedResource;

@SpringBootApplication
public class MultiThreadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiThreadApplication.class, args);

		// Added by Deepak for Thread part

		SharedResource sharedResource = new SharedResource();

		// Create and start the reader thread
		Thread readerThread = new Thread(new ReaderThread(
				"/home/deepakk/Desktop/Assignment_Remiges/multithread/multi_thread/src/main/java/com/deepak/multi_thread/thread/input.txt",
				sharedResource.lines));
		readerThread.start();

		// Create and start the counter thread
		int numberOfCounterThreads = 2; // You can adjust this based on your requirements
		for (int i = 0; i < numberOfCounterThreads; i++) {
			Thread counterThread = new Thread(new CounterThread(sharedResource.lines));
			counterThread.start();
		}

		// Wait for reader thread to finish
		try {
			readerThread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// Thread part over
	}

}

// home/deepakk/Desktop/Assignment_Remiges/multithread/multi_thread/src/main/java/com/deepak/multi_thread/thread/input.txt