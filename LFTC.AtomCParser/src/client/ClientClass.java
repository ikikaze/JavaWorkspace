package client;

import java.util.LinkedList;

import parser.AtomCParser;
import tokens.Tokenizer.Token;

public class ClientClass {

	public static void main(String args[])
	{
		AtomCParser.Initialize();
		LinkedList<Token> x=AtomCParser.Tokenize("test.c");
		for(Token y :x)
			System.out.println(y.token + " " + y.sequence);
	}
}
