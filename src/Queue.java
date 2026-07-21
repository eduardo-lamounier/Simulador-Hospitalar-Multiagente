// Uma fila dinâmica implementada para todos os tipos
public class Queue<T> {
  private Vector<T> data;
  private int begin;

  // Garante que a fila tem uma certa quantidade ('n') de elementos
  // alocados na memória
  //
  // Se a fila já tiver com 'n' elementos alocados, nada acontece
  public void reserve(int n) {
    // É necessário reservar 'n' elementos A PARTIR do início
    data.reserve(begin + n);
  }

  // Retorna o primeiro elemento da fila
  //
  // NÃO deve ser utilizado se a fila estiver vazia
  public T front() {
    assert !empty() : "Tentou-se acessar um elemento de uma fila vazia";
    return data.at(begin);
  }

  // Retorna o último elemento da fila
  //
  // NÃO deve ser utilizado se a fila estiver vazia
  public T back() {
    assert !empty() : "Tentou-se acessar um elemento de uma fila vazia";
    return data.last();
  }

  // Adiciona um novo elemento no fim da fila
  public void enqueue(T value) { data.push(value); }

  // Remove o primeiro elemento da fila
  //
  // NÃO deve ser utilizado se a fila estiver vazia
  public void dequeue() {
    assert !empty() : "Tentou-se remover um elemento de uma lista vazia";
    begin++;
  }

  // Retorna a quantidade de elementos atualmente na fila
  public int count() {
    return data.size() - begin;
  }

  // Retorna verdadeiro se a fila estiver vazia, retorna falso
  // caso contrário
  public boolean empty() { return count() == 0; }

  // Cria uma nova fila de acordo com os valores especificados
  //
  // Todos os elementos em 'values' vão ser copiados para a nova
  // fila
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

