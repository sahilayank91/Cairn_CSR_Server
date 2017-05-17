package com.cairnindia.csr.utilities;

public class Text {
	public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
        	e.printStackTrace();
            return null;
        }
        return out;
    }
}
