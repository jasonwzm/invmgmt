
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class Role extends BeanObject {

    public Role() {
    }
    private char role;
    private String roleDescription = null;

    /**
     * @return the role
     */
    public char getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(char role) {
        this.role = role;
    }

    /**
     * @return the roleDescription
     */
    public String getRoleDescription() {
        return roleDescription;
    }

    /**
     * @param roleDescription the roleDescription to set
     */
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String toString() {
        String buffer = "id[" + getId() + "], "
                + "role[" + getRole() + "], "
                + "roleDescription[" + getRoleDescription() + "], "
                + "lastModifyUserId[" + getLastModifyUserId() + "], "
                + "createUserId[" + getCreateUserId() + "]";

        return buffer;
    }
}
