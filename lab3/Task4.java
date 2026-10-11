import java.util.*;

public class Task4 {
    public static void main(String[] args) {
        Queue<String> waitlist = new LinkedList<>();

        waitlist.offer("Ivan");
        waitlist.offer("Anna");
        waitlist.offer("Petr");

        System.out.println(waitlist.peek());

        String next = waitlist.poll();
        if (next != null) {
            System.out.println("Processing: " + next);
        }

        System.out.println(waitlist);

        Queue<String> empty = new LinkedList<>();

        System.out.println(empty.poll());
        System.out.println(empty.peek());

        try {
            empty.remove();
        } catch (NoSuchElementException e) {
            System.out.println("Queue is empty");
        }
    }
}
