
package benchmark.invmgmt.dao;

/**
 * DbSQL
 *
 * @author zwang
 * @version 1.0
 * @filename DbSQL.java
 */
public final class DbSQL {

    /**
     * checkNull
     */
    private static String checkNull(String a_strReturnString) {
        if (a_strReturnString == null) {
            a_strReturnString = ""; //$NON-NLS-1$
        }
        return a_strReturnString;
    }
    private String deleteWhere = null;
    private String insertFields = null;
    private String listFields = null;
    private String listWhere = null;
    private String orderBy = null;
    private String sequenceGeneratorName = null;
    private String tableJoinsForList = null;
    private String tableName = null;
    private String updateFields = null;
    private String updateWhere = null;
    private String sequenceGeneratorDummyTableName = null;

    /**
     * Automatically generated constructor: DbSQL
     */
    public DbSQL() {
    }

    /**
     * getDeleteWhere
     *
     * @return String
     */
    public final String getDeleteWhere() {
        return checkNull(deleteWhere);
    }

    /**
     * getInsertFields
     *
     * @return String
     */
    public final String getInsertFields() {
        return insertFields;
    }

    /**
     * getListFields
     *
     * @return String
     */
    public final String getListFields() {
        return listFields;
    }

    /**
     * getListWhere
     *
     * @return String
     */
    public final String getListWhere() {
        return checkNull(listWhere);

    }

    /**
     * getOrderBy
     *
     * @return String
     */
    public final String getOrderBy() {
        return orderBy;
    }

    /**
     * getSequenceGeneratorName
     *
     * @return String
     */
    public final String getSequenceGeneratorName() {
        return sequenceGeneratorName;
    }

    /**
     * getListTableName
     *
     * @return String
     */
    public final String getTableJoinsForList() {
        if (tableJoinsForList == null) {
            tableJoinsForList = getTableName();
        }
        return tableJoinsForList;
    }

    /**
     * getTableName
     *
     * @return String
     */
    public final String getTableName() {
        return tableName;
    }

    /**
     * getUpdateFields
     *
     * @return String
     */
    public final String getUpdateFields() {
        return updateFields;
    }

    /**
     * getUpdateWhere
     *
     * @return String
     */
    public final String getUpdateWhere() {
        return checkNull(updateWhere);
    }

    /**
     * setDeleteWhere
     *
     * @param string void
     */
    public final void setDeleteWhere(String string) {
        deleteWhere = string;
    }

    /**
     * setInsertFields
     *
     * @param string void
     */
    public final void setInsertFields(String string) {
        insertFields = string;
    }

    /**
     * setListFields
     *
     * @param string void
     */
    public final void setListFields(String string) {
        listFields = string;
    }

    /**
     * setListWhere
     *
     * @param string void
     */
    public final void setListWhere(String string) {
        listWhere = string;
    }

    /**
     * setOrderBy
     *
     * @param string void
     */
    public final void setOrderBy(String string) {
        orderBy = string;
    }

    /**
     * setSequenceGeneratorName
     *
     * @param string void
     */
    public final void setSequenceGeneratorName(String string) {
        sequenceGeneratorName = string;
    }

    /**
     * setListTableName
     *
     * @param string void
     */
    public final void setTableJoinsForList(String string) {
        if (string == null) {
            tableJoinsForList = getTableName();
        }
        tableJoinsForList = string;
    }

    /**
     * setTableName
     *
     * @param string void
     */
    public final void setTableName(String string) {
        tableName = string;
    }

    /**
     * setUpdateFields
     *
     * @param string void
     */
    public final void setUpdateFields(String string) {
        updateFields = string;
    }

    /**
     * setUpdateWhere
     *
     * @param string void
     */
    public final void setUpdateWhere(String string) {
        updateWhere = string;
    }

    /**
     * Automatically generated method: toString
     *
     * @return String
     */
    public String toString() {
        return null;
    }

    /**
     * @return the sequenceGeneratorDummyTableName
     */
    public String getSequenceGeneratorDummyTableName() {
        return sequenceGeneratorDummyTableName;
    }

    /**
     * @param sequenceGeneratorDummyTableName the
     * sequenceGeneratorDummyTableName to set
     */
    public void setSequenceGeneratorDummyTableName(String sequenceGeneratorDummyTableName) {
        this.sequenceGeneratorDummyTableName = sequenceGeneratorDummyTableName;
    }
}
