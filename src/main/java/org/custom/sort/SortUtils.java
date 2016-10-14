package org.custom.sort;

import java.util.Arrays;

public class SortUtils {

    /**
     * 冒泡排序
     * @param a 待排序数组
     */
    public static void bubbleSort(int[] a) {
        for (int i = 0; i < a.length -1; i++) {
            for (int j = 0; j < a.length - i -1; j++) {
                if (a[j] > a[j+1]) {
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }
    }
    /**
     * 冒泡排序
     * @param a 待排序数组
     */
    public static void bubbleSort_1(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    int k = a[j];
                    a[j] = a[i];
                    a[i] = k;
                }
            }
        }
    }
    
    /**
     * 冒泡排序
     * @param a 待排序数组
     */
    public static void bubbleSort_2(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = a.length - 1; j > i; j--) {
                if (a[j] < a[j - 1]) {
                    int k = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = k;
                }
            }
        }
    }
    /**
     * 冒泡排序(与排序2无本质区别，只是如上一次没有交换，就不进行排序操作)
     * @param a 待排序数组
     */
    public static void bubbleSort_3(int[] a) {
        boolean flag = true;
        for (int i = 0; i < a.length && flag; i++) {
            flag = false;
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    int k = a[j];
                    a[j] = a[i];
                    a[i] = k;
                    flag = true;
                }
            }
        }
    }

    
    /**
     * 希尔排序算法
     * 先取一个小于n的整数d1作为第一个增量，
     * 把文件的全部记录分成d1个组。
     * 所有距离为dl的倍数的记录放在同一个组中。
     * 先在各组内进行直接插人排序；
     * 然后，取第二个增量d2<d1重复上述的分组和排序，直至所取的增量dt=1(dt<dt-l<…<d2<d1)，
     * 即所有记录放在同一组中进行直接插入排序为止。该方法实质上是一种分组插入方法。
     *
     * @param array
     * @param len
     */
    public static <T extends Comparable<? super T>> void shellSort(T[] array, int len) {
        int d = len;
        while (d > 1) {
            d = (d + 1) / 2;
            for (int i = 0; i < len - d; i++) {
                if (array[i + d].compareTo(array[i]) < 0) {
                    T temp = array[i + d];
                    array[i + d] = array[i];
                    array[i] = temp;
                }
            }
        }
    }

    /**
     * 插入排序
     * @param array
     * @param length
     */
    public static void insertSort(int[] array, int length) {
        for(int i = 0;i < length;i++){
            int k = i;
            int j;
            for(j = k + 1;j < length;j++){
                if(array[j] < array[k]){
                    k = j;
                }
            }
            if(k != i){
                int temp = array[i];
                array[i] = array[k];
                array[k] = temp;
            }
        }
    }

    
    /**
     * 排序数字字符串
     * 每次比较出字符串中最大的数字
     * @param user_ids
     */
    public static String selectMaxSort(String user_ids) {
        String[] _ids = user_ids.split(",");
        for (int i = 0; i < _ids.length; i++) {
            int largest = i;
            for (int j = i; j < _ids.length; j++) {
                if (Integer.parseInt(_ids[j]) > Integer.parseInt(_ids[largest]))
                    largest = j;
            }
            int temp;
            temp = Integer.parseInt(_ids[i]);
            _ids[i] = _ids[largest];
            _ids[largest] = temp + "";
        }
        return Arrays.toString(_ids).replaceAll("\\[|\\]", "");
    }
}
