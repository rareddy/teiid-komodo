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

import java.util.concurrent.Callable;

import org.komodo.KEngine;
import org.komodo.KException;
import org.komodo.WorkspaceManager;
import org.komodo.utils.KLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Provides the repository facade and transaction handling
 *
 * In many of the rollback/read only scenarios, a transaction is not necessary
 *
 * Eventually we'll probably replace with the Transactional annotation
 */
@Component
public class KEngineImpl implements KEngine {

    protected static final KLog LOGGER = KLog.getLogger();

    public static final String REPO_USER = "anonymous"; //$NON-NLS-1$

    private static DefaultTransactionDefinition NEW_TRANSACTION_DEFINITION = new DefaultTransactionDefinition();
    static {
        NEW_TRANSACTION_DEFINITION.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    @Autowired
    private WorkspaceManagerImpl workspaceManagerImpl;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    public void start() throws Exception {
        this.workspaceManagerImpl.findDataVirtualization("x"); //$NON-NLS-1$
    }

    @Override
    public WorkspaceManager getWorkspaceManager() throws KException {
        return workspaceManagerImpl;
    }

    @Override
    public <T> T runInTransaction(boolean rollbackOnly, Callable<T> callable) throws Exception {
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(NEW_TRANSACTION_DEFINITION);
        if (rollbackOnly) {
            transactionStatus.setRollbackOnly();
        }
        String txnName = null;
        if (LOGGER.isDebugEnabled()) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            txnName = stackTraceElements[2].getMethodName();
        }
        LOGGER.debug( "createTransaction:created '%s', rollbackOnly = '%b'", txnName, rollbackOnly ); //$NON-NLS-1$
        try {
            T result = callable.call();
            if (!rollbackOnly) {
                try {
                    platformTransactionManager.commit(transactionStatus);
                    LOGGER.debug( "commit: successfully committed '%s'", //$NON-NLS-1$
                            txnName);
                } catch (TransactionTimedOutException e) {
                    throw new TimeoutException(e);
                }
                //any other exceptions can be unchecked
            }
            return result;
        } finally {
            if (!transactionStatus.isCompleted()) {
                platformTransactionManager.rollback(transactionStatus);
            }
        }
    }

}
