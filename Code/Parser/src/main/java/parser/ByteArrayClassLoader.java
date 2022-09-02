package parser;

/**
 * @author samin on 11/17/21
 * @project aardroid
 */

public class ByteArrayClassLoader extends ClassLoader {

    public Class findClass(String name, byte[] ba) {

        return defineClass(name,ba,0,ba.length);
    }

}