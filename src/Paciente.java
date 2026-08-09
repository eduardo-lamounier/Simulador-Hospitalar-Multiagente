import java.util.Random;

public class Paciente {
  public enum Estado {
    INDO_AO_TOTEM, // Estado inicial
    NO_TOTEM,

    INDO_A_TRIAGEM,
    AGUARDANDO_ATENDIMENTO, // Representa tanto a espera pela chamada da
                            // enfermeira quanto a espera pela chamada do
                            // médico (em ambos o paciente ficará esperando
                            // em um assento)
    INDO_A_ENFERMEIRA,
    EM_ATENDIMENTO_TRIAGEM,

    INDO_AO_MEDICO,
    EM_CONSULTA_MEDICA,

    SAINDO_DO_HOSPITAL, // Estado final
  };

  private Estado estado;

  private static final int CHANCE_ATENDIMENTO_PREFERENCIAL = 75;

  private int[] caracteristicasClinicas;

  private Triagem.CorManchester corManchester;
  private boolean atendimentoPreferencial;

  public Estado estado() { return estado; }

  public void atualizarEstado(Estado estado) { this.estado = estado; }

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

  // Retorna `null` se nenhuma cor tiver sido atribuída ao paciente
  public Triagem.CorManchester corManchester() { return corManchester; }

  public void setCorManchester(Triagem.CorManchester corManchester) {
    this.corManchester = corManchester;
  }

  // Retorna verdadeiro (`true`) caso o paciente precise de atendimento preferencial,
  // retorna falso (`false`) caso contrário.
  public boolean atendimentoPreferencial() { return atendimentoPreferencial; }

  public Paciente() {
    caracteristicasClinicas = new int[4];

    estado = Estado.INDO_AO_TOTEM;

    Random rand = new Random();

    atendimentoPreferencial =
      rand.nextInt(1, 100+1) > CHANCE_ATENDIMENTO_PREFERENCIAL;
    corManchester = null;

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

