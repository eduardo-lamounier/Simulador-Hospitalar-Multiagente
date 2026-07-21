public class Paciente {
  private int[] caracteristicasClinicas;

  public int saturacaoOxigenio() {
    return caracteristicasClinicas[0];
  }

  public int temperaturaCorporal() {
    return caracteristicasClinicas[1];
  }

  public int nivelDor() {
    return caracteristicasClinicas[2];
  }

  public boolean conscienciaAlterada() {
    return caracteristicasClinicas[3] == 1;
  }

  public Paciente() { }
}

