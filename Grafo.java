import java.util.Map;

public class Grafo {
    Vertice[] vertices;
    String[] rotulos;
    Map<String, Integer> indices;

    public void adicionarVertice(int id, String x, String y, long idTubo){
        vertices[id] = new Vertice(x, y, idTubo);
        rotulos[id] = x + y;
        indices.put(x + y, id);
    }

    public void adicionarAdjacencia(int id1, int id2, double distancia){
        vertices[id1].adicionarAdjacencia(id2, distancia);
        vertices[id2].adicionarAdjacencia(id1, distancia);
    }

    
}
