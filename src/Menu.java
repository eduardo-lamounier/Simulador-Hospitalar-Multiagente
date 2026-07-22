import processing.core.PApplet;

public class Menu{
    /* 
    ToDo- 
    
    Desenhar o menu:
        Tela inicial
        Seleção da mapa
    */

    // Atributos comuns do PApplet
    private PApplet p; // Instância da skecth principal
    private int width; // Largura do skecth 
    private int height; // Altura do skecth

    // Botão de jogar:
    private Botao sair;
    private Botao escolher_mapa;
    private float jogar_l, jogar_h; // largura e altura
    private float jogar_x, jogar_y; // Posição x e y 

    public Menu(PApplet skecth) {
        this.p = skecth; // Mesma skecth da classe Skecth
        width = skecth.width;
        height = skecth.height;

        escolher_mapa = new Botao(skecth, width/5, height/3, 3 * width/5, height/4, 0xFFC4E1E6,
            () -> {
                
            });

        sair = new Botao(skecth, width/5, 2* height/3, 3 * width/5, height/6, 0xFFC4E1E6,
            () -> {
                System.out.println("Finalizando o programa...");
                skecth.exit();
            });

        
    }

    public void desenha() {
        // Botão de jogar        
        escolher_mapa.desenha();
        sair.desenha();
    }
}
