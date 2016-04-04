package tokens;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
	private class TokenInfo {
		public final Pattern regex;
		public final int token;

		public TokenInfo(Pattern regex, int token) {
			super();
			this.regex = regex;
			this.token = token;
		}
	}

	public class Token {
		public int token;
		public final String sequence;

		public Token(int token, String sequence) {
			this.token = token;
			this.sequence = sequence;
		}

	}

	private LinkedList<TokenInfo> tokenInfos;
	private LinkedList<Token> tokens;

	public Tokenizer() {
		tokenInfos = new LinkedList<TokenInfo>();
		tokens = new LinkedList<Token>();
	}

	public void add(String regex, int token) {
		tokenInfos.add(new TokenInfo(Pattern.compile("^(" + regex + ")"), token));
	}

	public void addTk(String info, int code) {
		tokens.add(new Token(code, info));
	}

	public void SortTokens() {
		for (Token x : tokens)
			if (x.token == 88) // delimitators
				switch (x.sequence) {
				case ",":
					x.token = 16;
					break;
				case ";":
					x.token = 17;
					break;
				case "(":
					x.token = 18;
					break;
				case ")":
					x.token = 19;
					break;
				case "[":
					x.token = 20;
					break;
				case "]":
					x.token = 21;
					break;
				case "{":
					x.token = 22;
					break;
				case "}":
					x.token = 23;
					break;
				}
			else if (x.token == 89) // keywords/IDs
				switch (x.sequence) {
				case "break":
					x.token = 1;
					break;
				case "char":
					x.token = 2;
					break;
				case "double":
					x.token = 3;
					break;
				case "else":
					x.token = 4;
					break;
				case "for":
					x.token = 5;
					break;
				case "if":
					x.token = 6;
					break;
				case "int":
					x.token = 7;
					break;
				case "return":
					x.token = 8;
					break;
				case "struct":
					x.token = 9;
					break;
				case "void":
					x.token = 10;
					break;
				case "while":
					x.token = 11;
					break;
				default:
					x.token = 0;
					break;
				}
			else if (x.token == 17)
				switch (x.sequence) {
				case "+":
					x.token = 24;
					break;
				case "-":
					x.token = 25;
					break;
				case "*":
					x.token = 26;
					break;
				case ".":
					x.token = 27;
					break;
				case "/":
					x.token = 28;
					break;
				case "&&":
					x.token = 29;
					break;
				case "||":
					x.token = 30;
					break;
				case "!":
					x.token = 31;
					break;
				case "=":
					x.token = 32;
					break;
				case "==":
					x.token = 33;
					break;
				case "!=":
					x.token = 34;
					break;
				case "<":
					x.token = 35;
					break;
				case ">":
					x.token = 36;
					break;
				case "<=":
					x.token = 37;
					break;
				case ">=":
					x.token = 38;
					break;
				}

	}

	public LinkedList<Token> getTokens() {
		return tokens;
	}

}
