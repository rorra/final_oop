import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Complejo extends Evento {
	private Hashtable<Fecha, Boolean> fechas;
	
	public Complejo() {
		fechas = new Hashtable<Fecha, Boolean>();
	}

	public Hashtable<Fecha, Boolean> getFechas() {
		return fechas;
	}

	public void setFechas(Hashtable<Fecha, Boolean> fechas) {
		this.fechas = fechas;
	}
	
	public void setFechas(ArrayList<Fecha> fechasReservadas) {
		for (Fecha fecha: fechasReservadas) {
			fechas.put(fecha, true);
		}
	}
	
	public Set<Fecha> getSetFechas() {
		return(fechas.keySet());
	}
	
	public ArrayList<Fecha> getArrayFechas() {
		ArrayList<Fecha> array = new ArrayList<Fecha>();
		array.addAll(getSetFechas());
		return(array);
	}
	
	/**
	 * Imprime en pantalla los detalles del tipo del evento en caso de consulta
	 */
	public void consultar() {
		super.consultar();
		System.out.println("Fechas");
		System.out.println("======");
		for(Fecha fecha: getSetFechas()) {
			System.out.println(fecha.toString());
		}
	}
}
