public class Main {
    public static void main(String[] args) {
        // Grafo g = new Grafo();
        // Leitor leitor = new Leitor();
        // leitor.preencherGrafo(g, "TubosDeDistribuicaoCopagas.csv");

        Grafo g = PajekUtil.carregarPajek("TubosDeDistribuicaoCopagas.net");

        System.out.println("Vértices: " + Grafo.qtdVertices);
        System.out.println("Arestas: " + g.contarArestas());

        g.contarTipos();

        // try{
        //     PajekUtil.gerarPajek(g, "TubosDeDistribuicaoCopagas.net");
        // }
        // catch(IOException e){
        //     e.printStackTrace();
        // }
        
    }
}