package rest;

import java.util.ArrayList;
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
import vos.*;

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
	


	
	@GET
	@Path("{id: \\d+}/productos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProductos(@PathParam("id")Long id) {
		
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> productos;
		try {
			productos = tm.buscarProductosPorRestaurante(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
	}
	
	@POST
	@Path("{id: \\d+}/productos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProducto(@PathParam("id")Long id, Producto producto) {
		
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Producto produc;
		try {
			produc = tm.addProductoPorRestaurante(id, producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(produc).build();
	}
	
	@GET
	@Path("{id: \\d+}/productos/{idProducto: \\d+}/ingredientes")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIngredientes(@PathParam("id")Long id, @PathParam("idProducto")Long idProducto) {
		
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Ingrediente> ingredientes = null;

		try {
			ingredientes = tm.buscarIngredientesPorProductos(id,idProducto);
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingredientes).build();
	}
	
	@POST
	@Path("{id: \\d+}/productos/{idProducto: \\d+}/ingredientes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addIngrediente(@PathParam("id")Long id, @PathParam("idProducto")Long idProducto, Ingrediente ingrediente) {
		
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Ingrediente> ingredientes = null;

		try {
			ingrediente = tm.addIngredientePorProducto(id, idProducto, ingrediente);
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingrediente).build();
	}
	

	@POST
    @Path("menus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenu(Menu menu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addMenu(menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}
	

	@GET
	@Path("menus")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMenus() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Menu> menus;
		System.out.println("imprime");
		try {
			menus = tm.darMenus();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menus).build();
	}
	

	
	@POST
	@Path("servido/{id: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response marcarServido(@PathParam("id")Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		String res;
		try {
			res = tm.marcarServido(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(res).build();
	}
	
	@POST
	@Path("productos/{id1: \\d+}/equivalencia/{id2: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response ingresarEquivalenciaProducto(@PathParam("id1")Long id1, @PathParam("id2")Long id2) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.agregarEquivalenciaProducto(id1, id2);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}

	@POST
	@Path("ingredientes/{id1: \\d+}/equivalencia/{id2: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response ingresarEquivalenciaIngrediente(@PathParam("id1")Long id1, @PathParam("id2")Long id2) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.agregarEquivalenciaIngredientes(id1, id2);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
	
	@POST
	@Path("{id: \\d+}/surtir")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response surtirRestaurante(@PathParam("id")Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.surtirRestaurante(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}

	@POST
	@Path("servicio/{id: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response marcarMesaServida(@PathParam("id")Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.marcarServidosMesa(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
	
	@POST
	@Path("cancelar/{id: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response cancelarPedido(@PathParam("id")Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.cancelarPedido(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
	
    /**
     * Metodo que muestra la información de los restaurantes
     */
	@GET
	@Path("/{id}/pedidos/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPedidos(@PathParam("tipo") String tipoCliente, @PathParam("id") Long id) {
		System.out.println(id);
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<ConsultaPedido> consumo;
		List<ConsultaPedido> consumoEspecifico = new ArrayList<ConsultaPedido>();
		try {
			consumo = tm.darInformePedidos(tipoCliente);
			for (ConsultaPedido consultaPedido : consumo) {
				System.out.println(consultaPedido.getIdRestaurante());
				if(consultaPedido.getIdRestaurante().equals(id)){
					consumoEspecifico.add(consultaPedido);
				}
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(consumoEspecifico).build();
	}
}
	
	

