
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.data.Schedule;
import benchmark.invmgmt.data.ScheduleDetail;
import benchmark.invmgmt.data.ScheduleDetails;
import benchmark.invmgmt.data.ScheduleGroupContainer;
import benchmark.invmgmt.data.ScheduleGroupForScheduleDetail;
import benchmark.invmgmt.data.ScheduleGroupForScheduleDetails;
import benchmark.invmgmt.data.ScheduleToScheduleGroup;
import benchmark.invmgmt.data.ScheduleToScheduleGroups;
import benchmark.invmgmt.data.Schedules;
import benchmark.invmgmt.data.VirtualComponentDetail;
import benchmark.invmgmt.data.VirtualComponentDetails;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.ServiceLocatorException;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.util.ApplicationConstants;
import benchmark.invmgmt.util.DateFormatter;
import benchmark.invmgmt.util.ServiceLocator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

/**
 *
 * @author zwang
 */
public class ScheduleDAO extends DbObjectDAO {

    private static LogHelper logger = new LogHelper(VirtualComponentDAO.class);
    private ServiceLocator objServiceLocator = ServiceLocator.getInstance();

    public ScheduleDAO() {
        this(null);
        setDbSQL(getDbSQL());
    }

    public ScheduleDAO(Connection a_objConnection) {
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
        Schedule compObject = (Schedule) a_objBeanObject;

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

    /*
     * Insert one schedule item, and generate the id from the seq generator.
     */
    @Override
    public int insert(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        int retval = 0;
        int schedId = -1;

        Schedule comp = (Schedule) a_objBeanObject;
        // see if we need to generate a new schedule id for this schedule.
        // or use an existing id.
        if (comp.getId() < 0) {
            schedId = getSequenceNumberFromDummy();
        } else {
            schedId = comp.getId();
        }

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {

                strSQLQuery = getSqlInsert();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setInt(1, schedId);
                objStatement.setString(2, comp.getBenchmarkId());

                if (comp.getScheduleStartDate() != null) {
                    objStatement.setTimestamp(3, new java.sql.Timestamp(comp.getScheduleStartDate().getTime()));
                }
                if (comp.getScheduleEnddate() != null) {
                    objStatement.setTimestamp(4, new java.sql.Timestamp(comp.getScheduleEnddate().getTime()));
                }

                objStatement.setString(5, comp.getCustomerName());
                objStatement.setString(6, comp.getProjectMgr());
                objStatement.setString(7, comp.getEmails());

                if (comp.getComment() != null && comp.getComment().length() > 0) {
                    objStatement.setString(8, comp.getComment());
                } else {
                    objStatement.setNull(8, Types.VARCHAR);
                }

                objStatement.setString(9, comp.getLastModifyUserId());
                objStatement.setInt(10, comp.getScheduleStartHourNumber());
                objStatement.setInt(11, comp.getScheduleStartNumHours());
                // we will make the create user the same as lastModifyUserId
                objStatement.setString(12, comp.getLastModifyUserId());

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

    /*
     * This routine will insert a schedule and all the links to ScheduleGroup and 
     * VirtualComponent. This is all done in one unit of work.
     * It will return the schedule id.
     */
    public int insert(ScheduleDetail a_schedDetailObj) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        int retval = 0;
        int schedId = -1;

        schedId = getSequenceNumberFromDummy();

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null && schedId > 0) {
                objConnection.setAutoCommit(false);
                // Lets insert the schedule first
                strSQLQuery = getSqlInsert();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setInt(1, schedId);
                objStatement.setString(2, a_schedDetailObj.getBenchmarkId());

                if (a_schedDetailObj.getScheduleStartDate() != null) {
                    objStatement.setTimestamp(3, new java.sql.Timestamp(a_schedDetailObj.getScheduleStartDate().getTime()));
                }
                if (a_schedDetailObj.getScheduleEnddate() != null) {
                    objStatement.setTimestamp(4, new java.sql.Timestamp(a_schedDetailObj.getScheduleEnddate().getTime()));
                }

                objStatement.setString(5, a_schedDetailObj.getCustomerName());
                objStatement.setString(6, a_schedDetailObj.getProjectMgr());
                objStatement.setString(7, a_schedDetailObj.getEmails());

                if (a_schedDetailObj.getComment() != null && a_schedDetailObj.getComment().length() > 0) {
                    objStatement.setString(8, a_schedDetailObj.getComment());
                } else {
                    objStatement.setNull(8, Types.VARCHAR);
                }

                objStatement.setString(9, a_schedDetailObj.getLastModifyUserId());
                objStatement.setInt(10, a_schedDetailObj.getScheduleStartHourNumber());
                objStatement.setInt(11, a_schedDetailObj.getScheduleStartNumHours());
                // we will make the create user the same as lastModifyUserId
                objStatement.setString(12, a_schedDetailObj.getLastModifyUserId());

                retval = objStatement.executeUpdate();

                // Next, lest loop over all the virtual components and grab the id
                // Also grab the ScheduleGroup id that this virtual component is in.
                ScheduleGroupForScheduleDetail schedGroupDetail;
                ScheduleGroupContainer schedContainer;
                VirtualComponentDetails virtComponentDetails;
                VirtualComponentDetail virtComponentDetail;
                ScheduleGroupForScheduleDetails schedGroupDetails = a_schedDetailObj.getSchedGroupDetails();

                ScheduleToScheduleGroupDAO dao = new ScheduleToScheduleGroupDAO(objConnection);

                if (schedGroupDetails != null) {
                    for (int i = 0; i < schedGroupDetails.size(); i++) {
                        schedGroupDetail = (ScheduleGroupForScheduleDetail) schedGroupDetails.get(i);
                        schedContainer = schedGroupDetail.getSchedGroupCont();

                        virtComponentDetails = schedGroupDetail.getVirtComDetails();
                        if (virtComponentDetails != null) {
                            for (int j = 0; j < virtComponentDetails.size(); j++) {
                                virtComponentDetail = (VirtualComponentDetail) virtComponentDetails.get(j);

                                // We have all the parts
                                ScheduleToScheduleGroup linkObj = new ScheduleToScheduleGroup();
                                linkObj.setSchedulId(schedId);
                                linkObj.setScheduleGroupId(schedGroupDetail.getId());
                                linkObj.setVirtualComponentId(virtComponentDetail.getId());
                                linkObj.setLastModifyUserId(a_schedDetailObj.getLastModifyUserId());
                                linkObj.setNumOfPartitions(virtComponentDetail.getUsedPartitions());

                                dao.insert(linkObj);
                            }
                        }
                    }
                }
            }
        } catch (ServiceLocatorException ex) {
            try {
                objConnection.rollback();
            } catch (SQLException ex1) {
                throw new DataAccessException(ex1);
            }
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
                        objConnection.setAutoCommit(true);
                        objConnection.close();
                    }
                }
            } catch (SQLException sqle) {
                logger.error("SQLException = " + sqle.getMessage());
            }
        }

        return schedId;
    }

    @Override
    public boolean update(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;
        int count;

        Schedule comp = (Schedule) a_objBeanObject;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                strSQLQuery = getSqlUpdate();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setString(1, comp.getBenchmarkId());

                if (comp.getScheduleStartDate() != null) {
                    objStatement.setTimestamp(2, new java.sql.Timestamp(comp.getScheduleStartDate().getTime()));
                }
                if (comp.getScheduleEnddate() != null) {
                    objStatement.setTimestamp(3, new java.sql.Timestamp(comp.getScheduleEnddate().getTime()));
                }

                objStatement.setString(4, comp.getCustomerName());
                objStatement.setString(5, comp.getProjectMgr());
                objStatement.setString(6, comp.getEmails());

                if (comp.getComment() != null && comp.getComment().length() > 0) {
                    objStatement.setString(7, comp.getComment());
                } else {
                    objStatement.setNull(7, Types.VARCHAR);
                }

                objStatement.setString(8, comp.getLastModifyUserId());
                objStatement.setInt(9, comp.getScheduleStartHourNumber());
                objStatement.setInt(10, comp.getScheduleStartNumHours());

                // for the where clause
                objStatement.setInt(11, comp.getId());

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
     * This routine will update a schedule and all the links to ScheduleGroup and 
     * VirtualComponent. This is all done in one unit of work.
     * It will return the count which pertains to the schedule and should be 1 if everything works out.
     */
    public boolean update(ScheduleDetail a_schedDetailObj) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;
        int count;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                objConnection.setAutoCommit(false);

                strSQLQuery = getSqlUpdate();
                logger.debug(" sql query for insert is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");

                objStatement.setString(1, a_schedDetailObj.getBenchmarkId());

                if (a_schedDetailObj.getScheduleStartDate() != null) {
                    objStatement.setTimestamp(2, new java.sql.Timestamp(a_schedDetailObj.getScheduleStartDate().getTime()));
                }
                if (a_schedDetailObj.getScheduleEnddate() != null) {
                    objStatement.setTimestamp(3, new java.sql.Timestamp(a_schedDetailObj.getScheduleEnddate().getTime()));
                }

                objStatement.setString(4, a_schedDetailObj.getCustomerName());
                objStatement.setString(5, a_schedDetailObj.getProjectMgr());
                objStatement.setString(6, a_schedDetailObj.getEmails());

                if (a_schedDetailObj.getComment() != null && a_schedDetailObj.getComment().length() > 0) {
                    objStatement.setString(7, a_schedDetailObj.getComment());
                } else {
                    objStatement.setNull(7, Types.VARCHAR);
                }

                objStatement.setString(8, a_schedDetailObj.getLastModifyUserId());
                objStatement.setInt(9, a_schedDetailObj.getScheduleStartHourNumber());
                objStatement.setInt(10, a_schedDetailObj.getScheduleStartNumHours());

                // for the where clause
                objStatement.setInt(11, a_schedDetailObj.getId());

                count = objStatement.executeUpdate();
                logger.debug("Number of records updated [" + count + "]");

                if (count > 0) {
                    retval = true;
                }

                // Lets get the current list of link objects from SCHED_TO_SCHEDGRP
                ScheduleToScheduleGroupDAO linkListdao = new ScheduleToScheduleGroupDAO(objConnection);
                ScheduleToScheduleGroups currentLinks = (ScheduleToScheduleGroups) linkListdao.listByScheduleId(a_schedDetailObj.getId());
                ScheduleGroupForScheduleDetails schedGroupDetails = a_schedDetailObj.getSchedGroupDetails();
                ScheduleGroupForScheduleDetail schedGroupDetail;
                VirtualComponentDetails virtComponentDetails;
                VirtualComponentDetail virtComponentDetail;

                ScheduleToScheduleGroups linksToDelete = new ScheduleToScheduleGroups();
                ScheduleToScheduleGroups linksToAdd = new ScheduleToScheduleGroups();
                ScheduleToScheduleGroups linksToUpdate = new ScheduleToScheduleGroups();
                ScheduleToScheduleGroup linkComp;

                // First lest find the links we need to add.
                int i, j, k;
                if (schedGroupDetails != null) {
                    for (i = 0; i < schedGroupDetails.size(); i++) {
                        schedGroupDetail = (ScheduleGroupForScheduleDetail) schedGroupDetails.get(i);

                        virtComponentDetails = schedGroupDetail.getVirtComDetails();
                        if (virtComponentDetails != null) {
                            for (j = 0; j < virtComponentDetails.size(); j++) {
                                virtComponentDetail = (VirtualComponentDetail) virtComponentDetails.get(j);
                                linkComp = currentLinks.findLinkComponent(a_schedDetailObj.getId(), schedGroupDetail.getId(), virtComponentDetail.getId());
                                if (linkComp == null) {
                                    ScheduleToScheduleGroup newComp = new ScheduleToScheduleGroup();
                                    newComp.setSchedulId(a_schedDetailObj.getId());
                                    newComp.setScheduleGroupId(schedGroupDetail.getId());
                                    newComp.setVirtualComponentId(virtComponentDetail.getId());
                                    newComp.setNumOfPartitions(virtComponentDetail.getUsedPartitions());
                                    newComp.setLastModifyUserId(a_schedDetailObj.getLastModifyUserId());
                                    linksToAdd.add(newComp);
                                } else {
                                    // we have the link but is the used partitions different.
                                    if (linkComp.getNumOfPartitions() != virtComponentDetail.getUsedPartitions()) {
                                        // add this to the update list
                                        ScheduleToScheduleGroup newComp = new ScheduleToScheduleGroup();
                                        newComp.setSchedulId(linkComp.getSchedulId());
                                        newComp.setScheduleGroupId(linkComp.getScheduleGroupId());
                                        newComp.setVirtualComponentId(linkComp.getVirtualComponentId());
                                        newComp.setNumOfPartitions(virtComponentDetail.getUsedPartitions());
                                        newComp.setLastModifyUserId(a_schedDetailObj.getLastModifyUserId());
                                        linksToUpdate.add(newComp);
                                    }
                                }
                            }
                        }
                    }

                    // Second lets find the links we need to delete
                    boolean found;
                    for (i = 0; i < currentLinks.size(); i++) {
                        found = false;
                        linkComp = (ScheduleToScheduleGroup) currentLinks.get(i);

                        for (j = 0; j < schedGroupDetails.size(); j++) {
                            schedGroupDetail = (ScheduleGroupForScheduleDetail) schedGroupDetails.get(j);

                            virtComponentDetails = schedGroupDetail.getVirtComDetails();
                            if (virtComponentDetails != null) {
                                for (k = 0; k < virtComponentDetails.size(); k++) {
                                    virtComponentDetail = (VirtualComponentDetail) virtComponentDetails.get(k);

                                    if (linkComp.getSchedulId() == a_schedDetailObj.getId()
                                            && linkComp.getScheduleGroupId() == schedGroupDetail.getId()
                                            && linkComp.getVirtualComponentId() == virtComponentDetail.getId()) {
                                        found = true;
                                        break;
                                    }
                                }
                            }
                            if (found) {
                                break;
                            }
                        }
                        if (found == false) {
                            linksToDelete.add(linkComp);
                        }
                    }
                }
                // Lets perform the deletes
                for (i = 0; i < linksToDelete.size(); i++) {
                    linkComp = (ScheduleToScheduleGroup) linksToDelete.get(i);
                    linkListdao.delete(linkComp);
                }
                // Lets perform the inserts
                for (i = 0; i < linksToAdd.size(); i++) {
                    linkComp = (ScheduleToScheduleGroup) linksToAdd.get(i);
                    linkListdao.insert(linkComp);
                }
                // Lets perform the updates
                for (i = 0; i < linksToUpdate.size(); i++) {
                    linkComp = (ScheduleToScheduleGroup) linksToUpdate.get(i);
                    linkListdao.update(linkComp);
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
                        objConnection.setAutoCommit(true);
                        objConnection.close();
                    }
                }
            } catch (SQLException sqle) {
            }
        }

        return retval;
    }

    public BeanObjects getActiveSchedules() throws DataAccessException {
        Date d = new Date();

        String dateStr = "'" + DateFormatter.convertDateToDBString(d) + "-00.00.00'";

        String condition = " SCHEDULE_END_DATE >= " + dateStr;
        return list(condition);
    }

    @Override
    public BeanObjects list(String aCondition) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        Schedule comp = null;
        Schedules comps = new Schedules();

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();
            if (objConnection != null) {
                if (aCondition != null) {
                    String newWhere = getDbSQLforDAO().getListWhere() + " " + aCondition;
                    getDbSQLforDAO().setListWhere(newWhere);
                }
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);
                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                while (objResultSet.next()) {
                    comp = new Schedule();

                    comp.setId(objResultSet.getInt("SCHEDULE_ID"));
                    comp.setBenchmarkId(objResultSet.getString("BENCHMARK_ID"));
                    comp.setScheduleStartDate(objResultSet.getTimestamp("SCHEDULE_START_DATE"));
                    comp.setScheduleEnddate(objResultSet.getTimestamp("SCHEDULE_END_DATE"));
                    comp.setCustomerName(objResultSet.getString("CUSTOMER_NAME"));
                    comp.setProjectMgr(objResultSet.getString("PROJECT_MGR"));
                    comp.setEmails(objResultSet.getString("EMAILS"));
                    comp.setComment(objResultSet.getString("COMMENT"));

                    comp.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    comp.setCreateUserId(objResultSet.getString("CREATE_USER"));
                    comp.setScheduleStartHourNumber(objResultSet.getInt("SCHEDULE_START_HOUR_NUM"));
                    comp.setScheduleStartNumHours(objResultSet.getInt("SCHEDULE_NUM_OF_HOURS"));

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
     * This routine will return all the schedules that a virtual component is used.
     * ie; where used.
     * It has been put in the SheduleDAO class because you are looking for Schedules
     */
    public BeanObjects listSchedulesByVirtualCompId(int a_virtCompId) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        Schedule sched = null;
        Schedules scheds = new Schedules();

        getDbSQLforDAO().setListFields("AL1.SCHEDULE_ID, AL1.BENCHMARK_ID, AL1.SCHEDULE_START_DATE, AL1.SCHEDULE_END_DATE, AL1.CUSTOMER_NAME, AL1.PROJECT_MGR, AL1.EMAILS, AL1.COMMENT, AL1.LAST_MODIFY_USER, AL1.SCHEDULE_START_HOUR_NUM, AL1.SCHEDULE_NUM_OF_HOURS, AL1.CREATE_USER ");
        getDbSQLforDAO().setTableJoinsForList(" SCHED.SCHEDULE AL1, SCHED.VIRTUAL_COMPONENT AL2, SCHED.SCHED_TO_SCHEDGRP AL3 ");
        getDbSQLforDAO().setListWhere("(AL3.VIRTUAL_COMP_ID=AL2.VIRTUAL_COMP_ID AND AL3.SCHEDULE_ID=AL1.SCHEDULE_ID) AND AL2.VIRTUAL_COMP_ID = " + Integer.toString(a_virtCompId));

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();
            if (objConnection != null) {
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);
                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                while (objResultSet.next()) {
                    sched = new Schedule();

                    sched.setId(objResultSet.getInt("SCHEDULE_ID"));
                    sched.setBenchmarkId(objResultSet.getString("BENCHMARK_ID"));
                    sched.setScheduleStartDate(objResultSet.getTimestamp("SCHEDULE_START_DATE"));
                    sched.setScheduleEnddate(objResultSet.getTimestamp("SCHEDULE_END_DATE"));
                    sched.setCustomerName(objResultSet.getString("CUSTOMER_NAME"));
                    sched.setProjectMgr(objResultSet.getString("PROJECT_MGR"));
                    sched.setEmails(objResultSet.getString("EMAILS"));
                    sched.setComment(objResultSet.getString("COMMENT"));

                    sched.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    sched.setCreateUserId(objResultSet.getString("CREATE_USER"));
                    sched.setScheduleStartHourNumber(objResultSet.getInt("SCHEDULE_START_HOUR_NUM"));
                    sched.setScheduleStartNumHours(objResultSet.getInt("SCHEDULE_NUM_OF_HOURS"));

                    scheds.add(sched);
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

        return scheds;
    }

    /*
     * Return a single record using the input scheduleId
     */
    public BeanObject listById(int a_scheduleId) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        Schedule comp = null;

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

            if (objConnection != null) {
                //get the SQl query for select statement
                getDbSQLforDAO().setListWhere("SCHEDULE_ID = ?");
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);

                objStatement = objConnection.prepareStatement(strSQLQuery);
                objStatement.setInt(1, a_scheduleId);
                logger.debug("sql statement created");

                // execute the statement
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                /*
                 * This will always be a loop of 1
                 */
                while (objResultSet.next()) {
                    comp = new Schedule();
                    comp.setId(objResultSet.getInt("SCHEDULE_ID"));
                    comp.setBenchmarkId(objResultSet.getString("BENCHMARK_ID"));
                    comp.setScheduleStartDate(objResultSet.getTimestamp("SCHEDULE_START_DATE"));
                    comp.setScheduleEnddate(objResultSet.getTimestamp("SCHEDULE_END_DATE"));
                    comp.setCustomerName(objResultSet.getString("CUSTOMER_NAME"));
                    comp.setProjectMgr(objResultSet.getString("PROJECT_MGR"));
                    comp.setEmails(objResultSet.getString("EMAILS"));
                    comp.setComment(objResultSet.getString("COMMENT"));
                    comp.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                    comp.setCreateUserId(objResultSet.getString("CREATE_USER"));
                    comp.setScheduleStartHourNumber(objResultSet.getInt("SCHEDULE_START_HOUR_NUM"));
                    comp.setScheduleStartNumHours(objResultSet.getInt("SCHEDULE_NUM_OF_HOURS"));
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

    public BeanObject listFullDetailByScheduleId(int a_schedId) throws DataAccessException {
        Schedule sched = null;
        String condition = "AL1.SCHEDULE_ID = " + Integer.toString(a_schedId) + " ";
        ScheduleDetail sd = (ScheduleDetail) listFullDetailById(condition);

        if (sd == null) {
            /*
             * No detail data came back, lets see if the schedule only comes back
             * without all of the component data.
             */
            sched = (Schedule) listById(a_schedId);
            if (sched != null) {
                // lets create a ScheduleDetail from a Schedule
                sd = new ScheduleDetail(sched);
            }
        }

        return sd;
    }

    /*
     * Return all the schedule details for a given virtual component id
     */
    public BeanObjects listFullDetailByVirtualCompId(int a_virtualId) throws DataAccessException {
        Schedule sched = null;
        Schedules scheds = null;
        ScheduleDetails schedDetails = null;

        // 1. Lets get all the schedules where the virtual component is used.
        scheds = (Schedules) listSchedulesByVirtualCompId(a_virtualId);
        if (scheds != null) {
            schedDetails = new ScheduleDetails();
            // 2. get the ScheduleDetail for each schedule
            for (int i = 0; i < scheds.size(); i++) {
                sched = (Schedule) scheds.get(i);
                String condition = "AL1.SCHEDULE_ID = "
                        + Integer.toString(sched.getId())
                        + " AND AL4.VIRTUAL_COMP_ID = "
                        + Integer.toString(a_virtualId)
                        + " AND AL4.AVAIL_FOR_SCHEDULING = 'Y' ";
                ScheduleDetail sd = (ScheduleDetail) listFullDetailById(condition);

                if (sd != null) {
                    schedDetails.add(sd);
                }
            }
        }

        return schedDetails;
    }

    /*
     * This rutine will return 0 - n ScheduleDetail objects that overlap with the 
     * schedule dates and component.
     * action = ApplicationConstants.SCHEDULE_UPDATE or ApplicationConstants.SCHEDULE_NEW
     */
    public ScheduleDetails getPossibleScheduleConflicts(Date a_schedStartDate, Date a_schedEndDate, int a_virtualId, int action) throws DataAccessException {
        Schedule sched = null;
        Schedules scheds = null;
        ScheduleDetails schedDetails = null;
        String condition;

        String dateStrStart = DateFormatter.convertDateToDBdateTimeString(a_schedStartDate);
        String dateStrEnd = DateFormatter.convertDateToDBdateTimeString(a_schedEndDate);

        // 1. Lets get all the schedules where the virtual component is used.
        scheds = (Schedules) listSchedulesByVirtualCompId(a_virtualId);
        if (scheds != null) {
            schedDetails = new ScheduleDetails();
            // 2. get the ScheduleDetail for each schedule
            for (int i = 0; i < scheds.size(); i++) {
                sched = (Schedule) scheds.get(i);
                
                if (action == ApplicationConstants.SCHEDULE_NEW) {
                    condition = "AL1.SCHEDULE_ID = " + Integer.toString(sched.getId());
                } else {
                    condition = "AL1.SCHEDULE_ID <> " + Integer.toString(sched.getId());
                }
                
                condition = condition 
                        + " AND AL4.VIRTUAL_COMP_ID = "
                        + Integer.toString(a_virtualId)
                        + " AND AL4.AVAIL_FOR_SCHEDULING = 'Y' "
                        + " AND TIMESTAMP('" + dateStrStart + "') <= TIMESTAMP(AL1.SCHEDULE_END_DATE)" 
                        + " AND TIMESTAMP('" + dateStrEnd   + "') >= TIMESTAMP(AL1.SCHEDULE_START_DATE)";
                
                ScheduleDetail sd = (ScheduleDetail) listFullDetailById(condition);

                if (sd != null) {
                    schedDetails.add(sd);
                }
            }
            if (schedDetails.size() == 0) {
                schedDetails = null;
            }
        }
        return schedDetails;
    }

    private BeanObject listFullDetailById(String a_condition) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery;
        VirtualComponentDetails vComDetails = null;
        ScheduleGroupContainer schedGroupCont = null;
        ScheduleGroupForScheduleDetails sGroupForSchedules = null;
        ScheduleGroupForScheduleDetail sGroupForSchedule = null;
        ScheduleDetail sDetail = new ScheduleDetail();
        int currSchedGroupId = -1;
        int prevSchedGroupId = -1;
        boolean hasData = false;

        try {
            if (a_condition != null && a_condition.length() > 0) {
                objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

                if (objConnection != null) {
                    //get the SQl query for select statement
                    strSQLQuery = getScheduleDetailSQL(a_condition);
                    logger.debug(" sql query for list is =>" + strSQLQuery);

                    objStatement = objConnection.prepareStatement(strSQLQuery);
                    logger.debug("sql statement created");

                    objResultSet = objStatement.executeQuery();
                    logger.debug("sql resultset obtained");

                    while (objResultSet.next()) {
                        hasData = true;
                        if (currSchedGroupId == -1) {
                            // schedule Detail
                            sDetail.setBenchmarkId(objResultSet.getString("S_BENCHMARK_ID"));
                            sDetail.setScheduleStartDate(objResultSet.getTimestamp("S_SCHEDULE_START_DATE"));
                            sDetail.setScheduleEnddate(objResultSet.getTimestamp("S_SCHEDULE_END_DATE"));
                            sDetail.setCustomerName(objResultSet.getString("S_CUSTOMER_NAME"));
                            sDetail.setProjectMgr(objResultSet.getString("S_PROJECT_MGR"));
                            sDetail.setEmails(objResultSet.getString("S_EMAILS"));
                            sDetail.setComment(objResultSet.getString("S_COMMENT"));
                            sDetail.setId(objResultSet.getInt("SCHEDULE_ID"));
                            sDetail.setLastModifyUserId(objResultSet.getString("S_LAST_MODIFY_USER"));
                            sDetail.setCreateUserId(objResultSet.getString("S_CREATE_USER"));
                            sDetail.setScheduleStartHourNumber(objResultSet.getInt("S_SCHEDULE_START_HOUR_NUM"));
                            sDetail.setScheduleStartNumHours(objResultSet.getInt("S_SCHEDULE_NUM_OF_HOURS"));

                        }
                        // schedule group for schedule detail
                        sGroupForSchedules = sDetail.getSchedGroupDetails();
                        currSchedGroupId = objResultSet.getInt("SCHED_GROUP_ID");

                        if (currSchedGroupId != prevSchedGroupId) {
                            sGroupForSchedule = new ScheduleGroupForScheduleDetail();
                            sGroupForSchedule.setGroupName(objResultSet.getString("SG_GROUP_NAME"));
                            sGroupForSchedule.setId(currSchedGroupId);
                            sGroupForSchedule.setLastModifyUserId(objResultSet.getString("SG_LAST_MODIFY_USER"));
                            sGroupForSchedule.setCreateUserId(objResultSet.getString("SG_CREATE_USER"));
                            sGroupForSchedule.setScheduleGroupContainerId(objResultSet.getInt("SCHED_GROUP_CONTAINER_ID"));

                            String tmp1 = objResultSet.getString("SG_AVAIL_FOR_SCHEDULING");
                            if (tmp1 != null) {
                                sGroupForSchedule.setAvailableForScheduling(tmp1.charAt(0));
                            } else {
                                sGroupForSchedule.setAvailableForScheduling('N');
                            }
                            sGroupForSchedule.setMaxFutureScheduleInDays(objResultSet.getInt("SG_MAX_FUTURE_SCHED_IN_DAYS"));
                            sGroupForSchedule.setMaxScheduleWindow(objResultSet.getInt("SG_MAX_SCHED_WINDOW"));
                            sGroupForSchedule.setMaxScheduleWindowTimDefinition(objResultSet.getString("SG_MAX_SCHED_WINDOW_TIME_DEF"));
                            sGroupForSchedule.setNumOfPartitions(objResultSet.getInt("SG_NUM_PARTITIONS"));
                            sGroupForSchedule.setDescription(objResultSet.getString("SG_DESCRIPTION"));
                            sGroupForSchedules.add(sGroupForSchedule);

                            // schedule group container
                            schedGroupCont = sGroupForSchedule.getSchedGroupCont();
                            schedGroupCont.setContainerName(objResultSet.getString("CONTAINER_NAME"));
                            schedGroupCont.setId(objResultSet.getInt("SCHED_GROUP_CONTAINER_ID"));
                            schedGroupCont.setLastModifyUserId(objResultSet.getString("CONT_LAST_MODIFY_USER"));
                            schedGroupCont.setCreateUserId(objResultSet.getString("CONT_CREATE_USER"));
                        }

                        // virtual component detail
                        vComDetails = sGroupForSchedule.getVirtComDetails();
                        VirtualComponentDetail vc = new VirtualComponentDetail();
                        vc.setId(objResultSet.getInt("VIRTUAL_COMP_ID"));
                        vc.setMachineName(objResultSet.getString("V_MACHINE_NAME"));
                        vc.setAvailStartDate(objResultSet.getDate("V_AVAIL_SCHED_START_DATE"));
                        vc.setAvailEndDate(objResultSet.getDate("V_AVAIL_SCHED_END_DATE"));

                        String tmp2 = objResultSet.getString("V_AVAIL_FOR_SCHEDULING");
                        if (tmp2 != null) {
                            vc.setAvailForScheduler(tmp2.charAt(0));
                        } else {
                            vc.setAvailForScheduler('N');
                        }
                        vc.setNumOfAvailablePartitions(objResultSet.getInt("V_NUM_OF_AVAIL_PARTITIONS"));
                        vc.setUsedPartitions(objResultSet.getInt("V_USED_PARTITIONS"));
                        vc.setDescription(objResultSet.getString("V_DESCRIPTION"));
                        vc.setLastModifyUserId(objResultSet.getString("V_LAST_MODIFY_USER"));
                        vc.setCreateUserId(objResultSet.getString("V_CREATE_USER"));
                        vComDetails.add(vc);

                        prevSchedGroupId = currSchedGroupId;
                    }
                    if (hasData == false) {
                        /*
                         * No data came from the above query. 
                         */
                        sDetail = null;
                    }
                }
            } else {
                logger.error(" Invalid schedule condition passed =>" + a_condition);
                throw new DataAccessException("Invalid schedule condition passed =>" + a_condition);
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

        return sDetail;
    }

    public int getSequenceNumberFromDummy() throws DataAccessException {
        Connection objConnection = null;
        int seqNum = -1;
        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();
            seqNum = super.getSequenceNumberFromDummy(objConnection);

        } catch (ServiceLocatorException ex) {
            throw new DataAccessException(ex);
        } finally {
            try {
                if (objConnection != null) {
                    // In case a connection was passed in we did not get our own.
                    if (getConnection() == null) {
                        objConnection.close();
                    }
                }
            } catch (Exception e) {
            }
        }

        return seqNum;
    }

    @Override
    protected DbSQL getDbSQL() {
        // create a new bean to hold the sql information for this DAO
        DbSQL objDbSQL = new DbSQL();

        objDbSQL.setTableName("SCHED.SCHEDULE");
        objDbSQL.setTableJoinsForList(null);
        objDbSQL.setListFields("SCHEDULE_ID, BENCHMARK_ID, SCHEDULE_START_DATE, SCHEDULE_END_DATE, CUSTOMER_NAME, PROJECT_MGR, EMAILS, COMMENT, LAST_MODIFY_USER, SCHEDULE_START_HOUR_NUM, SCHEDULE_NUM_OF_HOURS, CREATE_USER ");
        objDbSQL.setInsertFields("SCHEDULE_ID, BENCHMARK_ID, SCHEDULE_START_DATE, SCHEDULE_END_DATE, CUSTOMER_NAME, PROJECT_MGR, EMAILS, COMMENT, LAST_MODIFY_USER, SCHEDULE_START_HOUR_NUM, SCHEDULE_NUM_OF_HOURS, CREATE_USER ");
        objDbSQL.setUpdateFields("BENCHMARK_ID = ?, SCHEDULE_START_DATE = ?, SCHEDULE_END_DATE = ?, CUSTOMER_NAME = ?, PROJECT_MGR = ?, EMAILS = ?, COMMENT = ?, LAST_MODIFY_USER = ?, SCHEDULE_START_HOUR_NUM = ?, SCHEDULE_NUM_OF_HOURS = ? ");
        objDbSQL.setUpdateWhere("SCHEDULE_ID = ?");
        objDbSQL.setDeleteWhere("SCHEDULE_ID = ?");
        objDbSQL.setOrderBy(null);
        objDbSQL.setSequenceGeneratorName("SCHED.SEQ_SCHEDULE");
        objDbSQL.setSequenceGeneratorDummyTableName("INV.TDUMMY1");

        return objDbSQL;

    }

    private String getScheduleDetailSQL(String a_condition) {
        String sql = " SELECT "
                + "AL1.BENCHMARK_ID         AS S_BENCHMARK_ID,"
                + "AL1.SCHEDULE_START_DATE  AS S_SCHEDULE_START_DATE,"
                + "AL1.SCHEDULE_END_DATE    AS S_SCHEDULE_END_DATE,"
                + "AL1.CUSTOMER_NAME        AS S_CUSTOMER_NAME,"
                + "AL1.PROJECT_MGR          AS S_PROJECT_MGR,"
                + "AL1.EMAILS               AS S_EMAILS,"
                + "AL1.COMMENT              AS S_COMMENT,"
                + "AL1.LAST_MODIFY_USER     AS S_LAST_MODIFY_USER,"
                + "AL1.CREATE_USER          AS S_CREATE_USER,"
                + "AL1.SCHEDULE_START_HOUR_NUM AS S_SCHEDULE_START_HOUR_NUM,"
                + "AL1.SCHEDULE_NUM_OF_HOURS AS S_SCHEDULE_NUM_OF_HOURS,"
                + "AL2.SCHEDULE_ID,"
                + "AL3.GROUP_NAME                AS SG_GROUP_NAME,"
                + "AL3.AVAIL_FOR_SCHEDULING      AS SG_AVAIL_FOR_SCHEDULING,"
                + "AL3.MAX_FUTURE_SCHED_IN_DAYS  AS SG_MAX_FUTURE_SCHED_IN_DAYS,"
                + "AL3.MAX_SCHED_WINDOW          AS SG_MAX_SCHED_WINDOW,"
                + "AL3.MAX_SCHED_WINDOW_TIME_DEF AS SG_MAX_SCHED_WINDOW_TIME_DEF,"
                + "AL3.NUM_PARTITIONS            AS SG_NUM_PARTITIONS,"
                + "AL3.DESCRIPTION               AS SG_DESCRIPTION,"
                + "AL3.LAST_MODIFY_USER          AS SG_LAST_MODIFY_USER,"
                + "AL3.CREATE_USER               AS SG_CREATE_USER,"
                + "AL5.SCHED_GROUP_CONTAINER_ID,"
                + "AL5.CONTAINER_NAME,"
                + "AL5.LAST_MODIFY_USER         AS CONT_LAST_MODIFY_USER,"
                + "AL5.CREATE_USER              AS CONT_CREATE_USER,"
                + "AL4.MACHINE_NAME             AS V_MACHINE_NAME,"
                + "AL4.AVAIL_SCHED_START_DATE   AS V_AVAIL_SCHED_START_DATE,"
                + "AL4.AVAIL_SCHED_END_DATE     AS V_AVAIL_SCHED_END_DATE,"
                + "AL4.AVAIL_FOR_SCHEDULING     AS V_AVAIL_FOR_SCHEDULING,"
                + "AL4.NUM_OF_AVAIL_PARTITIONS  AS V_NUM_OF_AVAIL_PARTITIONS,"
                + "AL4.DESCRIPTION              AS V_DESCRIPTION,"
                + "AL4.LAST_MODIFY_USER         AS V_LAST_MODIFY_USER,"
                + "AL4.CREATE_USER              AS V_CREATE_USER,"
                + "AL2.NUM_PARTITIONS           AS V_USED_PARTITIONS,"
                + "AL2.VIRTUAL_COMP_ID,"
                + "AL2.SCHED_GROUP_ID"
                + " FROM "
                + "SCHED.SCHEDULE AL1,"
                + "SCHED.SCHED_TO_SCHEDGRP AL2,"
                + "SCHED.SCHEDULE_GROUP AL3,"
                + "SCHED.VIRTUAL_COMPONENT AL4,"
                + "SCHED.SCHEDULE_GROUP_CONTAINER AL5"
                + " WHERE "
                + " AL2.SCHEDULE_ID=AL1.SCHEDULE_ID AND "
                + " AL3.SCHED_GROUP_ID=AL2.SCHED_GROUP_ID AND "
                + " AL4.VIRTUAL_COMP_ID=AL2.VIRTUAL_COMP_ID AND "
                + " AL5.SCHED_GROUP_CONTAINER_ID=AL3.SCHED_GROUP_CONTAINER_ID  AND "
                + a_condition
                + " ORDER BY SCHEDULE_ID,SCHED_GROUP_ID";
        return sql;
    }
}
