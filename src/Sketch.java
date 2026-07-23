import processing.core.PApplet;

import ui;

public class Sketch extends PApplet {
  public static void main(String[] args) {
    PApplet.main("Sketch");
  }

  private enum Estado {MENU, SIMULACAO, PAUSE};
  private Estado estado_atual; 

  private Menu m;

  @Override
  public void settings() {
    size(800, 600);
  }

  @Override
  public void setup() {
    estado_atual = Estado.MENU;
    m = new Menu(this);
  }

  @Override
  public void draw() {
    

    switch (estado_atual) {
      case MENU:
        background(0xFF8DBCC7);
        m.desenha();
        break;
    
      case PAUSE:
        break;

      case SIMULACAO:
        break;

      default:
        throw new IllegalStateException("Estado atual inválido!"); 
    }
  }
}

