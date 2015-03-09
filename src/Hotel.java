import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class Hotel {
	private int codigo;
	private ArrayList<Salon> salones;
	private ArrayList<Evento> eventos;
	private ArrayList<Material> materiales;
	private ArrayList<TipoMaterial> tipoMateriales;
	private ArrayList<Empleado> empleados;
	
	public Hotel() {
		salones = new ArrayList<Salon>();
		eventos = new ArrayList<Evento>();
		materiales = new ArrayList<Material>();
		tipoMateriales = new ArrayList<TipoMaterial>();
		empleados = new ArrayList<Empleado>();
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public ArrayList<Salon> getSalones() {
		return salones;
	}
	
	public void setSalones(ArrayList<Salon> salones) {
		this.salones = salones;
	}
	
	public ArrayList<Evento> getEventos() {
		return eventos;
	}
	
	public void setEventos(ArrayList<Evento> eventos) {
		this.eventos = eventos;
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
	 * Agrega un salon
	 * @param salon salon a agregar
	 */
	public void agregarSalon(Salon salon) {
		if (salones.indexOf(salon) == -1)
			salones.add(salon);
	}
	
	/** 
	 * Agrega un tipo de material
	 * @param tipoMaterial tipo de material a agregar
	 */
	public void agregarTipoMaterial(TipoMaterial tipoMaterial) {
		if (tipoMateriales.indexOf(tipoMaterial) == -1)
			tipoMateriales.add(tipoMaterial);
	}

	/**
	 * Agrega un material
	 * @param material material a agregar
	 */
	public void agregarMaterial(Material material) {
		if (materiales.indexOf(material) == -1) {
			materiales.add(material);
			material.getTipoMaterial().agregarMaterial(material);
		}
	}
	
	/**
	 * Agrega un empleado
	 * @param empleado empleado a agregar
	 */
	public void agregarEmpleado(Empleado empleado) {
		if (empleados.indexOf(empleado) == -1)
			empleados.add(empleado);
	}
	
	/**
	 * Agrega un evento
	 * @param evento evento a agregar
	 */
	public void agregarEvento(Evento evento) {
		if (eventos.indexOf(evento) == -1)
			eventos.add(evento);
	}

	/**
	 * Caso de uso, alta de empleado
	 */
	public void altaEmpleado() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el legajo: ");
		int legajo = in.nextInt();
		
		Empleado empleado = buscarEmpleado(legajo);
		if (empleado == null) {
			System.out.println("Es un empleado capacitado (caso contrario sera un empleado razo)? S/N: ");
			String capacitado = in.next();			
			
			if (capacitado.compareToIgnoreCase("S") == 0)
				empleado = new Capacitado(legajo);
			else
				empleado = new Razo(legajo);
			
			System.out.println("Ingrese el nombre: ");
			empleado.setNombre(in.next());
			
			System.out.println("Ingrese la dirección: ");
			empleado.setDireccion(in.next());
			
			System.out.println("Ingrese el telefono: ");
			empleado.setTelefono(in.next());
			
			if (empleado instanceof Capacitado) {
				listarTipoMateriales();
				int opcion;
				TipoMaterial tipoMaterial;
				do {
					System.out.print("Ingrese un codigo de tipo de material para agregar al empleado (0 para terminar): ");
					opcion = in.nextInt();
					tipoMaterial = buscarTipoMaterial(opcion);
					if (tipoMaterial != null) {
						((Capacitado) empleado).agregarTipoMaterial(tipoMaterial);
					} else if (opcion != 0) {
						System.out.println("Código inválido");
					}
				} while(opcion != 0);
			}
			
			agregarEmpleado(empleado);
			System.out.println("Se agrego el empleado");
		} else {
			System.out.println("Error, el empleado ya existe");
		}			
	}
	
	/**
	 * Caso de uso, lista los empleados
	 */
	public void listarEmpleados() {
		System.out.println("Empleados");
		System.out.println("=========");
		
		for(Empleado e: empleados) {
			System.out.println(e);
		}		
	}
	
	/**
	 * Caso de uso, consultar un empleado
	 */
	public void consultarEmpleado() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el legajo: ");
		int legajo = in.nextInt();
		
		Empleado empleado = buscarEmpleado(legajo);
		if (empleado != null) {
			empleado.consultar();
			System.out.println("Fin de la consulta");
		} else {
			System.out.println("Error, el empleado no existe");
		}			
	}
	
	/**
	 * Caso de uso, baja definitiva de un empleado
	 */
	public void bajaDefinitivaEmpleado() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el legajo: ");
		int legajo = in.nextInt();
		
		Empleado empleado = buscarEmpleado(legajo);
		if (empleado != null) {
			// Primero sacamos el empleado del array asi ya no busca mas el empleado
			empleados.remove(empleado);
			
			if (empleado instanceof Capacitado) {
				Capacitado capacitado = (Capacitado)empleado;
				// Buscar el evento que tiene las fechas y fijarse si hay otro salon, sino condicionarlo
				Hashtable<String,Evento> ocupado = capacitado.getOcupado();
				Enumeration<String> keys = ocupado.keys();
				String key = keys.nextElement();
				Evento evento = ocupado.get(key);
				ArrayList<Fecha> fechas;
							
				if (evento instanceof Simple) {
					Simple e = (Simple)evento;
					fechas = new ArrayList<Fecha>();
					fechas.add(e.getFecha());
				} else {
					Complejo e = (Complejo)evento;
					fechas = e.getArrayFechas();
				}
												
				Capacitado nuevo = buscarCapacitadoDisponible(new ArrayList<Capacitado>(), fechas, capacitado.getTipoMateriales().get(0));
				if (nuevo != null) {
					evento.quitarEmpleado((Empleado)capacitado);
					evento.agregarCapacitado(nuevo);
				} else {
					evento.quitarEmpleado((Empleado)capacitado);
					evento.setCondicionado(true);
				}
			}
									
			System.out.println("Empleado " + empleado.getLegajo() + " dado de baja en forma definitiva");
		} else {
			System.out.println("Error, el empleado no existe");
		}			
	}
	
	/**
	 * Caso de uso, liquidación de sueldos
	 */
	public void liquidarSueldos() {
		System.out.println("Liquidación de sueldos");
		System.out.println("======================");		
		for (Empleado e: empleados) {
			e.liquidarSueldo();
		}
	}
	
	/**
	 * Caso de uso, agrega un tipo de material
	 */
	public void altaTipoMaterial() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));

		System.out.print("Ingrese el codigo: ");
		int codigo = in.nextInt();
		
		TipoMaterial tipoMaterial = buscarTipoMaterial(codigo);
		if (tipoMaterial == null) {
			System.out.print("Ingrese la descripción: ");
			String descripcion = in.next();
			
			agregarTipoMaterial(new TipoMaterial(codigo, descripcion));
			System.out.println("Se agrego el tipo de material " + descripcion);
		} else {
			System.out.println("Error, el tipo de materials ya existe");
		}		
	}
	
	/**
	 * Caso de uso, lista los tipos de materiales
	 */
	public void listarTipoMateriales() {
		System.out.println("Tipo de Materiales");
		System.out.println("==================");		
		
		for(TipoMaterial tm: tipoMateriales) {
			System.out.println(tm);
		}
	}
	
	/**
	 * Caso de uso, consulta un tipo de material
	 */
	public void consultarTipoMaterial() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo del tipo de material a consultar: ");
		int codigo = in.nextInt();
		
		TipoMaterial tipoMaterial = buscarTipoMaterial(codigo);
		if (tipoMaterial != null) {
			tipoMaterial.consultar();
			System.out.println("Fin de la consulta");
		} else {
			System.out.println("Error, el tipo de material no existe");
		}		
	}
	
	/**
	 * Caso de uso, baja definitiva de un tipo de material
	 */
	public void bajaDefinitivaTipoMaterial() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo del tipo de material a eliminar: ");
		TipoMaterial tipoMaterial = buscarTipoMaterial(in.nextInt());

		if (tipoMaterial != null) {
			if (tipoMaterial.getMateriales().size() == 0) {
				System.out.println("Error, hay materiales asociados al tipo de material que se quiere eliminar");
				System.out.println("Materiales: ");
				for(Material material: tipoMaterial.getMateriales())
					System.out.println(material);
			} else {
				tipoMateriales.remove(tipoMaterial);
				System.out.println("Se elimino el tipo de material " + tipoMaterial.toString());
			}
		} else {
			System.out.println("Error, el usuario no existe");
		} 		
	}
	
	/**
	 * Caso de uso, alta de un material
	 */
	public void altaMaterial() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));

		System.out.print("Ingrese el codigo: ");
		int codigo = in.nextInt();
		
		Material material = buscarMaterial(codigo);
		if (material == null) {
			System.out.println("Es material de uso (caso contrario será material de reserva)? S/N: ");
			String uso = in.next();			
			
			if (uso.compareToIgnoreCase("S") == 0)
				material = new MaterialUso(codigo);
			else
				material = new MaterialReserva(codigo);
			
			System.out.println("Ingrese el codigo del tipo de material");
			for(TipoMaterial tm: tipoMateriales) {
				System.out.println(tm);
			}
			System.out.print("Digite su opción: ");
			int cm = in.nextInt();
			TipoMaterial tipoMaterial = buscarTipoMaterial(cm);
			while (tipoMaterial == null) {
				System.out.print("El código no es válido, ingrese un código de tipo de material válido: ");
				cm = in.nextInt();
				tipoMaterial = buscarTipoMaterial(cm);
			}
			material.setTipoMaterial(tipoMaterial);
			
			agregarMaterial(material);
			System.out.println("Se agrego el material");
		} else {
			System.out.println("Error, el material ya existe");
		}		
	}
	
	/**
	 * Caso de uso, lista los materiales
	 */
	public void listarMateriales() {
		System.out.println("Materiales");
		System.out.println("==========");
		
		for(Material m: materiales) {
			System.out.println(m);
		}		
	}
	
	/**
	 * Caso de uso, consultar material
	 */
	public void consultarMaterial() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo del material a consultar: ");
		int codigo = in.nextInt();
		
		Material material = buscarMaterial(codigo);
		if (material != null) {
			material.consultar();
			System.out.println("Fin de la consulta");
		} else {
			System.out.println("Error, el material no existe");
		}				
	}	
	
	/**
	 * Caso de uso, baja definitiva de un material
	 */
	public void bajaDefinitivaMaterial() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo del material a eliminar: ");
		Material material = buscarMaterial(in.nextInt());
		if (material != null) {
			// Primero sacamos el material del array asi ya no busca mas el material
			materiales.remove(material);
						
			// Buscar el evento que tiene las fechas y fijarse si hay otro salon, sino condicionarlo
			Hashtable<String,Evento> ocupado = material.getOcupado();
			Enumeration<String> keys = ocupado.keys();
			String key = keys.nextElement();
			Evento evento = ocupado.get(key);
			ArrayList<Fecha> fechas;
										
			if (evento instanceof Simple) {
				Simple e = (Simple)evento;
				fechas = new ArrayList<Fecha>();
				fechas.add(e.getFecha());
			} else {
				Complejo e = (Complejo)evento;
				fechas = e.getArrayFechas();
			}
															
			Material nuevo = buscarMaterialDisponible(new ArrayList<Material>(), fechas, material.getTipoMaterial());
			if (nuevo != null) {
				evento.quitarMaterial(material);
				evento.agregarMaterial(nuevo);
			} else {
				nuevo = buscarMaterialDisponible(fechas, material.getTipoMaterial());
				if (nuevo != null) {
					evento.quitarMaterial(material);
					evento.agregarMaterial(nuevo);					
				} else {
					evento.quitarMaterial(material);
					evento.setCondicionado(true);
				}
			}
						
			System.out.println("Material " + material.getCodigo() + " dado de baja en forma definitiva");
		} else {
			System.out.println("Error, el material no existe");
		}	
	}
	
	/**
	 * Caso de uso, alta de salon
	 */
	public void altaSalon() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));

		System.out.print("Ingrese el codigo: ");
		int codigo = in.nextInt();
		
		Salon salon = buscarSalon(codigo);		
		if (salon == null) {
			System.out.print("Ingrese la capacidad: ");
			int capacidad = in.nextInt();
			
			agregarSalon(new Salon(codigo, capacidad));
			System.out.println("Se agrego el salón " + codigo);
		} else {
			System.out.println("Error, el salón ya existe");
		}				
	}
	
	/**
	 * Caso de uso, lista los salones
	 */
	public void listarSalones() {
		System.out.println("Salones");
		System.out.println("=======");
		
		for(Salon s: salones) {
			System.out.println(s);
		}		
	}
	
	/**
	 * Caso de uso, consultar salon
	 */
	public void consultarSalon() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo del salón a consultar: ");
		int codigo = in.nextInt();
		
		Salon salon = buscarSalon(codigo);
		if (salon != null) {
			salon.consultar();
			System.out.println("Fin de la consulta");
		} else {
			System.out.println("Error, el salón no existe");
		}			
	}	
	
	/**
	 * Caso de uso, baja definitiva de un salon
	 */
	public void bajaDefinitivaSalon() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo del salón a eliminar: ");
		Salon salon = buscarSalon(in.nextInt());

		if (salon != null) {
			// Primero sacamos el salon del array asi ya no busca mas el salon
			salones.remove(salon);
			
			// Buscar el evento que tiene las fechas y fijarse si hay otro salon, sino condicionarlo
			Hashtable<String,Evento> ocupado = salon.getOcupado();
			Enumeration<String> keys = ocupado.keys();
			String key = keys.nextElement();
			Evento evento = ocupado.get(key);
			ArrayList<Fecha> fechas;
			
			if (evento instanceof Simple) {
				Simple e = (Simple)evento;
				fechas = new ArrayList<Fecha>();
				fechas.add(e.getFecha());
			} else {
				Complejo e = (Complejo)evento;
				fechas = e.getArrayFechas();
			}
				

			Salon nuevo = buscarSalonDisponible(fechas, salon.getCapacidad());
			if (nuevo != null) {
				evento.quitarSalon();
				evento.agregarSalon(nuevo);
			} else {
				evento.quitarSalon();
				evento.setCondicionado(true);
			}
			
			System.out.println("El salón " + salon.getCodigo() + " fue dado de baja en forma definitiva");
		} else {
			System.out.println("Error, el salón no existe");
		} 			
	}
	
	/**
	 * Caso de uso, alta de un evento
	 * Lo separamos a proposito en dos metodos diferentes pero parecidos para no hacer un choclo de codigo
	 */
	public void altaEvento() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo para el nuevo evento: ");		
		int codigo = in.nextInt();
		
		Evento evento = buscarEvento(codigo);		
		
		if (evento == null) {
			// Si ingresa una fecha sola es un evento simple, si ingresa mas de una es un evento complejo
			ArrayList<Fecha> fechas = new ArrayList<Fecha>();
			System.out.println("Ingrese la primer fecha para el evento, si luego ingresa mas fechas sera un evento complejo");
			String ingresarMasFechas;
			do {
				fechas.add(Fecha.nuevaFecha());
				System.out.print("Ingresar otra fecha? S/N: ");
				ingresarMasFechas = in.next();
			} while(ingresarMasFechas.compareToIgnoreCase("s") == 0);
			
			if (fechas.size() == 1)
				evento = new Simple();
			else
				evento = new Complejo();
			evento.setCodigo(codigo);
			
			// Preguntamos la capacidad requerida y los materiales y luego comprobamos si tenemos todo o no
			System.out.print("Ingrese la capacidad requerida: ");
			int capacidad = in.nextInt();
			
			ArrayList<TipoMaterial> tipoMateriales = new ArrayList<TipoMaterial>();
			TipoMaterial tipoMaterial;
			int opcion;
			listarTipoMateriales();
			do {
				System.out.print("Ingrese un codigo de un material necesario para el evento (0 para terminar): ");
				opcion = in.nextInt();
				tipoMaterial = buscarTipoMaterial(opcion);
				if (tipoMaterial != null) {
					tipoMateriales.add(tipoMaterial);
				} else if (opcion != 0) {
					System.out.println("Código inválido");
				}
			} while(opcion != 0);
			
			// Ya tenemos el codigo, la fecha, la capacidad y los materiales, ahora buscamos haber si tenemos todo
			Salon salon = buscarSalonDisponible(fechas, capacidad);
			if (salon != null) {
				// Ahora comprobamos si tenemos los materiales y el personal capacitado
				ArrayList<Material> materialesEncontrados = new ArrayList<Material>();
				ArrayList<Capacitado> capacitadosEncontrados = new ArrayList<Capacitado>();
				
				boolean disponibilidad = true;
				int i = 0;
				while(i < tipoMateriales.size() && disponibilidad) {
					Material materialBuscado = buscarMaterialDisponible(materialesEncontrados, fechas, tipoMateriales.get(i));
					Capacitado capacitadoBuscado = buscarCapacitadoDisponible(capacitadosEncontrados, fechas, tipoMateriales.get(i));
					
					if (materialBuscado != null && capacitadoBuscado != null) {
						materialesEncontrados.add(materialBuscado);
						capacitadosEncontrados.add(capacitadoBuscado);
					} else {
						disponibilidad = false;
					}
					
					i++;
				}
				
				// Si tenemos disponibilidad, reservamos todo, sino, mostramos el error
				if (disponibilidad == true) {
					if (evento instanceof Simple)
						((Simple) evento).setFecha(fechas.get(0));
					else
						((Complejo)evento).setFechas(fechas);
										
					evento.agregarSalon(salon);
					for(Material m: materialesEncontrados) {
						evento.agregarMaterial(m);
					}
					for(Capacitado c: capacitadosEncontrados) {
						evento.agregarCapacitado(c);
					}
					agregarEvento(evento);
					System.out.println("Se agrego el evento " + evento.toString());
				} else {
					System.out.println("Error, no hay disponibilidad de materiales y personal capacitado");
				}			
			} else {
				System.out.println("Error, no se encuentra un salón disponible para el evento");
			}
		} else {
			System.out.println("Error, ya existe un evento con el codigo " + codigo);
		}		
	}
	
	
	/**
	 * Busca un salon disponible para la fecha seleccionada
	 * @param fechas fechas a consultar
	 * @param capacidad capacidad necesaria para el salon
	 * @return Salon disponible o null
	 */
	public Salon buscarSalonDisponible(ArrayList<Fecha> fechas, int capacidad) {
		int i = 0;
		Salon buscado = null;
		while(i < salones.size() && buscado == null) {
			if (salones.get(i).tenesCapacidad(capacidad) && salones.get(i).estasDisponible(fechas))
				buscado = salones.get(i);
			i++;
		}
		return buscado;
	}
	
	/**
	 * Busca un material disponible para la fecha seleccionada
	 * @param encontrados lista de encontrados que no deben ser retornados
	 * @param fechas fechas a consultar
	 * @param tipoMaterial tipo de material a buscar
	 * @return Material disponible o null
	 */
	public Material buscarMaterialDisponible(ArrayList<Material> encontrados, ArrayList<Fecha> fechas, TipoMaterial tipoMaterial) {
		int i = 0;
		Material buscado = null;
		Material material = null;
		while(i < materiales.size() && buscado == null) {
			material = materiales.get(i);
			if (encontrados.indexOf(material) == -1 &&
					material instanceof MaterialUso &&
					material.sos(tipoMaterial) && 
					material.estasDisponible(fechas))
				buscado = material;
			i++;
		}
		return buscado;	
	}
	
	/**
	 * Busca un material de reserva disponible para la fecha seleccionada
	 * @param fechas fechas a consultar
	 * @param tipoMaterial tipo de material a buscar
	 * @return Material disponible o null
	 */
	public Material buscarMaterialDisponible(ArrayList<Fecha> fechas, TipoMaterial tipoMaterial) {
		int i = 0;
		Material buscado = null;
		Material material = null;
		while(i < materiales.size() && buscado == null) {
			material = materiales.get(i);
			if (material instanceof MaterialReserva &&
					material.sos(tipoMaterial) && 
					material.estasDisponible(fechas))
				buscado = material;
			i++;
		}
		return buscado;	
	}
	
	/**
	 * Bsuca personal capacitado disponible para la fecha seleccionada que sepa manejar el material seleccionado
	 * @param encontrados lista de encontrados que no deben ser retornados
	 * @param fechas fechas a consultar
	 * @param material material a consultar
	 * @return Personal capacitado disponible o null
	 */
	public Capacitado buscarCapacitadoDisponible(ArrayList<Capacitado> encontrados, ArrayList<Fecha> fechas, TipoMaterial tipoMaterial) {
		int i = 0;
		Capacitado buscado = null;
		Capacitado capacitado = null;
		while(i < empleados.size() && buscado == null) {
			if (empleados.get(i) instanceof Capacitado) {
				capacitado = (Capacitado)empleados.get(i);
				if (encontrados.indexOf(capacitado) == -1 &&
						capacitado.sos(tipoMaterial) && 
						capacitado.estasDisponible(fechas))
					buscado = capacitado;
			}
			i++;
		}		
		return buscado;
	}
	
	/**
	 * Caso de uso, lista los eventos
	 */
	public void listarEventos() {
		System.out.println("Eventos");
		System.out.println("=======");
		
		for(Evento e: eventos) {
			System.out.println(e);
		}				
	}
	
	/**
	 * Caso de uso, lista los eventos condicionados
	 */	
	public void listarEventosCondicionados() {
		System.out.println("Eventos");
		System.out.println("=======");
		
		for(Evento e: eventos) {
			if (e.estasCondicionado())
				System.out.println(e);
		}			
	}
	
	/**
	 * Caso de uso, consulta un evento
	 */
	public void consultarEvento() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo: ");
		Evento evento = buscarEvento(in.nextInt());

		if (evento != null) {
			evento.consultar();
			System.out.println("Fin de la consulta");
		} else {
			System.out.println("Error, el evento no existe");
		}		
	}
	
	/**
	 * Caso de uso, baja definitiva de un evento
	 */
	public void bajaDefinitivaEvento() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));
		
		System.out.print("Ingrese el codigo: ");
		Evento evento = buscarEvento(in.nextInt());

		if (evento != null) {
			evento.quitarSalon();
			evento.quitarEmpleados();
			evento.quitarMateriales();
			eventos.remove(evento);
			System.out.println("Fin de la consulta");
		} else {
			System.out.println("Error, el evento no existe");
		}		
	}
	
	/**
	 * Caso de uso, alta de inscripciones
	 */
	public void altaInscripciones() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));

		System.out.print("Ingrese el codigo del evento: ");
		Evento evento = buscarEvento(codigo);
		
		if (evento != null) {
			System.out.print("Ingrese la cantidad a inscribir: ");
			int cantidad = in.nextInt();
			
			if (evento.agregarInscripciones(cantidad) == true)
				System.out.println("Se agregaron " + cantidad + " inscripciones");
			else
				System.out.println("No hay capacidad disponible. La capacidad disponible es de: " + evento.inscripcionesDisponibles());
		} else {
			System.out.println("Error, el evento no existe");
		}		
	}
	
	/**
	 * Caso de uso, baja de inscripciones
	 */
	public void bajaInscripciones() {
		Scanner in = new Scanner(System.in);
		in.useDelimiter(System.getProperty("line.separator"));

		System.out.print("Ingrese el codigo del evento: ");
		Evento evento = buscarEvento(codigo);
		
		if (evento != null) {
			System.out.print("Ingrese la cantidad a inscribir: ");
			int cantidad = in.nextInt();
			
			evento.quitarInscripciones(cantidad);
			System.out.println("Se quitaron las inscripciones solicitadas, inscripciones disponibles: " + evento.inscripcionesDisponibles());
		} else {
			System.out.println("Error, el evento no existe");
		}				
	}
	
	
	/**
	 * Busca un salon con el codigo provisto
	 * @param codigo codigo a buscar
	 * @return retorna el salon si lo encuentra, null caso contrario
	 */
	public Salon buscarSalon(int codigo) {
		int i = 0;
		Salon buscado = null;
		while(i < salones.size() && buscado == null) {
			if (salones.get(i).sos(codigo))
				buscado = salones.get(i);
			i++;
		}
		return buscado;		
	}
	
	/**
	 * Busca un evento con el codigo provisto
	 * @param codigo codigo a buscar
	 * @return retorna el evento si lo encuentra, null caso contrario
	 */
	public Evento buscarEvento(int codigo) {
		int i = 0;
		Evento buscado = null;
		while(i < eventos.size() && buscado == null) {
			if (eventos.get(i).sos(codigo))
				buscado = eventos.get(i);
			i++;
		}
		return buscado;		
	}
	
	/**
	 * Busca un material con el codigo provisto
	 * @param codigo codigo a buscar
	 * @return retorna el material si lo encuentra, null en caso contrario
	 */
	public Material buscarMaterial(int codigo) {
		int i = 0;
		Material buscado = null;
		while(i < materiales.size() && buscado == null) {
			if (materiales.get(i).sos(codigo))
				buscado = materiales.get(i);
			i++;
		}
		return buscado;		
	}
	
	/**
	 * Busca un tipo de material con el codigo provisto
	 * @param codigo codigo a buscar
	 * @return retorna el tipo de material si lo encuentra, null en caso contrario
	 */
	public TipoMaterial buscarTipoMaterial(int codigo) {
		int i = 0;
		TipoMaterial buscado = null;
		while(i < tipoMateriales.size() && buscado == null) {
			if (tipoMateriales.get(i).sos(codigo))
				buscado = tipoMateriales.get(i);
			i++;
		}
		return buscado;		
	}
	
	/**
	 * Busca un empleado con el legajo provisto
	 * @param legajo legajo a buscar
	 * @return retorna el empleado si lo encuentra, null en caso contrario
	 */
	public Empleado buscarEmpleado(int legajo) {
		int i = 0;
		Empleado buscado = null;
		while(i < empleados.size() && buscado == null) {
			if (empleados.get(i).sos(legajo))
				buscado = empleados.get(i);
			i++;
		}
		return buscado;		
	}
	
	/**
	 * Carga los tipos de materiales por defecto
	 */
	public void cargarTipoMateriales() {
		tipoMateriales.clear();

		// Dos tipos de materiales de uso avanzado para los empleados
		agregarTipoMaterial(new TipoMaterial(1, "Proyector"));
		agregarTipoMaterial(new TipoMaterial(2, "TV"));		
	}
	
	/**
	 * Carga los materiales por defecto
	 */
	public void cargarMateriales() {
		materiales.clear();
		
		agregarMaterial(new MaterialUso(1, buscarTipoMaterial(1)));
		agregarMaterial(new MaterialUso(2, buscarTipoMaterial(1)));
		agregarMaterial(new MaterialReserva(3, buscarTipoMaterial(1)));
		agregarMaterial(new MaterialUso(4, buscarTipoMaterial(2)));
		agregarMaterial(new MaterialReserva(4, buscarTipoMaterial(2)));
	}
	
	/**
	 * Carga los empleados por defecto
	 */
	public void cargarEmpleados() {
		empleados.clear();		
		Capacitado capacitado;
		
		capacitado = new Capacitado(100, "Marcos Gimenez", "Belgrano 251", "4040-2341");
		capacitado.agregarTipoMaterial(buscarTipoMaterial(1));
		capacitado.agregarTipoMaterial(buscarTipoMaterial(2));
		agregarEmpleado(capacitado);
		
		capacitado = new Capacitado(101, "Juan Perez", "Martinez 100", "4040-1212");
		capacitado.agregarTipoMaterial(buscarTipoMaterial(1));
		capacitado.agregarTipoMaterial(buscarTipoMaterial(2));
		agregarEmpleado(capacitado);
		
		capacitado = new Capacitado(102, "Teodofilo Filo", "Yrygoyen 300", "4040-4444");
		capacitado.agregarTipoMaterial(buscarTipoMaterial(2));
		agregarEmpleado(capacitado);
		
		agregarEmpleado(new Razo(103, "Mazon Zoman", "Bautista 100", "4040-3000"));
		agregarEmpleado(new Razo(104, "Marin Villa", "Bautista 110", "4040-4000"));
		agregarEmpleado(new Razo(105, "Juanjo Mercedez", "Bautista 120", "4040-5000")); 
	}
	
	/**
	 * Carga salones por defecto
	 */
	public void cargarSalones() {
		salones.clear();
		
		agregarSalon(new Salon(1, 100));
		agregarSalon(new Salon(2, 100));
		agregarSalon(new Salon(3, 50));
		agregarSalon(new Salon(4, 50));
	}
}
