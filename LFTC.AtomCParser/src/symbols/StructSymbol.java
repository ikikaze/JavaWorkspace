package symbols;

import java.util.ArrayList;

import enums.CLS;
import enums.MEM;

public class StructSymbol extends Symbol {

	
	private ArrayList<Symbol> members;
	
	public StructSymbol(String Name,CLS cls,MEM mem,Type type,int depth,ArrayList<Symbol> members)
	{ 	super(Name,cls,mem,type,depth);
		this.members=members;
	}
}
