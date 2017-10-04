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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los
 * requerimientos de la aplicaci贸n
 * 
 * @author Monitores 2017-20
 */
public class DAOTablaZonas {

	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo <b>post: </b> Crea la instancia del
	 * DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaZonas() {
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
	 * conexi贸n que entra como parametro.
	 * 
	 * @param con
	 *            - connection a la base de datos
	 */
	public void setConn(Connection con) {
		this.conn = con;
	}

	/**
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los videos
	 * de la base de datos <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * 
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException
	 *             - Cualquier error que la base de datos arroje.
	 * @throws Exception
	 *             - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Zona> darZonas() throws SQLException, Exception {
		ArrayList<Zona> zonas = new ArrayList<Zona>();

		String sql = "SELECT * FROM ZONA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");

			zonas.add(new Zona(nombre));
		}
		return zonas;
	}

	/**
	 * Metodo que busca la zona con el nombre que entra por par醡etro
	 * 
	 * @param name
	 *            - Id de el video a buscar
	 * @return Video encontrado
	 * @throws SQLException
	 *             - Cualquier error que la base de datos arroje.
	 * @throws Exception
	 *             - Cualquier error que no corresponda a la base de datos
	 */
	public Zona buscarZonaPorNombre(String nombre) throws SQLException, Exception {
		Zona zona = null;

		String sql = "SELECT * FROM ZONA WHERE NOMBRE =" + nombre;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {

			String name = rs.getString("NOMBRE");
			zona = new Zona(name);
		}
		return zona;
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
	public void addZona(Zona zona) throws SQLException, Exception {

		String sql = "INSERT INTO ZONA VALUES ('";
		sql += zona.getName() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que elimina el video que entra como parametro en la base de datos.
	 * 
	 * @param video
	 *            - el video a borrar. video != null <b> post: </b> se ha
	 *            borrado el video en la base de datos en la transaction actual.
	 *            pendiente que el video master haga commit para que los cambios
	 *            bajen a la base de datos.
	 * @throws SQLException
	 *             - Cualquier error que la base de datos arroje. No pudo
	 *             actualizar el video.
	 * @throws Exception
	 *             - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteZona(Zona zona) throws SQLException, Exception {

		String sql = "DELETE FROM ZONA";
		sql += " WHERE NOMBRE = '" + zona.getName() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public ArrayList<ConsultaZona> darInformacionZona(String nombrezona, String orden) throws SQLException, Exception {
		ArrayList<ConsultaZona> zonas = new ArrayList<ConsultaZona>();

		String sql = "SELECT * FROM (SELECT NOMBRE_ZONA, NUMERO_ESPACIOS, CAPACIDAD, NUM_ESPACIOS_DISCAPACITADOS, NUM_ESPACIOS_ABIERTOS, ID_PEDIDO AS ID_PEDIDO_SERVIDO, FECHAHORA_SERVIDO, NOMBRE_PRODUCTO AS NOMBRE_PRODUCTO_SERVIDO FROM (SELECT NOMBRE AS NOMBRE_ZONA, COUNT(ID_ESPACIO) AS NUMERO_ESPACIOS, SUM(CAPACIDAD) AS CAPACIDAD, SUM(ACCESO_ESPECIAL) AS NUM_ESPACIOS_DISCAPACITADOS, SUM(ABIERTO) AS NUM_ESPACIOS_ABIERTOS FROM (SELECT * FROM (SELECT * FROM ZONA Z WHERE Z.NOMBRE = '";
		sql += nombrezona
				+ "') NATURAL JOIN (SELECT ID AS ID_ESPACIO, CAPACIDAD, ACCESO_ESPECIAL, ABIERTO, NOMBRE_ZONA AS NOMBRE FROM ESPACIO)) GROUP BY NOMBRE) NATURAL JOIN (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT FECHAHORA AS FECHAHORA_SERVIDO, ID AS ID_PEDIDO FROM PEDIDO WHERE SERVIDO = 1) NATURAL JOIN (SELECT ID_PEDIDO, ID_PRODUCTO FROM PEDIDO_PRODUCTOS)) NATURAL JOIN (SELECT ID AS ID_PRODUCTO, NOMBRE AS NOMBRE_PRODUCTO, ID_RESTAURANTE  FROM PRODUCTO)) NATURAL JOIN (SELECT ID AS ID_RESTAURANTE, NOMBRE_ZONA  FROM RESTAURANTE))) NATURAL JOIN(SELECT DISTINCT NOMBRE AS NOMBRE_RESTAURANTE_EN_ZONA, NOMBRE_ZONA FROM RESTAURANTE )";
			if(orden!="" && orden!=null)	
		sql += "ORDER BY " + orden;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombrezo = rs.getString("NOMBRE_ZONA");
			Long numes = rs.getLong("NUMERO_ESPACIOS");
			Long capacidad = rs.getLong("CAPACIDAD");
			Long numespadis = rs.getLong("NUM_ESPACIOS_DISCAPACITADOS");
			Long numespabi = rs.getLong("NUM_ESPACIOS_ABIERTOS");
			Long idpedido = rs.getLong("ID_PEDIDO_SERVIDO");
			Date fecha= rs.getDate("FECHAHORA_SERVIDO");
			String nombrep = rs.getString("NOMBRE_PRODUCTO_SERVIDO");
			String nombreres = rs.getString("NOMBRE_RESTAURANTE_EN_ZONA");
			

			zonas.add(new ConsultaZona(nombrezo, numes, capacidad, numespadis, numespabi, idpedido, fecha, nombrep, nombreres));
		}
		return zonas;
	}

}
