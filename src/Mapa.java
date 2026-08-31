import java.io.*;
import java.util.Scanner;


public class Mapa {
    private static char[][] s_grid;

    // Lê o arquivo do mapa especificado e retorna o grid desse mapa
    // TODO: Implementar
    public static char[][] carregarMapa(int seletMapa) {
        Scanner scanner;

        try {
            scanner = new Scanner(new File(String.format("assets/Mapas/mapa%d.txt",seletMapa)));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Não foi possível encontrar o arquivo do mapa");
        }

        int linhas = scanner.nextInt();
        int colunas = scanner.nextInt();

        scanner.nextLine(); // pula o \n depois do 24 24

        char[][] matriz = new char[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            String linha = scanner.nextLine();

            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = linha.charAt(j);
            }
            
        }

        scanner.close();

        return matriz;

    }

    // Isso só serve para teste
    public void imprimirArquivoMapa(int seletMapa){
        try {
            String  NomeAqv = String.format("assets/Mapas/mapa%d.txt", seletMapa);

            FileReader fileReader = new FileReader(NomeAqv);
            BufferedReader br = new BufferedReader(fileReader);
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
            br.close(); //Quando for necessário fechar
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public static char getCelula(int i, int j) { return s_grid[i][j]; }
    
    public static void moverPaciente(int i_source, int j_source, int i_dest, int j_dest) {
        assert s_grid[i_source][j_source] == 'P' : "Só é possível mover pacientes";
        assert s_grid[i_dest][j_dest] == '.' : "Só é possível mover um paciente"
                                               + " para uma célula transitável";

        s_grid[i_source][j_source] = '.';
        s_grid[i_dest][j_dest] = 'P';
    }

    public static boolean mapaCarregado() { return s_grid != null; }

    public Mapa(int seletMapa){
        assert mapaCarregado() : "O mapa já foi inicializado!";
        s_grid = carregarMapa(seletMapa);
    }


    
    
    public static void desenharMapa(char matriz[][],Sketch p){

        float largura = p.width / (float) matriz[0].length;
        float altura = p.height / (float) matriz.length;


        for(int i = 0;i < matriz.length;i++){
            for(int j = 0;j < matriz[0].length;j++){

                switch (matriz[i][j]) {
                    case'T':
                        //Totem (T):
                        // PImage meuSprite2 = p.loadImage("../assets/Sprites/a1.png");
                        //p.image(meuSprite2, (width/2) - tamanho_sprite/2, (height/2)- tamanho_sprite/2,tamanho_sprite,tamanho_sprite);
                        //p.rect(j* largura, i * altura, largura, altura);
                        break;
                    case '.':
                        //Chão (.)
                        p.text(i, largura, altura);
                        p.rect(j* largura, i * altura, largura, altura);
                        break;
                    case'#':
                        //Parede (#):
                        p.rect(j* largura, i * altura, largura, altura);
                        break;
                    case 'R':
                        //Removedor (R):
                        p.rect(j* largura, i * altura, largura, altura);
                        break;
                    case 'E':
                        //Enfermeira de Triagem (E):
                        p.rect(j* largura, i * altura, largura, altura);
                        break;
                    case 'A':
                        //Assento (A):
                        p.rect(j* largura, i * altura, largura, altura);
                        break;                        
                    case 'G':
                        //Gerador (G):
                        p.rect(j* largura, i * altura, largura, altura);
                        break;
                    case'M':
                        //Médico (M):
                        p.rect(j* largura, i * altura, largura, altura);
                        break;
                    default:
                        break;
                }

            }
        }

    };

    public static void desenharMapaAtual(Sketch sketch) {
        assert mapaCarregado() : "Mapa ainda não foi carregado! Não é possível"
                                 + " desenhar";
        desenharMapa(s_grid, sketch);
    }
    
}

