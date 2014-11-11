
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.data.ScheduleGroup;
import benchmark.invmgmt.data.ScheduleGroupContainer;
import benchmark.invmgmt.data.ScheduleGroupDetail;
import benchmark.invmgmt.data.ScheduleGroupToVirtualComponentDetail;
import benchmark.invmgmt.data.ScheduleGroupToVirtualComponentDetails;
import benchmark.invmgmt.data.ScheduleGroups;
import benchmark.invmgmt.data.VirtualComponent;
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
public class ScheduleGroupDAO extends DbObjectDAO {

    private static LogHelper logger = new LogHelper(VirtualComponentDAO.class);
    private ServiceLocator objServiceLocator = ServiceLocator.getInstance();

    public ScheduleGroupDAO() {
        this(null);
        setDbSQL(getDbSQL());
    }

    public ScheduleGroupDAO(Connection a_objConnection) {
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
        ScheduleGroup compObject = (ScheduleGroup) a_objBeanObject;

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
        ScheduleGroup comp = (ScheduleGroup) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlInsert();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setInt(1, comp.getScheduleGroupContainerId());
                objStatement.setString(2, comp.getGroupName());

                Character c2 = new Character(comp.getAvailableForScheduling());
                objStatement.setString(3, c2.toString());

                objStatement.setInt(4, comp.getMaxFutureScheduleInDays());
                objStatement.setInt(5, comp.getMaxScheduleWindow());

                if (comp.getMaxScheduleWindowTimDefinition() != null && comp.getMaxScheduleWindowTimDefinition().length() > 0) {
                    objStatement.setString(6, comp.getMaxScheduleWindowTimDefinition());
                } else {
                    objStatement.setString(6, "HOUR");
                }

                if (comp.getNumOfPartitions() > 0) {
                    objStatement.setInt(7, comp.getNumOfPartitions());
                } else {
                    objStatement.setInt(7, 1);
                }

                if (comp.getDescription() != null && comp.getDescription().length() > 0) {
                    objStatement.setString(8, comp.getDescription());
                } else {
                    objStatement.setNull(8, Types.VARCHAR);
                }

                objStatement.setString(9, comp.getLastModifyUserId());
                // use lastModifyUser for createUser
                objStatement.setString(10, comp.getLastModifyUserId());

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

        ScheduleGroup comp = (ScheduleGroup) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlUpdate();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setInt(1, comp.getScheduleGroupContainerId());
                objStatement.setString(2, comp.getGroupName());

                Character c2 = new Character(comp.getAvailableForScheduling());
                objStatement.setString(3, c2.toString());

                objStatement.setInt(4, comp.getMaxFutureScheduleInDays());
                objStatement.setInt(5, comp.getMaxScheduleWindow());

                if (comp.getMaxScheduleWindowTimDefinition() != null && comp.getMaxScheduleWindowTimDefinition().length() > 0) {
                    objStatement.setString(6, comp.getMaxScheduleWindowTimDefinition());
                } else {
                    objStatement.setString(6, "HOUR");
                }

                if (comp.getNumOfPartitions() > 0) {
                    objStatement.setInt(7, comp.getNumOfPartitions());
                } else {
                    objStatement.setInt(7, 1);
                }

                if (comp.getDescription() != null && comp.getDescription().length() > 0) {
                    objStatement.setString(8, comp.getDescription());
                } else {
                    objStatement.setNull(8, Types.VARCHAR);
                }

                objStatement.setString(9, comp.getLastModifyUserId());
                // Where clause
                objStatement.setInt(10, comp.getId());

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

    public BeanObjects getScheduleGroupsByContainerId(int aContainerId) throws DataAccessException {
        Integer i = new Integer(aContainerId);
        String condition = "SCHED_GROUP_CONTAINER_ID = " + i.toString();
        return list(condition);
    }

    public BeanObjects getAvailableScheduleGroupsByContainerId(int aContainerId) throws DataAccessException {
        Integer i = new Integer(aContainerId);
        String condition = "SCHED_GROUP_CONTAINER_ID = " + i.toString() + " AND AVAIL_FOR_SCHEDULING = 'Y'";
        return list(condition);
    }

    @Override
    public BeanObjects list(String aCondition) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        ScheduleGroup comp = null;
        ScheduleGroups comps = new ScheduleGroups();

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();
            if (objConnection != null) {
                if (aCondition != null) {
                    String newWhere = getDbSQLforDAO().getListWhere();
                    if (newWhere != null && newWhere.length() > 0) {
                        newWhere = newWhere + " and " + aCondition;
                    } else {
                        newWhere = aCondition;
                    }

                    getDbSQLforDAO().setListWhere(newWhere);
                }
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);
                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                while (objResultSet.next()) {
                    comp = new ScheduleGroup();

                    String tmp1 = objResultSet.getString("AVAIL_FOR_SCHEDULING");
                    if (tmp1 != null) {
                        comp.setAvailableForScheduling(tmp1.charAt(0));
                    } else {
                        comp.setAvailableForScheduling('N');
                    }

                    comp.setId(objResultSet.getInt("SCHED_GROUP_ID"));
                    comp.setScheduleGroupContainerId(objResultSet.getInt("SCHED_GROUP_CONTAINER_ID"));
                    comp.setGroupName(objResultSet.getString("GROUP_NAME"));
                    comp.setMaxFutureScheduleInDays(objResultSet.getInt("MAX_FUTURE_SCHED_IN_DAYS"));
                    comp.setMaxScheduleWindow(objResultSet.getInt("MAX_SCHED_WINDOW"));
                    comp.setMaxScheduleWindowTimDefinition(objResultSet.getString("MAX_SCHED_WINDOW_TIME_DEF"));
                    comp.setNumOfPartitions(objResultSet.getInt("NUM_PARTITIONS"));
                    comp.setDescription(objResultSet.getString("DESCRIPTION"));
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
     * Return all records.
     */
    public BeanObjects list() throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        ScheduleGroup comp = null;
        ScheduleGroups comps = new ScheduleGroups();

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();
            strSQLQuery = getSqlList();
            logger.debug(" sql query for list is =>" + strSQLQuery);

            if (objConnection != null) {
                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                while (objResultSet.next()) {
                    comp = new ScheduleGroup();

                    String tmp1 = objResultSet.getString("AVAIL_FOR_SCHEDULING");
                    if (tmp1 != null) {
                        comp.setAvailableForScheduling(tmp1.charAt(0));
                    } else {
                        comp.setAvailableForScheduling('N');
                    }

                    comp.setId(objResultSet.getInt("SCHED_GROUP_ID"));
                    comp.setScheduleGroupContainerId(objResultSet.getInt("SCHED_GROUP_CONTAINER_ID"));
                    comp.setGroupName(objResultSet.getString("GROUP_NAME"));
                    comp.setMaxFutureScheduleInDays(objResultSet.getInt("MAX_FUTURE_SCHED_IN_DAYS"));
                    comp.setMaxScheduleWindow(objResultSet.getInt("MAX_SCHED_WINDOW"));
                    comp.setMaxScheduleWindowTimDefinition(objResultSet.getString("MAX_SCHED_WINDOW_TIME_DEF"));
                    comp.setNumOfPartitions(objResultSet.getInt("NUM_PARTITIONS"));
                    comp.setDescription(objResultSet.getString("DESCRIPTION"));
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
     * Return a single record using the input id
     */
    public BeanObject listById(int a_schedGroupId) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        ScheduleGroup comp = null;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                //get the SQl query for select statement
                getDbSQLforDAO().setListWhere("SCHED_GROUP_ID = ?");
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                objStatement.setInt(1, a_schedGroupId);
                logger.debug("sql statement created");

                // execute the statement
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                /*
                 * This will always be a loop of 1
                 */
                while (objResultSet.next()) {
                    comp = new ScheduleGroup();

                    String tmp1 = objResultSet.getString("AVAIL_FOR_SCHEDULING");
                    if (tmp1 != null) {
                        comp.setAvailableForScheduling(tmp1.charAt(0));
                    } else {
                        comp.setAvailableForScheduling('N');
                    }

                    comp.setId(objResultSet.getInt("SCHED_GROUP_ID"));
                    comp.setScheduleGroupContainerId(objResultSet.getInt("SCHED_GROUP_CONTAINER_ID"));
                    comp.setGroupName(objResultSet.getString("GROUP_NAME"));
                    comp.setMaxFutureScheduleInDays(objResultSet.getInt("MAX_FUTURE_SCHED_IN_DAYS"));
                    comp.setMaxScheduleWindow(objResultSet.getInt("MAX_SCHED_WINDOW"));
                    comp.setMaxScheduleWindowTimDefinition(objResultSet.getString("MAX_SCHED_WINDOW_TIME_DEF"));
                    comp.setNumOfPartitions(objResultSet.getInt("NUM_PARTITIONS"));
                    comp.setDescription(objResultSet.getString("DESCRIPTION"));
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

    public BeanObject listFullDetailById(int a_schedGroupId) throws DataAccessException {
        String condition = "AND SG.SCHED_GROUP_ID = " + a_schedGroupId;
        return listFullDetailByIdImpl(condition);
    }

    public BeanObject listAvailableFullDetailById(int a_schedGroupId) throws DataAccessException {
        String condition = "AND SG.SCHED_GROUP_ID = " + a_schedGroupId + " AND VC.AVAIL_FOR_SCHEDULING = 'Y' AND CURRENT_DATE <= VC.AVAIL_SCHED_END_DATE AND VC.AVAIL_SCHED_END_DATE >= VC.AVAIL_SCHED_START_DATE";
        return listFullDetailByIdImpl(condition);
    }

    private BeanObject listFullDetailByIdImpl(String aCondition) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery;
        ScheduleGroupContainer sGroupCont;
        ScheduleGroupToVirtualComponentDetails sGroupToVirtComponentDetails;
        ScheduleGroupToVirtualComponentDetail sGroupToVirtComponentDetail;
        VirtualComponent vComponent;
        ScheduleGroupDetail sGroupDetail = new ScheduleGroupDetail();

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                //get the SQl query for select statement
                strSQLQuery = getScheduleGroupDetailSQL(aCondition);
                logger.debug(" sql query for list is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                // execute the statement
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                boolean firstTime = true;
                while (objResultSet.next()) {
                    // First time through we get scheduleGroup/scheduleGroupToVirtual/scheduleGroupContainer/virtualComponent data
                    // every other time we get scheduleGroupToVirtual/virtualComponent
                    if (firstTime) {
                        // 1. Fill in the ScheduleGroup info
                        sGroupDetail.setId(objResultSet.getInt("SCHED_GROUP_ID"));
                        sGroupDetail.setScheduleGroupContainerId(objResultSet.getInt("SCHED_GROUP_CONTAINER_ID"));
                        sGroupDetail.setGroupName(objResultSet.getString("GROUP_NAME"));

                        String tmp1 = objResultSet.getString("SG_AVAIL");
                        if (tmp1 != null) {
                            sGroupDetail.setAvailableForScheduling(tmp1.charAt(0));
                        } else {
                            sGroupDetail.setAvailableForScheduling('N');
                        }
                        sGroupDetail.setMaxFutureScheduleInDays(objResultSet.getInt("MAX_FUTURE_SCHED_IN_DAYS"));
                        sGroupDetail.setMaxScheduleWindow(objResultSet.getInt("MAX_SCHED_WINDOW"));
                        sGroupDetail.setMaxScheduleWindowTimDefinition(objResultSet.getString("MAX_SCHED_WINDOW_TIME_DEF"));
                        sGroupDetail.setNumOfPartitions(objResultSet.getInt("NUM_PARTITIONS"));
                        sGroupDetail.setDescription(objResultSet.getString("SG_DESC"));
                        sGroupDetail.setLastModifyUserId(objResultSet.getString("SG_LMUSER"));
                        sGroupDetail.setCreateUserId(objResultSet.getString("SG_CREATE_USER"));

                        // 2. get the ScheduleGroupContainer info
                        sGroupCont = sGroupDetail.getSchedGroupCont();
                        sGroupCont.setId(objResultSet.getInt("SCHED_GROUP_CONTAINER_ID"));
                        sGroupCont.setContainerName(objResultSet.getString("CONTAINER_NAME"));
                        sGroupCont.setLastModifyUserId(objResultSet.getString("SGC_LMUSER"));
                        sGroupCont.setCreateUserId(objResultSet.getString("SGC_CREATE_USER"));

                        // 3. first array element ScheduleGroupToVirtualComponentDetail
                        sGroupToVirtComponentDetails = sGroupDetail.getvCompsDetails();         // the array

                        int virtId = objResultSet.getInt("VIRTUAL_COMP_ID");
                        if (virtId > 0) {
                            sGroupToVirtComponentDetail = new ScheduleGroupToVirtualComponentDetail();
                            sGroupToVirtComponentDetail.setDescription(objResultSet.getString("SGTV_DESC"));
                            sGroupToVirtComponentDetail.setScheduleGroupId(objResultSet.getInt("SCHED_GROUP_ID"));
                            sGroupToVirtComponentDetail.setVirtualComponentId(objResultSet.getInt("VIRTUAL_COMP_ID"));

                            vComponent = sGroupToVirtComponentDetail.getVirtComponent();
                            vComponent.setId(objResultSet.getInt("VIRTUAL_COMP_ID"));
                            vComponent.setMachineName(objResultSet.getString("MACHINE_NAME"));
                            vComponent.setAvailStartDate(objResultSet.getDate("AVAIL_SCHED_START_DATE"));
                            vComponent.setAvailEndDate(objResultSet.getDate("AVAIL_SCHED_END_DATE"));

                            String tmp2 = objResultSet.getString("VC_AVAIL");
                            if (tmp2 != null) {
                                vComponent.setAvailForScheduler(tmp2.charAt(0));
                            } else {
                                vComponent.setAvailForScheduler('N');
                            }

                            vComponent.setNumOfAvailablePartitions(objResultSet.getInt("NUM_OF_AVAIL_PARTITIONS"));
                            vComponent.setDescription(objResultSet.getString("VC_DESC"));
                            vComponent.setLastModifyUserId(objResultSet.getString("VC_LMUSER"));
                            vComponent.setCreateUserId(objResultSet.getString("VC_CREATE_USER"));

                            sGroupToVirtComponentDetails.add(sGroupToVirtComponentDetail);
                        }
                        firstTime = false;
                    } else {
                        // All other times.
                        sGroupToVirtComponentDetails = sGroupDetail.getvCompsDetails();         // the array
                        int virtId = objResultSet.getInt("VIRTUAL_COMP_ID");
                        if (virtId > 0) {
                            sGroupToVirtComponentDetail = new ScheduleGroupToVirtualComponentDetail();
                            sGroupToVirtComponentDetail.setDescription(objResultSet.getString("SGTV_DESC"));
                            sGroupToVirtComponentDetail.setScheduleGroupId(objResultSet.getInt("SCHED_GROUP_ID"));
                            sGroupToVirtComponentDetail.setVirtualComponentId(objResultSet.getInt("VIRTUAL_COMP_ID"));

                            vComponent = sGroupToVirtComponentDetail.getVirtComponent();
                            vComponent.setId(objResultSet.getInt("VIRTUAL_COMP_ID"));
                            vComponent.setMachineName(objResultSet.getString("MACHINE_NAME"));
                            vComponent.setAvailStartDate(objResultSet.getDate("AVAIL_SCHED_START_DATE"));
                            vComponent.setAvailEndDate(objResultSet.getDate("AVAIL_SCHED_END_DATE"));

                            String tmp2 = objResultSet.getString("VC_AVAIL");
                            if (tmp2 != null) {
                                vComponent.setAvailForScheduler(tmp2.charAt(0));
                            } else {
                                vComponent.setAvailForScheduler('N');
                            }

                            vComponent.setNumOfAvailablePartitions(objResultSet.getInt("NUM_OF_AVAIL_PARTITIONS"));
                            vComponent.setDescription(objResultSet.getString("VC_DESC"));
                            vComponent.setLastModifyUserId(objResultSet.getString("VC_LMUSER"));
                            vComponent.setCreateUserId(objResultSet.getString("VC_CREATE_USER"));

                            sGroupToVirtComponentDetails.add(sGroupToVirtComponentDetail);
                        }
                    }
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

        return sGroupDetail;
    }

    @Override
    protected DbSQL getDbSQL() {
        // create a new bean to hold the sql information for this DAO
        DbSQL objDbSQL = new DbSQL();

        objDbSQL.setTableName("SCHED.SCHEDULE_GROUP");
        objDbSQL.setTableJoinsForList(null);
        objDbSQL.setListFields("SCHED_GROUP_ID, SCHED_GROUP_CONTAINER_ID, GROUP_NAME, AVAIL_FOR_SCHEDULING, MAX_FUTURE_SCHED_IN_DAYS, MAX_SCHED_WINDOW, MAX_SCHED_WINDOW_TIME_DEF, NUM_PARTITIONS, DESCRIPTION, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setInsertFields("SCHED_GROUP_CONTAINER_ID, GROUP_NAME, AVAIL_FOR_SCHEDULING, MAX_FUTURE_SCHED_IN_DAYS, MAX_SCHED_WINDOW, MAX_SCHED_WINDOW_TIME_DEF, NUM_PARTITIONS, DESCRIPTION, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setUpdateFields("SCHED_GROUP_CONTAINER_ID = ?, GROUP_NAME = ?, AVAIL_FOR_SCHEDULING = ?, MAX_FUTURE_SCHED_IN_DAYS = ?, MAX_SCHED_WINDOW = ?, MAX_SCHED_WINDOW_TIME_DEF = ?, NUM_PARTITIONS = ?, DESCRIPTION = ?, LAST_MODIFY_USER = ? ");
        objDbSQL.setUpdateWhere("SCHED_GROUP_ID = ?");
        objDbSQL.setDeleteWhere("SCHED_GROUP_ID = ?");
        objDbSQL.setOrderBy("GROUP_NAME");

        return objDbSQL;
    }

    private String getScheduleGroupDetailSQL(String a_condition) {
        String sql = "SELECT "
                + " SG.SCHED_GROUP_ID, SG.GROUP_NAME, SG.AVAIL_FOR_SCHEDULING AS SG_AVAIL, SG.MAX_FUTURE_SCHED_IN_DAYS, SG.MAX_SCHED_WINDOW, SG.MAX_SCHED_WINDOW_TIME_DEF, SG.NUM_PARTITIONS, SG.DESCRIPTION AS SG_DESC, SG.LAST_MODIFY_USER AS SG_LMUSER, SG.CREATE_USER AS SG_CREATE_USER, "
                + " VC.VIRTUAL_COMP_ID, VC.MACHINE_NAME, VC.AVAIL_SCHED_START_DATE, VC.AVAIL_SCHED_END_DATE, VC.AVAIL_FOR_SCHEDULING AS VC_AVAIL, VC.NUM_OF_AVAIL_PARTITIONS, VC.DESCRIPTION AS VC_DESC, VC.LAST_MODIFY_USER AS VC_LMUSER,VC.CREATE_USER AS VC_CREATE_USER,"
                + " SGTV.DESCRIPTION AS SGTV_DESC, "
                + " SGC.SCHED_GROUP_CONTAINER_ID, SGC.CONTAINER_NAME, SGC.LAST_MODIFY_USER AS SGC_LMUSER, SGC.CREATE_USER AS SGC_CREATE_USER "
                + "FROM SCHED.SCHEDULE_GROUP_CONTAINER AS SGC, SCHED.SCHEDULE_GROUP AS SG "
                + " LEFT OUTER JOIN SCHED.SCHED_GRP_TO_VIRT_COMP AS SGTV ON (SG.SCHED_GROUP_ID=SGTV.SCHED_GROUP_ID) "
                + " LEFT OUTER JOIN SCHED.VIRTUAL_COMPONENT AS VC ON (SGTV.VIRTUAL_COMP_ID=VC.VIRTUAL_COMP_ID) "
                + "WHERE (SG.SCHED_GROUP_CONTAINER_ID=SGC.SCHED_GROUP_CONTAINER_ID) "
                + a_condition;
        return sql;
    }
}
