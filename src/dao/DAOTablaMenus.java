package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

public class DAOTablaMenus {

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
	public DAOTablaMenus(){
			recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for (Object ob : recursos) {
			if (ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la
	 * conexión que entra como parametro.
	 * 
	 * @param con
	 *            - connection a la base de datos
	 */
	public void setConn(Connection con) {
		this.conn = con;
	}

	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los videos
	 * de la base de datos <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * 
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException
	 *             - Cualquier error que la base de datos arroje.
	 * @throws Exception
	 *             - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Menu> darMenus() throws SQLException, Exception {
		ArrayList<Menu> menus = new ArrayList<Menu>();

		String sql = "SELECT * FROM MENU";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id = rs.getString("ID");
			String name = rs.getString("NOMBRE");
			Long costo = rs.getLong("COSTO");
			Long precio = rs.getLong("PRECIO");
			Long idres = rs.getLong("ID_RESTAURANTE");
			Long identr = rs.getLong("ID_PRODUCTOENTRADA");
			Long idacom = rs.getLong("ID_PRODUCTOACOMPA�AMIENTO");
			Long idplatof = rs.getLong("ID_PRODUCTOPLATOFUERTE");
			Long idpostre = rs.getLong("ID_PRODUCTOPOSTRE");
			Long idbeb = rs.getLong("ID_PRODUCTOBEBIDA");
			menus.add(new Menu(id, name, costo, precio, idres, identr, idacom, idplatof, idpostre, idbeb));
		}
		return menus;
	}


	/**
	 * Metodo que busca el video con el id que entra como parametro.
	 * 
	 * @param name
	 *            - Id de el video a buscar
	 * @return Video encontrado
	 * @throws SQLException
	 *             - Cualquier error que la base de datos arroje.
	 * @throws Exception
	 *             - Cualquier error que no corresponda a la base de datos
	 */
	public Menu buscarMenuPorId(String id) throws SQLException, Exception {
		Menu menu = null;

		String sql = "SELECT * FROM MENU WHERE ID = '" + id + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			String ide = rs.getString("ID");
			String name = rs.getString("NOMBRE");
			Long costo = rs.getLong("COSTO");
			Long precio = rs.getLong("PRECIO");
			Long idres = rs.getLong("ID_RESTAURANTE");
			Long identr = rs.getLong("ID_PRODUCTOENTRADA");
			Long idacom = rs.getLong("ID_PRODUCTOACOMPA�AMIENTO");
			Long idplatof = rs.getLong("ID_PRODUCTOPLATOFUERTE");
			Long idpostre = rs.getLong("ID_PRODUCTOPOSTRE");
			Long idbeb = rs.getLong("ID_PRODUCTOBEBIDA");
			menu = new Menu(ide, name, costo, precio, idres, identr, idacom, idplatof, idpostre, idbeb);
		}

		return menu;
	}

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * 
	 * @param video
	 *            - el video a agregar. video != null <b> post: </b> se ha
	 *            agregado el video a la base de datos en la transaction actual.
	 *            pendiente que el video master haga commit para que el video
	 *            baje a la base de datos.
	 * @throws SQLException
	 *             - Cualquier error que la base de datos arroje. No pudo
	 *             agregar el video a la base de datos
	 * @throws Exception
	 *             - Cualquier error que no corresponda a la base de datos
	 */
	public void addMenu(Menu menu) throws SQLException, Exception {

		String sql = "INSERT INTO MENU VALUES ('";
		sql += menu.getId() + "','";
		sql += menu.getNombre() + "',";
		sql += menu.getCosto() +  ",";
		sql += menu.getPrecio() +  ",";
		sql += menu.getIdrestaurante() +  ",";
		sql += menu.getIdentrada() +  ",";
		sql += menu.getIdacompa�amiento() +  ",";
		sql += menu.getIdplatoFuerte() +  ",";
		sql += menu.getIdpostre() +  ",";
		sql += menu.getIdbebida() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

}