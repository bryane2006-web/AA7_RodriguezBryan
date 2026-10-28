public class TourCultural extends Tour {
    private String idioma;

    public TourCultural(String nombre, double precioBase, int cupoMaximo,
                        Guia guia, String idioma) {
        super(nombre, precioBase, cupoMaximo, guia);
        this.idioma = idioma;
    }

    @Override
    public double calcularPrecioPorPersona() {
        double total = precioBase;
        for (Atractivo atractivo : atractivos) total += atractivo.getMontoEntrada();
        return total;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTipo: Cultural\nIdioma: " + idioma + "\n";
    }
}
