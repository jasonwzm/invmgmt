
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class UserToRole extends BeanObject {

    private String userID = null;
    private char userRole;

    public UserToRole() {
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the userRole
     */
    public char getUserRole() {
        return userRole;
    }

    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(char userRole) {
        this.userRole = userRole;
    }

    public String toString() {
        String buffer = "id[" + getUserID() + "], "
                + "role[" + getUserRole() + "], "
                + "lastModifyUserId[" + getLastModifyUserId() + "]"
                + "createUserId[" + getCreateUserId() + "]";

        return buffer;
    }
}
