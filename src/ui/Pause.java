package ui;

import processing.core.PApplet;

public class Pause {
/* ----------- Atributos ------------ */
    // Sketch: 
    PApplet p;
    int width;
    int height;

    // Botões:
    Botao continuarBT;
    Botao reiniciarBT;
    Botao sairBT;
    Botao voltarSelecaoBT;

    // Estados do programa e simulação:
    private boolean irSelecao = false;
    private boolean reiniciar = false;
    private boolean continuar = false;

    // Temporização:
    private int cooldown = 200; // 200 milissegundos
    private int clique_atual; // Em milissengundos
    
/* ----------- Métodos ------------ */
    // Construtor
    public Pause(PApplet sketch) {
        this.p = sketch;
        this.width = sketch.width;
        this.height = sketch.height;

        continuarBT = new Botao(sketch, width/2, 7 * height/22, 2*width/3, height/11)
                                .comArredondamento(20f)
                                .comCor(0xFFFFFFFF)
                                .comTexto("Continuar", 60, 0x00000000)
                                .comAcao(() -> { continuar = true; });

        reiniciarBT = new Botao(sketch, width/2, 11 * height/22, 2*width/3, height/11)
                                .comArredondamento(20f)
                                .comCor(0xFFFFFFFF)
                                .comTexto("Reiniciar", 60, 0x00000000)
                                .comAcao(() -> { reiniciar = true; });

        voltarSelecaoBT = new Botao(sketch, width/2, 15 * height/22, 2*width/3, height/11)
                                .comArredondamento(20f)
                                .comCor(0xFFFFFFFF)
                                .comTexto("Voltar para a seleção", 60, 0x00000000)
                                .comAcao(() -> { irSelecao = true; });

        sairBT = new Botao(sketch, width/2, 19 * height/22, 2*width/3, height/11)
                                .comArredondamento(20f)
                                .comCor(0xFFFFFFFF)
                                .comTexto("Sair", 60, 0x00000000)
                                .comAcao(() -> {
                                    System.out.println("Finalizando o programa...");
                                    p.exit();
                                });

    }

    public void atualiza() {
        desenha();
        checaClique();
    }

    public void desenha() {
        fazerTitulo();

        continuarBT.atualiza();
        reiniciarBT.atualiza();
        voltarSelecaoBT.atualiza();
        sairBT.atualiza();
    }

    private void fazerTitulo() {
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.fill(0x00000000);

        p.textSize(100);
        p.text("Pausado", width/2, height/6);  
    }

    // Método responsável por checar se algum botão foi clicado,
    // ignora os cliques que acontecem em um intervalo menor que o cooldown
    public void checaClique() {
        if(p.millis() - clique_atual <= cooldown)
            return;

        clique_atual = p.millis();

        p.delay(cooldown);
    }

    // Métodos controladores
    public boolean getContinuar() {
        return continuar;
    }

    public void setContinuar(boolean continuar) {
        this.continuar = continuar;
    }

    public boolean getReiniciar() {
        return reiniciar;
    }

    public void setReiniciar(boolean reiniciar) {
        this.reiniciar = reiniciar;
    }

    public boolean getIrSelecao() {
        return irSelecao;
    }
    
    public void setIrSelecao(boolean irSelecao) {
        this.irSelecao = irSelecao;
    }
}
