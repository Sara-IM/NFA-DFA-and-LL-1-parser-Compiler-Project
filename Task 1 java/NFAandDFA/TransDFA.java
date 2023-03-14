/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NFAandDFA;

import java.util.ArrayList;

/**
 *
 * @author soso-
 */
public class TransDFA {
    
    ArrayList<Integer> stateFrom = new ArrayList<Integer>();
    ArrayList<Integer> stateTo = new ArrayList<Integer>();
    char symbol;

    public TransDFA(ArrayList<Integer> stateFrom, ArrayList<Integer> stateTo, char symbol) {
        
        this.stateFrom = stateFrom;
        this.stateTo = stateTo;
        this.symbol = symbol;
        
    }
    
    public void print(){
        if(stateTo.size()!=0)
        System.out.println(stateFrom.toString()+" ---- "+symbol+" ----> "+stateTo.toString());
    }
    
}
