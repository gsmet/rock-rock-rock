package quarkus.world.tour;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.hibernate.search.mapper.orm.session.SearchSession;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/rock")
public class RockBandResource {

    @Inject
    SearchSession searchSession;

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
    public Band findById(@PathParam("id") long id) {
        final Band byId = Band.findById(id);
        if (byId == null) {
            Response notFound = Response.status(Response.Status.NOT_FOUND)
                    .entity("no band found with id: " + id)
                    .build();
            throw new WebApplicationException(notFound);
        }
        return byId;
    }
    
    @GET
    @Path("search")
    @Transactional
    public List<Band> search(@QueryParam String pattern) {
        return searchSession.search(Band.class)
                .selectEntity()
                .where(f -> {
                    if (pattern != null && !pattern.isBlank()) {
                        return f.simpleQueryString().field("name").matching(pattern);
                    } else {
                        return f.matchAll();
                    }
                })
                .sort(f -> f.field("name_sort"))
                .fetchAllHits();
    }

    @POST
    @Transactional
    public Response create(Band band) {
        band.persist();
        return Response.created(UriBuilder.fromUri("/rock/" + band.id).build())
                .build();
    }
}