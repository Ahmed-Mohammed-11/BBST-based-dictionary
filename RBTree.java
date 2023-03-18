public class RBTree<Any extends Comparable<Any>> extends Tree<Any>
{
	RBNode nil;
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
		nil = new RBNode(null);
		root = nil;
	}

	//Missing implementation
	boolean insert(Any key)
	{
		RBNode current = (RBNode)root;
		if (current == nil)
		{
			current = new RBNode(key, true);
			current.p = nil;
			current.right = nil;
			current.left = nil;
			root = current;
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
		return true;
	}

	//Fix Red black tree properties after insert
	void insertFixUp(RBNode z)
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
		r.black = true;
	}

	//Fix Red black tree properties after delete
	boolean delete(Any key)
	{
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
		if (!current.black) deleteFixUp(current);
		return true;
	}

	void deleteFixUp(RBNode z)
	{

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
	}



	//Methods that won't be used.

	Node delete(Node n, Any key){return null;}
	Node insert(Node n, Any key){return null;}
	void update(Node n){}
}
