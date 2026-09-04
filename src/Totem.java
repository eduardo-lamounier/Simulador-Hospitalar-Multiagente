public class Totem implements ObservadorPaciente {
  private PositionDTO posicao;
  
  private static int pacientesNormais = 0;
  private static int pacientesPreferenciais = 0; 

  private Triagem triagem;
  
  public PositionDTO posicao() { return posicao; }

  public void gerarSenhaPaciente(Paciente paciente) {
    char prefixo = paciente.atendimentoPreferencial() ? 'P' : 'N';
    int x = paciente.atendimentoPreferencial() ?
              pacientesPreferenciais : pacientesNormais;

    String senha = prefixo + String.format("%04d", x);
    paciente.atribuirSenha(senha);

    paciente.irAoAssento(triagem.assentoLivre());
  }

  public void objetivoPacienteAtingido(Paciente paciente) {
    gerarSenhaPaciente(paciente);
  }

  public Totem(Triagem triagem, int x, int y) {
    posicao = new PositionDTO(x, y);
  }

  public Totem(Triagem triagem, PositionDTO posicao) {
    this(triagem, posicao.x, posicao.y);
  }
}
