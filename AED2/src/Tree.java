
import java.util.Random;

public class Tree<T extends Comparable> {
    Node root;

    public Tree() {
        root = null;
    }
    class Node<T extends Comparable> {
        T item;
        Node right;
        Node left;
        public Node(T item) {
            this.item = item;
            left = right = null;
        }
    }

    public void insert(T item) {
        root = insert(root, item);
    }

    public Node insert(Node root, T item) {
        if (root == null) {
            root = new Node(item);
            return root;
        } else {
            Node current = root;
            Node last;
            while (true) {
                last = current;
                if (item.compareTo(last.item) < 0) {
                    current = current.left;
                    if (current == null) {
                        last.left = new Node(item);
                        return root;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        last.right = new Node(item);
                        return root;
                    }
                }
            }
        }
    }

    private void delete(T item) {
        root = delete(root, item);
    }

    private Node delete(Node root, T item) {
        if (root == null) {
            return root;
        }
        if (item.compareTo(root.item) < 0) {
            root.left = delete(root.left, item);
        } else if (item.compareTo(root.item) > 0) {
            root.right = delete(root.right, item);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            Node lower = root.right;
            while (lower.left != null) {
                lower = lower.left;
            }
            root.item = lower.item;
            root.right = delete(root.right, (T) lower.item);
        }
        return root;
    }

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
        sb.append("]");
        return sb.toString();
    }


}
