public class SinglyList<T> {
    public SinglyNode<T> head;

    public SinglyList() {
        this.head = new SinglyNode<>();
    }

    public SinglyList(T[] values) {
        this();
        SinglyNode<T> rear = this.head;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                rear.next = new SinglyNode<>(values[i]);
                rear = rear.next;
            }
        }
    }

    public SinglyList(SinglyList<T> list) {
        this();
        SinglyNode<T> rear = this.head, node = list.head;
        while (node.next != null) {
            node = node.next;
            rear.next = new SinglyNode<>(node.data);
            rear = rear.next;
        }
    }

    private SinglyNode<T> search(T key) {
        SinglyNode<T> q = this.head.next;
        while (q != null) {
            if (q.data.equals(key)) {
                return q;
            }
            q = q.next;
        }
        return null;
    }

    public SinglyList<T> getlist() {
        return this;
    }

    public static <T> void reverse(SinglyList<T> list) {
        SinglyNode<T> rear = list.head, node = null;
        SinglyList<T> temp = new SinglyList<>();
        while (rear.next != null) {
            rear = rear.next;
            node = new SinglyNode<>(rear.data, temp.head.next);
            temp.head.next = node;
        }
        list.head.next = node;
    }

    public boolean isEmpty() {
        return this.head.next == null;
    }

    @Override
    public String toString() {
        String a = "";
        SinglyNode<T> rear = this.head.next;
        while (rear != null) {
            if (rear.data != null) {
                a += rear.data + ",";
                rear = rear.next;
            }
        }
        return a.equals("") ? a : a.substring(0, a.length() - 1);
    }

    public static void main(String[] args) {
        SinglyList<Integer> a = new SinglyList<>(new Integer[]{1, 2, 3, 4, 5, 6});
        reverse(a);
        System.out.println(a);
    }

    public void insert(T x) { //头插法
        if (x == null) return;
        SinglyNode<T> front = this.head;
        front.next = new SinglyNode<>(x, front.next);
    }

    public T get(int i) {
        SinglyNode<T> p = this.head.next;
        for (int j = 0; p != null && j < i; j++) {
            p = p.next;
        }
        return (i >= 0 && p != null) ? p.data : null;
    }

    public SinglyList<T> remove(int i, int n) {
        if (n <= 0 || i < 0) return new SinglyList<>();
        SinglyNode<T> front = this.head;
        SinglyNode<T> behind;
        for (int j = 1; j < i && front != null; j++) front = front.next;
        if (front == null) return new SinglyList<>();
        else behind = front;
        for (int j = 0; j <= n && behind != null; j++) behind = behind.next;
        front.next = behind;
        return this;
    }

    public T remove(int i) {
        SinglyNode<T> front = this.head;
        for (int j = 0; front.next != null && j < i; j++) {
            front = front.next;
        }
        if (i >= 0 && front.next != null) {
            T x = front.next.data;
            front.next = front.next.next;
            return x;
        }
        return null;
    }

    public void clear() {
        this.head.next = null;
    }
}
