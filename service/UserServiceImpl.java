
package benchmark.invmgmt.service;

import benchmark.invmgmt.bo.UserBO;
import benchmark.invmgmt.bo.UserBOImpl;
import benchmark.invmgmt.dao.UserDetailDAO;
import benchmark.invmgmt.data.UserDetail;
import benchmark.invmgmt.data.UserDetails;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.UserException;
import benchmark.invmgmt.helper.LogHelper;

/**
 *
 * @author zwang
 */
public final class UserServiceImpl implements UserService {

    private static LogHelper logger = new LogHelper(UserServiceImpl.class);

    public UserServiceImpl() {
    }

    public final UserDetail userDetail(String a_strUserId) throws UserException {
        UserDetail ud = null;

        try {
            UserBO objUserBO = new UserBOImpl();
            ud = objUserBO.userDetail(a_strUserId);

        } catch (UserException uex) {
            throw uex;
        } catch (Exception e) {
            throw new UserException(e);
        }
        return ud;
    }

    // deleteUser - Deletes user given a userID
    public boolean deleteUser(String a_strUserId) throws UserException {
        boolean deleted = false;

        try {

            UserBO objUserBO = new UserBOImpl();
            deleted = objUserBO.deleteUser(a_strUserId);

        } catch (UserException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return deleted;
    }

    // insertUser - Deletes user given a user detail
    public int insertUser(UserDetail a_userDetail) throws UserException {
        int inserts = 0;

        try {

            UserBO objUserBO = new UserBOImpl();
            inserts = objUserBO.insertUser(a_userDetail);

        } catch (UserException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return inserts;
    }

    // updateUser - Updates user given a user detail
    public boolean updateUser(UserDetail a_userDetail) throws UserException {
        boolean updated = false;

        try {

            UserBO objUserBO = new UserBOImpl();
            updated = objUserBO.updateUser(a_userDetail);

        } catch (UserException dae) {
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

            UserBOImpl userBO = new UserBOImpl();
            userDetails = userBO.getAllUsers();

        } catch (UserException dae) {
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

            UserBOImpl userBO = new UserBOImpl();
            userDetails = (UserDetails) userBO.getComponentNewNotifyIds();

        } catch (UserException dae) {
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

            UserBOImpl userBO = new UserBOImpl();
            userDetails = (UserDetails) userBO.getComponentUpdateNotifyIds();

        } catch (UserException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return userDetails;
        
    }

     // Returns list of users who receive notifications for Deleted Components
    public UserDetails getComponentDeleteNotifyIds() throws UserException  {
        UserDetails userDetails = null;
        
         try {

            UserBOImpl userBO = new UserBOImpl();
            userDetails = (UserDetails) userBO.getComponentDeleteNotifyIds();

        } catch (UserException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return userDetails;
        
    }

     // Returns list of users who receive notifications for New Schedules
    public UserDetails getScheduleNewNotifyIds() throws UserException  {
        UserDetails userDetails = null;
        
         try {

            UserBOImpl userBO = new UserBOImpl();
            userDetails = (UserDetails) userBO.getScheduleNewNotifyIds();

        } catch (UserException dae) {
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

            UserBOImpl userBO = new UserBOImpl();
            userDetails = (UserDetails) userBO.getScheduleUpdateNotifyIds();

        } catch (UserException dae) {
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

            UserBOImpl userBO = new UserBOImpl();
            userDetails = (UserDetails) userBO.getScheduleDeleteNotifyIds();

        } catch (UserException dae) {
            throw new UserException(dae);
        } catch (Exception e) {
            throw new UserException(e);
        }

        return userDetails;
        
    }
}
