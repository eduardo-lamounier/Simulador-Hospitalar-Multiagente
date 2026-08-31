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
    private Botao sair; // Botão para sair da simulação
    private Botao proxima_etapa; // Botão para ir a próxima etapa
    private Botao ir_esquerda; // Botão para ir a esquerda na seleção de mapas
    private Botao ir_direita; // Botoao para ir a direita na seleção de mapas

    // Menu:
    private int etapa = 1;

    // Temporização:
    private int cooldown = 200; // 200 milissegundos
    private int clique_atual; // Em milissengundos

    public Menu(PApplet sketch) {
        this.p = sketch; // Mesma skecth da classe Skecth
        width = sketch.width;
        height = sketch.height;

        // Atributos de temporização:
        clique_atual = p.millis();

        // Botões sendo montados:
        proxima_etapa = new Botao(sketch, width/2, 5 * height/12, 3 * width/5, height/  4)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6)
                        .comTexto("Iniciar", 100, 0xFF020202)
                        .comAcao(() -> { etapa = 2; });

        sair = new Botao(sketch, width/2, 3 * height/4, 3 * width/5, height/6)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6)
                        .comTexto("Sair", 80, 0xFF000000)
                        .comAcao(() -> {
                                System.out.println("Finalizando o programa...");
                                sketch.exit();
                                });

        ir_esquerda = new Botao(sketch, width/10, 3 * height / 5, width/10, 6 * height / 10)
                                .comCor(0xFFFFFFFF)
                                .comAcao(() -> {});
    }

    public void desenha() {   
        fazerTitulo();
        
        checaClique();
        
        if(etapa == 1) {
            proxima_etapa.loop();
            sair.loop(); 

            return;
        }

        ir_esquerda.loop();

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
    
    public void checaClique() {
        if(p.millis() - clique_atual == cooldown) {
            return;
        }

        clique_atual = p.millis();

        proxima_etapa.clicado();
        sair.clicado();
        ir_esquerda.clicado();
    }
    
    public int getEtapa() {
        return etapa;
    }
}
