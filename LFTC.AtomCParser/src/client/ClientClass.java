package client;

import enums.CLS;
import enums.MEM;
import enums.TypeBase;
import sm.Asin;
import symbols.FunSymbol;
import symbols.StructSymbol;
import symbols.Symbol;
import symbols.Type;
import tokens.ParserException;

@SuppressWarnings("unused")
public class ClientClass {

	public static void main(String args[]) {
		// StateMachine Lexer = new StateMachine("test.c");
		// LinkedList<Token> x=Lexer.Tokenize();
		// for(Token y :x)
		// System.out.println(y.token + " " + y.sequence);

		Asin syn = new Asin("test.c");
		try {
			System.out.println(syn.consumeAll());
			for (Symbol s : syn.Symbols) {
				System.out.println();
				if (s.getCls() == CLS.CLS_STRUCT) {
					System.out.print(s.getName() + " -struct members - > ");
					((StructSymbol) s).getMembers().forEach(item -> System.out.print(item.getName() + " "));
					System.out.println();
				}
				if (s.getCls() == CLS.CLS_FUNC) {
					System.out.print(s.getName() + " -Function args - > ");
					((FunSymbol) s).getArgs().forEach(item -> System.out.print(item.getName() + " "));
					System.out.println();
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}

		// {
		// Symbol a = null, b = null, c = null,d=null;
		// Type x = new Type(TypeBase.TB_INT, -1, null);
		// Type y = new Type(TypeBase.TB_CHAR, -1, null);
		// Type z = new Type(TypeBase.TB_DOUBLE, -1, null);
		// a = new Symbol("s1", CLS.CLS_VAR, MEM.MEM_GLOBAL, x, 0);
		// b = new Symbol("s2", CLS.CLS_VAR, MEM.MEM_GLOBAL, y, 0);
		// d=new Symbol("s1", CLS.CLS_VAR, MEM.MEM_GLOBAL, x, 0);
		// c = new Symbol("s3", CLS.CLS_VAR, MEM.MEM_GLOBAL, z, 0);
		// syn.AddSymbol(a);
		// syn.AddSymbol(b);
		// syn.AddSymbol(d);
		// syn.AddSymbol(c);
		// for (Symbol s : syn.Symbols) {
		// System.out.println(s.getName());
		// }
		// syn.deleteSymbolsAfter("s1", TypeBase.TB_INT, CLS.CLS_VAR);
		// System.out.println("\n\n");
		// for (Symbol s : syn.Symbols) {
		// System.out.println(s.getName());
		// }
		// }

	}
}
