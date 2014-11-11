
package benchmark.invmgmt.data;

import java.util.ArrayList;

/**
 * InputValues
 *
 * @author zwang
 * @version 1.0
 * @filename InputValues.java
 */
public class InputValues extends ArrayList {

    private static final int STRING_SIZE = 100;

    /**
     * Automatically generated constructor: InputValues
     *
     * @post $none
     * @pre $none
     */
    public InputValues() {
    }

    /**
     * <p>Method toString - creates a String representation of the object</p>
     *
     * @return the String representation
     * @post $none
     * @pre $none
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(STRING_SIZE);
        buffer.append("InputValues["); //$NON-NLS-1$
        buffer.append(super.toString());
        buffer.append("]"); //$NON-NLS-1$
        return buffer.toString();
    }
}
