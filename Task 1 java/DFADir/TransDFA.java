
package DFADir;

import java.util.ArrayList;


public class TransDFA {
    
    ArrayList<Integer> stateFrom = new ArrayList<Integer>();
    ArrayList<Integer> stateTo = new ArrayList<Integer>();
    char symbol;

    public TransDFA(ArrayList<Integer> stateFrom, ArrayList<Integer> stateTo, char symbol) {
        
        this.stateFrom = stateFrom;
        this.stateTo = stateTo;
        this.symbol = symbol;
        
    }
    //to print states
    public void print(){
        if(stateTo.size()!=0)
        System.out.println(stateFrom.toString()+" ---- "+symbol+" ----> "+stateTo.toString());
    }
    
}
