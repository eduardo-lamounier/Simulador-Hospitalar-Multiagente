
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

