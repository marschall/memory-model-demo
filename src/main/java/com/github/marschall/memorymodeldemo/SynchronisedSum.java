package com.github.marschall.memorymodeldemo;

import java.util.concurrent.atomic.AtomicReference;

public final class SynchronisedSum {

  private final AtomicReference<int[]> shared;
  private final int samples;

  SynchronisedSum(int samples) {
    this.shared = new AtomicReference<>();
    this.samples = samples;
  }

  void generate() {
    // do work on data this thread owns
    int[] data = new int[this.samples];
    for (int i = 0; i < this.samples; i++) {
      data[i] = i;
    }

    // publish to other threads
    this.shared.setRelease(data);
  }

  void calculate(int expectedSum) {
    while (true) {
      // check if the work has been published yet
      int[] data = this.shared.getAcquire();
      if (data != null) {
        // the data is now accessible by multiple threads
        int sum = 0;
        for (int i = this.samples - 1; i >= 0; i--) {
          sum += data[i];
        }

        // did we access the data we expected?
        assertEquals(sum, expectedSum);
        break;
      }
    }
  }

  private static void assertEquals(int expected, int actual) {
    if (expected != actual) {
      throw new AssertionError("expected: " + expected + " but was: " + actual);
    }
  }

  public static void main(String[] args) {
    System.out.println("running on " + System.getProperty("os.arch"));
    for (int i = 0; i < 10_000; i++) {
      SynchronisedSum sum = new SynchronisedSum(512);

      Thread calculateThread = new Thread(() -> sum.calculate(130816), "calculate-thread");
      calculateThread.start();

      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        System.out.println("interrupted");
        return;
      }

      Thread generateThread = new Thread(() -> sum.generate(), "generate-thread");
      generateThread.start();

      try {
        calculateThread.join();
        generateThread.join();
      } catch (InterruptedException e) {
        System.out.println("interrupted");
        return;
      }
    }
    System.out.println("all iterations passed");
  }

}
