package com.expensetracker.junit5.tests;

import com.expensetracker.junit5.pilot.SampleTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class PilotTest {

  SampleTest sampleTest;

  @BeforeEach
  void init(){
    sampleTest = new SampleTest();
  }

  @Test
  void sum() {
     var actual = sampleTest.sum(3 ,3);
     var expected = 7;
     assertEquals(expected, actual);
  }
}