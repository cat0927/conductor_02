/**
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
/**
 *
 */
package com.netflix.conductor.dao.dynomite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.Task.Status;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.utils.JsonMapperProvider;
import com.netflix.conductor.config.TestConfiguration;
import com.netflix.conductor.core.config.Configuration;
import com.netflix.conductor.dao.ExecutionDAO;
import com.netflix.conductor.dao.ExecutionDAOTest;
import com.netflix.conductor.dao.IndexDAO;
import com.netflix.conductor.dao.MetadataDAO;
import com.netflix.conductor.dao.redis.JedisMock;
import com.netflix.conductor.dyno.DynoProxy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import redis.clients.jedis.JedisCommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

/**
 * @author Viren
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RedisExecutionDAOTest extends ExecutionDAOTest {

    private RedisMetadataDAO metadataDAO;

    private RedisExecutionDAO executionDAO;

	@Mock
	private IndexDAO indexDAO;

	private static ObjectMapper objectMapper = new JsonMapperProvider().get();

    @SuppressWarnings("unchecked")
    @Before
    public void init() throws Exception {
        Configuration config = new TestConfiguration();
        JedisCommands jedisMock = new JedisMock();
        DynoProxy dynoClient = new DynoProxy(jedisMock);

        metadataDAO = new RedisMetadataDAO(dynoClient, objectMapper, config);
        executionDAO = new RedisExecutionDAO(dynoClient, objectMapper, mock(IndexDAO.class), metadataDAO, config);

        // Ignore indexing in Redis tests.
        doNothing().when(indexDAO).indexTask(any(Task.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCorrelateTaskToWorkflowInDS() throws Exception {
        String workflowId = "workflowId";
        String taskId = "taskId1";
        String taskDefName = "task1";

        TaskDef def = new TaskDef();
        def.setName("task1");
        def.setConcurrentExecLimit(1);
        metadataDAO.createTaskDef(def);

        Task task = new Task();
        task.setTaskId(taskId);
        task.setWorkflowInstanceId(workflowId);
        task.setReferenceTaskName("ref_name");
        task.setTaskDefName(taskDefName);
        task.setTaskType(taskDefName);
        task.setStatus(Status.IN_PROGRESS);
        List<Task> tasks = executionDAO.createTasks(Collections.singletonList(task));
        assertNotNull(tasks);
        assertEquals(1, tasks.size());

        executionDAO.correlateTaskToWorkflowInDS(taskId, workflowId);
        tasks = executionDAO.getTasksForWorkflow(workflowId);
        assertNotNull(tasks);
        assertEquals(workflowId, tasks.get(0).getWorkflowInstanceId());
        assertEquals(taskId, tasks.get(0).getTaskId());
    }

    @Override
    protected ExecutionDAO getExecutionDAO() {
        return executionDAO;
    }

    @Override
    protected MetadataDAO getMetadataDAO() {
        return metadataDAO;
    }
}
