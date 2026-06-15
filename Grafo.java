import java.util.HashMap;
import java.util.Map;

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
}