package DFADir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.LinkedList;

public class REtoDFA {
    
    ArrayList<ArrayList<Integer>> setStates;
    ArrayList<TransDFA> transactions;
    SyntaxTree tree;
    ArrayList<Character> symbols;
    ArrayList<Integer> initialState;
    ArrayList<ArrayList<Integer>> finalState;
    
    public REtoDFA(SyntaxTree tree) {
        this.tree = tree;
        transactions=new ArrayList<TransDFA> ();
        setStates=new ArrayList<ArrayList<Integer>> ();
        symbols=new ArrayList<Character>();
        recognizeSymbol(tree.regex);
        initialState=new ArrayList<Integer> ();
        finalState=new ArrayList<ArrayList<Integer>> (); }

    public void recognizeSymbol(String re){
        for( Character c : re.toCharArray())
            if (isAlpha(c))
            if(!symbols.contains(c) )
                symbols.add(c);  }
    
    public  boolean isAlpha(char c){
        return c>='a' && c<='z' ; }
    
    
    public void compile(){

        System.out.println(symbols);

        Queue<ArrayList<Integer>> queue=new LinkedList <ArrayList<Integer>>();

        ArrayList<Integer> first= new ArrayList<Integer>();
        first.addAll(tree.getRoot().getFirstPos());
        setStates.add(first);
        
        queue.add(first);
        initialState=queue.peek();
        while(!queue.isEmpty()){
            ArrayList<Integer> q = new ArrayList<Integer>();
            q=queue.remove();
        for (Character c : symbols){

            ArrayList<Integer> moveResult = new ArrayList<Integer>();
            moveResult=move(q, c);
            //here
            ArrayList<Integer> followPosSet = new ArrayList<Integer>();
            for(Integer i : moveResult){
                //her2
                followPosSet=combine(followPosSet,tree.fol.get(i-1));  }
      
            if (followPosSet.size()!=0 && !setStates.contains(followPosSet)){      
                setStates.add(followPosSet);
                queue.add(followPosSet);
            }

            if(followPosSet.size()!=0)
            transactions.add(new TransDFA(q,followPosSet,c));
        } }
        
 System.out.println("States for DFA :"+setStates);
       
        printDFA();}
    
    public ArrayList<Integer> combine(ArrayList<Integer> real, ArrayList<Integer> res){
        for (Integer r:res){
            if(!real.contains(r))
                real.add(r);
        }
        return real; }
  
    
    public ArrayList<Integer> move( ArrayList<Integer> state, char symbol){
        // to find index with same input
        ArrayList<Integer> x= new ArrayList<Integer>();
        for(Integer i : state){
            for (Node n : tree.leafnode)
               if (n.symbol.charAt(0) == symbol && n.getFirstPos().contains(i) )
                    x.add(i);    }
        Collections.sort(x);
        return x; }
    
    
    public void printDFA(){

        System.out.println("Transaction for the DFA : ");
       for(TransDFA d: this.transactions)
           d.print();
        System.out.println("Intial state:"+ this.initialState);
        this.finalState=finalstates(setStates);
        System.out.println("final state:"+ this.finalState);
        
   }
     public ArrayList< ArrayList<Integer>> finalstates(ArrayList<ArrayList<Integer>> sets){
        ArrayList<ArrayList<Integer>> fs=new ArrayList<ArrayList<Integer>> ();
        for (ArrayList<Integer> s : sets){
            for(Integer i : s){
                if(i== (tree.leafnode.size()+1))
                   if(!fs.contains(s)) 
                       fs.add(s);
            } }
        
        return fs;  }
    
}
