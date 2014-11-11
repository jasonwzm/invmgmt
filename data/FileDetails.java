
package benchmark.invmgmt.data;

/**
 * FileDetails
 *
 * @author zwang
 * @version 1.0
 * @filename FileDetails.java
 */
public class FileDetails {
    //init the fields

    /**
     * Automatically generated constructor: FileDetails
     */
    public FileDetails() {
    }
    private String contentType = null;
    private String systemFileName = null;
    private String userFileName = null;
    private String FileName = null;
    private String uploadedFilePath = null;

    /**
     * getContentType
     *
     * @return String
     * @post $none
     * @pre $none
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * getFileName
     *
     * @return String
     * @post $none
     * @pre $none
     */
    public String getFileName() {
        return FileName;
    }

    /**
     * getSystemFileName
     *
     * @return String
     * @pre $none
     * @post $none
     */
    public String getSystemFileName() {
        return systemFileName;
    }

    /**
     * getUploadedFilePath
     *
     * @return String
     * @pre $none
     * @post $none
     */
    public String getUploadedFilePath() {
        return uploadedFilePath;
    }

    /**
     * getUserFileName
     *
     * @return String
     * @pre $none
     * @post $none
     */
    public String getUserFileName() {
        return userFileName;
    }

    /**
     * setContentType
     *
     * @param string void
     * @post $none
     * @pre $none
     */
    public void setContentType(String string) {
        contentType = string;
    }

    /**
     * setFileName
     *
     * @param string void
     * @post $none
     * @pre $none
     */
    public void setFileName(String string) {
        FileName = string;
    }

    /**
     * setSystemFileName
     *
     * @param string void
     * @post $none
     * @pre $none
     */
    public void setSystemFileName(String string) {
        systemFileName = string;
    }

    /**
     * setUploadedFilePath
     *
     * @param string void
     * @post $none
     * @pre $none
     */
    public void setUploadedFilePath(String string) {
        uploadedFilePath = string;
    }

    /**
     * setUserFileName
     *
     * @param string void
     * @pre $none
     * @post $none
     */
    public void setUserFileName(String string) {
        userFileName = string;
    }

    /**
     * Automatically generated method: toString
     *
     * @return String
     */
    public String toString() {
        return null;
    }
}
