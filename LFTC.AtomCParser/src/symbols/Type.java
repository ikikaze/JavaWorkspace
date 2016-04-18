package symbols;

import enums.TypeBase;

public class Type {
	
	private TypeBase type;
	private int nElements;
	private Symbol s;
	
	public Type(TypeBase type,int nElem,Symbol s)
	{
		this.setType(type);
		this.nElements=nElem;
		this.s=s;
	}

	public TypeBase getType()
		{
				return type;
		}

	public void setType(TypeBase type)
		{
				this.type = type;
		}
	
	

}
