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
package org.komodo.rest.datavirtualization;

import org.komodo.rest.datavirtualization.connection.RestSourceSchema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Represents the configuration for the view's source info
 */
@JsonSerialize(as = RestViewSourceInfo.class)
@JsonInclude(Include.NON_NULL)
public class RestViewSourceInfo {

    /*
     * The array of source schemas
     */
    private RestSourceSchema[] schemas = new RestSourceSchema[0];

    public RestViewSourceInfo(RestSourceSchema[] sourceSchemas) {
        this.schemas = sourceSchemas;
    }

    /**
     * @return the projected columns
     */
    public RestSourceSchema[] getSchemas() {
        return schemas;
    }
}
