
/**
 * Implementação do algoritmo de Wavefront (propagação de onda / BFS a partir
 * do destino), usado para guiar a movimentação dos pacientes pelo grid do
 * hospital.
 *
 * Ideia geral: a partir de uma célula de destino, o algoritmo "inunda" o
 * grid célula por célula, camada por camada (como uma onda se espalhando),
 * numerando cada célula alcançada com a sua distância (em passos) até o
 * destino. Depois, para mover um agente, basta ele olhar as células
 * vizinhas e andar sempre para a de meno:r número — isso garante o caminho
 * mais curto, sem precisar refazer a busca a cada passo (o mapa de
 * distâncias já foi calculado uma única vez).
**/ 
import java.util.Arrays;
import java.util.function.BiPredicate;
 
import estruturas.Queue;

public class WaveFront{
  public static final int [][]DIRECOES = {
    {1,0},
    {0,1},
    {-1,0},
    {0,-1}
  };
  private WaveFront(){}

  public static boolean InstransponivelPadrao(char [][]grid, int i, int j){
    char celula = grid[i][j];
    return celula != '#' && !='E' && !='M';
  }
  public static int[][] calcularOnda(char[][] grid, int destI, int destJ, BiPredicate<Integer, Integer> transponivel){
    int linhas = grid.length;
    int colunas = grid[0].length;
    int[][] onda = new int[linhas][colunas]; 
  } 
  Queue<int[]> fila = new Queue<>;
  onda[destI][destJ] = 0;
  fila.deqeue();

  while (!fila.empty()) {
  int[] atual = fila.front();
  fila.dequeue();
 
  int i = atual[0];
  int j = atual[1];
  for (int d = 0; d < 4; d++) {
    int ni = i + DI[d];
    int nj = j + DJ[d];
    if (ni < 0 || ni >= linhas || nj < 0 || nj >= colunas)
      continue; 
    if (onda[ni][nj] != -1) // já visitada
      continue;
    if (!transponivel.test(ni, nj){
      continue;
      onda[ni][nj] = onda[i][j] + 1;
      fila.enqueue(new int[]{ni, nj});
            }
        }
  return onda;
  }


}
