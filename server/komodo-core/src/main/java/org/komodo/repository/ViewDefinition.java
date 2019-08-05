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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.komodo.StringConstants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Represents the configuration of a view editor state
 */
@Entity
@JsonSerialize(as = ViewDefinition.class)
@JsonInclude(Include.NON_NULL)
public class ViewDefinition implements org.komodo.datavirtualization.ViewDefinition {
	
	public static class State {
		private List<SqlComposition> compositions = new ArrayList<>(1);
		private List<SqlProjectedColumn> projectedColumns = new ArrayList<>(1);
		private List<String> sourcePaths = new ArrayList<>(1);
		
		public List<SqlComposition> getSqlCompositions() {
			return compositions;
		}
		public void setSqlCompositions(List<SqlComposition> sqlCompositions) {
			this.compositions = sqlCompositions;
		}
		public List<SqlProjectedColumn> getProjectedColumns() {
			return projectedColumns;
		}
		public void setProjectedColumns(List<SqlProjectedColumn> projectedColumns) {
			this.projectedColumns = projectedColumns;
		}
		public List<String> getSourcePaths() {
			return sourcePaths;
		}
		public void setSourcePaths(List<String> sourcePaths) {
			this.sourcePaths = sourcePaths;
		}
	}
	
	public static class ViewDefinitionStateConvertor extends JpaConverterJson {
		@Override
		protected Class<?> targetClass() {
			return State.class;
		}
	}

	@Id
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	@JsonProperty(value = "viewName")
	@Column(unique=true)
	private String name;
	private String ddl;
	@Column(name = "dv_name")
	private String dataVirtualizationName;
	@JsonProperty(value = StringConstants.DESCRIPTION_FIELD_NAME)
	private String description;
	@JsonProperty(value = "isComplete")
	private boolean complete;
	@JsonProperty(value = "isUserDefined")
	private boolean userDefined;
	
	@JsonIgnore //for non-Entity serialization, the getters/setters will be used
	@Convert(converter = ViewDefinitionStateConvertor.class)
	private State state = new State();
	
	protected ViewDefinition() {
	}
	
	public ViewDefinition(String dataVirtualizationName, String name) {
		this.name = name;
		this.dataVirtualizationName = dataVirtualizationName;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public SqlComposition addComposition(String compositionName) {
		org.komodo.repository.SqlComposition sqlComposition = new org.komodo.repository.SqlComposition(compositionName);
		state.compositions.add(sqlComposition);
		return sqlComposition;
	}

	@Override
	public List<org.komodo.datavirtualization.SqlComposition> getCompositions() {
		return new ArrayList<>(state.compositions);
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getDdl() {
		return this.ddl;
	}

	@Override
	public void setDdl(String ddl) {
		this.ddl = ddl;
	}

	@Override
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@Override
	public boolean isComplete() {
		return this.complete;
	}

	@Override
	public void setUserDefined(boolean userDefined) {
		this.userDefined = userDefined;
	}

	@Override
	public boolean isUserDefined() {
		return this.userDefined;
	}

	@Override
	public List<String> getSourcePaths() {
		return state.sourcePaths;
	}

	@Override
	public void addSourcePath(String sourcePath) {
		this.getSourcePaths().add(sourcePath);
	}

	@Override
	public SqlProjectedColumn addProjectedColumn(String columnName) {
		org.komodo.repository.SqlProjectedColumn sqlProjectedColumn = new org.komodo.repository.SqlProjectedColumn(columnName);
		this.state.projectedColumns.add(sqlProjectedColumn);
		return sqlProjectedColumn;
	}

	@Override
	public List<org.komodo.datavirtualization.SqlProjectedColumn> getProjectedColumns() {
		return new ArrayList<>(state.projectedColumns);
	}
	
	public void setCompositions(List<SqlComposition> sqlCompositions) {
		state.compositions = sqlCompositions;
	}
	
	public void setSourcePaths(List<String> sourcePaths) {
		state.sourcePaths = sourcePaths;
	}
	
	public String getDataVirtualizationName() {
		return dataVirtualizationName;
	}
	
	public void setDataVirtualizationName(String dataVirtualizationName) {
		this.dataVirtualizationName = dataVirtualizationName;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setProjectedColumns(List<SqlProjectedColumn> projectedColumns) {
		this.state.projectedColumns = projectedColumns;
	}
	
	@Override
	public void clearState() {
		this.state = new State();
	}

}
