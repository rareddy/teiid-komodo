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

import java.util.List;

import org.komodo.datavirtualization.DataVirtualization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DataVirtualizationRepository extends JpaRepository<DataVirtualization, String> {

    public DataVirtualization findByName(String name);

    @Query(value = "SELECT name FROM data_virtualization where type like :pattern", nativeQuery = true)
    public List<String> findNamesByTypeLike(@Param("pattern") String pattern);

    @Query(value = "SELECT count(*) FROM data_virtualization where upper_name = :name", nativeQuery = true)
    public long countByUpperName(@Param("name") String name);

    @Query(value = "from DataVirtualization where source_id = :sourceId")
    public DataVirtualization findBySourceId(@Param("sourceId") String sourceId);
}
