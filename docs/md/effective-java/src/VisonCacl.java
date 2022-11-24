/**
 * @Author Vison
 * @Date 2022/11/24 21:25 星期四
 * 6.避免创建不必要的对象
 */
public class VisonCacl {

    String str = new String("ss"); // wrong implement
    String s1 = "ss"; // correct implement


    public static long sum1() {
        long sum = 0L;
        for (int j = 0; j < Integer.MAX_VALUE; j++) {
            sum += j;
        }
        return sum;
    }

    public static Long sum2() {
        Long sum = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        long s1 = System.currentTimeMillis();
        long l1 = sum1();
        System.out.println(l1 + "----- " + (System.currentTimeMillis() - s1) + "ms");


        long s2 = System.currentTimeMillis();
        Long l2 = sum2();
        System.out.println(l2 + "----- " + (System.currentTimeMillis() - s2) + "ms");
    }

}
