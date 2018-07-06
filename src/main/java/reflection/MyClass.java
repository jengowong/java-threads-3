package reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link MyClass}
 */
public class MyClass {

    protected List<String> stringList = new ArrayList<String>(Arrays.asList("1", "2", "3"));

    public List<String> getStringList() {
        return this.stringList;
    }

    public void setStringList(List<String> list) {
        this.stringList = list;
    }

}
