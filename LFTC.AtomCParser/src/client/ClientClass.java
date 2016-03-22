package client;

import java.util.LinkedList;

import parser.AtomCParser;
import sm.StateMachine;
import tokens.Tokenizer.Token;

public class ClientClass {

	public static void main(String args[])
	{
		
		LinkedList<Token> x=StateMachine.statemachine("test.c");
		for(Token y :x)
			System.out.println(y.token + " " + y.sequence);
		
	}
}
