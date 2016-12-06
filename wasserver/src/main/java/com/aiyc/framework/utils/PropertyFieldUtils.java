package com.aiyc.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import com.aiyc.framework.component.PropertyField;
import com.aiyc.framework.component.PropertyFieldDescriptor;
 
 


public class PropertyFieldUtils
{

    public PropertyFieldUtils(Class class1)
    {
        objclass = class1;
        classprops = (HashMap)propCache.get(class1);
        if(classprops == null)
        {
            classprops = getClassPropertys(class1);
            propCache.put(class1, classprops);
        }
    }

    public PropertyFieldUtils(Object obj)
    {
        this(obj.getClass());
        objInstance = obj;
    }

    public void setInstance(Object obj)
    {
        objInstance = obj;
    }

    private PropertyField getPropertyFieldDefine(String s)
        throws Exception
    {
        PropertyFieldDescriptor propertyfielddescriptor = (PropertyFieldDescriptor)classprops.get(s);
        Object obj = getInstance();
        if(propertyfielddescriptor == null)
        {
            String as[] = s.split("\\.");
            if(as.length > 1)
            {
                for(int i = 0; i < as.length - 1; i++)
                {
                    obj = getProperty(obj, as[i]);
                    if(obj == null)
                        return null;
                }

                PropertyFieldUtils propertyfieldutils = new PropertyFieldUtils(obj);
                propertyfielddescriptor = (PropertyFieldDescriptor)propertyfieldutils.classprops.get(as[as.length - 1]);
            }
        }
        if(propertyfielddescriptor == null)
        {
            return null;
        } else
        {
            PropertyField propertyfield = new PropertyField();
            propertyfield.setDesc(propertyfielddescriptor);
            propertyfield.setInstance(obj);
            return propertyfield;
        }
    }

    public void setPropertyField(String s, Object obj)
        throws Exception
    {
        PropertyField propertyfield = getPropertyFieldDefine(s);
        if(propertyfield == null)
        {
            throw new Exception("010109"+objclass.getName()+ s);
        } else
        {
            propertyfield.set(obj);
            return;
        }
    }

    public HashMap getClassPropMap()
    {
        return classprops;
    }

    public Object getInstance()
    {
        return objInstance;
    }

    public PropertyFieldDescriptor[] getFieldDescriptor()
    {
        PropertyFieldDescriptor apropertyfielddescriptor[] = new PropertyFieldDescriptor[classprops.size()];
        apropertyfielddescriptor = (PropertyFieldDescriptor[])(PropertyFieldDescriptor[])classprops.values().toArray(apropertyfielddescriptor);
        return apropertyfielddescriptor;
    }

    public PropertyFieldDescriptor getFieldDescriptor(String s)
        throws Exception
    {
        PropertyFieldDescriptor propertyfielddescriptor = (PropertyFieldDescriptor)classprops.get(s);
        if(propertyfielddescriptor == null)
            throw new Exception("010108"+objclass.getName()+s);
        else
            return propertyfielddescriptor;
    }

    public Object getPropertyField(String s)
        throws Exception
    {
        PropertyField propertyfield = getPropertyFieldDefine(s);
        if(propertyfield == null)
            throw new Exception("010108"+ objclass.getName()+s);
        else
            return propertyfield.get();
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        PropertyFieldDescriptor apropertyfielddescriptor[] = getFieldDescriptor();
        for(int i = 0; i < apropertyfielddescriptor.length; i++)
        {
            stringbuffer.append(apropertyfielddescriptor[i].getPropertyName());
            stringbuffer.append("=\"");
            stringbuffer.append(apropertyfielddescriptor[i].get(objInstance));
            stringbuffer.append("\";");
        }

        return stringbuffer.toString();
    }

    private HashMap getClassPropertys(Class class1)
    {
        Method amethod[] = class1.getMethods();
        Field afield[] = class1.getFields();
        HashMap hashmap = new HashMap();
        Object obj = null;
        Object obj1 = null;
        for(int i = 0; i < amethod.length; i++)
        {
            Method method3 = amethod[i];
            if(method3.getParameterTypes().length != 0 || !Modifier.isPublic(method3.getModifiers()))
                continue;
            String s = null;
            if(method3.getName().startsWith("get"))
                s = method3.getName().replaceFirst("get", "set");
            else
            if(method3.getName().startsWith("is"))
                s = method3.getName().replaceFirst("is", "set");
            if(s == null)
                continue;
            Method method = method3;
            Method method1 = null;
            String s2 = propertyName(s.substring(3));
            for(int k = 0; k < amethod.length; k++)
            {
                Method method4 = amethod[k];
                if(Modifier.isPublic(method4.getModifiers()) && method4.getName().equals(s))
                    method1 = method4;
            }

            if(method1 != null)
            {
                hashmap.put(s2, new PropertyFieldDescriptor(s2, method, method1));
                continue;
            }
            boolean flag = false;
            int l = 0;
            do
            {
                if(l >= afield.length)
                    break;
                Field field1 = afield[l];
                if(Modifier.isPublic(field1.getModifiers()) && field1.getName().equals(s2))
                {
                    flag = true;
                    hashmap.put(s2, new PropertyFieldDescriptor(method, field1));
                    break;
                }
                l++;
            } while(true);
            if(!flag)
                hashmap.put(s2, new PropertyFieldDescriptor(s2, method, method1));
        }

        for(int j = 0; j < afield.length; j++)
        {
            Field field = afield[j];
            String s3 = field.getName();
            String s1 = (new StringBuilder()).append("set").append(capitalize(s3)).toString();
            if(!Modifier.isPublic(field.getModifiers()) || hashmap.get(s3) != null)
                continue;
            Method method2 = null;
            for(int i1 = 0; i1 < amethod.length; i1++)
            {
                Method method5 = amethod[i1];
                if(Modifier.isPublic(method5.getModifiers()) && method5.getName().equals(s1))
                    method2 = method5;
                if(method2 != null)
                    hashmap.put(s3, new PropertyFieldDescriptor(field, method2));
                else
                    hashmap.put(s3, new PropertyFieldDescriptor(field));
            }

        }

        return hashmap;
    }

    public static void setProperty(Object obj, String s, Object obj1)
        throws Exception
    {
        PropertyFieldUtils propertyfieldutils = new PropertyFieldUtils(obj);
        propertyfieldutils.setPropertyField(s, obj1);
    }

    public static Object getProperty(Object obj, String s)
        throws Exception
    {
        if(obj == null)
        {
            return null;
        } else
        {
            PropertyFieldUtils propertyfieldutils = new PropertyFieldUtils(obj);
            return propertyfieldutils.getPropertyField(s);
        }
    }

    public static String objectToString(Object obj)
    {
        PropertyFieldUtils propertyfieldutils = new PropertyFieldUtils(obj);
        return propertyfieldutils.toString();
    }

    public static String capitalize(String s)
    {
        if(s.length() == 0)
        {
            return s;
        } else
        {
            char ac[] = s.toCharArray();
            ac[0] = Character.toUpperCase(ac[0]);
            return new String(ac);
        }
    }

    public static String propertyName(String s)
    {
        if(s.length() == 0)
        {
            return s;
        } else
        {
            char ac[] = s.toCharArray();
            ac[0] = Character.toLowerCase(ac[0]);
            return new String(ac);
        }
    }

    public static Object coverTo(Object obj, Class class1)
    {
        return ConvertUtil.CovertObject(class1, obj);
    }

    public static Object assign(Object obj, Object obj1)
    {
        PropertyFieldUtils propertyfieldutils = new PropertyFieldUtils(obj);
        PropertyFieldUtils propertyfieldutils1 = new PropertyFieldUtils(obj1);
        PropertyFieldDescriptor apropertyfielddescriptor[] = propertyfieldutils.getFieldDescriptor();
        for(int i = 0; i < apropertyfielddescriptor.length; i++)
        {
            PropertyFieldDescriptor propertyfielddescriptor = (PropertyFieldDescriptor)propertyfieldutils1.getClassPropMap().get(apropertyfielddescriptor[i].getPropertyName());
            if(propertyfielddescriptor != null)
                propertyfielddescriptor.set(obj1, apropertyfielddescriptor[i].get(obj));
        }

        return obj1;
    }

    private HashMap classprops;
    private Class objclass;
    private Object objInstance;
    private static HashMap propCache = new HashMap();

}