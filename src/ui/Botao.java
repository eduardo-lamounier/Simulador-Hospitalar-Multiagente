package ui;

import processing.core.PApplet;

public class Botao {
    // Atributos do Processing
    private PApplet p;

    // Atributos do botão
    private int x, y; // Posição x e y do canto superior esquerdo do botão 
    private int l, h; // Largura e altura respectivamente
    private int cor; // Em hexadecimal. Ex.: 0xFFFF0000 vermelho 

    private Runnable acao;

    Botao(PApplet skecth, int x, int y, int l, int h, int cor, Runnable acao) {
        this.p = skecth;
        this.x = x;     this.y = y;
        this.l = l;     this.h = h; 
        this.acao = acao;
        this.cor = cor; 
    }

    public void desenha() {
        if(mouseEmCima()) 
            p.fill(cor - 0x33000000); // Diminuindo a saturação

        else 
            p.fill(cor);

        p.noStroke();
        p.rect(x, y, l, h);

        clicado();
    }

    public boolean mouseEmCima() {
        if(p.mouseX >= x && p.mouseX <= x + l && p.mouseY >= y && p.mouseY <= y + h)
            return true;

        return false;
    }

    public void clicado() {
        if(mouseEmCima() && p.mousePressed) 
            acao.run();
    }

    // Métodos controladores
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getH() {
        return h;
    }

    public float getL() {
        return l;
    }

    public void  getCor(int cor) {
        this.cor = cor;
    }
}
