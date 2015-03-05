package hr.foi.cookie.core;

import java.util.List;

public class ListHelper {
	public static String implode(List<?> list)
	{
		String imploded = "";
		for (Object item  : list)
		{
			imploded += item.toString() + ",";
		}
		
		return imploded.substring(0, imploded.length() - 1);
	}
}
