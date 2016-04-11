package sm;

import java.util.LinkedList;

import tokens.ParserException;
import tokens.Tokenizer.Token;

public class Asin
	{
		private StateMachine		x;
		private LinkedList<Token>	Tokens;
		public int					index;

		public Asin(String fileName)
			{
				x = new StateMachine(fileName);
				Tokens = x.Tokenize();
				index = 0;
			}

		private boolean consume(int code)
			{
				if (Tokens.get(index).token == code)
					{
						index++;
						return true;
					}
				return false;
			}

		private boolean consumePrimary()
			{
				if (!consume(0))
					{
					} else
					{
						consumeprimfrag();
						return true;
					}
				if (consume(12))
					{ // ct_int
						return true;
					}
				if (consume(13))
					{ // real
						return true;
					}
				if (consume(14))
					{// char
						return true;
					}
				if (consume(15))
					{ // string
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
		private boolean consumeexpr()
			{
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

		// „exprOr ::= exprAnd exprOr1” si „exprOr1 ::= OR exprAnd exprOr1 |
		// eps”
		private boolean consumeOr()
			{
				int startindex = index;
				if (consumeAnd())
					if (consumeOr1())
						return true;

				index = startindex;
				return false;
			}

		private boolean consumeOr1()
			{
				int startindex = index;

				if (consume(30))
					if (consumeAnd())
						if (consumeOr1())
							return true;
				index = startindex;
				return true;
			}

		// and = exprEQ exprAND' ---- and' = AND exprEQ exprAND | Eps
		private boolean consumeAnd()
			{
				int startindex = index;
				if (consumeEq())
					if (consumeAnd1())
						return true;
				index = startindex;
				return false;
			}

		private boolean consumeAnd1()
			{
				int startindex = index;
				if (consume(29))
					if (consumeEq())
						if (consumeAnd1())
							return true;
				index = startindex;
				return true;
			}

		// eq = Rel EQ' -----> EQ' = (EQ | NEQ) Rel EQ' | Eps
		private boolean consumeEq()
			{
				if (consumeRel())
					if (consumeEq1())
						return true;
				return false;
			}

		private boolean consumeEq1()
			{
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
		private boolean consumeRel()
			{
				int startindex = index;
				if (consumeAdd())
					if (consumeRel1())
						return true;
				index = startindex;
				return false;
			}

		private boolean consumeRel1()
			{
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

		private boolean consumeprimfrag()
			{

				if (!consume(18))
					return false;
				consume2ndfrag();
				if (!consume(19))
					return false;

				return true;
			}

		private boolean consume2ndfrag()
			{
				consumeexpr();
				while (true)
					{
						if (!consume(16))
							break;
						if (!consumeexpr())
							break;
					}
				return true;

			}

		public boolean consumeAll()
			{
				int startindex = 0;
				int ok1 = 1;
				while (Tokens.get(index).token != 100 && index < Tokens.size())
					{
						startindex = index;
						ok1 = 1;
						if (!consumeAdd())
							{
								ok1 = 0;
								index = startindex;
							}
						if (!consumePrimary() && ok1 == 0)
							{
								index = startindex;
								ok1 = 0;
							}
						if (Tokens.get(index).token != 100)

							if (ok1 == 0)
								return false;// throw new ParserException("BAD
												// character : " +
												// Tokens.get(0).sequence);
					}
				return true;
			}

		// add op mul
		// add = mul add' ;--SI add' = (ADD|SUB) exprmul expradd' | Eps
		private boolean consumeAdd()
			{
				if (consumeMul())
					{
						if (consumeAdd1())
							return true;

					}
				return false;
			}

		private boolean consumeAddSub()
			{
				if (consume(24))
					return true;
				if (consume(25))
					return true;
				return false;
			}

		private boolean consumeAdd1()
			{
				int startindex = index;
				if (consumeAddSub())
					{
						if (consumeMul())
							if (consumeAdd1())
								return true;

					}
				index = startindex;
				return true;
			}

		// mul = cast mul' ; SI mul' = (MUL|DIV) cast mul' | eps

		private boolean consumeMul()
			{
				int startindex = index;
				if (consumeCast())
					{
						if (consumeMul1())
							return true;
					}
				index = startindex;
				return false;
			}

		private boolean consumeMul1()
			{
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

		private boolean consumeCast()
			{
				int startindex = index;
				if (consume(18))
					{
						if (consumeTName())
							if (consume(19))
								if (consumeCast())
									return true;
					} else
					{
						index = startindex;
						if (consumeUnary())
							return true;
					}

				return false;
			}

		private boolean consumeUnary()
			{
				int startindex = index;
				int ok = 0;
				if (consume(25))
					ok = 1;
				else
					{
						index = startindex;
						if (consume(31))
							ok = 1;
					}
				if (ok == 0)
					if (consumeUnary())
						return true;
					else if (consumePostFix())
						return true;

				return false;
			}

		// Postfix = exprPostfix LBRACKET expr RBRACKET
		// | exprPostfix DOT ID
		// | exprPrimary ;
		// Postfix = exprPrimary pf' ;
		private boolean consumePostFix()
			{
				if (!consumePrimary())
					return false;
				if (!consumePF1())
					return false;

				return true;

			}

		// PF' = LBRACKET expr RBRACKET pf' | DOT ID pf';
		private boolean consumePF1()
			{
				int startindex = index;
				int ok = 0;
				if (consume(20))
					{
						ok = 1;
						if (consumeexpr())
							if (consume(21))
								if (consumePF1())
									return true;
					}
				if (ok == 1)
					{
						index = startindex;
						if (consume(27))
							if (consume(0))
								if (consumePF1())
									return true;
					}

				return false;
			}

		// typeName: typeBase arrayDecl? ;
		private boolean consumeTName()
			{
				if (consumeTBase())
					{
						consumeArrayDecl();
						return true;
					}
				return false;
			}

		// arrayDecl: LBRACKET expr? RBRACKET ;
		private boolean consumeArrayDecl()
			{
				int startindex = index;
				if (consume(20))
					{
						consumeexpr();
						if (consume(21))
							return true;
					}
				index = startindex;
				return false;

			}

		// typeBase: INT | DOUBLE | CHAR | STRUCT ID ;
		private boolean consumeTBase()
			{
				if (consume(7)) // int
					return true;
				if (consume(3)) // double
					return true;
				if (consume(2)) // char
					return true;
				if (consume(9)) // struct
					if (consume(0)) // id
						return false;

				return false;
			}

		// funcArg: typeBase ID arrayDecl? ;
		private boolean consumeFuncArg()
			{
				int startindex = index;
				if (consumeTBase())
					if (consume(0))
						{
							consumeArrayDecl();
							return true;
						}
				index = startindex;
				return false;

			}

		private boolean consumeVar()
			{
				int startindex = index;
				if (consumeTBase())
					if (consume(0))
						{
							consumeArrayDecl();
							while (true)
								{
									if (!consume(16))
										break;
									if (!consume(0))
										break;
									consumeArrayDecl();
								}
							if (consume(17))
								return true;
						}

				index = startindex;
				return false;
			}

		private boolean consumeStruct()
			{
				int startindex = index;
				if (consume(9))
					if (consume(0))
						if (consume(22))
							{
								while (true)
									if (!consumeVar())
										break;
								if (consume(23))
									if (consume(17))
										return true;

							}
				index = startindex;
				return false;
			}

		private boolean consumeStm()
			{
				int startindex = index;
				index = startindex;
				if (consumeSTMC())
					return true;
				index = startindex;
				if (consume(6))
					if (consume(18))
						if (consumeexpr())
							if (consume(19))
								if (consumeStm())
									{
										int ok = 0;
										if (consume(4))
											ok = 1;
										if (ok == 1)
											consumeStm();
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
					if (consume(18))
						{
							consumeexpr();
							if (consume(17))
								{
									consumeexpr();
									if (consume(17))
										{
											consumeexpr();
											if (consume(18))
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
				if (consume(8))
					{
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

		private boolean consumeSTMC()
			{
				int startindex = index;
				if (consume(22))
					{
						while (true)
							{
								if (!consumeVar())
									break;
								if (!consumeStm())
									break;
							}
						if (consume(23))
							return true;
					}

				index = startindex;
				return false;
			}

		private boolean consumeFunc()
			{
				int startindex = index;
				int ok = 0;
				if (consumeTBase())
					{
						ok = 1;
						consume(26);
					}
				if (ok == 0 && consume(10))
					ok = 1;
				if (ok == 1)
					if (consume(0))
						if (consume(18))
							{
								consumeFFrag();
								if (consume(19))
									if (consumeSTMC())
										return true;
							}
				index = startindex;
				return false;
			}

		private boolean consumeFFrag()
			{
				if (consumeFuncArg())
					while (true)
						{
							if (!consume(16))
								break;
							if (!consumeFuncArg())
								break;
						}
				// TODO Auto-generated method stub
				return true;
			}
		
		
		private boolean consumeUnit()
		{
			while(true)
				{
					if(!consumeStruct())
						break;
					if(!consumeFunc())
						break;
					if(!consumeVar())
						break;
				}
			return true;
		}
	}
