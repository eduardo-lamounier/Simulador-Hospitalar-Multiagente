public class Assento {
  public enum Estado {
    LIVRE,
    RESERVADO,
    OCUPADO,
  }

  private PositionDTO posicao;
  private Estado estado;

  public PositionDTO posicao() {
    return posicao;
  }

  public Estado estado() {
    return estado;
  }

  public void deixarLivre() {
    estado = Estado.LIVRE;
  }

  public void reservar() {
    estado = Estado.RESERVADO;
  }

  public void ocupar() {
    estado = Estado.OCUPADO;
  }

  private Assento() {
    estado = Estado.LIVRE;
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
