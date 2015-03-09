import java.util.Scanner;

public class Ejecutora {

	public static void main(String[] args) {
		Sistema sistema = new Sistema();
		Hotel hotel = new Hotel();
		
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		sistema.cargarPermisos();
		sistema.cargarPerfiles();
		sistema.cargarUsuarios();
		hotel.cargarTipoMateriales();
		hotel.cargarMateriales();
		hotel.cargarEmpleados();
		hotel.cargarSalones();
		
		
		Usuario usuario = sistema.autentificar();
		if (usuario == null) {
			System.out.println("Acceso denegado");
		} else {
			int opcion;
			
			do {
				opcion = sistema.obtenerOpcion(usuario);
				
				switch(opcion) {
				case 1:
					hotel.altaEmpleado();
					break;
				case 2:
					hotel.listarEmpleados();
					break;
				case 3:
					hotel.consultarEmpleado();
					break;
				case 4:
					hotel.bajaDefinitivaEmpleado();
					break;
				case 5:
					hotel.liquidarSueldos();
					break;
				case 6:
					hotel.altaTipoMaterial();
					break;
				case 7:
					hotel.listarTipoMateriales();
					break;
				case 8:
					hotel.consultarTipoMaterial();
					break;
				case 9:
					hotel.bajaDefinitivaTipoMaterial();
					break;
				case 10:
					hotel.altaMaterial();
					break;
				case 11:
					hotel.listarMateriales();
					break;
				case 12:
					hotel.consultarMaterial();
					break;
				case 13:
					hotel.bajaDefinitivaMaterial();
					break;
				case 14:
					hotel.altaSalon();
					break;
				case 15:
					hotel.listarSalones();
					break;
				case 16:
					hotel.consultarSalon();
					break;
				case 17:
					hotel.bajaDefinitivaSalon();
					break;
				case 18:
					hotel.altaEvento();
					break;
				case 19:
					hotel.listarEventos();
					break;
				case 20:
					hotel.listarEventosCondicionados();
					break;
				case 21:
					hotel.consultarEvento();
					break;
				case 22:
					hotel.bajaDefinitivaEvento();
					break;
				case 23:
					hotel.altaInscripciones();
					break;
				case 24:
					hotel.bajaInscripciones();
					break;
				case 25:
					sistema.altaUsuario();
					break;
				case 26:
					sistema.listarUsuarios();
					break;
				case 27:
					sistema.consultarUsuario();
					break;
				case 28:
					sistema.modificarUsuario();
					break;
				case 29:
					sistema.bajaDefinitivaUsuario();
					break;
				case 30:
					sistema.altaPerfil();
					break;
				case 31:
					sistema.listarPerfiles();
					break;
				case 32:
					sistema.consultarPerfil();
					break;
				case 33:
					sistema.modificarPerfil();
					break;
				case 34:
					sistema.bajaDefinitivaPerfil();
					break;
				case 35:
					sistema.listarPermisos();
					break;
				}
			} while (opcion != 0);			
			
		}		
	}
}
