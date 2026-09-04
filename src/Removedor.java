public class Removedor implements ObservadorPaciente {
  private Sketch sketch;
  private PositionDTO posicao;

  public PositionDTO posicao() { return posicao; }

  public void removerPaciente(Paciente paciente) {
    sketch.removerPaciente(paciente); 
  }

  // Removerá o paciente da simulação quando ele chegar no removedor
  public void objetivoPacienteAtingido(Paciente paciente) {
    removerPaciente(paciente);
  }

  public Removedor(Sketch sketch, int x, int y) {
    this.sketch = sketch;
    this.posicao = new PositionDTO(x, y);
  }

  public Removedor(Sketch sketch, PositionDTO posicao) {
    this(sketch, posicao.x, posicao.y);
  }
}
