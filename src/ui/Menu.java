package ui;

import processing.core.PApplet;

public class Menu{
    /* 
    ToDo- 
    
    Desenhar o menu:
        Tela inicial
        Seleção da mapa
    */

    // Atributos comuns do PApplet
    private PApplet p; // Instância da sketch principal
    private int width; // Largura do sketch 
    private int height; // Altura do sketch

    // Botão de jogar:
    private Botao sair;
    private Botao escolher_mapa;
    private float jogar_l, jogar_h; // largura e altura
    private float jogar_x, jogar_y; // Posição x e y 

    public Menu(PApplet sketch) {
        this.p = sketch; // Mesma skecth da classe Skecth
        width = sketch.width;
        height = sketch.height;

        escolher_mapa = new Botao(sketch, width/5, height/3, 3 * width/5, height/4, 0xFFC4E1E6,
            () -> {
                
            });

        sair = new Botao(sketch, width/5, 2* height/3, 3 * width/5, height/6, 0xFFC4E1E6,
            () -> {
                System.out.println("Finalizando o programa...");
                sketch.exit();
            });

        
    }

    public void desenha() {
        // Botão de jogar        
        escolher_mapa.desenha();
        sair.desenha();
    }
}
