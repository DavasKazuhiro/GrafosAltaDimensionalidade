import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {


        // CARREGA O GRAFO REAL
        Grafo g = PajekUtil.carregarPajek("TubosDeDistribuicaoCopagas.net");

        System.out.println("Vértices: " + Grafo.qtdVertices);
        System.out.println("Arestas: " + g.contarArestas());
        g.contarTipos();
        System.out.println();

        //FUNÇÕES BÁSICAS 
        System.out.println("Conexo: " + g.conexo());
        System.out.println("Quantidade de componentes: " + g.encontrarComponentes().size());
        g.euleriano();
        System.out.println("Ciclico: " + g.ciclico());
        System.out.println();

        //PROXIMIDADE (top 5 por componente) 
        System.out.println("=== CENTRALIDADE DE PROXIMIDADE ===");
        long t0 = System.currentTimeMillis();
        g.topProximidadePorComponente(5);
        long t1 = System.currentTimeMillis();
        System.out.println("\nProximidade concluída em " + (t1 - t0) / 1000.0 + "s");
        System.out.println();

        //  INTERMEDIAÇÃO (top 10 geral)
        System.out.println("=== CENTRALIDADE DE INTERMEDIAÇÃO ===");
        long t2 = System.currentTimeMillis();
        HashMap<String, Double> inter = g.intermediacaoTodos();
        long t3 = System.currentTimeMillis();
        System.out.println("Intermediação concluída em " + (t3 - t2) / 1000.0 + "s");
        mostrarTop(inter, 10, "INTERMEDIAÇÃO");
    }

    // MOSTRA O TOP N DE UM MAPA (IGNORA OS NULL)
    static void mostrarTop(HashMap<String, Double> mapa, int n, String titulo) {
        System.out.println("\nTop " + n + " - " + titulo + ":");

        // COPIA AS ENTRADAS PRA UMA LISTA E ORDENA (MAIOR PRIMEIRO)
        java.util.List<Map.Entry<String, Double>> lista = new java.util.ArrayList<>(mapa.entrySet());
        lista.sort((a, b) -> {
            if (a.getValue() == null) return 1;
            if (b.getValue() == null) return -1;
            return Double.compare(b.getValue(), a.getValue());
        });

        for (int i = 0; i < n && i < lista.size(); i++) {
            System.out.println("  " + lista.get(i).getKey() + " -> " + lista.get(i).getValue());
        }
    }
}