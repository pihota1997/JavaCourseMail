package Server;

import DTO.ProductPOJO;
import generated.tables.records.ProductsRecord;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;

import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Path("/")
public final class ResourcesREST {

    @GET
    @Produces({"text/html"})
    public Response root() {

        try {
            return Response.ok(RequestProcessingMethods.readWelcomePage()).build();
        } catch (FileNotFoundException e) {
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/info")
    public Response info() {
        try {
            return Response.ok(RequestProcessingMethods.readInfoPage()).build();
        } catch (FileNotFoundException e) {
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/db")
    @Produces({"application/json"})
    public List<ProductPOJO> getAll() {

        try {
            return new RequestProcessingMethods().getData(RequestProcessingMethods.createProductDao()
                    .all());

        } catch (SQLException e) {
            throw new ServerErrorException(500);
        }
    }

    @GET
    @Path("/db/select")
    @Produces({"application/json"})
    public List<ProductPOJO> getManufacturerProducts(@QueryParam("manufacturer") @Nullable String manufacturer) {

        try {
            if (manufacturer == null)
                throw new BadRequestException();

            Result<ProductsRecord> result = RequestProcessingMethods.createProductDao()
                    .getManufacturerProducts(manufacturer);

            if (result.isEmpty())
                throw new NotFoundException();

            return new RequestProcessingMethods().getData(result);

        } catch (SQLException e) {
            throw new ServerErrorException(500);
        }
    }

    @POST
    @Path("/db/create")
    public Response addToDB(@FormParam("name") @Nullable String name,
                            @FormParam("manufacturer") @Nullable String manufacturer,
                            @FormParam("quantity") int quantity) {

        if (name == null || manufacturer == null) {
            return Response.status(400).entity("This").build();
        }

        try {
            RequestProcessingMethods.createProductDao()
                    .create(RequestProcessingMethods.createSecondaryFunction()
                            .createProductsRecord(name, manufacturer, quantity));
            return Response.ok("Product added successfully").build();
        } catch (DataAccessException e) {
            return Response.status(500).entity("Product with this name exists in the database")
                    .build();
        } catch (SQLException e) {
            return Response.status(500).build();
        }
    }

    @POST
    @Path("/db/delete")
    public Response delete(@FormParam("name") @Nullable String name) {

        try {
            if (name == null) {
                return Response.status(400).build();
            }

            @Nullable ProductsRecord productsRecord = RequestProcessingMethods.createProductDao().get(name);
            if (productsRecord == null) {
                return Response.status(404).build();
            }

            RequestProcessingMethods.createProductDao().delete(name);
            return Response.ok("Product deleted successfully").build();

        } catch (SQLException e) {
            return Response.status(500).build();
        }
    }
}
