
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.AppDefinition;
import benchmark.invmgmt.data.AppDefinitions;
import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.ServiceLocatorException;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.util.ServiceLocator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author zwang
 */
public class AppDefinitionDAO extends DbObjectDAO {

    private static LogHelper logger = new LogHelper(VirtualComponentDAO.class);
    private ServiceLocator objServiceLocator = ServiceLocator.getInstance();

    public AppDefinitionDAO() {
        this(null);
        setDbSQL(getDbSQL());
    }

    public AppDefinitionDAO(Connection a_objConnection) {
        super(a_objConnection);
        setDbSQL(getDbSQL());
    }

    @Override
    public boolean delete(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;
        int count = 0;
        AppDefinition defObject = (AppDefinition) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (defObject != null) {
                strSQLQuery = getSqlDelete();
                logger.debug(" sql query for delete is =>" + strSQLQuery);
                if (objConnection != null) {
                    //get the prepared statement
                    objStatement = objConnection.prepareStatement(strSQLQuery);
                    objStatement.setString(1, defObject.getKeyName());

                    logger.debug("sql statement created");
                    // execute the statement
                    count = objStatement.executeUpdate();
                    if (count > 0) {
                        retval = true;
                    }
                    logger.debug("sql resultset obtained");
                }
            }
        } catch (ServiceLocatorException ex) {
            throw new DataAccessException(ex);
        } catch (SQLException sqle) {
            try {
                objConnection.rollback();
                logger.error("SQLException[Rolling back transaction] = " + sqle.getMessage());
                throw new DataAccessException(sqle);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
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
        AppDefinition defObject = (AppDefinition) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlInsert();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setString(1, defObject.getKeyName());
                objStatement.setString(2, defObject.getKeyValue());
                objStatement.setString(3, defObject.getLastModifyUserId());
                // use lastModifiedUser for createUser value
                objStatement.setString(4, defObject.getLastModifyUserId());

                retval = objStatement.executeUpdate();
            }
        } catch (ServiceLocatorException ex) {
            throw new DataAccessException(ex);
        } catch (SQLException sqle) {
            try {
                objConnection.rollback();
                logger.error("SQLException[Rolling back transaction] = " + sqle.getMessage());
                throw new DataAccessException(sqle);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
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
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;
        int count;
        AppDefinition defObject = (AppDefinition) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlUpdate();
                logger.debug(" sql query for update is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setString(1, defObject.getKeyValue());
                objStatement.setString(2, defObject.getLastModifyUserId());

                // Set the where clause value
                objStatement.setString(3, defObject.getKeyName());

                count = objStatement.executeUpdate();
                logger.debug("Number of records updated [" + count + "]");
                if (count > 0) {
                    retval = true;
                }
            }
        } catch (ServiceLocatorException ex) {
            throw new DataAccessException(ex);
        } catch (SQLException sqle) {
            try {
                objConnection.rollback();
                logger.error("SQLException[Rolling back transaction] = " + sqle.getMessage());
                throw new DataAccessException(sqle);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
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
    public BeanObjects list(String aCondition) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BeanObjects list() throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        AppDefinition defObject = null;
        AppDefinitions defObjects = new AppDefinitions();

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();
            strSQLQuery = getSqlList();
            logger.debug(" sql query for list is =>" + strSQLQuery);

            if (objConnection != null) {
                //get the prepared statement
                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                while (objResultSet.next()) {
                    defObject = new AppDefinition();
                    defObject.setKeyName(objResultSet.getString("KEY_NAME"));
                    defObject.setKeyValue(objResultSet.getString("KEY_VALUE"));
                    defObject.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    defObject.setCreateUserId(objResultSet.getString("CREATE_USER"));

                    defObjects.add(defObject);
                }
            }
        } catch (ServiceLocatorException ex) {
            throw new DataAccessException(ex);
        } catch (SQLException sqle) {
            try {
                objConnection.rollback();
                logger.error("SQLException[Rolling back transaction] = " + sqle.getMessage());
                throw new DataAccessException(sqle);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        } finally {
            try {
                if (objResultSet != null) {
                    objResultSet.close();
                }
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

        return defObjects;
    }

    public BeanObject listByKey(String aKey) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        AppDefinition defObject = null;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                getDbSQLforDAO().setListWhere("KEY_NAME = '" + aKey + "'");
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);

                //get the prepared statement
                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                if (objResultSet.next()) {
                    defObject = new AppDefinition();
                    defObject.setKeyName(objResultSet.getString("KEY_NAME"));
                    defObject.setKeyValue(objResultSet.getString("KEY_VALUE"));
                    defObject.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    defObject.setCreateUserId(objResultSet.getString("CREATE_USER"));
                }
            }
        } catch (ServiceLocatorException ex) {
            throw new DataAccessException(ex);
        } catch (SQLException sqle) {
            try {
                objConnection.rollback();
                logger.error("SQLException[Rolling back transaction] = " + sqle.getMessage());
                throw new DataAccessException(sqle);
            } catch (SQLException ex) {
                throw new DataAccessException(ex);
            }
        } finally {
            try {
                if (objResultSet != null) {
                    objResultSet.close();
                }
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

        return defObject;
    }

    @Override
    protected DbSQL getDbSQL() {
        // create a new bean to hold the sql information for this DAO
        DbSQL objDbSQL = new DbSQL();

        objDbSQL.setTableName("SCHED.APP_DEFINITION");
        objDbSQL.setListFields("KEY_NAME, KEY_VALUE, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setInsertFields("KEY_NAME, KEY_VALUE, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setUpdateFields("KEY_VALUE = ?, LAST_MODIFY_USER = ? ");
        objDbSQL.setUpdateWhere("KEY_NAME = ?");
        objDbSQL.setDeleteWhere("KEY_NAME = ?");

        return objDbSQL;
    }
}
