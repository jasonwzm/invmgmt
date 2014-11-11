
package benchmark.invmgmt.service;

import benchmark.invmgmt.bo.UserBO;
import benchmark.invmgmt.bo.UserBOImpl;
import benchmark.invmgmt.exception.AuthenticationException;
import benchmark.invmgmt.exception.LoginException;

/**
 * LoginServiceImpl
 * @author zwang
 * @version 1.0
 * @filename LoginServiceImpl.java
 */

public final class LoginServiceImpl implements LoginService {

	/**
	 * Automatically generated constructor: LoginServiceImpl
	 * @pre $none
	 * @post $none
	 */
	public LoginServiceImpl () {
	}

	/**
	 * authenticate
	 * @param a_strUserId
	 * @param a_strPassword
	 * @return boolean
	 * @exception LoginException
	 * @pre $none
	 * @post $none
	 */
	public final boolean authenticate (
		String a_strUserId, String a_strPassword)
		throws LoginException {

		boolean boolAuthenticationStatus = false;
		try {
			//authenticate using the same
			UserBO objUserBO = new UserBOImpl();
			boolAuthenticationStatus =
				objUserBO.authenticate(a_strUserId, a_strPassword);
		} catch (AuthenticationException ae) {
			throw new LoginException(ae);
		} catch (Exception e) {
			throw new LoginException(e);
		}
		return boolAuthenticationStatus;
	}

	/**
	 * Automatically generated method: toString
	 *
	 * @return String
	 * @post $none
	 * @pre $none
	 */
	public String toString () {
	    return null;
	}

}
