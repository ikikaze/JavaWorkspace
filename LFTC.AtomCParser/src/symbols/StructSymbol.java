package symbols;

import java.util.ArrayList;
import java.util.List;

import enums.CLS;
import enums.MEM;

public class StructSymbol extends Symbol {

	private ArrayList<Symbol> members;

	public StructSymbol(String Name, CLS cls, MEM mem, Type type) {
		super(Name, cls, mem, type, 0);
		members = new ArrayList<Symbol>();

	}

	public Symbol AddMember(Symbol x) {
		members.add(x);
		return x;
	}

	public void AddFields(List<Symbol> x) {
		members.addAll(x);
	}

	public ArrayList<Symbol> getMembers() {
		return members;
	}

	public Symbol findMember(String name) {
		for (Symbol x : members)
			if (x.getName().equals(name))
				return x;

		return null;
	}
}
