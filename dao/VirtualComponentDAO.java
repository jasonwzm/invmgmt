
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.data.VirtualComponent;
import benchmark.invmgmt.data.VirtualComponents;
import benchmark.invmgmt.data.VirtualSearchComponent;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.ServiceLocatorException;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.util.ApplicationConstants;
import benchmark.invmgmt.util.ServiceLocator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author zwang
 */
public class VirtualComponentDAO extends DbObjectDAO {

    private static LogHelper logger = new LogHelper(VirtualComponentDAO.class);
    private ServiceLocator objServiceLocator = ServiceLocator.getInstance();

    public VirtualComponentDAO() {
        this(null);
        setDbSQL(getDbSQL());
    }

    public VirtualComponentDAO(Connection a_objConnection) {
        super(a_objConnection);
        setDbSQL(getDbSQL());
    }

    /*
     * Return a list of Virtual Components objects or if aCondition is passed
     * which is the id for the record, then return that record.
     */
    @Override
    public BeanObjects list(String aCondition) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        VirtualComponent vComp = null;
        VirtualComponents vComps = new VirtualComponents();

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            //get the SQl query for select statement
            if (aCondition != null && aCondition != "") {
                getDbSQLforDAO().setListWhere("VIRTUAL_COMP_ID = ? ");
            }
            strSQLQuery = getSqlList();

            logger.debug(" sql query for list is =>" + strSQLQuery);
            if (objConnection != null) {
                //get the prepared statement
                objStatement = objConnection.prepareStatement(strSQLQuery);

                if (aCondition != null && aCondition != "") {
                    objStatement.setString(1, aCondition);
                }

                logger.debug("sql statement created");
                // execute the statement
                objResultSet = objStatement.executeQuery();

                logger.debug("sql resultset obtained");
                while (objResultSet.next()) {
                    vComp = new VirtualComponent();
                    vComp.setAvailEndDate(objResultSet.getDate("AVAIL_SCHED_END_DATE"));
                    vComp.setAvailStartDate(objResultSet.getDate("AVAIL_SCHED_START_DATE"));

                    String tmp1 = objResultSet.getString("AVAIL_FOR_SCHEDULING");
                    if (tmp1 != null) {
                        vComp.setAvailForScheduler(tmp1.charAt(0));
                    } else {
                        vComp.setAvailForScheduler('N');
                    }

                    vComp.setDescription(objResultSet.getString("DESCRIPTION"));
                    vComp.setId(objResultSet.getInt("VIRTUAL_COMP_ID"));
                    vComp.setMachineName(objResultSet.getString("MACHINE_NAME"));
                    vComp.setNumOfAvailablePartitions(objResultSet.getInt("NUM_OF_AVAIL_PARTITIONS"));
                    vComp.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    vComp.setCreateUserId(objResultSet.getString("CREATE_USER"));

                    vComps.add(vComp);
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

        return vComps;
    }

    public BeanObject item(int aCondition) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        VirtualComponent vComp = null;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            //get the SQl query for select statement
            if (aCondition > 0) {
                getDbSQLforDAO().setListWhere("VIRTUAL_COMP_ID = ? ");
                strSQLQuery = getSqlList();

                logger.debug(" sql query for list is =>" + strSQLQuery);
                if (objConnection != null) {
                    //get the prepared statement
                    objStatement = objConnection.prepareStatement(strSQLQuery);
                    objStatement.setInt(1, aCondition);

                    logger.debug("sql statement created");
                    // execute the statement
                    objResultSet = objStatement.executeQuery();

                    logger.debug("sql resultset obtained");
                    // we will only ever get one record.
                    while (objResultSet.next()) {
                        vComp = new VirtualComponent();
                        vComp.setAvailEndDate(objResultSet.getDate("AVAIL_SCHED_END_DATE"));
                        vComp.setAvailStartDate(objResultSet.getDate("AVAIL_SCHED_START_DATE"));

                        String tmp1 = objResultSet.getString("AVAIL_FOR_SCHEDULING");
                        if (tmp1 != null) {
                            vComp.setAvailForScheduler(tmp1.charAt(0));
                        } else {
                            vComp.setAvailForScheduler('N');
                        }

                        vComp.setDescription(objResultSet.getString("DESCRIPTION"));
                        vComp.setId(objResultSet.getInt("VIRTUAL_COMP_ID"));
                        vComp.setMachineName(objResultSet.getString("MACHINE_NAME"));
                        vComp.setNumOfAvailablePartitions(objResultSet.getInt("NUM_OF_AVAIL_PARTITIONS"));
                        vComp.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                        vComp.setCreateUserId(objResultSet.getString("CREATE_USER"));
                        break;
                    }
                }
            } else {
                throw new DataAccessException("Vitual component item condition has to be > 0, actual value [" + aCondition + "]");
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
        return vComp;
    }

    @Override
    public boolean delete(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (a_objBeanObject != null) {
                strSQLQuery = getSqlDelete();
                logger.debug(" sql query for delete is =>" + strSQLQuery);
                if (objConnection != null) {
                    //get the prepared statement
                    objStatement = objConnection.prepareStatement(strSQLQuery);
                    objStatement.setInt(1, a_objBeanObject.getId());

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
        VirtualComponent vComp = (VirtualComponent) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlInsert();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setString(1, vComp.getMachineName());

                if (vComp.getAvailStartDate() != null) {
                    objStatement.setDate(2,
                            new java.sql.Date(vComp.getAvailStartDate().getTime()));
                } else {
                    objStatement.setNull(2, Types.DATE);
                }

                if (vComp.getAvailEndDate() != null) {
                    objStatement.setDate(3,
                            new java.sql.Date(vComp.getAvailEndDate().getTime()));
                } else {
                    objStatement.setNull(3, Types.DATE);
                }

                Character c2 = new Character(vComp.getAvailForScheduler());
                objStatement.setString(4, c2.toString());

                objStatement.setInt(5, vComp.getNumOfAvailablePartitions());

                if (vComp.getDescription() != null && vComp.getDescription().length() > 0) {
                    objStatement.setString(6, vComp.getDescription());
                } else {
                    objStatement.setNull(6, Types.VARCHAR);
                }

                objStatement.setString(7, vComp.getLastModifyUserId());
                // use lastModifyUser for createUser value
                objStatement.setString(8, vComp.getLastModifyUserId());

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
        VirtualComponent vComp = (VirtualComponent) a_objBeanObject;
        logger.debug(vComp.getId());

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlUpdate();
                logger.debug(" sql query for update is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setString(1, vComp.getMachineName());

                if (vComp.getAvailStartDate() != null) {
                    objStatement.setDate(2,
                            new java.sql.Date(vComp.getAvailStartDate().getTime()));
                } else {
                    objStatement.setNull(2, Types.DATE);
                }

                if (vComp.getAvailEndDate() != null) {
                    objStatement.setDate(3,
                            new java.sql.Date(vComp.getAvailEndDate().getTime()));
                } else {
                    objStatement.setNull(3, Types.DATE);
                }

                Character c2 = new Character(vComp.getAvailForScheduler());
                objStatement.setString(4, c2.toString());

                objStatement.setInt(5, vComp.getNumOfAvailablePartitions());

                if (vComp.getDescription() != null && vComp.getDescription().length() > 0) {
                    objStatement.setString(6, vComp.getDescription());
                } else {
                    objStatement.setNull(6, Types.VARCHAR);
                }

                objStatement.setString(7, vComp.getLastModifyUserId());
                objStatement.setInt(8, vComp.getId());

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

    public BeanObjects search(VirtualSearchComponent aSearchComp) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        StringBuilder strSQLQuery = null;
        boolean whereSet = false;
        boolean stmtAdded = false;
        VirtualComponent vComp = null;
        VirtualComponents vComps = new VirtualComponents();

        DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            // Build the SQL search select statement
            strSQLQuery = new StringBuilder(ApplicationConstants.INIT_STRING_100);
            strSQLQuery.append("Select ");
            strSQLQuery.append(getDbSQLforDAO().getListFields());
            strSQLQuery.append(" FROM ");
            strSQLQuery.append(getDbSQLforDAO().getTableName());

            // check to see if there are any changes for the where clause
            if (aSearchComp.isMachineNameBeenSet() == true) {
                if (whereSet == false) {
                    strSQLQuery.append(" WHERE ");
                    whereSet = true;
                }
                if (aSearchComp.getMachineName().indexOf('*') == -1) {
                    strSQLQuery.append(" MACHINE_NAME = '");
                    strSQLQuery.append(aSearchComp.getMachineName());
                    strSQLQuery.append("' ");
                } else {
                    strSQLQuery.append(" MACHINE_NAME LIKE '");
                    strSQLQuery.append(aSearchComp.getMachineName().replace('*', '%'));
                    strSQLQuery.append("' ");
                }
                stmtAdded = true;
            }

            if (aSearchComp.isAvailStartDateBeenSet() == true) {
                if (whereSet == false) {
                    strSQLQuery.append(" WHERE ");
                    whereSet = true;
                }

                if (stmtAdded == true) {
                    strSQLQuery.append(" AND ");
                }
                if (aSearchComp.getStartDateOperator().compareToIgnoreCase("NONE") != 0) {
                    strSQLQuery.append(" AVAIL_SCHED_START_DATE ");
                    strSQLQuery.append(aSearchComp.getStartDateOperator());
                    strSQLQuery.append(" '");
                    strSQLQuery.append(formatter.format(aSearchComp.getAvailStartDate()));
                    strSQLQuery.append("' ");
                } else {
                    strSQLQuery.append(" AVAIL_SCHED_START_DATE ='");
                    strSQLQuery.append(formatter.format(aSearchComp.getAvailStartDate()));
                    strSQLQuery.append("' ");
                }
                stmtAdded = true;
            }

            if (aSearchComp.isAvailEndDateBeenSet() == true) {
                if (whereSet == false) {
                    strSQLQuery.append(" WHERE ");
                    whereSet = true;
                }
                if (stmtAdded == true) {
                    strSQLQuery.append(" AND ");
                }
                if (aSearchComp.getEndDateOperator().compareToIgnoreCase("NONE") != 0) {
                    strSQLQuery.append(" AVAIL_SCHED_END_DATE ");
                    strSQLQuery.append(aSearchComp.getEndDateOperator());
                    strSQLQuery.append(" '");
                    strSQLQuery.append(formatter.format(aSearchComp.getAvailEndDate()));
                    strSQLQuery.append("' ");
                } else {
                    strSQLQuery.append(" AVAIL_SCHED_END_DATE = '");
                    strSQLQuery.append(formatter.format(aSearchComp.getAvailEndDate()));
                    strSQLQuery.append("' ");
                }
                stmtAdded = true;
            }

            if (aSearchComp.isAvailForSchedulerBeenSet() == true) {
                if (whereSet == false) {
                    strSQLQuery.append(" WHERE ");
                    whereSet = true;
                }
                if (stmtAdded == true) {
                    strSQLQuery.append(" AND ");
                }
                strSQLQuery.append(" AVAIL_FOR_SCHEDULING = '");
                strSQLQuery.append(aSearchComp.getAvailForScheduler());
                strSQLQuery.append("' ");
                stmtAdded = true;
            }

            if (aSearchComp.isNumOfAvailablePartitionsBeenSet() == true) {
                if (whereSet == false) {
                    strSQLQuery.append(" WHERE ");
                    whereSet = true;
                }
                if (stmtAdded == true) {
                    strSQLQuery.append(" AND ");
                }

                if (aSearchComp.getNumPartitionsOperator().compareToIgnoreCase("NONE") != 0) {
                    strSQLQuery.append(" NUM_OF_AVAIL_PARTITIONS ");
                    strSQLQuery.append(aSearchComp.getNumPartitionsOperator());
                    strSQLQuery.append(" ");
                    strSQLQuery.append(aSearchComp.getNumOfAvailablePartitions());
                } else {
                    // default to =
                    strSQLQuery.append(" NUM_OF_AVAIL_PARTITIONS = ");
                    strSQLQuery.append(aSearchComp.getNumOfAvailablePartitions());
                }
                stmtAdded = true;
            }

            if (aSearchComp.isDescriptionBeenSet() == true) {
                if (whereSet == false) {
                    strSQLQuery.append(" WHERE ");
                    whereSet = true;
                }
                if (stmtAdded == true) {
                    strSQLQuery.append(" AND ");
                }

                if (aSearchComp.getDescription().indexOf('*') == -1) {
                    strSQLQuery.append(" DESCRIPTION = '");
                    strSQLQuery.append(aSearchComp.getDescription());
                    strSQLQuery.append("' ");
                } else {
                    strSQLQuery.append(" DESCRIPTION LIKE '");
                    strSQLQuery.append(aSearchComp.getDescription().replace('*', '%'));
                    strSQLQuery.append("' ");

                }
                stmtAdded = true;
            }

            logger.debug(" sql query for list is =>" + strSQLQuery.toString());
            if (objConnection != null) {
                //get the prepared statement
                objStatement = objConnection.prepareStatement(strSQLQuery.toString());

                logger.debug("sql statement created");
                // execute the statement
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");
                while (objResultSet.next()) {
                    vComp = new VirtualComponent();
                    vComp.setAvailEndDate(objResultSet.getDate("AVAIL_SCHED_END_DATE"));
                    vComp.setAvailStartDate(objResultSet.getDate("AVAIL_SCHED_START_DATE"));
                    String tmp1 = objResultSet.getString("AVAIL_FOR_SCHEDULING");
                    if (tmp1 != null) {
                        vComp.setAvailForScheduler(tmp1.charAt(0));
                    } else {
                        vComp.setAvailForScheduler('N');
                    }

                    vComp.setDescription(objResultSet.getString("DESCRIPTION"));
                    vComp.setId(objResultSet.getInt("VIRTUAL_COMP_ID"));
                    vComp.setMachineName(objResultSet.getString("MACHINE_NAME"));
                    vComp.setNumOfAvailablePartitions(objResultSet.getInt("NUM_OF_AVAIL_PARTITIONS"));
                    vComp.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    vComp.setCreateUserId(objResultSet.getString("CREATE_USER"));
                    vComps.add(vComp);
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
        return vComps;
    }

    @Override
    protected DbSQL getDbSQL() {
        // create a new bean to hold the sql information for this DAO
        DbSQL objDbSQL = new DbSQL();

        objDbSQL.setTableName("SCHED.VIRTUAL_COMPONENT");
        objDbSQL.setTableJoinsForList(null);
        objDbSQL.setListFields("VIRTUAL_COMP_ID, MACHINE_NAME, AVAIL_SCHED_START_DATE, AVAIL_SCHED_END_DATE, AVAIL_FOR_SCHEDULING, NUM_OF_AVAIL_PARTITIONS, DESCRIPTION, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setInsertFields("MACHINE_NAME, AVAIL_SCHED_START_DATE, AVAIL_SCHED_END_DATE, AVAIL_FOR_SCHEDULING, NUM_OF_AVAIL_PARTITIONS, DESCRIPTION, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setUpdateFields("MACHINE_NAME = ?, "
                + "AVAIL_SCHED_START_DATE = ?, "
                + "AVAIL_SCHED_END_DATE = ?, "
                + "AVAIL_FOR_SCHEDULING = ?, "
                + "NUM_OF_AVAIL_PARTITIONS = ?, "
                + "DESCRIPTION = ?, "
                + "LAST_MODIFY_USER = ? ");
        objDbSQL.setUpdateWhere("VIRTUAL_COMP_ID = ? ");
        objDbSQL.setDeleteWhere("VIRTUAL_COMP_ID = ? ");
        objDbSQL.setOrderBy("VIRTUAL_COMP_ID");

        return objDbSQL;
    }
}
