package org.wilfly.admin;

import static org.junit.Assert.*;
import static org.wildfly.admin.AdminUtil.CLI.*;

import org.junit.Test;
import org.wildfly.admin.Admin;
import org.wildfly.admin.AdminException;
import org.wildfly.admin.AdminUtil;

public class TestAdmin {

    @Test
    public void testResourceBundle(){
        assertEquals(AdminUtil.gs(addXADataSource_xa_data_source_resource_description), "/subsystem=datasources/xa-data-source=foo:read-resource-description()");      
    }
    
    @Test
    public void testAdmin() throws AdminException {
        Admin admin = Admin.Factory.getInstance().createAdmin(null);
        assertNotNull(admin);
    }
}
