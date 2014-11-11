
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.data.ScheduleGroupToVirtualComponent;
import benchmark.invmgmt.data.ScheduleGroupToVirtualComponents;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.ServiceLocatorException;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.util.ServiceLocator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author zwang
 */
public class ScheduleGroupToVirtualComponentDAO extends DbObjectDAO {

    private static LogHelper logger = new LogHelper(VirtualComponentDAO.class);
    private ServiceLocator objServiceLocator = ServiceLocator.getInstance();

    public ScheduleGroupToVirtualComponentDAO() {
        this(null);
        setDbSQL(getDbSQL());
    }

    public ScheduleGroupToVirtualComponentDAO(Connection a_objConnection) {
        super(a_objConnection);
        setDbSQL(getDbSQL());
    }

    /*
     * This delete expects that you have passed it ScheduleGroupToVirtualComponent
     */
    @Override
    public boolean delete(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;
        ScheduleGroupToVirtualComponent compObject = (ScheduleGroupToVirtualComponent) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (compObject != null) {
                strSQLQuery = getSqlDelete();
                logger.debug(" sql query for delete is =>" + strSQLQuery);
                if (objConnection != null) {
                    //get the prepared statement
                    objStatement = objConnection.prepareStatement(strSQLQuery);
                    objStatement.setInt(1, compObject.getVirtualComponentId());
                    objStatement.setInt(2, compObject.getScheduleGroupId());

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

    /*
     * Insert one record into the DB for this link table.
     */
    @Override
    public int insert(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        int retval = 0;
        ScheduleGroupToVirtualComponent comp = (ScheduleGroupToVirtualComponent) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlInsert();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setInt(1, comp.getVirtualComponentId());
                objStatement.setInt(2, comp.getScheduleGroupId());

                if (comp.getDescription() != null && comp.getDescription().length() > 0) {
                    objStatement.setString(3, comp.getDescription());
                } else {
                    objStatement.setNull(3, Types.VARCHAR);
                }

                objStatement.setString(4, comp.getLastModifyUserId());
                // use lastModifyUser for the createUser value
                objStatement.setString(5, comp.getLastModifyUserId());
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
        ScheduleGroupToVirtualComponent comp = (ScheduleGroupToVirtualComponent) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlUpdate();
                logger.debug(" sql query for update is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setInt(1, comp.getVirtualComponentId());
                objStatement.setInt(2, comp.getScheduleGroupId());

                if (comp.getDescription() != null && comp.getDescription().length() > 0) {
                    objStatement.setString(3, comp.getDescription());
                } else {
                    objStatement.setNull(3, Types.VARCHAR);
                }

                objStatement.setString(4, comp.getLastModifyUserId());

                /* Where clause values */
                objStatement.setInt(5, comp.getVirtualComponentId());
                objStatement.setInt(6, comp.getScheduleGroupId());

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * Return all items
     */
    public BeanObjects list() throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        ScheduleGroupToVirtualComponent comp = null;
        ScheduleGroupToVirtualComponents comps = new ScheduleGroupToVirtualComponents();

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            strSQLQuery = getSqlList();

            logger.debug(" sql query for list is =>" + strSQLQuery);
            if (objConnection != null) {
                //get the prepared statement
                objStatement = objConnection.prepareStatement(strSQLQuery);

                logger.debug("sql statement created");
                // execute the statement
                objResultSet = objStatement.executeQuery();

                logger.debug("sql resultset obtained");
                while (objResultSet.next()) {
                    comp = new ScheduleGroupToVirtualComponent();
                    comp.setDescription(objResultSet.getString("DESCRIPTION"));
                    comp.setScheduleGroupId(objResultSet.getInt("SCHED_GROUP_ID"));
                    comp.setVirtualComponentId(objResultSet.getInt("VIRTUAL_COMP_ID"));
                    comp.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    comp.setCreateUserId(objResultSet.getString("CREATE_USER"));

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

    /* 
     * Return a single ScheduleGroupToVirtualComponent
     */
    public BeanObject listByIds(int a_virtualComponentId, int a_scheduleGroupId) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        ScheduleGroupToVirtualComponent comp = null;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                //get the SQl query for select statement
                getDbSQLforDAO().setListWhere("VIRTUAL_COMP_ID = ? AND SCHED_GROUP_ID = ?");
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                objStatement.setInt(1, a_virtualComponentId);
                objStatement.setInt(2, a_scheduleGroupId);
                logger.debug("sql statement created");

                // execute the statement
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                /*
                 * This will always be a loop of 1
                 */
                while (objResultSet.next()) {
                    comp = new ScheduleGroupToVirtualComponent();
                    comp.setDescription(objResultSet.getString("DESCRIPTION"));
                    comp.setScheduleGroupId(objResultSet.getInt("SCHED_GROUP_ID"));
                    comp.setVirtualComponentId(objResultSet.getInt("VIRTUAL_COMP_ID"));
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

        objDbSQL.setTableName("SCHED.SCHED_GRP_TO_VIRT_COMP");
        objDbSQL.setTableJoinsForList(null);
        objDbSQL.setListFields("VIRTUAL_COMP_ID, SCHED_GROUP_ID, DESCRIPTION, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setInsertFields("VIRTUAL_COMP_ID, SCHED_GROUP_ID, DESCRIPTION, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setUpdateFields("VIRTUAL_COMP_ID = ?, "
                + "SCHED_GROUP_ID = ?, "
                + "DESCRIPTION = ?, "
                + "LAST_MODIFY_USER = ? ");
        objDbSQL.setUpdateWhere("VIRTUAL_COMP_ID = ? AND SCHED_GROUP_ID = ?");
        objDbSQL.setDeleteWhere("VIRTUAL_COMP_ID = ? AND SCHED_GROUP_ID = ?");
        objDbSQL.setOrderBy("SCHED_GROUP_ID, VIRTUAL_COMP_ID");

        return objDbSQL;
    }
}
