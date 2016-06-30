package com.mapbar.carlimit.util;

import java.util.Hashtable;
import java.util.ResourceBundle;

public class CommonParameter {

	public static Hashtable Ht_TypeID = new Hashtable();
	public static Hashtable Ht_TemplateID = new Hashtable();

	public static int NearbyRange = 0;

	static {

		try {
			ResourceBundle rb = ResourceBundle.getBundle("wirelessInfo");
			NearbyRange = Integer.parseInt(rb.getString("nearbyRange"));

			String strID = rb.getString("IDInfo");
			String[] tmpID = strID.split(",");

			for (int i = 0; i < tmpID.length; i += 3) {
				if ((i + 2) < tmpID.length) {
					String typeID = tmpID[i].trim();
					String templateID = tmpID[i + 1].trim();
					String typePage = tmpID[i + 2].trim();
					if (typeID.length() > 0 && typePage.length() > 0) {
						Ht_TypeID.put(typeID, typePage);
					}
					if (templateID.length() > 0 && typePage.length() > 0) {
						Ht_TemplateID.put(typePage, templateID);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getTypePage(String typeID) {

		if (typeID == null || typeID.trim().length() < 1) {
			return "";
		}
		if (Ht_TypeID.containsKey(typeID)) {
			return (String) Ht_TypeID.get(typeID);
		} else {
			return "";
		}
	}

	public static String getTemplateID(String Url) {

		if (Url == null || Url.trim().length() < 1) {
			return "";
		}
		String pageName = Url;
		if (pageName.lastIndexOf("/") > 0) {
			pageName = pageName.substring(pageName.lastIndexOf("/") + 1);
		}
		if (pageName.indexOf("?") > 0) {
			pageName = pageName.substring(0, pageName.indexOf("?"));
		}

		if (Ht_TemplateID.containsKey(pageName)) {
			return (String) Ht_TemplateID.get(pageName);
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
		System.out.println(CommonParameter.getTemplateID("http://192.168.0.111/position/?&tp=13&key=[MMLC]/IB80E7rnZgB/SnRn8vj7yJn/qTq0EGbBYWj/qm80E7gBEk&ch=gbk"));
		System.out.println(CommonParameter.getTypePage("2"));
	}

}
