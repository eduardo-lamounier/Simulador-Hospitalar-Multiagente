public class Gerador {
  private Sketch sketch;
  private PositionDTO posicao;

  private Totem totem;
  
  public PositionDTO posicao() { return posicao; }

  public void adicionarPaciente() {
    Paciente paciente = new Paciente(posicao);
    sketch.adicionarPaciente(paciente);

    paciente.novoObjetivo(totem.posicao());
    paciente.adicionarObservador(totem);
  }

  public Gerador(Sketch sketch, Totem totem, int x, int y) {
    this.sketch = sketch;
    this.totem = totem;
    this.posicao = new PositionDTO(x, y);
  }

  public Gerador(Sketch sketch, Totem totem, PositionDTO posicao) {
    this(sketch, totem, posicao.x, posicao.y);
  }
}
