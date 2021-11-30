package Server;

import DAO.ProductDAO;
import DAO.ProductPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.dbConnection.JDBCCredentials;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Path("/")
public final class ResourcesREST {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    @GET
    @Produces({"text/html"})
    public Response root(){

        try {
            return Response.ok(new FileInputStream("src/main/resources/static/welcome.html"))
                    .build();
        } catch (FileNotFoundException e) {
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/info")
    public Response info(){
        try {
            return Response.ok(new FileInputStream("src/main/resources/static/info"))
                    .build();
        } catch (FileNotFoundException e) {
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/db")
    @Produces({"application/json"})
    public Response getAll() {

        try {
            return Response.ok(new ObjectMapper()
                    .writeValueAsString(SecondaryFunction.getInfo(new ProductDAO(CREDS.getConnection()).all())))
                    .build();
        } catch (JsonProcessingException |SQLException e) {
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/db/select")
    @Produces({"application/json"})
    public Response getManufacturerProducts(@QueryParam("manufacturer") String manufacturer) {

        List<ProductPOJO> pojos;
        try {
            pojos = SecondaryFunction.getInfo(new ProductDAO(CREDS.getConnection())
                    .getManufacturerProducts(manufacturer));
            if (pojos.isEmpty()) {
                return Response.status(404).build();
            }
            return Response.ok(new ObjectMapper().writeValueAsString(pojos)).build();

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
            new ProductDAO(CREDS.getConnection())
                    .create(new ProductsRecord(name, manufacturer, quantity));
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
            ProductsRecord productsRecord = new ProductDAO(CREDS.getConnection()).get(name);
            if (productsRecord.size() > 0){
                new ProductDAO(CREDS.getConnection()).delete(name);
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
