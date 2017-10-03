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
import vos.Ingrediente;
import vos.Producto;
import vos.Restaurante;
import vos.Usuario;

@Path("restaurantes")
public class RestauranteResource {

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
	 * Metodo que expone servicio REST usando GET que da todos los usuarios de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios
	 * @return Json con todos los usuarios de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurantes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Restaurante> restaurantes;
		System.out.println("imprime");
		try {
			restaurantes = tm.darRestaurantes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurantes).build();
	}

	 /**
     * Metodo que expone servicio REST usando GET que busca el usuario con el id que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios/<<id>>" para la busqueda"
     * @param name - Nombre del usuario a buscar que entra en la URL como parametro 
     * @return Json con el/los usuarios encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getRestaurante( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Restaurante v = tm.buscarRestaurantePorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

    /**
     * Metodo que expone servicio REST usando GET que busca el usuario con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios/nombre/nombre?nombre=<<nombre>>" para la busqueda"
     * @param name - Nombre del usuario a buscar que entra en la URL como parametro 
     * @return Json con el/los usuarios encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getRestauranteName( @PathParam("nombre") String name) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Restaurante restaurante;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre del usuario no valido");
			restaurante = tm.buscarRestaurantesPorName(name).get(0);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}


    /**
     * Metodo que expone servicio REST usando POST que agrega el usuario que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios/usuario
     * @param usuario - usuario a agregar
     * @return Json con el usuario que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRestaurante(Restaurante usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addRestaurante(usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}
	
    /**
     * Metodo que expone servicio REST usando POST que agrega los usuarios que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios/varios
     * @param usuarios - usuarios a agregar. 
     * @return Json con el usuario que agrego o Json con el error que se produjo
     */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRestaurante(List<Restaurante> usuarios) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addRestaurantes(usuarios);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}
	
    /**
     * Metodo que expone servicio REST usando PUT que actualiza el usuario que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios
     * @param usuario - usuario a actualizar. 
     * @return Json con el usuario que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRestaurante(Restaurante usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateRestaurante(usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el usuario que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/usuarios
     * @param usuario - usuario a aliminar. 
     * @return Json con el usuario que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRestaurante(Restaurante usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteRestaurante(usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}
	
	@GET
	@Path("{id: \\d+}/productos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProductos(@PathParam("id")Long id) {
		
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> productos;
		Restaurante restaurante;
		try {
			restaurante = tm.buscarRestaurantePorId(id);
			productos = tm.darProductosPorRestaurante(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
	}
	
	@GET
	@Path("{id: \\d+}/productos/{idProducto: \\d+}/ingredientes")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIngredientes(@PathParam("id")Long id, @PathParam("idProducto")Long idProducto) {
		
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Ingrediente> ingredientes = null;
		List<Producto> productos;
		Restaurante restaurante;
		try {
			restaurante = tm.buscarRestaurantePorId(id);
			productos = tm.darProductosPorRestaurante(restaurante);
			for(Producto prod : productos) {
				if(prod.getId()==idProducto) ingredientes = tm.buscarIngredientesPorProductos(prod);
				break;
			}
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingredientes).build();
	}

	private boolean checkUsuario(String id, String contraseña){
		
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Usuario usu;
		boolean respuesta = false;
		try {
			usu = tm.buscarUsuarioPorId(id);
			if(usu.getContraseña().equals(contraseña))respuesta = true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respuesta;
	}
}
