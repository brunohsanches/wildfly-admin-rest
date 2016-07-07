package org.wildfly.admin.tmp;

import java.util.Properties;
import java.util.UUID;

import javax.enterprise.concurrent.AbortedException;

import org.wildfly.admin.Admin;
import org.wildfly.admin.AdminException;
import org.wildfly.admin.AdminUtil;


public class Test {

    static Properties props = new Properties();
    static Properties xaprops = new Properties();
    
    static String dsName = UUID.randomUUID().toString();
    static String url = "jdbc:h2:mem:" + dsName + ";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    
//    static String dsName = UUID.randomUUID().toString();
    
    static {       
        props.setProperty("user-name", "test_user");
        props.setProperty("password", "test_pass");
        props.setProperty("use-java-context", "true");
        props.setProperty("enabled", "true");
        props.setProperty("min-pool-size", "5");
        props.setProperty("max-pool-size", "25");
//        props.setProperty("connection-url", url);
        
    }
    
    
    
    static {
        xaprops.putAll(props);
        
        xaprops.setProperty("xa-datasource-properties", "User=sa,Password=sa,URL=" + url + ",Description=\"this is description\"");
    }

    public static void main(String[] args) throws AbortedException, AdminException {

        Admin admin = AdminUtil.admin();
                
//        admin.createDataSource(dsName, "h2", props);
//        admin.createDataSource(xadsName, "h2", xaprops);
        
//        System.out.println(admin.createDataSource(dsName, "h2", props));
        
        System.out.println(admin.createXADataSource(dsName, "h2", xaprops));
        System.out.println(admin.getInstalledXADataSourceNames());
        
        System.exit(0);
    }

}
