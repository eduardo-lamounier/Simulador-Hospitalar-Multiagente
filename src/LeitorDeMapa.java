import java.io.*;

public class LeitorDeMapa{

public char[][] imprimirLinha(int seletMapa){
        try {
        String  NomeAqv = String.format("assets/Mapas/mapa%d.txt", seletMapa);
        //"Mapas/mapa" + seletMapa + ".txt"
        /* 
            LeitorDeMapa teste= new LeitorDeMapa();
            int x = 3;
            teste.imprimirLinha(x);
        */
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

    throw new IllegalStateException("Essa função não foi ainda implementada");
} 

}
