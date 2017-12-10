package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.ConsultaConsumo;
import vos.ConsultaPedido;
import vos.Restaurante;
import vos.Usuario;
import vos.Zona;

@Path("administradores")
public class AdministradorResource {


	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	

	/**
	 * Metodo que expone servicio REST usando GET que da todos los clientes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
	 * @return Json con todos los clientes de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Path("clientes")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> clientes;
		try {
			clientes = tm.darClientes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientes).build();
	}


    /**
     * Metodo que expone servicio REST usando POST que agrega el cliente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes/cliente
     * @param cliente - cliente a agregar
     * @return Json con el cliente que agrego o Json con el error que se produjo
     */
	@POST
	@Path("clientes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCliente(Usuario cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addUsuario(cliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();
	}
	
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el cliente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
     * @param cliente - cliente a aliminar. 
     * @return Json con el cliente que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCliente(Usuario cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteUsuario(cliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();
	}
	
    /**
     * Metodo que expone servicio REST usando POST que agrega el cliente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes/cliente
     * @param cliente - cliente a agregar
     * @return Json con el cliente que agrego o Json con el error que se produjo
     */
	@POST
	@Path("restaurantes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRestaurantes(Restaurante restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addRestaurante(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}
	
	  /**
     * Metodo que expone servicio REST usando POST que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Path("zonas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addZona(Zona zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addZona(zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}
	
	/**
	 * Metodo que expone servicio REST usando GET que da todos los clientes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
	 * @return Json con todos los clientes de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Path("zonas")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getZonas() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Zona> zonas;
		try {
			zonas = tm.darZonas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zonas).build();
	}

	@DELETE
	@Path("zonas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteZona(Zona zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteZona(zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}
	
    /**
     * Metodo que muestra la consulta de un cliente
     */
	@GET
	@Path("consumo/{id: \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConsumo(@PathParam("id") Long idCliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<ConsultaConsumo> consumo;
		try {
			consumo = tm.darConsumoCliente(idCliente);
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(consumo).build();
	}
	
    /**
     * Metodo que muestra la información de los restaurantes
     */
	@GET
	@Path("pedidos/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPedidos(@PathParam("tipo") String tipoCliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<ConsultaPedido> consumo;
		try {
			consumo = tm.darInformePedidos(tipoCliente);
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(consumo).build();
	}
	
	/**
     * Metodo que muestra la información de los restaurantes
     */
	@GET
	@Path("consumo/{res}/{fecha1}/{fecha2}/{filtro}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConsumo(@PathParam("res") String res,@PathParam("fecha1") String fecha1, @PathParam("fecha2") String fecha2, @PathParam("filtro") String filtro) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> consumo;
		try {
			consumo = tm.darConsumo(res,fecha1, fecha2, filtro);
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(consumo).build();
	}
	@GET
	@Path("noConsumo/{res}/{fecha1}/{fecha2}/{filtro}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNoConsumo(@PathParam("res") String res,@PathParam("fecha1") String fecha1, @PathParam("fecha2") String fecha2, @PathParam("filtro") String filtro) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> consumo;
		try {
			consumo = tm.darConsumo(res,fecha1, fecha2, filtro);
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(consumo).build();
	}
	
	@GET
	@Path("buenosClientes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBuenosCLientes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> consumo;
		try {
			consumo = tm.darBuenosClientes();
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(consumo).build();
	}
	
}
	


