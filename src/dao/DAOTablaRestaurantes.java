/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.Connection;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Monitores 2017-20
 */
public class DAOTablaRestaurantes {


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaRestaurantes() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Restaurante> darRestaurantes() throws SQLException, Exception {
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String representante = rs.getString("REPRESENTANTE");
			String paginaWeb = rs.getString("PAGINAWEB");
			Double balancePrecio = rs.getDouble("BALANCEPRECIO");
			Double balanceCosto = rs.getDouble("BALANCECOSTO");
			String tipo = rs.getString("TIPO");
			String idUsuario = rs.getString("ID_USUARIO");
			String nombreZona = rs.getString("NOMBRE_ZONA");
			
			restaurantes.add(new Restaurante(id, nombre, tipo, representante, paginaWeb, balancePrecio, balanceCosto, idUsuario, nombreZona));
		}
		return restaurantes;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Restaurante> buscarRestaurantesPorName(String pName) throws SQLException, Exception {
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTE WHERE NOMBRE ='" + pName + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String representante = rs.getString("REPRESENTANTE");
			String paginaWeb = rs.getString("PAGINAWEB");
			Double balancePrecio = rs.getDouble("BALANCEPRECIO");
			Double balanceCosto = rs.getDouble("BALANCECOSTO");
			String tipo = rs.getString("TIPO");
			String idUsuario = rs.getString("ID_USUARIO");
			String nombreZona = rs.getString("NOMBRE_ZONA");
			
			restaurantes.add(new Restaurante(id, nombre, tipo, representante, paginaWeb, balancePrecio, balanceCosto, idUsuario, nombreZona));
		}
		return restaurantes;
	}
	
	/**
	 * Metodo que busca el video con el id que entra como parametro.
	 * @param name - Id de el video a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Restaurante buscarRestaurantePorId(Long pId) throws SQLException, Exception 
	{
		Restaurante restaurante = null;

		String sql = "SELECT * FROM RESTAURANTE WHERE ID =" + pId;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String representante = rs.getString("REPRESENTANTE");
			String paginaWeb = rs.getString("PAGINAWEB");
			Double balancePrecio = rs.getDouble("BALANCEPRECIO");
			Double balanceCosto = rs.getDouble("BALANCECOSTO");
			String tipo = rs.getString("TIPO");
			String idUsuario = rs.getString("ID_USUARIO");
			String nombreZona = rs.getString("NOMBRE_ZONA");
			
			restaurante = new Restaurante(id, nombre, tipo, representante, paginaWeb, balancePrecio, balanceCosto, idUsuario,nombreZona);
		}
		return restaurante;
	}

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "INSERT INTO RESTAURANTE VALUES (";
		sql += restaurante.getId() + ",'";
		sql += restaurante.getNombre() + "','";		
		sql += restaurante.getRepresentante() + "','";
		sql += restaurante.getPaginaWeb() + "',";
		sql += restaurante.getBalancePrecio() + ",";
		sql += restaurante.getBalanceCosto() + ",'";
		sql += restaurante.getTipo() + "',";
		sql += restaurante.getUsuario() + ",'";
		sql += restaurante.getNombreZona() + "')";
		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el video que entra como parametro en la base de datos.
	 * @param video - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRestaurante(Restaurante usuario) throws SQLException, Exception {

		String sql = "UPDATE RESTAURANTE SET ";
		sql += "NOMBRE='" + usuario.getNombre() + "',";
		sql += "REPRESENTANTE='" + usuario.getRepresentante() + "',";
		sql += "PAGINAWEB='" + usuario.getPaginaWeb() + "',";
		sql += "BALANCEPRECIO=" + usuario.getBalancePrecio() + ",";
		sql += "BALANCECOSTO=" + usuario.getBalanceCosto() + ",";
		sql += "TIPO='" + usuario.getTipo() + "',";
		sql += "ID_USUARIO=" + usuario.getUsuario() + ",";
		sql += "NOMBRE_ZONA='" + usuario.getNombreZona()+ "'";
		sql += " WHERE IDENTIFICACION = " + usuario.getId() + ")";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el video que entra como parametro en la base de datos.
	 * @param video - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteRestaurante(Restaurante usuario) throws SQLException, Exception {

		String sql = "DELETE FROM RESTAURANTE";
		sql += " WHERE ID = " + usuario.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
	public void nullProds(Long idres) throws SQLException, Exception {

		String sql = "UPDATE PRODUCTO SET ID_RESTAURANTE = NULL ";
		sql += "WHERE ID_RESTAURANTE = " +  idres;

		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void eliminarProdsDisponibles(Long idres) throws SQLException, Exception {

		String sql = "UPDATE PRODUCTO SET DISPONIBLES = 0, MAXIMOPRODUCTOS = 0 ";
		sql += "WHERE ID_RESTAURANTE = "+ idres;

        System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void nullMenus(Long idres) throws SQLException, Exception {

		String sql = "UPDATE MENU SET ID_RESTAURANTE = NULL ";
		sql += "WHERE ID_RESTAURANTE =" +  idres;

		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	
	/**
	 * Da la informaci�n de los restaurantes
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<ConsultaPedido> darPedidosRestaurantes(String tipoCliente) throws SQLException, Exception {
		ArrayList<ConsultaPedido> pedidos = new ArrayList<ConsultaPedido>();

		String sql = "(SELECT DISTINCT ID_RESTAURANTE, NOMBRE_RESTAURANTE, ROL, ID_PRODUCTO, UNIDADES, INGRESO*UNIDADES AS INGRESOS  FROM (SELECT ID_PRODUCTO, SUM(CANTIDAD) AS UNIDADES FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT ID AS ID_RESTAURANTE, NOMBRE AS NOMBRE_RESTAURANTE FROM RESTAURANTE)  NATURAL JOIN (SELECT ID AS ID_PRODUCTO, ID_RESTAURANTE, PRECIO AS INGRESO FROM PRODUCTO)) NATURAL JOIN (SELECT * FROM PEDIDO_PRODUCTOS)) NATURAL JOIN (SELECT ID AS ID_PEDIDO, ID_CLIENTE FROM PEDIDO WHERE SERVIDO = 1)) NATURAL JOIN (SELECT IDENTIFICACION AS ID_CLIENTE, ROL FROM USUARIO WHERE ROL = '";
		sql += tipoCliente;
		sql+= "'))  GROUP BY ID_PRODUCTO) NATURAL JOIN (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT ID AS ID_RESTAURANTE, NOMBRE AS NOMBRE_RESTAURANTE FROM RESTAURANTE)  NATURAL JOIN (SELECT ID AS ID_PRODUCTO, ID_RESTAURANTE, PRECIO AS INGRESO FROM PRODUCTO)) NATURAL JOIN (SELECT * FROM PEDIDO_PRODUCTOS)) NATURAL JOIN (SELECT ID AS ID_PEDIDO, ID_CLIENTE FROM PEDIDO WHERE SERVIDO = 1)) NATURAL JOIN (SELECT IDENTIFICACION AS ID_CLIENTE, ROL FROM USUARIO WHERE ROL = '";
		sql+= tipoCliente +"'))) ORDER BY NOMBRE_RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID_RESTAURANTE");
			String nombre = rs.getString("NOMBRE_RESTAURANTE");
			String rol = rs.getString("ROL");
			Long idProducto = rs.getLong("ID_PRODUCTO");
			Long unidades = rs.getLong("UNIDADES");
			Long ingresos = rs.getLong("INGRESOS");
			
			pedidos.add(new ConsultaPedido(id, nombre, rol, idProducto, unidades, ingresos));
		}
		return pedidos;
	}

}
