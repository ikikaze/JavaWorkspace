package sm;

import java.util.LinkedList;

import tokens.ParserException;
import tokens.Tokenizer.Token;

public class Asin {
	private StateMachine x;
	private LinkedList<Token> Tokens;
	public int index;

	public Asin(String fileName) {
		x = new StateMachine(fileName);
		Tokens = x.Tokenize();
		index = 0;
	}

	private boolean consume(int code) {
		if (Tokens.get(index).token == code) {
			index++;
			return true;
		}
		return false;
	}

	private boolean consumePrimary() {
		if (!consume(0)) {
		} else {
			consumeprimfrag();
			return true;
		}
		if (consume(12)) { //ct_int
			return true;
		}
		if (consume(13)) { //real
			return true;
		}
		if (consume(14)) {//char
			return true;
		}
		if (consume(15)) { //string
			return true;
		}
		if (consume(18))  //lpar
			if (consumeexpr()) //expr
				if (consume(19)) //rpar
					return true;

		return false;
	}

	private boolean consumeexpr() {
		return consume(0);
		
	}

	private boolean consumeprimfrag() {

		if (!consume(18))
			return false;
		consume2ndfrag();
		if (!consume(19))
			return false;

		return true;
	}

	private boolean consume2ndfrag() {
		consumeexpr();
		while (true) {
			if (!consume(16))
				break;
			if (!consumeexpr())
				break;
		}
		return true;

	}

	public boolean consumeAll() {
		int startindex=0;
		int ok1=1,ok2=1;
		while (Tokens.get(index).token != 100 && index<Tokens.size()) {
			startindex = index;
			ok1=1;
			ok2=1;
			if (!consumeAdd())
			{ok1=0;
				index = startindex;
			}
			if (!consumePrimary()&& ok1==0)
			{
				index = startindex;
				ok1=0;}
			if(Tokens.get(index).token !=100)
			
			if(ok1==0)
				return false;//throw new ParserException("BAD character : " + Tokens.get(0).sequence);
		}
		return true;
	}

	// add = mul add' SI add' = (ADD|SUB) exprmul expradd' | Eps
	private boolean consumeAdd() {
		int startindex=index;
		if (consumeMul())
		{	
			if(consumeAdd1())
				return true;
				
		}
		return false;
	}
	
	private boolean consumeAddSub()
	{
		if(consume(24))
			return true;
		if(consume(25))
			return true;
		return false;
	}

	
	private boolean consumeAdd1()
	{int startindex=index;
		if(consumeAddSub())
		{
			if(consumeMul())
				if(consumeAdd1())
					return true;
					
		}
		
		return true;
	}

	private boolean consumeMul() {
		return consume(0);
	}

}
