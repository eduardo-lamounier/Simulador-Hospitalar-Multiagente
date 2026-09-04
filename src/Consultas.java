import processing.core.PApplet;

import estruturas.*;

public class Consultas {
  private Vector<Assento> assentos;

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
      // TODO: Fazer o paciente sair do hospital
    }
    
    public Medico(int x, int y, PApplet sketch) {
      super(x, y, sketch);
    }
  }

  private int buscarAssentoLivre() {
    return assentos.find((var assento) -> !assento.ocupado());
  }

  public boolean haAssentoLivre() {
    return buscarAssentoLivre() != -1;
  }

  private int buscarMedicoLivre() {
    return medicos.find((var medico) -> medico.estaLivre());
  }

  public void atualizar() {
    // TODO
  }

  public boolean haMedicoLivre() {
    return buscarMedicoLivre() != -1;
  }

  public boolean haPacientesParaAtender() {
    // TODO
    return false;
  }

  public void chamarProximoPaciente(PApplet sketch) {
    // TODO
  }

  public void adicionarPacienteAFila(Paciente paciente) {
    // TODO
  }

  public Consultas(Vector<Medico> medicos) {
    this.medicos = Vector.from(medicos);

    for(var cor : Triagem.CorManchester.values())
      filas.put(cor, new Queue<>());
  }
}
