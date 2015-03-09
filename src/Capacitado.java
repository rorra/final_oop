import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Capacitado extends Empleado {
	private ArrayList<TipoMaterial> tipoMateriales;
	private static double sueldo;
	private Hashtable<String,Evento> ocupado;
	
	public Capacitado() {
		tipoMateriales = new ArrayList<TipoMaterial>();
		this.ocupado = new Hashtable<String,Evento>();
	}
	
	public Capacitado(int legajo) {		
		super(legajo);
		tipoMateriales = new ArrayList<TipoMaterial>();
		this.ocupado = new Hashtable<String,Evento>();
	}
	
	public Capacitado(int legajo, String nombre, String direccion, String telefono) {
		super(legajo, nombre, direccion, telefono);
		tipoMateriales = new ArrayList<TipoMaterial>();
		this.ocupado = new Hashtable<String,Evento>();
	}

	public ArrayList<TipoMaterial> getTipoMateriales() {
		return tipoMateriales;
	}

	public void setTipoMateriales(ArrayList<TipoMaterial> tipoMateriales) {
		this.tipoMateriales = tipoMateriales;
	}
	
	public Hashtable<String,Evento> getOcupado() {
		return ocupado;
	}

	public void setOcupado(Hashtable<String,Evento> ocupado) {
		this.ocupado = ocupado;
	}
	
	/**
	 * Da el sueldo del empleado capacitado
	 */
	public static double getSueldo() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		if (sueldo == 0) {
			System.out.println("Ingrese el valor del sueldo para empleados capacitados: ");
			sueldo = in.nextDouble();
		}
		return sueldo;
	}
	
	/**
	 * Agreaga un tipo de material que puede manejar el empleado capacitado
	 */
	public void agregarTipoMaterial(TipoMaterial tipoMaterial) {
		if (tipoMateriales.indexOf(tipoMaterial) == -1)
			tipoMateriales.add(tipoMaterial);
	}
	
	/**
	 * Liquida el sueldo del empleado
	 */
	public void liquidarSueldo()
	{
		System.out.println("Legajo: " + super.getLegajo() + " - Nombre: " + super.getNombre() + " - Sueldo: " + getSueldo());
	}
	
	/**
	 * Imprime en pantalla los detalles del empleado en caso de consulta
	 */	
	public void consultar() {
		super.consultar();
		System.out.println("Sueldo a liquidar: " + getSueldo());
		
		System.out.print("Tipo de materiales: ");
		for (int i = 0; i < tipoMateriales.size(); i++) {
			if (i == 0)
				System.out.print(tipoMateriales.get(i).getDescripcion());
			else
				System.out.print(", " + tipoMateriales.get(i).getDescripcion());
		}
		
		System.out.println("Fechas ocupadas: ");		
		for (String fecha: ocupado.keySet()) {
			System.out.println("Fecha: " + fecha.toString() + " - Evento: " + ocupado.get(fecha).toString());
		}
	}
	
	/**
	 * Devuelve si el empleado esta disponible para una fecha
	 * @param fecha fecha a consultar
	 * @return true si esta disponible, false caso contrario
	 */
	public boolean estasDisponible(ArrayList<Fecha> fechas) {
		boolean disponible = true;
		int i = 0;
		
		while(i < fechas.size() && disponible) {
			String fecha = fechas.get(i).toString();
			if (ocupado.get(fecha) != null)
				disponible = false;
			i++;
		}
		
		return disponible;
	}

	/**
	 * Verifica si el legajo coincide
	 * @param legajo legajo a verificar
	 * @return true si coincide, false caso contrario
	 */
	public boolean sos(int legajo) {
		return super.sos(legajo);
	}
	
	/**
	 * Verifica si el tipo de material coincide
	 * @param tipoMaterial tipo de material a verificar
	 * @return true si coincide, false caso contrario
	 */
	public boolean sos(TipoMaterial tipoMaterial) {
		TipoMaterial buscado = null;
		int i = 0;
		while(i < tipoMateriales.size() && buscado == null) {
			if (tipoMateriales.get(i).sos(tipoMaterial.getCodigo()))
				buscado = tipoMateriales.get(i);
			i++;
		}
		return(buscado != null);
	}
	
	/**
	 * Agrega un evento al empleado
	 * @param evento evento a agregar al empleado
	 */
	public void agregarEvento(Evento evento) {
		if (evento instanceof Simple) {
			this.ocupado.put(((Simple) evento).getFecha().toString(), evento);
		} else if (evento instanceof Complejo) {
			for (Fecha fecha: ((Complejo)evento).getSetFechas()) {
				this.ocupado.put(fecha.toString(), evento);
			}			
		}
	}
	
	/**
	 * Quita un evento al empleado
	 * @param evento evento a quitar
	 */
	public void quitarEvento(Evento evento) {
		if (evento instanceof Simple) {
			this.ocupado.remove(((Simple) evento).getFecha().toString());
		} else if (evento instanceof Complejo) {
			for (Fecha fecha: ((Complejo)evento).getSetFechas()) {
				this.ocupado.remove(fecha.toString());
			}			
		}		
	}
}
