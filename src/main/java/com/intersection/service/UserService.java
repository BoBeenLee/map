package com.intersection.service;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intersection.model.User;


@Service("userService")
public class UserService {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addUser(User user) {
		em.persist(user);
	}

	@Transactional
	public void removeUserByNo(int no) {

		User user = em.find(User.class, no);
		if (null != user) {
			em.remove(user);
		}
	}

	@Transactional
	public List<User> getUsers(String phoneId) {

		CriteriaQuery<User> c = em.getCriteriaBuilder().createQuery(User.class);
		c.from(User.class);
		Root<User> rm = c.from(User.class);
		c.where(em.getCriteriaBuilder().equal(rm.get("phone_id"), phoneId));

		return em.createQuery(c).getResultList();
	}

	@Transactional
	public User getUser(int no) {

		CriteriaQuery<User> c = em.getCriteriaBuilder().createQuery(User.class);
		Root<User> rm = c.from(User.class);
		c.where(em.getCriteriaBuilder().equal(rm.get("user_no"), no));

		return em.createQuery(c).getSingleResult();
	}
}
