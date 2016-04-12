package symbols;

import java.util.ArrayList;

import enums.CLS;
import enums.MEM;

public class FunSymbol extends Symbol {
	
	private ArrayList<Symbol> args;
	
	public FunSymbol(String Name,CLS cls,MEM mem,Type type,int depth,ArrayList<Symbol> args)
	{	super(Name,cls,mem,type,depth);
		this.args=args;
	}

}
