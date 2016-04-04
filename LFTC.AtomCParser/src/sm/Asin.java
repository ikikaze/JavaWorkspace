package sm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import tokens.Tokenizer.Token;

public class Asin
	{
		StateMachine x;
		LinkedList<Token> Tokens;
		Iterator<Token> it;
		public Asin(String fileName)
		{
			x=new StateMachine(fileName);
			Tokens=x.Tokenize();
			it=Tokens.iterator();
			
		}
	}
