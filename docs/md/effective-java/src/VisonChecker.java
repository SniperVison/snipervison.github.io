import java.util.Objects;

/**
 * @Author Vison
 * @Date 2022/11/24 21:21 星期四
 * 5.优先使用依赖注入来引用资源(不能使用静态工具类和Singleton类)
 */
public class VisonChecker {
    private final VisonDictionary dictionary;

    public  VisonChecker(VisonDictionary dictionary){
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    // do something
    public  boolean isValid(String word){return true;}

    class VisonDictionary {
    }


}
