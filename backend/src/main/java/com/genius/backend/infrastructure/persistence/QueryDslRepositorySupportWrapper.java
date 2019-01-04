package com.genius.backend.infrastructure.persistence;

import com.querydsl.core.dml.DeleteClause;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class QueryDslRepositorySupportWrapper {

	private final PathBuilder<?> builder;

	private EntityManager entityManager;
	private Querydsl querydsl;


	public QueryDslRepositorySupportWrapper(Class<?> domainClass) {
		Assert.notNull(domainClass);
		this.builder = new PathBuilderFactory().create(domainClass);
	}

	@PersistenceContext(unitName = "entityManagerFactory")
	public void setEntityManager(EntityManager entityManager) {
		Assert.notNull(entityManager);
		this.querydsl = new Querydsl(entityManager, builder);
		this.entityManager = entityManager;
	}

	@PostConstruct
	public void validate() {
		Assert.notNull(entityManager, "EntityManager must not be null!");
		Assert.notNull(querydsl, "Querydsl must not be null!");
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	protected JPQLQuery from(EntityPath<?>... paths) {
		return querydsl.createQuery(paths);
	}

	protected DeleteClause<JPADeleteClause> delete(EntityPath<?> path) {
		return new JPADeleteClause(entityManager, path);
	}

	protected UpdateClause<JPAUpdateClause> update(EntityPath<?> path) {
		return new JPAUpdateClause(entityManager, path);
	}

	@SuppressWarnings("unchecked")
	protected <T> PathBuilder<T> getBuilder() {
		return (PathBuilder<T>) builder;
	}

	protected Querydsl getQuerydsl() {
		return this.querydsl;
	}
}
