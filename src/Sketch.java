import processing.core.PApplet;

import ui.*;

public class Sketch extends PApplet {
  public static void main(String[] args) {
    PApplet.main("Sketch");
  }

  private enum Estado {MENU, SELECAO, SIMULACAO, PAUSE};
  private Estado estado_atual; 

  private Menu menu;
<<<<<<< HEAD
  private SelecaoDeMapa selecao;
=======
  private Pause pause;
>>>>>>> 8d367e6 (feat: Implementa detecção de pressionar uma tecla)

  @Override
  public void settings() {
    size(800, 600);
  }

  @Override
  public void setup() {
    estado_atual = Estado.MENU;

<<<<<<< HEAD
    menu = new Menu(this);
    selecao = new SelecaoDeMapa(this);
=======
    menu = new Menu(this); 
    pause = new Pause(this);
>>>>>>> 8d367e6 (feat: Implementa detecção de pressionar uma tecla)
  }

  @Override
  public void draw() {
    switch (estado_atual) {
      case MENU:
        background(0xFF8DBCC7);
        menu.atualiza();
        break;
    
      case SELECAO:
        background(0xFF8DBCC7);
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
      // TODO Auto-generated method stub

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

