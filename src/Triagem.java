import processing.core.PApplet;
import java.util.function.Predicate;

import Estruturas.Vector;
import Estruturas.Queue;

public class Triagem {
  public enum CorManchester {
    VERMELHO, // Emergência
    LARANJA,  // Muito urgente
    AMARELO,  // Urgente
    VERDE,    // Pouco urgente
    AZUL      // Não urgente
  }

  private class NoManchester {
    public CorManchester cor;
    public Predicate<Paciente> decisao;

    public boolean ehFolha() { return cor != null; }

    public NoManchester(CorManchester cor, Predicate<Paciente> decisao) {
      assert cor != null && decisao == null
             || cor == null && decisao != null;

      this.cor = cor;
      this.decisao = decisao;
    }
  }

  public static final double TEMPO_ATENDIMENTO_MEDIO = 6 * 1000;
  public static final double TEMPO_ATENDIMENTO_MINIMO = 2 * 1000;
  public static final double DESVIO_TEMPO_ATENDIMENTO = 2 * 1000;

  // Uma enfermeira é a responsável pelo o atendimento de um paciente durante a
  // triagem. Cada enfermeira só pode atender um paciente por vez, e o paciente
  // deve deslocar-se até ela (que fica em uma posição fixa) para ser atendido.
  public class Enfermeira extends Atendente {
    @Override
    protected double gerarTempoAtendimento() {
      return PApplet.max(
        (int)(TEMPO_ATENDIMENTO_MEDIO +
          DESVIO_TEMPO_ATENDIMENTO * sketch.randomGaussian()),
        (int)TEMPO_ATENDIMENTO_MINIMO);
    } 

    @Override
    protected Paciente.Estado estadoIdaPaciente() {
      return Paciente.Estado.INDO_A_ENFERMEIRA;
    }

    @Override
    protected Paciente.Estado estadoAtendimentoPaciente() {
      return Paciente.Estado.EM_ATENDIMENTO_TRIAGEM;
    }

    @Override
    protected void finalizarAtendimento(Paciente paciente) {
      var cor = corPaciente(paciente);
      paciente.setCorManchester(cor); 
    } 

    public Enfermeira(int x, int y, PApplet sketch) {
      super(x, y, sketch);
    }
  }

  private Queue<Paciente> filaNormal;
  private Queue<Paciente> filaPreferencial;

  private int preferenciaisAtendidos = 0; // Contador da quantidade de últimos pacientes na fila
                                          // preferencial que foram atendidos

  private Vector<Enfermeira> enfermeiras;
 

  // Armazena os nós da árvore de decisão, representando a hierarquia
  // através de suas posições no array
  private NoManchester[] arvoreProtocoloManchester;

  // Gera a árvore de decisão do Protocolo de Manchester
  private void gerarArvoreDecisao() {
    arvoreProtocoloManchester = new NoManchester[30+1];

    arvoreProtocoloManchester[0] = new NoManchester(null,
      (var paciente) -> { return paciente.conscienciaAlterada(); }
    );

    arvoreProtocoloManchester[1] = new NoManchester(CorManchester.VERMELHO, null);
    arvoreProtocoloManchester[2] = new NoManchester(null,
      (var paciente) -> { return paciente.saturacaoOxigenio() < 92; }
    );

    arvoreProtocoloManchester[5] = new NoManchester(CorManchester.LARANJA, null);
    arvoreProtocoloManchester[6] = new NoManchester(null,
      (var paciente) -> { return paciente.nivelDor() >= 8; }
    );

    arvoreProtocoloManchester[13] = new NoManchester(CorManchester.AMARELO, null);
    arvoreProtocoloManchester[14] = new NoManchester(null,
      (var paciente) -> { return paciente.temperaturaCorporal() >= 38; }
    );

    arvoreProtocoloManchester[29] = new NoManchester(CorManchester.VERDE, null);
    arvoreProtocoloManchester[30] = new NoManchester(CorManchester.AZUL, null);
  }

  // Implementação recursiva da travesia pela árvore de decisão,
  // retornando - ao chegar em um nó folha - a cor do paciente
  private CorManchester corPaciente(int noAtual, Paciente paciente) {
    if(arvoreProtocoloManchester[noAtual].ehFolha())
      return arvoreProtocoloManchester[noAtual].cor;

    if(arvoreProtocoloManchester[noAtual].decisao.test(paciente))
      return corPaciente(noAtual * 2 + 1, paciente);
    
    return corPaciente(noAtual * 2 + 2, paciente);
  }

  // Busca uma enfermeira não ocupada.
  //
  // Retorna -1 se não existir nenhuma enfermeira livre,
  // ou o seu índice da primeira enfermeira livre caso essa exista.
  private int buscarEnfermeiraLivre(PApplet sketch) {
    return enfermeiras.find(
      (var enfermeira) -> enfermeira.estaLivre()
    );
  }

  // Atualiza o estado da triagem. Deve ser utilizado antes de chamar métodos
  // como 'haEnfermeiraLivre' ou 'chamarProximoPaciente'.
  public void atualizar() {
    enfermeiras.forEach((var enfermeira) -> enfermeira.atualizar());
  }

  // Avalia o estado do paciente de acordo com o Protocolo de Manchester,
  // retornando a cor desse paciente
  public CorManchester corPaciente(Paciente paciente) {
    return corPaciente(0, paciente);
  }

  public boolean haEnfermeiraLivre(PApplet sketch) {
    return buscarEnfermeiraLivre(sketch) != -1;
  }

  public boolean pacientesParaAtender() {
    return !filaNormal.empty() || !filaPreferencial.empty();
  }

  // Chama o próximo paciente na fila para ser atendido.
  //
  // As filas não podem estar vazias e alguma enfermeira deve estar livre.
  public void chamarProximoPaciente(PApplet sketch) {
    int enfermeiraLivreIdx = buscarEnfermeiraLivre(sketch);

    assert(pacientesParaAtender() && enfermeiraLivreIdx != -1);

    Enfermeira enfermeira = enfermeiras.at(enfermeiraLivreIdx);
    Paciente paciente;
 
    if((preferenciaisAtendidos < 2 && !filaPreferencial.empty())
        || filaNormal.empty()) {
      preferenciaisAtendidos++;
      paciente = filaPreferencial.front();
      filaPreferencial.dequeue();

      enfermeira.chamarPaciente(paciente);
      return;
    }
    
    preferenciaisAtendidos = 0;
    paciente = filaNormal.front();
    filaNormal.dequeue();
    
    enfermeira.chamarPaciente(paciente);
  }

  // Adiciona o paciente à fila correspondente (dependendo de seu atendimento
  // ser preferencial ou não)
  public void adicionarPacienteAFila(Paciente paciente) {
    paciente.atualizarEstado(Paciente.Estado.AGUARDANDO_ATENDIMENTO);
    if(paciente.atendimentoPreferencial())
      filaPreferencial.enqueue(paciente);
    else
      filaNormal.enqueue(paciente);
  }

  public Triagem(Vector<Enfermeira> enfermeiras) {
    this.enfermeiras = Vector.from(enfermeiras);
    filaNormal = new Queue<>();
    filaPreferencial = new Queue<>();

    gerarArvoreDecisao();
  }
}

