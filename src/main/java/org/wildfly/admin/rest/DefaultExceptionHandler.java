package org.wildfly.admin.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Exception> {
    
    static Logger logger = Logger.getLogger(DefaultExceptionHandler.class.getName());
    
	@Override
	public Response toResponse(Exception e) {
		
		String status = "ERROR";
		if(e instanceof NotFoundException){
			status = "404";
		} else if(e instanceof InternalServerErrorException){
			status = "500";
		}
		
		String message = e.getMessage();
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String details = sw.toString();
		
		logger.log(Level.SEVERE, details);
		
		StringBuilder response = new StringBuilder("<error>");
        response.append("<code>" + status + "</code>");
        response.append("<message>" + message + "</message>");
        response.append("<details>" + details + "</details>");
        response.append("</error>");
        return Response.serverError().entity(response.toString()).type(MediaType.APPLICATION_XML).build();
	}

}
