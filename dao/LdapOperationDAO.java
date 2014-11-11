
package benchmark.invmgmt.dao;

import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.util.MappingUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


/**
 * LdapOperationDAO
 *
 * @author zwang
 * @version 1.0
 * @filename LdapOperationDAO.java
 */
public final class LdapOperationDAO implements LdapOperation {

    /**
     * Logger for LDAP Util
     */
    private static LogHelper logger = new LogHelper(LdapOperationDAO.class);
    private static final int STRING_SIZE = 100;

    private static void addToList(Hashtable a_hashResult, String a_strKey, Object a_strValue) {
        try {
            a_hashResult.put(a_strKey, (String) a_strValue);
        } //put the String in the hash
        catch (ClassCastException cce) //it's not a String, so "decode" it (int'l chars)
        {
            try {
                byte[] objByteArray = (byte[]) a_strValue;
                String strValue = new String(objByteArray, "ISO-8859-1");
                logger.debug("hashResult key=>" + a_strKey + " value=>" + strValue);
                a_hashResult.put(a_strKey, strValue);
                //put the string in the hash
                logger.error("classcast exception" + cce.getMessage());
            } catch (UnsupportedEncodingException ue) {
                logger.error("UnsupportedEncodingException " + ue.getMessage());
            }
        }
    }

    /**
     * getProperties
     *
     * @param a_ServerURL
     * @return Properties
     */
    private static Properties getProperties(String a_ServerURL) {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, MappingUtil.getConfiguration("ldap_context_factory"));
        env.put(Context.PROVIDER_URL, a_ServerURL);
        env.put("java.naming.ldap.version",
                MappingUtil.getConfiguration("ldap_version_no"));
        return env;
    }

    /**
     * getProperties
     *
     * @param a_ServerURL
     * @param a_strDN
     * @param a_strPassword
     * @return Properties
     */
    private static Properties getProperties(
            String a_ServerURL,
            String a_strDN,
            String a_strPassword) {
        Properties env = getProperties(a_ServerURL);
        env.put(Context.SECURITY_AUTHENTICATION,
                MappingUtil.getConfiguration("ldap_security_authentication"));
        if (a_strDN != null) {
            env.put(Context.SECURITY_PRINCIPAL, a_strDN);
        }
        if (a_strPassword != null) {
            env.put(Context.SECURITY_CREDENTIALS, a_strPassword);
        }

        env.put("java.naming.security.ssl.keyring", MappingUtil.getConfiguration("ldap_ssl_keyring_class"));
        return env;
    }
    private String strLDAP_SearchBase = null;
    private String strLDAP_Server = null;
    private String strSecureServerURL = null;
    private String strServerURL = null;
    private String strUserDN = ""; //$NON-NLS-1$

    /**
     * LdapOperationDAO
     *
     * return_type
     *
     * @post $none
     * @pre $none
     */
    public LdapOperationDAO() {
        strLDAP_SearchBase = MappingUtil.getConfiguration("ldap_search_base"); //$NON-NLS-1$	
        strLDAP_Server = MappingUtil.getConfiguration("ldap_server"); //$NON-NLS-1$
        setLDAPServer(strLDAP_Server);
    }

    /**
     * bind
     *
     * @param a_strUserPassword
     * @throws NamingException
     * @throws NoInitialContextException void
     * @throws javax.naming.NoInitialContextException
     * @post $none
     * @pre $none
     */
    public void bind(String a_strUserPassword)
            throws NamingException, NoInitialContextException {
        //set the properties
        Properties env =
                getProperties(getLDAPServerURL(), getUserDN(), a_strUserPassword);

        //get the search results
        DirContext ctx = new InitialDirContext(env);
        ctx.close();

    }

    /**
     * getLDAPServerURL
     *
     * @return String
     */
    private String getLDAPServerURL() {
        return strServerURL;
    }

    /**
     * getsearchResults
     *
     * @param a_strUserEmail
     * @return
     * @throws NamingException NamingEnumeration
     * @post $none
     * @pre $none
     */
    private NamingEnumeration getsearchResults(String a_strUserEmail)
            throws NamingException {
        DirContext objContext = null;
        NamingEnumeration objEnumeration = null;
        SearchControls constraints = null;
        //set the properties
        Properties env = getProperties(getLDAPServerURL());

        //search if user exists
        String[] attrlist = {"uid"}; 
        String filter = "(mail=" + a_strUserEmail + ")"; 

        logger.debug("filter=>" + filter); 

        constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        constraints.setReturningAttributes(attrlist);
        objContext = new InitialDirContext(env);

        objEnumeration =
                objContext.search(strLDAP_SearchBase, filter, constraints);
        objContext.close(); //close the connection
        return objEnumeration;
    }

    /**
     * getUserDN
     *
     * @return String
     */
    private String getUserDN() {
        return strUserDN;
    }

    /**
     * search
     *
     * @param a_strUserEmail
     * @return
     * @throws NamingException ArrayList
     * @post $none
     * @pre $none
     */
    public List search(String a_strUserEmail) throws NamingException {
        //get the search results
        NamingEnumeration enumSearchResults = getsearchResults(a_strUserEmail);

        //return the result as list
        SearchResult searchresult = null;
        Attributes attrs = null;
        Attribute attr = null;
        Hashtable hashResult = new Hashtable(3);
        Enumeration enumValues = null;
        List listUsers = new ArrayList(2);
        NamingEnumeration enumAttributes = null;

        while (enumSearchResults.hasMore()) {
            searchresult = (SearchResult) enumSearchResults.next();
            attrs = searchresult.getAttributes();

            //snag the DN
            strUserDN = searchresult.getName() + ',' + MappingUtil.getConfiguration("ldap_search_base"); 

            enumAttributes = attrs.getAll();
            while (enumAttributes.hasMore()) {
                attr = (Attribute) enumAttributes.next();
                enumValues = attr.getAll();
                addToList(hashResult, attr.getID(), enumValues.nextElement());
            }
        }
        logger.debug("hashResult size=>" + hashResult.size());  
        if (hashResult.size() > 0) {
            listUsers.add(hashResult);
        }

        return listUsers;

    }

    /**
     * setLDAPServer
     *
     * @param a_strServerName void
     * @post $none
     * @pre $none
     */
    private void setLDAPServer(String a_strServerName) {
        if (a_strServerName != null) {
            if (a_strServerName.startsWith("ldaps://")) {  
                strSecureServerURL = a_strServerName;
                strServerURL = "ldap://" + a_strServerName.substring(8);  
            } else if (a_strServerName.startsWith("ldap://")) {  
                strSecureServerURL = "ldaps://" + a_strServerName.substring(7);
            } else //it's just "swat.blue.ibm.com"
            {
                strSecureServerURL = "ldaps://" + a_strServerName;  
                strServerURL = "ldap://" + a_strServerName;  
            }
        }
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
        buffer.append("LDAPUtil["); 
        buffer.append("strLDAP_SearchBase = "); 
        buffer.append(strLDAP_SearchBase);
        buffer.append(", strLDAP_Server = ").append(strLDAP_Server); 
        buffer.append(", strServerURL = ").append(strServerURL); 
        buffer.append(", strSecureServerURL = ");  
        buffer.append(strSecureServerURL);
        buffer.append(", strUserDN = ").append(strUserDN); 
        buffer.append("]"); 
        return buffer.toString();
    }
}
