package parser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import org.apache.commons.io.FileUtils;

import tokens.*;
import tokens.Tokenizer.Token;

public final class AtomCParser {
	
	private static Tokenizer x;
	
	private AtomCParser()
	{}
	
	//Initializes parser with the regexes used for matching
	public static void Initialize(Tokenizer x)
	{
		AtomCParser.x=x; //user defined tokenizer		
	}
	
	//non-parameter method defaults to AtomC
	public static void Initialize()
	{
		Tokenizer tokenizer=new Tokenizer();
		tokenizer.add("//[^\n\r\0]*", -1);
		tokenizer.add("/[*]([^*]|[*]+[^*/])*[*]+/", -1);
				//KEYWORDS 1-11
		tokenizer.add("break", 1);
		tokenizer.add("char", 2);
		tokenizer.add("double", 3);
		tokenizer.add("else", 4);
		tokenizer.add("for", 5);
		tokenizer.add("if", 6);
		tokenizer.add("int", 7);
		tokenizer.add("return", 8);
		tokenizer.add("struct", 9);
		tokenizer.add("void", 10);
		tokenizer.add("while", 11);
		//CT 12-15
		tokenizer.add("[0-9]+([.][0-9]+((e|E)(-|[+])?[0-9]+)?|([.][0-9]+)?((e|E)(-|[+])?[0-9]+))",13); //CT_REAL
		tokenizer.add("([1-9][0-9]*)|(0x[0-9a-fA-F]+)|(0[0-7]*)", 12); //CT_INT		
		
		//tokenizer.add("[']((\\\\[abfnrtv'?\"0\\]|[^'\\]))[']", 14); //CT_CHAR
		//tokenizer.add("\"(\\\\[abfnrtv'?\"\\0]|[^\"\\])*\"", 15); //CT_STRING
		//DELIMITERS 16-23
		tokenizer.add("[,]",16);
		tokenizer.add("[;]",17);
		tokenizer.add("[(]",18);
		tokenizer.add("[)]",19);
		tokenizer.add("\\[",20);
		tokenizer.add("\\]",21);
		tokenizer.add("\\{",22);
		tokenizer.add("}",23);
		//OPERATORS 24-39
		tokenizer.add("[+]",24);
		tokenizer.add("[-]",25);
		tokenizer.add("[*]",26);
		tokenizer.add("[.]",27);
		tokenizer.add("[/]",28);
		tokenizer.add("&&",29);
		tokenizer.add("[|][|]",30);
		tokenizer.add("!",31);
		tokenizer.add("=",32);
		tokenizer.add("==",33);
		tokenizer.add("!=",34);
		tokenizer.add("<",35);
		tokenizer.add(">",36);
		tokenizer.add("<=",37);
		tokenizer.add(">=",38);
		//ID - 0
				tokenizer.add("[a-zA-Z_][a-zA-Z0-9_]*", 0);

		
		
		AtomCParser.x=tokenizer;
		
		
		
	}
	
	//returns all tokens in a file - pass file as string to this
	public static LinkedList<Token> Tokenize(String str)
	{	if(str.endsWith(".c"))
			{
				File file=new File(str);
			try
				{
					str=FileUtils.readFileToString(file);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
	
			try {
				x.tokenize(str);
				} 	catch (ParseException e) {
				e.printStackTrace();	
				}
			
	
		return x.getTokens();
	}
	
}
