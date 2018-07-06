package collections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * {@link Test}
 */
public class Test {

    public static void main(String[] args) {

        System.out.println("--------Java Collections - Streams--------");
        List<String> items = new ArrayList<String>();
        items.add("one");
        items.add("two");
        items.add("three");
        Stream<String> stream = items.stream();
    }
}
