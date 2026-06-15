## `conexo()`

Pergunta: *"Todos os vértices estão ligados entre si?"*

Usa BFS partindo de um vértice qualquer e verifica se consegue alcançar todos os outros.

```
Grafo:  N1 — N2 — N3    N4 (isolado)

1. Começa em N1, marca como visitado:        visitados = [N1]
2. Visita vizinhos de N1 → N2:               visitados = [N1, N2]
3. Visita vizinhos de N2 → N3:               visitados = [N1, N2, N3]
4. N3 não tem mais vizinhos novos
5. Fila vazia. visitados(3) ≠ total(4) → NÃO conexo ✗

Grafo:  N1 — N2 — N3 — N4

1. Começa em N1:    visitados = [N1]
2. Visita N2:       visitados = [N1, N2]
3. Visita N3:       visitados = [N1, N2, N3]
4. Visita N4:       visitados = [N1, N2, N3, N4]
5. visitados(4) == total(4) → É conexo ✓
```

---

## `encontrarComponentes()` e `exibirComponentes()`

Pergunta: *"Quais são os grupos isolados do grafo?"*

Mesma ideia do `conexo()`, mas salva cada grupo separadamente.

```
Grafo:  N1 — N2    N3 — N4    N5

1. Começa em N1 (não visitado) → BFS encontra [N1, N2]  → Componente 1
2. N2 já visitado, pula
3. Começa em N3 (não visitado) → BFS encontra [N3, N4]  → Componente 2
4. N4 já visitado, pula
5. Começa em N5 (não visitado) → BFS encontra [N5]      → Componente 3

Resultado:
  Componente 1 (2 vértices): [N1, N2]
  Componente 2 (2 vértices): [N3, N4]
  Componente 3 (1 vértices): [N5]
```

---

## `euleriano()`

Pergunta: *"Existe um caminho que percorre todas as arestas exatamente uma vez?"*

Duas regras simples: o grafo precisa ser conexo, e os graus dos vértices determinam o resultado.

```
Grafo:  N1 — N2 — N3 — N1  (triângulo)

Graus:  N1=2  N2=2  N3=2  → todos pares → É Euleriano ✓
Rota possível: N1→N2→N3→N1

Grafo:  N1 — N2 — N3 — N4

Graus:  N1=1  N2=2  N3=2  N4=1  → 2 ímpares → Semi-Euleriano
Rota possível: N1→N2→N3→N4 (tem início e fim diferentes)

Grafo:  N1 — N2
        |  × |
        N3 — N4

Graus:  N1=3  N2=3  N3=3  N4=3  → 4 ímpares → NÃO Euleriano ✗
```

---

## `ciclico()` e `dfsCiclo()`

Pergunta: *"Existe algum ciclo no grafo?"*

DFS caminha pelo grafo lembrando sempre de onde veio (pai). Se encontrar um vizinho já visitado que não seja o pai, achou um ciclo.

```
Grafo:  N1 — N2 — N3 — N1

DFS a partir de N1 (pai = null):
  Visita N2 (pai = N1)
    Visita N3 (pai = N2)
      Vizinho N1: já visitado e N1 ≠ pai(N2) → CICLO! ✓

Grafo:  N1 — N2 — N3

DFS a partir de N1 (pai = null):
  Visita N2 (pai = N1)
    Vizinho N1: já visitado MAS N1 == pai → ignora (é só a aresta de volta)
    Visita N3 (pai = N2)
      Sem vizinhos novos
  Fila vazia → SEM ciclo ✗
```

O detalhe importante é a checagem do **pai**: em grafos não-direcionados toda aresta aparece nos dois sentidos, então ignorar o pai evita confundir a aresta de volta com um ciclo real.

---

## `gerarGrafoAleatorio()`

Gera um grafo com N vértices e M arestas, conexo ou não.

```
Exemplo: 4 vértices, 5 arestas, conexo=true

Passo 1 — Embaralha vértices:
  [N3, N1, N4, N2]

Passo 2 — Monta árvore geradora (V-1 = 3 arestas):
  i=1: N1 conecta a N3    →  N3 — N1
  i=2: N4 conecta a N1    →  N3 — N1 — N4
  i=3: N2 conecta a N3    →  N2 — N3 — N1 — N4
  Grafo agora é conexo ✓

Passo 3 — Adiciona arestas aleatórias até chegar em 5:
  Sorteia N1-N2 → não existe ainda → adiciona  ✓
  Sorteia N3-N4 → não existe ainda → adiciona  ✓
  Total: 5 arestas ✓

Validações feitas antes de gerar:
  numVertices > 0          ✓
  numArestas <= V*(V-1)/2  ✓  (máximo possível sem paralelas)
  se conexo: arestas >= V-1✓  (mínimo para conectar tudo)
```