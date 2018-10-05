//CSE 522: Assignment 1, Part 2

  class BST_Part2 {

	  public static void main(String[] args) {
		AbsTree tr = new DupTree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);
		
		tr.delete(20);
		tr.delete(20); // not present
		tr.delete(20);// not present
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);// not present
		tr.delete(125);
		tr.delete(125);// not present
		tr.delete(50);
		tr.delete(50);// not present
		tr.delete(50);// not present
		tr.delete(75);
		tr.delete(90); //can not delete
		tr.delete(75);// not present
		tr.delete(90);// not present
		}
  }
  
  abstract class AbsTree {

	public AbsTree(int n) {
		value = n;
		left = null;
		right = null;
	}

	public void insert(int n) {
		if (value == n)
			count_duplicates();
		else if (value < n)
			if (right == null)
			{
				right = add_node(n);
				right.parent = this;
			}
				
			else
				right.insert(n);
		else if (left == null)
		{
			left = add_node(n);
			left.parent = this;
		}
			
		else
			left.insert(n);
	}
	
	public void delete(int n) {  
		AbsTree t = find(n);
		if (t == null) { // n is not in the tree
			System.out.println("Unable to delete " + n + " -- not in the tree!");
			return;
		} 
		else {
			
			int count = t.get_count();
			// if count is more than once , it is duplicate. just decrease count
			if(count > 1 )
			{
				t.set_count(--count);
				return;
			}
			 if (t.left == null && t.right == null) { // n is at a leaf position
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

	}
	
	protected void case1(AbsTree t) {  
		// remove leaf node t;
			AbsTree parent = t.parent;
			// remove parents link to leaf node t
			if(parent.left == t)
				parent.left = null;
			else
				parent.right = null;
			// make parent link to node t null
			t.parent = null;
			
		}
	
	protected void case2(AbsTree t) {  
		// remove internal node t;
			AbsTree parent = t.parent;
			AbsTree child;
			
			// get child of internal node t
			if(t.left != null)
				child= t.left;
			else
				child = t.right;
			
			// assign child node of internal node t to its parent and vice versa 
			if(parent.left == t)
			{
				parent.left = child;
			}
				
			else
			{
				parent.right = child;
			}
			child.parent = parent;
			
			//detach internal node t from its parent and child
			t.parent = null;
			t.left = null;
			t.right = null;
				
		}
	
	protected void case3L(AbsTree t) {   
		// get largest value from t's left subtree 
			AbsTree largest = t.left.max();
			// Detaching largest element from its parent and child
			if(largest.parent.left == largest)
			{
				//only need to worry about largest's left child as right child 
				//of largest will always be null
				largest.parent.left = largest.left;
				if(largest.left!=null)
				largest.left.parent = largest.parent;
			}
				
			else
			{
				//only need to worry about largest's left child as right child 
				//of largest will always be null
				largest.parent.right = largest.left;
				if(largest.left!=null)
				largest.left.parent = largest.parent;
			}
			//check if t is root node
			if(t.parent != null) {
				// attach largest element to t's parent
				if(t.parent.left == t)
				{
					t.parent.left = largest;
				}
					
				else
				{
					t.parent.right = largest;
				}
				// assign t's parent and child pointer to largest 
				largest.parent = t.parent;
				largest.left=t.left;
				largest.right=t.right;
				// update t's child's parent pointer with largest
				if(t.left !=  null)
				t.left.parent =largest ;
				//detach t from its child and parents
				t.parent = null;
				t.left= null;
			}
			else
			{
				// if t is root, dont remove it . just exchange value and duplicate count with largest
				t.value= largest.value;
				t.set_count(largest.get_count());
				//detach largest with parent and child
				largest.parent = null ;
				largest.left = null;
			}
			
		
	 	}

	protected void case3R(AbsTree t) {   
		
		// get smallest value from t's right subtree 
			AbsTree smallest = t.right.min();
			if(smallest.parent.left == smallest)
			{
				//only need to worry about smallest's right child as left child 
				//of smallest will always be null
				smallest.parent.left = smallest.right;
				if(smallest.right!=null)
				smallest.right.parent = smallest.parent;
			}
				
			else
			{
				//only need to worry about smallest's right child as left child 
				//of smallest will always be null
				smallest.parent.right = smallest.right;
				if(smallest.right!=null)
				smallest.right.parent = smallest.parent;
			}
			//check if t is root node
			if(t.parent != null) {
				// attach smallest element to t's parent
				if(t.parent.left == t)
				{
					t.parent.left = smallest;
				}
					
				else
				{
					t.parent.right = smallest;
				}
				// assign t's parent and child pointer to smallest 
				smallest.parent = t.parent;
				smallest.left=t.left;
				smallest.right=t.right;
				// update t's child's parent pointer with smallest
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
				// if t is root, dont remove it . just exchange value with and duplicate count with smallest
				t.value= smallest.value;
				t.set_count(smallest.get_count());
				//detach smallest with parent and child
				smallest.parent = null ;
				smallest.right = null;
				smallest.left = null;
			}
			
	 	}

	private AbsTree find(int n) {
	// adapt Part 1 solution and use here
		if(this.value == n)
			return this;
		else if(n < this.value)
		{
			if(this.left == null)
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
	
	public AbsTree min() {
	// adapt Part 1 solution and use here
		if(this.left==null)
			return this;
		return this.left.min();
	
	}

	public AbsTree max() {
	// adapt Part 1 solution and use here
		if(this.right==null)
			return this;
		else return this.right.max();
	
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;
	protected AbsTree parent;

	// Protected Abstract Methods
	
	protected abstract AbsTree add_node(int n);
	protected abstract void count_duplicates();
	
	//Protected abstract methods added extra to support delete in Duptree
	protected abstract int get_count();
	protected abstract void set_count(int count);
}


class Tree extends AbsTree {

	public Tree(int n) {
		super(n);
	}

	protected AbsTree add_node(int n) {
		return new Tree(n);
	}

	protected void count_duplicates() {
		;
	}

	@Override
	protected int get_count() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	protected void set_count(int count) {
		// TODO Auto-generated method stub
		
	}
	
	// define additional protected methods here, as needed

}

class DupTree extends AbsTree {

	public DupTree(int n) {
		super(n);
		count = 1;
	};

	protected AbsTree add_node(int n) {
		return new DupTree(n);
	}

	protected void count_duplicates() {
		count++;
	}

	// defined additional protected methods
	@Override
	protected int get_count() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	protected void set_count(int count) {
		// TODO Auto-generated method stub
	this.count = count;	
	}

	protected int count;


}