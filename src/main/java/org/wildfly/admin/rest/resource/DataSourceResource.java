package org.wildfly.admin.rest.resource;

import static org.wildfly.admin.AdminUtil.admin;

import java.util.Properties;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.wildfly.admin.AdminException;
import org.wildfly.admin.AdminUtil;

@Path("/datasources")
@Produces({MediaType.APPLICATION_JSON })
@Api(value="/datasources")
public class DataSourceResource {
        
    @GET
    @Path("/getInstalledDataSource/{datasourceName}")
    @ApiOperation(value = "get datasource by datasourceName", notes = "get datasource by datasourceName as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "datasource not installed")})
    public String getInstalledDataSource(@ApiParam(value = "datasourceName", required = true) @PathParam("datasourceName") String datasourceName) throws AdminException {
        return admin().getInstalledDataSource(datasourceName).toJSONString(true);
    }
    
    @GET
    @Path("/getInstalledXADataSource/{datasourceName}")
    @ApiOperation(value = "get xa datasource by datasourceName", notes = "get xa datasource by datasourceName as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "datasource not installed")})
    public String getInstalledXADataSource(@ApiParam(value = "datasourceName", required = true) @PathParam("datasourceName") String datasourceName)throws AdminException{
        return admin().getInstalledXADataSource(datasourceName).toJSONString(true);
    }
    
    @GET
    @Path("/getInstalledDataSourceNames")
    @ApiOperation(value = "get datasource name", notes = "get datasource name as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getInstalledDataSourceNames() throws AdminException{
        return admin().getInstalledDataSourceNames().toJSONString(true);
    }
    
    @GET
    @Path("/getInstalledXADataSourceNames")
    @ApiOperation(value = "get xa datasource name", notes = "get xa datasource name as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getInstalledXADataSourceNames() throws AdminException{
        return admin().getInstalledXADataSourceNames().toJSONString(true);
    }
    
    @GET
    @Path("/getInstalledJDBCDrivers")
    @ApiOperation(value = "get installed driver list", notes = "get installed driver list as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getInstalledDataSourceDrivers() throws AdminException {
        return admin().getInstalledJDBCDrivers().toJSONString(true);
    }
    
    @GET
    @Path("/getInstalledJDBCDriver/{driverName}")
    @ApiOperation(value = "get installed jdbc driver by name", notes = "get installed jdbc driver by name as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getInstalledDataSourceDriver(@ApiParam(value = "driverName", required = true) @PathParam("driverName") String driverName) throws AdminException{
        return admin().getInstalledJDBCDriver(driverName).toJSONString(true);
    }
    
    @POST
    @Path("/createDataSource")
    @Consumes({MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Add a no-xa datasource")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String createDataSource(@ApiParam(value = "datasource name", required = true) String deploymentName, @ApiParam(value = "jdbc driver name", required = true)String driverName, @ApiParam(value = "comma separate properties, eg a=x,b=y", required = true)String properties) throws AdminException {
        Properties prop = AdminUtil.loadProperties(properties);
        return admin().createDataSource(deploymentName, driverName, prop).toJSONString(true);
    }
    
    @POST
    @Path("/createXADataSource")
    @Consumes({MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Add a xa datasource")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String createXADataSource(@ApiParam(value = "xa datasource name", required = true) String deploymentName, @ApiParam(value = "jdbc driver name", required = true)String driverName, @ApiParam(value = "comma separate properties, eg a=x,b=y", required = true)String properties) throws AdminException {
        Properties prop = AdminUtil.loadProperties(properties);
        return admin().createXADataSource(deploymentName, driverName, prop).toJSONString(true);
    }

}
