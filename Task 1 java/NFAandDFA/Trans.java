/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NFAandDFA;

// this class to make transaction for nfa states

public class Trans  implements Comparable<Trans>{
    int stateFrom, stateTo;
    char transSymbol;

    public Trans(int state_from, int state_to, char trans_symbol) {
        
        this.stateFrom = state_from;
        this.stateTo = state_to;
        this.transSymbol = trans_symbol;
        
    }
  // method to sort transaction  
@Override
    public int compareTo(Trans compare) {
        int compareage=((Trans)compare).stateFrom;
        /* For Ascending order*/
        return this.stateFrom-compareage;

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }
    
    
    public String print(){
        return (stateFrom+" ---- "+transSymbol+" ----> "+stateTo);
    }
    
    

}
