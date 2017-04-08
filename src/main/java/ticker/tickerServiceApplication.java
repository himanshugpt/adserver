package ticker;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ticker.health.HealthChecker;
import ticker.resources.AdCreator;
import ticker.resources.TickerResource;

public class tickerServiceApplication extends Application<tickerServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new tickerServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "tickerService";
    }

    @Override
    public void initialize(final Bootstrap<tickerServiceConfiguration> bootstrap) {
    }

    @Override
    public void run(final tickerServiceConfiguration configuration,
                    final Environment environment) {
    	final TickerResource resource = new TickerResource(configuration.getTemplate(), 
    			configuration.getDefaultName());
    	
    	final AdCreator ad = new AdCreator();
    	
        final HealthChecker healthCheck =
                new HealthChecker(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);    
    	environment.jersey().register(resource);
    	environment.jersey().register(ad);
    }

}
