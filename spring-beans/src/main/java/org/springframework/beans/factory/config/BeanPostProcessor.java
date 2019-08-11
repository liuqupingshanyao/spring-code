/*
 * Copyright 2002-2016 the original author or authors.
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

package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
 * BeanPostProcessor是Spring框架的提供的一个扩展类点（不止一个）
 * 通过实现BeanPostProcessor接口，程序员就可插手bean实例化的过程,从而减轻了beanFactory的负担
 * 值得说明的是这个接口可以设置多个，会形成一个列表，然后依次执行
 * (但是spring默认的怎么办？set)
 * 比如AOP就是在bean实例后期间将切面逻辑织入bean实例中的
 * AOP也正是通过BeanPostProcessor和IOC容器建立起了联系
 * （由spring提供的默认的PostPorcessor,spring提供了很多默认的PostProcessor,下面我会一一介绍这些实现类的功能）
 * 可以来演示一下 BeanPostProcessor 的使用方式（把动态代理和IOC、aop结合起来使用）
 * 在演示之前先来熟悉一下这个接口，其实这个接口本身特别简单，简单到你发指
 * 但是他的实现类特别复杂，同样复杂到发指！
 * 可以看看spring提供哪些默认的实现（前方高能）
 * 查看类的关系图可以知道spring提供了以下的默认实现，因为高能，故而我们只是解释几个常用的
 * 1、ApplicationContextAwareProcessor （acap）
 *    acap后置处理器的作用是，当应用程序定义的Bean实现ApplicationContextAware接口时注入ApplicationContext对象
 *    当然这是他的第一个作业，他还有其他作用，这里不一一列举了，可以参考源码
 *    我们可以针对ApplicationContextAwareProcessor写一个栗子
 *  2、InitDestroyAnnotationBeanPostProcessor
 *     用来处理自定义的初始化方法和销毁方法
 *    上次说过Spring中提供了3种自定义初始化和销毁方法分别是
 *    一、通过@Bean指定init-method和destroy-method属性
 *    二、Bean实现InitializingBean接口和实现DisposableBean
 *    三、@PostConstruct：@PreDestroy
 *    为什么spring通这三种方法都能完成对bean生命周期的回调呢？
 *    可以通过InitDestroyAnnotationBeanPostProcessor的源码来解释
 *  3、InstantiationAwareBeanPostProcessor
 *  4、CommonAnnotationBeanPostProcessor
 *  5、AutowiredAnnotationBeanPostProcessor
 *  6 、RequiredAnnotationBeanPostProcessor
 *  7、BeanValidationPostProcessor
 *  8、AbstractAutoProxyCreator
 *  ......
 *  后面会一一解释
 *
 *
 *
 *
 *
 * Factory hook that allows for custom modification of new bean instances,
 * e.g. checking for marker interfaces or wrapping them with proxies.
 * <p>ApplicationContexts can autodetect BeanPostProcessor beans in their
 * bean definitions and apply them to any beans subsequently created.
 * Plain bean factories allow for programmatic registration of post-processors,
 * applying to all beans created through this factory.
 *
 * <p>Typically, post-processors that populate beans via marker interfaces
 * or the like will implement {@link #postProcessBeforeInitialization},
 * while post-processors that wrap beans with proxies will normally
 * implement {@link #postProcessAfterInitialization}.
 *
 * @author Juergen Hoeller
 * @since 10.10.2003
 * @see InstantiationAwareBeanPostProcessor
 * @see DestructionAwareBeanPostProcessor
 * @see ConfigurableBeanFactory#addBeanPostProcessor
 * @see BeanFactoryPostProcessor
 */
public interface BeanPostProcessor {

	/**
	 * 在bean初始化之前执行
	 * Apply this BeanPostProcessor to the given new bean instance <i>before</i> any bean
	 * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
	 * or a custom init-method). The bean will already be populated with property values.
	 * The returned bean instance may be a wrapper around the original.
	 * <p>The default implementation returns the given {@code bean} as-is.
	 * @param bean the new bean instance
	 * @param beanName the name of the bean
	 * @return the bean instance to use, either the original or a wrapped one;
	 * if {@code null}, no subsequent BeanPostProcessors will be invoked
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
	 */
	@Nullable
	default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * 初始化之后
	 * Apply this BeanPostProcessor to the given new bean instance <i>after</i> any bean
	 * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
	 * or a custom init-method). The bean will already be populated with property values.
	 * The returned bean instance may be a wrapper around the original.
	 * <p>In case of a FactoryBean, this callback will be invoked for both the FactoryBean
	 * instance and the objects created by the FactoryBean (as of Spring 2.0). The
	 * post-processor can decide whether to apply to either the FactoryBean or created
	 * objects or both through corresponding {@code bean instanceof FactoryBean} checks.
	 * <p>This callback will also be invoked after a short-circuiting triggered by a
	 * {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation} method,
	 * in contrast to all other BeanPostProcessor callbacks.
	 * <p>The default implementation returns the given {@code bean} as-is.
	 * @param bean the new bean instance
	 * @param beanName the name of the bean
	 * @return the bean instance to use, either the original or a wrapped one;
	 * if {@code null}, no subsequent BeanPostProcessors will be invoked
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
	 * @see org.springframework.beans.factory.FactoryBean
	 */
	@Nullable
	default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
