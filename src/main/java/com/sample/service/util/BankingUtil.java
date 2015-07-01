package com.sample.service.util;

import java.util.Collection;

public class BankingUtil {
	
	public static String getArrayString(Collection<?> list) {
		StringBuilder sb = new StringBuilder();
		if (list != null) {
			for (Object obj : list) {
				sb.append("[" + obj.toString() + "];");
			}
		}
		return sb.toString();
	}
	
}
