public class TourAventura extends Tour {
    public static final double RECARGO_SEGURO = 0.15;
    public static final String DIFICULTAD_POR_DEFECTO = "medio";
    private String nivelDificultad;

    public TourAventura(String nombre, double precioBase, int cupoMaximo,
                        Guia guia, String nivelDificultad) {
        super(nombre, precioBase, cupoMaximo, guia);
        this.nivelDificultad = nivelDificultad;
    }

    public TourAventura(String nombre, double precioBase, int cupoMaximo, Guia guia) {
        this(nombre, precioBase, cupoMaximo, guia, DIFICULTAD_POR_DEFECTO);
    }

    @Override
    public double calcularPrecioPorPersona() {
        return precioBase * (1 + RECARGO_SEGURO);
    }

    @Override
    public String toString() {
        return super.toString() + "\nTipo: Aventura\nNivel de dificultad: " + nivelDificultad + "\n";
    }
}
