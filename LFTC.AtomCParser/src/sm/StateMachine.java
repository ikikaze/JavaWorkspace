package sm;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import tokens.ParserException;
import tokens.Tokenizer;
import tokens.Tokenizer.Token;

public class StateMachine {
	public static Tokenizer x = new Tokenizer();

	public static LinkedList<Token> statemachine(String str) {
		int state = 0;
		String tokenstring = "";
		File file = new File(str);
		try {
			str = FileUtils.readFileToString(file);
		} catch (IOException e) {

			e.printStackTrace();
		}
		str = str + "\n !";
		str=str.trim();
		while (str != "!") {
			String tmp = "";

			tmp = "" + str.charAt(0);

			switch (state) {
			case 0:
				if (Pattern.matches("[1-9]", tmp)) {
					state = 1;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("0", tmp)) {
					state = 2;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (tmp.equals("/")) {
					state = 8;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[a-zA-Z\\_]", tmp)) {
					state = 12;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[']", tmp)) {
					state = 20;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("\"", tmp)) {
					state = 24;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("\\\\/", tmp)) {
					state = 26;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[,:;(){}\\[\\]]", tmp)) {
					state = 31;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[!=<>]", tmp)&&str.length()!=1) {
					state = 32;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[\\\\+-\\\\*\\\\/\\\\.]", tmp)) {
					state = 33;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[\\\\|\\\\&]", tmp)) {
					state = 34;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 90;

				break;
			case 1:
				if (Pattern.matches("[0-9]", tmp)) {
					state = 1;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (tmp.equals(".")) {
					state = 14;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[Ee]", tmp) && !tokenstring.contains("x")
						&& !(tokenstring.startsWith("0") && tokenstring.length() > 1)) {
					state = 17;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 7;
				break;
			case 2:
				if (Pattern.matches("x", tmp)) {
					state = 3;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[0-7]", tmp)) {
					state = 6;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (tmp.equals(".") && !tokenstring.contains("x")
						&& !(tokenstring.startsWith("0") && tokenstring.length() > 1)) {
					state = 14;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[Ee]", tmp) && !tokenstring.contains("x")
						&& !(tokenstring.startsWith("0") && tokenstring.length() > 1)) {
					state = 17;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state=7;
				break;
			case 3:
				if (Pattern.matches("[0-9a-fA-F]", tmp)) {
					state = 5;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					str = "!";
				break;
			case 5:
				if (Pattern.matches("[0-9a-fA-F]", tmp)) {
					state = 5;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 7;
				break;

			case 6:
				if (Pattern.matches("[0-7]", tmp)) {
					state = 6;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 7;
				break;
			case 7: // CT_INT return state;
				x.addTk(tokenstring, 12);
				state = 0;
				tokenstring = "";
				str = str.trim();
				break;
			case 8:
				if (tmp.equals("*")) {
					state = 9;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (tmp.equals("/")) {
					state = 27;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state=33;
				break;
			case 9:
				if (Pattern.matches("[^*]", tmp)) {
					state = 9;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (tmp.equals("*")) {
					state = 10;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					str = "!";
				break;
			case 10:
				if (tmp.equals("*")) {
					state = 10;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[^*/]", tmp)) {
					state = 9;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (tmp.equals("/")) {
					state = 11;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					str = "!";
				break;
			case 11: // COMMENT return state;
				x.addTk("COMMENT", -1);
				state = 0;
				tokenstring = "";
				str = str.trim();
				break;
			case 12:
				if (Pattern.matches("[A-Za-z\\_]", tmp)) {
					state = 12;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 13;
				break;
			case 13:
				x.addTk(tokenstring, 89);
				state = 0;
				tokenstring = "";
				str = str.trim();
				break;
			case 14:
				if (Pattern.matches("[0-9]", tmp)) {
					state = 15;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 90;
				break;
			case 15:
				if (Pattern.matches("[0-9]", tmp)) {
					state = 15;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[Ee]", tmp)) {
					state = 17;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 16;
				break;
			case 16: // CT_REAL return state;
				x.addTk(tokenstring, 13);
				state = 0;
				tokenstring = "";
				str = str.trim();
				break;
			case 17:
				if (Pattern.matches("[+-]", tmp)) {
					state = 18;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[0-9]", tmp)) {
					state = 19;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 90;

				break;
			case 18:
				if (Pattern.matches("[0-9]", tmp)) {
					state = 19;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 90;

				break;
			case 19:
				if (Pattern.matches("[0-9]", tmp)) {
					state = 19;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 16;

				break;
			case 20:
				if (Pattern.matches("\\\\", tmp)) {
					state = 21;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("[^'\\\\]", tmp)) {
					state = 22;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 90;

				break;
			case 21:
				if (Pattern.matches("[abfnrtv'?\"\\\\]", tmp)) {
					state = 22;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
					if (!(str.charAt(0) == '\''))
						state = 24;
				} else
					state = 90;
				break;
			case 22:
				if (Pattern.matches("[']", tmp)) {
					state = 23;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 90;
				break;
			case 23: // CT_CHAR RETURN STATE
				x.addTk(tokenstring, 14);
				state = 0;
				tokenstring = "";
				str = str.trim();
				break;
			case 24:
				if (Pattern.matches("[^\"\\\\]", tmp)) {
					state = 24;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("\\\\", tmp)) {
					state = 21;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else if (Pattern.matches("\"", tmp)) {
					state = 25;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 90;
				break;
			case 25:
				x.addTk(tokenstring, 15);
				state = 0;
				tokenstring = "";
				str = str.trim();
				break;
			case 26:
				if (Pattern.matches("\\\\/", tmp)) {
					state = 27;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 33;

				break;
			case 27:
				if (Pattern.matches("[\n\r\0]", tmp)) {
					state = 30;
				} else {
					state = 27;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				}
				break;
			case 30:
				// COMMENT return state;
				x.addTk("COMMENT", -1);
				state = 0;
				tokenstring = "";
				str = str.trim();
				break;
			case 31:
				// DELIM return state
				x.addTk(tokenstring, 88);
				state = 0;
				tokenstring = "";
				str = str.trim();
				break;
			case 32:
				if (Pattern.matches("=", tmp)) {
					state = 33;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 33;
			case 33:  //Operator return state
				x.addTk(tokenstring, 17);
				state = 0;
				tokenstring = "";
				str = str.trim();
				break;
			case 34:
				if (tmp.equals(tokenstring)) {
					state = 33;
					tokenstring = tokenstring.concat(tmp);
					str = str.substring(1);
				} else
					state = 33;
				break;
			case 90: // Invalid char state or file end state
				if (str.equals("!")) {
					str = "!";
					x.addTk("END", 100);
				} else
					throw new ParserException("Error at " + str);
				break;

			}

		}
		x.SortTokens();
		return x.getTokens();
	}
}
