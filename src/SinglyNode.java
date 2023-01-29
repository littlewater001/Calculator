public class SinglyNode<T> {
    public T data;
    public SinglyNode<T> next;

    public SinglyNode(T data, SinglyNode<T> next) {
        this.data = data;
        this.next = next;
    }

    public SinglyNode(T data) {
        this(data,null);
    }

    public SinglyNode() {
        this(null, null);
    }
}