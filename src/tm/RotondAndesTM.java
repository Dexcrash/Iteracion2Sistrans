/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: UsuarioAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import dao.DAOTablaIngredientes;
import dao.DAOTablaProductos;
import dao.DAOTablaRestaurantes;
import dao.DAOTablaUsuarios;
import dao.DAOTablaVideos;
import dao.DAOTablaZonas;
import dao.DAOTablaUsuarios;
import vos.Ingrediente;
import vos.Producto;
import vos.Restaurante;
import vos.Usuario;
import vos.Video;
import vos.Zona;
import vos.Usuario;

/**
 * Transaction Manager de la aplicacion (TM) Fachada en patron singleton de la
 * aplicacion
 * 
 * @author Monitores 2017-20
 */
public class RotondAndesTM {

	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los
	 * datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los
	 * datos de la conexion
	 */
	private String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base
	 * de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base
	 * de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base
	 * de datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor de la clase UsuarioAndesMaster, esta clase modela y
	 * contiene cada una de las transacciones y la logica de negocios que estas
	 * conllevan. <b>post: </b> Se crea el objeto UsuarioAndesTM, se inicializa el
	 * path absoluto del archivo de conexion y se inicializa los atributos que
	 * se usan par la conexion a la base de datos.
	 * 
	 * @param contextPathP
	 *            - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que inicializa los atributos que se usan para la conexion a la
	 * base de datos. <b>post: </b> Se han inicializado los atributos que se
	 * usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que retorna la conexion a la base de datos
	 * 
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException
	 *             - Cualquier error que se genere durante la conexion a la base
	 *             de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	/////// Transacciones////////////////////
	////////////////////////////////////////

	/**
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base
	 * de datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Usuario> darUsuarios() throws Exception {
		List<Usuario> usuarios;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuarios = daoUsuarios.darUsuarios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los usuarios en la base de
	 * datos con el nombre entra como parametro.
	 * 
	 * @param name
	 *            - Nombre del usuario a buscar. name != null
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Usuario> buscarUsuariosPorName(String name) throws Exception {
		List<Usuario> usuarios;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuarios = daoUsuarios.buscarUsuariosPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

	/**
	 * Metodo que modela la transaccion que busca el usuario en la base de datos
	 * con el id que entra como parametro.
	 * 
	 * @param name
	 *            - Id del usuario a buscar. name != null
	 * @return Usuario - Resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public Usuario buscarUsuarioPorId(String id) throws Exception {
		Usuario usuario;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuario = daoUsuarios.buscarUsuarioPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuario;
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo usuario a la base de
	 * datos. <b> post: </b> se ha agregado el usuario que entra como parametro
	 * 
	 * @param usuario
	 *            - el usuario a agregar. usuario != null
	 * @throws Exception
	 *             - cualquier error que se genere agregando el usuario
	 */
	public void addUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.addUsuario(usuario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que agrega los usuarios que entran como
	 * parametro a la base de datos. <b> post: </b> se han agregado los usuarios
	 * que entran como parametro
	 * 
	 * @param usuarios
	 *            - objeto que modela una lista de usuarios y se estos se
	 *            pretenden agregar. usuarios != null
	 * @throws Exception
	 *             - cualquier error que se genera agregando los usuarios
	 */
	public void addUsuarios(List<Usuario> usuarios) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			////// transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoUsuarios.setConn(conn);
			Iterator<Usuario> it = usuarios.iterator();
			while (it.hasNext()) {
				daoUsuarios.addUsuario(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el usuario que entra como
	 * parametro a la base de datos. <b> post: </b> se ha actualizado el usuario
	 * que entra como parametro
	 * 
	 * @param usuario
	 *            - Usuario a actualizar. usuario != null
	 * @throws Exception
	 *             - cualquier error que se genera actualizando los usuarios
	 */
	public void updateUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.updateUsuario(usuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el usuario que entra como
	 * parametro a la base de datos. <b> post: </b> se ha eliminado el usuario que
	 * entra como parametro
	 * 
	 * @param usuario
	 *            - Usuario a eliminar. usuario != null
	 * @throws Exception
	 *             - cualquier error que se genera actualizando los usuarios
	 */
	public void deleteUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.deleteUsuario(usuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base
	 * de datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Restaurante> darRestaurantes() throws Exception {
		List<Restaurante> restaurante;
		DAOTablaRestaurantes daoRestaurante = new DAOTablaRestaurantes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			restaurante = daoRestaurante.darRestaurantes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurante;
	}

	/**
	 * Metodo que modela la transaccion que busca el/los usuarios en la base de
	 * datos con el nombre entra como parametro.
	 * 
	 * @param name
	 *            - Nombre del usuario a buscar. name != null
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Restaurante> buscarRestaurantesPorName(String name) throws Exception {
		List<Restaurante> restaurantes;
		DAOTablaRestaurantes daoRestaurante = new DAOTablaRestaurantes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			restaurantes = daoRestaurante.buscarRestaurantesPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurantes;
	}

	/**
	 * Metodo que modela la transaccion que busca el usuario en la base de datos
	 * con el id que entra como parametro.
	 * 
	 * @param name
	 *            - Id del usuario a buscar. name != null
	 * @return Usuario - Resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public Restaurante buscarRestaurantePorId(Long id) throws Exception {
		Restaurante restaurante;
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurante = daoRestaurantes.buscarRestaurantePorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurante;
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo usuario a la base de
	 * datos. <b> post: </b> se ha agregado el usuario que entra como parametro
	 * 
	 * @param usuario
	 *            - el usuario a agregar. usuario != null
	 * @throws Exception
	 *             - cualquier error que se genere agregando el usuario
	 */
	public void addRestaurante(Restaurante usuario) throws Exception {
		DAOTablaRestaurantes daoRestaurante = new DAOTablaRestaurantes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			daoRestaurante.addRestaurante(usuario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que agrega los usuarios que entran como
	 * parametro a la base de datos. <b> post: </b> se han agregado los usuarios
	 * que entran como parametro
	 * 
	 * @param usuarios
	 *            - objeto que modela una lista de usuarios y se estos se
	 *            pretenden agregar. usuarios != null
	 * @throws Exception
	 *             - cualquier error que se genera agregando los usuarios
	 */
	public void addRestaurantes(List<Restaurante> restaurantes) throws Exception {
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try {
			////// transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoRestaurantes.setConn(conn);
			Iterator<Restaurante> it = restaurantes.iterator();
			while (it.hasNext()) {
				daoRestaurantes.addRestaurante(it.next());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que actualiza el usuario que entra como
	 * parametro a la base de datos. <b> post: </b> se ha actualizado el usuario
	 * que entra como parametro
	 * 
	 * @param usuario
	 *            - Usuario a actualizar. usuario != null
	 * @throws Exception
	 *             - cualquier error que se genera actualizando los usuarios
	 */
	public void updateRestaurante(Restaurante restaurante) throws Exception {
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.updateRestaurante(restaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina el usuario que entra como
	 * parametro a la base de datos. <b> post: </b> se ha eliminado el usuario que
	 * entra como parametro
	 * 
	 * @param usuario
	 *            - Usuario a eliminar. usuario != null
	 * @throws Exception
	 *             - cualquier error que se genera actualizando los usuarios
	 */
	public void deleteRestaurante(Restaurante restaurante) throws Exception {
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.deleteRestaurante(restaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base
	 * de datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Producto> darProductosPorRestaurante(Restaurante restaurante) throws Exception {
		List<Producto> productos;
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			////// transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.darProductosPorRestaurante(restaurante.getId());

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base
	 * de datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Usuario> darClientes() throws Exception {
		List<Usuario> clientes;
		DAOTablaUsuarios daoRestaurante = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			clientes = daoRestaurante.darClientes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base
	 * de datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Ingrediente> buscarIngredientesPorProductos(Producto producto) throws Exception {
		List<Ingrediente> ingredientes;
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingredientes = daoIngredientes.darIngredientesPorProductos(producto.getId());

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredientes;
	}
	
	/**
	 * Metodo que modela la transaccion que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parametro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addZona(Zona zona) throws Exception {
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.addZona(zona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
}
