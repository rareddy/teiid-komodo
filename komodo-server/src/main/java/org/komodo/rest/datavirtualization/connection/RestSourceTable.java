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
package org.komodo.rest.datavirtualization.connection;

import java.util.ArrayList;
import java.util.List;

import org.teiid.metadata.Column;
import org.teiid.metadata.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Represents the configuration for a source table
 */
@JsonSerialize(as = RestSourceTable.class)
@JsonInclude(Include.NON_NULL)
public class RestSourceTable {

    /*
     * The table name
     */
    private String name;

    /*
     * The columns for this table
     */
    private RestSourceColumn[] columns;

    /**
     * Constructor for use when de-serializing
     */
    public RestSourceTable(Table table) {
        super();
        this.name = table.getName();
        List<RestSourceColumn> tableColumns = new ArrayList<RestSourceColumn>();
        for( Column column : table.getColumns()) {
            tableColumns.add(new RestSourceColumn(column));
        }
        this.columns = tableColumns.toArray(new RestSourceColumn[0]);
    }

    public String getName() {
        return this.name;
    }

    public RestSourceColumn[] getColumns() {
        return this.columns;
    }
}
