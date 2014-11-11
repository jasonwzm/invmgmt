
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.data.Role;
import benchmark.invmgmt.data.Roles;
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
public class RoleDAO extends DbObjectDAO {

    private static LogHelper logger = new LogHelper(VirtualComponentDAO.class);
    private ServiceLocator objServiceLocator = ServiceLocator.getInstance();

    public RoleDAO() {
        this(null);
        setDbSQL(getDbSQL());
    }

    public RoleDAO(Connection a_objConnection) {
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
        Role roleObject = (Role) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (roleObject != null) {
                strSQLQuery = getSqlDelete();
                logger.debug(" sql query for delete is =>" + strSQLQuery);
                if (objConnection != null) {
                    //get the prepared statement
                    objStatement = objConnection.prepareStatement(strSQLQuery);

                    Character c2 = new Character(roleObject.getRole());
                    objStatement.setString(1, c2.toString());

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
        Role roleObject = (Role) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlInsert();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");
                Character c1 = new Character(roleObject.getRole());
                objStatement.setString(1, c1.toString());
                objStatement.setString(2, roleObject.getRoleDescription());
                objStatement.setString(3, roleObject.getLastModifyUserId());

                // use lastModifiedUser for createUser value
                objStatement.setString(4, roleObject.getLastModifyUserId());

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
        Role roleObject = (Role) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlUpdate();
                logger.debug(" sql query for update is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setString(1, roleObject.getRoleDescription());
                objStatement.setString(2, roleObject.getLastModifyUserId());

                // Set the where clause value
                Character c1 = new Character(roleObject.getRole());
                objStatement.setString(3, c1.toString());

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
        Role role = null;
        Roles roles = new Roles();

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
                    role = new Role();

                    String tmp1 = objResultSet.getString("USER_ROLE");
                    if (tmp1 != null) {
                        role.setRole(tmp1.charAt(0));
                    }

                    role.setRoleDescription(objResultSet.getString("ROLE_DESCRIPTION"));
                    role.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    role.setCreateUserId(objResultSet.getString("CREATE_USER"));

                    roles.add(role);
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

        return roles;
    }

    @Override
    protected DbSQL getDbSQL() {
        DbSQL objDbSQL = new DbSQL();

        objDbSQL.setTableName("INV.TUSER_ROLE");
        objDbSQL.setTableJoinsForList(null);
        objDbSQL.setListFields("USER_ROLE, ROLE_DESCRIPTION, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setInsertFields("USER_ROLE, ROLE_DESCRIPTION, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setUpdateFields("ROLE_DESCRIPTION = ?, LAST_MODIFY_USER = ? ");
        objDbSQL.setUpdateWhere("USER_ROLE = ?");
        objDbSQL.setDeleteWhere("USER_ROLE = ?");
        objDbSQL.setOrderBy("USER_ROLE");

        return objDbSQL;
    }
}
