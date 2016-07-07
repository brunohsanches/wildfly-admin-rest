package org.wildfly.admin;

public class AdminException extends Exception {

    private static final long serialVersionUID = -1236520748419615322L;
    
    public AdminException() {
        super();
    }
    
    public AdminException(String message) {
        super(message);
    }
    
    public AdminException(Throwable cause) {
        super(cause);
    }
    
    public AdminException(String message, Throwable cause) {
        super(message, cause);
    }
}
