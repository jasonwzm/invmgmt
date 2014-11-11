
package benchmark.invmgmt.dao;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.NoInitialContextException;

/**
 * LdapOperation
 *
 * @author zwang
 * @version 1.0
 * @filename LdapOperation.java
 */
public interface LdapOperation {

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
    void bind(String a_strUserPassword)
            throws NamingException, NoInitialContextException;

    /**
     * search
     *
     * @param a_strUserEmail
     * @return
     * @throws NamingException ArrayList
     * @post $none
     * @pre $none
     */
    List search(String a_strUserEmail) throws NamingException;
}