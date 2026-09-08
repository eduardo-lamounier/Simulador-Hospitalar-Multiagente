package ui;

import processing.core.PApplet;
import processing.core.PImage;
import estruturas.Vector;

public class SelecaoDeMapa {
    // Final:
    private static final int NUM_MAPAS = 3; // Número total de mapas disponíveis

    // Atributos comuns do PApplet
    private PApplet p; // Instância da sketch principal
    private int width; // Largura do sketch 
    private int height; // Altura do sketch
    private boolean proxima_etapa = false;

    // Botões:
    private Botao sair; // Botão para sair da simulação
    private Botao ir_esquerda; // Botão para ir a esquerda na seleção de mapas
    private Botao ir_direita; // Botoao para ir a direita na seleção de mapas

    // Elementos da tela:
    private int selecaoMapa = 1; // Responsável por definir qual mapa sera mostrado
    private PImage spriteMapa; // Responsável por armazenar a imagem do mapa mostrado
    private Vector<Botao> mapas = new Vector<>(); // Vetor de botões para seleção de mapas

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
                        .comCor(0xFFC4E1E6) // #c4e1e6
                        .comTexto("X", 20, 0xFFb30c15) // #b30c15
                        .comAcao(() -> {
                                System.out.println("Finalizando o programa...");
                                sketch.exit();
                                });
            
        ir_direita = new Botao(sketch, 700, 300, 50, 50)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6) // # C4E1E6
                        .comTexto("direita", 20, 0xFF020202) // #020202
                        .comAcao(() -> {
                            this.setSelecaoMapa(getSelecaoMapa() + 1);
                        });
            
        ir_esquerda = new Botao(sketch, 100, 300, 50, 50)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6) // #C4E1E6
                        .comTexto("esquerda", 20, 0xFF020202) // #020202
                        .comAcao(() -> {
                            this.setSelecaoMapa(getSelecaoMapa() - 1);
                        });               
                        
        renderizaMapa();
    }

    // Método responsável por renderizar as imagens dos mapas
    private void renderizaMapa() {
        final int tamanho_sprite = 450;

        for(int i = 0; i < NUM_MAPAS; i++) {
            spriteMapa = p.loadImage("./assets/Sprites/Exemplo_Mapa" + (i + 1) + ".png");

            assert spriteMapa != null : "Sprite do mapa não foi carregado corretamente!";

            mapas.insert(i, new Botao(p, width/2, height/2, tamanho_sprite, tamanho_sprite)
                            .comImagem(spriteMapa)
                            .comAcao(() -> {
                                this.getSelecaoMapa();
                                this.setProximaEtapa(true);
                            }));
        }
    }

    // Método responsável pelas mecanicas de repetição do draw() como desenhar a seleção de mapa
    public void atualiza() {
        checaClique();
        desenha();
    }

    public void desenha() {   
        fazerTitulo();

        mapas.at(selecaoMapa - 1).atualiza();

        sair.atualiza();

        if(selecaoMapa > 1)
            ir_esquerda.atualiza();

        if(selecaoMapa < NUM_MAPAS)
            ir_direita.atualiza();
    }

    // Método responsável por desenhar o título do mapa
    private void fazerTitulo() {
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.fill(0x00000000); // #000000
        p.textSize(75);
        p.text(" mapa " + selecaoMapa, width/2, 30);   
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

    private void setSelecaoMapa(int selecaoMapa) {
        assert selecaoMapa > 0 && selecaoMapa <= NUM_MAPAS : "O índice do mapa está" 
                                                            + " fora dos limites";
        this.selecaoMapa = selecaoMapa;
    }

    public boolean getProximaEtapa() {
        return proxima_etapa;
    }

    public void setProximaEtapa(boolean proxima_etapa) {
        this.proxima_etapa = proxima_etapa;
    }
}
