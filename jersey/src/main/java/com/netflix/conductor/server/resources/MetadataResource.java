/*
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.conductor.server.resources;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.core.execution.ApplicationException;
import com.netflix.conductor.service.MetadataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author Viren
 */
@Api(value = "/metadata", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, tags = "Metadata Management")
@Path("/metadata")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class MetadataResource {

    private MetadataService service;

    @Inject
    public MetadataResource(MetadataService service) {
        this.service = service;
    }

    @POST
    @Path("/workflow")
    @ApiOperation("Create a new workflow definition")
    public void create(WorkflowDef def) {
        service.registerWorkflowDef(def);
    }

    @PUT
    @Path("/workflow")
    @ApiOperation("Create or update workflow definition")
    public void update(List<WorkflowDef> defs) {
        service.updateWorkflowDef(defs);
    }

    @GET
    @ApiOperation("Retrieves workflow definition along with blueprint")
    @Path("/workflow/{name}")
    public WorkflowDef get(@PathParam("name") String name, @QueryParam("version") Integer version) {
        return service.getWorkflowDef(name, version).orElseThrow(() ->
                new ApplicationException(
                        ApplicationException.Code.NOT_FOUND,
                        String.format("No such workflow for name=%s, version=%s", name, version)
                )
        );
    }

    @GET
    @ApiOperation("Retrieves all workflow definition along with blueprint")
    @Path("/workflow")
    public List<WorkflowDef> getAll() {
        return service.getWorkflowDefs();
    }

    @DELETE
    @Path("/workflow/{name}/{version}")
    @ApiOperation("Removes workflow definition. It does not remove workflows associated with the definition.")
    public void unregisterWorkflowDef(@PathParam("name") String name, @PathParam("version") Integer version) throws Exception {
        service.unregisterWorkflowDef(name, version);
    }

    @POST
    @Path("/taskdefs")
    @ApiOperation("Create new task definition(s)")
    public void registerTaskDef(List<TaskDef> taskDefs) {
        service.registerTaskDef(taskDefs);
    }

    @PUT
    @Path("/taskdefs")
    @ApiOperation("Update an existing task")
    public void registerTaskDef(TaskDef taskDef) {
        service.updateTaskDef(taskDef);
    }

    @GET
    @Path("/taskdefs")
    @ApiOperation("Gets all task definition")
    @Consumes({MediaType.WILDCARD})
    public List<TaskDef> getTaskDefs() {
        return service.getTaskDefs();
    }

    @GET
    @Path("/taskdefs/{tasktype}")
    @ApiOperation("Gets the task definition")
    @Consumes({MediaType.WILDCARD})
    public TaskDef getTaskDef(@PathParam("tasktype") String taskType) {
        return service.getTaskDef(taskType);
    }

    @DELETE
    @Path("/taskdefs/{tasktype}")
    @ApiOperation("Remove a task definition")
    public void unregisterTaskDef(@PathParam("tasktype") String taskType) {
        service.unregisterTaskDef(taskType);
    }

}
