package symbols;

import java.awt.datatransfer.ClipboardOwner;

import enums.*;
@SuppressWarnings("unused")
public class Symbol implements Cloneable {

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
		this.setDepth(depth);
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
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	


}
