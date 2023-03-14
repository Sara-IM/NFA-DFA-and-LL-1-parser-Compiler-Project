
package DFADir;

import java.util.*;


public class SyntaxTree {

    public String regex;
    public static BinaryTree bt;
    private Node root; //the head of raw syntax tree
    private int numOfLeafs; // the number of leafs
     ArrayList<ArrayList<Integer>> fol; // the set of follow pos
     ArrayList<Node> leafnode; //the leaf node

    public SyntaxTree(String regex) {
        this.regex = regex;
        bt = new BinaryTree();
        fol= new ArrayList<ArrayList<Integer>>();
        /**
         * generates the binary tree of the syntax tree
         */
        root = bt.generateTree(regex);

        numOfLeafs = bt.getNumberOfLeafs();

        for(int i=0; i< numOfLeafs ;i++ )
            fol.add(new ArrayList<Integer>());
        this.leafnode = new ArrayList<Node>();
        leaf( root);

        generateNullable(root);
        generateFirstposLastPos(root);
        generateFollowPos(root);}

    public void generateNullable(Node node) {
        if (node == null) {
            return; }
        if (!(node instanceof LeafNode)) {
            Node left = node.getLeft();
            Node right = node.getRight();
            generateNullable(left);
            generateNullable(right);
            switch (node.getSymbol()) {
                //if "|" we will take nullable c1 OR nullable c2
                case "|":
                    node.setNullable(left.isNullable() | right.isNullable());
                    break;
                     //if "." we will take nullable c1 AND nullable c2
                case ".":
                    node.setNullable(left.isNullable() & right.isNullable());
                    break;
                    //if "*" will be true
                case "*":
                    node.setNullable(true);
                    break;  } } }

    public void generateFirstposLastPos(Node node) {
        if (node == null) {
            return; }

        if (node instanceof LeafNode) {
            LeafNode lnode = (LeafNode) node;
            node.addToFirstPos(lnode.getNum());
            node.addToLastPos(lnode.getNum()); } 
            else {
            Node left = node.getLeft();
            Node right = node.getRight();
            generateFirstposLastPos(left);
            generateFirstposLastPos(right);

            switch (node.getSymbol()) {
                case "|":
                // for both first and last pos for case "|" we will take c1&c2
                    node.addAllToFirstPos(left.getFirstPos());
                    node.addAllToFirstPos(right.getFirstPos());
                    node.addAllToLastPos(left.getLastPos());
                    node.addAllToLastPos(right.getLastPos());
                    
                    break;
                case ".":
                //if c1 is nullable, first pos will be c1&c2
                    if (left.isNullable()) {
                        node.addAllToFirstPos(left.getFirstPos());
                        node.addAllToFirstPos(right.getFirstPos());
                    } else {
                          //if c1 is not nullable, first pos  will be c1
                        node.addAllToFirstPos(left.getFirstPos());
                    }
                        //if c2 is nullable, last pos will be c1&c2  
                    if (right.isNullable()) {
                        node.addAllToLastPos(left.getLastPos());
                        node.addAllToLastPos(right.getLastPos());
                    } else {
                     //if c1 is not nullable, last pos  will be c2
                        node.addAllToLastPos(right.getLastPos());
                    }
                    break;
             
                case "*":
                //for both first and last pos we will take (c1)
                    node.addAllToFirstPos(left.getFirstPos());
                    node.addAllToLastPos(left.getLastPos());
                    break;  }
            System.out.println(node.getSymbol());  } }

    public void generateFollowPos(Node node) {
        if (node == null) {
            return;
        }
       
        switch (node.getSymbol()) {
   //first we will take last pos(c1) and first pos(c2)
   //in case "." the follow pos(c1) =c2
            case ".":{
                ArrayList<Integer> k = new  ArrayList<Integer>();
                k.addAll(node.getRight().getFirstPos());
                for (Integer i : node.getLeft().getLastPos() ){
                    k=combine(fol.get(i-1),k);   
                  System.out.println("i="+i + "fol"+ k); } }
                break;
          //in case "*" the follow pos(c2) =c1
            case "*":{
                ArrayList<Integer> k = new  ArrayList<Integer>();
                k.addAll(node.getLastPos());
                for (Integer i : node.getFirstPos() ){
                   k=combine(fol.get(i-1),k);
                System.out.println("i="+i + "fol"+ k);  } }
                break; }
       
       generateFollowPos(node.getLeft());
       generateFollowPos(node.getRight());  }

public ArrayList<Integer> combine(ArrayList<Integer> real, ArrayList<Integer> res){
        for (Integer r:res){
            if(!real.contains(r))
                real.add(r); }
        Collections.sort(real);
        return real;}

        //method to get root
    public Node getRoot() {
        return this.root;}

//this method is to print the first pos and last pos
  public static void  printTree(Node node) {
        //base case
        if (node == null) {
            return; }

        //first left child 
        if (node.getLeft() != null || node.getRight()!=null)
         {    System.out.println(node.getSymbol() + " ");   // to print the data of the node 
             System.out.println("The First pos is: " + node.getFirstPos().toString());
             System.out.println("The Last pos is: "+ node.getLastPos().toString()); 
             System.out.println(); }
        else if (node.getLeft() == null && node.getRight()==null)
         {    System.out.println(node.getSymbol() + " ");   // to print the data of the node 
             System.out.println("The First pos is: " + node.getFirstPos().toString());
             System.out.println("The Last pos is: "+ node.getLastPos().toString()); 
             System.out.println();} 
        // now right child 
        printTree(node.getLeft());
        printTree(node.getRight());} 
  
   public  void  leaf(Node node) {
        //base case
        if (node == null) {
            return;}
        //first left child 
        else if (node.getLeft() == null && node.getRight()==null)
         {    if(! node.getSymbol().equals("#"))
             this.leafnode.add(node); } 
        // now right child 
        leaf(node.getLeft());
        leaf(node.getRight()); }}
