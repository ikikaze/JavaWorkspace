package symbols;

import enums.*;

public class Symbol {

	private String Name;
	private CLS cls;
	private MEM mem;
	private Type type;
	private int depth;
	
	public Symbol(String Name,CLS cls,MEM mem,Type type,int depth)
	{
		this.setName(Name);
		this.cls=cls;
		this.mem=mem;
		this.type=type;
		this.depth=depth;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
}

