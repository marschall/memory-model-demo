package com.github.marschall.memorymodeldemo;

import org.junit.jupiter.api.Test;

class SynchronisedSumTest {

  @Test
  void test() {
    SynchronisedSum sum = new SynchronisedSum(512);
    sum.generate();
    sum.calculate(130816);
  }

}
