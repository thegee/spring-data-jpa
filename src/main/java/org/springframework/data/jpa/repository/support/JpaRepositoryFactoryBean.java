/*
 * Copyright 2008-2010 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.springframework.data.jpa.repository.support;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.support.RepositoryFactorySupport;
import org.springframework.util.Assert;


/**
 * Special adapter for Springs {@link FactoryBean} interface to allow easy setup
 * of repository factories via Spring configuration.
 * 
 * @author Oliver Gierke
 * @author Eberhard Wolff
 * @param <T> the type of the repository
 */
public class JpaRepositoryFactoryBean<T extends JpaRepository<?, ?>> extends
        RepositoryFactoryBeanSupport<T> {

    private EntityManager entityManager;


    public static <S extends JpaRepository<?, ?>> JpaRepositoryFactoryBean<S> create(
            Class<S> repositoryInterface, EntityManager em) {

        JpaRepositoryFactoryBean<S> factory = new JpaRepositoryFactoryBean<S>();
        factory.setRepositoryInterface(repositoryInterface);
        factory.setEntityManager(em);

        return factory;
    }


    /**
     * The {@link EntityManager} to be used.
     * 
     * @param entityManager the entityManager to set
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {

        this.entityManager = entityManager;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.repository.support.RepositoryFactoryBeanSupport
     * #createRepositoryFactory()
     */
    @Override
    protected RepositoryFactorySupport<?> createRepositoryFactory() {

        return createRepositoryFactory(entityManager);
    }


    /**
     * Returns a {@link RepositoryFactorySupport}.
     * 
     * @param entityManager
     * @return
     */
    protected RepositoryFactorySupport<?> createRepositoryFactory(
            EntityManager entityManager) {

        return new JpaRepositoryFactory(entityManager);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() {

        Assert.notNull(entityManager, "EntityManager must not be null!");
        super.afterPropertiesSet();
    }
}