package estruturas;

// Uma lista (implementada como uma lista encadeada) para todos os tipos
public class List<T> {
  private class Node {
    public T value;
    public Node next;
    public Node prev;
  }

  private Node head;
  private Node tail;
  private int count;

  private Node nodeAt(int i) {
    if(i < 0) return null;

    Node current = head;
    for(int j = 0; j <= i && current != null; j++) 
      current = current.next;
    
    return current;
  }
 
  public void add(T value) { }

  public void insert(T value, int i) { }
 
  public void remove(int i) {
    Node node = nodeAt(i);

    assert node != null : "Tentou-se acessar um elemento inexistente na lista";

    if(node.prev != null)
      node.prev.next = node.next;
    if(node.next != null)
      node.next.prev = node.prev;

    count--;
  }

  // Retorna o elemento no índice especificado
  //
  // Se não existir elemento algum no índice 'i',
  // retorna `null`
  public T get(int i) {
    Node node = nodeAt(i);

    assert node != null : "Tentou-se acessar um elemento inexistente na lista";
    return node.value;
  }
  public int count() { return count; }
  public boolean empty() { return count == 0; }

  public List() {
    head = null;
    tail = null;
  }
}

