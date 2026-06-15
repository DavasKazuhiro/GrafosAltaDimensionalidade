import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Leitor {
    public void preencherGrafo(Grafo g, String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            boolean primeira = true;

            while ((linha = br.readLine()) != null) {

                if (primeira) {           // pula o cabeçalho
                    primeira = false;
                    continue;
                }

                String[] c = linha.split(",");
                // colunas: x_origem, y_origem, x_destino, y_destino, comprimento, id_tubo, tipo_origem, tipo_destino, material, dn, pressao
                String origem  = c[0] + "," + c[1];
                String destino = c[2] + "," + c[3];
                double comprimento = Double.parseDouble(c[4]);
                int idTubo = (int) Double.parseDouble(c[5]);
                String tipoOrigem  = c[6];
                String tipoDestino = c[7];
                String material = c[8];
                double dn = Double.parseDouble(c[9]);
                double pressao = Double.parseDouble(c[10]);

                Tubo tubo = new Tubo(comprimento, material, dn, pressao, idTubo);
                g.adicionarAresta(origem, destino, tubo, tipoOrigem, tipoDestino);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}