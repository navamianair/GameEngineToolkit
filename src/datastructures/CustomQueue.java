package datastructures;

public class CustomQueue<T> {

    // A Node to hold 1 piece of data and a pointer to the node behind it
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head; // Points to the FRONT of the queue (for removing/dequeue)
    private Node tail; // Points to the REAR of the queue (for adding/enqueue)
    private int size;

    public CustomQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // ENQUEUE: Add a new item to the back of the line
    public void enqueue(T data) {
        Node newNode = new Node(data);
        if (tail == null) { // If the queue was completely empty
            head = tail = newNode;
        } else {
            tail.next = newNode; // Link old tail to new node
            tail = newNode;      // Move tail pointer to the new node
        }
        size++;
    }

    // DEQUEUE: Remove and return the item from the front of the line
    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty! Nothing to dequeue.");
            return null;
        }
        T value = head.data;
        head = head.next; // Move head pointer to the next node in line
        if (head == null) {
            tail = null;  // If that was the last item, queue is now empty
        }
        size--;
        return value;
    }

    // PEEK: Look at the front item without removing it
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}