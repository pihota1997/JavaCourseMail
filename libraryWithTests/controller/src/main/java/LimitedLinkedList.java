import java.util.LinkedList;

public class LimitedLinkedList extends LinkedList<Book> {
    private int capacity;

    public LimitedLinkedList(int capacity) {
        this.capacity = capacity;
        for (int i = 0; i < capacity; i++)
            this.add(i, null);
    }

    @Override
    public boolean add(Book o) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) == null) {
                this.set(i, o);
                return true;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
