import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Material {
	private int codigo;
	private TipoMaterial tipoMaterial;
	private Hashtable<String,Evento> ocupado;
	
	public Material() {
		this.ocupado = new Hashtable<String,Evento>();
	}
	
	public Material(int codigo) {
		this.codigo = codigo;
		this.ocupado = new Hashtable<String,Evento>();
	}
	
	public Material(int codigo, TipoMaterial tipoMaterial) {
		this.codigo = codigo;
		this.tipoMaterial = tipoMaterial;
		this.ocupado = new Hashtable<String,Evento>();
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public TipoMaterial getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(TipoMaterial tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}
	
	public Hashtable<String,Evento> getOcupado() {
		return ocupado;
	}

	public void setOcupado(Hashtable<String,Evento> ocupado) {
		this.ocupado = ocupado;
	}
	
	/** 
	 * Representación del material
	 */
	public String toString() {
		return("Código: " + codigo + " - Tipo de material: [" + tipoMaterial.toString() + "]");
	}
	
	/**
	 * Verifica si el codigo recibido coincide con el codigo del material
	 * @param codigo codigo a verificar
	 * @return true si coincide, false si no coincide
	 */
	public boolean sos(int codigo) {
		return(this.codigo == codigo);
	}
	
	/**
	 * Verifica si el material es del tipo de material recibido
	 * @param tipoMaterial tipo de material a verificar
	 * @return true si coincide, false si no coincide
	 */
	public boolean sos(TipoMaterial tipoMaterial) {
		return this.tipoMaterial.sos(tipoMaterial.getCodigo());
	}

	/**
	 * Imprime en pantalla los detalles del tipo de material en caso de consulta
	 */
	public void consultar() {
		System.out.println("Código: " + codigo);
		System.out.println("Tipo de material: " + tipoMaterial.toString());
		
		System.out.println("Fechas ocupadas: ");		
		for (String fecha: ocupado.keySet()) {
			System.out.println("Fecha: " + fecha + " - Evento: " + ocupado.get(fecha).toString());
		}
	}
	
	/**
	 * Devuelve si el salon esta disponible para una fecha
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
	 * Agrega un evento al material
	 * @param evento evento a agregar al material
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
	 * Quita un evento al material
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
