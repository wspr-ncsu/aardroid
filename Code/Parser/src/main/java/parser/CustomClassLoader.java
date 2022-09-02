package parser;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author samin on 11/18/21
 * @project aardroid
 */
public class CustomClassLoader {

    public void loadClasses(String pathToJar) throws IOException {

        JarFile jarFile = new JarFile(pathToJar);
        Enumeration<JarEntry> e = jarFile.entries();
        String libdir = "file://"+ System.getProperty("user.dir")+ "temp/unzip/libs/";

        URL[] urls = { new URL("jar:file:" + pathToJar+"!/"), new URL(libdir) };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            try {
                Class c = cl.loadClass(className);
                System.out.println(c.getName());
                Constructor[] constructors = c.getConstructors();
                Method[] m = c.getDeclaredMethods();

                for (Method mm :
                        m) {

                    List<String> parameterNames = getParameterNames(mm);
                    System.out.println("Yes");
                }

                for (Constructor cc :
                        constructors) {


                    Parameter[] parameters = cc.getParameters();
                    System.out.println("Yes");
                }
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            catch(Throwable t){
                t.printStackTrace();
            }

        }

    }

    public List<String> getParameterNames(Method method) {
        Parameter[] parameters = method.getParameters();
        List<String> parameterNames = new ArrayList<>();

        for (Parameter parameter : parameters) {
            if(!parameter.isNamePresent()) {
                throw new IllegalArgumentException("Parameter names are not present!");
            }

            String parameterName = parameter.getName();
            parameterNames.add(parameterName);
        }

        return parameterNames;
    }




}
