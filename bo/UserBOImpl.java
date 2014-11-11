
package benchmark.invmgmt.bo;

import benchmark.invmgmt.dao.Authentication;
import benchmark.invmgmt.dao.AuthenticationDAO;
import benchmark.invmgmt.dao.UserDetailDAO;
import benchmark.invmgmt.data.UserDetail;
import benchmark.invmgmt.data.UserDetails;
import benchmark.invmgmt.exception.AuthenticationException;
import benchmark.invmgmt.exception.AuthenticationFailureException;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.UserException;
import benchmark.invmgmt.helper.LogHelper;
import java.util.List;


/**
 * UserBOImpl
 *
 * @author zwang
 * @version 1.0
 * @filename UserBOImpl.java
 */
public final class UserBOImpl implements UserBO {

    /**
     * Logger
     */
    private static LogHelper logger = new LogHelper(UserBOImpl.class);

    /**
     * Automatically generated constructor: UserBOImpl
     */
    public UserBOImpl() {
    }

    /**
     * authenticate
     */
    public boolean authenticate(String a_strUserId, String a_strPassword)
            throws AuthenticationException {
        //init the local variables
        List listUsers = null;
        UserDetail userDetailItem = null;
        boolean isAuthenticated = false;

        //get the authentication access object
        Authentication authenticationdao = new AuthenticationDAO();
        logger.debug("contacting ldap");

        //get the list of users matching the details provided
        listUsers = authenticationdao.findUsers(a_strUserId);
        logger.debug(
                "got the list of users and the count is " + listUsers.size());

        //check if there is an exact match
        if (listUsers.size() != 1) {
            return false;
        }

        try {
            // if there is a match try connecting with the password to confirm the credentials
            // Ldap authentication
            isAuthenticated = authenticationdao.authenticateUser(a_strPassword);
            // Lets see if this user has an id in the application.
            if (isAuthenticated) {
                userDetailItem = authenticationdao.findAppUser(a_strUserId);
                if (userDetailItem == null) {
                    isAuthenticated = false;
                    logger.error("Application authentication failed for " + a_strUserId);
                }
            }
        } catch (AuthenticationFailureException afe) {
            logger.error("Authentication failed " + afe.getMessage());
            return false;
        } catch (Exception ex) {
            logger.error("Authentication failed " + ex.getMessage());
            return false;
        }
        return true;

    }

    // userDetail - Returns user detail from userid
    public UserDetail userDetail(String a_strUserId) throws UserException {
        UserDetail udetail = null;

        try {
            UserDetailDAO udetailDao = new UserDetailDAO();
            udetail = (UserDetail) udetailDao.getUserByName(a_strUserId);

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return udetail;
    }

    // deleteUser - Deletes user given a userID
    public boolean deleteUser(String a_strUserId) throws UserException {

        boolean deleted = false;

        try {
            
            UserDetail userDetail = new UserDetail();
            userDetail.setUserId(a_strUserId);

            UserDetailDAO userDAO = new UserDetailDAO();
            deleted = userDAO.delete(userDetail);

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return deleted;
    }

    // insertUser - Insert user given a userID
    public int insertUser(UserDetail a_userDetail) throws UserException {

        int usersInserted = 0;

        try {

            UserDetailDAO userDAO = new UserDetailDAO();
            usersInserted = userDAO.insert(a_userDetail);

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return usersInserted;
    }

    // updateUser - Updates user given a userID
    public boolean updateUser(UserDetail a_userDetail) throws UserException {

        boolean updated = false;

        try {

            UserDetailDAO userDAO = new UserDetailDAO();
            updated = userDAO.update(a_userDetail);

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return updated;
    }
    
    // Returns user details for all users in the system
    public UserDetails getAllUsers() throws UserException {
        UserDetails userDetails = null;
        
         try {

            UserDetailDAO userDAO = new UserDetailDAO();
            userDetails = (UserDetails) userDAO.list(null);

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return userDetails;
        
    }

    // Returns list of users who receive notifications for New Components
    public UserDetails getComponentNewNotifyIds() throws UserException {
        UserDetails userDetails = null;
        
         try {

            UserDetailDAO userDAO = new UserDetailDAO();
            userDetails = (UserDetails) userDAO.getComponentNewNotifyIds();

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return userDetails;
        
    }

    // Returns list of users who receive notifications for Updated Components
    public UserDetails getComponentUpdateNotifyIds() throws UserException {
        UserDetails userDetails = null;
        
         try {

            UserDetailDAO userDAO = new UserDetailDAO();
            userDetails = (UserDetails) userDAO.getComponentUpdateNotifyIds();

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return userDetails;
        
    }

    // Returns list of users who receive notifications for Deleted Components
    public UserDetails getComponentDeleteNotifyIds() throws UserException {
        UserDetails userDetails = null;
        
         try {

            UserDetailDAO userDAO = new UserDetailDAO();
            userDetails = (UserDetails) userDAO.getComponentDeleteNotifyIds();

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return userDetails;
        
    }

    // Returns list of users who receive notifications for New Schedules
    public UserDetails getScheduleNewNotifyIds() throws UserException {
        UserDetails userDetails = null;
        
         try {

            UserDetailDAO userDAO = new UserDetailDAO();
            userDetails = (UserDetails) userDAO.getScheduleNewNotifyIds();

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return userDetails;
        
    }

    // Returns list of users who receive notifications for Updated Schedules
    public UserDetails getScheduleUpdateNotifyIds() throws UserException {
        UserDetails userDetails = null;
        
         try {

            UserDetailDAO userDAO = new UserDetailDAO();
            userDetails = (UserDetails) userDAO.getScheduleUpdateNotifyIds();

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return userDetails;
        
    }

     // Returns list of users who receive notifications for Deleted Schedules
    public UserDetails getScheduleDeleteNotifyIds() throws UserException {
        UserDetails userDetails = null;
        
         try {

            UserDetailDAO userDAO = new UserDetailDAO();
            userDetails = (UserDetails) userDAO.getScheduleDeleteNotifyIds();

        } catch (DataAccessException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return userDetails;
        
    }

    // toString - creates a String representation of the object</p>
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("UserBO[");
        buffer.append("]");
        return buffer.toString();
    }
}
