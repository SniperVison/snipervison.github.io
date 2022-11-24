/**
 * @Author Vison
 * @Date 2022/11/24 9:46 星期四
 * 3. 用私有构造器或者枚举类型强化Singleton属性
 */
public class SingtonTest {

    //使用公有静态final域成员
    public static final SingtonTest INSTANCE = new SingtonTest();
    private SingtonTest() {}


    // 使用静态工厂方法
    public static final SingtonTest INSTANCE = new SingtonTest();
    private SingtonTest() {}

    public static  SingtonTest getInstance(){
        return INSTANCE;
    }
}
