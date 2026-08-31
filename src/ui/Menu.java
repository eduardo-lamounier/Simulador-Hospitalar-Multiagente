package ui;

import processing.core.PApplet;
import processing.core.PImage;

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
<<<<<<< HEAD
    private Botao selecao_mapa;
    private Botao esquerdo_mapa;
    private Botao direita_mapa;
=======
    private Botao ir_esquerda; // Botão para ir a esquerda na seleção de mapas
    private Botao ir_direita; // Botoao para ir a direita na seleção de mapas
>>>>>>> 1b4dfeeaf3e4572e950ed0e804e0ed4c7736690d

    // Menu:
    private int etapa = 1;

<<<<<<< HEAD
    
=======
    // Temporização:
    private int cooldown = 200; // 200 milissegundos
    private int clique_atual; // Em milissengundos

>>>>>>> 1b4dfeeaf3e4572e950ed0e804e0ed4c7736690d
    public Menu(PApplet sketch) {
        this.p = sketch; // Mesma skecth da classe Skecth
        width = sketch.width;
        height = sketch.height;
<<<<<<< HEAD
        
        //
=======

        // Atributos de temporização:
        clique_atual = p.millis();

        // Botões sendo montados:
>>>>>>> 1b4dfeeaf3e4572e950ed0e804e0ed4c7736690d
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
<<<<<<< HEAD
        selecao_mapa = new Botao(sketch, 100, 100, 50, 50)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6)
                        .comTexto("voltar", 20, 0xFF020202)
                        .comAcao(() -> {
                            etapa = 1;
                        });
            
        direita_mapa = new Botao(sketch, 700, 300, 50, 50)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6)
                        .comTexto("direita", 20, 0xFF020202)
                        .comAcao(() -> {
                            etapa += 1;
                        });
            
        esquerdo_mapa = new Botao(sketch, 100, 300, 50, 50)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6)
                        .comTexto("esquerda", 20, 0xFF020202)
                        .comAcao(() -> {
                            etapa -= 1;
                        });                              

=======

        ir_esquerda = new Botao(sketch, width/10, 3 * height / 5, width/10, 6 * height / 10)
                                .comCor(0xFFFFFFFF)
                                .comAcao(() -> {});
>>>>>>> 1b4dfeeaf3e4572e950ed0e804e0ed4c7736690d
    }

    public void desenha() {   
        fazerTitulo();
        
        checaClique();
        
        if(etapa == 1) {
            proxima_etapa.loop();
            sair.loop(); 

            return;
        }

<<<<<<< HEAD
        else if(etapa == 2){
            selecao_mapa.loop();
            direita_mapa.loop();
        }
        else if (etapa == 3){
            selecao_mapa.loop();
            direita_mapa.loop();
            esquerdo_mapa.loop();
        }
        else if(etapa == 4){
            selecao_mapa.loop();
            esquerdo_mapa.loop();
        }
        
=======
        ir_esquerda.loop();

>>>>>>> 1b4dfeeaf3e4572e950ed0e804e0ed4c7736690d
    }

    private void fazerTitulo() {
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.fill(0x00000000);
        final float tamanho_sprite = 450;
        switch (etapa) {
            case 1:
                p.textSize(100);
                p.text("Bem-vindo!", width/2, height/6);

                break;
        
            case 2:
                p.textSize(75);
                p.text(" mapa 1", width/2,30);
                PImage meuSprite2 = p.loadImage("../assets/Sprites/a1.png");
                p.image(meuSprite2, (width/2) - tamanho_sprite/2, (height/2)- tamanho_sprite/2,tamanho_sprite,tamanho_sprite);
                break;
            case 3:
                p.textSize(75);
                p.text(" mapa 2", width/2,30);
                PImage meuSprite = p.loadImage("../assets/Sprites/b3.png");
                p.image(meuSprite, (width/2) - tamanho_sprite/2, (height/2)- tamanho_sprite/2,tamanho_sprite,tamanho_sprite);
                break;
            case 4:
                p.textSize(75);
                p.text(" mapa 3", width/2,30);
                PImage meuSprite3 = p.loadImage("../assets/Sprites/Chao.png");
                p.image(meuSprite3, (width/2) - tamanho_sprite/2, (height/2)- tamanho_sprite/2,tamanho_sprite,tamanho_sprite);
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
