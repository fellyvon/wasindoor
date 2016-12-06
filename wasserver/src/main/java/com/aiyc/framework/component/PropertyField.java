package com.aiyc.framework.component;


public class PropertyField
{

    public PropertyField()
    {
    }

    public PropertyFieldDescriptor getDesc()
    {
        return desc;
    }

    public void setInstance(Object obj)
    {
        instance = obj;
    }

    public void setDesc(PropertyFieldDescriptor propertyfielddescriptor)
    {
        desc = propertyfielddescriptor;
    }

    public Object getInstance()
    {
        return instance;
    }

    public Object get()
    {
        return desc.get(instance);
    }

    public void set(Object obj)
    {
        desc.set(instance, obj);
    }

    private Object instance;
    private PropertyFieldDescriptor desc;
}
