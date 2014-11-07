/*
 * Copyright 2014 sonoport.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.sonoport.service.account.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sonoport.model.account.User;
import com.sonoport.service.account.UserService;

/**
 * EclipseLink compatible JPA implementation of the User persistency service
 * 
 * @author Peter Back
 *
 */
public class UserServiceImpl implements UserService {
	
	@PersistenceContext(unitName = "JPAService")
	private EntityManager entityManager;
	
	@Override
	public User load(Long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User loadByUserName(String username) {		
		TypedQuery<User> query = entityManager
				.createQuery("select u from User u where u.username = :username", User.class)
				.setParameter("username", username);
		// Force hard refresh, useful if running parallel instances
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		return query.getResultList().get(0);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(User user) {
		if (user.getId() == null) {
			entityManager.persist(user);	
		} else {
			entityManager.merge(user);
		}
	}

	@Override
	public List<User> listAll() {
        TypedQuery<User> query = entityManager.createQuery("select u from User u",User.class);
        // Force hard refresh, useful if running parallel instances
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void delete(User user) {
		// EclipseLink JPA requires a merge() before a remove()
		User toDelete = entityManager.merge(user);
		entityManager.remove(toDelete);
	}

}
