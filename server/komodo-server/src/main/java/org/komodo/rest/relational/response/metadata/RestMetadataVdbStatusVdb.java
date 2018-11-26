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
package org.komodo.rest.relational.response.metadata;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.komodo.rest.KRestEntity;
import org.komodo.spi.runtime.TeiidVdb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Object to be serialised by GSON that encapsulates the status of a deployed vdb
 */
@JsonSerialize
@JsonInclude(value=Include.NON_NULL)
public class RestMetadataVdbStatusVdb implements KRestEntity {

	/**
	 * A status indicating no VDB was found to provide a status for.
	 */
	public static final RestMetadataVdbStatusVdb NO_VDB_STATUS = new RestMetadataVdbStatusVdb();

    /**
     * Label for the name
     */
    public static final String VDB_STATUS_NAME = "name";

    /**
     * Label for the deployed name
     */
    public static final String VDB_STATUS_DEPLOYED_NAME = "deployedName";

    /**
     * Label for the version
     */
    public static final String VDB_STATUS_VERSION = "version";

    /**
     * Label for the active state
     */
    public static final String VDB_STATUS_ACTIVE = "active";

    /**
     * Label for the loading state
     */
    public static final String VDB_STATUS_LOADING = "loading";

    /**
     * Label for the failed state
     */
    public static final String VDB_STATUS_FAILED = "failed";

    /**
     * Label for the errors list
     */
    public static final String VDB_STATUS_ERROR = "errors";

    private String name;

    private String deployedName;

    private String version;

    private boolean active;

    private boolean failed;

    private boolean loading;

    private List<String> errors;

    /**
     * Default constructor for deserialization
     */
    public RestMetadataVdbStatusVdb() {
        // do nothing
    }

    public RestMetadataVdbStatusVdb(TeiidVdb vdb) {
        name = vdb.getName();
        deployedName = vdb.getDeployedName();
        version = vdb.getVersion();
        active = vdb.isActive();
        failed = vdb.hasFailed();
        loading = vdb.isLoading();
        errors = vdb.getValidityErrors();
    }

    /**
     * @return <code>true</code> if a VDB exists for this status
     */
    public boolean hasVdb() {
    	return this != RestMetadataVdbStatusVdb.NO_VDB_STATUS;
    }

    @Override
    public boolean supports(MediaType mediaType) {
        return MediaType.APPLICATION_JSON_TYPE.equals(mediaType);
    }

    @Override
    public Object getXml() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeployedName() {
        return deployedName;
    }

    public void setDeployedName(String deployedName) {
        this.deployedName = deployedName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((deployedName == null) ? 0 : deployedName.hashCode());
        result = prime * result + ((errors == null) ? 0 : errors.hashCode());
        result = prime * result + (failed ? 1231 : 1237);
        result = prime * result + (loading ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RestMetadataVdbStatusVdb other = (RestMetadataVdbStatusVdb)obj;
        if (active != other.active)
            return false;
        if (deployedName == null) {
            if (other.deployedName != null)
                return false;
        } else if (!deployedName.equals(other.deployedName))
            return false;
        if (errors == null) {
            if (other.errors != null)
                return false;
        } else if (!errors.equals(other.errors))
            return false;
        if (failed != other.failed)
            return false;
        if (loading != other.loading)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (version != other.version)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RestMetadataVdbStatusVdb [name=" + name + ", deployedName=" + deployedName + ", version=" + version + ", active="
               + active + ", failed=" + failed + ", loading=" + loading + ", errors=" + errors + "]";
    }
}