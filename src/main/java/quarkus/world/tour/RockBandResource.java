package quarkus.world.tour;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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

    @POST
    @Transactional
    public Response create(Band band) {
        band.persist();
        return Response.created(UriBuilder.fromUri("/rock/" + band.id).build())
                .build();
    }
}