public class Simple extends Evento {
	private Fecha fecha;
	
	public Simple() {}
	
	public Simple(Fecha fecha) {
		this.setFecha(fecha);
	}

	public Fecha getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = Fecha.fromString(fecha);
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}
	
	/**
	 * Imprime en pantalla los detalles del tipo del evento en caso de consulta
	 */
	public void consultar() {
		super.consultar();
		System.out.println("Fecha: " + fecha.toString());
	}
}
