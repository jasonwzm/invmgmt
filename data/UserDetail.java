
package benchmark.invmgmt.data;

import java.util.ArrayList;

/**
 *
 * @author zwang
 */
public class UserDetail extends BeanObject {

    public UserDetail() {
        this.active = 'N';
        this.notifyComponentNew = 'N';
        this.notifyComponentUpdate = 'N';
        this.notifyComponentDelete = 'N';
        this.notifyScheduleNew = 'N';
        this.notifyScheduleUpdate = 'N';
        this.notifyScheduleDelete = 'N';
        this.roles = new ArrayList();
    }
    private String userId = null;
    private String email = null;
    private char active;
    private String country = null;
    private char notifyComponentNew;
    private char notifyComponentUpdate;
    private char notifyComponentDelete;
    private char notifyScheduleNew;
    private char notifyScheduleUpdate;
    private char notifyScheduleDelete;
    private ArrayList roles = null;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the active
     */
    public char getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(char active) {
        this.active = active;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the notifyComponentNew
     */
    public char getNotifyComponentNew() {
        return notifyComponentNew;
    }

    /**
     * @param notifyComponentNew the notifyComponentNew to set
     */
    public void setNotifyComponentNew(char notifyComponentNew) {
        this.notifyComponentNew = notifyComponentNew;
    }

    /**
     * @return the notifyComponentUpdate
     */
    public char getNotifyComponentUpdate() {
        return notifyComponentUpdate;
    }

    /**
     * @param notifyComponentUpdate the notifyComponentUpdate to set
     */
    public void setNotifyComponentUpdate(char notifyComponentUpdate) {
        this.notifyComponentUpdate = notifyComponentUpdate;
    }

    /**
     * @return the notifyComponentDelete
     */
    public char getNotifyComponentDelete() {
        return notifyComponentDelete;
    }

    /**
     * @param notifyComponentDelete the notifyComponentDelete to set
     */
    public void setNotifyComponentDelete(char notifyComponentDelete) {
        this.notifyComponentDelete = notifyComponentDelete;
    }

    /**
     * @return the notifyScheduleNew
     */
    public char getNotifyScheduleNew() {
        return notifyScheduleNew;
    }

    /**
     * @param notifyScheduleNew the notifyScheduleNew to set
     */
    public void setNotifyScheduleNew(char notifyScheduleNew) {
        this.notifyScheduleNew = notifyScheduleNew;
    }

    /**
     * @return the notifyScheduleUpdate
     */
    public char getNotifyScheduleUpdate() {
        return notifyScheduleUpdate;
    }

    /**
     * @param notifyScheduleUpdate the notifyScheduleUpdate to set
     */
    public void setNotifyScheduleUpdate(char notifyScheduleUpdate) {
        this.notifyScheduleUpdate = notifyScheduleUpdate;
    }

    /**
     * @return the notifyScheduleDelete
     */
    public char getNotifyScheduleDelete() {
        return notifyScheduleDelete;
    }

    /**
     * @param notifyScheduleDelete the notifyScheduleDelete to set
     */
    public void setNotifyScheduleDelete(char notifyScheduleDelete) {
        this.notifyScheduleDelete = notifyScheduleDelete;
    }

    /**
     * @return the roles
     */
    public ArrayList getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(ArrayList roles) {
        this.roles = roles;
    }

    public String toString() {
        String buffer = "id[" + getUserId() + "], "
                + "email[" + getEmail() + "], "
                + "active[" + getActive() + "], "
                + "country[" + getCountry() + "], "
                + "notifyComponentNew[" + getNotifyComponentNew() + "], "
                + "notifyComponentUpdate[" + getNotifyComponentUpdate() + "], "
                + "notifyComponentDelete[" + getNotifyComponentDelete() + "], "
                + "notifyScheduleNew[" + getNotifyScheduleNew() + "], "
                + "notifyScheduleUpdate[" + getNotifyScheduleUpdate() + "], "
                + "notifyScheduleDelete[" + getNotifyScheduleDelete() + "], "
                + "lastModifyUserId[" + getLastModifyUserId() + "], "
                + "createUserId[" + getCreateUserId() + "]";

        for (int i = 0; i < getRoles().size(); i++) {
            Role role = (Role) getRoles().get(i);
            buffer = buffer + "\n\troles[" + role.getRole() + "] [" + role.getRoleDescription() + "]";
        }
        return buffer;
    }
}
