import java.util.function.Consumer;

public class Vector<T> {
  private static final int STARTING_CAPACITY = 1;
  private static final double RESIZE_FACTOR = 2.0;

  private Object[] data;
  private int size;
  private int capacity;

  private void expand() { reserve((int)(capacity * RESIZE_FACTOR)); }

  public void reserve(int new_capacity) {
    if(new_capacity <= capacity)
      return;

    capacity = new_capacity;
    var old_data = data;
    data = new Object[new_capacity];

    for(int i = 0; i < size; i++)
      data[i] = old_data[i];
  }

  public void push(T value) {
    if(size + 1 > capacity)
      expand();

    data[size++] = value;
  }

  public void pop() {
    assert size > 0 : "Tentou-se remover um elemento de um vetor vazio";
    size--;
  }

  @SuppressWarnings("unchecked")
  public T at(int i) {
    assert i >= 0 && i < size : "Tentou-se acessar um elemento fora dos"
                                + " limites do vetor";
    return (T)data[i];
  }

  public T first() { return at(0); }

  public T last() { return at(size - 1); }

  @SuppressWarnings("unchecked")
  public void forEach(Consumer<T> callback) {
    for(int i = 0; i < size; i++)
      callback.accept((T)data[i]);
  }
  
  public void fill(T value) {
    for(int i = 0; i < size; i++)
      data[i] = (Object)value;
  }

  public int size() { return size; }
  public boolean empty() { return size == 0; }

  public static <U> Vector<U> from(Vector<U> values) {
    Vector<U> new_vector = new Vector<U>();
    new_vector.reserve(values.size());

    values.forEach((U value) -> {
      new_vector.push(value);
    });

    return new_vector;
  }

  @SafeVarargs
  public static <U> Vector<U> from(U... values) {
    Vector<U> new_vector = new Vector<U>();
    new_vector.reserve(values.length);

    for(U value : values)
      new_vector.push(value);

    return new_vector;
  }
  
  public Vector() {
    capacity = STARTING_CAPACITY;
    data = new Object[capacity];
    size = 0;
  }

  public Vector(int n, T value) {
    reserve(n);
    size = n;
    fill(value);
  } 
}

