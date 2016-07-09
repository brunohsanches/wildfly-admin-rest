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

@Path("/")
@Produces({MediaType.APPLICATION_JSON })
@Api(value="/")
public class RootResource {
    
    @GET
    @Path("/reload")
    @ApiOperation(value = "Reloads the server", notes = "Reloads the server as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String reload() throws AdminException {
        return admin().reload().toJSONString(true);
    }
    
    @GET
    @Path("/resume")
    @ApiOperation(value = "Resumes normal operations in a suspended server", notes = "Resumes normal operations in a suspended server as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String resume() throws AdminException {
        return admin().resume().toJSONString(true);
    }
    
    @GET
    @Path("/suspend")
    @ApiOperation(value = "Suspends server operations gracefully", notes = "Suspends server operations gracefully as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String suspend() throws AdminException {
        return admin().suspend().toJSONString(true);
    }
    
    @GET
    @Path("/shutdown")
    @ApiOperation(value = "Shuts down the server", notes = "Shuts down the server as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String shutdown() throws AdminException {
        return admin().shutdown().toJSONString(true);
    }
    
    @GET
    @Path("/restart")
    @ApiOperation(value = "Restart the server", notes = "Restart the server as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String restart() throws AdminException {
        return admin().restart().toJSONString(true);
    }
    
    @GET
    @Path("/product-info")
    @ApiOperation(value = "Get product info report", notes = "Get product info report as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getProductInfo() throws AdminException {
        return admin().getProductInfo().toJSONString(true);
    }

}
