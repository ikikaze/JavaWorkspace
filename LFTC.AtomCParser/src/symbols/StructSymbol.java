package symbols;

import java.util.ArrayList;

import enums.CLS;
import enums.MEM;

public class StructSymbol extends Symbol
	{

		private ArrayList<Symbol> members;

		public StructSymbol(String Name, CLS cls, MEM mem, Type type)
			{
				super(Name, cls, mem, type, 0);

			}

		public void AddMember(Symbol x)
			{
				members.add(x);
			}
	}
