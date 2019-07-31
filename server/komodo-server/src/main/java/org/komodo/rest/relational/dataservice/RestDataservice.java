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
package org.komodo.rest.relational.dataservice;

import java.util.ArrayList;
import java.util.List;

import org.komodo.KException;
import org.komodo.WorkspaceManager;
import org.komodo.datavirtualization.DataVirtualization;
import org.komodo.datavirtualization.ViewDefinition;
import org.komodo.openshift.BuildStatus;
import org.komodo.openshift.KomodoType;
import org.komodo.rest.RestBasicEntity;

/**
 * A Dataservice that can be used by GSON to build a JSON document representation.
 */
public final class RestDataservice extends RestBasicEntity {

    /**
     * Label used to describe description
     */
    public static final String DESCRIPTION_LABEL = "tko__description";

    /**
     * Label used to describe dataservice view modelName
     */
    public static final String DATASERVICE_VIEW_MODEL_LABEL = "serviceViewModel"; //$NON-NLS-1$

    /**
     * Label used to describe dataservice viewNames
     */
    public static final String DATASERVICE_VIEW_DEFINITIONS_LABEL = "serviceViewDefinitions"; //$NON-NLS-1$

    /**
     * Label used to describe dataservice vdbName
     */
    public static final String DATASERVICE_VDB_NAME_LABEL = "serviceVdbName"; //$NON-NLS-1$

    /**
     * Label used to describe dataservice vdbVersion
     */
    public static final String DATASERVICE_VDB_VERSION_LABEL = "serviceVdbVersion"; //$NON-NLS-1$

    /**
     * Label used to describe dataservice connection total
     */
    public static final String DATASERVICE_CONNECTION_TOTAL_LABEL = "connections"; //$NON-NLS-1$

    /**
     * Label used to describe dataservice published state
     */
    public static final String DATASERVICE_PUBLISHED_STATE_LABEL = "publishedState"; //$NON-NLS-1$
    
    /**
     * Label used to describe dataservice pod namespace
     */
    public static final String DATASERVICE_POD_NAMESPACE = "podNamespace"; //$NON-NLS-1$

    /**
     * Label used to describe dataservice publish pod name
     */
    public static final String DATASERVICE_PUBLISH_POD_NAME = "publishPodName"; //$NON-NLS-1$

    /**
     * Label used to describe dataservice odata host name
     */
    public static final String DATASERVICE_ODATA_HOST_NAME = "odataHostName"; //$NON-NLS-1$

    /**
     * Constructor for use when deserializing
     */
    public RestDataservice() {
        super();
        setkType(KomodoType.DATASERVICE);
    }

    /**
     * Constructor for use when serializing.
     * @param dataService the dataService
     * @param exportXml whether xml should be exported
     * @throws KException if error occurs
     */
    public RestDataservice(DataVirtualization dataService, boolean exportXml, String vdbName) throws KException {
        setId(dataService.getName());
        setkType(KomodoType.DATASERVICE);

        setDescription(dataService.getDescription());

        setServiceVdbName(vdbName);
        setHasChildren(true);

        // Initialize the published state to NOTFOUND
        setPublishedState(BuildStatus.Status.NOTFOUND.name());
    }

    /**
     * @return the VDB description (can be empty)
     */
    public String getDescription() {
        Object description = tuples.get(DESCRIPTION_LABEL);
        return description != null ? description.toString() : null;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        tuples.put(DESCRIPTION_LABEL, description);
    }

    /**
     * @return the service view model name (can be empty)
     */
    public String getServiceViewModel() {
        Object modelName = tuples.get(DATASERVICE_VIEW_MODEL_LABEL);
        return modelName != null ? modelName.toString() : null;
    }

    /**
     * @param modelName the view model name to set
     */
    public void setServiceViewModel(String modelName) {
        tuples.put(DATASERVICE_VIEW_MODEL_LABEL, modelName);
    }

    /**
     * @return the service ViewDefinition names (can be empty)
     */
    public String[] getViewDefinitionNames() {
        return (String[])tuples.get(DATASERVICE_VIEW_DEFINITIONS_LABEL);
    }

    /**
     * @param viewDefinitionNames the service view names to set
     */
    public void setViewDefinitionNames(final String[] viewDefinitionNames) {
        tuples.put(DATASERVICE_VIEW_DEFINITIONS_LABEL, viewDefinitionNames);
    }

    /**
     * @return the service vdb name (can be empty)
     */
    public String getServiceVdbName() {
        Object serviceVdbName = tuples.get(DATASERVICE_VDB_NAME_LABEL);
        return serviceVdbName != null ? serviceVdbName.toString() : null;
    }

    /**
     * @param serviceVdbName the service vdb name to set
     */
    public void setServiceVdbName(String serviceVdbName) {
        tuples.put(DATASERVICE_VDB_NAME_LABEL, serviceVdbName);
    }

    /**
     * @return the service vdb version (can be empty)
     */
    public String getServiceVdbVersion() {
        Object version = tuples.get(DATASERVICE_VDB_VERSION_LABEL);
        return version != null ? version.toString() : "1"; //$NON-NLS-1$
    }

    /**
     * @param version the version to set
     */
    public void setServiceVdbVersion( final String version) {
        tuples.put(DATASERVICE_VDB_VERSION_LABEL, version);
    }

    /**
     * @return the service published state (never empty)
     */
    public String getPublishedState() {
        Object publishedState = tuples.get(DATASERVICE_PUBLISHED_STATE_LABEL);
        return publishedState != null ? publishedState.toString() : null;
    }
    
    /**
     * @param publishedState the published state
     */
    public void setPublishedState(String publishedState) {
        tuples.put(DATASERVICE_PUBLISHED_STATE_LABEL, publishedState);
    }

    /**
     * @return the pod namespace (can be empty)
     */
    public String getPodNamespace() {
        Object podNamespace = tuples.get(DATASERVICE_POD_NAMESPACE);
        return podNamespace != null ? podNamespace.toString() : null;
    }

    /**
     * @param podNamesapce the service pod namespace to set
     */
    public void setPodNamespace(String podNamespace) {
        tuples.put(DATASERVICE_POD_NAMESPACE, podNamespace);
    }

    /**
     * @return the service pod name (can be empty)
     */
    public String getPublishPodName() {
        Object publishPodName = tuples.get(DATASERVICE_PUBLISH_POD_NAME);
        return publishPodName != null ? publishPodName.toString() : null;
    }

    /**
     * @param publishPodName the service pod name to set
     */
    public void setPublishPodName(String publishPodName) {
        tuples.put(DATASERVICE_PUBLISH_POD_NAME, publishPodName);
    }
    
    /**
     * @return the service pod name (can be empty)
     */
    public String getOdataHostName() {
        Object odataHostName = tuples.get(DATASERVICE_ODATA_HOST_NAME);
        return odataHostName != null ? odataHostName.toString() : null;
    }

    /**
     * @param publishPodName the service pod name to set
     */
    public void setOdataHostName(String odataHostName) {
        tuples.put(DATASERVICE_ODATA_HOST_NAME, odataHostName);
    }
    
    /**
     *  get the ViewDefinitionImpl names for the dataservice
     */
    public static String[] getViewDefnNames(WorkspaceManager workspaceManager, String vdbName) throws KException {
        ViewDefinition[] editorStates = null;
    	String svcVdbName = vdbName.toLowerCase();
        editorStates = workspaceManager.getViewDefinitions(svcVdbName);

        List<String> viewNames = new ArrayList<String>();
        for (ViewDefinition editorState: editorStates) {
        	viewNames.add(editorState.getViewName());
        }
        
        return viewNames.toArray(new String[0]);
    }
    
}
