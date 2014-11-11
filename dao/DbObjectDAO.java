
package benchmark.invmgmt.dao;

import benchmark.invmgmt.data.BeanObject;
import benchmark.invmgmt.data.BeanObjects;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.util.MappingUtil;
import benchmark.invmgmt.util.Messages;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the base class for Db Object
 */
public abstract class DbObjectDAO {

    /**
     * Logger
     */
    private static LogHelper logger = new LogHelper(DbObjectDAO.class);
    private static final int STATEMENT_SIZE = 100;
    /**
     * connection
     */
    private Connection connection = null;
    /**
     * bean for holding SQL statements
     */
    private DbSQL objDbSQL = null;

    //empty private construtor so as to force to use the second constructor
    public DbObjectDAO() {
        //this(null); //should never be called;
    }
    //constructor to be used by all child classes

    /**
     * DbObjectDAO
     *
     * @param a_objConnection return_type
     * @pre $none
     * @post $none
     */
    protected DbObjectDAO(Connection a_objConnection) {
        setConnection(a_objConnection);
    }

    /**
     * delete
     *
     * @param a_objBeanObject
     * @return boolean
     * @exception DataAccessException
     * @post $none
     * @pre $none
     */
    public abstract boolean delete(BeanObject a_objBeanObject)
            throws DataAccessException;

    /**
     * insert
     *
     * @param a_objBeanObject
     * @return boolean
     * @exception DataAccessException
     * @pre $none
     * @post $none
     */
    public abstract int insert(BeanObject a_objBeanObject)
            throws DataAccessException;

    /**
     * Update
     *
     * @param a_objBeanObject
     * @return boolean
     * @exception DataAccessException
     * @post $none
     * @pre $none
     */
    public abstract boolean update(BeanObject a_objBeanObject)
            throws DataAccessException;

    /**
     * list
     *
     * @param aCondition
     * @return BeanObjects
     * @exception DataAccessException
     * @post $none
     * @pre $none
     */
    public abstract BeanObjects list(String aCondition)
            throws DataAccessException;

    /**
     * getDbSQL
     *
     * @return DbSQL
     * @post $none
     * @pre $none
     */
    protected abstract DbSQL getDbSQL();

    /**
     * setDbSQL
     *
     * @param a_ObjDbSQL
     * @pre $none
     * @post $none
     */
    protected final void setDbSQL(DbSQL a_ObjDbSQL) {
        objDbSQL = a_ObjDbSQL;
    }

    /**
     * getDbSQLforDAO
     *
     * @return DbSQL
     * @post $none
     * @pre $none*
     */
    protected final DbSQL getDbSQLforDAO() {
        return objDbSQL;
    }

    /**
     * Method setConnection.
     *
     * @param aConnection
     * @post $none
     * @pre $none
     */
    private final void setConnection(Connection aConnection) {
        connection = aConnection;
    }

    /**
     * Method getConnection.
     *
     * @return Connection
     * @post $none
     * @pre $none
     */
    protected final Connection getConnection() {
        return connection;
    }

    /**
     * Method getSqlDelete.
     *
     * @return String
     * @post $none
     * @pre $none
     */
    protected final String getSqlDelete() {
        StringBuffer sql = new StringBuffer(STATEMENT_SIZE);
        if (objDbSQL != null) {
            sql.append("DELETE FROM ");
            sql.append(objDbSQL.getTableName());
            sql.append(" WHERE ");
            sql.append(objDbSQL.getDeleteWhere());
        }
        return sql.toString();
    }

    /**
     * Method getSqlInsert.
     *
     * @return String
     * @pre $none
     * @post $none
     */
    protected final String getSqlInsert() {
        StringBuffer sql = new StringBuffer(STATEMENT_SIZE);
        StringBuffer questions = new StringBuffer(STATEMENT_SIZE);
        String fields = null;
        int index = -1;

        questions.append("?");
        if (objDbSQL != null) {
            fields = objDbSQL.getInsertFields();

            //Find the number of commas			
            do {
                index = fields.indexOf(",", index + 1);
                if (index >= 0) {
                    questions.append(", ?");
                }
            } while (index >= 0);

            //Prepare the sql
            sql.append("INSERT INTO ");
            sql.append(objDbSQL.getTableName());
            sql.append(" (");
            sql.append(fields);
            sql.append(") VALUES (");
            sql.append(questions.toString());
            sql.append(")");
        }
        return sql.toString();
    }

    /**
     * getSequenceNumber
     *
     * @param a_objConnection
     * @return
     * @throws DataAccessException int
     * @post $none
     * @pre $none
     */
    protected final int getSequenceNumber(Connection a_objConnection)
            throws DataAccessException {
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        int intSequenceNo = -1;

        try {
            //get the SQl query for select statement
            String strSQLQuery = "SELECT NEXT VALUE FOR "
                    + objDbSQL.getSequenceGeneratorName()
                    + "  FROM "
                    + MappingUtil.getDataMapping("table_sequence_dummy");
            logger.debug("sequence query is " + strSQLQuery);
            if (a_objConnection != null) {
                //get the prepared statement
                objStatement = a_objConnection.prepareStatement(strSQLQuery);

                //execute the statement
                objResultSet = objStatement.executeQuery();

                //get the result
                if (objResultSet.next()) {
                    //create the bean to hold the data
                    intSequenceNo = objResultSet.getInt(1);
                } else {
                    throw new DataAccessException(
                            Messages.getString("no_sequence_number"));
                }
            } else {
                throw new DataAccessException(
                        Messages.getString("database_connection_not_exists"));
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } catch (Exception e) {
            throw new DataAccessException(e);
        } finally {
            try {
                if (objResultSet != null) {
                    objResultSet.close();
                }
                if (objStatement != null) {
                    objStatement.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return intSequenceNo;

    }

    /*
     * This is used when you need to test client code in a web app.
     */
    protected final int getSequenceNumberFromDummy(Connection a_objConnection)
            throws DataAccessException {
        PreparedStatement objStatement = null;
        ResultSet objResultSet = null;
        int intSequenceNo = -1;

        try {
            //get the SQl query for select statement
            String strSQLQuery = "SELECT NEXT VALUE FOR "
                    + objDbSQL.getSequenceGeneratorName()
                    + "  FROM "
                    + objDbSQL.getSequenceGeneratorDummyTableName();
            logger.debug("sequence query is " + strSQLQuery);
            if (a_objConnection != null) {
                //get the prepared statement
                objStatement = a_objConnection.prepareStatement(strSQLQuery);

                //execute the statement
                objResultSet = objStatement.executeQuery();

                //get the result
                if (objResultSet.next()) {
                    //create the bean to hold the data
                    intSequenceNo = objResultSet.getInt(1);
                } else {
                    throw new DataAccessException(
                            Messages.getString("no_sequence_number"));
                }
            } else {
                throw new DataAccessException(
                        Messages.getString("database_connection_not_exists"));
            }
        } catch (SQLException sqle) {
            throw new DataAccessException(sqle);
        } catch (Exception e) {
            throw new DataAccessException(e);
        } finally {
            try {
                if (objResultSet != null) {
                    objResultSet.close();
                }
                if (objStatement != null) {
                    objStatement.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return intSequenceNo;

    }

    /**
     * Method getSqlInsert.
     *
     * @return String
     * @pre $none
     * @post $none
     */
    protected final String getSqlInsertForSequence() {
        StringBuffer sql = new StringBuffer(STATEMENT_SIZE);
        StringBuffer questions = new StringBuffer(STATEMENT_SIZE);
        String fields = null;
        int index = -1;

        if (objDbSQL != null) {

            questions.append(" (NEXTVAL FOR "
                    + objDbSQL.getSequenceGeneratorName() + ")");

            fields = objDbSQL.getInsertFields();

            //Find the number of commas			
            do {
                index = fields.indexOf(",", index + 1);
                if (index >= 0) {
                    questions.append(", ?");
                }
            } while (index >= 0);

            //Prepare the sql
            sql.append("INSERT INTO ");
            sql.append(objDbSQL.getTableName());
            sql.append(" (");
            sql.append(fields);
            sql.append(") VALUES (");
            sql.append(questions.toString());
            sql.append(")");
        }
        return sql.toString();
    }

    /**
     * Method getSqlList.
     *
     * @return String
     * @pre $none
     * @post $none
     *
     */
    protected final String getSqlList() {
        String orderBy = null;
        String where = null;
        StringBuffer sql = new StringBuffer(STATEMENT_SIZE);
        if (objDbSQL != null) {
            where = objDbSQL.getListWhere();
            sql.append("SELECT ");
            sql.append(objDbSQL.getListFields());
            sql.append(" FROM ");
            sql.append(objDbSQL.getTableJoinsForList());

            if (where != null) {
                if (!"".equals(where)) {
                    sql.append(" WHERE ");
                    sql.append(where);
                }
            }
            orderBy = objDbSQL.getOrderBy();
            if (orderBy != null) {
                if (!"".equals(orderBy)) {
                    sql.append(" ORDER BY ");
                    sql.append(orderBy);
                }
            }
        }
        return sql.toString();
    }

    /**
     * Method getSqlUpdate.
     *
     * @return String
     * @pre $none
     * @post $none
     */
    protected final String getSqlUpdate() {
        StringBuffer buff = new StringBuffer(STATEMENT_SIZE);
        if (objDbSQL != null) {
            //Prepare the sql
            buff.append("UPDATE ");
            buff.append(objDbSQL.getTableName());
            buff.append(" SET ");
            buff.append(objDbSQL.getUpdateFields());
            buff.append(" WHERE ");
            buff.append(objDbSQL.getUpdateWhere());
        }
        return buff.toString();
    }

    /**
     * Automatically generated method: toString
     *
     * @return String
     * @pre $none
     * @post $none
     */
    public String toString() {
        return null;
    }
}
