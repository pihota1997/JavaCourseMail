package Server;

import com.fasterxml.jackson.core.JsonProcessingException;
import generated.tables.records.ProductsRecord;
import org.jooq.exception.DataAccessException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Path("/")
public final class ResourcesREST {

    @GET
    @Produces({"text/html"})
    public Response root(){

        try {
            return Response.ok(SecondaryFunction.readWelcomePage()).build();
        } catch (FileNotFoundException e) {
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/info")
    public Response info(){
        try {
            return Response.ok(SecondaryFunction.readInfoPage()).build();
        } catch (FileNotFoundException e) {
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/db")
    @Produces({"application/json"})
    public Response getAll() {

        try {
            return Response.ok(SecondaryFunction.createSecondaryFunction()
                    .convertToJSON(SecondaryFunction.createProductDao().all())).build();
        } catch (JsonProcessingException |SQLException e) {
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/db/select")
    @Produces({"application/json"})
    public Response getManufacturerProducts(@QueryParam("manufacturer") String manufacturer) {

        try {
            String json = SecondaryFunction.createSecondaryFunction().convertToJSON(SecondaryFunction.createProductDao()
                    .getManufacturerProducts(manufacturer));

            if(json.length() < 3)
                return Response.status(404).build();

            return Response.ok(json).build();

        } catch (SQLException | JsonProcessingException e) {
            return Response.status(500).build();
        }
    }

    @POST
    @Path("/db/create")
    public Response addToDB(@FormParam("name") String name,
                            @FormParam("manufacturer") String manufacturer,
                            @FormParam("quantity") int quantity){

        if (name == null || manufacturer == null) {
            return Response.status(400).build();
        }

        try {
            SecondaryFunction.createProductDao()
                    .create(SecondaryFunction.createSecondaryFunction()
                            .createProductsRecord(name, manufacturer, quantity));
            return Response.ok("Product added successfully").build();
        } catch (DataAccessException e) {
            return Response.status(500).entity("Product with this name exists in the database")
                    .build();
        } catch (SQLException e){
            return Response.status(500).build();
        }
    }

    @POST
    @Path("/db/delete")
    public Response delete(@FormParam("name") String name){

        try {
            ProductsRecord productsRecord = SecondaryFunction.createProductDao().get(name);
            if (productsRecord.size() > 0){
                SecondaryFunction.createProductDao().delete(name);
                return Response.ok("Product deleted successfully").build();
            }
            return Response.status(404).build();
        } catch (NullPointerException e) {
            return Response.status(404).build();
        } catch (SQLException e){
            return Response.status(500).build();
        }
    }
}
