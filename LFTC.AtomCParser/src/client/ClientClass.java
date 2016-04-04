package client;

import java.util.LinkedList;

import sm.StateMachine;
import tokens.Tokenizer.Token;

public class ClientClass {

	public static void main(String args[])
	{
		StateMachine Lexer = new StateMachine("test.c");
		LinkedList<Token> x=Lexer.Tokenize();
		for(Token y :x)
			System.out.println(y.token + " " + y.sequence);
		
	}
}
