package org.wildfly.admin.tmp;

import java.util.Properties;
import java.util.UUID;

import javax.enterprise.concurrent.AbortedException;

import org.jboss.dmr.ModelNode;
import org.wildfly.admin.Admin;
import org.wildfly.admin.AdminException;
import org.wildfly.admin.AdminUtil;


public class Test {

    static Properties props = new Properties();
    static Properties xaprops = new Properties();
    
    static Properties driverprops = new Properties();
    
    static String dsName = UUID.randomUUID().toString();
    static String url = "jdbc:h2:mem:" + dsName + ";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    
//    static String dsName = UUID.randomUUID().toString();
    
    static {
        driverprops.setProperty("driver-xa-datasource-class-name", "org.h2.jdbcx.JdbcDataSource");
        driverprops.setProperty("driver-class-name", "org.h2.Driver");
    }
    
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
        xaprops.setProperty("use-java-context", "true");
        xaprops.setProperty("enabled", "true");
        xaprops.setProperty("min-pool-size", "5");
        xaprops.setProperty("max-pool-size", "25");
        xaprops.setProperty("User", "sa");
        xaprops.setProperty("Password", "sa");
        xaprops.setProperty("URL", url);
        xaprops.setProperty("Description", "This is description");
    }

    public static void main(String[] args) throws AbortedException, AdminException {

        Admin admin = AdminUtil.admin();
        
        System.out.println(admin.getJVMVersion());
        System.out.println(admin.getProductVersion());
        System.out.println(admin.getLaunchType());
        
        admin.close();
        
//        admin.addJDBCDriver("h3", "com.h2database.h2", driverprops);
//        System.out.println(admin.getInstalledJDBCDriverNames());
                
//        System.out.println(admin.addDataSource("foo2", "h2", props));
//        System.out.println(admin.getInstalledDataSourceNames());
        
//        System.out.println(admin.addXADataSource("zoo1", "h2", xaprops));
//        System.out.println(admin.getInstalledXADataSourceNames());
//        System.out.println(admin.testConnectionInPoolXA("zoo1"));
        
//        admin.createDataSource(dsName, "h2", props);
//        admin.createDataSource(xadsName, "h2", xaprops);
        
//        System.out.println(admin.createDataSource(dsName, "h2", props));
        
//        System.out.println(admin.createXADataSource(dsName, "h2", xaprops));
//        System.out.println(admin.getInstalledXADataSourceNames());
        
//        System.exit(0);
        
//        ModelNode list = new ModelNode();
//        list.add(new ModelNode());
//        list.add(new ModelNode());
//        System.out.println(list);
    }

}
