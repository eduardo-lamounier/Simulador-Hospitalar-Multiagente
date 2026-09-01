package ui;

import processing.core.PApplet;
import processing.core.PImage;

public class Menu{
    // Atributos comuns do PApplet
    private PApplet p; // Instância da sketch principal
    private int width; // Largura do sketch 
    private int height; // Altura do sketch

    // Botões:
    private Botao sair; // Botão para sair da simulação
    private Botao proxima_etapa; // Botão para ir a próxima etapa

    // Menu:
    private boolean irSelecao = false; // TODO: mudar nome

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
                        .comAcao(() -> { irSelecao = true; });

        sair = new Botao(sketch, width/2, 3 * height/4, 3 * width/5, height/6)
                        .comArredondamento(20f)
                        .comCor(0xFFC4E1E6)
                        .comTexto("Sair", 80, 0xFF000000)
                        .comAcao(() -> {
                                System.out.println("Finalizando o programa...");
                                sketch.exit();
                                });                       
    }

    public void atualiza() {
        // Método responsável pelas mecanicas de repetição do draw() como desenhar o menu 
        checaClique();
        desenha();
    }

    public void desenha() {   
        fazerTitulo();
        
        proxima_etapa.atualiza();
        sair.atualiza(); 
    }

    private void fazerTitulo() {
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.fill(0x00000000);
        p.textSize(100);
        p.text("Bem-vindo!", width/2, height/6);
    }
    
    public void checaClique() {
        if(p.millis() - clique_atual <= cooldown)
            return;

        clique_atual = p.millis();

        proxima_etapa.clicado();
        sair.clicado();
    }

    public boolean getIrSelecao() {
        return irSelecao;
    }
}
