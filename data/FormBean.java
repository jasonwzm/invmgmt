
package benchmark.invmgmt.data;

import java.util.HashMap;

import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.util.StringUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * FormBean
 *
 * @author zwang
 * @version 1.0
 * @filename FormBean.java
 */
public class FormBean extends HashMap {

    /**
     * Automatically generated constructor: FormBean
     */
    public FormBean() {
    }
    /**
     * Logger
     */
    private static LogHelper logger = new LogHelper(FormBean.class);
    //store the user id here from session
    private String userid = null;

    /**
     * getIntValue gets value in form as int
     *
     * @param a_strKey
     * @return int
     * @pre a_strKey != null
     * @post $none
     */
    public final int getIntValue(String a_strKey) {
        int intValue = -1;
        String strValue = getValue(a_strKey);
        if (!StringUtil.isEmpty(strValue)) {
            try {
                intValue = Integer.parseInt(strValue);
            } catch (NumberFormatException nfe) {
                logger.error("getParameterIntValue() NumberFormatException" + nfe.getMessage());
            }
        }
        return intValue;
    }
    
    public final Date getDateValue(String a_strKey) {
        DateFormat formatter =  null;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValue = null;
        String strValue = getValue(a_strKey);
        if (!StringUtil.isEmpty(strValue)) {
            try {
                dateValue = formatter.parse(strValue);
            } catch(ParseException ex) {
                
            }
        }
        return dateValue;
    }
    
    /**
     * getMultiValues get the parameters value from the request
     *
     * @param a_a_strKey
     * @return Array
     * @pre $none
     * @post $none
     */
    public final String[] getMultiValues(String a_strKey) {
        String[] strValue = null;
        
        if ((a_strKey != null) && (containsKey(a_strKey))) {
            if (!get(a_strKey).getClass().isArray()){
                // It is NOT an array - with only 1 item
                strValue = new String[1];
                strValue[0] = (String) get(a_strKey);
            }
            else {
                // It is an ARRAY - more than 1 item
                strValue = (String[]) get(a_strKey);
            }
        }
        
        for (int x = 0; x < strValue.length; x++) {
            if (strValue[x] != null) {
                strValue[x] = strValue[x].trim();
            }
        }
        
        return strValue;
    }


    /**
     * getValue get the parameter value from the request
     *
     * @param a_a_strKey
     * @param a_strKey
     * @return String
     * @pre $none
     * @post $none
     */
    public final String getValue(String a_strKey) {
        String strValue = null;
        if ((a_strKey != null)
                && (containsKey(a_strKey))) {
            strValue = (String) get(a_strKey);
        }
        if (strValue != null) {
            strValue = strValue.trim();
        }
        return strValue;
    }

    /**
     * getValue get the parameter value from the request
     *
     * @param a_a_strKey
     * @param a_strKey
     * @return String
     * @pre $none
     * @post $none
     */
    public final String getStringValue(String a_strKey) {
        String strValue = getValue(a_strKey);
        if (strValue == null) {
            strValue = "";  //$NON-NLS-1$
        }
        return strValue;
    }

    /**
     * getValue
     *
     * @param a_strDefaultValue
     * @param strFormValue
     * @return String
     * @post $none
     * @pre $none
     */
    public String getStringValue(
            String strFormValue,
            String a_strDefaultValue) {
        String strValue = getStringValue(strFormValue); //$NON-NLS-1$
        if ("".equals(strValue)) { //$NON-NLS-1$
            strValue = a_strDefaultValue;
        }
        return strValue;
    }

    /**
     * getUserid
     *
     * @return String
     * @post $none
     * @pre $none
     */
    public String getUserid() {
        return userid;
    }

    /**
     * setUserid
     *
     * @param string void
     * @post $none
     * @pre $none
     */
    public void setUserid(String string) {
        userid = string;
    }

    /**
     * isEmpty
     *
     * @param a_strKey
     * @return boolean boolean
     */
    public boolean isEmpty(String a_strKey) {
        return StringUtil.isEmpty(getValue(a_strKey));
    }

    /**
     * <p>Method toString - creates a String representation of the object</p>
     *
     * @return the String representation
     * @author a developer
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("FormBean["); //$NON-NLS-1$
        buffer.append("userid = ").append(userid); //$NON-NLS-1$
        buffer.append("params = ").append(super.toString()); //$NON-NLS-1$
        buffer.append("]"); //$NON-NLS-1$
        return buffer.toString();
    }
}
