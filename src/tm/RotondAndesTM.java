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
import dao.DAOTablaPreferencias;
import dao.DAOTablaMenus;
import dao.DAOTablaPedido;
import dao.DAOTablaProductos;
import dao.DAOTablaRestaurantes;
import dao.DAOTablaUsuarios;
import dao.DAOTablaZonas;
import dtm.RotondAndesDistributed;
import jms.NonReplyException;
import vos.ConsultaCliente;
import vos.ConsultaConsumo;
import vos.ConsultaFiltros;
import vos.ConsultaPedido;
import vos.ConsultaRentabilidadLocal;
import vos.ConsultaZona;
import vos.Ingrediente;
import vos.ListaProductos;
import vos.Preferencia;
import vos.Menu;
import vos.Pedido;
import vos.PedidoCompleto;
import vos.Producto;
import vos.Restaurante;
import vos.Usuario;
import vos.Zona;

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
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;
	
	private RotondAndesDistributed dtm;

	/**
	 * Metodo constructor de la clase UsuarioAndesMaster, esta clase modela y
	 * contiene cada una de las transacciones y la logica de negocios que estas
	 * conllevan. <b>post: </b> Se crea el objeto UsuarioAndesTM, se inicializa el
	 * path absoluto del archivo de conexion y se inicializa los atributos que se
	 * usan par la conexion a la base de datos.
	 * 
	 * @param contextPathP
	 *            - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que inicializa los atributos que se usan para la conexion a la base de
	 * datos. <b>post: </b> Se han inicializado los atributos que se usan par la
	 * conexion a la base de datos.
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
	 *             - Cualquier error que se genere durante la conexion a la base de
	 *             datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	/////// Transacciones////////////////////
	////////////////////////////////////////

	/**
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base de
	 * datos.
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
	 * Metodo que modela la transaccion que busca el usuario en la base de datos con
	 * el id que entra como parametro.
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
	 * parametro a la base de datos. <b> post: </b> se han agregado los usuarios que
	 * entran como parametro
	 * 
	 * @param usuarios
	 *            - objeto que modela una lista de usuarios y se estos se pretenden
	 *            agregar. usuarios != null
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
	 * parametro a la base de datos. <b> post: </b> se ha actualizado el usuario que
	 * entra como parametro
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
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base de
	 * datos.
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
	 * Metodo que modela la transaccion que busca el usuario en la base de datos con
	 * el id que entra como parametro.
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
	 * parametro a la base de datos. <b> post: </b> se han agregado los usuarios que
	 * entran como parametro
	 * 
	 * @param usuarios
	 *            - objeto que modela una lista de usuarios y se estos se pretenden
	 *            agregar. usuarios != null
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
	 * parametro a la base de datos. <b> post: </b> se ha actualizado el usuario que
	 * entra como parametro
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
	public Restaurante deleteRestauranteLocal(Long idRes) throws Exception {
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		Restaurante aborrar = null;
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			aborrar = daoRestaurantes.buscarRestaurantePorId(idRes);
			//Poner en null el restaurante de los productos y los menus
			
  
			daoRestaurantes.eliminarProdsDisponibles(idRes);

			daoRestaurantes.setConn(conn);
			daoRestaurantes.nullMenus(idRes);
			
			daoRestaurantes.setConn(conn);		
			daoRestaurantes.nullProds(idRes);
			
			daoRestaurantes.setConn(conn);	
			daoRestaurantes.deleteRestaurante(aborrar);
			
            
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
		return aborrar;
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base de
	 * datos.
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
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base de
	 * datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Ingrediente> buscarIngredientesPorProductos(Long idRestaurante, Long idProducto) throws Exception {
		List<Ingrediente> ingredientes;
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingredientes = daoIngredientes.darIngredientesPorProductos(idProducto);

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
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base de
	 * datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public Ingrediente addIngredientePorProducto(Long idRestaurante, Long idProducto, Ingrediente ingrediente)
			throws Exception {
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			if (daoIngredientes.buscarIngredientePorId(ingrediente.getId()) == null)
				daoIngredientes.addIngrediente(ingrediente);
			daoIngredientes.relacionarIngredienteConProducto(ingrediente.getId(), idProducto);

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
		return ingrediente;
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base de
	 * datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public void addZona(Zona zona) throws Exception {
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		try {
			////// transaccion
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
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base de
	 * datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public Producto addProductoPorRestaurante(Long idRestaurante, Producto producto) throws Exception {
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			////// transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.addProducto(producto);

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
		return producto;
	}

	/**
	 * Metodo que modela la transaccion que retorna todos los usuarios de la base de
	 * datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Producto> buscarProductosPorRestaurante(Long idRestaurante) throws Exception {
		List<Producto> productos;
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			////// transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.darProductosPorRestaurante(idRestaurante);

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

	public List<Preferencia> darPreferencias(Long idCliente) throws Exception {
		List<Preferencia> preferencias;
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			preferencias = daoPreferencias.buscarPreferenciaPorIdUsuario(idCliente);

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
				daoPreferencias.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencias;
	}

	public Preferencia addPreferencia(Long idCLiente, Preferencia preferencia) throws Exception {
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			daoPreferencias.addPreferencia(preferencia);

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
				daoPreferencias.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencia;
	}

	/**
	 * Metodo que modela la transaccion que retorna todas las zonas de la base de
	 * datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Zona> darZonas() throws Exception {
		List<Zona> zonas;
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		try {
			////// transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			zonas = daoZonas.darZonas();

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

				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonas;
	}

	public void deleteZona(Zona zona) throws Exception {
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		try {
			////// transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.deleteZona(zona);

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
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addMenu(Menu menu) throws Exception {
		DAOTablaMenus daoMenus = new DAOTablaMenus();

		try {
			////// transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			Producto entrada = null;
			Producto acompa = null;
			Producto platof = null;
			Producto postre = null;
			Producto bebida = null;

			if (menu.getIdentrada() != null)
				entrada = daoMenus.buscarProductoPorId(menu.getIdentrada());
			if (menu.getIdacompa�amiento() != null)
				acompa = daoMenus.buscarProductoPorId(menu.getIdacompa�amiento());
			if (menu.getIdplatoFuerte() != null)
				platof = daoMenus.buscarProductoPorId(menu.getIdplatoFuerte());
			if (menu.getIdpostre() != null)
				postre = daoMenus.buscarProductoPorId(menu.getIdpostre());
			if (menu.getIdbebida() != null)
				bebida = daoMenus.buscarProductoPorId(menu.getIdbebida());
			Long idres = menu.getIdrestaurante();

			if (entrada != null) {
				if (entrada.getIdRestaurante() != idres) {
					throw new Exception(
							"El restaurante " + idres + " no tiene al producto con id " + menu.getIdentrada());
				}
			}
			if (acompa != null) {
				if (acompa.getIdRestaurante() != idres) {
					throw new Exception(
							"El restaurante " + idres + "  no tiene al producto con id " + menu.getIdacompa�amiento());
				}
			}
			if (platof != null) {
				if (platof.getIdRestaurante() != idres) {
					throw new Exception(
							"El restaurante " + idres + "  no tiene al producto con id " + menu.getIdplatoFuerte());
				}
			}
			if (postre != null) {
				if (postre.getIdRestaurante() != idres) {
					throw new Exception(
							"El restaurante " + idres + "  no tiene al producto con id " + menu.getIdpostre());
				}
			}
			if (bebida != null) {
				if (bebida.getIdRestaurante() != idres) {
					throw new Exception(
							"El restaurante " + idres + "  no tiene al producto con id " + menu.getIdbebida());
				}
			}

			daoMenus.addMenu(menu);
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
				daoMenus.cerrarRecursos();
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
	 * Metodo que modela la transaccion que retorna todas las zonas de la base de
	 * datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public List<Menu> darMenus() throws Exception {
		List<Menu> menu;
		DAOTablaMenus daoMenus = new DAOTablaMenus();
		try {
			////// transaccion
			this.conn = darConexion();
			daoMenus.setConn(conn);
			menu = daoMenus.darMenus();

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
				daoMenus.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menu;
	}

	public Preferencia updatePreferencia(Long idCliente, Preferencia preferencia) throws Exception {
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			daoPreferencias.updatePreferencia(preferencia);

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
				daoPreferencias.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencia;
	}

	public List<Producto> buscarProductosMasOfrecidos() throws Exception {

		DAOTablaProductos daoProductos = new DAOTablaProductos();
		List<Producto> productos;
		try {
			////// transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.buscarProductosMasOfrecidos();

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

	public void realizarPedido(PedidoCompleto pedido) throws Exception {

		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		DAOTablaProductos daoProdutos = new DAOTablaProductos();
		DAOTablaMenus daoMenus = new DAOTablaMenus();

		String menus;
		String productos;
		try {
			////// transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			daoPedidos.addPedido(new Pedido(pedido.getFecha(), pedido.getIdCliente(), pedido.getId(),
					pedido.getServido(), pedido.getIdMesa()));
			daoPedidos.cerrarRecursos();
			menus = pedido.getIdsMenu();
			String[] info;
			String[] platos;
			if (menus != "" && menus!=null) {
				String[] menuIds = menus.split("-");

				ArrayList<Long> platosOriginales = new ArrayList<>();
				for (String idMenu : menuIds) {
					daoMenus.setConn(conn);
					info = idMenu.split(":");
					platos = info[1].split(",");
					platosOriginales = daoMenus.darProductodeMenu(Long.parseLong(info[0]));
					daoMenus.cerrarRecursos();
					daoProdutos.setConn(conn);
					for (int i = 0; i < 5; i++) {
						if (Long.parseLong(platos[i + 1]) != 0) {
							if (!daoProdutos.esEquivalente(platosOriginales.get(i), Long.parseLong(platos[i + 1]))) {
								throw new Exception("Hay productos no equivalentes.");
							}
						} else {
							platos[i + 1] = platosOriginales.get(i).toString();
						}
					}
					daoProdutos.cerrarRecursos();
					daoPedidos.setConn(conn);
					daoPedidos.addMenuAPedido(pedido.getId(), Long.parseLong(info[0]), Integer.parseInt(platos[0]),
							Long.parseLong(platos[1]), Long.parseLong(platos[2]), Long.parseLong(platos[3]),
							Long.parseLong(platos[4]), Long.parseLong(platos[5]));
				}
			}

			productos = pedido.getIdsProducto();
			String[] productoIds = productos.split("-");
			for (String idProducto : productoIds) {
				info = idProducto.split(":");
				daoPedidos.addProductoAPedido(pedido.getId(), Long.parseLong(info[0]), Integer.parseInt(info[1]));
			}

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
				daoPedidos.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<ConsultaZona> darConsultaZonas(String nombrezona, String orden) throws Exception {
		List<ConsultaZona> zonas;
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		try {
			////// transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			String ordensql = null;
			if (daoZonas.buscarZonaPorNombre(nombrezona) == null) {
				throw new Exception("La zona no existe");
			}
			if (orden.equalsIgnoreCase("nombrezona")) {
				ordensql = "NOMBRE_ZONA";
			} else if (orden.equalsIgnoreCase("numeroespacios")) {
				ordensql = "NUMERO_ESPACIOS";
			} else if (orden.equalsIgnoreCase("capacidadTotal")) {
				ordensql = "CAPACIDAD";
			} else if (orden.equalsIgnoreCase("numeroespaciosParaDiscapacitados")) {
				ordensql = "NUM_ESPACIOS_DISCAPACITADOS";
			} else if (orden.equalsIgnoreCase("numeroespaciosAbiertos")) {
				ordensql = "NUM_ESPACIOS_ABIERTOS";
			} else if (orden.equalsIgnoreCase("idPedidoServido")) {
				ordensql = "ID_PEDIDO_SERVIDO";
			} else if (orden.equalsIgnoreCase("fechaServicio")) {
				ordensql = "FECHAHORA_SERVIDO";
			} else if (orden.equalsIgnoreCase("nombreProducto")) {
				ordensql = "NOMBRE_PRODUCTO_SERVIDO";
			} else if (orden.equalsIgnoreCase("nombreRestaurante")) {
				ordensql = "NOMBRE_RESTAURANTE_EN_ZONA";
			} else {
				throw new Exception("El par�metro de ordenamiento no es v�lido");
			}
			zonas = daoZonas.darInformacionZona(nombrezona, ordensql);
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

				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonas;
	}

	public List<ConsultaCliente> darConsultaCliente(Long idcl) throws Exception {
		List<ConsultaCliente> clientes;
		DAOTablaUsuarios daoClientes = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoClientes.setConn(conn);
			Usuario c = daoClientes.buscarUsuarioPorId(idcl.toString());
			if (c == null)
				throw new Exception("El usuario no existe");
			if (!c.getRol().equals("Cliente"))
				throw new Exception("El usuario no es un cliente");

			clientes = daoClientes.darInformacionCliente(idcl);

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
				daoClientes.cerrarRecursos();

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

	public String marcarServido(Long id) throws Exception {
		List<Long> productos;
		List<Long> menus;
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		DAOTablaMenus daoMenus = new DAOTablaMenus();
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPedido.setConn(conn);
			daoPedido.marcarServido(id);
			productos = daoPedido.darProductosDePedido(id);
			menus = daoPedido.darMenusDePedido(id);
			daoPedido.cerrarRecursos();
			daoMenus.setConn(conn);
			for (Long idMenu : menus)
				productos.addAll(daoMenus.darProductodeMenu(idMenu));
			daoMenus.cerrarRecursos();
			daoProductos.setConn(conn);
			for (Long idProducto : productos)
				daoProductos.descontarProducto(idProducto);

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
		return "Se sirvio el pedido con el id : " + id;
	}

	public List<Producto> consultarProductosFiltros(ConsultaFiltros consulta) throws Exception {
		Long idRestaurante = consulta.getIdRestaurante();
		String categoria = consulta.getCategoria();
		String fechas = consulta.getFechas();
		String precios = consulta.getPrecios();
		ArrayList<Producto> filtro = new ArrayList<>();
		List<Long> pedidos = new ArrayList<>();
		List<Long> productosPedidos = new ArrayList<>();
		List<Long> menus = new ArrayList<>();
		ArrayList<Producto> productos = new ArrayList<>();
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		DAOTablaMenus daoMenus = new DAOTablaMenus();
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			////// transaccion
			this.conn = darConexion();
			if(fechas!=null){
			if (!fechas.equals("")) {
				daoPedido.setConn(conn);
				pedidos = daoPedido.darPedidosPorFecha(fechas);
				for (Long idPedido : pedidos) {
					productosPedidos.addAll(daoPedido.darProductosDePedido(idPedido));
					menus.addAll(daoPedido.darMenusDePedido(idPedido));
				}
				daoPedido.cerrarRecursos();
				daoMenus.setConn(conn);
				for (Long idMenu : menus) {
					productosPedidos.addAll(daoMenus.darProductodeMenu(idMenu));
				}
				daoMenus.cerrarRecursos();
			}
			}
			daoProductos.setConn(conn);
			productos = daoProductos.darProductos();
			if (idRestaurante != null) {
				filtro = daoProductos.darProductosPorRestaurante(idRestaurante);
				productos = unir(productos, filtro);
			}
			if (categoria != null) {
				filtro = daoProductos.darProductosPorCategoria(categoria);
				productos = unir(productos, filtro);
			}
			if (precios != null) {
				filtro = daoProductos.darProductosPorRangoPrecio(precios);
				productos = unir(productos, filtro);
			}
			if (fechas != null) {
				for (Long idProducto : productosPedidos)
					filtro.add(daoProductos.buscarProductoPorId(idProducto));
				productos = unir(productos, filtro);
			}

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

	public boolean agregarEquivalenciaProducto(Long id1, Long id2) throws Exception {
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			////// transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.agragarEquivalencia(id1, id2);

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
		return true;

	}

	public boolean darEquivalenciaProducto(Long id) throws Exception {
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			////// transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.darEquivalencias(id);

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
		return true;
	}

	public boolean darEquivalenciaIngrediente(Long id) throws Exception {
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.darEquivalencias(id);

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
		return true;

	}

	public boolean agregarEquivalenciaIngredientes(Long id1, Long id2) throws Exception {
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try {
			////// transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.agragarEquivalencia(id1, id2);

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
		return true;
	}

	public boolean surtirRestaurante(Long id) throws Exception {
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			////// transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.surtirProductosDeRestaurante(id);

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
		return true;
	}

	/**
	 * Crea un arraylist dond euniamente esten los elementos que se encuentran tanto
	 * en la primera lista como en la segunda.
	 * 
	 * @param l1
	 *            Lista base
	 * @param l2
	 *            Lista de comparacion
	 * @return Lista con los elementos que estan en l1 y tambien en l2
	 */
	private ArrayList<Producto> unir(ArrayList<Producto> l1, ArrayList<Producto> l2) {
		ArrayList<Producto> union = new ArrayList<>();
		for (Producto p1 : l1) {
			for (Producto p2 : l2) {
				if (p1.getId().equals(p2.getId()))
					union.add(p1);
			}
		}
		return union;
	}

	/**
	 * Devuelve el consumo de un cliente
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ConsultaConsumo> darConsumoCliente(Long idCliente) throws Exception {
		List<ConsultaConsumo> consumos;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			consumos = daoUsuarios.darInformacionConsumo(idCliente);
			if (consumos.isEmpty()) {
				throw new Exception("El cliente con id " + idCliente
						+ " no est� registrado o no ha consumido productos en RotondAndes");
			}

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
		return consumos;
	}

	/**
	 * Devuelve el consumo de un cliente
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ConsultaPedido> darInformePedidos(String tipoCliente) throws Exception {
		List<ConsultaPedido> pedidos;
		DAOTablaRestaurantes daoRes = new DAOTablaRestaurantes();
		try {
			////// transaccion
			String f = "";
			if (tipoCliente.equalsIgnoreCase("registrado")) {
				f = "Cliente";
			} else if (tipoCliente.equalsIgnoreCase("noregistrado")) {
				f = "No registrado";
			} else {
				throw new Exception("El rol del cliente no es v�lido");
			}
			this.conn = darConexion();
			daoRes.setConn(conn);
			pedidos = daoRes.darPedidosRestaurantes(f);

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
				daoRes.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedidos;
	}

	public String marcarServidosMesa(Long idMesa) throws Exception {
		List<Long> pedidos;
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPedido.setConn(conn);
			pedidos = daoPedido.darPedidosDeMesa(idMesa);
			for (Long id : pedidos) {
				marcarServido(id);
			}

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
				daoPedido.cerrarRecursos();

				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return "Se sirvieron todos los pedidos de la mesa " + idMesa;
	}

	public String cancelarPedido(Long idMesa) throws Exception {
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPedido.setConn(conn);
			if (daoPedido.darServidos(idMesa) != null) {
				System.err.println("No deberia entrar");
				return "Ya hay pedidos servidos.";
			}
			for (Long id : daoPedido.darPedidosDeMesa(idMesa)) {
				daoPedido.eliminarMenusDePedido(id);
				daoPedido.eliminarProductosDePedido(id);
			}
			daoPedido.eliminarPedidoDeMesa(idMesa);
			System.err.println("Se supone que elimino");
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
				daoPedido.cerrarRecursos();

				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return "Se cancelaron los pedidos de la mesa " + idMesa;
	}

	
	public List<Usuario> darConsumo(String res, String fecha1, String fecha2, String filtro) throws Exception {		
			List<Usuario> clientes;
			DAOTablaUsuarios daoRestaurante = new DAOTablaUsuarios();
			try {
				////// transaccion
				this.conn = darConexion();
				daoRestaurante.setConn(conn);
				clientes = daoRestaurante.darConsumo(res, fecha1, fecha2, filtro);

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
	
	public List<Usuario> darNoConsumo(String res, String fecha1, String fecha2, String filtro) throws Exception {		
		List<Usuario> clientes;
		DAOTablaUsuarios daoRestaurante = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			clientes = daoRestaurante.darNoConsumo(res, fecha1, fecha2, filtro);

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
	
	public List<Usuario> darBuenosClientes() throws Exception {		
		List<Usuario> clientes;
		DAOTablaUsuarios daoRestaurante = new DAOTablaUsuarios();
		try {
			////// transaccion
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			clientes = daoRestaurante.darBuenosClientes();

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
	
	//
	/**
	 * Metodo que modela la transaccion que retorna todas las zonas de la base de
	 * datos.
	 * 
	 * @return ListaUsuarios - objeto que modela un arreglo de usuarios. este
	 *         arreglo contiene el resultado de la busqueda
	 * @throws Exception
	 *             - cualquier error que se genere durante la transaccion
	 */
	public ListaProductos darProductosLocal() throws Exception {
		List<Producto> productos;
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			////// transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.darProductos();

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
		return new ListaProductos(productos);
	}
	
	/**
	 * Método que modela la transacci�n que retorna todos los productos
	 * @return ListaProductos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaProductos darProductos() throws Exception {
		ListaProductos remL = darProductosLocal();
		try
		{
//			ListaProductos resp = dtm.getRemoteProductos();
//			System.out.println(resp.getProductos().size());
//			remL.getProductos().addAll(resp.getProductos());
		}
		catch(Exception e)
		{
			
		}
		return remL;
	}
	
	public ConsultaRentabilidadLocal darRentabilidad(String fecha1, String fecha2, Long idres) throws Exception {
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		ConsultaRentabilidadLocal renta = null;
		try {
			////// transaccion
			this.conn = darConexion();
			daoPedido.setConn(conn);
			renta = daoPedido.darRentabilidad(fecha1, fecha2, idres);
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
				daoPedido.cerrarRecursos();

				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return renta;
	}

}
