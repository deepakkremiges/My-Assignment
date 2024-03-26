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

		SharedResource sharedResource = new SharedResource();
		ReaderThread readerThread = new ReaderThread(
				"/home/deepakk/Desktop/Assignment_Remiges/multithread/multi_thread/src/main/java/com/deepak/multi_thread/thread/input.txt",
				sharedResource);
		CounterThread counterThread = new CounterThread(sharedResource);

		readerThread.start();
		counterThread.start();
	}

}

// home/deepakk/Desktop/Assignment_Remiges/multithread/multi_thread/src/main/java/com/deepak/multi_thread/thread/input.txt