import java.util.HashMap;

public class Vertice {
    public String id;                           
    public HashMap<String, Tubo> adjacentes;   
    public int grau;                             
    public String tipo;                         

    public Vertice(String id) {
        this.id = id;
        this.grau = 0;
        this.adjacentes = new HashMap<>();
        this.tipo = "conexao";                   // começa conexao; vira distribuicao se algum tubo disser
    }

    // adiciona um vizinho com o tubo (aresta)
    public void adicionarAdjacente(String vizinho, Tubo tubo) {
        if (!adjacentes.containsKey(vizinho)) {
            adjacentes.put(vizinho, tubo);
            grau++;
        }
    }

    public void marcarTipo(String tipoDoSegmento) {
        if (tipoDoSegmento.equals("distribuicao")) {
            this.tipo = "distribuicao";
        }
    }
}