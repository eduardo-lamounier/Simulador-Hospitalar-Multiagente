/**
 * Implementação do algoritmo de Wavefront (propagação de onda / BFS a partir
 * do destino), usado para guiar a movimentação dos pacientes pelo grid do
 * hospital.
 *
 * Ideia geral: a partir de uma célula de destino, o algoritmo "inunda" o
 * grid célula por célula, camada por camada (como uma onda se espalhando),
 * numerando cada célula alcançada com a sua distância (em passos) até o
 * destino. Depois, para mover um agente, basta ele olhar as células
 * vizinhas e andar sempre para a de menor número — isso garante o caminho
 * mais curto, sem precisar refazer a busca a cada passo (o mapa de
 * distâncias já foi calculado uma única vez por objetivo).
 */
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

  // Verifica se a célula (i, j) do grid é transponível (não é parede,
  // enfermeira ou médico).
  public static boolean passavel(char[][] grid, int i, int j) {
    char celula = grid[i][j];
    return celula != '#' && celula != 'E' && celula != 'M';
  }

  // Calcula o mapa de distâncias (onda) a partir da célula de destino
  // (destI, destJ) até cada célula alcançável do grid
  // Células não alcançáveis (ou bloqueadas) ficam com valor -1.
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
        if (!passavel(grid, ni, nj))
          continue;

        onda[ni][nj] = onda[i][j] + 1;
        fila.enqueue(new int[]{ni, nj});
      }
    }

    return onda;
  }

  // A partir da posição atual (i, j) e do mapa de distâncias 'onda' (já
  // calculado com calcularOnda), retorna a posição vizinha de menor
  // distância até o destino — ou seja, o próximo passo do caminho mais
  // curto.
  //
  // Retorna 'null' se a posição atual já for o destino (distância 0), se
  // ela não tiver caminho até o destino (-1), ou se nenhum vizinho tiver
  // distância menor que a atual (agente cercado).
  public static PositionDTO proximoPasso(int[][] onda, int i, int j) {
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

      if (distanciaVizinho < menorDistancia) {
        menorDistancia = distanciaVizinho;
        melhor = new PositionDTO(nj, ni); // x = coluna, y = linha
      }
    }

    return melhor;
  }
}
