import java.util.Random;

import estruturas.Vector;

public class Paciente {
  private Vector<ObservadorPaciente> observadores;

  public void adicionarObservador(ObservadorPaciente observador) {
    observadores.push(observador);
  }

  // Se o observador não existir, não tem efeito
  public void removerObservador(ObservadorPaciente observador) {
    int i = observadores.find((var ob) -> ob == observador);

    if(i == -1)
      return;

    observadores.remove(i);
  }

  public enum Estado {
    INDO_AO_TOTEM, // Estado inicial
    NO_TOTEM,

    INDO_A_TRIAGEM,
    AGUARDANDO_ATENDIMENTO, // Representa tanto a espera pela chamada da
                            // enfermeira quanto a espera pela chamada do
                            // médico (em ambos o paciente ficará esperando
                            // em um assento)
    INDO_A_ENFERMEIRA,
    EM_ATENDIMENTO_TRIAGEM,

    INDO_AO_MEDICO,
    EM_CONSULTA_MEDICA,

    SAINDO_DO_HOSPITAL, // Estado final
  };

  private PositionDTO posicao, posicaoObjetivo;
  private Estado estado;

  private static final int CHANCE_ATENDIMENTO_PREFERENCIAL = 75;

  private int[] caracteristicasClinicas;

  private Triagem.CorManchester corManchester;
  private boolean atendimentoPreferencial;

  private Assento assentoAtual; // `null` se o paciente não estiver sentado
  
  private String senha;

  public PositionDTO posicao() { return posicao; }

  public PositionDTO posicaoObjetivo() { return posicaoObjetivo; }

  public Estado estado() { return estado; }

  public void atualizarEstado(Estado estado) { this.estado = estado; }

  public void novoObjetivo(PositionDTO posicaoObjetivo) {
    assert posicaoObjetivo != null : "A posição do novo objetivo não pode ser null!";
    this.posicaoObjetivo = posicaoObjetivo;
  }

  public void removerObjetivo() { posicaoObjetivo = null; }

  public PositionDTO atualizarPosicao() {
    if(posicaoObjetivo == null)
      return posicao;

    if(assentoAtual != null && assentoAtual.estado() == Assento.Estado.OCUPADO)
      levantar();


    // Recalcula a onda apenas quando o objetivo mudou desde o último
    // cálculo (evita refazer o BFS do Wavefront todo frame).
    if(onda == null || !posicaoObjetivo.equals(objetivoDaOndaCalculada)) {
      onda = WaveFront.calcularOnda(Mapa.gridAtual(), posicaoObjetivo.y, posicaoObjetivo.x);
      objetivoDaOndaCalculada = posicaoObjetivo;
    }

    PositionDTO proximaPosicao =
      WaveFront.proximoPasso(onda, Mapa.gridAtual(), posicao.y, posicao.x);
    // TODO: Caminhada em direção ao objetivo

    // TODO: Atualizar posição do paciente
    
    if(posicao == posicaoObjetivo) {
      observadores.forEach(
        (var observador) -> { observador.objetivoPacienteAtingido(this); }
      ); 
      removerObjetivo();
    }
    // `proximoPasso` retorna null tanto quando chegamos exatamente no
    // objetivo (ex.: assento, totem, removedor) quanto quando estamos
    // encostados num objetivo que não pode ser pisado (enfermeira/médico)
    // — em ambos os casos, do ponto de vista do paciente, ele "chegou".
    if(proximaPosicao == null) {
      // Se estávamos indo para um assento reservado e chegamos até ele,
      // sentamos automaticamente.
      if(assentoAtual != null && assentoAtual.estado() == Assento.Estado.RESERVADO)
        sentar(assentoAtual);

      observadores.forEach(
        (var observador) -> { observador.objetivoPacienteAtingido(this); }
      );
      removerObjetivo();

      onda = null;
      objetivoDaOndaCalculada = null;

      return posicao;
    }

    posicao = proximaPosicao;
    return posicao;
  }
    
    return posicao;
  }

  public int saturacaoOxigenio() {
    return caracteristicasClinicas[0];
  }

  public int temperaturaCorporal() {
    return caracteristicasClinicas[1];
  }

  public int nivelDor() {
    return caracteristicasClinicas[2];
  }

  public boolean conscienciaAlterada() {
    return caracteristicasClinicas[3] == 1;
  }

  // Retorna `null` se nenhuma cor tiver sido atribuída ao paciente
  public Triagem.CorManchester corManchester() { return corManchester; }

  public void setCorManchester(Triagem.CorManchester corManchester) {
    this.corManchester = corManchester;
  }

  // Retorna verdadeiro (`true`) caso o paciente precise de atendimento preferencial,
  // retorna falso (`false`) caso contrário.
  public boolean atendimentoPreferencial() { return atendimentoPreferencial; }

  public boolean estaSentado() {
    return assentoAtual != null;
  }

  public Assento assentoAtual() {
    return assentoAtual;
  }

  public void irAoAssento(Assento assento) {
    assentoAtual = assento;
    assentoAtual.reservar();
    novoObjetivo(assento.posicao());
  }

  public void sentar(Assento assento) {
    assert assento != null;
    posicao = assentoAtual.posicao();
    assentoAtual = assento;
    assento.ocupar();
  }

  // Direções usadas para procurar uma célula livre adjacente ao assento,
  // na mesma convenção usada no WaveFront (baixo, direita, cima, esquerda).
  private static final int[][] DIRECOES_ADJACENTES = {
    {1, 0}, {0, 1}, {-1, 0}, {0, -1}
  };

  // Procura, entre as 4 células vizinhas de 'origem', a primeira que seja
  // transitável (chão, assento, totem, etc — não parede/enfermeira/médico).
  // Se nenhuma for encontrada (paciente cercado), retorna a própria
  // 'origem' como último recurso.
  private PositionDTO primeiraPosicaoLivreAdjacente(PositionDTO origem) {
    char[][] grid = Mapa.gridAtual();
    int linhas = grid.length;
    int colunas = grid[0].length;

    for(int[] direcao : DIRECOES_ADJACENTES) {
      int ni = origem.y + direcao[0];
      int nj = origem.x + direcao[1];

      if(ni < 0 || ni >= linhas || nj < 0 || nj >= colunas)
        continue;

      if(!WaveFront.passavel(grid, ni, nj))
        continue;

      return new PositionDTO(nj, ni);
    }

    return origem;
  }

  public void levantar() {
    posicao = primeiraPosicaoLivreAdjacente(assentoAtual.posicao());
    assentoAtual.deixarLivre();
    assentoAtual = null;
  }

  public String senha() { return senha; }

  public void atribuirSenha(String senha) {
    assert senha.length() == 5;

    this.senha = senha;
  }

  public Paciente(int x, int y) {
    observadores = new Vector<>();

    posicao = new PositionDTO(x, y);
    caracteristicasClinicas = new int[4];

    estado = Estado.INDO_AO_TOTEM;

    Random rand = new Random();

    atendimentoPreferencial =
      rand.nextInt(1, 100+1) > CHANCE_ATENDIMENTO_PREFERENCIAL;
    corManchester = null;

    int saturacaoOxigenio = rand.nextInt(70, 100+1);
    int temperaturaCorporal = rand.nextInt(34, 42+1);
    int nivelDor = rand.nextInt(0, 10+1);
    boolean conscienciaAlterada = rand.nextBoolean();

    caracteristicasClinicas[0] = saturacaoOxigenio;
    caracteristicasClinicas[1] = temperaturaCorporal;
    caracteristicasClinicas[2] = nivelDor;
    caracteristicasClinicas[3] = conscienciaAlterada ? 1 : 0;

    assentoAtual = null;
    senha = null;
  }

  public Paciente(PositionDTO posicao) {
    this(posicao.x, posicao.y);
  }
}

