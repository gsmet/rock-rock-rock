package quarkus.world.tour;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.hibernate.search.mapper.orm.session.SearchSession;

@Liveness
@ApplicationScoped
public class ElasticsearchHealthCheck implements HealthCheck {
    
    @Inject
    SearchSession searchSession;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.builder().name("Our health check").down().build();
    }
}
