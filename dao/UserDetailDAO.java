
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.data.Role;
import benchmark.invmgmt.data.UserDetail;
import benchmark.invmgmt.data.UserDetails;
import benchmark.invmgmt.data.UserToRole;
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
public final class UserDetailDAO extends DbObjectDAO {

    private static LogHelper logger = new LogHelper(UserDetailDAO.class);
    private ServiceLocator objServiceLocator = ServiceLocator.getInstance();

    public UserDetailDAO() {
        //this(null);
        setDbSQL(getDbSQL());
    }

    public UserDetailDAO(Connection a_objConnection) {
        super(a_objConnection);
        setDbSQL(getDbSQL());
    }

    public boolean delete(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;
        UserDetail ud = (UserDetail) a_objBeanObject;

        try {
            if (ud != null) {
                objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

                if (objConnection != null) {
                    objConnection.setAutoCommit(false);

                    // In order to delete a user you have to first delete the links in USER_TO_ROLE
                    // otherwise the db will restrict the delete.
                    UserToRoleDAO roleDAO = new UserToRoleDAO(objConnection);
                    roleDAO.deleteByUserId(ud.getUserId());

                    strSQLQuery = getSqlDelete();
                    logger.debug(" sql query for delete is =>" + strSQLQuery);

                    //get the prepared statement
                    objStatement = objConnection.prepareStatement(strSQLQuery);
                    objStatement.setString(1, ud.getUserId());

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
                        objConnection.setAutoCommit(true);
                        objConnection.close();
                    }
                }
            } catch (SQLException sqle) {
            }
        }

        return retval;
    }

    public int insert(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        int retval = 0;
        UserDetail ud = (UserDetail) a_objBeanObject;
        try {
            if (ud != null) {
                objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

                if (objConnection != null) {
                    objConnection.setAutoCommit(false);

                    // To perform the insert, we first have to insert the user and the add all the 
                    // links to the roles.
                    strSQLQuery = getSqlInsert();
                    objStatement = objConnection.prepareStatement(strSQLQuery);

                    objStatement.setString(1, ud.getUserId());
                    objStatement.setString(2, ud.getEmail());

                    Character c1 = new Character(ud.getActive());
                    objStatement.setString(3, c1.toString());

                    objStatement.setString(4, ud.getCountry());

                    Character c2 = new Character(ud.getNotifyComponentNew());
                    objStatement.setString(5, c2.toString());

                    Character c3 = new Character(ud.getNotifyComponentUpdate());
                    objStatement.setString(6, c3.toString());

                    Character c4 = new Character(ud.getNotifyComponentDelete());
                    objStatement.setString(7, c4.toString());

                    Character c5 = new Character(ud.getNotifyScheduleNew());
                    objStatement.setString(8, c5.toString());

                    Character c6 = new Character(ud.getNotifyScheduleUpdate());
                    objStatement.setString(9, c6.toString());

                    Character c7 = new Character(ud.getNotifyScheduleDelete());
                    objStatement.setString(10, c7.toString());

                    objStatement.setString(11, ud.getLastModifyUserId());
                    // use lastModifyDate for createUser value
                    objStatement.setString(12, ud.getLastModifyUserId());

                    retval = objStatement.executeUpdate();

                    // Next we need to add all the roles for this user
                    UserToRoleDAO roleDAO = new UserToRoleDAO(objConnection);

                    for (int i = 0; i < ud.getRoles().size(); i++) {
                        Role role = (Role) ud.getRoles().get(i);
                        UserToRole link = new UserToRole();
                        link.setUserID(ud.getUserId());
                        link.setUserRole(role.getRole());
                        link.setLastModifyUserId(ud.getLastModifyUserId());
                        // use lastModifyDate for createUser value
                        link.setCreateUserId(ud.getLastModifyUserId());

                        roleDAO.insert(link);
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

    public boolean update(BeanObject a_objBeanObject) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        String strSQLQuery = null;
        boolean retval = false;
        int count;
        UserDetail ud = (UserDetail) a_objBeanObject;
        try {
            if (ud != null) {
                objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();

                if (objConnection != null) {
                    objConnection.setAutoCommit(false);

                    // Delete the current roles
                    UserToRoleDAO roleDAO = new UserToRoleDAO(objConnection);
                    roleDAO.deleteByUserId(ud.getUserId());

                    // Add back the new roles
                    for (int i = 0; i < ud.getRoles().size(); i++) {
                        Role role = (Role) ud.getRoles().get(i);
                        UserToRole link = new UserToRole();
                        link.setUserID(ud.getUserId());
                        link.setUserRole(role.getRole());
                        link.setLastModifyUserId(ud.getLastModifyUserId());
                        // use lastModifyDate for createUser value
                        link.setCreateUserId(ud.getLastModifyUserId());

                        roleDAO.insert(link);
                    }

                    // Update the user
                    strSQLQuery = getSqlUpdate();
                    objStatement = objConnection.prepareStatement(strSQLQuery);

                    objStatement.setString(1, ud.getEmail());

                    Character c1 = new Character(ud.getActive());
                    objStatement.setString(2, c1.toString());

                    objStatement.setString(3, ud.getCountry());

                    Character c2 = new Character(ud.getNotifyComponentNew());
                    objStatement.setString(4, c2.toString());

                    Character c3 = new Character(ud.getNotifyComponentUpdate());
                    objStatement.setString(5, c3.toString());

                    Character c4 = new Character(ud.getNotifyComponentDelete());
                    objStatement.setString(6, c4.toString());

                    Character c5 = new Character(ud.getNotifyScheduleNew());
                    objStatement.setString(7, c5.toString());

                    Character c6 = new Character(ud.getNotifyScheduleUpdate());
                    objStatement.setString(8, c6.toString());

                    Character c7 = new Character(ud.getNotifyScheduleDelete());
                    objStatement.setString(9, c7.toString());

                    objStatement.setString(10, ud.getLastModifyUserId());
                    // Where clause value
                    objStatement.setString(11, ud.getUserId());
                    count = objStatement.executeUpdate();
                    logger.debug("Number of records updated [" + count + "]");
                    if (count > 0) {
                        retval = true;
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

    /*
     * Return all the users that need to be notified when a new Component is created.
     */
    public BeanObjects getComponentNewNotifyIds() throws DataAccessException {
        String condition = " UP.NOTIFY_COMPONENT_NEW = 'Y' ";
        return list(condition);
    }

    /*
     * Return all the users that need to be notified when a Updated Component is created.
     */
    public BeanObjects getComponentUpdateNotifyIds() throws DataAccessException {
        String condition = " UP.NOTIFY_COMPONENT_UPDATE = 'Y' ";
        return list(condition);
    }

    /*
     * Return all the users that need to be notified when a Deleted Component is created.
     */
    public BeanObjects getComponentDeleteNotifyIds() throws DataAccessException {
        String condition = " UP.NOTIFY_COMPONENT_DELETE = 'Y' ";
        return list(condition);
    }

    /*
     * Return all the users that need to be notified when a new schedule is created.
     */
    public BeanObjects getScheduleNewNotifyIds() throws DataAccessException {
        String condition = " UP.NOTIFY_SCHEDULE_NEW = 'Y' ";
        return list(condition);
    }

    /*
     * Return all the users that need to be notified when a Updated schedule is created.
     */
    public BeanObjects getScheduleUpdateNotifyIds() throws DataAccessException {
        String condition = " UP.NOTIFY_SCHEDULE_UPDATE = 'Y' ";
        return list(condition);
    }

    /*
     * Return all the users that need to be notified when a Deleted schedule is created.
     */
    public BeanObjects getScheduleDeleteNotifyIds() throws DataAccessException {
        String condition = " UP.NOTIFY_SCHEDULE_DELETE = 'Y' ";
        return list(condition);
    }

    public BeanObjects list(String aCondition) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        UserDetail objUserDetail = null;
        UserDetails objUserDetails = new UserDetails();

        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();
            if (objConnection != null) {
                if (aCondition != null) {
                    String newWhere = getDbSQLforDAO().getListWhere() + " and " + aCondition;
                    getDbSQLforDAO().setListWhere(newWhere);
                }
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);
                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");
                // execute the statement
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                /*
                 * This will return a number of rows all pertaining to the same user.
                 * The difference will be the different roles that this user is in.
                 */
                boolean firstTime = true;
                String userId = null;
                while (objResultSet.next()) {
                    if (firstTime) {
                        firstTime = false;
                        objUserDetail = new UserDetail();
                        objUserDetails.add(objUserDetail);

                        userId = objResultSet.getString("USER_ID");
                        objUserDetail.setUserId(objResultSet.getString("USER_ID"));
                        objUserDetail.setEmail(objResultSet.getString("EMAIL"));
                        objUserDetail.setActive((objResultSet.getString("ACTIVE")).charAt(0));
                        objUserDetail.setCountry(objResultSet.getString("COUNTRY"));
                        objUserDetail.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                        objUserDetail.setCreateUserId(objResultSet.getString("CREATE_USER"));

                        objUserDetail.setNotifyComponentNew((objResultSet.getString("NOTIFY_COMPONENT_NEW")).charAt(0));
                        objUserDetail.setNotifyComponentUpdate((objResultSet.getString("NOTIFY_COMPONENT_UPDATE")).charAt(0));
                        objUserDetail.setNotifyComponentDelete((objResultSet.getString("NOTIFY_COMPONENT_DELETE")).charAt(0));

                        objUserDetail.setNotifyScheduleNew((objResultSet.getString("NOTIFY_SCHEDULE_NEW")).charAt(0));
                        objUserDetail.setNotifyScheduleUpdate((objResultSet.getString("NOTIFY_SCHEDULE_UPDATE")).charAt(0));
                        objUserDetail.setNotifyScheduleDelete((objResultSet.getString("NOTIFY_SCHEDULE_DELETE")).charAt(0));

                        Role role = new Role();
                        role.setRole((objResultSet.getString("USER_ROLE")).charAt(0));
                        role.setRoleDescription(objResultSet.getString("ROLE_DESCRIPTION"));
                        objUserDetail.getRoles().add(role);
                    } else {
                        if (userId.contentEquals(objResultSet.getString("USER_ID"))) {
                            // Not the first time
                            Role role = new Role();
                            role.setRole((objResultSet.getString("USER_ROLE")).charAt(0));
                            role.setRoleDescription(objResultSet.getString("ROLE_DESCRIPTION"));
                            objUserDetail.getRoles().add(role);
                        } else {
                            objUserDetail = new UserDetail();
                            objUserDetails.add(objUserDetail);

                            userId = objResultSet.getString("USER_ID");
                            objUserDetail.setUserId(objResultSet.getString("USER_ID"));
                            objUserDetail.setEmail(objResultSet.getString("EMAIL"));
                            objUserDetail.setActive((objResultSet.getString("ACTIVE")).charAt(0));
                            objUserDetail.setCountry(objResultSet.getString("COUNTRY"));
                            objUserDetail.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                            objUserDetail.setCreateUserId(objResultSet.getString("CREATE_USER"));

                            objUserDetail.setNotifyComponentNew((objResultSet.getString("NOTIFY_COMPONENT_NEW")).charAt(0));
                            objUserDetail.setNotifyComponentUpdate((objResultSet.getString("NOTIFY_COMPONENT_UPDATE")).charAt(0));
                            objUserDetail.setNotifyComponentDelete((objResultSet.getString("NOTIFY_COMPONENT_DELETE")).charAt(0));

                            objUserDetail.setNotifyScheduleNew((objResultSet.getString("NOTIFY_SCHEDULE_NEW")).charAt(0));
                            objUserDetail.setNotifyScheduleUpdate((objResultSet.getString("NOTIFY_SCHEDULE_UPDATE")).charAt(0));
                            objUserDetail.setNotifyScheduleDelete((objResultSet.getString("NOTIFY_SCHEDULE_DELETE")).charAt(0));

                            Role role = new Role();
                            role.setRole((objResultSet.getString("USER_ROLE")).charAt(0));
                            role.setRoleDescription(objResultSet.getString("ROLE_DESCRIPTION"));
                            objUserDetail.getRoles().add(role);
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

        return objUserDetails;

    }

    // Given a user name return the details.
    public BeanObject getUserByName(String aName) throws DataAccessException {
        // this codition will be added to the end of the where clause of the sql
        String condition = " UP.USER_ID = '" + aName + "' ";
        return item(condition);
    }

    private BeanObject item(String aCondition) throws DataAccessException {
        Connection objConnection = null;
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        String strSQLQuery = null;
        UserDetail objUserDetail = null;
        try {
            objConnection = (getConnection() != null) ? getConnection() : objServiceLocator.getDBConnection();
            if (objConnection != null) {
                if (aCondition != null) {
                    String newWhere = getDbSQLforDAO().getListWhere() + " and " + aCondition;
                    getDbSQLforDAO().setListWhere(newWhere);
                }
                strSQLQuery = getSqlList();
                logger.debug(" sql query for list is =>" + strSQLQuery);
                objStatement = objConnection.prepareStatement(strSQLQuery);
                logger.debug("sql statement created");
                // execute the statement
                objResultSet = objStatement.executeQuery();
                logger.debug("sql resultset obtained");

                boolean firstTime = true;
                while (objResultSet.next()) {
                    //create the bean to hold the data
                    if (firstTime) {
                        firstTime = false;
                        objUserDetail = new UserDetail();
                        objUserDetail.setUserId(objResultSet.getString("USER_ID"));
                        objUserDetail.setEmail(objResultSet.getString("EMAIL"));
                        objUserDetail.setActive((objResultSet.getString("ACTIVE")).charAt(0));
                        objUserDetail.setCountry(objResultSet.getString("COUNTRY"));
                        objUserDetail.setLastModifyUserId(objResultSet.getString("LAST_MODIFY_USER"));
                        objUserDetail.setCreateUserId(objResultSet.getString("CREATE_USER"));

                        objUserDetail.setNotifyComponentNew((objResultSet.getString("NOTIFY_COMPONENT_NEW")).charAt(0));
                        objUserDetail.setNotifyComponentUpdate((objResultSet.getString("NOTIFY_COMPONENT_UPDATE")).charAt(0));
                        objUserDetail.setNotifyComponentDelete((objResultSet.getString("NOTIFY_COMPONENT_DELETE")).charAt(0));

                        objUserDetail.setNotifyScheduleNew((objResultSet.getString("NOTIFY_SCHEDULE_NEW")).charAt(0));
                        objUserDetail.setNotifyScheduleUpdate((objResultSet.getString("NOTIFY_SCHEDULE_UPDATE")).charAt(0));
                        objUserDetail.setNotifyScheduleDelete((objResultSet.getString("NOTIFY_SCHEDULE_DELETE")).charAt(0));

                    }
                    Role role = new Role();
                    role.setRole((objResultSet.getString("USER_ROLE")).charAt(0));
                    role.setRoleDescription(objResultSet.getString("ROLE_DESCRIPTION"));
                    objUserDetail.getRoles().add(role);
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

        return objUserDetail;
    }

    protected DbSQL getDbSQL() {
        // create a new bean to hold the sql information for this DAO
        DbSQL objDbSQL = new DbSQL();
        objDbSQL.setTableName("INV.TUSER_PROFILE");
        objDbSQL.setTableJoinsForList("INV.TUSER_ROLE AS UR, INV.TUSER_TO_ROLE AS UTR, INV.TUSER_PROFILE AS UP");
        objDbSQL.setListFields("UP.USER_ID, UP.EMAIL, UP.ACTIVE, UP.COUNTRY, UR.USER_ROLE, UR.ROLE_DESCRIPTION, NOTIFY_COMPONENT_NEW, NOTIFY_COMPONENT_UPDATE, NOTIFY_COMPONENT_DELETE, NOTIFY_SCHEDULE_NEW, NOTIFY_SCHEDULE_DELETE, NOTIFY_SCHEDULE_UPDATE, UP.LAST_MODIFY_USER, UP.CREATE_USER ");
        objDbSQL.setInsertFields("USER_ID, EMAIL, ACTIVE, COUNTRY, NOTIFY_COMPONENT_NEW, NOTIFY_COMPONENT_UPDATE, NOTIFY_COMPONENT_DELETE, NOTIFY_SCHEDULE_NEW, NOTIFY_SCHEDULE_UPDATE, NOTIFY_SCHEDULE_DELETE, LAST_MODIFY_USER, CREATE_USER ");
        objDbSQL.setUpdateFields("EMAIL = ?, ACTIVE = ?, COUNTRY = ?, NOTIFY_COMPONENT_NEW = ?, NOTIFY_COMPONENT_UPDATE = ?, NOTIFY_COMPONENT_DELETE = ?, NOTIFY_SCHEDULE_NEW = ?, NOTIFY_SCHEDULE_DELETE = ?, NOTIFY_SCHEDULE_UPDATE = ?, LAST_MODIFY_USER = ? ");
        objDbSQL.setUpdateWhere("USER_ID = ?");
        objDbSQL.setListWhere("UP.USER_ID = UTR.USER_ID and UTR.USER_ROLE = UR.USER_ROLE ");
        objDbSQL.setDeleteWhere("USER_ID = ?");
        objDbSQL.setOrderBy("USER_ID");

        return objDbSQL;
    }
}
