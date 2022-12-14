### 一、创建与销毁对象

#### 1、用静态工厂方法代替构造器

```java
public static final Boolean TRUE = new Boolean(true);
public static final Boolean FALSE = new Boolean(false);

public static Boolean valueOf(boolean b) {
    return (b ? TRUE : FALSE);
 }
```

- 拥有名称，方便使用，如：```BigInteger.probablePrime```
- 不必每次调用的时候都创建一个新对象，如：```Boolean.valueOf(boolean)```
- 可以返回原返回类型的任何子类型的对象
- 所返回的对象的类可以随着每次调用而发生变化，这取决于静态工厂方法的参数值

```java
public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
    Enum<?>[] universe = getUniverse(elementType);
    if (universe == null)
        throw new ClassCastException(elementType + " not an enum");
    if (universe.length <= 64)
        return new RegularEnumSet<>(elementType, universe);
    else
        return new JumboEnumSet<>(elementType, universe);
}
```

- 方法返回的对象所属的类，在编写包含该静态工厂方法的类时可以不存在，构成服务提供者框架，如：JDBC

#### 2. 遇到多个构造器参数时要考虑使用构建器

优点：如果类的构造器或者静态工厂中具有多个(>=4) 参数,此时使用Builder模式很不错。
缺点：创建对象的开始稍微明细，在注重性能的场景下存在问题

#### 3. 用私有构造器或者枚举类型强化Singleton属性

- 使用 私有构造器 + 公有静态final域成员，但反射时无效

```java
//1. 使用公有静态final域成员 (反射能够入侵代码)
public static final SingletonTest INSTANCE = new SingletonTest();
private SingletonTest() {}
```

- 使用私有构造器 + 静态工厂方法，但多线程创建时，无效

```java
// 2. 使用静态工厂方法
public static final SingletonTest INSTANCE = new SingletonTest();
public static SingletonTest getInstance() {return INSTANCE;}
private SingletonTest() {}
```

- 使用枚举类实现，单例的最佳实现

```java
public enum SingletonTest2 {CAR,HOUSE,PEOPLE;}
```

- 序列化的时候会破坏单例模式，需要加上```transient```关键字，同时添加readResolve()方法

```java
private Object readResolve() {return INSTANCE;}
```

#### 4.通过私有构造器强化不可实例化的能力

- 不能使用抽象类来强制该类不可实例化，因为此类可以被子类实例化，同时子类可以实例化
- 显式创建私有构造器，并抛出异常，加上注释，如工具类。

```java
public class Collections {
    // Suppresses default constructor, ensuring non-instantiability.
    private Collections() {}
}  
```

#### 5.优先使用依赖注入来引用资源

- 静态工具类和Singleton类不适用于需要引用底层资源的类 (无法适配引入多个不同的资源)
- 当创建一个实例时，将该资源或工厂传给构造器/静态工厂/构建器中(即依赖注入的方式，提供良好的灵活性和可测试性)

#### 6.避免创建不必要的对象

- 当创建的对象不可变时，如```String``` ,```正则表达式```, ```适配器/视图(如java.util.Map#keySet)```
- 避免无意识的装箱

#### 7. 消除过期的对象引用（避免导致内存泄漏）

- 集合和数组中不需要的对象记得置空（如果集合或者数组不会被销毁的情况下）
- 缓存，如WeakHashMap(当key不被其他对象引用时，会被GC), ```java.util.LinkedHashMap#removeEldestEntry```
- 监听器和其他回调，确保回调立即被当做垃圾回收的最佳方法是只保存它们的弱引用（weak reference）
- 借助Head Profiler工具排查内存泄漏问题

#### 8. 避免使用终结方法(finalizer)和清除方法(Cleaner)

- 行为不稳定，性能降低，以及可移植性问题
- 被执行的时机不确定
- finalizer抛出的异常可以被恶意错误记录，导致GC异常
- 实现AutoCloseable接口，调用close()方法，如```try-with-resources ``` 进行终止

#### 9. try -with-resources 优先于 try-finally

### 二、对于所有对象都通用的方法

#### 10. 重写equals方法时请遵守通用约定

- 自反性
- 对称性
- 传递性
- 一致性
- 对于任何非null的引用值x, x.equals(null) 必须返回false

#### 11. 重写equals方法时需要同时重写hashCode方法

#### 12. 始终要重写toString方法

#### 13. 谨慎成功重写clone方法

- 不可变的类永远都不应该提供clone方法
- clone方法实际上就是一个构造器，必须确保它不会伤害到原始的对象
- 公有的clone方法应该省略throws声明
- Cloneable架构与引用可变的final域的正常用法是不相兼容的
- 对于拷贝更好的办法是提供一个拷贝构造器或者拷贝工厂

```java
// Copy Constructor
public Yum(Yum yum) {...}
// Copy Factory
public static Yum newInstance(Yum yum){...}
```

#### 14.考虑实现Comparable接口

- 如果你编写明显具备内在排序关系的类(如 字母/数字/时间排序)，则应该实现Comparable接口
- 避免在compareTo方法中使用< 和 > 关系操作符，应该基本类型的包装类中使用静态的compare方法，或者在Comparator接口使用比较器构造方法

### 三、类和接口

#### 15. 使类和成员的可访问性最小化

- 尽可能使每个类或成员不被外界访问
- 公有类的实例域不能是公有的
- 除常量等公共静态final字段外，	公共类都不应该有公有字段，并且确保公有静态final字段所引用的对象是不可变的

#### 16. 要在公共类中使用访问方法而不是公共属性

- 属性私有化，提供getter和setter方法

#### 17. 使可变性最小化(不可变类)

不可变类是线程安全的，不要求哦同步，可以被自由地共享如String，基本类型的包装类，BigInteger和BigDecimal
1.不要提供任何会修改对象状态的方法(设值方法)
2.保证类不会被扩扩展(即final关键字修饰类)
3.声明的所有字段设置final
4.声明的所有字段设置private
5.确保对于任何可变组件的互斥访问

```
a.  非final修饰的字段不要从任何访问方法返回该对象引用(不提供getter方法)
b.  implement Serializable接口需要提供readObject或者readResolve方法，或者使用java.io.ObjectInputStream#readUnshared和java.io.ObjectOutputStream#writeUnshared方法
c.   如果类不能成为不可变类，则应尽量限制其可变性（如CountDownLatch类）
d.  构造器应该创建完全初始化的对象，并建立所有的约束关系
```

#### 18 组合优于继承
- 装饰器模式（使用属性注入然后调用的方式，不直接使用继承）
- 包装类不适用回调框架，被包装的对象并不知道它外面的包装对象，所以当它床底一个指向自身的引用（this），回调是避开了外面的包装对象，导致包装失效，引发SELF问题
- 继承打破封装性，当子类完全需要用到父类的属性和方法时，才适合使用继承，否则建议使用组合

#### 19 要么设计继承并提供文档说明，要么禁止继承
- 可重写方法（public ,protected）尽量少调用可重写方法（）比如AbstractList.addAll调用了add方法，当子类重写add方法，父类的addAll的逻辑也会发生变化
- 如果调用了可重写方法（即违法第一点）,则在注释中说明调用顺序以及方法（@implSpec注解）
- 对于为了继承而设计的类，唯一的测试方法就是编写子类（非父类作者外的开发，三个子类足矣）
- 构造器，clone方法，readObject方法，都不能直接或间接调用可被重写的方法

#### 20 接口优于抽象类
- 单继承（抽象类），多实现（接口）
- 对接口提供一个抽象的骨架实现类（模板方法模式），如AbstractList, AbstractSet
- 可以使用内部私有类扩展骨架实现类的方法，实现模拟多重继承

#### 21 为后代设计接口
- 避免向现有的接口添加新的缺省方法，java 8开始在接口编写缺省方法不会出现编译时报错或警告，运行时失败的情况，但方法在使用时，极易受影响，如：```java.util.Collection#removeIf```

```java
   default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }
```
-  发布之前，应使用不少于三种实现来测试接口

#### 22 接口只用于定义类型
- 常量接口模式是对接口的不良使用，非final类实现常量接口，子类的命名空间会被污染（JDK反面教材java.io.ObjectStreamConstants），如：
```java
// 常量接口
public interface Test{
  static final double PI =3.14;
}
```

- 接口应该只用来定义类型，而不是用来导出常量，用于其他地方的静态导入（static import）
- 导出常量的合理反感
  - 常量类
  - 使用枚举类型
  - 不可实例化的工具类（即私有构造方法）

#### 23 类层次优于标签类

- 不要在一个类中根据类型生成不同的类 ，比如一个类根据条件生成长方形类和正方形类

  应该使用接口或者抽象类，生成长方形子类和正方形子类

#### 24 静态成员类优于非静态成员类
嵌套类有四种：静态成员类，非静态成员类，匿名类，局部类
- 静态成员类：通常作为公有的辅助类 
  - 如果声明成员类不要求访问外层实例，则始终在声明加上static修饰符，节省额外保存外层实例引用所消耗的时间和空间
  
- 非静态成员类：定义一个适配器，如Map的keySet,entrySet,values等使用非静态成员类实现集合的视图
  - 非静态内部类可能会导致宿主类满足GC条件却无法被回收
- 匿名类：动态创建小型函数对象和过程对象。如lambda表达式；另一种用法是在静态工厂方法的内部
- 局部类：使用在“可以声明局部变量”的地方

#### 25限制源文件为单个顶级类
- 一个java文件一个类或接口（内部类不限）

### 四、泛型
#### 26 请不要使用原生态类型

- 使用泛型，如使用List<E> 而不是List

#### 27 消除非检查警告
- 不使用泛型时(如List)，会出现警告，使用@SuppressWarnings(“unchecked”)可以抑制警告，但是最好注解在变量上（最细粒度）以及注释说明为什么无法修改成泛型

#### 28 列表优于数组

- 使用List<E> 优于Object[]
  - 集合有更好的类型安全性
  - 数组是协变且可以具体化的；泛型是不可变的且可以被擦除的。
  - 数组提供运行时的类型安全；泛型提供了编译时的类型安全，相比之下集合更容易发现错误与警告

#### 29 优先考虑泛型

- 为了提升性能，有些泛型是使用数组实现的，如ArrayList, HashMap
- 优先使用泛型而不是Object类型，可以在编译期间避免类型转换错误，也减少了强转类型的代码，更加安全和易于操作
```java
   // ArrayList 声明的Object数组
   transient Object[] elementData
   // 获取元素的方法，也进行强转类型
   @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }
```

#### 30 优先考虑泛型方法

#### 31 使用限定通配符来增加API的灵活性

- Collection <? extend E>
- Collection <? super E>

