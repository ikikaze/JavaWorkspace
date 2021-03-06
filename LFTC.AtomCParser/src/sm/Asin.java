package sm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import enums.CLS;
import enums.MEM;
import enums.TypeBase;
import symbols.FunSymbol;
import symbols.StructSymbol;
import symbols.Symbol;
import symbols.Type;
import tokens.ParserException;
import tokens.Tokenizer.Token;

@SuppressWarnings("unused")
public class Asin {

	private StateMachine x;
	private LinkedList<Token> Tokens;
	public int index;
	public ArrayList<Symbol> Symbols;
	private int depth = 0;
	private FunSymbol crtFunc = null;
	private StructSymbol crtStruct = null;
	private List<Symbol> crtArgs = new ArrayList<Symbol>();

	public Asin(String fileName) {
		x = new StateMachine(fileName);
		Tokens = x.Tokenize();
		index = 0;
		for (int i = 0; i < Tokens.size(); i++) {
			Token a = Tokens.get(i);
			if (a.token == -1) {
				Tokens.remove(i);
				i--;
			}
			Symbols = new ArrayList<Symbol>();

		}
	}

	public void AddSymbol(Symbol s) {
		Symbols.add(s);
	}

	private Symbol findSymbol(String name) {
		Collections.reverse(Symbols);
		for (Symbol s : Symbols)
			if (s.getName().equals(name)) {
				Collections.reverse(Symbols);
				return s;
			}
		return null;
	}

	public void deleteSymbolsAfter(String name, TypeBase type, CLS cls) {
		Symbol s;
		int index;
		if ((s = findSymbol(name)) != null && s.getCls() == cls && s.getType().getType() == type)
			index = Symbols.indexOf(s);

		else
			return;
		ListIterator<Symbol> it = Symbols.listIterator(index);
		if (it.hasNext()) {
			it.next();
			while (it.hasNext()) {
				it.next();
				it.remove();
			}

		}
	}

	private boolean consume(int code) {
		int a;
		if ((a = Tokens.get(index).token) == code) {
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
		if (consume(12)) { // ct_int
			return true;
		}
		if (consume(13)) { // real
			return true;
		}
		if (consume(14)) {// char
			return true;
		}
		if (consume(15)) { // string
			return true;
		}
		if (consume(18)) // lpar
			if (consumeexpr()) // expr
				if (consume(19)) // rpar
					return true;

		return false;
	}

	// expr: exprAssign ;
	// exprAssign: exprUnary ASSIGN exprAssign | exprOr ;
	private boolean consumeexpr() {
		int startindex = index;
		if (consumeUnary())
			if (consume(32))
				if (consumeexpr())
					return true;

		index = startindex;
		if (consumeOr())
			return true;
		index = startindex;
		return false;

	}

	// �exprOr ::= exprAnd exprOr1� si �exprOr1 ::= OR exprAnd exprOr1 |
	// eps�
	private boolean consumeOr() {
		int startindex = index;
		if (consumeAnd())
			if (consumeOr1())
				return true;

		index = startindex;
		return false;
	}

	private boolean consumeOr1() {
		int startindex = index;

		if (consume(30))
			if (consumeAnd())
				if (consumeOr1())
					return true;
		index = startindex;
		return true;
	}

	// and = exprEQ exprAND' ---- and' = AND exprEQ exprAND | Eps
	private boolean consumeAnd() {
		int startindex = index;
		if (consumeEq())
			if (consumeAnd1())
				return true;
		index = startindex;
		return false;
	}

	private boolean consumeAnd1() {
		int startindex = index;
		if (consume(29))
			if (consumeEq())
				if (consumeAnd1())
					return true;
		index = startindex;
		return true;
	}

	// eq = Rel EQ' -----> EQ' = (EQ | NEQ) Rel EQ' | Eps
	private boolean consumeEq() {
		if (consumeRel())
			if (consumeEq1())
				return true;
		return false;
	}

	private boolean consumeEq1() {
		int startindex = index;
		int ok = 0;
		if (consume(33))
			ok = 1;
		if (ok == 0 && consume(34))
			ok = 1;
		if (ok == 1)
			if (consumeRel())
				if (consumeEq1())
					return true;

		index = startindex;
		return true;
	}

	// rel = Add Rel' ----> Rel' = (<|>|<=|>=)Add Rel' | Eps
	private boolean consumeRel() {
		int startindex = index;
		if (consumeAdd())
			if (consumeRel1())
				return true;
		index = startindex;
		return false;
	}

	private boolean consumeRel1() {
		int startindex = index;
		int ok = 0;
		if (consume(35))
			ok = 1;
		if (ok == 0 && consume(36))
			ok = 1;
		if (ok == 0 && consume(37))
			ok = 1;
		if (ok == 0 && consume(38))
			ok = 1;
		if (ok == 1)
			if (consumeAdd())
				if (consumeRel1())
					return true;
		index = startindex;
		return true;
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
		int startindex = 0;
		int ok1 = 0;
		while (index < Tokens.size() && Tokens.get(index).token != 100) {
			startindex = index;
			ok1 = 0;

			if (consumeUnit()) {
				System.out.println("Unitconsumed");
				ok1 = 1;
			}

			if (ok1 == 0)
				return false;// throw new ParserException("BAD
								// character : " +
								// Tokens.get(0).sequence);
		}
		return true;
	}

	// add op mul
	// add = mul add' ;--SI add' = (ADD|SUB) exprmul expradd' | Eps
	private boolean consumeAdd() {
		if (consumeMul()) {
			if (consumeAdd1()) {
				return true;
			}

		}
		return false;
	}

	private boolean consumeAddSub() {
		if (consume(24))
			return true;
		if (consume(25))
			return true;
		return false;
	}

	private boolean consumeAdd1() {
		int startindex = index;
		if (consumeAddSub()) {
			if (consumeMul())
				if (consumeAdd1())
					return true;

		}
		index = startindex;
		return true;
	}

	// mul = cast mul' ; SI mul' = (MUL|DIV) cast mul' | eps

	private boolean consumeMul() {
		int startindex = index;
		if (consumeCast()) {
			if (consumeMul1())
				return true;
		}
		index = startindex;
		return false;
	}

	private boolean consumeMul1() {
		int startindex = index;
		int ok = 0;
		if (consume(26))
			ok = 1;
		if (ok == 0)
			if (consume(28))
				ok = 1;
		if (ok == 1)

			if (consumeCast())
				if (consumeMul1())
					return true;

		index = startindex;
		return true;

	}

	private boolean consumeCast() {
		int startindex = index;
		if (consume(18)) {
			if (consumeTName()!=null)
				if (consume(19))
					if (consumeCast())
						return true;
		} else {
			index = startindex;
			if (consumeUnary())
				return true;
		}

		return false;
	}

	private boolean consumeUnary() {
		int startindex = index;
		int ok = 0;
		if (consume(25)) // SUB
			ok = 1;
		else {
			index = startindex;
			if (consume(31)) // NOT
				ok = 1;
		}
		if (ok == 1) {
			if (consumeUnary()) {
				System.out.println("UNARY Consumed");
				return true;
			}
		}
		if (consumePostFix()) // NO SUB OR NOT WAS CONSUMED
		{
			System.out.println("UNARY Consumed");
			return true;
		}

		return false;
	}

	// Postfix = exprPostfix LBRACKET expr RBRACKET
	// | exprPostfix DOT ID
	// | exprPrimary ;
	// Postfix = exprPrimary pf' ;
	private boolean consumePostFix() {
		if (consumePrimary())
			if (consumePF1())
				return true;

		return true;

	}

	// PF' = LBRACKET expr RBRACKET pf' | DOT ID pf';
	private boolean consumePF1() {
		int startindex = index;
		int ok = 0;
		if (consume(20)) {
			ok = 1;
			if (consumeexpr())
				if (consume(21))
					if (consumePF1())
						return true;
		}
		if (ok == 1) {
			index = startindex;
			if (consume(27))
				if (consume(0))
					if (consumePF1())
						return true;
		}

		return false;
	}

	// typeName: typeBase arrayDecl? ;
	private Type consumeTName() {
		Type t;
		if (( t =consumeTBase()) != null) {
			int a=consumeArrayDecl();
			t.setNElem(a);
			return t;
		}
		return null;
	}

	// arrayDecl: LBRACKET expr? RBRACKET ;
	private int consumeArrayDecl() {
		int startindex = index;
		if (consume(20)) {
			consumeexpr();
			if (consume(21))
				return 0; // not computing array size atm.
		}
		index = startindex;
		return -20; // TODO add the array implementation

	}

	// typeBase: INT | DOUBLE | CHAR | STRUCT ID ;
	private Type consumeTBase() {

		if (consume(7)) // int
			return new Type(TypeBase.TB_INT, 0);
		if (consume(3)) // double
			return new Type(TypeBase.TB_DOUBLE, 0);
		if (consume(2)) // char
			return new Type(TypeBase.TB_CHAR, 0);
		if (consume(9)) // struct
			if (consume(0)) // id
			{
				int i = index - 1;
				String name = Tokens.get(i).sequence;
				if (findSymbol(name) == null)
					throw new ParserException("struct " + name + " is undefined");
				if (findSymbol(name).getCls() != CLS.CLS_STRUCT)
					throw new ParserException("symbol not a struct ->" + name);
				Type t = new Type(TypeBase.TB_STRUCT, 0);
				t.addSymbol(findSymbol(name));
				return t;
			}
		return null;
	}

	// funcArg: typeBase ID arrayDecl? ;
	private Symbol consumeFuncArg() {
		int startindex = index;
		String name;
		Symbol s=new Symbol();
		Type type;
		if ((type=consumeTBase() )!= null)
			if (consume(0)) {
				name=Tokens.get(index-1).sequence;
				int arr=consumeArrayDecl();
				s.addInfo(MEM.MEM_ARG);
				s.addInfo(CLS.CLS_VAR);
				type.setNElem(arr);
				s.addInfo(type);
				s.addInfo(name);				
				return s;
			}
		index = startindex;
		return null;

	}

	private boolean consumeVar() {
		Symbol symbol = new Symbol();
		Type base;
		int arr;
		String name;
		int nElem = -1;
		int startindex = index;
		if ((base = consumeTBase()) != null) {
			if (consume(0)) {
				name = Tokens.get(index - 1).sequence;
				arr = consumeArrayDecl();
				base.setNElem(arr);
				addVar(name, base);
				while (true) {
					if (!consume(16))
						break;
					if (!consume(0))
						break;
					else {
						name = Tokens.get(index - 1).sequence;
						arr = consumeArrayDecl();

						addVar(name, new Type(base.getType(), arr));
					}
				}
				if (consume(17)) {
					symbol.addInfo(name);

					symbol.addInfo(base);
					symbol.addInfo(CLS.CLS_VAR);
					return true;
				}
			}
		}

		index = startindex;
		return false;
	}

	void addVar(String name, Type t) {

		if (crtStruct != null) {
			if (crtStruct.findMember(name) != null)
				throw new ParserException("Symbol redefinition ->" + name);
			Symbol s = crtStruct.AddMember(new Symbol());
			s.addInfo(CLS.CLS_VAR);
			s.addInfo(name);
			s.addInfo(t);
			s.setDepth(depth);
		} else if (crtFunc != null) {
			Symbol s = findSymbol(name);
			if (s != null && s.getDepth() == depth)
				throw new ParserException("Symbol redefition ->" + s.getName());
			s=new Symbol();
			s.addInfo(name);
			s.addInfo(t);
			s.addInfo(MEM.MEM_LOCAL);
			s.addInfo(CLS.CLS_VAR);
			s.addInfo(t);
			s.setDepth(depth);
			

		} else {
			if (findSymbol(name) != null)
				throw new ParserException("Symbol redefinition ->" + name);
			AddSymbol(new Symbol(name, CLS.CLS_VAR, MEM.MEM_GLOBAL, t, depth));
		}

	}

	private boolean consumeStruct() {
		int startindex = index;
		if (consume(9))
			if (consume(0)) {
				if (findSymbol(Tokens.get(startindex + 1).sequence) != null)
					throw new ParserException("Symbol Redefinition");
				else {

					String strname = Tokens.get(startindex + 1).sequence;
					StructSymbol s = new StructSymbol(strname, CLS.CLS_STRUCT, MEM.MEM_GLOBAL, null);
					ArrayList<Symbol> args = new ArrayList<Symbol>();

					crtStruct = s;
					if (consume(22)) {
						while (true) {

							if(!consumeVar()) break;

						}

					}
					if (consume(23))
						if (consume(17)) {
							Type type = new Type(TypeBase.TB_STRUCT, -1, args);
							s.addInfo(type);

							AddSymbol(s);
							crtStruct = null;
							return true;
						}

				}
			}

		index = startindex;
		crtStruct = null;
		return false;
	}

	private boolean consumeStm() {
		int startindex = index;
		index = startindex;
		if (consumeSTMC())
			return true;
		index = startindex;
		if (consume(6))
			if (consume(18))
				if (consumeexpr())
					if (consume(19))
						if (consumeStm()) {
							if (consume(4))
								if (consumeStm()) {
								}
							return true;
						}
		index = startindex;
		if (consume(11))
			if (consume(18))
				if (consumeexpr())
					if (consume(19))
						if (consumeStm())
							return true;

		index = startindex;
		if (consume(5))
			if (consume(18)) {
				consumeexpr();
				if (consume(17)) {
					consumeexpr();
					if (consume(17)) {
						consumeexpr();
						if (consume(19))
							if (consumeStm())
								return true;
					}
				}

			}
		index = startindex;
		if (consume(1))
			if (consume(17))
				return true;
		index = startindex;
		if (consume(8)) {
			consumeexpr();
			if (consume(17))
				return true;
		}
		index = startindex;
		consumeexpr();
		if (consume(17))
			return true;

		index = startindex;
		return false;

	}

	private boolean consumeSTMC() {
		int startindex = index;
		int crtdepth=depth;
		if (consume(22)) {
			depth++;
			Symbol s=Symbols.get(Symbols.size()-1);
			while (true) {
				if (consumeVar()) {
				} else if (consumeStm()) {
				} else
					break;
			}
			if (consume(23))
			{depth--;
			deleteSymbolsAfter(s.getName(), s.getType().getType(), s.getCls());
				return true;
			}
		}
		if(crtdepth!=depth)
			depth--;
		index = startindex;
		return false;
	}

	private boolean consumeFunc() {
		int startindex = index;
		int ok = 0;
		Type t;
		String name;
		if ((t=consumeTBase()) != null) {
			ok = 1;
			if(consume(26))
				t.setNElem(0);
			else
				t.setNElem(-1);
		}
		
		if (ok == 0 && consume(10))
			{ok = 1;
			t=new Type();
			t.setNElem(-1);
			t.setType(TypeBase.TB_VOID);
			}
		if (ok == 1)
			if (consume(0)){
				name=Tokens.get(index-1).sequence;
				if(findSymbol(name)!=null)
					throw new ParserException("Symbol redefition -> " + name);
				crtFunc=new FunSymbol(name, CLS.CLS_FUNC, MEM.MEM_GLOBAL, t, 0);
				depth+=1;
				AddSymbol(crtFunc);
				if (consume(18)) {
					consumeFFrag();
					if (consume(19))
					{ depth-=1;
						if (consumeSTMC())
						{	deleteSymbolsAfter(crtFunc.getName(), t.getType(), CLS.CLS_FUNC);
							//crtFunc=null;
							return true;
						}
					}
				}
		}
		
		crtFunc=null;
		index = startindex;
		return false;
	}

	private boolean consumeFFrag() {
		Symbol s;
		if ((s=consumeFuncArg())!=null)
		{s.setDepth(depth);
			AddSymbol(s);
		crtFunc.addArg(new Symbol(s.getName(),s.getCls(),MEM.MEM_ARG,s.getType(),depth));
			while (true) {
				if (!consume(16))
					break;
				if ((s=consumeFuncArg())==null)
					break;
				else
					AddSymbol(s);
			}
		}

		return true;
	}

	private boolean consumeUnit() {
		while (true) {
			if (consumeStruct()) {
			} else if (consumeFunc()) {
			} else if (consumeVar()) {
			} else if (consume(100)) {
				System.out.println("END");
				return true;
			} else
				return false;
		}

	}
}
