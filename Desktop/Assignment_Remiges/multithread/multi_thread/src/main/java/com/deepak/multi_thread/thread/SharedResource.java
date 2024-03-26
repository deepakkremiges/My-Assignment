package com.deepak.multi_thread.thread;

import java.util.ArrayList;
import java.util.List;

public class SharedResource {
    // Assuming lines is the only shared resource between threads
    public final List<String> lines = new ArrayList<>();
}
