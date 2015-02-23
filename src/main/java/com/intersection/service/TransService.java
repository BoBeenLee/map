package com.intersection.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intersection.model.Translation;

@Service("transService")
public class TransService {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addTranslation(Translation translation) {
		em.persist(translation);
	}

	@Transactional
	public void modifyTranslation(String type, int no) {
		Translation translation = em.find(Translation.class, no);
		translation.setType(type);
	}
	
	@Transactional
	public void removeTranslationByNo(int no) {
		Translation translation = em.find(Translation.class, no);
		if (null != translation) {
			em.remove(translation);
		}
	}

	@Transactional
	public List<Translation> getTranslations(String type) {
		CriteriaQuery<Translation> c = em.getCriteriaBuilder().createQuery(Translation.class);
		c.from(Translation.class);
		Root<Translation> rm = c.from(Translation.class);
		c.where(em.getCriteriaBuilder().equal(rm.get("type"), type));

		return em.createQuery(c).getResultList();
	}

	@Transactional
	public List<Translation> getTranslationByName(String name) {
		 CriteriaBuilder criteriaBuilder=em.getCriteriaBuilder();
		
		List<Translation> result = null;
		CriteriaQuery<Translation> c = criteriaBuilder.createQuery(Translation.class);
		Root<Translation> rm = c.from(Translation.class);
		c.where(criteriaBuilder.like(rm.<String>get("name"), "%" + name + "%")); 

		try {
			result =em.createQuery(c).getResultList();
		} catch(NoResultException nre){
			result = null;
		}
		return result;
	}
	
	@Transactional
	public Translation getTranslationByNo(int no) {
		CriteriaQuery<Translation> c = em.getCriteriaBuilder().createQuery(Translation.class);
		Root<Translation> rm = c.from(Translation.class);
		c.where(em.getCriteriaBuilder().equal(rm.get("transNo"), no));

		return em.createQuery(c).getSingleResult();
	}
}
