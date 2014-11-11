
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class AppDefinition extends BeanObject {

    private String keyName = null;
    private String keyValue = null;

    public AppDefinition() {
    }

    /**
     * @return the keyName
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * @param keyName the keyName to set
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * @return the keyValue
     */
    public String getKeyValue() {
        return keyValue;
    }

    /**
     * @param keyValue the keyValue to set
     */
    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String toString() {
        String buffer = "id[" + getId() + "], "
                + "keyName[" + getKeyName() + "], "
                + "keyValue[" + getKeyValue() + "], "
                + "lastModifyUserId[" + getLastModifyUserId() + "], "
                + "createUserId[" + getCreateUserId() + "]";

        return buffer;
    }
}
