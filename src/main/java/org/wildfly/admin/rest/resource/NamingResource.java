package org.wildfly.admin.rest.resource;

import static org.wildfly.admin.AdminUtil.admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.wildfly.admin.AdminException;

@Path("/naming")
@Produces({MediaType.APPLICATION_JSON })
@Api(value="/naming")
public class NamingResource {
    
    @GET
    @Path("/jndi-view")
    @ApiOperation(value = "Checking JNDI tree view", notes = "Checking JNDI tree view as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getJNDIView() throws AdminException {
        return admin().getJNDIView().toJSONString(true);
    }

}
