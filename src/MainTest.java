import datastructures.CustomStack;
import datastructures.CustomQueue;
import datastructures.CustomLinkedList;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("--- Testing Custom Stack (LIFO) ---");
        CustomStack<String> gameActions = new CustomStack<>();
        gameActions.push("Move Player Up");
        gameActions.push("Player Attack!");
        System.out.println("Undoing action: " + gameActions.pop());

        System.out.println("\n--- Testing Custom Queue (FIFO) ---");
        CustomQueue<String> pathWay = new CustomQueue<>();
        pathWay.enqueue("Tile (0,0)");
        pathWay.enqueue("Tile (0,1)");
        System.out.println("Player stepped onto: " + pathWay.dequeue());

        System.out.println("\n--- Testing Custom LinkedList ---");
        CustomLinkedList<String> entities = new CustomLinkedList<>();
        entities.add("Player 1");
        entities.add("Asteroid A");
        entities.add("Asteroid B");

        System.out.println("Total entities: " + entities.size());
        System.out.println("Entity at index 1: " + entities.get(1));

        // Remove Asteroid A
        entities.remove("Asteroid A");
        System.out.println("New entity at index 1 after removal: " + entities.get(1));
        System.out.println("New total entities: " + entities.size());
    }
}