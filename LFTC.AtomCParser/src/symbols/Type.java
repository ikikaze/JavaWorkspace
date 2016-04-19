package symbols;

import enums.TypeBase;

@SuppressWarnings("unused")
public class Type {

	private TypeBase type;
	private int nElements;
	private Symbol s;

	public Type(TypeBase type, int nElem, Symbol s) {
		this.setType(type);
		this.nElements = nElem;
		this.s = s;
	}

	public TypeBase getType() {
		return type;
	}

	public void setType(TypeBase type) {
		this.type = type;
	}

	public static Type getArithType(Type a, Type b) {

		if (a.nElements == -1 && b.nElements == -1 && a.s == b.s && b.s == null)
			if ((b.type != TypeBase.TB_STRUCT && b.type != TypeBase.TB_VOID)
					&& (a.type != TypeBase.TB_STRUCT && a.type != TypeBase.TB_VOID)) {
				switch (a.type) {
				case TB_CHAR:
					return b;
				case TB_INT:
					if (b.type != TypeBase.TB_CHAR)
						return b;
					else
						return a;
				case TB_DOUBLE:
					return a;
				default:
					break;
				}

			}

		return null;
	}

	public boolean equals(Object o) {
		if (o instanceof Type) {
			Type t = (Type) o;
			if (this.s == t.s && this.nElements == t.nElements && this.type == t.type)
				return true;
		}
		return false;
	}

}
