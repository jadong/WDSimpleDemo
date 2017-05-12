package test;

/**
 * Created by zengwendong on 2017/5/11.
 */
public class SortUtil {

    private static int[] arr = {32, 25, 88, 45, 15, 97, 12, 67, 58, 49};

    public static void bubbleSort() {
        int count = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                count++;
            }

        }
        for (int k = 0; k < arr.length; k++) {
            System.out.print("--" + arr[k]);
        }

        System.out.println("");
        System.out.println("执行次数:" + count);

    }

    public static int getMiddle(int[] numbers, int low,int high)
    {
        int temp = numbers[low]; //数组的第一个作为中轴
        while(low < high)
        {
            while(low < high && numbers[high] > temp)
            {
                high--;
            }
            numbers[low] = numbers[high];//比中轴小的记录移到低端
            while(low < high && numbers[low] < temp)
            {
                low++;
            }
            numbers[high] = numbers[low] ; //比中轴大的记录移到高端
        }
        numbers[low] = temp ; //中轴记录到尾
        return low ; // 返回中轴的位置
    }

}
