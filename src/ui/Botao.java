package ui;

import processing.core.PApplet;
import processing.core.PImage;

public class Botao {
    // Atributos do Processing
    private PApplet p;
    private int width;
    private int height;

    // Atributos do botão
    private int x, y; // Posição x e y do canto superior esquerdo do botão 
    private int l, h; // Largura e altura respectivamente
    private float raio = 10; // Raio para arredondamento
    private int cor; // Em hexadecimal. Ex.: 0xFFFF0000 vermelho
    private float escala = 1; // Para o botão aumentar com o efeito hover 

    private String texto; // Texto dentro do botão (opcional)
    private float tamanhoTexto; // Tamanho do texto
    private int corTexto; // Cor do texto

    private PImage imagem; // Imagem dentro do botão (opcional)

    private Runnable acao;

    public Botao(PApplet p, int x, int y, int l, int h) {
        this.p = p;
        this.x = x;     this.y = y;
        this.l = l;     this.h = h; 

        this.width = p.width;
        this.height =  p.height;
        p.rectMode(PApplet.CENTER);
    }

    public void loop() { 
        // Método responsável por chamar todas as outros métodos do draw()
        // TODO: Mudar nome do método
        desenha();
    }

    public void desenha() {
        if(mouseEmCima()) {
            p.fill(cor - 0x44000000); // Diminuindo a saturação

            escala = PApplet.lerp(escala, 1.20f, 0.15f); // Aumentando escala até 1.04
        }

        else {
            p.fill(cor);

            escala = PApplet.lerp(escala, 1f, 0.15f); // Diminuindo escala até 1
        }

        p.noStroke();

        float l_atual = l * escala;
        float h_atual = h * escala;
        
        

        if(imagem != null) {
            p.image(imagem, x, y, l_atual, h_atual); 
            return;
        }

        p.rect(x, y, l_atual, h_atual, raio);

        if(texto != null && texto != "") {
            p.fill(corTexto);
            p.textAlign(PApplet.CENTER, PApplet.CENTER);
            p.textSize(tamanhoTexto);
            p.text(texto, x, y);
        }
    }

    public boolean mouseEmCima() {
        if(p.mouseX >= x - l/2 && p.mouseX <= x + l/2 && p.mouseY >= y - h/2 && p.mouseY <= y + h/2)
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

    public float getRaio() {
        return raio;
    }

    public void getCor(int cor) {
        this.cor = cor;
    }

    public String getTexto() {
        return texto;
    }

    public float getTamanhoTexto() {
        return tamanhoTexto;
    }

    public PImage getImagem() {
        return imagem;
    }

    public Runnable getAcao() {
        return acao;
    }

    public void setX(int x) throws IllegalArgumentException {
        if(x < 0 || x > width) 
            throw new IllegalArgumentException("Valor x do botão errado!");
        
        this.x = x;
    }

    public void setY(int y) throws IllegalArgumentException {
        if(y < 0 || y > height) 
            throw new IllegalArgumentException("Valor y do botão errado!");
            
        this.y = y;
    }

    public void setL(int l) {
        this.l = l;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Botao comArredondamento(float raio) {
        this.raio = raio;

        return this;
    }

    public Botao comCor(int cor) {
        this.cor = cor;

        return this;
    }

    public Botao comTexto(String texto, float tamanho, int cor) {
        this.texto = texto;
        this.tamanhoTexto = tamanho;
        this.corTexto = cor;

        return this;
    }

    public Botao comImagem(PImage imagem) {
        this.imagem = imagem; 

        return this;
    }

    public Botao comAcao(Runnable acao) {
        this.acao = acao; 

        return this;
    }
}
