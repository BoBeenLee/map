package com.intersection.service;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hsqldb.lib.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intersection.model.Like;
import com.intersection.model.Translation;

@Service("likeService")
public class LikeService {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addLike(String phoneId, int transNo) {
		Query query = em.createQuery("insert into Like(userNo, transNo)" + " select userNo, :transNo" + " from User u"
				+ " where u.phoneId = :phoneId");
		query.setParameter("phoneId", phoneId);
		query.setParameter("transNo", transNo);

		query.executeUpdate();
	}

	@Transactional
	public void removeLikeByNo(int no) {
		Like person = em.find(Like.class, no);
		if (null != person) {
			em.remove(person);
		}
	}

	@Transactional
	public List<Like> getLikesByUserNo(int userNo) {
		CriteriaQuery<Like> c = em.getCriteriaBuilder().createQuery(Like.class);
		c.from(Like.class);
		Root<Like> rm = c.from(Like.class);
		c.where(em.getCriteriaBuilder().equal(rm.get("userNo"), userNo));

		return em.createQuery(c).getResultList();
	}

	@Transactional
	public Like getLikesByPhoneId(String phoneId, int transNo) {
		TypedQuery<Like> query = em.createQuery("select L" + " from Like L, User U"
				+ " where L.userNo = U.userNo and U.phoneId = :phoneId and L.transNo = :transNo", Like.class);

		query.setParameter("phoneId", phoneId);
		query.setParameter("transNo", transNo);

		Like like = null;

		try {
			like = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

		return like;
	}

	@Transactional
	public List<Translation> getLikesByNames(List<String> names, String phoneId) {
		Query query = em
				.createNativeQuery("select trans.trans_no, trans.name, trans.type, (select count(*) from is_trans_like_db where trans_no = trans.trans_no) as like_count,"
						+ " (phone_id is not null) as like_status from is_trans_db trans left outer join is_trans_like_db trans_like"
						+ " on trans_like.trans_no = trans.trans_no"
						+ " left outer join is_user_db usr"
						+ " on trans_like.user_no = usr.user_no and usr.phone_id = :phoneId"
						+ " where trans.name IN (:names)" + " and usr.phone_id = :phoneId" + " order by like_count;");
		query.setParameter("names", names);
		query.setParameter("phoneId", phoneId);

		List<Object[]> list = query.getResultList();
		List<Translation> transList = new ArrayList<Translation>();

		for (int i = 0; i < list.size(); i++) {
			Translation translation = new Translation();

			translation.setSeq((i + 1));
			translation.setTransNo((Integer) list.get(i)[0]);
			translation.setName((String) list.get(i)[1]);
			translation.setType((String) list.get(i)[2]);
			translation.setLikeCount(((BigInteger) list.get(i)[3]).intValue());
			translation.setLikeStatus((Boolean) list.get(i)[4]);

			transList.add(translation);
		}

		// "select trans.trans_no, trans.name, trans.type, count(trans_like.trans_no) as like_count"
		// + " from Like trans_like join Translation trans"
		// + " on trans_like.trans_no = trans.trans_no"
		// + " where trans.name IN (:names)"
		// + " group by trans.trans_no, trans.name, trans.type"
		// + " order by like_count");
		// query.setParameter("names", names);
		return transList;
	}

	/*
	 * List<Long> ids = Arrays.asList(380L, 382L, 386L); Query query =
	 * em.createQuery("FROM TrackedItem item WHERE item.id IN (:ids)");
	 * query.setParameter("ids", ids) List<TrackedItem> items =
	 * query.getResultList();
	 */

	@Transactional
	public Like getLike(int no) {
		CriteriaQuery<Like> c = em.getCriteriaBuilder().createQuery(Like.class);
		Root<Like> rm = c.from(Like.class);
		c.where(em.getCriteriaBuilder().equal(rm.get("likeNo"), no));
		return em.createQuery(c).getSingleResult();
	}
}
