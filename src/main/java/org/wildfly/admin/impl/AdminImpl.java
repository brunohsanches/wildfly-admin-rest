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
        
        Set<String> propSet = new HashSet<String>();
        List<ModelNode> list = this.execute(buildRequest(createDataSource_data_source_resource_description)).asList();
        
        for(ModelNode node : list) {
            if(node.asProperty().getName().equals("attributes")){
                ModelNode attrNode = node.asProperty().getValue();
                List<ModelNode> sublist = attrNode.asList();
                for(ModelNode subnode : sublist){
                    propSet.add(subnode.asProperty().getName());
                }
                
            }          
        }
        return propSet;
    }
    
    private Set<String> getXADataSourceTmplete() throws AdminException {
        
        Set<String> propSet = new HashSet<String>();
        List<ModelNode> list = this.execute(buildRequest(createXADataSource_xa_data_source_resource_description)).asList();
        
        for(ModelNode node : list) {
            if(node.asProperty().getName().equals("attributes")){
                ModelNode attrNode = node.asProperty().getValue();
                List<ModelNode> sublist = attrNode.asList();
                for(ModelNode subnode : sublist){
                    propSet.add(subnode.asProperty().getName());
                }
                
            }          
        }
        return propSet;
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
    public ModelNode getInstalledJDBCDrivers() throws AdminException {
        return this.execute(buildRequest(getInstalledJDBCDrivers));
    }

    @Override
    public ModelNode getInstalledJDBCDriver(String driverName) throws AdminException {
        return this.execute(buildRequest(getInstalledJDBCDriver, driverName));
    }

    @Override
    public ModelNode createDataSource(String deploymentName, String driverName, Properties properties) throws AdminException {
        
        deploymentName = removeJavaContext(deploymentName);
        
        ModelNode dsResult = this.getInstalledDataSourceNames();
        for(ModelNode node : dsResult.asList()){
            if(node.asString().equals(deploymentName)){
                throw new AdminValidatiopnException(deploymentName + " aready exist");
            }
        }
        
        if(this.getInstalledJDBCDriver(driverName).hasDefined("failure-description")){
            throw new AdminValidatiopnException(driverName + " not exist");
        }
        
        Iterator<String> iterator = getDataSourceTmplete().iterator();
        StringBuffer sb = new StringBuffer();
        while(iterator.hasNext()){
            String key = iterator.next();
            Object value = properties.remove(key);
            if(value != null){
                sb.append(", ");
                sb.append(key).append("=").append("\"").append(value).append("\"");
            }
        }
 
        return this.execute(buildRequest(createDataSource, removeJavaContext(deploymentName), driverName, addJavaContext(deploymentName), sb.toString()));
    }  

    @Override
    public ModelNode createXADataSource(String deploymentName, String driverName, Properties properties) throws AdminException {
        
        deploymentName = removeJavaContext(deploymentName);
        
        ModelNode dsResult = this.getInstalledXADataSourceNames();
        for(ModelNode node : dsResult.asList()){
            if(node.asString().equals(deploymentName)){
                throw new AdminValidatiopnException(deploymentName + " aready exist");
            }
        }
        
        if(this.getInstalledJDBCDriver(driverName).hasDefined("failure-description")){
            throw new AdminValidatiopnException(driverName + " not exist");
        }
        
        Iterator<String> iterator = getXADataSourceTmplete().iterator();
        StringBuffer sb = new StringBuffer();
        while(iterator.hasNext()){
            String key = iterator.next();
            Object value = properties.remove(key);
            if(value != null){
                sb.append(", ");
                sb.append(key).append("=").append("\"").append(value).append("\"");
            }
        }
        String optionArgu = sb.toString();
        sb.delete(0, sb.length());
        
        for(Object obj : properties.keySet()){
            String key = (String) obj;
            sb.append("--" + key).append("=>").append(properties.getProperty(key));
        }
              
        return this.execute(buildRequest(createXADataSource, removeJavaContext(deploymentName), driverName, addJavaContext(deploymentName), optionArgu, sb.toString()));
    }

    

}
