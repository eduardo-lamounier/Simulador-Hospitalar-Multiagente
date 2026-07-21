import processing.core.PApplet;

public class Sketch extends PApplet {
  public static void main(String[] args) {
    PApplet.main("Sketch");
  }

  private enum Estado {MENU, SIMULACAO, PAUSE};
  private Estado estado_atual; 

  @Override
  public void settings() {
    size(800, 600);
  }

  @Override
  public void setup() {
    estado_atual = Estado.MENU;
  }

  @Override
  public void draw() {
    background(255, 255, 255);

    switch (estado_atual) {
      case MENU:
        
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

