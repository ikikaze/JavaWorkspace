package sm;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import tokens.ParserException;
import tokens.Tokenizer;
import tokens.Tokenizer.Token;

public class StateMachine {
	public static Tokenizer x=new Tokenizer();
	
	
	public static LinkedList<Token> statemachine(String str)
	{	int state=0;
		String tokenstring="";
		
		File file=new File(str);
		try {
			str=FileUtils.readFileToString(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(str!="!")
		{
			String tmp="";
			
			tmp=""+ str.trim().charAt(0);
			
			switch (state){
			case 0: 
				if(Pattern.matches("[1-9]", tmp))
					{
					state=1;
					tokenstring=tokenstring.concat(tmp);
					str=str.substring(1);
					}
				else
					if(Pattern.matches("0", tmp))
						{
						state=2;
						tokenstring=tokenstring.concat(tmp);
						str=str.substring(1);
						}
					else
						if(tmp.equals("/"))
							{
							state=8;
							tokenstring=tokenstring.concat(tmp);
							str=str.substring(1);
							}
						else
						
							str="!";
						
				break;
			case 1: 
				if(Pattern.matches("[0-9]", tmp))
					{
					state=1;
					tokenstring=tokenstring.concat(tmp);
					str=str.substring(1);
					}
				else
					state=7;
				break;
			case 2:
				if(Pattern.matches("x",tmp))
					{
					state=3;
					tokenstring=tokenstring.concat(tmp);
					str=str.substring(1);
					}
				else
					if(Pattern.matches("[0-7]", tmp))
						{
						state=6;
						tokenstring=tokenstring.concat(tmp);
						str=str.substring(1);
						}
					else 
						str="!";
				break;
			case 3: 
				if(Pattern.matches("[0-9a-fA-F]",tmp))
					{
					state=5;
					tokenstring=tokenstring.concat(tmp);
					str=str.substring(1);
					}
				else 
					str="!";
					break;
			case 5: 
				if(Pattern.matches("[0-9a-fA-F]",tmp))
					{
					state=5;
					tokenstring=tokenstring.concat(tmp);
					str=str.substring(1);
					}
				else
					state=7;
					break;
					
			case 6:
				if(Pattern.matches("[0-7]", tmp))
					{
					state=6;
					tokenstring=tokenstring.concat(tmp);
					str=str.substring(1);
					}
				else state=7;
				break;
			case 7:	
				x.addTk(tokenstring, 12);
				state=0;
				tokenstring="";
				str=str.trim();
				//CT_INT return state;
				break;
			case 8: 
				if(tmp.equals("*"))
				{
				state=9;
				tokenstring=tokenstring.concat(tmp);
				str=str.substring(1);
				}
				else str="!";
			case 9:
				if(Pattern.matches("[^*]", tmp))
					{
					state=9;
					tokenstring=tokenstring.concat(tmp);
					str=str.substring(1);
					}
				else
					if(tmp.equals("*"))
					{
					state=10;
					tokenstring=tokenstring.concat(tmp);
					str=str.substring(1);
					}
					else
						str="!";
				break;
			case 10:
				if(tmp.equals("*"))
				{
				state=10;
				tokenstring=tokenstring.concat(tmp);
				str=str.substring(1);
				}
				else
					if(Pattern.matches("[^*/]", tmp))
						{
						state=9;
						tokenstring=tokenstring.concat(tmp);
						str=str.substring(1);
						}
				else
					if(tmp.equals("/"))
						{
						state=11;
						tokenstring=tokenstring.concat(tmp);
						str=str.substring(1);
						}
					else
					str="!";
				break;
			case 11:
				x.addTk("COMMENT", -1);
				state=0;
				tokenstring="";
				str=str.trim();				
				//COMMENT return state;
				break;
					
			}
			
			
		}
		return x.getTokens();
	}
}
