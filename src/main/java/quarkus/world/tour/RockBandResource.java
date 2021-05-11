package quarkus.world.tour;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("/rock")
public class RockBandResource {

    @GET
    public List<Band> listBands() {
        return Band.listAll();
    }

    @GET
    @Path("alive")
    public List<Band> listAlive() {
        return Band.listAlive();
    }

    @GET
    @Path("{id}")
    public Band listAlive(@PathParam("id") long id) {
        final Band byId = Band.findById(id);
        if (byId == null) {
            Response notFound = Response.status(Response.Status.NOT_FOUND)
                    .entity("no band found with id: " + id)
                    .build();
            throw new WebApplicationException(notFound);
        }
        return byId;
    }

    @POST
    @Transactional
    public Response listAlive(Band band) {
        band.persist();
        return Response.created(UriBuilder.fromUri("/rock/" + band.id).build())
                .build();
    }
}