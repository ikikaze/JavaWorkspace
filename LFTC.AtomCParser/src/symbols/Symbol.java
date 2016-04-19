package symbols;

import enums.*;

public class Symbol {

	private String Name;
	private CLS cls;
	private MEM mem;
	private Type type;
	private int depth;

	public Symbol(String Name, CLS cls, MEM mem, Type type, int depth) {
		this.setName(Name);
		this.setCls(cls);
		this.mem = mem;
		this.setType(type);
		this.depth = depth;
	}
	public Symbol() {}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public CLS getCls() {
		return cls;
	}

	public void setCls(CLS cls) {
		this.cls = cls;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void addInfo(CLS cls) {
		this.cls = cls;
	}

	public void addInfo(MEM mem) {
		this.mem = mem;

	}
	
	public void addInfo(String name) {
		this.Name = name;
	}
	
	public void addInfo(Type type) {
		this.type = type;
	}

}
