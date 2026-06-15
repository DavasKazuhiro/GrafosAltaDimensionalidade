import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PajekUtil {
    // GRAVADOR
    public static void gerarPajek(Grafo g, String caminho){
        // ATRIBUI UM INDICE PARA CADA COORDENADA
        Map<String, Integer> indice = new HashMap<>();
        int i = 1;
        for (String coordenada : g.tabelaAdjacencias.keySet()) {
            indice.put(coordenada, i++);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            // GRAVACAO DOS VERTICES
            bw.write("*Vertices " + g.tabelaAdjacencias.size());
            bw.newLine();
            for (Map.Entry<String, Vertice> entry : g.tabelaAdjacencias.entrySet()) {
                Vertice v = entry.getValue();
                // FORMATO: <indice> "<coordenada>" tipo:<tipo>
                bw.write(indice.get(v.coordenada) + " \"" + v.coordenada + "\" tipo:" + v.tipo);
                bw.newLine();
            }

            // ARESTAS
            bw.write("*Edges");
            bw.newLine();

            // Evita duplicatas: só escreve aresta (u,v) quando índice(u) < índice(v)
            Set<String> escritas = new HashSet<>();
            for (Map.Entry<String, Vertice> entry : g.tabelaAdjacencias.entrySet()) {
                String origemId = entry.getKey();
                Vertice v = entry.getValue();
                for (Map.Entry<String, Tubo> adj : v.adjacentes.entrySet()) {
                    String destinoId = adj.getKey();
                    Tubo tubo = adj.getValue();

                    int u = indice.get(origemId);
                    int w = indice.get(destinoId);
                    String chave = Math.min(u, w) + "-" + Math.max(u, w);

                    if (!escritas.contains(chave)) {
                        escritas.add(chave);
                        // FORMATO: <u> <v> <peso> idTubo:<id> material:<mat> dn:<dn> pressao:<p>
                        bw.write(u + " " + w + " " + tubo.comprimento
                                + " idTubo:" + tubo.idTubo
                                + " material:" + tubo.material
                                + " dn:" + tubo.dn
                                + " pressao:" + tubo.pressao);
                        bw.newLine();
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    // LEITOR
    public static Grafo carregarPajek(String caminho){
        Grafo g = new Grafo();

        // INDICE PARA ID
        Map<Integer, String> coordenadaIndice = new HashMap<>();
        // Guarda o tipo de cada vértice para reaplicar depois
        Map<String, String> tipoCoordenada = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            String secao = "";

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                // Detecta cabeçalho de seção
                if (linha.startsWith("*")) {
                    secao = linha.toLowerCase();
                    continue;
                }

                if (secao.contains("*vertices")) {
                    // FORMATO: <indice> "<id>" tipo:<tipo>
                    String[] partes = linha.split("\\s+", 3);
                    int id = Integer.parseInt(partes[0]);
                    String coordenada = partes[1].replace("\"", "");
                    String tipo = partes[2].replace("tipo:", "").trim();

                    coordenadaIndice.put(id, coordenada);
                    tipoCoordenada.put(coordenada, tipo);

                    g.tabelaAdjacencias.put(coordenada, new Vertice(coordenada));
                    g.tabelaAdjacencias.get(coordenada).marcarTipo(tipo);
                    Grafo.qtdVertices++;

                } else if (secao.startsWith("*edges")) {
                    // FORMATO: <u> <v> <comprimento> idTubo:<id> material:<mat> dn:<dn> pressao:<p>
                    String[] partes = linha.split("\\s+");
                    int u = Integer.parseInt(partes[0]);
                    int v = Integer.parseInt(partes[1]);
                    double comprimento = Double.parseDouble(partes[2]);

                    // ATRIBUTOS DO TUBO
                    int idTubo = 0;
                    String material = "";
                    double dn = 0, pressao = 0;

                    for (int k = 3; k < partes.length; k++) {
                        if (partes[k].startsWith("idTubo:"))
                            idTubo = Integer.parseInt(partes[k].split(":")[1]);
                        else if (partes[k].startsWith("material:"))
                            material = partes[k].split(":")[1];
                        else if (partes[k].startsWith("dn:"))
                            dn = Double.parseDouble(partes[k].split(":")[1]);
                        else if (partes[k].startsWith("pressao:"))
                            pressao = Double.parseDouble(partes[k].split(":")[1]);
                    }

                    String origem  = coordenadaIndice.get(u);
                    String destino = coordenadaIndice.get(v);
                    Tubo tubo = new Tubo(comprimento, material, dn, pressao, idTubo);

                    String tipoOrigem  = tipoCoordenada.get(origem);
                    String tipoDestino = tipoCoordenada.get(destino);

                    g.tabelaAdjacencias.get(origem).adicionarAdjacente(destino, tubo);
                    g.tabelaAdjacencias.get(destino).adicionarAdjacente(origem, tubo);
                    g.tabelaAdjacencias.get(origem).marcarTipo(tipoOrigem);
                    g.tabelaAdjacencias.get(destino).marcarTipo(tipoDestino);
                    Grafo.qtdArestas++;
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }

        return g;
    }
}
