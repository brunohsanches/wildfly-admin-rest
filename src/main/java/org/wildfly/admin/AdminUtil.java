package org.wildfly.admin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.ResourceBundle;

import org.wildfly.admin.impl.AdminImpl;

public class AdminUtil {
    
    static ResourceBundle bundle = ResourceBundle.getBundle("cli");
    
    public static String getString(final String key){
        return bundle.getString(key);
    }
    
    public static String getString(final String key, final Object... parameters) {
        
        String text = getString(key);
        
        if (text == null) {
            return '<' + key + '>';
        }

        if (parameters == null || parameters.length == 0) {
            return text;
        }
        
        return MessageFormat.format(text, parameters);
    }
    
    public static String gs(final CLI cli, final Object... parameters){
        return getString(cli.name(), parameters);
    }
    
    public static Properties loadProperties(String properties) throws AdminException {
        if(properties == null || properties.equals("")){
            return new Properties();
        }
        properties = properties.replaceAll("\\\\", "\\\\\\\\");
        properties = properties.replaceAll(",", "\n");
        ByteArrayInputStream is = new ByteArrayInputStream(properties.getBytes());
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            throw new AdminException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return props;
    }
    
    static Admin admin = null;
    
    public static Admin admin() throws AdminException{
        if(admin == null) {
            admin = Admin.Factory.getInstance().createAdmin("localhost", 10090, "admin", "password1!".toCharArray());
        }
        return admin;
    }
    
    public static void setAdmin(AdminImpl adminImpl) {
        admin = adminImpl;
    } 

    public static enum CLI {    
        reload,
        resume,
        suspend,
        shutdown,
        restart,
        getProductInfo,
        
        getInstalledDataSourceNames,
        getInstalledXADataSourceNames,
        getInstalledJDBCDriverNames,
        getInstalledJDBCDrivers,
        getInstalledJDBCDriver,
        
        addJDBCDriver_jdbc_driver_resource_description,
        addJDBCDriver,
        removeJDBCDriver,
        
        addDataSource_data_source_resource_description,
        addDataSource,
        getInstalledDataSource,
        removeDataSource,
        testConnectionInPool,
        testConnectionInPoolAuth,
        dumpQueuedThreadsInPool,
        flushAllConnectionInPool,
        flushGracefullyConnectionInPool,
        flushIdleConnectionInPool,
        flushInvalidConnectionInPool,
        
        addXADataSource_xa_data_source_resource_description,
        addXADataSource,
        getInstalledXADataSource,
        removeXADataSource,
        testConnectionInPoolXA,
        testConnectionInPoolAuthXA,
        dumpQueuedThreadsInPoolXA,
        flushAllConnectionInPoolXA,
        flushGracefullyConnectionInPoolXA,
        flushIdleConnectionInPoolXA,
        flushInvalidConnectionInPoolXA
    }
}
