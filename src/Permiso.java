public class Permiso {
	private int codigo;
	private String nombre;
	
	public Permiso() {}
	
	public Permiso(int codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Representación del permiso
	 */
	public String toString() {
		return(codigo + " - " + nombre);
	}
	
	/**
	 * Verifica si el permiso tiene el mismo codigo
	 * @param codigo codigo a verificar
	 * @return true si tiene el mismo codigo, false caso contrario
	 */
	public boolean sos(int codigo) {
		return(this.codigo == codigo);
	}
}
