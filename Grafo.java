import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Grafo {
    public HashMap<String, Vertice> tabelaAdjacencias;
    public static int qtdArestas = 0;
    public static int qtdVertices = 0;

    public Grafo() {
        this.tabelaAdjacencias = new HashMap<>();
    }

    public void adicionarAresta(String origem, String destino, Tubo tubo, String tipoOrigem, String tipoDestino) {
        // cria vértices se não existirem
        if (!tabelaAdjacencias.containsKey(origem)) {
            tabelaAdjacencias.put(origem, new Vertice(origem));
            Grafo.qtdVertices++;
        }
        if (!tabelaAdjacencias.containsKey(destino)) {
            tabelaAdjacencias.put(destino, new Vertice(destino));
            Grafo.qtdVertices++;
        }

        // NÃO é DIRECIONADO o mesmo tubo entra nos dois
        tabelaAdjacencias.get(origem).adicionarAdjacente(destino, tubo);
        tabelaAdjacencias.get(destino).adicionarAdjacente(origem, tubo);

        // marca o tipo de cada ponta
        tabelaAdjacencias.get(origem).marcarTipo(tipoOrigem);
        tabelaAdjacencias.get(destino).marcarTipo(tipoDestino);
    }

    // contagem simples
    public void contarTipos() {
        int dist = 0, conx = 0;
        for (Map.Entry<String, Vertice> e : tabelaAdjacencias.entrySet()) {
            if (e.getValue().tipo.equals("distribuicao")) dist++;
            else conx++;
        }
        System.out.println("Vértices de distribuição: " + dist);
        System.out.println("Vértices de conexão: " + conx);
    }

    public int contarArestas() {
        int somaGraus = 0;
        for (Map.Entry<String, Vertice> e : tabelaAdjacencias.entrySet()) {
            somaGraus += e.getValue().grau;
        }
        return somaGraus / 2;   
    }

    // VERIFICAR SE É CONEXO
    public boolean conexo() {
        if (tabelaAdjacencias.isEmpty()) return true;

        Set<String> visitados = new HashSet<>();
        Queue<String> fila = new LinkedList<>();

        // ESCOLHE UM VERTICE PARA INICIAR
        String inicio = tabelaAdjacencias.keySet().iterator().next();
        fila.add(inicio);
        visitados.add(inicio);

        while (!fila.isEmpty()) {
            String atual = fila.poll();
            for (String vizinho : tabelaAdjacencias.get(atual).adjacentes.keySet()) {
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    fila.add(vizinho);
                }
            }
        }

        return visitados.size() == tabelaAdjacencias.size();
    }

    // COMPONENTES
    public List<Set<String>> encontrarComponentes() {
        List<Set<String>> componentes = new ArrayList<>();
        Set<String> visitados = new HashSet<>();

        for (String id : tabelaAdjacencias.keySet()) {
            if (!visitados.contains(id)) {
                // BFS A PARTIR DE UM VERTICE NAO VISITADO (NOVO COMPONENTE)
                Set<String> componente = new HashSet<>();
                Queue<String> fila = new LinkedList<>();

                fila.add(id);
                visitados.add(id);

                while (!fila.isEmpty()) {
                    String atual = fila.poll();
                    componente.add(atual);
                    for (String vizinho : tabelaAdjacencias.get(atual).adjacentes.keySet()) {
                        if (!visitados.contains(vizinho)) {
                            visitados.add(vizinho);
                            fila.add(vizinho);
                        }
                    }
                }

                componentes.add(componente);
            }
        }

        return componentes;
    }

    public void exibirComponentes() {
        List<Set<String>> componentes = encontrarComponentes();

        if (componentes.size() == 1) {
            System.out.println("O grafo é conexo (apenas 1 componente com " + componentes.get(0).size() + " vértices)");
            return;
        }

        System.out.println("O grafo NÃO é conexo: " + componentes.size() + " componentes encontrados:\n");
        int i = 1;
        for (Set<String> comp : componentes) {
            System.out.println("  Componente " + i++ + " (" + comp.size() + " vértices): " + comp);
        }
    }

    public boolean euleriano() {
        // PRECISA SER CONEXO
        if (!conexo()) {
            System.out.println("NÃO Euleriano (grafo desconexo)");
            return false;
        }

        // Condição 2: todos os vértices devem ter grau par
        List<String> verticesImpares = new ArrayList<>();
        for (Map.Entry<String, Vertice> entry : tabelaAdjacencias.entrySet()) {
            if (entry.getValue().grau % 2 != 0) {
                verticesImpares.add(entry.getKey());
            }
        }

        if (verticesImpares.isEmpty()) {
            System.out.println("É Euleriano (Sem vértices com grau ímpar)");
            return true;
        } else if (verticesImpares.size() == 2) {
            System.out.println("É Euleriano (2 vértices com grau ímpar)");
            return true;
        } else {
            System.out.println("NÃO Euleriano (" + verticesImpares.size() + " vértices com grau ímpar");
            return false;
        }
    }

    public boolean ciclico() {
        Set<String> visitados = new HashSet<>();

        for (String id : tabelaAdjacencias.keySet()) {
            if (!visitados.contains(id)) {
                if (dfsCiclo(id, null, visitados)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsCiclo(String atual, String pai, Set<String> visitados) {
        visitados.add(atual);

        for (String vizinho : tabelaAdjacencias.get(atual).adjacentes.keySet()) {
            if (!visitados.contains(vizinho)) {
                if (dfsCiclo(vizinho, atual, visitados)) {
                    return true;
                }
            } else if (!vizinho.equals(pai)) {
                // Vizinho já visitado e não é o pai → ciclo encontrado
                return true;
            }
        }
        return false;
    }

    public static Grafo gerarGrafoAleatorio(int numVertices, int numArestas, boolean conexo) {
        if (numVertices <= 0){
            System.out.println("Número de vértices deve ser positivo.");
            return null;
        }
        if (numArestas < 0){
            System.out.println("Número de arestas não pode ser negativo.");
            return null;
        }

        // MAXIMO DE ARESTAS PARA GRAFO SIMPLES NAO DIRECIONADO: V*(V-1)/2
        int maxArestas = numVertices * (numVertices - 1) / 2;
        if (numArestas > maxArestas){
            System.out.println("Número de arestas (" + numArestas + ") excede o máximo possível (" + maxArestas + ").");
            return null;
        }

        // PELO MENOS V - 1 PARA SER CONEXO
        if (conexo && numArestas < numVertices - 1){
            System.out.println("Para um grafo conexo com " + numVertices + " vértices são necessárias pelo menos " + (numVertices - 1) + " arestas.");
            return null;
        }

        Grafo g = new Grafo();
        Random rand = new Random();

        // Nx AO INVÉS DE COORDENADAS
        List<String> ids = new ArrayList<>();
        for (int i = 1; i <= numVertices; i++) {
            ids.add("N" + i);
        }

        Set<String> arestasExistentes = new HashSet<>(); // EVITAR DUPLICAÇÃO DE ARESTAS

        
        if (conexo) { // GERAR AGM
            List<String> embaralhados = new ArrayList<>(ids);
            Collections.shuffle(embaralhados, rand);

            for (int i = 1; i < embaralhados.size(); i++) {
                String origem  = embaralhados.get(i);
                String destino = embaralhados.get(rand.nextInt(i)); // CONECTA A ALGUM JA INSERIDO
                Tubo tubo = gerarTuboAleatorio(rand, Grafo.qtdArestas + 1);
                g.adicionarAresta(origem, destino, tubo, sortearTipo(rand), sortearTipo(rand));
                arestasExistentes.add(chaveAresta(origem, destino));
                Grafo.qtdArestas++;
            }
        } 
        else { // ADICIONA OS VERTICES SEPARADOS
            for (String id : ids) {
                g.tabelaAdjacencias.put(id, new Vertice(id));
                Grafo.qtdVertices++;
            }
        }

        // COMPLETAR O NUMERO DE ARESTAS
        int tentativasMax = numArestas * 20;
        int tentativas = 0;
        int arestasInseridas = arestasExistentes.size();

        while (arestasInseridas < numArestas && tentativas < tentativasMax) {
            tentativas++;
            String origem  = ids.get(rand.nextInt(numVertices));
            String destino = ids.get(rand.nextInt(numVertices));

            if (origem.equals(destino)) continue; // EVITA LAÇOS
            if (arestasExistentes.contains(chaveAresta(origem, destino))) continue; // EVITA PARALELOS

            Tubo tubo = gerarTuboAleatorio(rand, Grafo.qtdArestas + 1);
            g.adicionarAresta(origem, destino, tubo, sortearTipo(rand), sortearTipo(rand));
            arestasExistentes.add(chaveAresta(origem, destino));
            Grafo.qtdArestas++;
            arestasInseridas++;
        }

        if (arestasInseridas < numArestas) {
            System.out.println("Aviso: só foi possível inserir " + arestasInseridas
                    + " de " + numArestas + " arestas sem repetição.");
        }

        return g;
    }

    private static String chaveAresta(String a, String b) {
        if (a.compareTo(b) < 0) return a + "-" + b;
        return b + "-" + a;
    }

    private static Tubo gerarTuboAleatorio(Random rand, int id) {
        String[] materiais = {"AC", "PEAD"};
        double[] dns = {50.0, 75.0, 100.0, 150.0, 200.0};
        double comprimento = 50.0  + rand.nextDouble() * 950.0;  // 50 – 1000 m
        double pressao = 2.0   + rand.nextDouble() * 8.0;    // 2  – 10 bar
        String material = materiais[rand.nextInt(materiais.length)];
        double dn = dns[rand.nextInt(dns.length)];
        return new Tubo(Math.round(comprimento * 100.0) / 100.0, material, dn,
                        Math.round(pressao * 100.0) / 100.0, id);
    }

    private static String sortearTipo(Random rand) {
        if(rand.nextDouble() < 0.3) return "distribuicao";
        return "conexao";
    }
}