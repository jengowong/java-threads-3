package reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * {@link Test}
 *
 * http://tutorials.jenkov.com/java-reflection/index.html
 */
public class Test {

    public static void main(String[] args) throws Exception {
        System.out.println("--------Java Reflection Tutorial--------");
        Class clazz = String.class;
        //Class clazz = Class.forName("java.lang.String");

        System.out.println("--------Java Reflection - Classes--------");
        System.out.println(String.class.getName());
        System.out.println(String.class.getSimpleName());
        System.out.println(String.class.getModifiers());
        System.out.println(Modifier.isFinal(String.class.getModifiers()));
        System.out.println(String.class.getPackage().getName());
        System.out.println(String.class.getPackage().getSpecificationVersion());
        System.out.println(String.class.getSuperclass().getName());
        for (Class i : String.class.getInterfaces()) {
            System.out.println(i.getName());
        }

        System.out.println("--------Java Reflection - Constructors--------");
        for (Constructor c : String.class.getConstructors()) {
            System.out.print(c + " -->");
            for (Class p : c.getParameterTypes()) {
                System.out.print(" " + p.getSimpleName());
            }
            System.out.println();
        }
        System.out.println(String.class.getConstructor(new Class[]{String.class}));
        System.out.println(String.class.getConstructor(new Class[]{String.class}).newInstance("constructor-arg1"));

        System.out.println("--------Java Reflection - Fields--------");
        for (Field f : String.class.getFields()) {
            System.out.println(f.getName() + " --> " + f.getType().getName());
        }
        Field field = String.class.getField("CASE_INSENSITIVE_ORDER");
        String strInstance = "test field";
        System.out.println(field.get(strInstance).getClass().getName());

        System.out.println("--------Java Reflection - Methods--------");
        for (Method m : String.class.getMethods()) {
            System.out.print(m + " --> ");
            for (Class p : m.getParameterTypes()) {
                System.out.print(" " + p.getSimpleName());
            }
            System.out.println();
        }
        Method method = String.class.getMethod("indexOf", String.class);
        System.out.println(method.invoke(strInstance, "ie"));

        System.out.println("--------Java Reflection - Getters and Setters--------");
        for (Method m : String.class.getMethods()) {
            if (isGetter(m)) {
                System.out.println("getter: " + m);
            }
            if (isSetter(m)) {
                System.out.println("setter: " + m);
            }
        }

        System.out.println("--------Java Reflection - Private Fields and Methods--------");
        PrivateObject privateObject = new PrivateObject("The Private Value");

        Field privateStringField = PrivateObject.class.getDeclaredField("privateString");
        privateStringField.setAccessible(true);
        System.out.println("fieldValue = " + privateStringField.get(privateObject));

        Method privateStringMethod = PrivateObject.class.getDeclaredMethod("getPrivateString", null);
        privateStringMethod.setAccessible(true);
        System.out.println("returnValue = " + privateStringMethod.invoke(privateObject, null));

        System.out.println("--------Java Reflection - Annotations--------");
        for (Annotation annotation : TheClass.class.getDeclaredAnnotations()) {
            if (annotation instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                System.out.println("name=" + myAnnotation.name() + " " + "value=" + myAnnotation.value());
            }
        }

        for (Annotation annotation : TheClass.class.getMethod("doSomething").getDeclaredAnnotations()) {
            if (annotation instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                System.out.println("name=" + myAnnotation.name() + " " + "value=" + myAnnotation.value());
            }
        }

        Method method1 = TheClass.class.getMethod("doSomethingElse", String.class);
        Class[] parameterTypes = method1.getParameterTypes();
        int i = 0;
        for (Annotation[] annotations : method1.getParameterAnnotations()) {
            Class parameterType = parameterTypes[i];
            for (Annotation annotation : annotations) {
                if (annotation instanceof MyAnnotation) {
                    MyAnnotation myAnnotation = (MyAnnotation) annotation;
                    System.out.println("param:" + parameterType.getName() + " name=" + myAnnotation.name() + " " + "value=" + myAnnotation.value());
                }
            }
        }

        for (Annotation annotation : TheClass.class.getField("myField").getDeclaredAnnotations()) {
            if (annotation instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                System.out.println("name=" + myAnnotation.name() + " " + "value=" + myAnnotation.value());
            }
        }

        System.out.println("--------Java Reflection - Generics--------");
        Type genericReturnType = MyClass.class.getMethod("getStringList").getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericReturnType;
            Type[] returnArgTypes = aType.getActualTypeArguments();
            for (Type returnArgType : returnArgTypes) {
                Class returnArgClass = (Class) returnArgType;
                System.out.println("typeArgClass = " + returnArgClass);
                System.out.println(String.class.equals(returnArgClass));
                System.out.println(Integer.class.equals(returnArgClass));
            }
        }

        Type[] genericParameterTypes = MyClass.class.getMethod("setStringList", List.class).getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            if (genericParameterType instanceof ParameterizedType) {
                ParameterizedType aType = (ParameterizedType) genericParameterType;
                Type[] parameterArgTypes = aType.getActualTypeArguments();
                for (Type parameterArgType : parameterArgTypes) {
                    Class parameterArgClass = (Class) parameterArgType;
                    System.out.println("parameterArgClass = " + parameterArgClass);
                }
            }
        }

        Type genericFieldType = MyClass.class.getDeclaredField("stringList").getGenericType();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldArgType : fieldArgTypes) {
                Class fieldArgClass = (Class) fieldArgType;
                System.out.println("fieldArgClass = " + fieldArgClass);
            }
        }

        System.out.println("--------Java Reflection - Arrays--------");
        int[] intArray = (int[]) Array.newInstance(int.class, 3);
        Array.set(intArray, 0, 123);
        Array.set(intArray, 1, 456);
        Array.set(intArray, 2, 789);
        System.out.println("intArray[0]=" + Array.get(intArray, 0));
        System.out.println("intArray[1]=" + Array.get(intArray, 1));
        System.out.println("intArray[2]=" + Array.get(intArray, 2));

        Class intArrayClazz1 = int[].class;
        System.out.println("intArrayClazz1=" + intArrayClazz1);
        Class intArrayClazz2 = Class.forName("[I");
        System.out.println("intArrayClazz2=" + intArrayClazz2);
        System.out.println(intArrayClazz1.getComponentType());

        System.out.println("--------Java Reflection - Dynamic Proxies--------");

        System.out.println("--------Java Reflection - Dynamic Class Loading and Reloading--------");
        Class aClass = Test.class.getClassLoader().loadClass("reflection.MainClass");
        System.out.println("aClass.getName() = " + aClass.getName());

        ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
        MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
        Class myObjectClass = classLoader.loadClass("reflection.MyObject");
        MyObjectInterface object1 = (MyObjectInterface) myObjectClass.newInstance();
        MyObjectSuperClass object2 = (MyObjectSuperClass) myObjectClass.newInstance();
        MyObject object3 = (MyObject) myObjectClass.newInstance();

        //create new class loader so classes can be reloaded.
        classLoader = new MyClassLoader(parentClassLoader);
        myObjectClass = classLoader.loadClass("reflection.MyObject");
        object1 = (MyObjectInterface) myObjectClass.newInstance();
        object2 = (MyObjectSuperClass) myObjectClass.newInstance();
        System.out.println("myObjectClass=" + myObjectClass.hashCode() + " object1=" + object1 + " object2=" + object2);

        System.out.println("--------Java Reflection - Modules--------");
    }

    public static boolean isGetter(Method m) {
        if (!m.getName().startsWith("get")) {
            return false;
        }
        if (m.getParameterTypes().length != 0) {
            return false;
        }
        if (void.class.equals(m.getReturnType())) {
            return false;
        }
        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }

}
