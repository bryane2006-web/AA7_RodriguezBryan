import java.util.ArrayList;

public abstract class Tour {
    public static final String PREFIJO_CODIGO = "TOUR-";
    public static final double DESCUENTO_NACIONAL = 0.20;

    private static int cantidadTours = 0;
    private String codigo;
    private String nombre;
    protected double precioBase;
    private int cupoMaximo;
    private Guia guia;
    protected ArrayList<Atractivo> atractivos;
    private ArrayList<Reserva> reservas;

    public Tour(String nombre, double precioBase, int cupoMaximo, Guia guia) {
        cantidadTours++;
        codigo = PREFIJO_CODIGO + cantidadTours;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.cupoMaximo = cupoMaximo;
        this.guia = guia;
        atractivos = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public static int getCantidadTours() { return cantidadTours; }
    public String consultarNombreGuia() { return guia.getNombreCompleto(); }
    public void cambiarGuia(Guia nuevaGuia) { guia = nuevaGuia; }

    public void agregarAtractivo(Atractivo atractivo) {
        if (atractivo != null && !atractivos.contains(atractivo)) {
            atractivos.add(atractivo);
        }
    }

    public String consultarNombresAtractivos() {
        StringBuilder nombres = new StringBuilder();
        for (Atractivo atractivo : atractivos) {
            if (nombres.length() > 0) nombres.append(", ");
            nombres.append(atractivo.getNombre());
        }
        return nombres.toString();
    }

    public abstract double calcularPrecioPorPersona();

    public final void registrarReserva(Cliente cliente) {
        registrarReserva(cliente, 1);
    }

    public final void registrarReserva(Cliente cliente, int cantidadPersonas) {
        if (!reservaValida(cliente, cantidadPersonas)) return;
        double monto = calcularMontoReserva(cliente, cantidadPersonas);
        reservas.add(new Reserva(reservas.size() + 1, cliente.getNombre(), cantidadPersonas, monto));
    }

    private boolean reservaValida(Cliente cliente, int cantidadPersonas) {
        return cliente != null && cantidadPersonas > 0 && cantidadPersonas <= getCuposDisponibles();
    }

    private double calcularMontoReserva(Cliente cliente, int cantidadPersonas) {
        double monto = calcularPrecioPorPersona() * cantidadPersonas;
        return cliente.esNacional() ? monto * (1 - DESCUENTO_NACIONAL) : monto;
    }

    public final int getCuposDisponibles() {
        int utilizados = 0;
        for (Reserva reserva : reservas) utilizados += reserva.cantidadPersonas;
        return cupoMaximo - utilizados;
    }

    public final double calcularIngresoTotal() {
        double ingreso = 0;
        for (Reserva reserva : reservas) ingreso += reserva.monto;
        return ingreso;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Código: ").append(codigo)
         .append("\nNombre: ").append(nombre)
         .append("\nGuía: ").append(consultarNombreGuia())
         .append("\nPrecio base: ₡").append(precioBase)
         .append("\nPrecio por persona: ₡").append(calcularPrecioPorPersona())
         .append("\nCupo máximo: ").append(cupoMaximo)
         .append("\nCupos disponibles: ").append(getCuposDisponibles())
         .append("\nAtractivos: ").append(consultarNombresAtractivos())
         .append("\nReservas (").append(reservas.size()).append("):");
        for (Reserva reserva : reservas) s.append("\n  ").append(reserva);
        s.append("\nIngreso total: ₡").append(calcularIngresoTotal());
        return s.toString();
    }

    // Composición: únicamente Tour puede crear y conservar sus reservas.
    private static class Reserva {
        private final int numero;
        private final String nombreCliente;
        private final int cantidadPersonas;
        private final double monto;

        private Reserva(int numero, String nombreCliente, int cantidadPersonas, double monto) {
            this.numero = numero;
            this.nombreCliente = nombreCliente;
            this.cantidadPersonas = cantidadPersonas;
            this.monto = monto;
        }

        @Override
        public String toString() {
            return "Reserva #" + numero + " | Cliente: " + nombreCliente +
                   " | Personas: " + cantidadPersonas + " | Monto: ₡" + monto;
        }
    }
}
