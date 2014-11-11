
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.data.ScheduleToScheduleGroup;
import benchmark.invmgmt.data.ScheduleToScheduleGroups;
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
public class ScheduleToScheduleGroupDAO extends DbObjectDAO {

    private static LogHelper logger = new LogHelper(VirtualComponentDAO.class);
    private ServiceLocator objServiceLocator = ServiceLocator.getInstance();

    public ScheduleToScheduleGroupDAO() {
        this(null);
        setDbSQL(getDbSQL());
    }

    public ScheduleToScheduleGroupDAO(Connection a_objConnection) {
        super(a_objConnection);
        setDbSQL(getDbSQL());
    }

    @Override
    public boolean delete(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;
        ScheduleToScheduleGroup compObject = (ScheduleToScheduleGroup) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (compObject != null) {
                strSQLQuery = getSqlDelete();
                logger.debug(" sql query for delete is =>" + strSQLQuery);
                if (objConnection != null) {
                    //get the prepared statement
                    objStatement = objConnection.prepareStatement(strSQLQuery);
                    objStatement.setInt(1, compObject.getScheduleGroupId());
                    objStatement.setInt(2, compObject.getVirtualComponentId());
                    objStatement.setInt(3, compObject.getSchedulId());

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

    @Override
    public int insert(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        int retval = 0;
        ScheduleToScheduleGroup comp = (ScheduleToScheduleGroup) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlInsert();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setInt(1, comp.getScheduleGroupId());
                objStatement.setInt(2, comp.getVirtualComponentId());
                objStatement.setInt(3, comp.getSchedulId());
                objStatement.setInt(4, comp.getNumOfPartitions());

                objStatement.setString(5, comp.getLastModifyUserId());
                // use lastModifyUser for createUser value
                objStatement.setString(6, comp.getLastModifyUserId());
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
                logger.error("SQLException = " + sqle.getMessage());
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
        ScheduleToScheduleGroup comp = (ScheduleToScheduleGroup) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlUpdate();
                logger.debug(" sql query for update is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setInt(1, comp.getScheduleGroupId());
                objStatement.setInt(2, comp.getVirtualComponentId());
                objStatement.setInt(3, comp.getSchedulId());
                objStatement.setInt(4, comp.getNumOfPartitions());
                objStatement.setString(5, comp.getLastModifyUserId());

                /* Where clause values */
                objStatement.setInt(6, comp.getScheduleGroupId());
                objStatement.setInt(7, comp.getVirtualComponentId());
                objStatement.setInt(8, comp.getSchedulId());

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

    /*
     * Return all items
     */
    public BeanObjects list() throws DataAccessException {
        return list(null);
    }

    public BeanObjects listByScheduleId(int a_scheduleId) throws DataAccessException {
        Integer i = new Integer(a_scheduleId);
        String condition = " SCHEDULE_ID = " + i.toString() + " ";
        return list(condition);
    }

    @Override
    public BeanObjects list(String aCondition) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        ScheduleToScheduleGroup comp = null;
        ScheduleToScheduleGroups comps = new ScheduleToScheduleGroups();

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                if (aCondition != null) {
                    String newWhere = getDbSQLforDAO().getListWhere() + aCondition;
                    getDbSQLforDAO().setListWhere(newWhere);
                }
                
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);
                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                while (objResultSet.next()) {
                    comp = new ScheduleToScheduleGroup();
                    comp.setScheduleGroupId(objResultSet.getInt("SCHED_GROUP_ID"));
                    comp.setVirtualComponentId(objResultSet.getInt("VIRTUAL_COMP_ID"));
                    comp.setSchedulId(objResultSet.getInt("SCHEDULE_ID"));
                    comp.setNumOfPartitions(objResultSet.getInt("NUM_PARTITIONS"));
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
    public BeanObject listByIds(int a_virtualComponentId, int a_scheduleGroupId, int a_scheduleId) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        ScheduleToScheduleGroup comp = null;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                getDbSQLforDAO().setListWhere("SCHED_GROUP_ID = ? AND VIRTUAL_COMP_ID = ? AND SCHEDULE_ID = ? ");
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                objStatement.setInt(1, a_scheduleGroupId);
                objStatement.setInt(2, a_virtualComponentId);
                objStatement.setInt(3, a_scheduleId);
                logger.debug("sql statement created");

                // execute the statement
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                /*
                 * This will always be a loop of 1
                 */
                while (objResultSet.next()) {
                    comp = new ScheduleToScheduleGroup();
                    comp.setScheduleGroupId(objResultSet.getInt("SCHED_GROUP_ID"));
                    comp.setVirtualComponentId(objResultSet.getInt("VIRTUAL_COMP_ID"));
                    comp.setSchedulId(objResultSet.getInt("SCHEDULE_ID"));
                    comp.setNumOfPartitions(objResultSet.getInt("NUM_PARTITIONS"));
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

        objDbSQL.setTableName("SCHED.SCHED_TO_SCHEDGRP");
        objDbSQL.setTableJoinsForList(null);
        objDbSQL.setListFields("SCHED_GROUP_ID, VIRTUAL_COMP_ID, SCHEDULE_ID, NUM_PARTITIONS, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setInsertFields("SCHED_GROUP_ID, VIRTUAL_COMP_ID, SCHEDULE_ID, NUM_PARTITIONS, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setUpdateFields("SCHED_GROUP_ID = ?, "
                + "VIRTUAL_COMP_ID = ?, "
                + "SCHEDULE_ID = ?, "
                + "NUM_PARTITIONS = ?, "
                + "LAST_MODIFY_USER = ? ");
        objDbSQL.setUpdateWhere("SCHED_GROUP_ID = ? AND VIRTUAL_COMP_ID = ? AND SCHEDULE_ID = ? ");
        objDbSQL.setDeleteWhere("SCHED_GROUP_ID = ? AND VIRTUAL_COMP_ID = ? AND SCHEDULE_ID = ? ");
        objDbSQL.setOrderBy(null);

        return objDbSQL;
    }
}
