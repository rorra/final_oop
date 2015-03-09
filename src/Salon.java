import java.util.ArrayList;
import java.util.Hashtable;

public class Salon {
	private int codigo;
	private int capacidad;		
	private Hashtable<String,Evento> ocupado;
	
	public Salon() {
		this.ocupado = new Hashtable<String,Evento>();
	}
	
	public Salon(int codigo, int capacidad) {
		this.codigo = codigo;
		this.capacidad = capacidad;
		this.ocupado = new Hashtable<String,Evento>();
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public int getCapacidad() {
		return capacidad;
	}
	
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public Hashtable<String,Evento> getOcupado() {
		return ocupado;
	}

	public void setOcupado(Hashtable<String,Evento> ocupado) {
		this.ocupado = ocupado;
	}
	
	/** 
	 * Representación del salon
	 */
	public String toString() {
		return("Código: " + codigo + " - Capacidad: " + capacidad);
	}
	
	/**
	 * Verifica si el codigo recibido es igual al codigo del salon
	 * @param codigo codigo a verificar
	 * @return true si el codigo es igual, false caso contrario
	 */
	public boolean sos(int codigo) {
		return(this.codigo == codigo);
	}
	
	/**
	 * Imprime en pantalla los detalles del salon en caso de consulta
	 */	
	public void consultar() {
		System.out.println("Código: " + codigo);
		System.out.println("Capacidad: " + capacidad);
		
		System.out.println("Fechas ocupadas: ");		
		for (String fecha: ocupado.keySet()) {
			System.out.println("Fecha: " + fecha.toString() + " - Evento: " + ocupado.get(fecha).toString());
		}		
	}
	
	/**
	 * Devuelve si el salon esta disponible para una fecha
	 * @param fechas fechas a consultar
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
	 * Verifica si el salon tiene la capacidad disponible
	 * @param capacidad capacidad a comprobar
	 * @return true si tiene la capacidad, false caso contrario
	 */
	public boolean tenesCapacidad(int capacidad) {
		return this.capacidad >= capacidad;
	}
	
	/**
	 * Agrega un evento al salon
	 * @param evento evento a agregar al salon
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
	 * Quita un evento al salon
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
