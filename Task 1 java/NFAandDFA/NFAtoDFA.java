/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NFAandDFA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class NFAtoDFA {
    
    ArrayList<ArrayList<Integer>> setStates;
    //ArrayList<Character> statesLetter;
    ArrayList<TransDFA> transactions;
    //ArrayList<TransDFA> transactionLetters;
    NFA nfa;
    ArrayList<Character> symbols;
    ArrayList<Integer> initialState;
    ArrayList<ArrayList<Integer>> finalState;
    public NFAtoDFA(NFA nfa) {
        this.nfa = nfa;
        transactions=new ArrayList<TransDFA> ();
        setStates=new ArrayList<ArrayList<Integer>> ();
        symbols=new ArrayList<Character>();
        recognizeSymbol(nfa);
        
        initialState=new ArrayList<Integer> ();
        finalState=new ArrayList<ArrayList<Integer>> ();
    }
    public void recognizeSymbol(NFA nfa){
        for( Trans c : nfa.transactions)
            if(!symbols.contains(c.transSymbol) && c.transSymbol!='E' )
                symbols.add(c.transSymbol);
        //System.out.println("symbols"+ symbols);
    }
    
    
    public ArrayList<Integer> e_cluser(int state){
        Stack<Integer> stack = new Stack<Integer>();
        ArrayList<Integer> states =new ArrayList<Integer> ();
        stack.push(state);
        while(!stack.empty()){
            int x=stack.pop();
            for (Trans s : nfa.transactions){
                if(!states.contains(x))
                    states.add(x);
                if(x==s.stateFrom && s.transSymbol=='E')
                    stack.add(s.stateTo);
            }
            Collections.sort(states);
        }
        //System.out.println("E-cluser: "+states);
        
        return states;
        
    }
    
    
    public ArrayList<Integer> move( ArrayList<Integer> state, char symbol){
        ArrayList<Integer> x= new ArrayList<Integer>();
        for(Integer i : state){
            for(Trans s : nfa.transactions)
                if (i==s.stateFrom && symbol==s.transSymbol){
                    x.add(s.stateTo);
                    //System.out.println(i);
                }
        }
        Collections.sort(x);
        //System.out.println(x.toString());
        return x;
    }
    
    
    public void compile(){
        Queue<ArrayList<Integer>> queue=new LinkedList <ArrayList<Integer>>();
        //if(e_cluser(0) != null)
        setStates.add(e_cluser(0));
        
        queue.add(e_cluser(0));
        initialState=queue.peek();
        while(!queue.isEmpty()){
            ArrayList<Integer> q = new ArrayList<Integer>();
            q=queue.remove();
        for (Character c : symbols){
            
            //System.out.println(c);
            ArrayList<Integer> moveResult = new ArrayList<Integer>();
            moveResult=move(q, c);
            
            ArrayList<Integer> ecluserforMove = new ArrayList<Integer>();
            for(Integer i : moveResult){
                
                ecluserforMove=combine(ecluserforMove,e_cluser(i));
            }
            //System.out.println("E-cluser: for move "+ ecluserforMove);
            if (ecluserforMove.size()!=0 && !setStates.contains(ecluserforMove)){
                //System.out.println("ecluserforMove"+ecluserforMove);
                setStates.add(ecluserforMove);
                queue.add(ecluserforMove);
            }
            //System.out.println("from "+q);
            //System.out.println("to "+queue.peek());
            if(ecluserforMove.size()!=0)
            transactions.add(new TransDFA(q,ecluserforMove,c));
        }
        
        }
        
        
        System.out.println("States for DFA :"+setStates);
       
        printDFA();
    }
    public void printDFA(){
        

        System.out.println("Transaction for the DFA : ");
        for(TransDFA d: this.transactions)
            d.print();
        System.out.println("Intial state:"+ this.initialState);
        this.finalState=finalstates(setStates);
        System.out.println("final state:"+ this.finalState);
        
        //Collections.sort(transactions);
        
    }
    
//    public void convertToLetters(ArrayList<ArrayList<Integer>> sets){
//        transactions=new ArrayList<TransDFA>();
//        for (ArrayList<Integer> s : sets ){
//            
//            
//        }
//        
//    }
    
    public ArrayList<Integer> combine(ArrayList<Integer> real, ArrayList<Integer> res){
        for (Integer r:res){
            if(!real.contains(r))
                real.add(r);
        }
        return real;
    }

    public ArrayList< ArrayList<Integer>> finalstates(ArrayList<ArrayList<Integer>> sets){
        ArrayList<ArrayList<Integer>> fs=new ArrayList<ArrayList<Integer>> ();
        for (ArrayList<Integer> s : sets){
            for(Integer i : s){
                if(i== nfa.finalState)
                   if(!fs.contains(s)) 
                       fs.add(s);
            }
            
        }
        
        return fs;
    }
    

}
