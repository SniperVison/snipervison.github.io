/**
 * @Author Vison
 * @Date 2022/11/24 9:46 星期四
 * 3. 用私有构造器或者枚举类型强化Singleton属性
 */
public class SingtonTest {

    //1. 使用公有静态final域成员 (反射能够入侵代码)
    public static final SingtonTest INSTANCE = new SingtonTest();
    private SingtonTest() {}


    // 2. 使用静态工厂方法
    public static SingtonTest getInstance() {return INSTANCE;}

    // 保证序列化时，维护单例，需要加上transient关键字和readResolve()方法
    private Object readResolve() {
        return INSTANCE;
    }

    // 3. 使用枚举类，就是实现单例的最佳办法，无偿地提供序列化机制，防止多次实例化，反射也无效

    // 普通方法
    public void doSomething() {

    }

    public enum SingtonTest2 {CAR,HOUSE,PEOPLE;}
}
