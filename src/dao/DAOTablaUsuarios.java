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
import java.util.Date;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaci贸n
 * @author Monitores 2017-20
 */
public class DAOTablaUsuarios {


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
	public DAOTablaUsuarios() {
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
	public ArrayList<Usuario> darUsuarios() throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String correo = rs.getString("CORREOELECTRONICO");
			String rol = rs.getString("ROL");
			String identificacion = rs.getString("IDENTIFICACION");
			String contrase馻 = rs.getString("PASS");
			usuarios.add(new Usuario(rol, name, identificacion, correo, contrase馻));
		}
		return usuarios;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Usuario> buscarUsuariosPorName(String pName) throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO WHERE NOMBRE ='" + pName + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String correo = rs.getString("CORREOELECTRONICO");
			String rol = rs.getString("ROL");
			String identificacion = rs.getString("IDENTIFICACION");
			String contrase馻 = rs.getString("PASS");
			usuarios.add(new Usuario(rol, name, identificacion, correo, contrase馻));
		}
		return usuarios;
	}
	
	/**
	 * Metodo que busca el video con el id que entra como parametro.
	 * @param name - Id de el video a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Usuario buscarUsuarioPorId(String pId) throws SQLException, Exception 
	{
		Usuario usuario = null;

		String sql = "SELECT * FROM USUARIO WHERE IDENTIFICACION = '" + pId + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String name = rs.getString("NOMBRE");
			String correo = rs.getString("CORREOELECTRONICO");
			String rol = rs.getString("ROL");
			String identificacion = rs.getString("IDENTIFICACION"); 
			String contrase馻 = rs.getString("PASS");
			usuario = new Usuario(rol, name, identificacion, correo, contrase馻);
		}

		return usuario;
	}

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "INSERT INTO USUARIO VALUES ('";
		sql += usuario.getIdentificacion() + "','";
		sql += usuario.getName() + "','";		
		sql += usuario.getCorreoEletronico() + "','";
		sql += usuario.getRol() + "','";
		sql += usuario.getContrase馻() + "')";
		
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
	public void updateUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "UPDATE USUARIO SET ";
		sql += "NOMBRE='" + usuario.getName() + "',";
		sql += "CORREOELECTRONICO='" + usuario.getCorreoEletronico() + "'";
		sql += " WHERE IDENTIFICACION = " + usuario.getIdentificacion() + ")";


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
	public void deleteUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "DELETE FROM USUARIO";
		sql += " WHERE ID = '" + usuario.getIdentificacion() + "'";

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
	public ArrayList<Usuario> darClientes() throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO WHERE ROL = 'Cliente'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String correo = rs.getString("CORREOELECTRONICO");
			String rol = rs.getString("ROL");
			String identificacion = rs.getString("IDENTIFICACION");
			String contrase馻 = rs.getString("PASS");
			usuarios.add(new Usuario(rol, name, identificacion, correo, contrase馻));
		}
		return usuarios;
	}
	
	public ArrayList<ConsultaCliente> darInformacionCliente(Long idCliente) throws SQLException, Exception {
		ArrayList<ConsultaCliente> clients = new ArrayList<ConsultaCliente>();

		String sql = "SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT  ID_CLIENTE, DIA, COUNT(FECHAHORA) AS COMPRASENDIA FROM (select to_char(FECHAHORA, 'Dy') AS DIA, FECHAHORA, ID_CLIENTE, ID, SERVIDO FROM (SELECT * FROM PEDIDO WHERE ID_CLIENTE = ";
		sql+= idCliente + ")) GROUP BY DIA, ID_CLIENTE) NATURAL JOIN (SELECT ID AS ID_PEDIDO, FECHAHORA AS FECHAHORAPEDIDO, SERVIDO AS PEDIDOSERVIDO, ID_CLIENTE FROM PEDIDO)) NATURAL JOIN (SELECT TIPO AS TIPOPREF, VALOR AS VALORPREF, ID_CLIENTE FROM PREFERENCIA)) NATURAL JOIN (SELECT IDENTIFICACION AS ID_CLIENTE, NOMBRE AS NOMBRE_CLIENTE, CORREOELECTRONICO AS CORREO, ROL AS ROL FROM USUARIO WHERE ROL = 'Cliente')";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idcliente = rs.getLong("ID_CLIENTE");
			String dia = rs.getString("DIA");
			Long comprasd = rs.getLong("COMPRASENDIA");
			Long idpedi = rs.getLong("ID_PEDIDO");
			Date fechaped= rs.getDate("FECHAHORAPEDIDO");
			Long pedidoservido = rs.getLong("PEDIDOSERVIDO");
			String tipopre = rs.getString("TIPOPREF");
			String valorpre = rs.getString("VALORPREF");
			String nombrecl = rs.getString("NOMBRE_CLIENTE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			

			clients.add(new ConsultaCliente(idcliente.toString(), dia, comprasd, idpedi, fechaped, pedidoservido, tipopre, valorpre, nombrecl, correo, rol));
		}
		return clients;
	}
	
	public ArrayList<ConsultaConsumo> darInformacionConsumo(Long idCliente) throws SQLException, Exception {
		ArrayList<ConsultaConsumo> consumos = new ArrayList<ConsultaConsumo>();

		String sql = "SELECT ID_PEDIDO, NOMBRE_MENU, NOMBRE_PRODUCTO FROM (SELECT * FROM (SELECT ID AS ID_PEDIDO, ID_MENU, ID_PRODUCTO FROM ((SELECT* FROM (SELECT ID FROM PEDIDO WHERE ID_CLIENTE = ";
		sql += idCliente
				+ " AND SERVIDO = 1) NATURAL JOIN (SELECT ID_PEDIDO AS ID, ID_MENU FROM PEDIDO_MENUS))) NATURAL LEFT OUTER JOIN (SELECT ID_PEDIDO AS ID, ID_PRODUCTO FROM PEDIDO_PRODUCTOS)) NATURAL LEFT OUTER JOIN  (SELECT ID AS ID_MENU, NOMBRE AS NOMBRE_MENU FROM MENU)) NATURAL LEFT OUTER JOIN (SELECT ID AS ID_PRODUCTO, NOMBRE AS NOMBRE_PRODUCTO FROM PRODUCTO)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idPedido = rs.getLong("ID_PEDIDO");
			String nombremenu = rs.getString("NOMBRE_MENU");
			String nombreproducto = rs.getString("NOMBRE_PRODUCTO");
			

			consumos.add(new ConsultaConsumo(idPedido, nombremenu, nombreproducto));
		}
		return consumos;
	}

}
