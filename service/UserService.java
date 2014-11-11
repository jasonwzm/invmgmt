
package benchmark.invmgmt.service;

import benchmark.invmgmt.data.UserDetail;
import benchmark.invmgmt.data.UserDetails;
import benchmark.invmgmt.exception.UserException;

/**
 *
 * @author zwang
 */
public interface UserService {

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
