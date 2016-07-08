package org.wildfly.admin.rest.resource;

import static org.wildfly.admin.AdminUtil.admin;
import static org.wildfly.admin.AdminUtil.loadProperties;

import java.util.Properties;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.wildfly.admin.AdminException;
import org.wildfly.admin.AdminUtil;

@Path("/datasources")
@Produces({MediaType.APPLICATION_JSON })
@Api(value="/datasources")
public class DataSourceResource {

    @GET
    @Path("/get-installed-driver/{driverName}")
    @ApiOperation(value = "get installed jdbc driver by name", notes = "get installed jdbc driver by name as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getInstalledDataSourceDriver(@ApiParam(value = "driverName", required = true) @PathParam("driverName") String driverName) throws AdminException{
        return admin().getInstalledJDBCDriver(driverName).toJSONString(true);
    }
    
    @GET
    @Path("/installed-datasource-names")
    @ApiOperation(value = "get datasource name", notes = "get datasource name as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getInstalledDataSourceNames() throws AdminException{
        return admin().getInstalledDataSourceNames().toJSONString(true);
    }
    
    @GET
    @Path("/installed-xa-datasource-names")
    @ApiOperation(value = "get xa datasource name", notes = "get xa datasource name as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getInstalledXADataSourceNames() throws AdminException{
        return admin().getInstalledXADataSourceNames().toJSONString(true);
    }
    
    @GET
    @Path("/installed-jdbc-driver-names")
    @ApiOperation(value = "get jdbc driver names", notes = "get jdbc driver names as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getInstalledJDBCDriverNames() throws AdminException {
        return admin().getInstalledJDBCDriverNames().toJSONString(true);
    }
    
    @GET
    @Path("/installed-drivers-list")
    @ApiOperation(value = "get installed driver list", notes = "get installed driver list as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getInstalledDataSourceDrivers() throws AdminException {
        return admin().getInstalledJDBCDrivers().toJSONString(true);
    }
        
    @POST
    @Path("/data-source/{deploymentName}/add")
    @Consumes({MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Add a data source")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String addDataSource(@ApiParam(value = "datasource name", required = true) @PathParam("deploymentName") String deploymentName, @ApiParam(value = "jdbc driver name", required = true) @QueryParam("driverName") String driverName, @ApiParam(value = "comma separate properties, eg: 'connection-url={url},user-name=sa,password=sa'", required = true)@QueryParam("properties") String properties) throws AdminException {
        Properties prop = AdminUtil.loadProperties(properties);
        return admin().addDataSource(deploymentName, driverName, prop).toJSONString(true);
    }
    
    @POST
    @Path("/xa-data-source/add")
    @Consumes({MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Add a xa datasource")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String addXADataSource(@ApiParam(value = "datasource name", required = true) @PathParam("deploymentName") String deploymentName, @ApiParam(value = "jdbc driver name", required = true) @QueryParam("driverName") String driverName, @ApiParam(value = "comma separate properties, eg: 'User=sa,Password=sa'", required = true)@QueryParam("properties") String properties) throws AdminException {
        Properties prop = AdminUtil.loadProperties(properties);
        return admin().addXADataSource(deploymentName, driverName, prop).toJSONString(true);
    }
    
    @POST
    @Path("/jdbc-driver/{driverName}/add")
    @Consumes({MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Add a jdbc driver")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String addJDBCDriver(@ApiParam(value = "driverName", required = true) @PathParam("driverName") String driverName, @ApiParam(value = "An existed driver nodule name", required = true) @QueryParam("driverModuleName") String driverModuleName, @ApiParam(value = "comma separate properties, eg 'driver-class-name=org.h2.Driver,driver-xa-datasource-class-name=org.h2.jdbcx.JdbcDataSource'", required = false) @QueryParam("properties") String properties) throws AdminException {
        return admin().addJDBCDriver(driverName, driverModuleName, loadProperties(properties)).toJSONString(true);
    }
    
    @DELETE
    @Path("/jdbc-driver/{driverName}/remove")
    @ApiOperation(value = "Remove the jdbc driver", notes = "Remove the jdbc driver")
    @ApiResponses({@ApiResponse(code = 404, message = "jdbc driver not found")})
    public String removeJDBCDriver(@ApiParam(value = "driverName", required = true) @PathParam("driverName") String driverName) throws AdminException{
        return admin().removeJDBCDriver(driverName).toJSONString(true);
    }
    
    @GET
    @Path("/data-source/{datasourceName}/get")
    @ApiOperation(value = "get datasource by datasourceName", notes = "get datasource by datasourceName as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "datasource not installed")})
    public String getInstalledDataSource(@ApiParam(value = "datasourceName", required = true) @PathParam("datasourceName") String datasourceName) throws AdminException {
        return admin().getInstalledDataSource(datasourceName).toJSONString(true);
    }
    
    @DELETE
    @Path("/data-source/{dsName}/remove")
    @ApiOperation(value = "Remove the data source", notes = "Remove the data source")
    @ApiResponses({@ApiResponse(code = 404, message = "data source not found")})
    public String removeDataSource(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException{
        return admin().removeDataSource(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/data-source/{dsName}/test-connection-in-pool")
    @ApiOperation(value = "Test if a connection can be obtained", notes = "Test if a connection can be obtained as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String testConnectionInPool(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException{
        return admin().testConnectionInPool(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/data-source/{dsName}/test-connection-in-pool-auth")
    @ApiOperation(value = "Test if a connection can be obtained", notes = "Test if a connection can be obtained as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String testConnectionInPool(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName, @ApiParam(value = "username", required = true) @QueryParam("username") String username, @ApiParam(value = "password", required = true) @QueryParam("password") String password) throws AdminException{
        return admin().testConnectionInPool(dsName, username, password).toJSONString(true);
    }
    
    @GET
    @Path("/data-source/{dsName}/dump-queued-threads-in-pool")
    @ApiOperation(value = "Dump queued threads in the pool", notes = "Dump queued threads in the pool as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String dumpQueuedThreadsInPool(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException {
        return admin().dumpQueuedThreadsInPool(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/data-source/{dsName}/flush-all-connection-in-pool")
    @ApiOperation(value = "Flushes all connections in the pool", notes = "Flushes all connections in the pool as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String flushAllConnectionInPool(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException {
        return admin().flushAllConnectionInPool(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/data-source/{dsName}/flush-gracefully-connection-in-pool")
    @ApiOperation(value = "Flushes all connections gracefully in the pool", notes = "Flushes all connections gracefully in the pool as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String flushGracefullyConnectionInPool(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException{
        return admin().flushGracefullyConnectionInPool(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/data-source/{dsName}/flush-idle-connection-in-pool")
    @ApiOperation(value = "Flushes all idle connections in the pool", notes = "Flushes all idle connections in the pool as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String flushIdleConnectionInPool(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException {
        return admin().flushIdleConnectionInPool(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/data-source/{dsName}/flush-invalid-connection-in-pool")
    @ApiOperation(value = "Flushes all invalid connections in the pool", notes = "Flushes all invalid connections in the pool as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String flushInvalidConnectionInPool(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException {
        return admin().flushInvalidConnectionInPool(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/xa-data-source/{datasourceName}/get")
    @ApiOperation(value = "get xa datasource by datasourceName", notes = "get xa datasource by datasourceName as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "datasource not installed")})
    public String getInstalledXADataSource(@ApiParam(value = "datasourceName", required = true) @PathParam("datasourceName") String datasourceName)throws AdminException{
        return admin().getInstalledXADataSource(datasourceName).toJSONString(true);
    }
    
    @DELETE
    @Path("/xa-data-source/{dsName}/remove")
    @ApiOperation(value = "Remove the XA data source", notes = "Remove the XA data source")
    @ApiResponses({@ApiResponse(code = 404, message = "XA data source not found")})
    public String removeXADataSource(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException{
        return admin().removeXADataSource(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/xa-data-source/{dsName}/test-connection-in-pool")
    @ApiOperation(value = "Test if a connection can be obtained", notes = "Test if a connection can be obtained as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String testConnectionInPoolXA(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException{
        return admin().testConnectionInPoolXA(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/xa-data-source/{dsName}/test-connection-in-pool-auth")
    @ApiOperation(value = "Test if a connection can be obtained", notes = "Test if a connection can be obtained as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String testConnectionInPoolXA(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName, @ApiParam(value = "username", required = true) @QueryParam("username") String username, @ApiParam(value = "password", required = true) @QueryParam("password") String password) throws AdminException{
        return admin().testConnectionInPoolXA(dsName, username, password).toJSONString(true);
    }
    
    @GET
    @Path("/xa-data-source/{dsName}/dump-queued-threads-in-pool")
    @ApiOperation(value = "Dump queued threads in the pool", notes = "Dump queued threads in the pool as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String dumpQueuedThreadsInPoolXA(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException {
        return admin().dumpQueuedThreadsInPoolXA(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/xa-data-source/{dsName}/flush-all-connection-in-pool")
    @ApiOperation(value = "Flushes all connections in the pool", notes = "Flushes all connections in the pool as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String flushAllConnectionInPoolXA(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException {
        return admin().flushAllConnectionInPoolXA(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/xa-data-source/{dsName}/flush-gracefully-connection-in-pool")
    @ApiOperation(value = "Flushes all connections gracefully in the pool", notes = "Flushes all connections gracefully in the pool as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String flushGracefullyConnectionInPoolXA(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException{
        return admin().flushGracefullyConnectionInPoolXA(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/xa-data-source/{dsName}/flush-idle-connection-in-pool")
    @ApiOperation(value = "Flushes all idle connections in the pool", notes = "Flushes all idle connections in the pool as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String flushIdleConnectionInPoolXA(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException {
        return admin().flushIdleConnectionInPoolXA(dsName).toJSONString(true);
    }
    
    @GET
    @Path("/xa-data-source/{dsName}/flush-invalid-connection-in-pool")
    @ApiOperation(value = "Flushes all invalid connections in the pool", notes = "Flushes all invalid connections in the pool as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String flushInvalidConnectionInPoolXA(@ApiParam(value = "dsName", required = true) @PathParam("dsName") String dsName) throws AdminException {
        return admin().flushInvalidConnectionInPoolXA(dsName).toJSONString(true);
    }

}
