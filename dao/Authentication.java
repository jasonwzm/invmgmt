
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.UserDetail;
import benchmark.invmgmt.exception.AuthenticationException;
import benchmark.invmgmt.exception.AuthenticationFailureException;
import java.util.List;

/**
 * Authentication
 * @author zwang
 * @version 1.0
 * @filename Authentication.java
 */
public interface Authentication {
	/**
	 * authenticateUser
	 * @param a_strUserPassword
	 * @return
	 * @throws AuthenticationException
	 * @throws AuthenticationFailureException
	 * boolean
	 * @post $none
	 * @pre $none
	 */
	boolean authenticateUser (String a_strUserPassword) throws AuthenticationException, AuthenticationFailureException;
	/**
	 * findUsers
	 * @param a_strUserEmailId
	 * @return
	 * @throws AuthenticationException
	 * ArrayList
	 * @pre $none
	 * @post $none
	 */
	List findUsers (String a_strUserEmailId) throws AuthenticationException;
        
        /*
         * find the user in the application db.
         */
        UserDetail findAppUser(String a_strUserId)throws AuthenticationException;
}