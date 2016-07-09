package org.wilfly.admin;

import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;
import org.wildfly.admin.AdminException;

@Ignore
public class TestDataSources extends SubsystemBase {

    @Test
    public void testGetInstalledDataSourceNames() throws AdminException {
        admin.getInstalledDataSourceNames();
    }

    @Test
    public void testGetInstalledXADataSourceNames() throws AdminException {
        admin.getInstalledXADataSourceNames();
    }

    @Test
    public void testGetInstalledJDBCDriverNames() throws AdminException {
        admin.getInstalledJDBCDriverNames();
    }

    @Test
    public void testGetInstalledJDBCDrivers() throws AdminException {
        admin.getInstalledJDBCDrivers();
    }

    @Test
    public void testGetInstalledJDBCDriver() throws AdminException {
        admin.getInstalledJDBCDriver("h2");
    }

    @Test
    public void testAddJDBCDriver() throws AdminException {
        Properties properties = new Properties();
        properties.setProperty("driver-xa-datasource-class-name", "org.h2.jdbcx.JdbcDataSource");
        properties.setProperty("driver-class-name", "org.h2.Driver");
        admin.addJDBCDriver("h2", "com.h2database.h2", properties);
    }

    @Test
    public void testRemoveJDBCDriver() throws AdminException {
        admin.removeJDBCDriver("h2");
    }

    @Test
    public void testAddDataSource() throws AdminException {
        Properties props = new Properties();
        props.setProperty("user-name", "test_user");
        props.setProperty("password", "test_pass");
        props.setProperty("use-java-context", "true");
        props.setProperty("enabled", "true");
        props.setProperty("min-pool-size", "5");
        props.setProperty("max-pool-size", "25");
        props.setProperty("connection-url", "jdbc:h2:mem:test");
        admin.addDataSource("ExampleDS", "h2", props);
    }

    @Test
    public void testGetInstalledDataSource() throws AdminException {
        admin.getInstalledDataSource("ExampleDS");
    }

    @Test
    public void testRemoveDataSource() throws AdminException {
        admin.removeDataSource("ExampleDS");
    }

    @Test
    public void testTestConnectionInPool() throws AdminException {
        admin.testConnectionInPool("ExampleDS");
    }

    @Test
    public void testTestConnectionInPoolAuth() throws AdminException {
        admin.testConnectionInPool("ExampleDS", "sa", "sa");
    }

    @Test
    public void testDumpQueuedThreadsInPool() throws AdminException {
        admin.dumpQueuedThreadsInPool("ExampleDS");
    }

    @Test
    public void testFlushAllConnectionInPool() throws AdminException {
        admin.flushAllConnectionInPool("ExampleDS");
    }

    @Test
    public void testFlushGracefullyConnectionInPool() throws AdminException {
        admin.flushGracefullyConnectionInPool("ExampleDS");
    }

    @Test
    public void testFlushIdleConnectionInPool() throws AdminException {
        admin.flushIdleConnectionInPool("ExampleDS");
    }

    @Test
    public void testFlushInvalidConnectionInPool() throws AdminException {
        admin.flushInvalidConnectionInPool("ExampleDS");
    }

    @Test
    public void testGetInstalledXADataSource() throws AdminException {
        admin.getInstalledXADataSource("H2XADS");
    }

    @Test
    public void testRemoveXADataSource() throws AdminException {
        admin.removeXADataSource("H2XADS");
    }

    @Test
    public void testTestConnectionInPoolXA() throws AdminException {
        admin.testConnectionInPoolXA("H2XADS");
    }

    @Test
    public void testTestConnectionInPoolXAAuth() throws AdminException {
        admin.testConnectionInPoolXA("H2XADS", "sa", "sa");
    }

    @Test
    public void testDumpQueuedThreadsInPoolXA()throws AdminException {
        admin.dumpQueuedThreadsInPoolXA("H2XADS");
    }

    @Test
    public void testFlushAllConnectionInPoolXA() throws AdminException {
        admin.flushAllConnectionInPoolXA("H2XADS");
    }

    @Test
    public void testFlushGracefullyConnectionInPoolXA() throws AdminException {
        admin.flushGracefullyConnectionInPoolXA("H2XADS");
    }

    @Test
    public void testFlushIdleConnectionInPoolXA() throws AdminException {
        admin.flushIdleConnectionInPoolXA("H2XADS");
    }

    @Test
    public void testFlushInvalidConnectionInPoolXA() throws AdminException {
        admin.flushIdleConnectionInPoolXA("H2XADS");
    }

    @Test
    public void testAddXADataSource() throws AdminException {
        Properties properties = new Properties();
        properties.setProperty("use-java-context", "true");
        properties.setProperty("enabled", "true");
        properties.setProperty("min-pool-size", "5");
        properties.setProperty("max-pool-size", "25");
        properties.setProperty("User", "sa");
        properties.setProperty("Password", "sa");
        properties.setProperty("URL", "jdbc:h2:mem:test");
        properties.setProperty("Description", "This is description");
        admin.addXADataSource("H2XADS", "h2", properties);
    }



}
