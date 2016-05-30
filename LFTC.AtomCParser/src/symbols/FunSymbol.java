package symbols;

import java.util.ArrayList;

import enums.CLS;
import enums.MEM;
public class FunSymbol extends Symbol {

	private ArrayList<Symbol> args;

	public FunSymbol(String Name, CLS cls, MEM mem, Type type, int depth, ArrayList<Symbol> args) {
		super(Name, cls, mem, type, depth);
		this.args = args;
	}
	
	public FunSymbol(String Name, CLS cls, MEM mem, Type type, int depth) {
		super(Name, cls, mem, type, depth);
		this.args = new ArrayList<Symbol>();
	}
	
	
	
	public void addArg(Symbol arg)
	{
		this.args.add(arg);
	}

	public ArrayList<Symbol> getArgs() {
		// TODO Auto-generated method stub
		return args;
	}

}
