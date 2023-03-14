/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NFAandDFA;

import java.util.ArrayDeque;
import java.util.Deque;
// this class used to convert the RE from infix format to postfix format
public class RE {
     String RE;
     Deque<Character> stack = new ArrayDeque<Character>();

    public RE(String RE) {
        if (isValid(RE)){
   
        this.RE = toPostfix(add_join_symbol(RE));
        
    }
        else System.out.println("RE is not valid ");
    }
    // check if the RE is valid 
    public  boolean isValid(String RE){
        if (RE.isEmpty())
            return false;
        else {
            for ( char c : RE.toCharArray())
                if ( !isOperator(c) && !isAlpha(c)   )
                    return false;
        }
        
        return true;
    }
    
    public String add_join_symbol(String re) {
		
		if(re.length()==1) {
			System.out.println("new RE is :" + re);
			
			return re;
		}
		int return_string_length = 0;
		char return_string[] = new char[2 * re.length() + 2];
		char first, second = '0';
		for (int i = 0; i < re.length() - 1; i++) {
			first = re.charAt(i);
			second = re.charAt(i + 1);
			return_string[return_string_length++] = first;
			if (first != '(' && first != '|' && isAlpha(second)) {
				return_string[return_string_length++] = '.';
			}
			else if (second == '(' && first != '|' && first != '(') {
				return_string[return_string_length++] = '.';
			}
		}
		return_string[return_string_length++] = second;
		String rString = new String(return_string, 0, return_string_length);
		System.out.println("new RE is: " + rString);
		
		re = rString;
		return rString;
	}

    
    public  boolean isAlpha(char c){
        return c>='a' && c<='z' || c=='E';
    }
    
    public  boolean isOperator(char c){
        return c=='|' || c=='*' || c==')' || c=='(' ;
    }
    
    public  String toPostfix(String RE){
        String newRE="";
        for ( char c : RE.toCharArray()){
            if( Character.isLetterOrDigit(c))
                newRE+=c;
            
            else if (c=='(')
                stack.push(c);
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    newRE += stack.peek();
                    stack.pop();
                }
            stack.pop();
            }
            else 
            {
                while (!stack.isEmpty() && prec(c) <= prec(stack.peek())) {
                    newRE += stack.peek();
                    stack.pop();
                }
                stack.push(c);
            }
  
        }
        
        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                return "Invalid Expression";
            newRE += stack.peek();
            stack.pop();
        }
        return newRE;
    }
    
    public  int prec(char c){
        switch (c) {
            case '|':
                return 1;
                
            case '.':
                return 2;
            case '*':
                return 3;
        }
     return -1;          
    }
    
    
}
