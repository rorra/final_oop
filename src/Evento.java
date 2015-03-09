import java.util.ArrayList;

public abstract class Evento {
	private int codigo;
	private boolean condicionado;
	private Salon salon;
	private ArrayList<Material> materiales;
	private ArrayList<Empleado> empleados;
	private int inscripciones;
	
	public Evento() {
		materiales = new ArrayList<Material>();
		empleados = new ArrayList<Empleado>();
	}
		
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public boolean getCondicionado() {
		return(condicionado);
	}
	
	public void setCondicionado(boolean condicionado) {
		this.condicionado = true;
	}
	
	public Salon getSalon() {
		return salon;
	}
	
	public void setSalon(Salon salon) {
		this.salon = salon;
	}
	
	public ArrayList<Material> getMateriales() {
		return materiales;
	}
	
	public void setMateriales(ArrayList<Material> materiales) {
		this.materiales = materiales;
	}
	
	public ArrayList<Empleado> getEmpleados() {
		return empleados;
	}
	
	public void setEmpleados(ArrayList<Empleado> empleados) {
		this.empleados = empleados;
	}
	
	/** 
	 * Representación del evento
	 */
	public String toString() {
		return("Código: " + codigo + " - Salón: [" + salon.toString() + "] - Inscripciones: " + inscripciones);
	}
	
	/**
	 * Verifica si el codigo recibido es igual al codigo del evento
	 * @param codigo codigo a verificar
	 * @return true si el codigo es igual, false caso contrario
	 */
	public boolean sos(int codigo) {
		return(this.codigo == codigo);
	}
	
	/**
	 * Verifica si el evento esta condicionado
	 * @return true si esta condicionado, false caso contrario
	 */
	public boolean estasCondicionado() {
		return(condicionado == true);
	}
	
	/**
	 * Devuelve la cantidad de inscripciones disponibles
	 * @return
	 */
	public int inscripcionesDisponibles() {
		return salon.getCapacidad() - inscripciones;
	}
	

	/**
	 * Agrega inscripciones al evento
	 * @param cantidad cantidad de inscripciones a agregar
	 * @return true si se pudo agregar, false caso contrario
	 */
	public boolean agregarInscripciones(int cantidad) {
		if (cantidad <= inscripcionesDisponibles()) {
			inscripciones += cantidad;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Quita inscripciones del evento
	 * @param cantidad cantidad de inscripciones a quitar
	 */
	public void quitarInscripciones(int cantidad) {
		if (cantidad <= inscripciones)
			inscripciones -= inscripciones;
		else
			inscripciones = 0;
	}
	
	/**
	 * Agrega un salon al evento
	 * @param salon
	 */
	public void agregarSalon(Salon salon) {
		this.salon = salon;
		this.salon.agregarEvento(this);
	}
	
	/**
	 * Quita un salon del evento
	 */
	public void quitarSalon() {
		salon.quitarEvento(this);
		salon = null;
	}
	
	/**
	 * Agrega un material al evento
	 * @param material
	 */
	public void agregarMaterial(Material material) {
		if (materiales.indexOf(material) == -1) {
			materiales.add(material);
			material.agregarEvento(this);
		}		
	}
	
	/**
	 * Quita los materiales del evento
	 */
	public void quitarMateriales() {
		for(Material material: materiales) {
			material.quitarEvento(this);
		}
		materiales.clear();
	}
	
	/**
	 * Quita un material en especifico
	 */
	public void quitarMaterial(Material material) {
		materiales.remove(material);
	}
	
	/**
	 * Agrega un empleado capacitado al evento
	 * @param capacitado
	 */
	public void agregarCapacitado(Capacitado capacitado) {
		if (empleados.indexOf(capacitado) == -1) {
			empleados.add(capacitado);
			capacitado.agregarEvento(this);
		}
	}
	
	/**
	 * Quita los empleados capacitados del evento
	 */
	public void quitarEmpleados() {
		for(Empleado empleado: empleados) {
			((Capacitado)empleado).quitarEvento(this);
		}
	}
	
	/**
	 * Quita un empleado en especifico del evento
	 */
	public void quitarEmpleado(Empleado empleado) {
		empleados.remove(empleado);
	}
	
	/**
	 * Imprime en pantalla los detalles del tipo del evento en caso de consulta
	 */
	public void consultar() {
		System.out.println("Codigo: " + codigo);
		System.out.println("Condicionado: " + condicionado);
		System.out.println("Salón: " + salon.toString());
		System.out.println("Inscripciones: " + inscripciones);
		System.out.println("Materiales");
		System.out.println("==========");
		for(Material m: materiales) System.out.println(m.toString());
		System.out.println("Empleados");
		System.out.println("=========");
		for(Empleado e: empleados) System.out.println(e.toString());
	}
}
