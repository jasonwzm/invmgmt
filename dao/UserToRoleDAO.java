
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.data.UserToRole;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.ServiceLocatorException;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.util.ServiceLocator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author zwang
 */
public class UserToRoleDAO extends DbObjectDAO {

    private static LogHelper logger = new LogHelper(VirtualComponentDAO.class);
    private ServiceLocator objServiceLocator = ServiceLocator.getInstance();

    public UserToRoleDAO() {
        //this(null);
        setDbSQL(getDbSQL());
    }

    public UserToRoleDAO(Connection a_objConnection) {
        super(a_objConnection);
        setDbSQL(getDbSQL());
    }

    @Override
    public boolean delete(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;
        UserToRole compObject = (UserToRole) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (compObject != null) {
                strSQLQuery = getSqlDelete();
                logger.debug(" sql query for delete is =>" + strSQLQuery);
                if (objConnection != null) {
                    //get the prepared statement
                    objStatement = objConnection.prepareStatement(strSQLQuery);
                    objStatement.setString(1, compObject.getUserID());

                    Character c2 = new Character(compObject.getUserRole());
                    objStatement.setString(2, c2.toString());

                    logger.debug("sql statement created");
                    // execute the statement
                    int count = objStatement.executeUpdate();
                    if (count > 0) {
                        retval = true;
                    }
                    logger.debug("sql resultset obtained");
                }
            }
        } catch (ServiceLocatorException ex) {
            throw new DataAccessException(ex);
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            try {
                if (objStatement != null) {
                    objStatement.close();
                }
                if (objConnection != null) {
                    // In case a connection was passed in we did not get our own.
                    if (getConnection() == null) {
                        objConnection.close();
                    }
                }
            } catch (SQLException sqle) {
            }
        }

        return retval;
    }

    public boolean deleteByUserId(String a_userId) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;
        
        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (a_userId != null) {
                strSQLQuery = getSqlDeleteByUserId();
                logger.debug(" sql query for delete is =>" + strSQLQuery);
                if (objConnection != null) {
                    //get the prepared statement
                    objStatement = objConnection.prepareStatement(strSQLQuery);
                    objStatement.setString(1, a_userId);

                    logger.debug("sql statement created");
                    // execute the statement
                    int count = objStatement.executeUpdate();
                    if (count > 0) {
                        retval = true;
                    }
                    logger.debug("sql resultset obtained");
                }
            }
        } catch (ServiceLocatorException ex) {
            throw new DataAccessException(ex);
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            try {
                if (objStatement != null) {
                    objStatement.close();
                }
                if (objConnection != null) {
                    // In case a connection was passed in we did not get our own.
                    if (getConnection() == null) {
                        objConnection.close();
                    }
                }
            } catch (SQLException sqle) {
            }
        }

        return retval;
    }

    @Override
    public int insert(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        int retval = 0;
        UserToRole comp = (UserToRole) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlInsert();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setString(1, comp.getUserID());

                Character c2 = new Character(comp.getUserRole());
                objStatement.setString(2, c2.toString());

                objStatement.setString(3, comp.getLastModifyUserId());
                // use lastModifyUser for createUser value
                objStatement.setString(4, comp.getLastModifyUserId());
                retval = objStatement.executeUpdate();
            }
        } catch (ServiceLocatorException ex) {
            throw new DataAccessException(ex);
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } finally {
            try {
                if (objStatement != null) {
                    objStatement.close();
                }
                if (objConnection != null) {
                    // In case a connection was passed in we did not get our own.
                    if (getConnection() == null) {
                        objConnection.close();
                    }
                }
            } catch (SQLException sqle) {
            }
        }

        return retval;
    }

    @Override
    public boolean update(BeanObject a_objBeanObject) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BeanObjects list(String aCondition) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected DbSQL getDbSQL() {
        // create a new bean to hold the sql information for this DAO
        DbSQL objDbSQL = new DbSQL();

        objDbSQL.setTableName("INV.TUSER_TO_ROLE");
        objDbSQL.setTableJoinsForList(null);
        objDbSQL.setListFields("USER_ID, USER_ROLE, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setInsertFields("USER_ID, USER_ROLE, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setUpdateFields("USER_ID = ?, USER_ROLE = ?, LAST_MODIFY_USER = ? ");
        objDbSQL.setUpdateWhere("USER_ID = ? AND USER_ROLE = ?");
        objDbSQL.setDeleteWhere("USER_ID = ? AND USER_ROLE = ?");

        return objDbSQL;
    }
    protected String getSqlDeleteByUserId() {
        String sql ="DELETE FROM INV.TUSER_TO_ROLE WHERE USER_ID = ?";
        return sql;
    }
}
