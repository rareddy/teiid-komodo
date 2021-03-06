/*
 * Copyright Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags and
 * the COPYRIGHT.txt file distributed with this work.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.komodo.repository;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komodo.datavirtualization.DataVirtualization;
import org.komodo.datavirtualization.ViewDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@SuppressWarnings("nls")
public class ViewDefinitionTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WorkspaceManagerImpl workspaceManagerImpl;

    @Test
    public void testFindDeleteByName() throws Exception {
        DataVirtualization dv = workspaceManagerImpl.createDataVirtualization("name");

        ViewDefinition v = workspaceManagerImpl.createViewDefiniton(dv.getName(), "x");
        v.setDdl("create ...");

        entityManager.flush();

        ViewDefinition found = workspaceManagerImpl.findViewDefinition(v.getId());

        assertEquals(v.getDdl(), found.getDdl());

        workspaceManagerImpl.createViewDefiniton(dv.getName(), "y");

        workspaceManagerImpl.createViewDefiniton(dv.getName(), "x1").setComplete(true);

        assertNotNull(found.getCreatedAt());

        entityManager.flush();

        assertNotNull(found.getCreatedAt());

        assertEquals(3, workspaceManagerImpl.findViewDefinitions(dv.getName()).size());

        assertEquals(Arrays.asList("x", "y", "x1"), workspaceManagerImpl.findViewDefinitionsNames(dv.getName()));

        //x matching ignore case
        assertNotNull(workspaceManagerImpl.findViewDefinitionByNameIgnoreCase(dv.getName(), "X"));

        assertTrue(workspaceManagerImpl.deleteViewDefinition(v.getId()));

        assertFalse(workspaceManagerImpl.deleteViewDefinition(v.getId()));

        workspaceManagerImpl.createViewDefiniton(dv.getName(), v.getName());

        entityManager.flush();
    }

    @Test
    public void testState() {
        DataVirtualization dv = workspaceManagerImpl.createDataVirtualization("name");

        ViewDefinition v = workspaceManagerImpl.createViewDefiniton(dv.getName(), "existing");

        v.setDdl("create ...");
        v.addSourcePath("x");

        entityManager.flush();
        entityManager.detach(v);

        ViewDefinition found = workspaceManagerImpl.findViewDefinition(v.getId());
        assertEquals("create ...", found.getDdl());
        assertEquals(Arrays.asList("x"), found.getSourcePaths());
    }

    @Test
    public void testSameName() throws Exception {
        workspaceManagerImpl.createDataVirtualization("name");

        workspaceManagerImpl.createDataVirtualization("name1");

        workspaceManagerImpl.createViewDefiniton("name", "x");

        entityManager.flush();

        workspaceManagerImpl.createViewDefiniton("name1", "x");

        entityManager.flush();
    }

}
