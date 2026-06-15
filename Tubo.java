public class Tubo {
    public double comprimento;   // peso da aresta
    public String material;
    public double dn;
    public double pressao;
    public int idTubo;

    public Tubo(double comprimento, String material, double dn, double pressao, int idTubo) {
        this.comprimento = comprimento;
        this.material = material;
        this.dn = dn;
        this.pressao = pressao;
        this.idTubo = idTubo;
    }
}