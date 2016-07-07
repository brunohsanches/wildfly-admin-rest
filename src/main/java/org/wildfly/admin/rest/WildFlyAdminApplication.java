package org.wildfly.admin.rest;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import java.util.Set;
import java.util.HashSet;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.wildfly.admin.rest.resource.DataSourceResource;

@ApplicationPath("/")
public class WildFlyAdminApplication extends Application {

    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();
    public WildFlyAdminApplication(){
        singletons.add(new ApiListingResource());
        singletons.add(new SwaggerSerializers());
        singletons.add(new DataSourceResource());
    }
    @Override
    public Set<Class<?>> getClasses() {
         return empty;
    }
    @Override
    public Set<Object> getSingletons() {
         return singletons;
    }
}
