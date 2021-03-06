
package benchmark.invmgmt.bo;

import benchmark.invmgmt.data.Role;
import benchmark.invmgmt.data.Roles;
import benchmark.invmgmt.exception.UserRoleException;

/**
 *
 * @author zwang
 */
public interface UserRoleBO {
    
    public abstract boolean updateUserRole(Role aRole) throws UserRoleException;
    public abstract boolean deleteUserRole(Role aRole) throws UserRoleException;
    public abstract int insertUserRole(Role aRole) throws UserRoleException;
    public abstract Roles getAllUserRoles() throws UserRoleException;
   
}
