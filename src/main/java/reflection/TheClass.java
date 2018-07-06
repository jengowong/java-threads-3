package reflection;

/**
 * {@link TheClass}
 */
@MyAnnotation(name = "cName", value = "cValue")
public class TheClass {

    @MyAnnotation(name = "fName", value = "fValue")
    public String myField = null;

    @MyAnnotation(name = "mName", value = "mValue")
    public void doSomething() {
    }

    public void doSomethingElse(@MyAnnotation(name = "pName", value = "pValue") String parameter) {
    }

}
