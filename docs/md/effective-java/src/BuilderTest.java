/**
 * @Author Vison
 * @Date 2022/11/23 21:54 星期三
 * 建造者模式
 * 2. 遇到多个构造器参数时要考虑使用构建器
 */
public class BuilderTest {
    private int age;

    private String name;

    private Double weight;

    private String type;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private BuilderTest(Builder builder) {
        age = builder.age;
        name = builder.name;
        weight = builder.weight;
        type = builder.type;
    }


    public static class Builder {
        private int age;

        private String name;

        private Double weight;

        private String type;

        public BuilderTest build() {
            return new BuilderTest(this);
        }

        public Builder age(int val) {
            age = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder weight(Double val) {
            weight = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
            return this;
        }

    }


}
