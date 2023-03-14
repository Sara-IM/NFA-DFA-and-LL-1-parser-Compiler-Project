/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NFAandDFA;

import java.util.ArrayList;

public class DFA {
    ArrayList<Character> setStates = new  ArrayList<Character>();
    char initialState;
    ArrayList<Character> finalState;
    
    public DFA(NFAtoDFA dfa) {
        finalState=new ArrayList<Character>();
        setDfaStetes(dfa);
        finalstate(dfa);
        System.out.println("DFA states: "+setStates);
        initialState=setStates.get(dfa.setStates.indexOf(dfa.initialState));
        printTrans(dfa);
        System.out.println("initial state is :"+  initialState);
        System.out.println("final states are :"+ finalState );
        
    }
    
    public void setDfaStetes(NFAtoDFA dfa){
        char c='A';
        for(int i=0; i<dfa.setStates.size();i++ ){
            this.setStates.add(c);
            c++;
        }
        //printTrans(dfa);
    }
    
    public void finalstate(NFAtoDFA dfa){
        for(ArrayList<Integer> s: dfa.finalState)
            this.finalState.add(setStates.get(dfa.setStates.indexOf(s)));
           // System.out.println("from"+posFrom);
            
           
    }
    
     public String printDFAState(){
        String s="States for the DFA :\n "; 
         for(Character d: setStates )
            s+=("state "+d+"\n ");
         return s;
     }
     
     public String printStartState(){
        
         return Character.toString(initialState);
     }
     
    public String printFinalState(){
         String s="\n";
         for(Character d: finalState )
            s+=d+"\n ";
         return s;
     } 
    
    
    public String printTrans(NFAtoDFA dfa){
        String st= "Transaction for the DFA :\n";
        for(TransDFA s: dfa.transactions){
            int posFrom=dfa.setStates.indexOf(s.stateFrom);
            int posTo=dfa.setStates.indexOf(s.stateTo);
           
            if( setStates.get(posTo) != -1)
            st+=(setStates.get(posFrom)+"-----"+s.symbol+"---->"+
                    setStates.get(posTo))+"\n";
        }
        return st;
    }
    
    
    
    
    
}
