public class Usuario {
	private String usuario;
	private String password;
	private Perfil perfil;
	
	public Usuario() {}
	
	public Usuario(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
	}
	
	public Usuario(String usuario, String password, Perfil perfil) {
		this.usuario = usuario;
		this.password = password;
		this.perfil = perfil;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Perfil getPerfil() {
		return perfil;
	}
	
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	/**
	 * Verifica si tiene el mismo nombre de usuario y password
	 * @param usuario nombre de usuario a verificar
	 * @param password password a verificar
	 * @return true si tiene el mismo usuario y password, false en caso contrario
	 */
	public boolean sos(String usuario, String password) {
		return(this.usuario.compareTo(usuario) == 0 && this.password.compareTo(password) == 0);
	}
	
	/**
	 * Verifica si el usuario tiene el mismo nombre de usuario
	 * @param usuario nombre de usuario a verificar
	 * @return true si tiene el mismo nombre, false en caso contrario
	 */
	public boolean sos(String usuario) {
		return(this.usuario.compareTo(usuario) == 0);
	}
	
	/**
	 * Representacion del usuario en pantalla
	 */
	public String toString() {
		return(usuario + " - " + perfil.getNombre());
	}
	
	/**
	 * Imprime en pantalla los detalles del usuario en caso de consulta
	 */
	public void consultar() {
		System.out.println("Usuario: " + usuario);
		perfil.consultar();
	}
}
