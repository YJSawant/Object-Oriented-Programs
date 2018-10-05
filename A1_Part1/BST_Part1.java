
// CSE 522: Assignment 1, Part 1

class BST_Part1 {

	public static void main(String args[]) {
		 Tree tr;
/*		 tr = new Tree(50);
		 tr.insert(30);
		 tr.insert(70);
		 tr.insert(10);
		 tr.insert(5);
		 tr.insert(7);
		 tr.insert(40);
		 tr.insert(39);	
		 tr.insert(38);
		 tr.insert(45);
		 tr.insert(80);
		 tr.insert(90);
		 tr.insert(75);
		 tr.delete(7);
		 tr.delete(10);
		 tr.delete(70);
		 tr.delete(30);
		 tr.delete(38);
		 tr.delete(50);
		 tr.delete(75);
		 tr.delete(80);
		 tr.delete(90);
		 tr.delete(5);
		 tr.delete(39);
		 tr.delete(45);
		 tr.delete(40);*/
		 
		 tr = new Tree(100);
		 tr.insert(50);
		 tr.insert(125);
		 tr.insert(150);
		 tr.insert(20);
		 tr.insert(75);
		 tr.insert(20);	
		 tr.insert(90);
		 tr.insert(100);
		 tr.delete(20);
		 tr.delete(20);
		 tr.delete(125);
		 tr.delete(150);
		 tr.delete(100);
		 tr.delete(50);
		 tr.delete(75);
		 tr.delete(25);
		 tr.delete(90);
	}
}

class Tree { // Defines one node of a binary search tree
	
	public Tree(int n) {
		value = n;
		left = null;
		right = null;
	}
	
	public void insert(int n) {
		if (value == n)
			return;
		if (value < n)
			if (right == null)
			{
				right = new Tree(n);
				right.parent = this;
			}
				
			else
				right.insert(n);
		else if (left == null)
		{
			left = new Tree(n);	
			left.parent = this;
		}
			
		else
			left.insert(n);
	}

	public Tree min() {
		if(this.left==null)
			return this;
		return this.left.min();
	}
	
	public Tree max() {
		if(this.right==null)
			return this;
		else return this.right.max();
	}
	
	
	public Tree find(int n) {
		if(this.value==n)
			return this;
		else if(n<this.value)
		{
			if(this.left==null)
				return null;
			else return this.left.find(n);
		}
		else
		{
			if(this.right==null)
				return null;
			else return this.right.find(n);
		}
		}
	
	public void delete(int n) {  
		//
		// *** do not modify this method ***
		//
		Tree t = find(n);
		if (t == null) { // n is not in the tree
			System.out.println("Unable to delete " + n + " -- not in the tree!");
			return;
		} else if (t.left == null && t.right == null) { // n is at a leaf position
			if (t != this) // if t is not the root of the tree
				case1(t);
			else
				System.out.println("Unable to delete " + n + " -- tree will become empty!");
			return;
		} else if (t.left == null || t.right == null) {
			// t has one subtree only
			if (t != this) { // if t is not the root of the tree
				case2(t);
				return;
			} else { // t is the root of the tree with one subtree
				if (t.left != null)
					case3L(t);
				else
					case3R(t);
				return;
			}
		} else 
			// t has two subtrees; replace n with the smallest value in t's right subtree
			case3R(t);
	}
	
	private void case1(Tree t) {  
	// remove leaf node t;
	// you should write the code
		Tree parent = t.parent;
		if(parent.left == t)
			parent.left = null;
		else
			parent.right = null;
		t.parent = null;
		
	}
	
	private void case2(Tree t) {  
	// remove internal node t;
	// you should write the code
		Tree parent = t.parent;
		Tree child;
		if(t.left!=null)
			child= t.left;
		else
			child = t.right;
		if(parent.left == t)
		{
			parent.left = child;
		}
			
		else
		{
			parent.right = child;
		}
		child.parent = parent;
		t.parent = null;
		t.left = null;
		t.right = null;
			
	}
	
	private void case3L(Tree t) {   
	// replace t.value with the largest value, v, in
	// t's left subtree; then delete value v from tree;
	// you should write the code
		Tree largest = t.left.max();
		if(largest.parent.left == largest)
		{
			largest.parent.left = largest.left;
			if(largest.left!=null)
			largest.left.parent = largest.parent;
		}
			
		else
		{
			largest.parent.right = largest.left;
			if(largest.left!=null)
			largest.left.parent = largest.parent;
		}
		if(t.parent != null) {
			if(t.parent.left == t)
			{
				t.parent.left = largest;
			}
				
			else
			{
				t.parent.right = largest;
			}	
			largest.parent = t.parent;
			largest.left=t.left;
			largest.right=t.right;
			if(t.left!= null)
			t.left.parent =largest ;
			t.parent = null;
			t.left= null;
		}
		else
		{
			// if t is root, dont remove it . just exchange value with largest
			t.value= largest.value;
			//t.left= null;
			largest.parent = null ;
			largest.left = null;
		}
		
	
 	}

	private void case3R(Tree t) {   
	// replace t.value with the smallest value, v, in
	// t's right subtree; then delete value v from tree;
	// you should write the code
		Tree smallest = t.right.min();
		if(smallest.parent.left == smallest)
		{
			smallest.parent.left = smallest.right;
			if(smallest.right!=null)
			smallest.right.parent = smallest.parent;
		}
			
		else
		{
			smallest.parent.right = smallest.right;
			if(smallest.right!=null)
			smallest.right.parent = smallest.parent;
		}
		if(t.parent != null) {
			if(t.parent.left == t)
			{
				t.parent.left = smallest;
			}
				
			else
			{
				t.parent.right = smallest;
			}	
			smallest.parent = t.parent;
			smallest.left=t.left;
			smallest.right=t.right;
			if(t.right!= null)
			t.right.parent =smallest ;
			t.right= null;
			if(t.left != null)
			{
			 t.left.parent =smallest ;
			 t.left= null;
			}
			t.parent = null;
		}
		else
		{
			// if t is root, dont remove it . just exchange value with smallest
			t.value= smallest.value;
			//t.left= null;
			smallest.parent = null ;
			smallest.right = null;
			smallest.left = null;
		}
		
 	}

	protected int value;
	protected Tree left;
	protected Tree right;
      protected Tree parent;
}

























