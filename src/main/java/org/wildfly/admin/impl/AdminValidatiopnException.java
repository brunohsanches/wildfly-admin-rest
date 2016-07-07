package org.wildfly.admin.impl;

import org.wildfly.admin.AdminException;


public class AdminValidatiopnException extends AdminException {

    private static final long serialVersionUID = 7086535912239835251L;
    
    public AdminValidatiopnException() {
        super();
    }
    
    public AdminValidatiopnException(String message) {
        super(message);
    }
    
    public AdminValidatiopnException(Throwable cause) {
        super(cause);
    }
    
    public AdminValidatiopnException(String message, Throwable cause) {
        super(message, cause);
    }

}
