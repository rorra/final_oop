import java.util.ArrayList;

public class Perfil {
	private int codigo;
	private String nombre;
	private ArrayList<Permiso> permisos;
	
	public Perfil() {
		permisos = new ArrayList<Permiso>();
	}
	
	public Perfil(int codigo, String nombre) {
		permisos = new ArrayList<Permiso>();
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public Perfil(int codigo, String nombre, ArrayList<Permiso> permisos) {
		permisos = new ArrayList<Permiso>();
		this.codigo = codigo;
		this.nombre = nombre;
		this.permisos = permisos;
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
	
	public ArrayList<Permiso> getPermisos() {
		return permisos;
	}
	
	public void setPermisos(ArrayList<Permiso> permisos) {
		this.permisos = permisos;
	}
	
	/**
	 * Agrega un permiso a la lista de permisos para el perfil
	 * @param permiso permiso a agregar
	 */
	public void agregarPermiso(Permiso permiso) {
		if (permisos.indexOf(permiso) == -1)
			permisos.add(permiso);
	}
	
	/**
	 * Representacion del perfil
	 */
	public String toString() { 
		return codigo + " - " + nombre;
	}
	
	/**
	 * Recupera un permiso de la lista de permisos buscando su codigo
	 */
	public Permiso buscarPermiso(int codigo) {
		int i = 0;
		Permiso buscado = null;
		while(i < permisos.size() && buscado == null) {
			if (permisos.get(i).sos(codigo))
				buscado = permisos.get(i);
			i++;
		}
		return buscado;
	}
	
	/**
	 * Imprime en pantalla los detalles del perfil en caso de consulta
	 */
	public void consultar() {
		System.out.println("Perfil: " + nombre);
		System.out.print("Permisos: ");
		for (int i = 0; i < permisos.size(); i++) {
			if (i == 0)
				System.out.print(permisos.get(i).getNombre());
			else
				System.out.print(" | " + permisos.get(i).getNombre());
		}
	}
	
	/**
	 * Verifica si el perfil tiene el codigo recibido como parametro
	 * @param codigo codigo a verificar
	 * @return true si tiene el mismo codigo, caso contrario false
	 */
	public boolean sos(int codigo) {
		return(this.codigo == codigo);
	}
}
