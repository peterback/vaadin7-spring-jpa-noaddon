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
package com.sonoport.service.account;

import java.util.List;

import com.sonoport.model.account.User;

/**
 * User persistency service
 * @author Peter Back
 *
 */
public interface UserService {

	/**
	 * Load the user given by the ID
	 * @param id - The ID to look for
	 * @return fully populated User object
	 */
	public User load(Long id);
	
	/**
	 * Load the user given by the username
	 * @param username - The username to look for
	 * @return fully populated User object
	 */
	public User loadByUserName(String username);
	
	/**
	 * Save the given user
	 * @param user - The User object to save
	 */
	public void save(User user);
	
	/**
	 * List all users in the data-store
	 * @return - List of all User records
	 */
	public List<User> listAll();
	
	/**
	 * Delete the given user
	 * @param user - The User object to delete
	 */
	public void delete(User user);

}
