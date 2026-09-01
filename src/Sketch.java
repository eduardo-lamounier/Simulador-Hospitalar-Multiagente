import processing.core.PApplet;

import ui.*;

public class Sketch extends PApplet {
  public static void main(String[] args) {
    PApplet.main("Sketch");
  }

  private enum Estado {MENU, SELECAO, SIMULACAO, PAUSE};
  private Estado estado_atual; 

  private Menu menu;
  private SelecaoDeMapa selecao;

  @Override
  public void settings() {
    size(800, 600);
  }

  @Override
  public void setup() {
    estado_atual = Estado.MENU;

    menu = new Menu(this);
    selecao = new SelecaoDeMapa(this);
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
}

