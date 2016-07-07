package org.wildfly.admin;

import java.text.MessageFormat;
import java.util.ResourceBundle;

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
    
    static Admin admin = null;
    
    public static Admin admin() throws AdminException{
        if(admin == null) {
            admin = Admin.Factory.getInstance().createAdmin("localhost", 10090, "admin", "password1!".toCharArray());
        }
        return admin;
    }

    public static enum CLI {
        getInstalledDataSource,
        getInstalledXADataSource,
        getInstalledDataSourceNames,
        getInstalledXADataSourceNames,
        getInstalledJDBCDrivers,
        getInstalledJDBCDriver,
        createXADataSource_data_source_resource_description,
        createXADataSource,
        createXADataSource_xa_data_source_resource_description,
        createXADataSource_xa_datasource_class_validation
    }
}
