package estruturas;

import java.util.function.*;

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

  // Muda o tamanho do vetor para o valor especificado.
  //
  // Se o novo tamanho for menor que o anterior, os elementos em posições após a
  // anterior última posição do vetor são removidos.
  //
  // Se o novo tamanho for maior que o anterior, as posições após a anterior
  // última posição do vetor são todas preenchidas `null`.
  public void resize(int n) {
    reserve(n);
    size = n;
  }

  // Adiciona um novo elemento ao fim do vetor e expande-o se necessário
  public void push(T value) {
    if(size + 1 > capacity)
      expand();

    data[size++] = value;
  }

  // Insere um novo elemento no índice especificado e desvia todos os elementos
  // à direita para frente. Se o índice passado for o tamanho atual do vetor,
  // tem comportamento equivalente a 'push(...)' - adiciona elemento ao fim.
  public void insert(int idx, T value) {
    assert idx >= 0 && idx <= size : "Tentou-se inserir um elemento em uma"
                                     + "posição inválida do vetor";

    if(size + 1 > capacity)
      expand();

    for(int i = size; i > idx; i--)
      data[i] = data[i-1];
    
    data[idx] = value;
    size++;
  }

  public void remove(int idx) {
    assert idx >= 0 && idx < size : "Tentou-se remover um elemento fora dos"
                                    + " limites do vetor";

    for(int i = idx + 1; i < size; i++)
      data[i-1] = data[i];

    size--;
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

  // Atribui um valor a uma posição do vetor pelo índice
  //
  // NÃO deve ser utilizado se esse índice passar dos atuais
  // limites do vetor
  public void setAt(int i, T value) {
    assert i >= 0 && i < size : "Tentou-se acessar um elemento fora dos"
                                + " limites do vetor";
    data[i] = value;
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
  // da iteração. Ela deve retornar `true` caso a iteração
  // deva continuar, ou `false` para interrompé-la.
  @SuppressWarnings("unchecked")
  public void forEach(Predicate<T> predicate) {
    for(int i = 0; i < size; i++)
      if(!predicate.test((T)data[i]))
        break;
  }

  // Passa por todos os elementos atuais no vetor e aplica
  // uma função de callback (passada por parâmetro)
  //
  // 'callback' é uma função que recebe o índice atual da iteração e o
  // elemento nesse índice. Ela deve retornar `true` caso a iteração
  // deva continuar, ou `false` para interrompé-la.
  @SuppressWarnings("unchecked")
  public void forEach(BiPredicate<Integer, T> predicate) {
    for(int i = 0; i < size; i++)
      if(!predicate.test(i, (T)data[i]))
        break;
  }

  // Passa por todos os elementos atuais no vetor e aplica
  // uma função de callback (passada por parâmetro)
  //
  // 'callback' é uma função que recebe o elemento atual
  // da iteração
  public void forEach(Consumer<T> callback) {
    forEach((var x) -> {
      callback.accept(x);
      return true;
    });
  }

  // Passa por todos os elementos atuais no vetor e aplica
  // uma função de callback (passada por parâmetro)
  //
  // 'callback' é uma função que recebe o índice atual da iteração e o
  // elemento nesse índice
  @SuppressWarnings("unchecked")
  public void forEach(BiConsumer<Integer, T> callback) {
    for(int i = 0; i < size; i++)
      callback.accept(i, (T)data[i]);
  }

  // Procura pelo PRIMEIRO elemento no vetor que satifaz uma condição específica
  // (definida pelo predicado passado como argumento). Se um elemento
  // satisfazer a condição, o seu índice é retornado; caso contrário, `-1` é
  // retornado.
  @SuppressWarnings("unchecked")
  public int find(Predicate<T> predicate) {
    for(int i = 0; i < size; i++)
      if(predicate.test((T)data[i]))
        return i;

    return -1;
  }

  // Procura pelo ÚLTIMO elemento no vetor que satisfaz uma condição especifica
  // (definida pelo predicado passado como argumento). Se um elemento
  // satisfazer a condição, o seu índice é retornado; caso contrário, '-1' é
  // retornado.
  @SuppressWarnings("unchecked")
  public int findLast(Predicate<T> predicate) {
    for(int i = size - 1; i >= 0; i--)
      if(predicate.test((T)data[i]))
        return i;

    return -1;
  }
  
  // Preenche todo o vetor com o valor retornado pela
  // função de callback 'valueSupplier'.
  // 
  // Se toda chamada da função de callback retornar o
  // mesmo objeto, o vetor inteiro será preenchido com
  // referências para esse mesmo objeto; tome cuidado.
  public void fill(Supplier<T> valueSupplier) {
    for(int i = 0; i < size; i++)
      data[i] = (Object)valueSupplier.get();
  }

  // Retorna a quantidade atual de elementos no vetor
  public int size() { return size; }

  // Retorna verdadeiro se o vetor estiver vazio,
  // falso caso contrário
  public boolean empty() { return size == 0; }

  // Retorna um novo vetor com os elementos no intervalo [left, right[.
  //
  // Pode ser utilizado seguido de 'forEach(...)' para iterar apenas pelos
  // elementos nesse intervalo especifico.
  @SuppressWarnings("unchecked")
  public Vector<T> sliced(int left, int right) {
    assert left >= 0 && right < size && left <= right : "Intervalo inválido!";

    Vector<T> vec = new Vector<>();
    vec.reserve(size);

    for(int i = left; i < right; i++)
      vec.push((T)data[i]);

    return vec;
  }

  // Retorna um novo vetor com os elementos no intervalo [0, right[
  //
  // Pode ser utilizado seguido de 'forEach(...)' para iterar apenas pelos
  // elementos nesse intervalo especifico.
  public Vector<T> sliced(int right) {
    return sliced(0, right);
  }

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

  // Cria o vetor com a quantidade especificada de elementos e
  // preenche o vetor com os valores fornecidos (por 'valueSupplier')
  public Vector(int n, Supplier<T> valueSupplier) {
    this();
    resize(n);
    fill(valueSupplier);
  } 

  // Cria o vetor com a quantidade especificada de elementos e preenche o vetor
  // com `null`
  public Vector(int n) {
    this(n, () -> null);
  }
}

