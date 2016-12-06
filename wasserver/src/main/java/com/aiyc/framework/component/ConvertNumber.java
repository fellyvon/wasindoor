package com.aiyc.framework.component;

public class ConvertNumber extends ConvertObject {

	public ConvertNumber(Class class1, Object obj) {
		super(class1, obj);
	}

	public Object covert(Object obj) {
		Object obj1 = null;
		if (Number.class.isInstance(obj))
			obj1 = (Number) obj;
		else if (String.class.isInstance(obj))
			obj1 = new Double(Double.parseDouble((String) obj));
		return super.covert(obj1);
	}

	public Object doCoverit(Number number) {
		if (number != null)
			return new Double(number.doubleValue());
		else
			return null;
	}

	public Object doCoverit(Object obj) throws Exception {
		return doCoverit((Number) obj);
	}

	private static Double DEFALUT_NUMBRR = new Double(0.0D);

}
