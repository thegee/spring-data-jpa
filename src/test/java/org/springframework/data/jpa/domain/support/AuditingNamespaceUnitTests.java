/*
 * Copyright 2008-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.jpa.domain.support;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * Unit test for the Hades {@code auditing} namespace element.
 * 
 * @author Oliver Gierke
 */
public class AuditingNamespaceUnitTests extends
        AuditingBeanFactoryPostProcessorUnitTests {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.hades.domain.auditing.AuditingBeanFactoryPostProcessorUnitTest
     * #getConfigFile()
     */
    @Override
    protected String getConfigFile() {

        return "auditing-namespace-context.xml";
    }


    @Test
    public void registersBeanDefinitions() throws Exception {

        BeanDefinition definition =
                beanFactory.getBeanDefinition(AuditingEntityListener.class
                        .getName());
        assertEquals(
                definition.getPropertyValues().getPropertyValue("auditorAware")
                        .getValue(), new RuntimeBeanReference("auditorAware"));
    }
}