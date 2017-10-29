package rest;

import java.util.List; 

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.ConsultaConsumo;
import vos.ConsultaZona;
import vos.Preferencia;
import vos.Producto;
import vos.Usuario;

@Path("clientes/{id: \\d+}")
public class ClienteResource {

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
     * Metodo que expone servicio REST usando DELETE que elimina el cliente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
     * @param cliente - cliente a aliminar. 
     * @return Json con el cliente que elimino o Json con el error que se produjo
     */
	@GET
	@Path("preferencias")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPreferencias(@PathParam("id") Long idCliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Preferencia> preferencias = null;
		try {
			preferencias = tm.darPreferencias(idCliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencias).build();
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el cliente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
     * @param cliente - cliente a aliminar. 
     * @return Json con el cliente que elimino o Json con el error que se produjo
     */
	@POST
	@Path("preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferencia(@PathParam("id") Long idCliente, Preferencia preferencia) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addPreferencia(idCliente, preferencia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el cliente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
     * @param cliente - cliente a aliminar. 
     * @return Json con el cliente que elimino o Json con el error que se produjo
     */
	@PUT
	@Path("preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePreferencia(@PathParam("id") Long idCliente, Preferencia preferencia) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Preferencia> preferencias = null;
		try {
			tm.updatePreferencia(idCliente, preferencia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencias).build();
	}

    /**
     * Metodo que expone servicio REST usando DELETE que elimina el cliente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
     * @param cliente - cliente a aliminar. 
     * @return Json con el cliente que elimino o Json con el error que se produjo
     */
	@GET
	@Path("productosMasOfrecidos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductosMasOfrecidos() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> productos = null;
		try {
			productos = tm.buscarProductosMasOfrecidos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
	}
	
    /**
     * Metodo que muestra la consulta de un cliente
     */
	@GET
	@Path("consumo")
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
}
