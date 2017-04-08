package ticker.resources;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ad.dao.AdControllerDataAccess;
import com.ad.dao.AdControllerDataAsccessImpl;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;

@Path("/ad")
@Produces(MediaType.APPLICATION_JSON)
public class AdCreator {
	
	AdControllerDataAccess controller = new AdControllerDataAsccessImpl();

	@GET
    @Timed
    @Path("/get")
    public String get(@QueryParam("id") Optional<String> id){
		return controller.get(id.orElse(""));
    }
	
	@POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createDataInJSON(Ad ad) { 
        return controller.add(ad.content);
    }
	
	@POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean delete(Ad ad) { 
        return controller.delete(ad.id);
    }
	
	@POST
    @Path("/impression")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean impression(Ad ad) { 
        return controller.registerImpression(ad.id);
    }
	
	@POST
    @Path("/click")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean click(Ad ad) { 
        return controller.registerClick(ad.id);
    }
	
	@GET
    @Path("/smartget")
    @Consumes(MediaType.APPLICATION_JSON)
    public String smartGet() { 
        return controller.smartGet();
    }
	
	 public static class Ad {
	        @JsonProperty("content")
	        public String content;
	        
	        @JsonProperty("id")
	        public String id;
	  }
}
