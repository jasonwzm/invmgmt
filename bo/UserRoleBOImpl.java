
package benchmark.invmgmt.bo;


import benchmark.invmgmt.dao.RoleDAO;
import benchmark.invmgmt.data.Role;
import benchmark.invmgmt.data.Roles;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.UserRoleException;

/**
 *
 * @author zwang
 */
public class UserRoleBOImpl implements UserRoleBO {

    // updateUserRole - updates a user role
    public boolean updateUserRole(Role aRole) throws UserRoleException {
          Boolean updated = false;
       
        try {
            RoleDAO roleDAO = new RoleDAO();
            updated =  roleDAO.update(aRole);

        } catch (DataAccessException dae) {
            throw new UserRoleException(dae);
        } catch (Exception e) {
            throw new UserRoleException(e);
        }
       
       return updated;
    }

    // deleteUserRole - deletes a user role
    public boolean deleteUserRole(Role aRole) throws UserRoleException {
         Boolean deleted = false;
       
        try {
            RoleDAO roleDAO = new RoleDAO();
            deleted =  roleDAO.delete(aRole);

        } catch (DataAccessException dae) {
            throw new UserRoleException(dae);
        } catch (Exception e) {
            throw new UserRoleException(e);
        }
       
       return deleted;
    }

    // insertUserRole = inserts a user role
    public int insertUserRole(Role aRole) throws UserRoleException {
        int inserts = 0;
        try {
            RoleDAO roleDAO = new RoleDAO();
            inserts =  roleDAO.insert(aRole);

        } catch (DataAccessException dae) {
            throw new UserRoleException(dae);
        } catch (Exception e) {
            throw new UserRoleException(e);
        }
       
       return inserts;
    }

    // getAllUserRoles - returns all of the user roles
    public Roles getAllUserRoles() throws UserRoleException {
         Roles roles = null;
       
        try {
            RoleDAO roleDAO = new RoleDAO();
            roles =  (Roles)roleDAO.list();

        } catch (DataAccessException dae) {
            throw new UserRoleException(dae);
        } catch (Exception e) {
            throw new UserRoleException(e);
        }
       
       return roles;
    }
    
    
}
