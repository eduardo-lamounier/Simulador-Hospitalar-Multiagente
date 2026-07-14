public class Queue<T> {
  private Vector<T> data;
  private int begin;

  public T front() {
    assert !empty() : "Tentou-se acessar um elemento de uma fila vazia";
    return data.at(begin);
  }

  public T back() {
    assert !empty() : "Tentou-se acessar um elemento de uma fila vazia";
    return data.last();
  }

  public void enqueue(T value) { data.push(value); }

  public void dequeue() {
    assert !empty() : "Tentou-se remover um elemento de uma lista vazia";
    begin++;
  }

  public int count() {
    return data.size() - begin;
  }

  public boolean empty() { return count() == 0; }

  @SafeVarargs
  public static <U> Queue<U> from(U... values) {
    Queue<U> new_queue = new Queue<U>();

    new_queue.data = Vector.from(values);

    return new_queue;
  }

  public Queue() {
    begin = 0;
    data = new Vector<T>();
  }
}

