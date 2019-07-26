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

package org.komodo.relational;

import org.komodo.relational.dataservice.Dataservice;
import org.komodo.relational.dataservice.ViewEditorState;

public interface WorkspaceManager {
	
	String findSchema(String name);
	
	boolean deleteSchema(String name);
	
	void saveSchema(String name, String contents);
	

	Dataservice createDataservice(String serviceName);

	Dataservice findDataservice(String dataserviceName);

	public Iterable<? extends Dataservice> findDataservices();

	boolean deleteDataservice(String serviceName);
	
	
   ViewEditorState addViewEditorState(  String stateName);

   /**
    * @param namePrefix
    *
    * @return the view editor states (never <code>null</code> but can be empty)
    */
   ViewEditorState[] getViewEditorStates(String namePrefix);

   /**
    * @param viewEditorStateId
    *        the id of the viewEditorState being removed (cannot be empty)
    * @return true if removed
    */
   boolean removeViewEditorState( 
                        final String stateName );

   ViewEditorState getViewEditorState(String stateName);

}
