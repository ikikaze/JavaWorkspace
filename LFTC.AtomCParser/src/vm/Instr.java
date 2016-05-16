package vm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import enums.Opcodes;

public class Instr extends VMObject{

	
	Opcodes code;
	class Args {
	private int i;
	private double d;
	private VMObject addr;
	
	public Args(int i,double d,VMObject addr)
	{
		this.i=i;
		this.d=d;
		this.addr=addr;
	}
	}
	
	ArrayList<Args> args=new ArrayList<Args>();
	
	
	
	public Instr(Opcodes code,Object a,Object b)
	{
		
		this.code=code;
		
		args.add((Args)a);
		args.add((Args)b);
	}
}
