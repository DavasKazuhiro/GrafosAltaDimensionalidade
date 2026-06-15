public class Main {
    public static void main(String[] args) {
        // Grafo g = new Grafo();
        // Leitor leitor = new Leitor();
        // leitor.preencherGrafo(g, "TubosDeDistribuicaoCopagas.csv");

        // Grafo g = PajekUtil.carregarPajek("TubosDeDistribuicaoCopagas.net");

        // System.out.println("Vértices: " + Grafo.qtdVertices);
        // System.out.println("Arestas: " + g.contarArestas());

        // g.contarTipos();
        // System.out.println("Conexo: " + g.conexo());
        // System.out.println("Quantidade de componentes: " + g.encontrarComponentes().size());
        // System.out.println("Euleriano: " + g.euleriano());
        // System.out.println("Ciclico: " + g.ciclico());

        Grafo g1 = Grafo.gerarGrafoAleatorio(6, 8, true);
        g1.exibirComponentes();
        g1.euleriano();

        // Grafo possivelmente desconexo com 6 vértices e 4 arestas
        Grafo g2 = Grafo.gerarGrafoAleatorio(6, 4, false);
        g2.exibirComponentes();

        // try{
        //     PajekUtil.gerarPajek(g, "TubosDeDistribuicaoCopagas.net");
        // }
        // catch(IOException e){
        //     e.printStackTrace();
        // }
    }
}