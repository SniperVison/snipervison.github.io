/**
 * @Author Vison
 * @Date 2022/11/24 21:05 星期四
 * 4. 通过私有构造器强化不可实例化的能力
 * 工具类不需要实例化
 */
public class VisonUtil {

    // 用于限制该工具类不可实例化
    private VisonUtil(){
        throw new AssertionError();
    }
}
