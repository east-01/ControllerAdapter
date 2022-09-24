package me.bowlerguy66.controlleradapter.utils;

public class Debug {

	public static boolean showlog = false;
	
	public static void log(String s) {
		if(showlog) System.out.println(s);
	}
	
}
