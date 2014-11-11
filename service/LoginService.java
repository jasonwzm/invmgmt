
package benchmark.invmgmt.service;

import benchmark.invmgmt.exception.LoginException;

/**
 * LoginService
 * @author zwang
 * @version 1.0
 * @filename LoginService.java
 */
public interface LoginService {
	/**
	 * authenticate
	 * @param a_strUserId
	 * @param a_strPassword
	 * @return boolean
	 * @exception LoginException
	 * @pre $none
	 * @post $none
	 */
	public abstract boolean authenticate(
		String a_strUserId,
		String a_strPassword)
		throws LoginException;
}