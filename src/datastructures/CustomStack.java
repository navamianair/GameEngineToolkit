package datastructures;

public class CustomStack<T> {

    // A Node is a small container that holds 1 piece of data and points to the next container
    private class Node {
        T data;      // The item stored
        Node next;   // Pointer to the item below it

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top; // Pointer to the top plate in the stack
    private int size; // Keeps track of total items

    public CustomStack() {
        this.top = null;
        this.size = 0;
    }

    // PUSH: Put a new item on top of the stack
    public void push(T data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    // POP: Remove and return the top item
    public T pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty! Nothing to pop.");
            return null;
        }
        T value = top.data;
        top = top.next; // Move top pointer to the next item down
        size--;
        return value;
    }

    // PEEK: Look at the top item without removing it
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }

    // Check if stack has no items
    public boolean isEmpty() {
        return top == null;
    }

    // Get current total items
    public int size() {
        return size;
    }
}