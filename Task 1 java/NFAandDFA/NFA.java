/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NFAandDFA;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class NFA {
    ArrayList<Integer> states; 
    ArrayList<Trans> transactions;
    int startState,finalState;
    
    public NFA() {
        states = new ArrayList <Integer> ();
        transactions = new ArrayList <Trans> ();
        startState = 0;
        finalState = 0;
    }
    // if alpha construct nfa
    public NFA(char symbol){
        states=new ArrayList<Integer>();
        transactions= new ArrayList<Trans>();
        addStates(2);
        startState=0;
        finalState=1;
        transactions.add(new Trans (0,1,symbol));
        
    }
    // used in concatenation and alternation methods
        public NFA(ArrayList<Integer> st, ArrayList<Trans> trans){
        states=new ArrayList<Integer>();
        transactions= new ArrayList<Trans>();
        for(Integer s :st )
        states.add(s);
        startState=0;
        finalState=states.size()-1;
        for(Trans t :trans )
        transactions.add(new Trans (t.stateFrom,t.stateTo,t.transSymbol));
        
    }
    
    public void addStates(int stateNum){
        for(int i=0; i< stateNum ; i++)
            states.add(i);
    }
    
    
    public NFA concat(NFA nfa1, NFA nfa2){
        //System.out.println("Concat");
        //remove first state of nfa2 
        nfa2.states.remove(startState);
        // combine states of nfa1 and nfa2
        int nfa1size=nfa1.states.size();
        for(Integer s : nfa2.states){
            nfa1.states.add(s+nfa1size-1);
        }
        // combine transactions of nfa1 and nfa2
        for (Trans t : nfa2.transactions){
            nfa1.transactions.add(new Trans(t.stateFrom+nfa1size-1,t.stateTo+nfa1size-1,t.transSymbol));
        }
        // create new nfa 
        NFA newNFA=new NFA(nfa1.states,nfa1.transactions );
        //printNFA(newNFA);    
        return newNFA;
        
    }
   
    public NFA star(NFA nfa){
        //System.out.println("Star");
        // main states and transaction to move the new satates and transactions in
        ArrayList<Integer> newStates= new ArrayList<Integer>();
        ArrayList<Trans> newTrans=new ArrayList<Trans>();
        // add first state
        newStates.add(0);
        // s0--E-->s1
        newTrans.add(new Trans(0,nfa.startState+1,'E'));
        //s0--E-->slast (( for empty case ))
        newTrans.add(new Trans(0,nfa.states.size()+1,'E'));
        
        //move the original states and trans to the new arrays
        for(Integer s : nfa.states)
            newStates.add(s+1);
            
        for(Trans t : nfa.transactions)
            newTrans.add(new Trans (t.stateFrom+1,t.stateTo+1,t.transSymbol));
        
        // add final state
        newStates.add(newStates.size());
        // sn-1--E---> sn  (sn is last state)
        newTrans.add(new Trans(newStates.size()-2,newStates.size()-1,'E'));
        // trans for the loop with E
        newTrans.add(new Trans(newStates.size()-2,newStates.size()-nfa.states.size()-1,'E'));

           
        NFA newNFA=new NFA(newStates,newTrans );
        //printNFA(newNFA);    
        return newNFA;
    }
    
    public NFA alternate(NFA nfa1, NFA nfa2){
        //System.out.println("OR");
    // main states and transaction to move the new satates and transactions in
    ArrayList<Integer> newStates= new ArrayList<Integer>();
    ArrayList<Trans> newTrans=new ArrayList<Trans>(); 
    // s0 and its transaction for first branch (nfa1)
    newStates.add(0);
    newTrans.add(new Trans(0,nfa1.startState+1,'E'));
    
    for(Integer s : nfa1.states)
        newStates.add(s+1);
            
    for(Trans t : nfa1.transactions)
        newTrans.add(new Trans (t.stateFrom+1,t.stateTo+1,t.transSymbol));
    
    // s0 and its transaction for second branch (nfa2)
    newTrans.add(new Trans(0,newStates.size(),'E'));
    
    int size = newStates.size()-1;
    for(Integer s : nfa2.states)
        newStates.add(s+size+1);
            
    for(Trans t : nfa2.transactions)
        newTrans.add(new Trans (t.stateFrom+size+1,t.stateTo+size+1,t.transSymbol));
    // add last state
    newStates.add(newStates.size());
    // combine the 2 branches with the final state
    newTrans.add(new Trans(nfa1.states.size(),newStates.size()-1,'E'));
    newTrans.add(new Trans(newStates.size()-2,newStates.size()-1,'E'));

    NFA newNFA=new NFA(newStates,newTrans );
    //printNFA(newNFA);    
    return newNFA;
    }
    
    public String printNFATrans(NFA newNFA){
//        System.out.println("States for the NFA : ");
//        for(Integer d: newNFA.states)
//            System.out.print("state "+d+" ");
        String s="Transaction for the NFA :\n";
        //System.out.println("Transaction for the NFA : ");
        for(Trans d: newNFA.transactions)
            s+= d.print()+"\n";
        
        Collections.sort(newNFA.transactions);
        return s;
    }
    
     public String printNFAState(NFA newNFA){
        String s="States for the NFA :\n "; 
         for(Integer d: newNFA.states)
            s+=("state "+d+"\n ");
         return s;
     }
     
     public String printStartState(NFA newNFA){
        
         return Integer.toString(newNFA.startState);
     }
     
    public String printFinalState(NFA newNFA){
        
         return Integer.toString(newNFA.finalState);
     } 
    
    public NFA compile(String re){
        Stack<NFA> stack = new Stack<NFA>();
        //Scanner input = new Scanner(System.in);
        //System.out.println("Enter RE note :\n"
               // + "use '|' for alternation\nuse '*' for repetation");
        // it has to check the validity of RE and convert it from infix to popstfix expression
        // example re= a*|b.a
        //String re=input.next();
        RE reval=new RE(re);
        String postRE= reval.RE;
        System.out.println("the postfix form "+postRE);
        // if alpha:
        // 1- convert alpa into nfa 
        //2- push nfa into stack
        // if operator: pop the stack and call appropriate method
        for (int i=0; i<postRE.length();i++){
            if (isAlpha(postRE.charAt(i))){
                NFA nfa = new NFA(postRE.charAt(i));
                stack.push(nfa);
            }
            
            else if (postRE.charAt(i)=='.'){
                NFA nfa2=stack.pop();
                NFA nfa1=stack.pop();
                stack.push(concat(nfa1,nfa2));
            }
            
            else if (postRE.charAt(i)=='|'){
                NFA nfa2=stack.pop();
                NFA nfa1=stack.pop();
                stack.push(alternate(nfa1,nfa2));
            }
            else{
                NFA nfa=stack.pop();
                stack.push(star(nfa));
            
            }
                
                
        }
//       
        NFA nfa=stack.pop();
//        System.out.println("");
//        System.out.println("The NFA from the given expression is : " );
//        printNFA(nfa);
//        NFAtoDFA dfa=new NFAtoDFA(nfa);
//        dfa.compile();
//        DFA dfa1=new DFA(dfa);
        //System.out.println(dfa1.setStates);
        //dfa1.printTrans(dfa);
     return nfa;   
    }
    public  boolean isAlpha(char c){
        return c>='a' && c<='z' || c=='E';
    }
    
    
    
    
    
    public static void main(String[] args){
//        NFA a= new NFA('a');
//        for(Trans s: a.transactions)
//            s.print();
//       NFA b= new NFA('b');
//        for(Trans s: b.transactions)
//            s.print();        
        NFA ab= new NFA();
        //ab.concat(a, b);
        //ab.star(a);
        //ab.alternate(a, b);
        //ab.compile();
        
//        for(Trans s: ab.transactions)
//            s.print();
    }

    
}
