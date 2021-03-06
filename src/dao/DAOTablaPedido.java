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
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Monitores 2017-20
 */
public class DAOTablaPedido {


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
	public DAOTablaPedido() {
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
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPedido(Pedido pedido) throws SQLException, Exception {
		String sql = "INSERT INTO PEDIDO VALUES (TO_DATE('";
		sql += pedido.getFecha() + "', 'YYYY-MM-DD HH24:MI:SS'),'";
		sql += pedido.getIdCliente() + "',";
		sql += pedido.getId() + ",";
		sql += pedido.getServido()+ ",";
		sql += pedido.getIdMesa() + ")";

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
	public void deletePedido(Pedido pedido) throws SQLException, Exception {

		String sql = "DELETE FROM PEDIDO";
		sql += " WHERE ID = " + pedido.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addMenuAPedido(Long idPedido, long idMenu, int cant, long id1, long id2, long id3, long id4,long id5) throws SQLException, Exception {

		String sql = "INSERT INTO PEDIDO_MENUS VALUES (";
		sql += idPedido + ",";
		sql += idMenu + ",";		
		sql += cant + ",";
		if(id1 == 0)sql += "null ,";
		else sql += id1 + ",";
		if(id2 == 0)sql += "null ,";
		else sql += id2 + ",";
		if(id3 == 0)sql += "null ,";
		else sql += id3 + ",";
		if(id4 == 0)sql += "null ,";
		else sql += id4 + ",";
		if(id5 == 0)sql += "null)";
		else sql += id5 + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProductoAPedido(Long idPedido, long idProducto, int cant) throws SQLException, Exception {

		String sql = "INSERT INTO PEDIDO_PRODUCTOS VALUES (";
		sql += idPedido + ",";
		sql += idProducto + ",";		
		sql += cant + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void marcarServido(Long idPedido) throws SQLException, Exception {

		String sql = "UPDATE PEDIDO SET SERVIDO = 1 WHERE ID =" + idPedido;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Long> darProductosDePedido(Long idPedido) throws SQLException, Exception {
		ArrayList<Long> productos = new ArrayList<Long>();

		String sql = "SELECT ID_PRODUCTO FROM PEDIDO_PRODUCTOS WHERE ID_PEDIDO =" +idPedido;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			productos.add(rs.getLong("ID_PRODUCTO"));
		}
		return productos;
	}

	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Long> darMenusDePedido(Long idPedido) throws SQLException, Exception {
		ArrayList<Long> productos = new ArrayList<Long>();

		String sql = "SELECT ID_MENU FROM PEDIDO_MENUS WHERE ID_PEDIDO =" +idPedido;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			productos.add(rs.getLong("ID_MENU"));
		}
		return productos;
	}
	
	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Long> darPedidosPorFecha(String rangoFechas) throws SQLException, Exception {
		ArrayList<Long> pedidos = new ArrayList<Long>();
		String[] fechas = rangoFechas.split("y");
       System.out.println(fechas[0]);
       System.out.println(fechas[1]);
		String sql = "SELECT ID FROM PEDIDO WHERE FECHAHORA > (TO_DATE('" + fechas[0] 
				+ "', 'YYYY-MM-DD HH24:MI:SS')) AND FECHAHORA < (TO_DATE('" + fechas[1] +"', 'YYYY-MM-DD HH24:MI:SS'))";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			pedidos.add(rs.getLong("ID"));
		}
		return pedidos;
	}	
	
	public ArrayList<Long> darPedidosDeMesa(Long idMesa) throws SQLException, Exception{
		ArrayList<Long> pedidos = new ArrayList<Long>();

		String sql = "SELECT ID FROM PEDIDO WHERE id_MESA =" +idMesa;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			pedidos.add(rs.getLong("ID"));
		}
		return pedidos;
	}
	
	public void eliminarPedidoDeMesa(Long idMesa) throws SQLException, Exception{
		String sql = "DELETE FROM PEDIDO WHERE ID_MESA =" +idMesa;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void eliminarProductosDePedido(Long idPedido) throws SQLException, Exception{
		String sql = "DELETE FROM PEDIDO_PRODUCTOS WHERE ID_PEDIDO =" +idPedido;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void eliminarMenusDePedido(Long idPedido) throws SQLException, Exception{
		String sql = "DELETE FROM PEDIDO_MENUS WHERE ID_PEDIDO =" +idPedido;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public ArrayList<Long> darServidos(Long idMesa) throws SQLException, Exception {
		ArrayList<Long> pedidos = new ArrayList<Long>();

		String sql = "SELECT ID FROM PEDIDO WHERE ID_MESA =" +idMesa + "AND SERVIDO = 1";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			pedidos.add(rs.getLong("ID"));
		}
		if(pedidos.size()==0)return null;
		
		return pedidos;
	}
	
	public ConsultaRentabilidadLocal darRentabilidad(String fecha1, String fecha2, Long idres) throws SQLException, Exception {
		ConsultaRentabilidadLocal renta = null;

		String sql = "SELECT PERDIDAST, GANANCIAST, GANANCIAST-PERDIDAST AS BALANCE FROM (SELECT SUM(PERDIDAS) AS PERDIDAST, SUM(GANANCIAS) AS GANANCIAST FROM (SELECT  CANTIDAD*COSTO AS PERDIDAS, CANTIDAD*PRECIO AS GANANCIAS FROM (SELECT * FROM (SELECT *  FROM PEDIDO WHERE FECHAHORA BETWEEN TO_DATE('";
		sql += fecha1;
		sql+= "', 'YYYY-MM-DD')  AND TO_DATE('";
		sql+= fecha2;
		sql+= "', 'YYYY-MM-DD') AND SERVIDO = 1)";
		sql+= "NATURAL JOIN (SELECT ID_PEDIDO AS ID, ID_PRODUCTO, CANTIDAD FROM PEDIDO_PRODUCTOS)) NATURAL JOIN (SELECT ID AS ID_PRODUCTO, COSTO, PRECIO  FROM PRODUCTO WHERE ID_RESTAURANTE =";
		sql+=  idres + ")))";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			
			Long perd = rs.getLong("PERDIDAST");
			Long gan = rs.getLong("GANANCIAST");
			Long tot = rs.getLong("BALANCE");
			renta = new ConsultaRentabilidadLocal(perd, gan, tot);
		}
		return renta;
	}
	
}
