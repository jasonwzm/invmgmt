
package benchmark.invmgmt.service;

import benchmark.invmgmt.bo.UserRoleBOImpl;
import benchmark.invmgmt.data.Role;
import benchmark.invmgmt.data.Roles;
import benchmark.invmgmt.exception.UserRoleException;

/**
 *
 * @author zwang
 */
public class UserRoleServiceImpl implements UserRoleService{

    // updateUserRole - updates a user role
    public boolean updateUserRole(Role aRole) throws UserRoleException {
         Boolean updated = false;
       
        try {
            UserRoleBOImpl roleBO = new UserRoleBOImpl();
            updated =  roleBO.updateUserRole(aRole);

        } catch (UserRoleException ure) {
            throw new UserRoleException(ure);
        } catch (Exception e) {
            throw new UserRoleException(e);
        }
       
       return updated;
    }

    // deleteUserRole - deletea user role
    public boolean deleteUserRole(Role aRole) throws UserRoleException {
        Boolean deleted = false;
       
        try {
            UserRoleBOImpl roleBO = new UserRoleBOImpl();
            deleted =  roleBO.deleteUserRole(aRole);

        } catch (UserRoleException ure) {
            throw new UserRoleException(ure);
        } catch (Exception e) {
            throw new UserRoleException(e);
        }
       
       return deleted;
    }

    // insertUserRole - inserts a user role
    public int insertUserRole(Role aRole) throws UserRoleException {
       int inserts = 0;
       
        try {
            UserRoleBOImpl roleBO = new UserRoleBOImpl();
            inserts =  roleBO.insertUserRole(aRole);

        } catch (UserRoleException ure) {
            throw new UserRoleException(ure);
        } catch (Exception e) {
            throw new UserRoleException(e);
        }
       
       return inserts;
    }

    // getAllUserRoles - returns all user roles
    public Roles getAllUserRoles() throws UserRoleException {
                 Roles roles = null;
       
        try {
            UserRoleBOImpl roleBO = new UserRoleBOImpl();
            roles =  (Roles)roleBO.getAllUserRoles();

        } catch (UserRoleException ure) {
            throw new UserRoleException(ure);
        } catch (Exception e) {
            throw new UserRoleException(e);
        }
       
       return roles;
    }

    
}
