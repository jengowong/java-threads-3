package algorithm.compare;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * {@link Comparable}和{@link Comparator}的比较程序。
 * <pre/>
 *
 * 1.{@link Comparable}
 * 若一个类实现了{@link Comparable}接口，就意味着“该类本身支持排序”。
 *
 * 假设现在存在“实现Comparable接口的类的对象的List列表(或数组)”，
 * 则该List列表(或数组)可以通过 Collections.sort(或 Arrays.sort)进行排序。
 *
 * 此外，
 * “实现Comparable接口的类的对象”可以用作“有序映射(如TreeMap)”中的键，
 * 或“有序集合(TreeSet)”中的元素，而不需要指定比较器。
 *
 * Comparable接口只包含一个函数 compareTo(T t)，
 * 说明：
 * 假设我们通过 x.compareTo(y) 来“比较x和y的大小”，
 * 返回“负数”，意味着“x比y小”；
 * 返回“零”，意味着“x等于y”；
 * 返回“正数”，意味着“x大于y”。
 *
 *
 * 2.{@link Comparator}
 * 若一个类实现了{@link Comparator}接口，那么它就是一个“比较器”。
 *
 * 如果需要控制某个类的次序，
 * 而该类本身不支持排序(即没有实现Comparable接口)，
 * 那么我们可以建立一个“该类的比较器”来进行排序。
 * 这个“比较器”只需要实现Comparator接口即可。
 * 也就是说，我们可以通过“实现Comparator类来新建一个比较器”，然后通过该比较器对类进行排序。
 *
 * Comparator接口包括两个函数 compare(T o1, T o2) 和 equals(Object obj)。
 * 说明：
 * (1)若一个类要实现Comparator接口：
 * 它一定要实现 compare(T o1, T o2) 函数，但可以不实现 equals(Object obj) 函数。
 * 为什么可以不实现 equals(Object obj) 函数呢？
 * 因为任何类默认都是已经实现了 equals(Object obj) 的。
 * Java中的一切类都是继承于java.lang.Object，
 * 在Object.java中实现了equals(Object obj)函数；
 * 所以，其它所有的类也相当于都实现了该函数。
 *
 * (2)int compare(T o1, T o2) 是“比较o1和o2的大小”。
 * 返回“负数”，意味着“o1比o2小”；
 * 返回“零”，意味着“o1等于o2”；
 * 返回“正数”，意味着“o1大于o2”。
 *
 * 综上所述：
 * {@link Comparable}是内部比较器，
 * {@link Comparator}是外部比较器。
 * 一个类本身实现了{@link Comparable}比较器，就意味着它本身支持排序；
 * 若它本身没实现{@link Comparable}，也可以通过外部比较器{@link Comparator}进行排序。
 */
@Slf4j
public class DiffComparableComparator {

    public static void main(String[] args) {
        List<Person> list = Lists.newArrayList();
        list.add(new Person("ccc", 20));
        list.add(new Person("AAA", 30));
        list.add(new Person("bbb", 10));
        list.add(new Person("ddd", 40));

        log.info("Original  sort, list:{}", list);

        //根据“Person实现的Comparable<String>接口”进行排序，即会根据“name”进行排序
        Collections.sort(list);
        log.info("Name      sort, list:{}", list);

        //通过“比较器(AscAgeComparator)”，根据“age”的升序排序
        Collections.sort(list, new AscAgeComparator());
        log.info("Asc(age)  sort, list:{}", list);

        //通过“比较器(DescAgeComparator)”，根据“age”的降序排序
        Collections.sort(list, new DescAgeComparator());
        log.info("Desc(age) sort, list:{}", list);

        //判断两个person是否相等
        testEquals();
    }

    /**
     * 测试两个Person比较是否相等。
     *
     * 由于Person实现了equals()函数：
     * 若两person的age、name都相等，则认为这两个person相等。
     *
     * 所以，这里的p1和p2相等。
     *
     * 若去掉Person中的equals()函数，则p1不等于p2
     */
    private static void testEquals() {
        Person p1 = new Person("eee", 100);
        Person p2 = new Person("eee", 100);
        if (p1.equals(p2)) {
            System.out.printf("%s EQUAL %s\n", p1, p2);
        } else {
            System.out.printf("%s NOT EQUAL %s\n", p1, p2);
        }
    }


    /**
     * Person.age的升序比较器
     */
    private static class AscAgeComparator implements Comparator<Person> {
        public int compare(Person p1, Person p2) {
            return p1.getAge() - p2.getAge();
        }
    }

    /**
     * Person.age的降序比较器
     */
    private static class DescAgeComparator implements Comparator<Person> {
        public int compare(Person p1, Person p2) {
            return p2.getAge() - p1.getAge();
        }
    }

}
