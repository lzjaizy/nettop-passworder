package com.nettop.utils;

public class SortUtil {

  //冒泡排序
  public static int[] bubbleSort(int[] arr){
    int flag = 0;
    int num = 0;

    for (int i = arr.length; i > 0; i--){
      num++;
      flag = 0;
      for (int j = 0; j < i - 1; j++){

        if (arr[j] > arr[j + 1]){
          flag = 1;
          int temp = arr[j + 1];
          arr[j + 1] = arr[j];
          arr[j] = temp;

        }

      }

      if (flag == 0){
        break;
      }
    }
    System.out.println(num);
    return arr;
  }

  //冒泡排序
  public static int[] selectSort(int[] arr){

    int min = 0;

    for (int i = 0; i < arr.length; i++){

      min = i;
      for (int j = i + 1; j < arr.length; j++){
          if (arr[i] < arr[j]){

          }
      }
    }


    return arr;
  }

}
