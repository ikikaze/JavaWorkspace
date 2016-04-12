package client;

import sm.Asin;
import tokens.ParserException;

public class ClientClass {

	public static void main(String args[])
	{
//		StateMachine Lexer = new StateMachine("test.c");
//		LinkedList<Token> x=Lexer.Tokenize();
//		for(Token y :x)
//			System.out.println(y.token + " " + y.sequence);
		
		Asin syn=new Asin("test.c");
		try {System.out.println(syn.consumeAll()); }
		catch(ParserException e)
		{e.printStackTrace();}
		
		
	}
}
