import java.io.*;

public class Mapa {
    private static char[][] s_grid;

    // Lê o arquivo do mapa especificado e retorna o grid desse mapa
    // TODO: Implementar
    public static char[][] carregarMapa(int seletMapa) {
        throw new IllegalStateException("Essa função não foi ainda implementada");
    }

    // Isso só serve para teste
    public void imprimirArquivoMapa(int seletMapa){
        try {
            String  NomeAqv = String.format("assets/Mapas/mapa%d.txt", seletMapa);
            //"Mapas/mapa" + seletMapa + ".txt"
            
            // LeitorDeMapa teste= new LeitorDeMapa();
            // int x = 3;
            // teste.imprimirLinha(x);
            
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

    public Mapa(int seletMapa) {
        assert mapaCarregado() : "O mapa já foi inicializado!";

        s_grid = carregarMapa(seletMapa);
    }
}

