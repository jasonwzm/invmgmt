
package benchmark.invmgmt.data;

/**
 * BeanObject This is a base class used for classes in the data package
 *
 */
public class BeanObject {

    private int id = -1;
    private String lastModifyUserId = null;
    private String createUserId = null;

    /**
     * Automatically generated constructor: BeanObject
     */
    public BeanObject() {
    }

    /**
     * Method getId.
     *
     * @return int
     */
    public final int getId() {
        return id;
    }

    /**
     * getLastModifyUserId
     *
     * @return String
     */
    public final String getLastModifyUserId() {
        return lastModifyUserId;
    }

    /**
     * Method setId.
     *
     * @param newId
     */
    public final void setId(int newId) {
        id = newId;
    }

    /**
     * setLastModifyUserId
     *
     * @param string void
     */
    public final void setLastModifyUserId(String string) {
        lastModifyUserId = string;
    }

    /**
     * @return String
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * @param String void
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    /**
     * Automatically generated method: toString
     *
     * @return String
     */
    public String toString() {
        return "id[" + String.valueOf(id) + "] createUserId[" + getCreateUserId() + "] lastModifyUserId[" + lastModifyUserId + "]";
    }

}
