
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.UserDetail;
import benchmark.invmgmt.exception.AuthenticationException;
import benchmark.invmgmt.exception.AuthenticationFailureException;
import benchmark.invmgmt.helper.LogHelper;
import java.util.List;

import javax.naming.NamingException;


/**
 * AuthenticationDAO
 *
 * @author zwang
 * @version 1.0
 * @filename AuthenticationDAO.java
 */
public final class AuthenticationDAO implements Authentication {

    private static LogHelper logger = new LogHelper(AuthenticationDAO.class);
    private LdapOperation ldaputil = new LdapOperationDAO();

    public AuthenticationDAO() {
    }

    /**
     * authenticateUser
     *
     * @param a_strUserPassword
     * @return
     * @throws AuthenticationException
     * @throws AuthenticationFailureException boolean
     * @post $none
     * @pre $none
     */
    public final boolean authenticateUser(String a_strUserPassword)
            throws AuthenticationException, AuthenticationFailureException {
        try {
            ldaputil.bind(a_strUserPassword);
            return true;
        } catch (NamingException ne) {
            throw new AuthenticationFailureException(ne);
        } catch (Exception e) {
            throw new AuthenticationException(e, "Exception in AuthenticationDAO::authenticateUser");
        }
    }

    /**
     * findUsers
     *
     * @param a_strUserEmailId
     * @return
     * @throws AuthenticationException ArrayList
     * @pre $none
     * @post $none
     */
    public final List findUsers(String a_strUserEmailId)
            throws AuthenticationException {
        try {
            return ldaputil.search(a_strUserEmailId);
        } catch (NamingException ne) {
            logger.error("error is " + ne.getExplanation());
            throw new AuthenticationException(ne, "Naming Exception in AuthenticationDAO::findUsers ");
        } catch (Exception e) {
            logger.error("Exception in authentication dao is " + e.getMessage());
            throw new AuthenticationException(e, "Exception in AuthenticationDAO::findUsers");
        }
    }

    public final UserDetail findAppUser(String a_strUserId) throws AuthenticationException {
        UserDetail userDetail = null;
        //put in code to get the user information from the inventory
        UserDetailDAO userDetailDao = new UserDetailDAO();
        try {
            userDetail = (UserDetail) userDetailDao.getUserByName(a_strUserId);
            if (userDetail == null) {
                throw new Exception("User is not a member in the application. <" + a_strUserId + ">");
            } else if (userDetail.getActive() == 'N') {
                throw new Exception("User is not an ACTIVE member in the application, user is <" + a_strUserId + ">");
            }
        } catch (Exception e) {
            logger.error("Exception in userDetails dao is " + e.getMessage());
            throw new AuthenticationException(e, "Exception in AuthenticationDAO::findAppUser");
        }

        return userDetail;
    }

    /**
     * Automatically generated method: toString
     *
     * @return String
     * @pre $none
     * @post $none
     */
    public String toString() {
        return null;
    }
}