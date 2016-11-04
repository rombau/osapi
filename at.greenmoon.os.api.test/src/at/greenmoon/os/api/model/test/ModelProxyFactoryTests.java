package at.greenmoon.os.api.model.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import at.greenmoon.os.model.ITeam;
import at.greenmoon.os.model.impl.ModelProxyFactory;
import at.greenmoon.os.model.impl.ModelProxyFactory.ResourceEntity;

public class ModelProxyFactoryTests {

    @Test
    public void testModelProxyFactory() throws Exception {

        new ModelProxyFactory() {
            //
        };
    }

    @Test
    public void testCreateModelProxy() throws Exception {

        ITeam team = ModelProxyFactory.create(ITeam.class, null);

        assertNotNull(team);
        assertNull(team.getResource());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUnknownModelProxy() throws Exception {

        ModelProxyFactory.create(ResourceEntity.class, null);
    }

}
