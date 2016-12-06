package com.waspring.wasindoor.locale.entry;

import java.util.Comparator;

public class IntIntDoubleComparator implements Comparator{


	public int compare(Object o1, Object o2) {
		IntIntDouble a = (IntIntDouble) o1;
		IntIntDouble b = (IntIntDouble) o2;
		if(a.getInt1()>b.getInt1())
		{
			return 1;
		}
		else if(a.getInt1()==b.getInt1())
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}

}
