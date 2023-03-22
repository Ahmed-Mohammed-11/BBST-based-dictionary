import java.util.ArrayList;

public class RBTree<Any extends Comparable<Any>> extends Tree<Any>
{
	protected RBNode nil;
	class RBNode extends Node
	{
		boolean black;
		RBNode p;

		RBNode(Any key)
		{
			super(key);
			black = false;
		}

		RBNode(Any key, boolean black)
		{
			super(key);
			this.black = black; 
		}

		RBNode(Any key, boolean black, RBNode p)
		{
			super(key);
			this.black = black;
			this.p = p;
		}

	}

	RBTree()
	{
		nil = new RBNode(null, true);
		root = nil;
	}

	public int height()
	{
		
		return height(root);
	}

	private int height(Node n)
	{
		if (n == nil) return 0;
		int hl = height(n.left), hr = height(n.right);
		return hl > hr ? hl+1 : hr+1;
	}


	
	public boolean insert(Any key)
	{
		RBNode current = (RBNode)root;
		if (current == nil)
		{
			current = new RBNode(key, true);
			current.p = nil;
			current.right = nil;
			current.left = nil;
			root = current;
			size++;
			return true;
		}
		RBNode lastNode = null;
		while(current != nil)
		{
			lastNode = current;
			int compareResult = key.compareTo(current.key); 
			if (compareResult < 0) current = (RBNode)current.left;
			else if(compareResult > 0) current = (RBNode)current.right;
			else return false;
		}
		RBNode newNode = new RBNode(key, false, lastNode);
		newNode.left = nil;
		newNode.right = nil;
		int compareResult = key.compareTo(lastNode.key);
		if (compareResult < 0) lastNode.left = newNode;
		else lastNode.right = newNode;
		
		insertFixUp(newNode);
		size++;
		return true;
	}

	//Fix Red black tree properties after insert
	protected void insertFixUp(RBNode z)
	{
		while (!z.p.black)
		{
			RBNode uncle;

			boolean gpOnRight = z.p.p.left == z.p;
			if (gpOnRight) uncle = (RBNode)z.p.p.right;
			else uncle = (RBNode)z.p.p.left;
			if (!uncle.black)
			{
				uncle.black = true;
				z.p.black = true;
				z.p.p.black = false;
				z = z.p.p;
			}
			else
			{
				boolean LR = gpOnRight && z==z.p.right, RL = !gpOnRight && z==z.p.left;
				if (RL || LR)
				{
					z = z.p;
					if (RL) rotateRight(z);
					else rotateLeft(z);
				}
				z.p.black = true;
				z.p.p.black = false;
				if (gpOnRight) rotateRight(z.p.p);
				else rotateLeft(z.p.p);
			}
		}

		RBNode r = (RBNode)root;
		// while (r.p != nil) r = r.p;
		// root = r;
		r.black = true;
	}

	//Fix Red black tree properties after delete
	public boolean delete(Any key)
	{
		if (size == 1 && key.compareTo(root.key) == 0)
		{
			size = 0;
			root = nil;
			return true;
		}
		RBNode current = (RBNode)root;
		while(current != nil)
		{
			int compareResult = key.compareTo(current.key); 
			if (compareResult < 0) current = (RBNode)current.left;
			else if(compareResult > 0) current = (RBNode)current.right;
			else break;
		}
		if (current == nil) return false;
		
		if (current.left != nil && current.right != nil) //current has 2 children
		{
			RBNode succ = (RBNode)current.right;
			while(succ.left != nil) 
				succ = (RBNode)succ.left;
			
			Any tmp =current.key;
			current.key = succ.key;
			succ.key = tmp;
			current = succ;
		}
		//Now current has at most one real child.
		RBNode child;
		if (current.left == nil) child = (RBNode)current.right;
		else child = (RBNode)current.left;
		
		if (current == current.p.left) current.p.left = child;
		else current.p.right = child;
		child.p = current.p;
		if (current == root) root = child;
		if (current.black) deleteFixUp(child);
		size--;
		return true;
	}

	protected void deleteFixUp(RBNode x)
	{
		while(x != root && x.black)
		{
			boolean leftLeaning = x == x.p.left;
			RBNode s;
			if (leftLeaning) s = (RBNode) x.p.right;
			else s = (RBNode) x.p.left;

			if (!s.black) //Case 1
			{
				s.black = true;
				x.p.black = false;
				if (leftLeaning)
				{
					rotateLeft(x.p);
					s = (RBNode)x.p.right;
				}
				else
				{
					rotateRight(x.p);
					s = (RBNode) x.p.left;
				}
			}// End of case 1
			if (((RBNode)s.left).black && ((RBNode)s.right).black) //Case 2
			{
				s.black = false;
				x = x.p;
				continue; //End of case 2
			}
			else
			{
				if (leftLeaning && ((RBNode)s.right).black)//Case 3
				{
					RBNode c = (RBNode) s.left;
					c.black = true;
					s.black = false;
					rotateRight(s);
					s = (RBNode)x.p.right;
				}
				else if (!leftLeaning && ((RBNode)s.left).black)
				{
					RBNode c = (RBNode) s.right;
					c.black = true;
					s.black = false;
					rotateLeft(s);
					s = (RBNode) x.p.left;
				} //End of case 3
				//Case 4
				s.black = x.p.black;
				x.p.black = true;
				RBNode c;
				if (leftLeaning)
				{
					c = (RBNode) s.right;
					rotateLeft(x.p);
				}
				else
				{
					c = (RBNode) s.left;
					rotateRight(x.p);
				}
				c.black = true;
				x = (RBNode)root;
			}
		}
		x.black = true;
	}



	void rotateLeft(RBNode n)
	{
		if (n.right == nil) 
		{
			System.out.printf("WARNING! LEFT ROTATE AT %d IS FUTILE! RIGHT CHILD IS NIL.\n", n.key);
			return;
		}
		RBNode parent = n.p;
		RBNode child = (RBNode) n.right;
		//Connect child to parent
		child.p = parent;
		if (n == parent.left) parent.left = child;
		else parent.right = child;
		//Connect n to child as its child
		RBNode newChild = (RBNode)child.left;
		child.left = n;
		n.p = child;
		n.right = newChild;
		newChild.p = n;
		if (root == n) root = child;
	}

	void rotateRight(RBNode n)
	{
		if (n.left == nil) 
		{
			System.out.printf("WARNING! RIGHT ROTATE AT %d IS FUTILE! LEFT CHILD IS NIL.\n", n.key);
			return;
		}
		RBNode parent = n.p;
		RBNode child = (RBNode) n.left;
		//Connect child to parent
		child.p = parent;
		if (n == parent.right) parent.right = child;
		else parent.left = child;
		//Connect n to child as its child
		RBNode newChild = (RBNode)child.right;
		child.right = n;
		n.p = child;
		n.left = newChild;
		newChild.p = n;
		if (root == n) root = child;
	}


	public ArrayList<Any> getKeysAscending()
	{
		ArrayList<Any> keys = new ArrayList<Any>(size);
		inOrderTraverse(root, keys, true);
		
		return keys;
	}
	
	public ArrayList<Any> getKeysDescending()
	{
		ArrayList<Any> keys = new ArrayList<Any>(size);
		inOrderTraverse(root, keys, false);
		
		return keys;
	}

	private void inOrderTraverse(Node n, ArrayList<Any> list, boolean ascending)
	{
		if (n == nil) return;
		if (ascending)
		{
			inOrderTraverse(n.left, list, ascending);
			list.add(n.key);
			inOrderTraverse(n.right, list, ascending);
		}
		else
		{
			inOrderTraverse(n.right, list, ascending);
			list.add(n.key);
			inOrderTraverse(n.left, list, ascending);
			
		}
	}


	public void printTree()
	{
		ArrayList<Any> keys = getKeysAscending();
		for (Any i: keys)
		{
			System.out.print(i + " ");
		}
		System.out.println();
	}



	//Methods that won't be used.

	protected Node delete(Node n, Any key){return null;}
	protected Node insert(Node n, Any key){return null;}
	protected void update(Node n){}
	protected Node rotateLeft(Node n){return null;}
	protected Node rotateRight(Node n){return null;}
}
