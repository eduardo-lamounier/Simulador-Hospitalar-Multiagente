import processing.core.PApplet;

public class Gerador {
  private Sketch sketch;
  private PositionDTO posicao;

  private Totem totem;

  private boolean haTempoProximoSpawn = false;
  private float tempoProximoSpawn = 0.0f;
  
  public PositionDTO posicao() { return posicao; }

  private void calcularTempoProximoSpawn() {
    final float mediaSpawn = 5.0f;
    final float u = sketch.random(0, 1);
    tempoProximoSpawn = sketch.millis() -mediaSpawn * PApplet.log(1 - u);
    haTempoProximoSpawn = true;
  }

  public boolean deveAdicionarPaciente() {
    if(haTempoProximoSpawn) {
      boolean deveAdicionarPaciente = sketch.millis() >= tempoProximoSpawn;
      haTempoProximoSpawn = false;
      return deveAdicionarPaciente;
    }
     
    calcularTempoProximoSpawn();
    return false;
  }

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
