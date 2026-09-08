import processing.core.PImage;

import java.io.*;
import java.util.Scanner;

import estruturas.Vector;

public class Mapa {
    private static char[][] s_grid;
    private static int atul_mapa;

    private static Gerador s_gerador;
    private static Totem s_totem;
    private static Triagem s_triagem;
    private static Consultas s_consultas;
    private static Removedor s_removedor;

    public static Gerador geradorMapaAtual() { return s_gerador; }

    public static Totem totemMapaAtual() { return s_totem; }

    public static Triagem triagemMapaAtual() { return s_triagem; }

    public static Consultas consultasMapaAtual() { return s_consultas; }

    
    public static Removedor removedorMapaAtual() { return s_removedor; }    

    private static boolean assetsCarregados = false;
    private static PImage spriteChao;
    private static PImage spriteParede;
    private static PImage spriteGerador;
    private static PImage spriteTotem;
    private static PImage spriteRemovedor;
    private static PImage spriteEnfermeira;
    private static PImage spriteMedico;
    private static PImage spritePaciente;
    private static PImage spriteAssento;
    private static PImage spritePacienteSentado;
    private static PImage spritePacienteNoGerador;

    private static void carregarAssets(Sketch p) {
        assetsCarregados = true;
        spriteTotem = p.loadImage(String.format("./assets/Sprites/trem%d/Totem.png", atul_mapa));
        spriteChao = p.loadImage(String.format("./assets/Sprites/trem%d/Chao.png", atul_mapa));
        spriteParede = p.loadImage(String.format("./assets/Sprites/trem%d/Parede.png", atul_mapa));
        spriteRemovedor = p.loadImage(String.format("./assets/Sprites/trem%d/Removedor.png", atul_mapa));
        spriteEnfermeira = p.loadImage(String.format("./assets/Sprites/trem%d/Enfermeira.png", atul_mapa));
        spriteAssento = p.loadImage(String.format("./assets/Sprites/trem%d/Assento.png", atul_mapa));
        spriteGerador = p.loadImage(String.format("./assets/Sprites/trem%d/Gerador.png", atul_mapa));
        spriteMedico = p.loadImage(String.format("./assets/Sprites/trem%d/Medico.png", atul_mapa));
        spritePaciente = p.loadImage(String.format("./assets/Sprites/trem%d/Paciente.png", atul_mapa));
        spritePacienteNoGerador = p.loadImage(String.format("./assets/Sprites/trem%d/Paciente_Gerador.png", atul_mapa));
        spritePacienteSentado = p.loadImage(String.format("./assets/Sprites/trem%d/Paciente_Sentado.png", atul_mapa));
    }

    // Lê o arquivo do mapa especificado e retorna o grid desse mapa
    public static char[][] carregarMapa(int seletMapa) {
        Scanner scanner;

        try {
            scanner = new Scanner(new File(String.format("assets/Mapas/mapa%d.txt",seletMapa)));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Não foi possível encontrar o arquivo do mapa");
        }

        atul_mapa = seletMapa;
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
    public static char[][] gridAtual() { return s_grid; }
    
    public static void moverPaciente(int i_source, int j_source, int i_dest, int j_dest) {
        assert s_grid[i_source][j_source] == 'P' : "Só é possível mover pacientes";
        assert s_grid[i_dest][j_dest] == '.' : "Só é possível mover um paciente"
                                               + " para uma célula transitável";

        s_grid[i_source][j_source] = '.';
        s_grid[i_dest][j_dest] = 'P';
    }

    public static boolean mapaCarregado() { return s_grid != null; } 
    
    public static void desenharMapa(char matriz[][],Sketch p){
        if(!assetsCarregados)
            carregarAssets(p);

        float largura = p.width / (float) matriz[0].length;
        float altura = p.height / (float) matriz.length;
        
        for(int i = 0;i < matriz.length;i++){
            for(int j = 0;j < matriz[0].length;j++){
                
                switch (matriz[i][j]) {
                    case'T':
                    //Totem (T):
                    p.image(spriteTotem,j* largura, i * altura, largura, altura);
                        
                    break;
                    case '.':
                            //Chão (.)
                        p.image(spriteChao,j* largura, i * altura, largura, altura);
                        
                    break;
                    case'#':
                        //Parede (#):
                        p.image(spriteParede,j* largura, i * altura, largura, altura);

                    break;
                    case 'R':
                        //Removedor (R):
                        p.image(spriteRemovedor,j* largura, i * altura, largura, altura);

                    break;
                    case 'E':
                        //Enfermeira de Triagem (E):
                        p.image(spriteEnfermeira,j* largura, i * altura, largura, altura);

                    break;
                    case 'A':
                        //Assento (A):
                        p.image(spriteAssento,j* largura, i * altura, largura, altura);      

                    break;                        
                    case 'G':
                        //Gerador (G):
                        p.image(spriteGerador,j* largura, i * altura, largura, altura);

                    break;
                    case'M':
                        //Médico (M):
                        p.image(spriteMedico,j* largura, i * altura, largura, altura);

                    break;
                    case'P':
                        p.image(spritePaciente, j* largura, i * altura, largura, altura);

                    break;
                    case 'S':
                        p.image(spritePacienteSentado, j* largura, i * altura, largura, altura);

                    break;
                    case'D':
                        p.image(spritePacienteNoGerador, j* largura, i * altura, largura, altura);

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
    
    public Mapa(Sketch sketch, int seletMapa){
        assert !mapaCarregado() : "O mapa já foi inicializado!";
        s_grid = carregarMapa(seletMapa);

        int m = s_grid.length;
        int n = s_grid[0].length;

        PositionDTO posicaoGerador = null;
        PositionDTO posicaoRemovedor = null;
        PositionDTO posicaoTotem = null;
        Vector<PositionDTO> posicoesAssentos = new Vector<>();
        Vector<PositionDTO> posicoesMedicos = new Vector<>();
        Vector<PositionDTO> posicoesEnfermeiras = new Vector<>();

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                PositionDTO posicaoAtual = new PositionDTO(j, i);
                switch(s_grid[i][j]) {
                    case 'G':
                        posicaoGerador = posicaoAtual;
                        break;
                    case 'R':
                        posicaoRemovedor = posicaoAtual;
                        break;
                    case 'T':
                        posicaoTotem = posicaoAtual;
                        break;
                    case 'A':
                        posicoesAssentos.push(posicaoAtual);
                        break;
                    case 'M':
                        posicoesMedicos.push(posicaoAtual);
                        break;
                    case 'E':
                        posicoesEnfermeiras.push(posicaoAtual);
                        break;
                    default:
                        break;
                }
            }
        }

        assert posicaoGerador != null;
        assert posicaoRemovedor != null;
        assert posicaoTotem != null;
        assert posicoesAssentos.size() > 0;
        assert posicoesMedicos.size() > 0;
        assert posicoesEnfermeiras.size() > 0;
        
        Vector<Assento> assentos = new Vector<>();
        assentos.reserve(posicoesAssentos.size());
        posicoesAssentos.forEach((var posicao) -> {
            assentos.push(new Assento(posicao));
        });
        
        s_removedor = new Removedor(sketch, posicaoRemovedor);
        s_consultas = new Consultas(assentos, s_removedor);
        s_triagem = new Triagem(assentos, s_consultas);
        s_totem = new Totem(s_triagem, posicaoTotem);
        s_gerador = new Gerador(sketch, s_totem, posicaoGerador);

        posicoesEnfermeiras.forEach((var posicao) -> {
            Triagem.Enfermeira enfermeira = s_triagem.new Enfermeira(posicao, sketch);
            s_triagem.adicionarEnfermeira(enfermeira);
        });

        posicoesMedicos.forEach((var posicao) -> {
            Consultas.Medico medico = s_consultas.new Medico(posicao, sketch);
            s_consultas.adicionarMedico(medico);
        });
    }
}

