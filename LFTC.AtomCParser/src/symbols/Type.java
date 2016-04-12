package symbols;

import enums.TypeBase;

public class Type {
	
	private TypeBase type;
	private int nElements;
	private Symbol s;
	
	public Type(TypeBase type,int nElem,Symbol s)
	{
		this.type=type;
		this.nElements=nElem;
		this.s=s;
	}
	
	

}
