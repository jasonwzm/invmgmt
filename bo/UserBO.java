
package benchmark.invmgmt.bo;

import benchmark.invmgmt.data.UserDetail;
import benchmark.invmgmt.data.UserDetails;
import benchmark.invmgmt.exception.AuthenticationException;
import benchmark.invmgmt.exception.UserException;

/**
 * UserBO
 *
 * @author zwang
 * @version 1.0
 * @filename UserBO.java
 */
public interface UserBO {

    /**
     * authenticate
     *
     * @param a_strUserId
     * @param a_strPassword
     * @return
     * @throws AuthenticationException boolean
     * @post $none
     * @pre $none
     */
    public abstract boolean authenticate(
            String a_strUserId,
            String a_strPassword)
            throws AuthenticationException;

    public abstract UserDetail userDetail(String a_strUserId) throws UserException;

    public abstract boolean deleteUser(String a_strUserId) throws UserException;

    public abstract int insertUser(UserDetail a_userDetail) throws UserException;

    public abstract boolean updateUser(UserDetail a_userDetail) throws UserException;
    
    public abstract UserDetails getAllUsers() throws UserException;

    public abstract UserDetails getComponentNewNotifyIds() throws UserException;

    public abstract UserDetails getComponentUpdateNotifyIds() throws UserException;

    public abstract UserDetails getComponentDeleteNotifyIds() throws UserException;

    public abstract UserDetails getScheduleNewNotifyIds() throws UserException;

    public abstract UserDetails getScheduleUpdateNotifyIds() throws UserException;

    public abstract UserDetails getScheduleDeleteNotifyIds() throws UserException;
}