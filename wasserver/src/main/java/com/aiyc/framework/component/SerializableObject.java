package com.aiyc.framework.component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableObject implements Serializable {

	public SerializableObject() {
	}

	public boolean SaveObject(Object obj) {
		if (obj == null)
			return true;
		Exception exception = null;
		if (Serializable.class.isInstance(obj))
			try {
				ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
				ObjectOutputStream objectoutputstream = new ObjectOutputStream(
						bytearrayoutputstream);
				objectoutputstream.writeObject(obj);
				objectoutputstream.close();
				data = bytearrayoutputstream.toByteArray();
				bytearrayoutputstream.close();
			} catch (Exception exception1) {
				exception = exception1;
			}
		else
			exception = new Exception((new StringBuilder()).append(
					obj.getClass().getName()).append(
					"  not implements java.io.Serializable").toString());
		if (exception != null)
			throw new RuntimeException("Serialize Error", exception);
		else
			return true;
	}

	public Object LoadObject() {
		return LoadObject(null);
	}

	public Object LoadObject(ClassLoader classloader) {
		if (data == null || data.length == 0)
			return null;
		Object obj = null;
		Object obj1 = null;
		Object obj2 = null;
		if (data != null)
			try {
				ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(
						data);
				ObjectInputStreamForContext objectinputstreamforcontext = new ObjectInputStreamForContext(
						bytearrayinputstream, classloader);
				obj = objectinputstreamforcontext.readObject();
				objectinputstreamforcontext.close();
				bytearrayinputstream.close();
			} catch (Exception exception) {
				throw new RuntimeException("LoadObject", exception);
			}
		return obj;
	}

	public static Object clone(Object obj) {
		if (obj == null) {
			return null;
		} else {
			SerializableObject serializableobject = new SerializableObject();
			serializableobject.SaveObject(obj);
			return serializableobject.LoadObject();
		}
	}

	public static byte[] toByteArray(Object obj) {
		SerializableObject serializableobject = new SerializableObject();
		if (obj != null)
			serializableobject.SaveObject(obj);
		return serializableobject.data;
	}

	public static Object toObject(byte abyte0[]) {
		SerializableObject serializableobject = new SerializableObject();
		serializableobject.data = abyte0;
		return serializableobject.LoadObject();
	}

	private static final long serialVersionUID = 1L;
	public byte data[];
}
