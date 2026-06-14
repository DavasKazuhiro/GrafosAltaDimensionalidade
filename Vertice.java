import java.util.Map;

public class Vertice {
    Map<Integer, Double> adjacencias;
    String x;
    String y;
    long idTubo;

    public Vertice(String x, String y, long idTubo) {
        this.x = x;
        this.y = y;
        this.idTubo = idTubo;
    }

    public void adicionarAdjacencia(int destino, double distancia){
        adjacencias.put(destino, distancia);
    }
}
