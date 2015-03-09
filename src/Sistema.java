import java.util.ArrayList;
import java.util.Scanner;

public class Sistema {
	private ArrayList<Usuario> usuarios;
	private ArrayList<Perfil> perfiles;
	private ArrayList<Permiso> permisos;
	
	/**
	 * Constructor
	 */
	public Sistema() {
		usuarios = new ArrayList<Usuario>();
		perfiles = new ArrayList<Perfil>();
		permisos = new ArrayList<Permiso>();
	}
	
	/**
	 * Devuelve un array con los usuarios del sistema
	 * @return usuarios del sistema
	 */
	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}
	
	/**
	 * Setea los usuarios del sistema
	 * @param usuarios usuarios del sistema
	 */
	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	/**
	 * Devuelve un array con los perfiles del sistema
	 * @return perfiles del sistema
	 */
	public ArrayList<Perfil> getPerfiles() {
		return perfiles;
	}
	
	/**
	 * Setea los perfiles del sistema
	 * @param perfiles perfiles del sistema
	 */
	public void setPerfiles(ArrayList<Perfil> perfiles) {
		this.perfiles = perfiles;
	}
	
	/**
	 * Devuelve un array con los permisos del sistema
	 * @return permisos del sistema
	 */
	public ArrayList<Permiso> getPermisos() {
		return permisos;
	}
	
	/**
	 * Setea una rray con los permisos del sistema
	 * @param permisos permisos del sistema
	 */
	public void setPermisos(ArrayList<Permiso> permisos) {
		this.permisos = permisos;
	}
	
	/**
	 * Caso de uso, da de alta un usuario en el sistema
	 */
	public void altaUsuario() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));

		System.out.print("Ingrese el usuario: ");
		String usr = in.next();
		
		Usuario usuario = buscarUsuario(usr);
		if (usuario == null) {
			System.out.print("Ingrese el password: ");			
			String pwd = in.next();
			
			System.out.println("Ingrese el codigo de perfil");
			for(Perfil perfil: perfiles) {
				System.out.println(perfil);
			}
			System.out.print("Digite su opción: ");
			int codigo = in.nextInt();
			Perfil perfil = buscarPerfil(codigo);
			while (perfil == null) {
				System.out.print("El perfil no es válido, ingrese un código de perfil valido: ");
				codigo = in.nextInt();
				perfil = buscarPerfil(codigo);
			}
			
			usuarios.add(new Usuario(usr, pwd, perfil));
			System.out.println("Se agrego el usuario " + usr);
		} else {
			System.out.println("Error, el usuario ya existe");
		}
	}
	
	/**
	 * Caso de uso, lista los usuarios del sistema
	 */
	public void listarUsuarios() {
		System.out.println("Usuarios");
		System.out.println("========");		
		for (Usuario u: usuarios) {
			System.out.println(u);
		}
	}
	
	/**
	 * Caso de uso, consulta un usuario del sistema
	 */
	public void consultarUsuario() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el usuario a consultar: ");
		String usr = in.next();
		
		Usuario usuario = buscarUsuario(usr);
		if (usuario != null) {
			usuario.consultar();
			System.out.println("Fin de la consulta");
		} else {
			System.out.println("Error, el usuario no existe");
		}
	}
	
	/**
	 * Caso de uso, modifica un usuario en el sistema
	 */
	public void modificarUsuario() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el usuario a modificar: ");
		String usr = in.next();
		
		Usuario usuario = buscarUsuario(usr);
		if (usuario != null) {
			String answer;
			System.out.print("Modificar el login del usuario? S/N: ");
			answer = in.next();
			if (answer.compareToIgnoreCase("S") == 0) {
				System.out.print("Ingrese el nuevo login para el usuario: ");
				usuario.setUsuario(in.next());
			}
			
			System.out.print("Modificar el password del usuario? S/N: ");
			answer = in.next();
			if (answer.compareToIgnoreCase("S") == 0) {
				System.out.print("Ingrese el nuevo password para el usuario: ");
				usuario.setPassword(in.next());				
			}
			
			System.out.print("Modificar el perfil del usuario? S/N: ");
			answer = in.next();
			if (answer.compareToIgnoreCase("S") == 0) {
				System.out.println("Perfiles disponibles");
				System.out.println("====================");
				for(Perfil p: perfiles) {System.out.println(p);}
				System.out.print("Ingrese el código del perfil que desea asignar al usuario: ");
				Perfil perfil = buscarPerfil(in.nextInt());
				while(perfil == null) {
					System.out.print("Código invalido, ingrese el código del perfil que desea asignar: ");
					perfil = buscarPerfil(in.nextInt());
				}
				usuario.setPerfil(perfil);				
			}
			
			System.out.println("Usuario " + usuario.getUsuario() + " modificado");
		} else {
			System.out.println("Error, el usuario no existe");
		}		
	}
	
	/**
	 * Caso de uso, da de baja un usuario del sistema en forma definitiva
	 */
	public void bajaDefinitivaUsuario() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el usuario a eliminar: ");
		String usr = in.next();
		
		Usuario usuario = buscarUsuario(usr);
		if (usuario != null) {
			usuarios.remove(usuario);
			System.out.println("Usuario " + usuario.getUsuario() + " dado de baja en forma definitiva");
		} else {
			System.out.println("Error, el usuario no existe");
		} 
	}
	
	/**
	 * Caso de uso, da de alta un perfil en el sistema
	 */
	public void altaPerfil() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo: ");
		int codigo = in.nextInt();
		
		Perfil perfil = buscarPerfil(codigo);
		if (perfil == null) {
			System.out.print("Ingrese el nombre: ");
			String nombre = in.next();
			
			perfil = new Perfil(codigo, nombre);
			
			listarPermisos();
			int opcion;
			Permiso permiso;
			do {
				System.out.print("Ingrese un codigo de permiso para agregar al perfil (0 para terminar): ");
				opcion = in.nextInt();
				permiso = buscarPermiso(opcion);
				if (permiso != null) {
					perfil.agregarPermiso(permiso);
				} else if (opcion != 0) {
					System.out.println("Código inválido");
				}
			} while(opcion != 0);
			
			perfiles.add(perfil);
			System.out.println("Perfil agregado");			
		} else {
			System.out.println("Error, el perfil ya existe");
		} 
	}	
	
	/**
	 * Caso de uso, lista los perfiles del sistema
	 */
	public void listarPerfiles() {
		System.out.println("Perfiles");
		System.out.println("========");		
		for (Perfil p: perfiles) {
			System.out.println(p);
		}
	}
	
	/**
	 * Caso de uso, consulta un perfil del sistema
	 */
	public void consultarPerfil() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo del perfil a consultar: ");
		int codigo = in.nextInt();
		
		Perfil perfil = buscarPerfil(codigo);
		if (perfil != null) {
			perfil.consultar();
			System.out.println("Fin de la consulta");
		} else {
			System.out.println("Error, el perfil no existe");
		}		
	}
	
	/**
	 * Caso de uso, modifica un perfil
	 */
	public void modificarPerfil() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo: ");
		int codigo = in.nextInt();
		
		Perfil perfil = buscarPerfil(codigo);
		if (perfil != null) {
			System.out.print("Modificar el nombre? S/N: ");
			if (in.next().compareToIgnoreCase("S") == 0) {
				System.out.print("Ingrese el nombre: ");
				perfil.setNombre(in.next());
			}
			
			System.out.print("Modificar permisos? S/N: ");
			if (in.next().compareToIgnoreCase("S") == 0) {
				perfil.setPermisos(new ArrayList<Permiso>());
				
				listarPermisos();
				int opcion;
				Permiso permiso;
				do {
					System.out.print("Ingrese un codigo de permiso para agregar al perfil (0 para terminar): ");
					opcion = in.nextInt();
					permiso = buscarPermiso(opcion);
					if (permiso != null) {
						perfil.agregarPermiso(permiso);
					} else if (opcion != 0) {
						System.out.println("Código inválido");
					}
				} while(opcion != 0);			
			}

			System.out.println("Perfil modificado");			
		} else {
			System.out.println("Error, el perfil ya existe");
		}		
	}
	
	/**
	 * Caso de uso, da de baja definitivamente a un perfil del sistema
	 */
	public void bajaDefinitivaPerfil() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo del perfil a eliminar: ");
		int codigo = in.nextInt();
		
		Perfil perfil = buscarPerfil(codigo);
		if (perfil != null) {
			ArrayList<Usuario> usuarios = buscarUsuariosConPerfil(perfil);
			if (usuarios.size() > 0) {
				String lista = "";
				for (int i = 0; i < usuarios.size(); i++) {
					if (i == 0)
						lista = usuarios.get(i).getUsuario();
					else
						lista += ", " + usuarios.get(i).getUsuario();
				}
				System.out.println("Error, el perfil esta siendo utilizado por los usuarios: " + lista);
			} else {
				perfiles.remove(perfil);
				System.out.println("P dado de baja en forma definitiva");
			}			
		} else {
			System.out.println("Error, el perfil no existe");
		} 		
	}
	
	/**
	 * Caso de uso, lista los permisos del sistema
	 */
	public void listarPermisos() {
		System.out.println("Permisos");
		System.out.println("========");
		for (Permiso p: permisos) {
			System.out.println(p);
		}
	}
	
	/**
	 * Carga los permisos por defecto para que el sistema funcione
	 */
	public void cargarPermisos() {
		permisos.clear();
		permisos.add(new Permiso(1, "Agregar un empleado"));
		permisos.add(new Permiso(2, "Listar los empleados"));
		permisos.add(new Permiso(3, "Consultar un empleado"));
		permisos.add(new Permiso(4, "Baja definitiva de un empleado"));
		permisos.add(new Permiso(5, "Liquidar sueldos"));
		permisos.add(new Permiso(6, "Agregar un tipo de material"));
		permisos.add(new Permiso(7, "Listar los tipos de materiales"));
		permisos.add(new Permiso(8, "Consultar un tipo de material"));
		permisos.add(new Permiso(9, "Baja definitiva a un tipo de material"));
		permisos.add(new Permiso(10, "Alta de un material"));
		permisos.add(new Permiso(11, "Listar los materiales"));
		permisos.add(new Permiso(12, "Consultar un material"));
		permisos.add(new Permiso(13, "Baja definitiva de un material"));
		permisos.add(new Permiso(14, "Alta de un salón"));
		permisos.add(new Permiso(15, "Listar los salones"));
		permisos.add(new Permiso(16, "Consultar un salón"));
		permisos.add(new Permiso(17, "Baja definitiva de un salón"));
		permisos.add(new Permiso(18, "Alta evento"));
		permisos.add(new Permiso(19, "Listar eventos"));
		permisos.add(new Permiso(20, "Listar eventos condicionados"));
		permisos.add(new Permiso(21, "Consultar evento"));
		permisos.add(new Permiso(22, "Baja definitiva de un evento"));
		permisos.add(new Permiso(23, "Alta de inscripciones"));
		permisos.add(new Permiso(24, "Baja de inscripciones"));
		permisos.add(new Permiso(25, "Alta de un usuario"));
		permisos.add(new Permiso(26, "Listar usuarios"));
		permisos.add(new Permiso(27, "Consultar un usuario"));
		permisos.add(new Permiso(28, "Modificar un usuario"));
		permisos.add(new Permiso(29, "Baja definitiva de un usuario"));
		permisos.add(new Permiso(30, "Alta de un perfil"));
		permisos.add(new Permiso(31, "Listar perfiles"));
		permisos.add(new Permiso(32, "Consultar un perfil"));
		permisos.add(new Permiso(33, "Modificar un perfil"));
		permisos.add(new Permiso(34, "Baja definitiva de un perfil"));
		permisos.add(new Permiso(35, "Listar permisos"));
		permisos.add(new Permiso(0, "Salir"));
	}
	
	/**
	 * Carga los perfiles por defecto para que el sistema funcione
	 */
	public void cargarPerfiles() {
		perfiles.clear();
		
		// Perfil administrador
		Perfil admin = new Perfil(1, "Administrador");
		for (int i = 1; i < permisos.size(); i++) {
			admin.agregarPermiso(buscarPermiso(i));
		}
		admin.agregarPermiso(buscarPermiso(0));
		perfiles.add(admin);
		
		// Perfil RRHH
		Perfil rrhh = new Perfil(2, "RRHH");
		for (int i = 1; i <= 5; i++) {
			rrhh.agregarPermiso(buscarPermiso(i));
		}
		rrhh.agregarPermiso(buscarPermiso(0));
		perfiles.add(rrhh);
		
		// Perfil administrador tipo de materiales
		Perfil admTipoMateriales = new Perfil(3, "Administrador de Materiales");		
		for (int i = 6; i <= 13; i++) {
			admTipoMateriales.agregarPermiso(buscarPermiso(i));
		}
		admTipoMateriales.agregarPermiso(buscarPermiso(0));
		perfiles.add(admTipoMateriales);
		
		// Perfil administrador salones
		Perfil admSalones = new Perfil(4, "Administrador de Salones");	
		for (int i = 14; i <= 17; i++) {
			admSalones.agregarPermiso(buscarPermiso(i));
		}
		admSalones.agregarPermiso(buscarPermiso(0));
		perfiles.add(admSalones);
		
		// Perfil administrador eventos
		Perfil admEventos = new Perfil(5, "Administrador de Eventos");
		admEventos.agregarPermiso(buscarPermiso(0));
		for (int i = 18; i <= 24; i++) {
			admEventos.agregarPermiso(buscarPermiso(i));
		}
		admEventos.agregarPermiso(buscarPermiso(0));
		perfiles.add(admEventos);
		
		// Perfil administrador de usuarios
		Perfil admUsuarios = new Perfil(6, "Administrador de Usuarios");		
		for (int i = 25; i <= 35; i++) {
			admUsuarios.agregarPermiso(buscarPermiso(i));
		}
		admUsuarios.agregarPermiso(buscarPermiso(0));
		perfiles.add(admUsuarios);
	}
	
	/**
	 * Carga los usuarios por defecto para que el sistema funcione
	 */
	public void cargarUsuarios() {
		usuarios.clear();
		
		// Usuarios para cada uno de los perfiles
		usuarios.add(new Usuario("admin", "admin", buscarPerfil(1)));
		usuarios.add(new Usuario("rrhh", "rrhh", buscarPerfil(2)));
		usuarios.add(new Usuario("materiales", "materiales", buscarPerfil(3)));
		usuarios.add(new Usuario("salones", "salones", buscarPerfil(4)));
		usuarios.add(new Usuario("eventos", "eventos", buscarPerfil(5)));
		usuarios.add(new Usuario("usuarios", "usuarios", buscarPerfil(6)));
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
	 * Recupera un perfil de la lista de perfiles buscando su codigo
	 */
	public Perfil buscarPerfil(int codigo) {
		int i = 0;
		Perfil buscado = null;
		while(i < perfiles.size() && buscado == null) {
			if (perfiles.get(i).sos(codigo))
				buscado = perfiles.get(i);
			i++;
		}
		return buscado;
	}
	
	/**
	 * Solicita al usuario que ingrese un usuario y contraseña, devolviendo el usuario autentificado o null en caso contraio
	 * @return Usuario autentificado o null si no se pudo autentificar
	 */
	public Usuario autentificar() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.println();
		System.out.println("Sistema Hotel");
		System.out.println("=============");
		System.out.println();
		
		System.out.print("Ingrese su usuario: ");
		String usr = in.next();
		System.out.print("Ingrese su contraseña: ");
		String pwd = in.next();
		
		return buscarUsuario(usr, pwd);
	}
	
	/**
	 * Busca un usuario en base al usuario
	 * @param usr Usuario a buscar
	 * @return Usuario encontrado con los parametros provistos, o null si no se encontro
	 */
	public Usuario buscarUsuario(String usr) {
		int i = 0;
		Usuario buscado = null;
		while(i < usuarios.size() && buscado == null) {
			if (usuarios.get(i).sos(usr))
				buscado = usuarios.get(i);
			i++;
		}
		return buscado;		
	}

	/**
	 * Busca un usuario en base al usuario y contraseña
	 * @param usr Usuario a buscar
	 * @param pwd Contraseña del usuario
	 * @return Usuario encontrado con los parametros provistos, o null si no se encontro
	 */
	public Usuario buscarUsuario(String usr, String pwd) {
		int i = 0;
		Usuario buscado = null;
		while(i < usuarios.size() && buscado == null) {
			if (usuarios.get(i).sos(usr, pwd))
				buscado = usuarios.get(i);
			i++;
		}
		return buscado;
	}
	
	/**
	 * Devuelve la lista de usuarios que contiene el perfil especificado
	 * @param perfil perfil a buscar en los usuarios
	 * @return lista de usuarios que contiene el perfil
	 */
	public ArrayList<Usuario> buscarUsuariosConPerfil(Perfil perfil) {
		ArrayList<Usuario> encontrados = new ArrayList<Usuario>();
		for(Usuario u: usuarios) {
			if (u.getPerfil().sos(perfil.getCodigo()))
				encontrados.add(u);
		}
		return encontrados;
	}
	
	/**
	 * Imprime el menu de opciones disponibles para el usuario y devuelve la opción seleccionada
	 * @param usuario Usuario autentificado en el sistema
	 * @return opción digitada por el usuario
	 */
	public int obtenerOpcion(Usuario usuario) {
		int opcion;
		
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));

		imprimirMenu(usuario.getPerfil());
			
		System.out.print("Digite su opción: ");
		opcion = in.nextInt();
		while(usuario.getPerfil().buscarPermiso(opcion) == null) {
			System.out.print("Opción incorrecta, digite su opción: ");
			opcion = in.nextInt();
		}
		
		return(opcion);
	}
	
	/**
	 * Imprime el menu basado en los permisos de un perfil
	 * @param perfil perfil con la lista de permisos asignados
	 */
	public void imprimirMenu(Perfil perfil) {
		System.out.println("Sistema Hotel");
		System.out.println("=============");
		System.out.println();
		
		for(Permiso permiso: perfil.getPermisos()) {
			System.out.println(permiso.getCodigo() + " - " + permiso.getNombre());
		}
	}
}
