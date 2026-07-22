package estruturas;

import java.util.function.Consumer;

// Um array dinâmico implementado para todos os tipos
public class Vector<T> {
  private static final int STARTING_CAPACITY = 1;
  private static final double RESIZE_FACTOR = 2.0;

  private Object[] data;
  private int size;
  private int capacity;

  // Expande o vetor de acordo com o fator definido
  private void expand() { reserve((int)(capacity * RESIZE_FACTOR)); }

  // Garante que o vetor tem uma certa quantidade ('n') de elementos
  // alocados na memória.
  //
  // Se o vetor já tiver com 'n' elementos alocados, nada acontece
  public void reserve(int n) {
    if(n <= capacity)
      return;

    capacity = n;
    var old_data = data;
    data = new Object[n];

    for(int i = 0; i < size; i++)
      data[i] = old_data[i];
  }

  // Adiciona um novo elemento ao fim do vetor e expande-o se necessário
  public void push(T value) {
    if(size + 1 > capacity)
      expand();

    data[size++] = value;
  }

  // Remove o último elemento do vetor
  //
  // NÃO deve ser utilizado se o vetor estiver vazio
  public void pop() {
    assert !empty() : "Tentou-se remover um elemento de um vetor vazio";
    size--;
  }

  // Acessa um elemento do vetor pelo índice
  //
  // NÃO deve ser utilizado se esse índice passar dos atuais
  // limites do vetor
  @SuppressWarnings("unchecked")
  public T at(int i) {
    assert i >= 0 && i < size : "Tentou-se acessar um elemento fora dos"
                                + " limites do vetor";
    return (T)data[i];
  }

  // Retorna o primeiro elemento do vetor
  //
  // NÃO deve ser utilizado se o vetor estiver vazio
  public T first() { return at(0); }

  // Retorna o último elemento do vetor
  //
  // NÃO deve ser utilizado se o vetor estiver vazio
  public T last() { return at(size - 1); }

  // Passa por todos os elementos atuais no vetor e aplica
  // uma função de callback (passada por parâmetro)
  //
  // 'callback' é uma função que recebe o elemento atual
  // da iteração
  @SuppressWarnings("unchecked")
  public void forEach(Consumer<T> callback) {
    for(int i = 0; i < size; i++)
      callback.accept((T)data[i]);
  }
  
  // Preenche todo o vetor com o valor especificado
  public void fill(T value) {
    for(int i = 0; i < size; i++)
      data[i] = (Object)value;
  }

  // Retorna a quantidade atual de elementos no vetor
  public int size() { return size; }

  // Retorna verdadeiro se o vetor estiver vazio,
  // falso caso contrário
  public boolean empty() { return size == 0; }

  // Inicializa e retorna um vetor de acordo com os valores
  // especificados
  //
  // Todos os valores de 'values' vão ser copiados para o novo
  // vetor
  public static <U> Vector<U> from(Vector<U> values) {
    Vector<U> new_vector = new Vector<U>();
    new_vector.reserve(values.size());

    values.forEach((U value) -> {
      new_vector.push(value);
    });

    return new_vector;
  }

  // Inicializa e retorna um vetor de acordo com os valores
  // especificados
  //
  // Todos os valores de 'values' vão ser copiados para o
  // novo vetor
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

