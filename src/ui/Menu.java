package ui;

import processing.core.PApplet;

public class Menu{
    /* 
    TODO: 
    
    Desenhar o menu:
        Tela inicial
        Seleção da mapa
    */

    // Atributos comuns do PApplet
    private PApplet p; // Instância da sketch principal
    private int width; // Largura do sketch 
    private int height; // Altura do sketch

    // Botões:
    private Botao sair;
    private Botao escolher_mapa;

    // Menu:
    private int etapa = 1;

    public Menu(PApplet sketch) {
        this.p = sketch; // Mesma skecth da classe Skecth
        width = sketch.width;
        height = sketch.height;

        // 
        escolher_mapa = new Botao.Builder(sketch, width/5, height/3, 3 * width/5, height/4)
                        .setCor(0xFFC4E1E6)
                        .setTexto("Iniciar")
                        .setTamanhoTexto(100)
                        .setCorTexto(0x00000000)
                        .setAcao(() -> {etapa = 2;})
                        .build();

        sair = new Botao.Builder(sketch, width/5, 2 * height/3, 3 * width/5, height/6)
                        .setCor(0xFFC4E1E6)
                        .setTexto("Sair")
                        .setTamanhoTexto(80)
                        .setCorTexto(0x000000000)
                        .setAcao(() -> {
                                System.out.println("Finalizando o programa...");
                                sketch.exit();
                                })
                        .build();
    }

    public void desenha() {   
        fazerTitulo();
        
        if(etapa == 1) {
            escolher_mapa.loop();
            sair.loop(); 
        }
    }

    private void fazerTitulo() {
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.fill(0x00000000);

        switch (etapa) {
            case 1:
                p.textSize(100);
                p.text("Bem-vindo!", width/2, height/6);

                break;
        
            case 2:
                p.textSize(75);
                p.text("Selecione um mapa", width/2, height/7);

                break;

            default: 
                break;
        }
        
    }

    public int getEtapa() {
        return etapa;
    }
}
