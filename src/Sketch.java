import processing.core.PApplet;

import estruturas.Vector;
import ui.*;

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
        background(0xFF8DBCC7);
        menu.atualiza();

        if(menu.getIrSelecao())
          estado_atual = Estado.SELECAO;
        
        break;
    
      case SELECAO:
        background(0xFF8DBCC7);
        selecao.atualiza();
        break;

      case PAUSE:
        background(0xFF8DBCC7);
        pause.atualiza();
        break;

      case SIMULACAO:
        assert Mapa.mapaCarregado() : "Mapa deve estar carregado na fase de"
                                      + " simulação!";
        Mapa.desenharMapaAtual(this);
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
            estado_atual = Estado.MENU;
            return;
          }

          estado_atual = Estado.PAUSE;
          break;
      
        default:
          break;
      }
  }
}

