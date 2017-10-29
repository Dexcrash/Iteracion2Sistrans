/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogot谩 - Colombia)
 * Departamento de Ingenier铆a de Sistemas y Computaci贸n
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe Garc铆a - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaci贸n
 * @author Monitores 2017-20
 */
public class DAOTablaProductos {


	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaProductos() {
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
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexi贸n que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> darProductos() throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			Long tiempoPreparacion = rs.getLong("TIEMPOPREPARACION");
			Double costo = rs.getDouble("COSTO");
			Double precio = rs.getDouble("PRECIO");
			Integer disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			
			productos.add(new Producto(id, nombre, tipo, disponibles, tiempoPreparacion, precio, costo, descripcion, traduccion, idRestaurante));
		}
		return productos;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> buscarProductosPorName(String pName) throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO WHERE NOMBRE ='" + pName + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			Long tiempoPreparacion = rs.getLong("TIEMPOPREPARACION");
			Double costo = rs.getDouble("COSTO");
			Double precio = rs.getDouble("PRECIO");
			Integer disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			
			productos.add(new Producto(id, nombre, tipo, disponibles, tiempoPreparacion, precio, costo, descripcion, traduccion, idRestaurante));
		}
		return productos;
	}
	
	/**
	 * Metodo que busca el video con el id que entra como parametro.
	 * @param name - Id de el video a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Producto buscarProductoPorId(Long pId) throws SQLException, Exception 
	{
		Producto producto = null;

		String sql = "SELECT * FROM PRODUCTO WHERE ID =" + pId;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			Long tiempoPreparacion = rs.getLong("TIEMPOPREPARACION");
			Double costo = rs.getDouble("COSTO");
			Double precio = rs.getDouble("PRECIO");
			Integer disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			
			producto = new Producto(id, nombre, tipo, disponibles, tiempoPreparacion, precio, costo, descripcion, traduccion, idRestaurante);
		}
		return producto;
	}
	


	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProducto(Producto producto) throws SQLException, Exception {

		String sql = "INSERT INTO PRODUCTO VALUES (";
		sql += producto.getId() + ",'";
		sql += producto.getNombre() + "','";
		sql += producto.getDescripcion() + "','";
		sql += producto.getTraduccion() + "',";
		sql += producto.getTiempoPreparacion() + ",";
		sql += producto.getCosto() + ",";
		sql += producto.getPrecio() + ",";
		sql += producto.getDisponibles() + ",'";
		sql += producto.getTipo() + "',";
		sql += producto.getIdRestaurante() + ")";
		
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
	public void updateProducto(Producto producto) throws SQLException, Exception {

		String sql = "UPDATE RESTAURANTE SET ";
		sql += "ID='" + producto.getId()+ ",";
		sql += "NOMBRE='" + producto.getNombre() + "',";
		sql += "TIPO='" + producto.getTipo() + "',";
		sql += "TIEMPOPREPARACION='" + producto.getTiempoPreparacion() + "',";
		sql += "DISPONIBLES='" + producto.getDisponibles() + ",";
		sql += "PRECIO='" + producto.getPrecio() + ",";
		sql += "COSTO='" + producto.getCosto() + ",";
		sql += "DESCRIPCION='" + producto.getDescripcion()+ "',";
		sql += "TRADUCCION='" + producto.getTraduccion() + "',";
		sql += "IDRESTAURANTE='" + producto.getIdRestaurante() + "',";
		sql += " WHERE IDENTIFICACION = " + producto.getId() + ")";


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
	public void deleteProducto(Producto producto) throws SQLException, Exception {

		String sql = "DELETE FROM PRODUCTO";
		sql += " WHERE ID = " + producto.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> darProductosPorRestaurante(Long id_Restaurante) throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO WHERE ID_RESTAURANTE = " + id_Restaurante +"";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			Long tiempoPreparacion = rs.getLong("TIEMPOPREPARACION");
			Double costo = rs.getDouble("COSTO");
			Double precio = rs.getDouble("PRECIO");
			Integer disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			
			productos.add(new Producto(id, nombre, tipo, disponibles, tiempoPreparacion, precio, costo, descripcion, traduccion, idRestaurante));
		}
		return productos;
	}

	/**
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> buscarProductosMasOfrecidos() throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO JOIN " + 
				"(SELECT X.ID, COUNT(X.ID)AS CUENTA FROM " + 
				"(SELECT PRODUCTO.ID, PRODUCTO.NOMBRE, MENU.ID AS IDM " + 
				"FROM (PRODUCTO JOIN MENU ON PRODUCTO.ID = MENU.ID_PRODUCTOENTRADA) " + 
				"UNION SELECT PRODUCTO.ID, PRODUCTO.NOMBRE, MENU.ID AS IDM " + 
				"FROM (PRODUCTO JOIN MENU ON PRODUCTO.ID = MENU.ID_PRODUCTOACOMPA袮MIENTO) " + 
				"UNION SELECT PRODUCTO.ID, PRODUCTO.NOMBRE, MENU.ID AS IDM " + 
				"FROM (PRODUCTO JOIN MENU ON PRODUCTO.ID = MENU.ID_PRODUCTOPLATOFUERTE) " + 
				"UNION SELECT PRODUCTO.ID, PRODUCTO.NOMBRE, MENU.ID AS IDM " + 
				"FROM (PRODUCTO JOIN MENU ON PRODUCTO.ID = MENU.ID_PRODUCTOPOSTRE) " + 
				"UNION SELECT PRODUCTO.ID, PRODUCTO.NOMBRE, MENU.ID AS IDM " + 
				"FROM (PRODUCTO JOIN MENU ON PRODUCTO.ID = MENU.ID_PRODUCTOBEBIDA))X " + 
				"GROUP BY X.ID)Z ON PRODUCTO.ID = Z.ID " + 
				"WHERE CUENTA IN (SELECT MAX(CUENTA) AS MAXIMO FROM " + 
				"(SELECT X.ID, COUNT(X.ID)AS CUENTA FROM " + 
				"(SELECT PRODUCTO.ID, PRODUCTO.NOMBRE, MENU.ID AS IDM " + 
				"FROM (PRODUCTO JOIN MENU ON PRODUCTO.ID = MENU.ID_PRODUCTOENTRADA)" + 
				"UNION SELECT PRODUCTO.ID, PRODUCTO.NOMBRE, MENU.ID AS IDM " + 
				"FROM (PRODUCTO JOIN MENU ON PRODUCTO.ID = MENU.ID_PRODUCTOACOMPA袮MIENTO) " + 
				"UNION SELECT PRODUCTO.ID, PRODUCTO.NOMBRE, MENU.ID AS IDM " + 
				"FROM (PRODUCTO JOIN MENU ON PRODUCTO.ID = MENU.ID_PRODUCTOPLATOFUERTE) " + 
				"UNION SELECT PRODUCTO.ID, PRODUCTO.NOMBRE, MENU.ID AS IDM " + 
				"FROM (PRODUCTO JOIN MENU ON PRODUCTO.ID = MENU.ID_PRODUCTOPOSTRE) " + 
				"UNION SELECT PRODUCTO.ID, PRODUCTO.NOMBRE, MENU.ID AS IDM " + 
				"FROM (PRODUCTO JOIN MENU ON PRODUCTO.ID = MENU.ID_PRODUCTOBEBIDA))X " + 
				"GROUP BY X.ID))";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			Long tiempoPreparacion = rs.getLong("TIEMPOPREPARACION");
			Double costo = rs.getDouble("COSTO");
			Double precio = rs.getDouble("PRECIO");
			Integer disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			
			productos.add(new Producto(id, nombre, tipo, disponibles, tiempoPreparacion, precio, costo, descripcion, traduccion, idRestaurante));
		}
		return productos;
	}

	/**
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void descontarProducto(Long id_Producto) throws SQLException, Exception {
		String sql = "UPDATE PRODUCTO SET DISPONIBLES = DISPONIBLES - 1 WHERE ID = " + id_Producto;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> darProductosPorCategoria(String categoria) throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO JOIN CATEGORIA ON PRODUCTO.ID = CATEGORIA.ID_PRODUCTO WHERE CATEGORIA.NOMBRE = '" + categoria+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			Long tiempoPreparacion = rs.getLong("TIEMPOPREPARACION");
			Double costo = rs.getDouble("COSTO");
			Double precio = rs.getDouble("PRECIO");
			Integer disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			
			productos.add(new Producto(id, nombre, tipo, disponibles, tiempoPreparacion, precio, costo, descripcion, traduccion, idRestaurante));
		}
		return productos;
	}
	
	/**
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> darProductosPorRangoPrecio(String rangoPrecios) throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String [] precios= rangoPrecios.split(":");
		
		String sql = "SELECT * FROM PRODUCTO WHERE PRECIO > " + precios[0] + "AND PRECIO < "+ precios[1] ;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			Long tiempoPreparacion = rs.getLong("TIEMPOPREPARACION");
			Double costo = rs.getDouble("COSTO");
			Double precio = rs.getDouble("PRECIO");
			Integer disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			
			productos.add(new Producto(id, nombre, tipo, disponibles, tiempoPreparacion, precio, costo, descripcion, traduccion, idRestaurante));
		}
		return productos;
	}
	
	
	public void agragarEquivalencia(Long id1, Long id2) throws SQLException, Exception {

		String sql = "INSERT INTO EQUIVALENCIAPRODUCTOS VALUES (";
		sql += id1 + ",";
		sql += id2 + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void surtirProductosDeRestaurante(Long id) throws SQLException, Exception{
		
		String sql = "UPDATE RESTAURANTE SET DISPONIBLES = MAXIMOPRODUCTOS";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
	}

	public ArrayList<Long> darEquivalencias(Long id) throws SQLException, Exception {
		ArrayList<Long> productos = new ArrayList<Long>();
		
		String sql = "SELECT EQUIVALENCIA FROM EQUIVALENCIAPRODUCTOS WHERE ORIGINAL = " + id ;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idEq = rs.getLong("EQUIVALENCIA");
			productos.add(idEq);
		}
		return productos;
	}
	
}
