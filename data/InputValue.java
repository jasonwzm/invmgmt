
package benchmark.invmgmt.data;

/**
 * InputValue
 *
 * @author zwang
 * @version 1.0
 * @filename InputValue.java
 */
public class InputValue {

    private static final int STRING_SIZE = 100;
    private String error = null;
    private String value = null;

    /**
     * Automatically generated constructor: InputValue
     *
     * @pre $none
     * @post $none
     */
    public InputValue() {
    }

    /**
     * Constructor for adding input value
     *
     * @param a_strValue
     * @param a_strError
     * @pre $none
     * @post $none
     */
    public InputValue(String a_strValue, String a_strError) {
        value = a_strValue;
        error = a_strError;
    }

    /**
     * getError
     *
     * @return String
     * @pre $none
     * @post $none
     */
    public String getError() {
        return error;
    }

    /**
     * getValue
     *
     * @return String
     * @pre $none
     * @post $none
     */
    public String getValue() {
        return value;
    }

    /**
     * setError
     *
     * @param string void
     * @post $none
     * @pre $none
     */
    public void setError(String string) {
        error = string;
    }

    /**
     * setValue
     *
     * @param string void
     * @post $none
     * @pre $none
     */
    public void setValue(String string) {
        value = string;
    }

    /**
     * <p>Method toString - creates a String representation of the object</p>
     *
     * @return the String representation
     * @pre $none
     * @post $none
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(STRING_SIZE);
        buffer.append("InputValue["); //$NON-NLS-1$
        buffer.append("error = ").append(error); //$NON-NLS-1$
        buffer.append(", value = ").append(value); //$NON-NLS-1$
        buffer.append("]"); //$NON-NLS-1$
        return buffer.toString();
    }
}
