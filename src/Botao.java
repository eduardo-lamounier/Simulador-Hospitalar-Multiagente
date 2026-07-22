import processing.core.PApplet;

public class Botao {
    // Atributos do Processing
    private PApplet p;
    private int mouseX;
    private int mouseY;

    // Atributos do botão
    private float x, y; // Posição x e y do canto superior esquerdo do botão 
    private float l, h; // Largura e altura respectivamente

    Botao(PApplet skecth, float x, float y, float l, float h) {
        this.p = skecth;
        this.x = x;
        this.y = y;
        this.l = l;
        this.h = h;

        mouseX = p.mouseX;
        mouseY = p.mouseY;
    }

    public void desenha() {
        p.rect(x, y, l, h);
    }

    public boolean mouseEmCima() {
        if(mouseX >= x && mouseX <= x + l && mouseY >= y && mouseY <= y + h)
            return true;

        return false;
    }

    public boolean clicado() {
        if(mouseEmCima() && p.mousePressed) 
            return true;

        return false;
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
}
