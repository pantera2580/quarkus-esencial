package org.mec;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;
import org.mec.config.ProductInventoryConfig;
import org.mec.model.ProductInventory;
import org.mec.model.ValidationGroups;
import org.mec.services.ProductService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;

@Path("/products")
@ApplicationScoped //recomendable este scope para trabajar con base de datos
public class InventoryResource {
    private static final Logger LOGGER = Logger.getLogger(InventoryResource.class);

    @Inject
    ProductService productService;
    @Inject
    ProductInventoryConfig productInventoryConfig;

    /**
     * Toma la configuracion del application.properties si son muchas configs se vuelve denso hacerlo asi
     * @ConfigProperty(name = "org.mec.greeting-message")
     * String message;
     *
     * luego usar la variable message donde corresponda
    */


    @GET
    @Path(("/hello"))
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return productInventoryConfig.message();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{sku}")
    public Response inventory(@PathParam("sku") String sku) {
        LOGGER.debugf("get by sku %s", sku);
        ProductInventory productInventory = ProductInventory.findBySku(sku);

        if (productInventory == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(productInventory).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ProductInventory> listInventory() {
        LOGGER.debug("Product inventory list");
        return ProductInventory.listAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createProduct(@Context UriInfo uriInfo, @Valid @ConvertGroup(to = ValidationGroups.Post.class) ProductInventory productInventory) {
        LOGGER.debugf("create %s", productInventory);
        productInventory.persist();
        UriBuilder path = uriInfo.getAbsolutePathBuilder().path(productInventory.sku);
        //recomendable devolver el path absoluto
        return Response.created(path.build()).build();
    }

    @PUT
    @Path("/{sku}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("sku") String sku, @ConvertGroup(to = ValidationGroups.Put.class)  @Valid ProductInventory productInventory) {
        LOGGER.debugf("update %s", productInventory);
        ProductInventory update = ProductInventory.findBySku(sku);
        if(update == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        update.name = productInventory.name;
        update.category = productInventory.category;
        update.persist();
        //devolver un notfound si el elemento no existe o devolver la entidad modificada
        return Response.accepted(productInventory).build();
    }
    @PATCH
    @Path("/{sku}")
    @Transactional
    public Response updateStock(@PathParam("sku") String sku, @QueryParam("stock") Integer stock) {
        LOGGER.debugf("get by sku %s", sku);
        int currentStock = ProductInventory.findCurrentStock(sku);
        ProductInventory.update("unitsAvailable = ?1 where sku=?2", currentStock + stock, sku);
        return Response.accepted(URI.create(sku)).build();
    }

    @DELETE
    @Path("/{sku}")
    @Transactional
    @Operation(summary = "Update the stock of a product by sku", description = "Longer description that explains all.")
    public Response delete(@PathParam("sku") String sku) {
        LOGGER.debugf("delete by sku %s", sku);
        ProductInventory.delete("sku", sku);
        return Response.accepted().build();
    }
}