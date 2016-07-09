package org.wilfly.admin;

import java.io.IOException;
import java.util.ArrayList;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.as.controller.client.Operation;
import org.jboss.as.controller.client.OperationMessageHandler;
import org.jboss.as.controller.client.OperationResponse;
import org.jboss.dmr.ModelNode;
import org.jboss.threads.AsyncFuture;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.wildfly.admin.Admin;
import org.wildfly.admin.AdminException;
import org.wildfly.admin.impl.AdminImpl;

public class SubsystemBase {
    
    static Admin admin;
    
    @BeforeClass
    public static void init() throws AdminException{
        admin = new AdminImpl(new TestModelControllerClient());
    }
    
    @AfterClass
    public static void destroy(){
        admin.close();
    }
    
    static class TestModelControllerClient implements ModelControllerClient{

        @Override
        public void close() throws IOException {            
        }

        @Override
        public ModelNode execute(ModelNode operation) throws IOException {
            System.out.println(operation);
            ModelNode list = new ModelNode();
            list.add(new ModelNode());
            return list;
        }

        @Override
        public ModelNode execute(Operation operation) throws IOException {
            return null;
        }

        @Override
        public ModelNode execute(ModelNode operation, OperationMessageHandler messageHandler) throws IOException {
            return null;
        }

        @Override
        public ModelNode execute(Operation operation, OperationMessageHandler messageHandler) throws IOException {
            return null;
        }

        @Override
        public OperationResponse executeOperation(Operation operation, OperationMessageHandler messageHandler) throws IOException {
            return null;
        }

        @Override
        public AsyncFuture<ModelNode> executeAsync(ModelNode operation, OperationMessageHandler messageHandler) {
            return null;
        }

        @Override
        public AsyncFuture<ModelNode> executeAsync(Operation operation, OperationMessageHandler messageHandler) {
            return null;
        }

        @Override
        public AsyncFuture<OperationResponse> executeOperationAsync(Operation operation, OperationMessageHandler messageHandler) {
            return null;
        }
        
    }

}
