public class Main {
    public static void main(String[] args) {
        Grafo g = new Grafo();
        Leitor leitor = new Leitor();
        leitor.preencherGrafo(g, "TubosDeDistribuicaoCopagas.csv");

        System.out.println("Vértices: " + Grafo.qtdVertices);
        System.out.println("Arestas: " + g.contarArestas());

        g.contarTipos();
    }
}