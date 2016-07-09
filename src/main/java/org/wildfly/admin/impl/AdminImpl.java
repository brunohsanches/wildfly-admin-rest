package org.wildfly.admin.impl;

import static org.wildfly.admin.AdminUtil.CLI.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandFormatException;
import org.jboss.as.cli.Util;
import org.jboss.as.cli.operation.impl.DefaultOperationRequestAddress;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.wildfly.admin.Admin;
import org.wildfly.admin.AdminException;
import org.wildfly.admin.AdminUtil;
import org.wildfly.admin.AdminUtil.CLI;

public class AdminImpl implements Admin {
    
    private static final String JAVA_CONTEXT = "java:/";
    
    private ModelControllerClient connection;
    private CommandContext ctx;
    
    private boolean domainMode = false;
    private String profileName;

    public AdminImpl(ModelControllerClient connection) throws AdminException {
        this.connection = connection;
        List<String> nodeTypes = Util.getNodeTypes(connection, new DefaultOperationRequestAddress());
        if (!nodeTypes.isEmpty()) {
            this.domainMode = nodeTypes.contains("server-group"); //$NON-NLS-1$
        }
        try {
            this.ctx = CommandContextFactory.getInstance().newCommandContext();
            this.ctx.bindClient(connection);
        } catch (CliInitializationException e) {
            throw new AdminException(e);
        }
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
    
    private String getProfileName() throws AdminException{
        if((this.profileName == null || this.profileName.equals("")) && this.domainMode){
            throw new AdminException("domainMode need set a profileName");
        }
        return this.profileName;
    }
    
    private String removeJavaContext(String deployedName) {
        if (deployedName.startsWith(JAVA_CONTEXT)) {
            deployedName = deployedName.substring(6);
        }
        return deployedName;
    }
    
    private String addJavaContext(String deployedName) {
        if (!deployedName.startsWith(JAVA_CONTEXT)) {
            deployedName = JAVA_CONTEXT + deployedName;
        }
        return deployedName;
    }
    
    private ModelNode buildRequest(CLI cli, Object... parameters) throws AdminException{
        String line = AdminUtil.gs(cli, parameters);
        if(this.domainMode){
            line = this.getProfileName().concat(line);
        }
        try {
            return this.ctx.buildRequest(line);
        } catch (CommandFormatException e) {
            throw new AdminException(e);
        }
    }
    
    private ModelNode buildRequestSimple(CLI cli, Object... parameters) throws AdminException{
        String line = AdminUtil.gs(cli, parameters);
        try {
            return this.ctx.buildRequest(line);
        } catch (CommandFormatException e) {
            throw new AdminException(e);
        }
    }
    
    private ModelNode execute(ModelNode request) throws AdminException{
        try {
            ModelNode outcome = this.connection.execute(request);
            if (outcome.hasDefined("result")){
                return outcome.get("result");
            }
            return outcome;
        } catch (IOException e) {
            throw new AdminException(e);
        }
    }
    
    private Set<String> getDataSourceTmplete() throws AdminException {
        return this.dumpReadResourceDescription(execute(buildRequest(addDataSource_data_source_resource_description)).asList());
    }
    
    private Set<String> getXADataSourceTmplete() throws AdminException {
        return this.dumpReadResourceDescription(execute(buildRequest(addXADataSource_xa_data_source_resource_description)).asList());
    }
    
    private Set<String> getJDBCDriverTmplete() throws AdminException {
        return this.dumpReadResourceDescription(execute(buildRequest(addJDBCDriver_jdbc_driver_resource_description)).asList());
    }
    
    private String buildPropertyString(Set<String> propKeys, String prefix, Properties properties, String... excludes) {
        Set<String> excludesSet = new HashSet<String>();
        for(String str : excludes){
            excludesSet.add(str);
        }
        Iterator<String> iterator = propKeys.iterator();
        StringBuffer sb = new StringBuffer();
        while(iterator.hasNext()){
            String key = iterator.next();
            Object value = properties.remove(key);
            if(excludesSet.contains(key)){
                continue;
            }
            if(value != null){
                sb.append(", ");
                if(key.equals("connection-url") || key.equals("URL") || key.equals("url")){
                    sb.append(prefix + key).append("=").append("\"").append(value).append("\"");
                } else {
                    sb.append(prefix + key).append("=").append(value);
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * dump the :read-resource-description() result's key to a set
     */
    private Set<String> dumpReadResourceDescription(List<ModelNode> list){
        Set<String> keySet = new HashSet<String>();
        for(ModelNode node : list) {
            if(node.asProperty().getName().equals("attributes")){
                ModelNode attrNode = node.asProperty().getValue();
                List<ModelNode> sublist = attrNode.asList();
                for(ModelNode subnode : sublist){
                    keySet.add(subnode.asProperty().getName());
                }
            }          
        }
        return keySet;
    }
    
    @Override
    public ModelNode reload() throws AdminException {
        return this.execute(buildRequest(reload));
    }

    @Override
    public ModelNode resume() throws AdminException {
        return this.execute(buildRequest(resume));
    }

    @Override
    public ModelNode suspend() throws AdminException {
        return this.execute(buildRequest(suspend));
    }

    @Override
    public ModelNode shutdown() throws AdminException {
        return this.execute(buildRequest(shutdown));
    }
    
    @Override
    public ModelNode restart() throws AdminException {
        return this.execute(buildRequest(restart));
    }

    @Override
    public ModelNode getProductInfo() throws AdminException {
        return this.execute(buildRequest(getProductInfo));
    }
    
    @Override
    public ModelNode getInstalledDataSource(String datasourceName)throws AdminException {
        return this.execute(buildRequest(getInstalledDataSource, datasourceName));
    }

    @Override
    public ModelNode getInstalledXADataSource(String datasourceName) throws AdminException {
        return this.execute(buildRequest(getInstalledXADataSource, datasourceName));
    }

    @Override
    public ModelNode getInstalledDataSourceNames() throws AdminException {
        return this.execute(buildRequest(getInstalledDataSourceNames));
    }

    @Override
    public ModelNode getInstalledXADataSourceNames() throws AdminException {
        return this.execute(buildRequest(getInstalledXADataSourceNames));
    }

    @Override
    public ModelNode getInstalledJDBCDriverNames() throws AdminException {
        return this.execute(buildRequest(getInstalledJDBCDriverNames));
    }

    @Override
    public ModelNode getInstalledJDBCDrivers() throws AdminException {
        return this.execute(buildRequest(getInstalledJDBCDrivers));
    }

    @Override
    public ModelNode getInstalledJDBCDriver(String driverName) throws AdminException {
        return this.execute(buildRequest(getInstalledJDBCDriver, driverName));
    }

    @Override
    public ModelNode addJDBCDriver(String driverName, String driverModuleName, Properties properties) throws AdminException {

        for(ModelNode node : getInstalledJDBCDriverNames().asList()){
            if(node.asString().equals(driverName)){
                throw new AdminValidatiopnException(driverName + " already exist");
            }
        }
        
        String propString  = this.buildPropertyString(getJDBCDriverTmplete(), "", properties, "driver-name", "driver-module-name");       
        return this.execute(buildRequest(addJDBCDriver, driverName, driverName, driverModuleName, propString));
    }

    @Override
    public ModelNode addDataSource(String deploymentName, String driverName, Properties properties) throws AdminException {
        
        deploymentName = removeJavaContext(deploymentName);

        for(ModelNode node : getInstalledDataSourceNames().asList()){
            if(node.asString().equals(deploymentName)){
                throw new AdminValidatiopnException(deploymentName + " already exist");
            }
        }
        
        if(this.getInstalledJDBCDriver(driverName).hasDefined("failure-description")){
            throw new AdminValidatiopnException(driverName + " not exist");
        }
        
        String propString = this.buildPropertyString(getDataSourceTmplete(), "", properties, "driver-name", "jndi-name");
        return this.execute(buildRequest(addDataSource, removeJavaContext(deploymentName), driverName, addJavaContext(deploymentName), propString));
    }  

    @Override
    public ModelNode addXADataSource(String deploymentName, String driverName, Properties properties) throws AdminException {
        
        deploymentName = removeJavaContext(deploymentName);
        
        ModelNode dsResult = this.getInstalledXADataSourceNames();
        for(ModelNode node : dsResult.asList()){
            if(node.asString().equals(deploymentName)){
                throw new AdminValidatiopnException(deploymentName + " already exist");
            }
        }
        
        if(this.getInstalledJDBCDriver(driverName).hasDefined("failure-description")){
            throw new AdminValidatiopnException(driverName + " not exist");
        }
        
        String optionArgu = this.buildPropertyString(getXADataSourceTmplete(), "--" , properties, "driver-name", "jndi-name", "xa-datasource-properties");
        
        StringBuffer sb = new StringBuffer();
        boolean isFirst = true;
        for(Object obj : properties.keySet()){
            String key = (String) obj;
            if(isFirst){
                isFirst = false;
            } else {
                sb.append(", ");
            }
            sb.append(key).append("=>").append(properties.getProperty(key));
        }
              
        return this.execute(buildRequestSimple(addXADataSource, removeJavaContext(deploymentName), driverName, addJavaContext(deploymentName), optionArgu, sb.toString()));
    }

    @Override
    public ModelNode testConnectionInPool(String dsName) throws AdminException {
        return this.execute(buildRequest(testConnectionInPool, dsName));
    }

    @Override
    public ModelNode testConnectionInPool(String dsName, String username, String password) throws AdminException {
        return this.execute(buildRequest(testConnectionInPoolAuth, dsName, username, password));
    }

    @Override
    public ModelNode dumpQueuedThreadsInPool(String dsName) throws AdminException {
        return this.execute(buildRequest(dumpQueuedThreadsInPool, dsName));
    }

    @Override
    public ModelNode flushAllConnectionInPool(String dsName) throws AdminException {
        return this.execute(buildRequest(flushAllConnectionInPool, dsName));
    }

    @Override
    public ModelNode flushGracefullyConnectionInPool(String dsName) throws AdminException {
        return this.execute(buildRequest(flushGracefullyConnectionInPool, dsName));
    }

    @Override
    public ModelNode flushIdleConnectionInPool(String dsName) throws AdminException {
        return this.execute(buildRequest(flushIdleConnectionInPool, dsName));
    }

    @Override
    public ModelNode flushInvalidConnectionInPool(String dsName)throws AdminException {
        return this.execute(buildRequest(flushInvalidConnectionInPool, dsName));
    }

    @Override
    public ModelNode testConnectionInPoolXA(String xaDsName) throws AdminException {
        return this.execute(buildRequest(testConnectionInPoolXA, xaDsName));
    }

    @Override
    public ModelNode testConnectionInPoolXA(String xaDsName, String username, String password) throws AdminException {
        return this.execute(buildRequest(testConnectionInPoolAuthXA, xaDsName, username, password));
    }

    @Override
    public ModelNode dumpQueuedThreadsInPoolXA(String xaDsName) throws AdminException {
        return this.execute(buildRequest(dumpQueuedThreadsInPoolXA, xaDsName));
    }

    @Override
    public ModelNode flushAllConnectionInPoolXA(String xaDsName) throws AdminException {
        return this.execute(buildRequest(flushAllConnectionInPoolXA, xaDsName));
    }

    @Override
    public ModelNode flushGracefullyConnectionInPoolXA(String xaDsName) throws AdminException {
        return this.execute(buildRequest(flushGracefullyConnectionInPoolXA, xaDsName));
    }

    @Override
    public ModelNode flushIdleConnectionInPoolXA(String xaDsName) throws AdminException {
        return this.execute(buildRequest(flushIdleConnectionInPoolXA, xaDsName));
    }

    @Override
    public ModelNode flushInvalidConnectionInPoolXA(String xaDsName) throws AdminException {
        return this.execute(buildRequest(flushInvalidConnectionInPoolXA, xaDsName));
    }

    @Override
    public ModelNode removeDataSource(String dsName) throws AdminException {
        return this.execute(buildRequest(removeDataSource, dsName));
    }

    @Override
    public ModelNode removeXADataSource(String xaDsName) throws AdminException {
        return this.execute(buildRequest(removeXADataSource, xaDsName));
    }

    @Override
    public ModelNode removeJDBCDriver(String driverName) throws AdminException {
        return this.execute(buildRequest(removeJDBCDriver, driverName));
    }

    @Override
    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (Throwable t) {
                //ignore
            }
            this.connection = null;
            this.domainMode = false;
        }
    }

}
