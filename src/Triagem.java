import java.util.function.Predicate;

import estruturas.Queue;

public class Triagem {
  public enum CorManchester {
    VERMELHO, // Emergência
    LARANJA,  // Muito urgente
    AMARELO,  // Urgente
    VERDE,    // Pouco urgente
    AZUL      // Não urgente
  }

  private class NoManchester {
    public boolean ehFolha;
    public CorManchester cor;
    public Predicate<Paciente> decisao;

    public NoManchester(boolean ehFolha, CorManchester cor,
                         Predicate<Paciente> decisao) {
      this.ehFolha = ehFolha;
      this.cor = cor;
      this.decisao = decisao;
    }
  }

  private Queue<Paciente> filaNormal;
  private Queue<Paciente> filaPreferencial;

  private int preferenciaisAtendidos = 0; // Contador da quantidade de últimos pacientes na fila
                                          // preferencial que foram atendidos
 

  // Armazena os nós da árvore de decisão, representando a hierarquia
  // através de suas posições no array
  private NoManchester[] arvoreProtocoloManchester;

  // Gera a árvore de decisão do Protocolo de Manchester
  private void gerarArvoreDecisao() {
    arvoreProtocoloManchester = new NoManchester[30+1];

    arvoreProtocoloManchester[0] = new NoManchester(false, null,
      (var paciente) -> { return paciente.conscienciaAlterada(); }
    );

    arvoreProtocoloManchester[1] = new NoManchester(true,
      CorManchester.VERMELHO, null);
    arvoreProtocoloManchester[2] = new NoManchester(false, null,
      (var paciente) -> { return paciente.saturacaoOxigenio() < 92; }
    );

    arvoreProtocoloManchester[5] = new NoManchester(true,
      CorManchester.LARANJA, null);
    arvoreProtocoloManchester[6] = new NoManchester(false, null,
      (var paciente) -> { return paciente.nivelDor() >= 8; }
    );

    arvoreProtocoloManchester[13] = new NoManchester(true,
      CorManchester.AMARELO, null);
    arvoreProtocoloManchester[14] = new NoManchester(false, null,
      (var paciente) -> { return paciente.temperaturaCorporal() >= 38; }
    );

    arvoreProtocoloManchester[29] = new NoManchester(true,
      CorManchester.VERDE, null);
    arvoreProtocoloManchester[30] = new NoManchester(true,
      CorManchester.AZUL, null);
  }

  // Implementação recursiva da travesia pela árvore de decisão,
  // retornando - ao chegar em um nó folha - a cor do paciente
  private CorManchester corPaciente(int noAtual, Paciente paciente) {
    if(arvoreProtocoloManchester[noAtual].ehFolha)
      return arvoreProtocoloManchester[noAtual].cor;

    if(arvoreProtocoloManchester[noAtual].decisao.test(paciente))
      return corPaciente(noAtual * 2 + 1, paciente);
    
    return corPaciente(noAtual * 2 + 2, paciente);
  }

  // Avalia o estado do paciente de acordo com o Protocolo de Manchester,
  // retornando a cor desse paciente
  public CorManchester corPaciente(Paciente paciente) {
    return corPaciente(0, paciente);
  }

  public boolean pacientesParaAtender() {
    return !filaNormal.empty() || !filaPreferencial.empty();
  }

  private void atenderPaciente(Paciente paciente) {
    var cor = corPaciente(paciente);
    paciente.setCorManchester(cor);
  }

  public Paciente atenderProximoPaciente() {
    assert(pacientesParaAtender());

    Paciente paciente;
 
    if((preferenciaisAtendidos < 2 && !filaPreferencial.empty())
        || filaNormal.empty()) {
      preferenciaisAtendidos++;
      paciente = filaPreferencial.front();
      filaPreferencial.dequeue();

      atenderPaciente(paciente);
      return paciente;
    }
    
    preferenciaisAtendidos = 0;
    paciente = filaNormal.front();
    filaPreferencial.dequeue();
    
    atenderPaciente(paciente); 
    return paciente;
  }

  public void adicionarPacienteAFila(Paciente paciente) {
    if(paciente.atendimentoPreferencial())
      filaPreferencial.enqueue(paciente);
    else
      filaNormal.enqueue(paciente);
  }

  public Triagem() {
    filaNormal = new Queue<>();
    filaPreferencial = new Queue<>();

    gerarArvoreDecisao();
  }
}

