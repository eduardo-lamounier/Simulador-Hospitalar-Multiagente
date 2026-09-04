import processing.core.PApplet;

import estruturas.*;

public class Consultas {
  private Removedor removedor;

  private Map<Triagem.CorManchester, Queue<Paciente>> filas;

  private Vector<Medico> medicos;

  public static final double TEMPO_ATENDIMENTO_MEDIO = 12 * 1000;
  public static final double TEMPO_ATENDIMENTO_MINIMO = 4 * 1000;
  public static final double DESVIO_TEMPO_ATENDIMENTO = 4 * 1000;

  public class Medico extends Atendente {
    @Override
    protected double gerarTempoAtendimento() {
      return PApplet.max(
        (int)(TEMPO_ATENDIMENTO_MEDIO +
          DESVIO_TEMPO_ATENDIMENTO * sketch.randomGaussian()),
        (int)TEMPO_ATENDIMENTO_MINIMO);
    }

    @Override
    protected Paciente.Estado estadoIdaPaciente() {
      return Paciente.Estado.INDO_AO_MEDICO;
    }

    @Override
    protected Paciente.Estado estadoAtendimentoPaciente() {
      return Paciente.Estado.EM_CONSULTA_MEDICA;
    }

    @Override
    protected void finalizarAtendimento(Paciente paciente) {
      paciente.novoObjetivo(removedor.posicao());
      paciente.adicionarObservador(removedor);
    }
    
    public Medico(int x, int y, PApplet sketch) {
      super(x, y, sketch);
    }
  }

  private int buscarMedicoLivre() {
    return medicos.find((var medico) -> medico.estaLivre());
  }

  public void atualizar() {
    medicos.forEach((var medico) -> { medico.atualizar(); });
  }

  public boolean haMedicoLivre() {
    return buscarMedicoLivre() != -1;
  }

  public boolean haPacientesParaAtender() {
    for(var cor : Triagem.CorManchester.values()) {
      var fila = filas.get(cor);
      
      if(!fila.empty())
        return true;
    }

    return false;
  }

  // Chama o paciente com a maior prioridade na fila.
  public void chamarProximoPaciente(PApplet sketch) {
    if(!haMedicoLivre())
      return;

    var medicoLivre = medicos.at(buscarMedicoLivre());

    for(var cor : Triagem.CorManchester.values()) {
      var fila = filas.get(cor);

      if(!fila.empty()) {
        var paciente = fila.front();
        fila.dequeue();

        medicoLivre.chamarPaciente(paciente);
        return;
      }
    }
  }

  public void adicionarPacienteAFila(Paciente paciente) {
    var cor = paciente.corManchester();

    assert cor != null;

    filas.get(cor).enqueue(paciente);
  }

  public Consultas(Vector<Medico> medicos, Removedor removedor) {
    this.medicos = Vector.from(medicos);
    this.removedor = removedor;

    for(var cor : Triagem.CorManchester.values())
      filas.put(cor, new Queue<>());
  }
}
