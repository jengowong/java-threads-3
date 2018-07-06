package reflection;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * {@link MyClassLoader }
 */
public class MyClassLoader extends ClassLoader {

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        if (!"reflection.MyObject".equals(name)) {
            return super.loadClass(name);
        }
        try {
            String url = "file:/Users/jengowang/Applications/java-threads-3/target/classes/reflection/MyObject.class";
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();
            while (data != -1) {
                buffer.write(data);
                data = input.read();
            }
            input.close();
            byte[] classData = buffer.toByteArray();
            return super.defineClass("reflection.MyObject", classData, 0, classData.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
