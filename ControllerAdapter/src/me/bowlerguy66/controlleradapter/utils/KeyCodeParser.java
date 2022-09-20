package me.bowlerguy66.controlleradapter.utils;

import java.awt.event.KeyEvent;

public class KeyCodeParser {

	public static String getStringFromKeyCode(int keyCode) {
		switch(keyCode) {
		case KeyEvent.VK_ESCAPE:
			return "Esc";
		case KeyEvent.VK_F1:
			return "F1";
		case KeyEvent.VK_F2:
			return "F2";
		case KeyEvent.VK_F3:
			return "F3";
		case KeyEvent.VK_F4:
			return "F4";
		case KeyEvent.VK_F5:
			return "F5";
		case KeyEvent.VK_F6:
			return "F6";
		case KeyEvent.VK_F7:
			return "F7";
		case KeyEvent.VK_F8:
			return "F8";
		case KeyEvent.VK_F9:
			return "F9";
		case KeyEvent.VK_F10:
			return "F10";
		case KeyEvent.VK_F11:
			return "F11";
		case KeyEvent.VK_F12:
			return "F12";
		case KeyEvent.VK_INSERT:
			return "Ins";
		case KeyEvent.VK_DELETE:
			return "Del";
		case KeyEvent.VK_DEAD_TILDE:
			return "~";
		case KeyEvent.VK_DEAD_GRAVE:
			return "`";
		case KeyEvent.VK_TAB:
			return "Tab";
		case KeyEvent.VK_BACK_SPACE:
			return "Backspace";
		case KeyEvent.VK_CAPS_LOCK:
			return "Caps Lock";
		case KeyEvent.VK_QUOTE:
			return "'";
		case KeyEvent.VK_ENTER:
			return "Enter";
		case KeyEvent.VK_SHIFT:
			return "Shift";
		case KeyEvent.VK_SPACE:
			return "Space";
		case KeyEvent.VK_CONTROL:
			return "Ctrl";
		case KeyEvent.VK_WINDOWS:
			return "Win";
		case KeyEvent.VK_ALT:
			return "Alt";
		case KeyEvent.VK_UP:
			return "^";
		case KeyEvent.VK_LEFT:
			return "<";
		case KeyEvent.VK_DOWN:
			return "V";
		case KeyEvent.VK_RIGHT:
			return ">";
		case KeyEvent.VK_EXCLAMATION_MARK:
			return "!";
		case KeyEvent.VK_AT:
			return "@";
		case KeyEvent.VK_NUMBER_SIGN:
			return "#";
		case KeyEvent.VK_DOLLAR:
			return "$";
		case KeyEvent.VK_AMPERSAND:
			return "&";
		case KeyEvent.VK_MULTIPLY:
			return "*";
		case KeyEvent.VK_LEFT_PARENTHESIS:
			return "(";
		case KeyEvent.VK_RIGHT_PARENTHESIS:
			return ")";
		case KeyEvent.VK_UNDERSCORE:
			return "_";
		case KeyEvent.VK_PLUS:
			return "+";
		case KeyEvent.VK_BRACELEFT:
			return "{";
		case KeyEvent.VK_BRACERIGHT:
			return "}";
		case KeyEvent.VK_COLON:
			return ":";
		case KeyEvent.VK_QUOTEDBL:
			return "\"";
		case KeyEvent.VK_LESS:
			return "<";
		case KeyEvent.VK_GREATER:
			return ">";
//		case KeyEvent.VK_:
//			return "";
		case CustomKeyCode.PERCENT:
			return "%";
		case CustomKeyCode.POWER:
			return "^";
		case CustomKeyCode.BAR:
			return "|";
		case CustomKeyCode.QUESTION_MARK:
			return "?";
		}
		return null;
	}
	
}
