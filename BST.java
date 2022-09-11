/***************************************************************************************
*    Citation
*    Title: BinarySearchTree.java
*    Author: thmain
*    Date: 11 March 2016
*    Availability: https://gist.github.com/thmain/449545d18617a670c68f
*
***************************************************************************************/
/*
This Code is Binary Search Tree 
*/
import java.io.*;
import java.util.*;

public class BST {
	private class Node {
		//members
		Word data;//data was originally integer
		Node left;
		Node right;
		public Node(Word data){//data was also originally integer
			this.data = data;
			left = null;
			right = null;
		}
		public Node() {//added default constructor
			this.data = new Word();
			left = null;
			right = null;
		}
	}
	private Node root;//head of the nodes
	public Node getRoot() { return root; }
	public void setRoot(Node root) { this.root = root; }
	
	public BST(){//default constructor
		this.root = null;
	}
	
	public boolean exists(Word id){//id was originally integer. Checks if id exists in the tree.
		Node current = root;
		while(current!=null){//locating the node if it exists
			if(current.data.getValue().equals(id.getValue())){//was originally == since id and data were integers
				return true;
			}else if(current.data.getValue().compareTo(id.getValue()) > 0){//was originally current.data > id. Now it checks if current word is alphabetically greater than id
				current = current.left;
			}else{//else move to right node
				current = current.right;
			}
		}
		return false;
	}
	
	public boolean delete(Word id){//id was originally integer
		Node parent = root;
		Node current = root;
		boolean isLeftChild = false;//checks if id is left child
		while(current.data!=id){//exits if id is found in the tree
			parent = current;
			if(current.data.getValue().compareTo(id.getValue()) > 0){//if current node's word is alphabetically greater than id move to left node
				isLeftChild = true;
				current = current.left;
			}else{//else compare right node with id next time
				isLeftChild = false;
				current = current.right;
			}
			if(current ==null){//id is not found.
				return false;
			}
		}
		//if i am here that means we have found the node
		//Case 1: if node to be deleted has no children
		if(current.left==null && current.right==null) {
			if(current==root){//delete root
				root = null;
			}
			if(isLeftChild ==true){//if current is left child of parent, delete parent.left
				parent.left = null;
			}else{//else delete parent.right
				parent.right = null;
			}
		}
		//Case 2 : if node to be deleted has only one child
		else if(current.right==null){//current's right child does not exist
			if(current==root){//current's left child is now new root if current == root
				root = current.left;
			}else if(isLeftChild){//if current is left child of parent, current.left is now parent's left child
				parent.left = current.left;
			}else{//else current.left is parent's right child
				parent.right = current.left;
			}
		}
		else if(current.left==null){//current's left child does not exist
			if(current==root){//new root set to current.right
				root = current.right;
			}else if(isLeftChild){//if current is left child of parent
				parent.left = current.right;//parent's left child is now current's right child
			}else {//else parent's right child is now current's right child
				parent.right = current.right;
			}
		//Case 3 : if node to be deleted has both children
		}else if(current.left!=null && current.right!=null){
			
			//now we have found the minimum element in the right sub tree
			Node successor	 = getSuccessor(current);//returns leftmost descendant of current's right child.
			if(current==root){//if current is root, successor is now new root
				root = successor;
			}else if(isLeftChild){//if current is left child of parent, successor is now new left child of parent
				parent.left = successor;
			}else{//else successor is now new right child of parent
				parent.right = successor;
			}			
			successor.left = current.left;//successor was originally current's right child's leftmost descendant, thus it's left should now be current's left
		}		
		return true;		
	}
	
	public Node getSuccessor(Node deleleNode){//gets descendant node to success the node to be deleted
		Node successsor =null;
		Node successsorParent =null;
		Node current = deleleNode.right;
		while(current!=null){//finds deleleNode's right child's leftmost descendant.
			successsorParent = successsor;
			successsor = current;
			current = current.left;
		}
		//check if successor has the right child, it cannot have left child for sure
		// if it does have the right child, add it to the left of successorParent.
//		successsorParent
		if(successsor!=deleleNode.right){//since successor is being moved to deleleNode's spot, successor's child must replace successor
			successsorParent.left = successsor.right;
			successsor.right = deleleNode.right;
		}
		return successsor;
	}
	public void insert(Word id){//id was originally integer
		Node newNode = new Node(id);
		if(root==null){//inserting first node to the tree
			root = newNode;
			return;//exit function since no other operation needs to be done
		}
		Node current = root;
		Node parent = null;
		while(true){//loops until insertion is done, which it must. Then, exits with return.
			parent = current;
			if(current.data.getValue().compareTo(id.getValue()) > 0){//if current's data is alphabetically greater than id			
				current = current.left;
				if(current==null){//empty spot found at left. insert id
					parent.left = newNode;
					return;
				}
			}else{//current's data is alphabetically lesser than id
				current = current.right;
				if(current==null){//empty spot found at right. insert id
					parent.right = newNode;
					return;
				}
			}
		}
	}
	
	public void outputInOrder(Node node, PrintWriter output, int how) {//Function that I added to print out content to files. 
		//how == 0 indicates writeEqual(), and how == 1 indicates writeDifference() is calling this function.
		if(node != null) {
			outputInOrder(node.left, output, how);//print left
			//writeEqual() called this function, and equal count detected
			if((how == 0) && (node.data.getCountPT() == node.data.getCountYT())) {
				output.println(node.data.getValue() + "\t\t" + node.data.getCountPT());
			}
			//writeDifference() called this function, and PT count > YT count
			else if((how == 1) && (node.data.getCountPT() > node.data.getCountYT())) {
				output.print(node.data.getValue() + "\t\t" + "+" + (node.data.getCountPT() - node.data.getCountYT()) + " PT");
				if(node.data.getCountYT() == 0) {
					output.println(" - ZERO");
				}
				else {
					output.println("");
				}
			}
			//writeDifference() called this function, and PT count < YT count
			else if((how == 1) && (node.data.getCountPT() < node.data.getCountYT())) {
				output.print(node.data.getValue() + "\t\t" + "+" + (node.data.getCountYT() - node.data.getCountPT()) + " YT");
				if(node.data.getCountPT() == 0) {
					output.println(" - ZERO");
				}
				else {
					output.println("");
				}
			}
			outputInOrder(node.right, output, how);//print right
		}
		
	}

}


