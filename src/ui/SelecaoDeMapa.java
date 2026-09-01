package ui;

import processing.core.PApplet;
import processing.core.PImage;

public class SelecaoDeMapa {
    // Atributos comuns do PApplet
    private PApplet p; // Instância da sketch principal
    private int width; // Largura do sketch 
    private int height; // Altura do sketch

    // Botões:
    private Botao sair; // Botão para sair da simulação
    private Botao ir_esquerda; // Botão para ir a esquerda na seleção de mapas
    private Botao ir_direita; // Botoao para ir a direita na seleção de mapas

    // Menu:
    private int selecaoMapa = 1; // Responsável por definir qual o mapa sera mostrado

    // Temporização:
    private int cooldown = 200; // 200 milissegundos
    private int clique_atual; // Em milissengundos

    public SelecaoDeMapa(PApplet sketch) {
        this.p = sketch; // Mesma skecth da classe Skecth
        width = sketch.width;
        height = sketch.height;
        
        // Atributos de temporização:
        clique_atual = p.millis();

        // Botões sendo montados:
        sair = new Botao(sketch, 100, 100, 50, 50)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6)
                        .comTexto("X", 20, 0xFFb30c15) // #b30c15
                        .comAcao(() -> {
                                System.out.println("Finalizando o programa...");
                                sketch.exit();
                                });
            
        ir_direita = new Botao(sketch, 700, 300, 50, 50)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6)
                        .comTexto("direita", 20, 0xFF020202)
                        .comAcao(() -> {
                            selecaoMapa += 1;
                        });
            
        ir_esquerda = new Botao(sketch, 100, 300, 50, 50)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6)
                        .comTexto("esquerda", 20, 0xFF020202)
                        .comAcao(() -> {
                            selecaoMapa -= 1;
                        });                              
    }

    public void atualiza() {
        // Método responsável pelas mecanicas de repetição do draw() como desenhar a seleção de mapa
        checaClique();
        desenha();
    }

    public void desenha() {   
        fazerTitulo();

        switch (selecaoMapa) {
            case 1:
                sair.atualiza();
                ir_direita.atualiza();
                break;

            case 2:
                sair.atualiza();
                ir_direita.atualiza();
                ir_esquerda.atualiza();
                break;

            case 3:
                sair.atualiza();
                ir_esquerda.atualiza();
                break;

            default:
                break;
        }
    }

    private void fazerTitulo() {
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.fill(0x00000000);
        final float tamanho_sprite = 450;
        switch (selecaoMapa) {
            case 1:
                p.textSize(75);
                p.text(" mapa 1", width/2, 30);
                PImage meuSprite2 = p.loadImage("./assets/Sprites/a1.png");
                p.image(meuSprite2, (width/2) - tamanho_sprite/2, (height/2)- tamanho_sprite/2,tamanho_sprite,tamanho_sprite);
                break;

            case 2:
                p.textSize(75);
                p.text(" mapa 2", width/2, 30);
                PImage meuSprite = p.loadImage("./assets/Sprites/b3.png");
                p.image(meuSprite, (width/2) - tamanho_sprite/2, (height/2)- tamanho_sprite/2,tamanho_sprite,tamanho_sprite);
                break;

            case 3:
                p.textSize(75);
                p.text(" mapa 3", width/2,30);
                PImage meuSprite3 = p.loadImage("./assets/Sprites/Chao.png");
                p.image(meuSprite3, (width/2) - tamanho_sprite/2, (height/2)- tamanho_sprite/2,tamanho_sprite,tamanho_sprite);
                break;

            default: 
                break;
        }      
    }
    
    public void checaClique() {
        if(p.millis() - clique_atual <= cooldown)
            return;

        clique_atual = p.millis();

        sair.clicado();
        ir_esquerda.clicado();
        ir_direita.clicado();
    }
    
    public int getSelecaoMapa() {
        return selecaoMapa;
    }
}
