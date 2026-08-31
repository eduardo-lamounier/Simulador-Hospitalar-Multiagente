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
                                .comAcao(null);

        reiniciarBT = new Botao(sketch, width/2, 11 * height/22, 2*width/3, height/11)
                                .comArredondamento(20f)
                                .comCor(0xFFFFFFFF)
                                .comTexto("Reiniciar", 60, 0x00000000)
                                .comAcao(null);

        voltarSelecaoBT = new Botao(sketch, width/2, 15 * height/22, 2*width/3, height/11)
                                .comArredondamento(20f)
                                .comCor(0xFFFFFFFF)
                                .comTexto("Voltar para a seleção", 60, 0x00000000)
                                .comAcao(null);

        sairBT = new Botao(sketch, width/2, 19 * height/22, 2*width/3, height/11)
                                .comArredondamento(20f)
                                .comCor(0xFFFFFFFF)
                                .comTexto("Sair", 60, 0x00000000)
                                .comAcao(null);

    }

    public void atualiza() {
        desenha();
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
}
