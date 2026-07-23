import java.util.Random;

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

  public Paciente() {
    caracteristicasClinicas = new int[4];

    Random rand = new Random();

    int saturacaoOxigenio = rand.nextInt(70, 100+1);
    int temperaturaCorporal = rand.nextInt(34, 42+1);
    int nivelDor = rand.nextInt(0, 10+1);
    boolean conscienciaAlterada = rand.nextBoolean();

    caracteristicasClinicas[0] = saturacaoOxigenio;
    caracteristicasClinicas[1] = temperaturaCorporal;
    caracteristicasClinicas[2] = nivelDor;
    caracteristicasClinicas[3] = conscienciaAlterada ? 1 : 0;
  }
}

