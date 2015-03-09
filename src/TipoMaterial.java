import java.util.ArrayList;

public class TipoMaterial {
	private int codigo;
	private String descripcion;
	private ArrayList<Material> materiales;
	
	public TipoMaterial() {
		materiales = new ArrayList<Material>();
	}
	
	public TipoMaterial(int codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		materiales = new ArrayList<Material>();
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public ArrayList<Material> getMateriales() {
		return materiales;
	}

	public void setMateriales(ArrayList<Material> materiales) {
		this.materiales = materiales;
	}
	
	/**
	 * Agrega un material al tipo de material
	 * @param material material a agregar
	 */
	public void agregarMaterial(Material material) {
		if (materiales.indexOf(material) == -1)
			materiales.add(material);
	}
	
	/** 
	 * Representación del tipo de material
	 */
	public String toString() {
		return("Código: " + codigo + " - Descripción: " + descripcion);
	}
	
	/**
	 * Verifica si el codigo recibido coincide con el codigo del tipo de material
	 * @param codigo codigo a verificar
	 * @return true si coincide, false si no coincide
	 */
	public boolean sos(int codigo) {
		return(this.codigo == codigo);
	}
	
	/**
	 * Imprime en pantalla los detalles del tipo de material en caso de consulta
	 */
	public void consultar() {
		System.out.println("Código: " + codigo);
		System.out.println("Descripción: " + descripcion);
		System.out.println("Materiales");
		System.out.println("==========");
		for(Material m: materiales) {
			System.out.println(m);
		}
	}
}
