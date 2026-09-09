import estruturas.Vector;
import processing.core.PApplet;
import ui.Menu;
import ui.Pause;
import ui.SelecaoDeMapa;

public class Sketch extends PApplet {
  public static void main(String[] args) {
    PApplet.main("Sketch");
  }

  private enum Estado {MENU, SELECAO, SIMULACAO, PAUSE};
  private Estado estado_atual; 

  private Menu menu;
  private SelecaoDeMapa selecao;
  private Pause pause;

  private Vector<Paciente> pacientes = new Vector<>();
  
  public void adicionarPaciente(Paciente paciente) {
    assert paciente != null;

    pacientes.push(paciente);
  }

  public void removerPaciente(Paciente paciente) {
    assert paciente != null;

    int idx = pacientes.find((var p) -> p == paciente);

    assert idx != -1;
    pacientes.remove(idx);
  }

  public int quantidadePacientes() {
    return pacientes.size();
  }

  @Override
  public void settings() {
    size(800, 600);
  }
  
  @Override
  public void setup() {
    estado_atual = Estado.MENU;
    
    menu = new Menu(this);
    selecao = new SelecaoDeMapa(this);
    pause = new Pause(this);
  }
  
  @Override
  public void draw() {
    switch (estado_atual) {
      case MENU:
        background(0xFF8DBCC7); // #8DBCC7
        menu.atualiza();

        if(menu.getProximaEtapa())
          estado_atual = Estado.SELECAO;
        
        break;
    
      case SELECAO:
        background(0xFF8DBCC7); // #8DBCC7

        selecao.atualiza();

          if(selecao.getProximaEtapa()){
            new Mapa(this, selecao.getSelecaoMapa());

            estado_atual = Estado.SIMULACAO;

            selecao.setProximaEtapa(false);
          }

        break;

      case PAUSE:
        background(0xFF8DBCC7); // #8DBCC7
        pause.atualiza();

        if(pause.getContinuar()) {
          estado_atual = Estado.SIMULACAO;
        
          pause.setContinuar(false);

          break;
        }
        
        if(pause.getIrSelecao()) {
          estado_atual = Estado.SELECAO;
          pause.setIrSelecao(false);

          break;
        }

        break;

      case SIMULACAO:
        assert Mapa.mapaCarregado() : "Mapa deve estar carregado na fase de"
                                      + " simulação!";
        Mapa.desenharMapaAtual(this);

        if(Mapa.geradorMapaAtual().deveAdicionarPaciente()) {
          Mapa.geradorMapaAtual().adicionarPaciente();
        }

        // Atualiza o atendimento (enfermeiras/médicos terminando
        // consultas e chamando o próximo paciente da fila).
        Mapa.triagemMapaAtual().atualizar();
        Mapa.consultasMapaAtual().atualizar();

        Mapa.triagemMapaAtual().chamarProximoPaciente(this);
        Mapa.consultasMapaAtual().chamarProximoPaciente(this);

        // Move cada paciente um passo em direção ao seu objetivo atual,
        // usando o Wavefront (Paciente.atualizarPosicao()).
        pacientes.forEach((var paciente) -> { paciente.atualizarPosicao(); });
      break;

      default:
        throw new IllegalStateException("Estado atual inválido!"); 
    }
  }

  @Override
  public void keyPressed() {
      switch (this.key) {
        case 'p':
          if(estado_atual == Estado.PAUSE) {
            estado_atual = Estado.SELECAO;
            return;
          }

          estado_atual = Estado.PAUSE;
          break;
      
        default:
          break;
      }
  }
}
