package estruturas;

// Um hash map não-ordenado definido para qualquer par (chave, valor).
//
// É implementado com uma hash table e open adressing.
// 
// Mantém o load factor entre 50% e 70%.
//
// NOTE: NÃO permite `null` como chave.
public class Map<K, V> {
  private class Entry {
    public K key;
    public V value;
    public boolean removed = false;

    public Entry(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  private static final int STARTING_CAPACITY = 16; // Internamente, é o TAMANHO
                                                   // (não capacidade) do vetor
                                                   // 'data'
  private static final double HIGH_LOAD_FACTOR = 0.7;
  private static final double LOW_LOAD_FACTOR = 0.5;

  private Vector<Entry> data;
  private int used = 0;
  private int count = 0;

  private int hash(K key) {
    assert key != null;
    int n = data.size();
    return ((key.hashCode() % n) + n) % n;
  }

  private int findPositionForKey(K key) {
    assert key != null;
    int begin = hash(key) % data.size();

    for(int i = 0; i < data.size(); i++) {
      int idx = (begin + i) % data.size();

      var entry = data.at(idx);
      
      if(entry == null)
        return idx;

      if(!entry.removed && entry.key.equals(key)) {
        return idx;
      }
    }

    assert false;
    return -1;
  }

  private void rehash() {
    Vector<Entry> temp = data;

    int newSize = Math.max(
      STARTING_CAPACITY,
      (int)Math.ceil(count / LOW_LOAD_FACTOR)
    );
    data = new Vector<>(newSize);
    used = 0;

    temp.forEach((var i, var entry) -> {
      if (entry != null && !entry.removed) {
        int idx = findPositionForKey(entry.key);
        data.setAt(idx, new Entry(entry.key, entry.value));
        used++;
      }
    });
  }

  // Acessa o valor atribuído a uma chave.
  //
  // Se a chave não tiver sido adicionada, retorna `null`.
  public V get(K key) {
    assert key != null;
    int idx = findPositionForKey(key);
    var entry = data.at(idx);

    return entry != null ? entry.value : null;
  }

  // Retorna `true` se a chave especificada tiver sido atribuída a algum valor,
  // retorna `false` caso contrário
  public boolean contains(K key) {
    int idx = findPositionForKey(key);

    var entry = data.at(idx);
    return entry != null && !entry.removed;
  }

  // Adiciona um novo par (chave, valor).
  //
  // Se a chave especificada já tiver sido atribuída a um valor, o valor é
  // atualizado e é retornado `false`. Caso o par seja novo, é retornado
  // `true`.
  public boolean put(K key, V value) {
    assert key != null;
    if((double)used / data.size() > HIGH_LOAD_FACTOR)
      rehash();
   
    int idx = findPositionForKey(key);

    if(data.at(idx) == null) {
      count++;
      used++;

      data.setAt(idx, new Entry(key, value));
      return true;
    }

    data.at(idx).value = value;
    return false;
  }

  // Remove a atribuição feita a uma chave.
  //
  // Retorna `true` se uma remoção foi feita, `false` se a chave já não tinha
  // sido atribuída a valor nenhum.
  public boolean remove(K key) {
    assert key != null;
    int idx = findPositionForKey(key);

    var entry = data.at(idx);
    if(entry == null || entry.removed)
      return false;

    entry.removed = true;
    count--;
    return true;
  }

  // Retorna a quantidade de pares (chave, valor) criados.
  public int count() {
    return count;
  }

  // Retorna se pelo menos um par (chave, valor) foi criado.
  public boolean empty() {
    return count == 0;
  }

  public Map() {
    data = new Vector<>(STARTING_CAPACITY);
  }
}
