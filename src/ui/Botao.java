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
    private int cor; // Em hexadecimal. Ex.: 0xFFFF0000 vermelho 
    private String texto; // Texto dentro do botão (opcional)
    private float tamanhoTexto; // Tamanho do texto
    private int corTexto; // Cor do texto
    private PImage imagem; // Imagem dentro do botão (opcional)

    private Runnable acao;

    private Botao(Builder builder) {
        this.p = builder.p;
        this.x = builder.x;     this.y = builder.y;
        this.l = builder.l;     this.h = builder.h; 
        this.acao = builder.acao;
        this.cor = builder.cor;
        this.texto = builder.texto;
        this.tamanhoTexto = builder.tamanhoTexto;
        this.corTexto = builder.corTexto;

        this.width = p.width;
        this.height =  p.height;
    }

    public void loop() { 
        // Método responsável por chamar todas as outros métodos do draw()
        // TODO: Mudar nome do método
        if(imagem != null) 
            desenha(imagem);

        else { 
            if(texto != null) 
                desenha(texto);

            else 
                desenha();
        }

        clicado();
    }

    public void desenha() {
        if(mouseEmCima()) 
            p.fill(cor - 0x33000000); // Diminuindo a saturação

        else 
            p.fill(cor);

        p.noStroke();
        p.rect(x, y, l, h);
    }

    public void desenha(String texto) {
        if(mouseEmCima()) 
            p.fill(cor - 0x33000000); // Diminuindo a saturação

        else 
            p.fill(cor);

        p.noStroke();
        p.rect(x, y, l, h);

        p.fill(corTexto);
        p.textAlign(PApplet.CENTER, PApplet.CENTER);
        p.textSize(tamanhoTexto);
        p.text(texto, x + l/2, y + h/2);
    }

    public void desenha(PImage imagem) {
       p.noStroke();
       p.image(imagem, x, y, l, h); 
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

    public String getTexto() {
        return texto;
    }

    public float getTamanhoTexto() {
        return tamanhoTexto;
    }

    public PImage getImagem() {
        return imagem;
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

    public void setCor(int cor) {
        this.cor = cor;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setTamanhoTexto(float tamanhoTexto) {
        this.tamanhoTexto = tamanhoTexto;
    }

    public void setImagem(PImage imagem) {
        this.imagem = imagem;
    }

    // Classe Builder
    public static class Builder {
        // Atributos obrigatórios
        private PApplet p;
        private int x, y; // Posição x e y do canto superior esquerdo do botão 
        private int l, h; // Largura e altura respectivamente

        //Atributos opcionais
        private int cor = 0xFFFEFEFE; // Padrão branco
        private String texto = null; // Texto dentro do botão (opcional)
        private float tamanhoTexto = 0; // Tamanho do texto
        private int corTexto = 0x00000000; // Texto preto por padrão 
        private PImage imagem = null; // Imagem dentro do botão (opcional)

        private Runnable acao = () -> {};

        Builder(PApplet sketch, int x, int y, int l, int h) {
            this.p = sketch;
            this.x = x; this.y = y;
            this.l = l; this.h = h;
        }

        public Builder setX(int x) {
            this.x = x;

            return this;
        }

        public Builder setY(int y) {
            this.y = y;

            return this;
        }

        public Builder setL(int l) {
            this.l = l;

            return this;
        }

        public Builder setH(int h) {
            this.h = h;

            return this;
        }

        public Builder setCor(int cor) {
            this.cor = cor;

            return this;
        }

        public Builder setTexto(String texto) {
            this.texto = texto;

            return this;
        }

        public Builder setTamanhoTexto(float tamanhoTexto) {
            this.tamanhoTexto = tamanhoTexto;

            return this;
        }

        public Builder setCorTexto(int corTexto) {
            this.corTexto = corTexto;

            return this;
        }

        public Builder setImagem(PImage imagem) {
            this.imagem = imagem;

            return this;
        }

        public Builder setAcao(Runnable acao) {
            this.acao = acao;

            return this;
        }

        public Botao build() {
            return new Botao(this);
        }
    }
}
