public class Assento {
  private PositionDTO posicao;
  private boolean ocupado;

  public PositionDTO posicao() {
    return posicao;
  }

  public boolean ocupado() {
    return ocupado;
  }

  public void ocupar() {
    ocupado = true;
  }

  public void desocupar() {
    ocupado = false;
  }

  private Assento() {
    ocupado = false;
  }

  public Assento(PositionDTO posicao) {
    this();
    this.posicao = posicao;
  }

  public Assento(int x, int y) {
    this();
    posicao = new PositionDTO(x, y);
  }
}
