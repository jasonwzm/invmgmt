
package benchmark.invmgmt.data;

import java.util.Date;

/**
 *
 * @author zwang
 */
public class VirtualComponent extends BeanObject {

    private String machineName = null;
    private Date availStartDate = null;
    private Date availEndDate = null;
    private char availForScheduler = 'N';       // Default
    private int numOfAvailablePartitions = 1;   // Default
    private String description = null;

    public VirtualComponent() {
    }

    /**
     * @return the machineName
     */
    public String getMachineName() {
        return machineName;
    }

    /**
     * @param machineName the machineName to set
     */
    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    /**
     * @return the availStartDate
     */
    public Date getAvailStartDate() {
        return availStartDate;
    }

    /**
     * @param availStartDate the availStartDate to set
     */
    public void setAvailStartDate(Date availStartDate) {
        this.availStartDate = availStartDate;
    }

    /**
     * @return the availEndDate
     */
    public Date getAvailEndDate() {
        return availEndDate;
    }

    /**
     * @param availEndDate the availEndDate to set
     */
    public void setAvailEndDate(Date availEndDate) {
        this.availEndDate = availEndDate;
    }

    /**
     * @return the availForScheduler
     */
    public char getAvailForScheduler() {
        return availForScheduler;
    }

    /**
     * @param availForScheduler the availForScheduler to set
     */
    public void setAvailForScheduler(char availForScheduler) {
        this.availForScheduler = availForScheduler;
    }

    /**
     * @return the numOfAvailablePartitions
     */
    public int getNumOfAvailablePartitions() {
        return numOfAvailablePartitions;
    }

    /**
     * @param numOfAvailablePartitions the numOfAvailablePartitions to set
     */
    public void setNumOfAvailablePartitions(int numOfAvailablePartitions) {
        this.numOfAvailablePartitions = numOfAvailablePartitions;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        String buffer = "id[" + getId() + "], "
                + "machinename[" + getMachineName() + "], "
                + "availStartDate[" + getAvailStartDate() + "], "
                + "availEndDate[" + getAvailEndDate() + "], "
                + "availForScheduler[" + getAvailForScheduler() + "], "
                + "numOfAvailablePartitions[" + getNumOfAvailablePartitions() + "], "
                + "description[" + getDescription() + "], "
                + "lastModifyUserId[" + getLastModifyUserId() + "], "
                + "createUserId[" + getCreateUserId() + "]";
        return buffer;
    }
}
