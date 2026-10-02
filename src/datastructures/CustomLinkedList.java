package datastructures;

public class CustomLinkedList<T> {

    // A Node to hold 1 piece of data and a pointer to the next item in the list
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head; // Points to the first node in the list
    private int size;  // Tracks total number of elements

    public CustomLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // ADD: Appends a new item to the end of the list
    public void add(T data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // GET: Retrieves data at a specific index (0, 1, 2...)
    public T get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Index out of bounds!");
            return null;
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // REMOVE: Searches for an item and removes it from the list
    public boolean remove(T data) {
        if (head == null) return false;

        // If the item to remove is the very first node
        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }

        // Search for the node prior to the target item
        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next; // Bypass the removed node
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}