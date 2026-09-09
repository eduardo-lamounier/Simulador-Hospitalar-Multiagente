import java.util.Arrays;

import estruturas.Queue;

public class WaveFront {

  private static final int[][] DIRECOES = {
    {1, 0},
    {0, 1},
    {-1, 0},
    {0, -1},
  };

  private WaveFront() {}

  // Apenas chão e assentos são transitáveis.
  // Paredes (#), Enfermeiras (E), Médicos (M), Totens (T), etc. NÃO são passáveis.
  public static boolean passavel(char[][] grid, int i, int j) {
    char celula = grid[i][j];
    return celula != '#' && celula != 'E' && celula != 'M' && celula != 'T'
      && celula != 'P' && celula != 'D' && celula != 'S';
  }

  public static int[][] calcularOnda(char[][] grid, int destI, int destJ) {
    int linhas = grid.length;
    int colunas = grid[0].length;
    int[][] onda = new int[linhas][colunas];
    for (int[] linha : onda) Arrays.fill(linha, -1);

    Queue<int[]> fila = new Queue<>();
    onda[destI][destJ] = 0;
    fila.enqueue(new int[]{destI, destJ});

    while (!fila.empty()) {
      int[] atual = fila.front();
      fila.dequeue();

      int i = atual[0];
      int j = atual[1];

      for (int d = 0; d < DIRECOES.length; d++) {
        int ni = i + DIRECOES[d][0];
        int nj = j + DIRECOES[d][1];

        if (ni < 0 || ni >= linhas || nj < 0 || nj >= colunas)
          continue;

        if (onda[ni][nj] != -1) // já visitada
          continue;

        // Permite expandir a onda a partir da origem (mesmo se for 'E' ou 'M'),
        // mas bloqueia outras células intransitáveis pelo caminho.
        boolean eOrigem = (ni == destI && nj == destJ);
        if (!eOrigem && !passavel(grid, ni, nj))
          continue;

        onda[ni][nj] = onda[i][j] + 1;
        fila.enqueue(new int[]{ni, nj});
      }
    }

    return onda;
  }

  public static PositionDTO proximoPasso(int[][] onda, char[][] grid, int i, int j) {
    if (onda[i][j] == 0 || onda[i][j] == -1)
      return null;

    int linhas = onda.length;
    int colunas = onda[0].length;

    PositionDTO melhor = null;
    int menorDistancia = onda[i][j];

    for (int[] direcao : DIRECOES) {
      int ni = i + direcao[0];
      int nj = j + direcao[1];

      if (ni < 0 || ni >= linhas || nj < 0 || nj >= colunas)
        continue;

      int distanciaVizinho = onda[ni][nj];

      if (distanciaVizinho == -1)
        continue;

      if (!passavel(grid, ni, nj))
        continue;

      if (distanciaVizinho < menorDistancia) {
        menorDistancia = distanciaVizinho;
        melhor = new PositionDTO(nj, ni); // x = coluna, y = linha
      }
    }

    return melhor;
  }
}
