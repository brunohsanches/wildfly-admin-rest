package org.wildfly.admin;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;
import javax.security.sasl.RealmChoiceCallback;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.wildfly.admin.impl.AdminImpl;

public interface Admin {
    
    /**
     * Get Installed DataSource
     * @param datasourceName
     * @return
     * @throws AdminException
     */
    ModelNode getInstalledDataSource(String datasourceName)throws AdminException;
    
    /**
     * Get Installed XA DataSource
     * @param datasourceName
     * @return
     * @throws AdminException
     */
    ModelNode getInstalledXADataSource(String datasourceName)throws AdminException;
    
    /**
     * Get Installed DataSource Name
     * @return
     * @throws AdminException
     */
    ModelNode getInstalledDataSourceNames() throws AdminException;
    
    /**
     * Get Installed XA DataSource Name
     * @return
     * @throws AdminException
     */
    ModelNode getInstalledXADataSourceNames() throws AdminException;
    
    /**
     * Get all installed DataSource driver  
     * @throws AdminException
     */
    ModelNode getInstalledJDBCDrivers() throws AdminException;
    
    /**
     * Get an installed DataSource driver by driverName
     * @param driverName
     * @throws AdminException
     */
    ModelNode getInstalledJDBCDriver(String driverName) throws AdminException;
    
    /**
     * Creates a JCA data source
     * @param deploymentName - name of the source
     * @param templateName - template of data source
     * @param properties - properties
     * @throws AdminException
     */
    ModelNode createDataSource(String deploymentName, String driverName, Properties properties) throws AdminException;

    /**
     * Creates a JCA xa data source
     * @param deploymentName - name of the source
     * @param templateName - template of data source
     * @param properties - properties
     * @throws AdminException
     */
    ModelNode createXADataSource(String deploymentName, String driverName, Properties properties) throws AdminException;
    
    public static class Factory {
        
        private static Factory INSTANCE = new Factory();
        
        public static Factory getInstance() {
            return INSTANCE;
        }
        
        /**
         * Creates a ServerAdmin with the specified connection properties.
         * @param userName
         * @param password
         * @param serverURL
         * @param applicationName
         * @param profileName - Name of the domain mode profile
         * @return
         * @throws AdminException
         */
        public Admin createAdmin(String host, int port, String userName, char[] password, String profileName) throws AdminException {
            AdminImpl admin = (AdminImpl)createAdmin(host, port, userName, password);
            if (admin != null){
                admin.setProfileName(profileName);
            }
            return admin;
        }

        /**
         * Creates a ServerAdmin with the specified connection properties.
         * @param userName
         * @param password
         * @param serverURL
         * @param applicationName
         * @return
         * @throws AdminException
         */
        public Admin createAdmin(String host, int port, String userName, char[] password) throws AdminException {
            
            if(host == null){
                host = "localhost";
            }
            
            if(port < 0){
                port = 9990;
            }
            
            try {
                CallbackHandler cbh = new AuthenticationCallbackHandler(userName, password);
                ModelControllerClient newClient = ModelControllerClient.Factory.create(host, port, cbh);
                return new AdminImpl(newClient);
            } catch (UnknownHostException e) {
                throw new AdminException(e);
            }
        }
        
        public Admin createAdmin() throws AdminException {
            
            try {
                 ModelControllerClient newClient = ModelControllerClient.Factory.create("localhost", 9990);
                return new AdminImpl(newClient);
            } catch (UnknownHostException e) {
                throw new AdminException(e);
            }
        }   
        
        public Admin createAdmin(ModelControllerClient connection) throws AdminException {
            return new AdminImpl(connection);
        }
        
        public Admin createAdmin(ModelControllerClient connection, String profileName) throws AdminException {
            AdminImpl admin = new AdminImpl(connection);
            admin.setProfileName(profileName);
            return admin;
        }
        
        private class AuthenticationCallbackHandler implements CallbackHandler {
            private boolean realmShown = false;
            private String userName = null;
            private char[] password = null;

            public AuthenticationCallbackHandler(String user, char[] password) {
                this.userName = user;
                this.password = password;
            }

            public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                // Special case for anonymous authentication to avoid prompting user for their name.
                if (callbacks.length == 1 && callbacks[0] instanceof NameCallback) {
                    ((NameCallback)callbacks[0]).setName("anonymous CLI user"); //$NON-NLS-1$
                    return;
                }

                for (Callback current : callbacks) {
                    if (current instanceof RealmCallback) {
                        RealmCallback rcb = (RealmCallback) current;
                        String defaultText = rcb.getDefaultText();
                        rcb.setText(defaultText); // For now just use the realm suggested.
                        if (this.realmShown == false) {
                            this.realmShown = true;
                        }
                    } else if (current instanceof RealmChoiceCallback) {
                        throw new UnsupportedCallbackException(current, "Realm choice not currently supported."); //$NON-NLS-1$
                    } else if (current instanceof NameCallback) {
                        NameCallback ncb = (NameCallback) current;
                        ncb.setName(this.userName);
                    } else if (current instanceof PasswordCallback) {
                        PasswordCallback pcb = (PasswordCallback) current;
                        pcb.setPassword(this.password);
                    } else {
                        throw new UnsupportedCallbackException(current);
                    }
                }
            }

        }
    }
}
