class AVLTree<T extends Comparable> {
    Node root;
    class Node<T> {
        int height;
        T item;
        Node left;
        Node right;

        Node(T d) {
            item = d;
            height = 1;
        }
    }

    int height(Node N) {
        if (N == null) {
            return 0;
        } else {
            return N.height;
        }
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node aux = x.right;

        x.right = y;
        y.left = aux;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node aux = y.left;

        y.left = x;
        x.right = aux;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    Node insert(Node node, T key) {
        if (node == null)
            return (new Node(key));

        if (key.compareTo(node.item) < 0)
            node.left = insert(node.left, key);
        else if (key.compareTo(node.item) > 0)
            node.right = insert(node.right, key);
        else
            return node;

        // Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node
        // to check whether this node became unbalanced
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && key.compareTo(node.left.item) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key.compareTo(node.right.item) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key.compareTo(node.left.item) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key.compareTo(node.right.item) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public Node minValueNode(Node node) {
        Node current = node;

        // loop down to find the leftmost leaf
        while (current.left != null)
            current = current.left;

        return current;
    }

    public Node delete(Node root, T key) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller
        // than the root's key, then it lies in
        // left subtree
        if (key.compareTo(root.item)<0)
            root.left = delete(root.left, key);

            // If the key to be deleted is greater
            // than the root's key, then it lies in
            // right subtree
        else if (key.compareTo(root.item)>0)
            root.right = delete(root.right, key);

            // if key is same as root's key, then
            // this is the node to be deleted
        else {
            // node with only one child or no child
            if ((root.left == null) ||
                    (root.right == null)) {
                Node temp = root.left != null ?
                        root.left : root.right;

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp; // Copy the contents of
                // the non-empty child
            } else {
                // node with two children: Get the
                // inorder successor (smallest in
                // the right subtree)
                Node temp = minValueNode(root.right);

                // Copy the inorder successor's
                // data to this node
                root.item = temp.item;

                // Delete the inorder successor
                root.right = delete(root.right, (T)temp.item);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = Math.max(height(root.left),
                height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS
        // NODE (to check whether this node
        // became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then
        // there are 4 cases

        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    /*
    IMPRESSÃO
     */

    public class Stack<T> {
        private Cell head;
        private long size;

        public Stack() {
            size = 0;
            this.head = new Cell(null, null);
        }

        class Cell<T> {
            T item;
            Cell next;

            public Cell(T item, Cell next) {
                this.item = item;
                this.next = next;
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void push(T value) {
            Cell cell = new Cell(value, head);
            head = cell;
            size++;
        }

        public T pop() {
            if (isEmpty()) {
                throw new RuntimeException("empty stack");
            }
            T value = (T) head.item;
            head = head.next;
            size--;
            return value;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Stack<Node> stack = new Stack<>();
        Node current = this.root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            sb.append(current.item);
            sb.append(" ");
            current = current.right;
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
}