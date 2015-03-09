public abstract class Empleado {
	private int legajo;
	private String nombre;
	private String direccion;
	private String telefono;
	
	public Empleado() {}
	
	public Empleado(int legajo) {
		this.legajo = legajo;
	}
	
	public Empleado(int legajo, String nombre, String direccion, String telefono) {
		this.legajo = legajo;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	
	public int getLegajo() {
		return legajo;
	}
	
	public void setLeagjo(int legajo) {
		this.legajo = legajo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	/**
	 * Representacion del empleado
	 */
	public String toString() {
		return("Legajo: " + legajo + " - Nombre: " + nombre + " - Dirección: " + direccion + " - Teléfono: " + telefono);
	}
	
	/**
	 * Verifica si el legajo recibido coincide con el legajo del empleado
	 * @param legajo legajo a verificar
	 * @return true si coincide, false si no coincide
	 */
	public boolean sos(int legajo) {
		return(this.legajo == legajo);
	}
	
	/**
	 * Liquida el sueldo de un empleado
	 */
	public abstract void liquidarSueldo();
	
	/**
	 * Imprime en pantalla los detalles del empleado en caso de consulta
	 */	
	public void consultar() {
		System.out.println("Legajo: " + legajo);
		System.out.println("Nombre: " + nombre);
		System.out.println("Dirección: " + direccion);
		System.out.println("Teléfono: " + telefono);
	}
}
