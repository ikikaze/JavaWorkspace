package client;

import enums.CLS;
import enums.MEM;
import enums.TypeBase;
import sm.Asin;
import symbols.Symbol;
import symbols.Type;
import tokens.ParserException;

public class ClientClass {

	public static void main(String args[]) {
		// StateMachine Lexer = new StateMachine("test.c");
		// LinkedList<Token> x=Lexer.Tokenize();
		// for(Token y :x)
		// System.out.println(y.token + " " + y.sequence);

		Asin syn = new Asin("test.c");
		try {
			System.out.println(syn.consumeAll());
		} catch (ParserException e) {
			e.printStackTrace();
		}
//		{
//			Symbol a = null, b = null, c = null;
//			Type x = new Type(TypeBase.TB_INT, -1, null);
//			Type y = new Type(TypeBase.TB_CHAR, -1, null);
//			Type z = new Type(TypeBase.TB_DOUBLE, -1, null);
//			a = new Symbol("s1", CLS.CLS_VAR, MEM.MEM_GLOBAL, x, 0);
//			b = new Symbol("s2", CLS.CLS_VAR, MEM.MEM_GLOBAL, y, 0);
//			c = new Symbol("s3", CLS.CLS_VAR, MEM.MEM_GLOBAL, z, 0);
//			syn.AddSymbol(a);
//			syn.AddSymbol(b);
//			syn.AddSymbol(c);
//			for (Symbol s : syn.Symbols) {
//				System.out.println(s.getName());
//			}
//			syn.deleteSymbolsAfter("s1", TypeBase.TB_INT, CLS.CLS_VAR);
//			System.out.println("\n\n");
//			for (Symbol s : syn.Symbols) {
//				System.out.println(s.getName());
//			}
//		}

	}
}
