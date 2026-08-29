import processing.core.PApplet;

// Um(a) atendente é responsável pelo atendimento de um paciente em algum
// momento da simulação. Cada atendente só pode atender um paciente por
// vez, e o paciente deve deslocar-se até o(a) atendente (que
// fica em uma posição fixa) para ser atendido.
//
// Abstrai o ciclo presente em enfermeiras e médicos de:
// chamar paciente
// -> esperar chegada
// -> atender paciente
// -> finalizar e esperar pelo próximo.
//
// É extendida e implementada pelos médicos e enfermeiras.
public abstract class Atendente implements ObservadorPaciente {
  protected PApplet sketch;
  protected PositionDTO posicao;

  protected boolean esperandoChegadaPaciente;
  protected Paciente pacienteEmAtendimento;
  protected int ultimoAtendimento;
  protected double ultimoTempoAtendimento;

  public Atendente(int x, int y, PApplet sketch) {
    this.sketch = sketch;
    this.posicao = new PositionDTO(x, y);
  }

  // Gera o tempo que o atendimento vai demorar para um paciente.
  protected abstract double gerarTempoAtendimento();

  // Retorna o estado que vai ser settado no paciente no momento
  // que ele estiver indo em direção ao(à) atendente.
  protected abstract Paciente.Estado estadoIdaPaciente();

  // Retorna o estado que vai ser settado no paciente no momento
  // que ele estiver sendo atendido.
  protected abstract Paciente.Estado estadoAtendimentoPaciente();

  // Faz a ação do atendimento.
  protected abstract void finalizarAtendimento(Paciente paciente);

  public boolean estaLivre() {
    return !esperandoChegadaPaciente && pacienteEmAtendimento == null;
  }

  // Muda o estado do paciente para fazé-lo vir até o(a) atendente.
  //
  // O(a) atendente deve estar livre para realizar essa ação, isso porque ela
  // já ocupa o(a) atendente.
  public void chamarPaciente(Paciente paciente) {
    assert(estaLivre());

    esperandoChegadaPaciente = true;
    paciente.adicionarObservador(this);

    paciente.atualizarEstado(estadoIdaPaciente());
    // Atualiza deslocamento do paciente, fazendo ele vir até o(a) atendente:
    paciente.novoObjetivo(posicao);
  }

  // Muda o estado do paciente para fazé-lo esperar pelo fim do atendimento.
  //
  // O(A) atendente deve estar livre para realizar essa ação, já que ela
  // própria ocupa o(a) atendente.
  private void iniciarAtendimento(Paciente paciente) {
    pacienteEmAtendimento = paciente;
    ultimoAtendimento = sketch.millis();
    ultimoTempoAtendimento = gerarTempoAtendimento();

    paciente.atualizarEstado(estadoAtendimentoPaciente());
  }

  @Override
  public void objetivoPacienteAtingido(Paciente paciente) {
    esperandoChegadaPaciente = false;
    paciente.removerObservador(this);
    iniciarAtendimento(paciente);
  }

  // Atualiza o estado do(a) atendente. Deve ser chamado todo frame.
  public void atualizar() {
    if (pacienteEmAtendimento == null)
      return;

    if (sketch.millis() >= ultimoAtendimento + ultimoTempoAtendimento) {
      finalizarAtendimento(pacienteEmAtendimento);
      pacienteEmAtendimento = null;
    }
  }
}

