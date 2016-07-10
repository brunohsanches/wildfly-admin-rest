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

@Path("/core-service")
@Produces({MediaType.APPLICATION_JSON })
@Api(value="/core-service")
public class CoreSeriveResource {

    @GET
    @Path("/platform-mbean/operating-system/version")
    @ApiOperation(value = "Getting OS version", notes = "Getting OS version as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getOSVersion() throws AdminException {
        return admin().getOSVersion().toJSONString(true);
    }
    
    @GET
    @Path("/platform-mbean/runtime/spec-version")
    @ApiOperation(value = "Getting JVM version", notes = "Getting JVM version as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getJVMVersion() throws AdminException {
        return admin().getJVMVersion().toJSONString(true);
    }
    
    @GET
    @Path("/platform-mbean/runtime/input-arguments")
    @ApiOperation(value = "Checking JVM options", notes = "Checking JVM options as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getJVMOptions() throws AdminException {
        return admin().getJVMOptions().toJSONString(true);
    }
    
    @GET
    @Path("/platform-mbean/memory/heap-memory-usage")
    @ApiOperation(value = "Checking JVM Heap memory usage", notes = "Checking JVM Heap memory usage as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getJVMMemoryHeap() throws AdminException {
        return admin().getJVMMemoryHeap().toJSONString(true);
    }
    
    @GET
    @Path("/platform-mbean/memory/non-heap-memory-usage")
    @ApiOperation(value = "Checking JVM Non-Heap memory usage", notes = "Checking JVM Non-Heap memory usage as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getJVMMemoryNonHeap() throws AdminException {
        return admin().getJVMMemoryNonHeap().toJSONString(true);
    }
    
    @GET
    @Path("/platform-mbean/memory-pool/Metaspace")
    @ApiOperation(value = "Getting JVM Metaspace", notes = "Checking JVM Getting JVM Metaspace as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getJVMMemoryMetaspace() throws AdminException {
        return admin().getJVMMemoryMetaspace().toJSONString(true);
    }
    
    @GET
    @Path("/platform-mbean/memory-pool/PS_Eden_Space")
    @ApiOperation(value = "Getting JVM Eden", notes = "Getting JVM Eden as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getJVMMemoryEden() throws AdminException {
        return admin().getJVMMemoryEden().toJSONString(true);
    }
    
    @GET
    @Path("/platform-mbean/memory-pool/PS_Old_Gen")
    @ApiOperation(value = "Getting JVM OldGen", notes = "Getting JVM OldGen as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getJVMMemoryOldGen() throws AdminException {
        return admin().getJVMMemoryOldGen().toJSONString(true);
    }
    
    @GET
    @Path("/platform-mbean/memory-pool/PS_Survivor_Space")
    @ApiOperation(value = "Getting JVM Survivor", notes = "Getting JVM Survivor as return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getJVMMemorySurvivor() throws AdminException {
        return admin().getJVMMemorySurvivor().toJSONString(true);
    }
    
    @GET
    @Path("/platform-mbean/runtime/system-properties")
    @ApiOperation(value = "Getting System Properties version", notes = "Getting System Properties version return json")
    @ApiResponses({@ApiResponse(code = 404, message = "operation error")})
    public String getSystemProperties() throws AdminException {
        return admin().getSystemProperties().toJSONString(true);
    }
}
