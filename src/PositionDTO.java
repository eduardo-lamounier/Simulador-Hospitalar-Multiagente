// Representa uma posição (x,y) no grid
public class PositionDTO {
  public int x, y;

  public PositionDTO(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Duas posições são iguais se tiverem as mesmas coordenadas (comparar
  // por '==' compara referência, não valor, o que nunca funciona aqui).
  @Override
  public boolean equals(Object outro) {
    if (this == outro)
      return true;

    if (!(outro instanceof PositionDTO))
      return false;

    PositionDTO p = (PositionDTO) outro;
    return x == p.x && y == p.y;
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}