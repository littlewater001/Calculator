import java.util.Stack;

public class LinkedStack<T> extends Stack<T> {
    private SinglyList<T> list;

    public LinkedStack() {
        this.list = new SinglyList<>();
    }

    public LinkedStack(LinkedStack<T> stack) {
        this.list = new SinglyList<>(stack.list.getlist());
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public T push(T x) {
        this.list.insert(x);
        return null;
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public String toString() {
        LinkedStack<T> s = new LinkedStack<>(this);
        StringBuilder string = new StringBuilder();
        while (!s.isEmpty()) {
            if (string.toString().isEmpty()) string.append(s.pop());
            else string.append(",").append(s.pop());
        }

        return string.toString();
    }

    @Override
    public T peek() {
        return this.list.get(0);
    }

    @Override
    public T pop() {
        return this.list.remove(0);
    }
}
