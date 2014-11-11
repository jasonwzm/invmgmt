
package benchmark.invmgmt.data;

import java.util.Date;

/**
 *
 * @author zwang
 */
public class Schedule extends BeanObject {

    private String benchmarkId = null;
    private Date scheduleStartDate = null;
    private int scheduleStartHourNumber = -1; // this is the hour when a schedule will start on a date
    private int scheduleStartNumHours = -1;  // this is the number of hours that schedule will run for
    private Date scheduleEnddate = null;
    private String customerName = null;
    private String projectMgr = null;
    private String emails = null;
    private String comment = null;

    public Schedule() {
    }

    /**
     * @return the benchmarkId
     */
    public String getBenchmarkId() {
        return benchmarkId;
    }

    /**
     * @param benchmarkId the benchmarkId to set
     */
    public void setBenchmarkId(String benchmarkId) {
        this.benchmarkId = benchmarkId;
    }

    /**
     * @return the scheduleStartDate
     */
    public Date getScheduleStartDate() {
        return scheduleStartDate;
    }

    /**
     * @param scheduleStartDate the scheduleStartDate to set
     */
    public void setScheduleStartDate(Date scheduleStartDate) {
        this.scheduleStartDate = scheduleStartDate;
    }

    /**
     * @return the scheduleEnddate
     */
    public Date getScheduleEnddate() {
        return scheduleEnddate;
    }

    /**
     * @param scheduleEnddate the scheduleEnddate to set
     */
    public void setScheduleEnddate(Date scheduleEnddate) {
        this.scheduleEnddate = scheduleEnddate;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the projectMgr
     */
    public String getProjectMgr() {
        return projectMgr;
    }

    /**
     * @param projectMgr the projectMgr to set
     */
    public void setProjectMgr(String projectMgr) {
        this.projectMgr = projectMgr;
    }

    /**
     * @return the emails
     */
    public String getEmails() {
        return emails;
    }

    /**
     * @param emails the emails to set
     */
    public void setEmails(String emails) {
        this.emails = emails;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the scheduleStartHourNumber
     */
    public int getScheduleStartHourNumber() {
        return scheduleStartHourNumber;
    }

    /**
     * @param scheduleStartHourNumber the scheduleStartHourNumber to set
     */
    public void setScheduleStartHourNumber(int scheduleStartHourNumber) {
        this.scheduleStartHourNumber = scheduleStartHourNumber;
    }

    /**
     * @return the scheduleStartNumHours
     */
    public int getScheduleStartNumHours() {
        return scheduleStartNumHours;
    }

    /**
     * @param scheduleStartNumHours the scheduleStartNumHours to set
     */
    public void setScheduleStartNumHours(int scheduleStartNumHours) {
        this.scheduleStartNumHours = scheduleStartNumHours;
    }

    public String toString() {
        String buffer = "id[" + getId() + "], "
                + "benchmarkId[" + getBenchmarkId() + "], "
                + "scheduleStartDate[" + getScheduleStartDate() + "], "
                + "scheduleEnddate[" + getScheduleEnddate() + "], "
                + "customerName[" + getCustomerName() + "], "
                + "projectMgr[" + getProjectMgr() + "], "
                + "emails[" + getEmails() + "], "
                + "comment[" + getComment() + "], "
                + "scheduleStartHourNumber[" + getScheduleStartHourNumber()+ "], "
                + "scheduleStartNumHours[" + getScheduleStartNumHours() + "], "
                + "lastModifyUserId[" + getLastModifyUserId() + "], "
                + "createUserId[" + getCreateUserId() + "]";

        return buffer;
    }
    
     public String toStringUserMessage() {
        String buffer = "BMID:" + getBenchmarkId() + ", "
                + "ScheduleStartDate:" + getScheduleStartDate() + ", "
                + "ScheduleEnddate:" + getScheduleEnddate() + ", "
                + "Customer:" + getCustomerName() + ", "
                + "Project Mgr:" + getProjectMgr() + ", ";

        return buffer;
    }

}
