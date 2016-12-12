package algorithm.compare;

import com.sun.istack.internal.NotNull;

/**
 * {@link Person}实现了{@link Comparable}接口，
 * 这意味着{@link Person}本身支持排序
 */
public class Person implements Comparable<Person> {

    private int age;
    private String name;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

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

    @Override
    public String toString() {
        return name + "-" + age;
    }

    /**
     * 比较两个Person是否相等：若它们的name和age都相等，则认为它们相等。
     *
     * @param person {@link Person}
     *
     * @return true / false
     */
    boolean equals(Person person) {
        return this.age == person.age && this.name.equals(person.name);
    }

    /**
     * 重写{@link Comparable#compareTo(Object)}函数。
     * 这里通过{@link Person#name}进行比较。
     *
     * @param person {@link Person}
     *
     * @return int
     */
    public int compareTo(@NotNull Person person) {
        return name.compareTo(person.name);
    }

}
