import java.util.Scanner;

public class Razo extends Empleado {
	private int horasExtras;
	private static double basico;
	
	public Razo() {}
	
	public Razo(int legajo) {
		super(legajo);
	}
	
	public Razo(int legajo, String nombre, String direccion, String telefono) {
		super(legajo, nombre, direccion, telefono);
	}

	public int getHorasExtras() {
		return horasExtras;
	}

	public void setHorasExtras(int horasExtras) {
		this.horasExtras = horasExtras;
	}
	
	/**
	 * Da el sueldo basico del empleado capacitado
	 */
	public static double getBasico() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		if (basico == 0) {
			System.out.println("Ingrese el valor del sueldo para empleados razos: ");
			basico = in.nextDouble();
		}
		return basico;
	}
	
	/**
	 * Liquida el sueldo del empleado
	 */
	public void liquidarSueldo()
	{
		System.out.println("Legajo: " + super.getLegajo() + " - Nombre: " + super.getNombre() + " - Sueldo: " + calcularSueldo());
	}
	
	/**
	 * Calcula el sueldo del empleado razo
	 */
	public double calcularSueldo() {
		return getBasico() + ((getBasico() * 0.1) * horasExtras);
	}
	
	/**
	 * Imprime en pantalla los detalles del empleado en caso de consulta
	 */	
	public void consultar() {
		super.consultar();
		System.out.println("Horas extras: " + horasExtras);
		System.out.println("Sueldo a liquidar: " + calcularSueldo());
	}
}
