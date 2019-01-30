package com.nettop.demo;

import com.nettop.utils.SortUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SortTest {

  int[] arr = {10,9,8,7,6,5,4,3,2,1,0};
  //    int[] arr = {0,1,2,3,4,5,6,7,8,10,9};

  @Test
  public void sortTest() {

    int[] newArr = SortUtil.bubbleSort(arr);
    for (int a:newArr){
      System.out.print(a + ",");
    }

  }
}
