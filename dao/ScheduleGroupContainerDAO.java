
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.data.ScheduleGroupContainer;
import benchmark.invmgmt.data.ScheduleGroupContainers;
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
public class ScheduleGroupContainerDAO extends DbObjectDAO {

    private static LogHelper logger = new LogHelper(VirtualComponentDAO.class);
    private ServiceLocator objServiceLocator = ServiceLocator.getInstance();

    public ScheduleGroupContainerDAO() {
        this(null);
        setDbSQL(getDbSQL());
    }

    public ScheduleGroupContainerDAO(Connection a_objConnection) {
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
        ScheduleGroupContainer compObject = (ScheduleGroupContainer) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (compObject != null) {
                strSQLQuery = getSqlDelete();
                logger.debug(" sql query for delete is =>" + strSQLQuery);
                if (objConnection != null) {
                    //get the prepared statement
                    objStatement = objConnection.prepareStatement(strSQLQuery);
                    objStatement.setInt(1, compObject.getId());

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
        ScheduleGroupContainer comp = (ScheduleGroupContainer) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlInsert();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setString(1, comp.getContainerName());
                objStatement.setString(2, comp.getLastModifyUserId());
                // use lastModifiedUser for createUser value
                objStatement.setString(3, comp.getLastModifyUserId());

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
        ScheduleGroupContainer comp = (ScheduleGroupContainer) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlUpdate();
                logger.debug(" sql query for update is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setString(1, comp.getContainerName());
                objStatement.setString(2, comp.getLastModifyUserId());

                // Set the where clause value
                objStatement.setInt(3, comp.getId());

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
        ScheduleGroupContainer comp = null;
        ScheduleGroupContainers comps = new ScheduleGroupContainers();

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
                    comp = new ScheduleGroupContainer();
                    comp.setContainerName(objResultSet.getString("CONTAINER_NAME"));
                    comp.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    comp.setCreateUserId(objResultSet.getString("CREATE_USER"));
                    comp.setId(objResultSet.getInt("SCHED_GROUP_CONTAINER_ID"));

                    comps.add(comp);
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

        return comps;
    }

    public BeanObject listById(int a_schedGroupContainerId) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        ScheduleGroupContainer comp = null;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                //get the SQl query for select statement
                getDbSQLforDAO().setListWhere("SCHED_GROUP_CONTAINER_ID = ?");
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                objStatement.setInt(1, a_schedGroupContainerId);
                logger.debug("sql statement created");

                // execute the statement
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                /*
                 * This will always be a loop of 1
                 */
                while (objResultSet.next()) {
                    comp = new ScheduleGroupContainer();
                    comp.setContainerName(objResultSet.getString("CONTAINER_NAME"));
                    comp.setId(objResultSet.getInt("SCHED_GROUP_CONTAINER_ID"));
                    comp.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    comp.setCreateUserId(objResultSet.getString("CREATE_USER"));
                    break;
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

        return comp;
    }

    @Override
    protected DbSQL getDbSQL() {
        // create a new bean to hold the sql information for this DAO
        DbSQL objDbSQL = new DbSQL();

        objDbSQL.setTableName("SCHED.SCHEDULE_GROUP_CONTAINER");
        objDbSQL.setTableJoinsForList(null);
        objDbSQL.setListFields("SCHED_GROUP_CONTAINER_ID, CONTAINER_NAME, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setInsertFields("CONTAINER_NAME, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setUpdateFields("CONTAINER_NAME = ?, LAST_MODIFY_USER = ? ");
        objDbSQL.setUpdateWhere("SCHED_GROUP_CONTAINER_ID = ?");
        objDbSQL.setDeleteWhere("SCHED_GROUP_CONTAINER_ID = ?");
        objDbSQL.setOrderBy("CONTAINER_NAME");

        return objDbSQL;
    }
}
