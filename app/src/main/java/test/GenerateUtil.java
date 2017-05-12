package test;

import java.math.BigDecimal;

/**
 * Created by zengwendong on 2017/5/9.
 */
public class GenerateUtil {

    private static int[] arr = {0,1,2,3,4,5,6};

    public static void generate() {
        //从n个元素中取m个元素排列组合,共有多少种排列
        // num = n!/(n-m)!
        BigDecimal f = factorial(10 - 3);

        if (f.equals(BigDecimal.ZERO)) {
            f = BigDecimal.ONE;
        }
        BigDecimal num = factorial(10).divide(f);
        System.out.println("排列总数:" + num);

    }

    /**
     * 阶乘
     */
    private static BigDecimal factorial(int n) {
        BigDecimal result = new BigDecimal(1);
        BigDecimal a;
        for (int i = 2; i <= n; i++) {
            a = new BigDecimal(i);
            result = result.multiply(a);
        }
        return result;
    }


}
