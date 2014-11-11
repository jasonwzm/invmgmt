
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleGroup extends BeanObject {

    private int scheduleGroupContainerId = -1;
    private int numOfPartitions = 1;
    private int maxFutureScheduleInDays = -1;   // how far in advance is someone allowed to add a schedule
    private int maxScheduleWindow = -1;         // what is the max amount of time can a schedule be aloted
    private String maxScheduleWindowTimDefinition = "HOUR";
    private String groupName = null;
    private String description = null;
    private char availableForScheduling = 'N';

    public ScheduleGroup() {
    }

    /**
     * @return the scheduleGroupContainerId
     */
    public int getScheduleGroupContainerId() {
        return scheduleGroupContainerId;
    }

    /**
     * @param scheduleGroupContainerId the scheduleGroupContainerId to set
     */
    public void setScheduleGroupContainerId(int scheduleGroupContainerId) {
        this.scheduleGroupContainerId = scheduleGroupContainerId;
    }

    /**
     * @return the numOfPartitions
     */
    public int getNumOfPartitions() {
        return numOfPartitions;
    }

    /**
     * @param numOfPartitions the numOfPartitions to set
     */
    public void setNumOfPartitions(int numOfPartitions) {
        this.numOfPartitions = numOfPartitions;
    }

    /**
     * @return the maxFutureScheduleInDays
     */
    public int getMaxFutureScheduleInDays() {
        return maxFutureScheduleInDays;
    }

    /**
     * @param maxFutureScheduleInDays the maxFutureScheduleInDays to set
     */
    public void setMaxFutureScheduleInDays(int maxFutureScheduleInDays) {
        this.maxFutureScheduleInDays = maxFutureScheduleInDays;
    }

    /**
     * @return the maxScheduleWindow
     */
    public int getMaxScheduleWindow() {
        return maxScheduleWindow;
    }

    /**
     * @param maxScheduleWindow the maxScheduleWindow to set
     */
    public void setMaxScheduleWindow(int maxScheduleWindow) {
        this.maxScheduleWindow = maxScheduleWindow;
    }

    /**
     * @return the maxScheduleWindowTimDefinition
     */
    public String getMaxScheduleWindowTimDefinition() {
        return maxScheduleWindowTimDefinition;
    }

    /**
     * @param maxScheduleWindowTimDefinition the maxScheduleWindowTimDefinition
     * to set
     */
    public void setMaxScheduleWindowTimDefinition(String maxScheduleWindowTimDefinition) {
        this.maxScheduleWindowTimDefinition = maxScheduleWindowTimDefinition;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    /**
     * @return the availableForScheduling
     */
    public char getAvailableForScheduling() {
        return availableForScheduling;
    }

    /**
     * @param availableForScheduling the availableForScheduling to set
     */
    public void setAvailableForScheduling(char availableForScheduling) {
        this.availableForScheduling = availableForScheduling;
    }

    public String toString() {
        String buffer = "id["                           + getId() + "], "
                + "scheduleGroupContainerId["           + getScheduleGroupContainerId() + "], "
                + "numOfPartitions["                    + getNumOfPartitions() + "], "
                + "maxFutureScheduleInDays["            + getMaxFutureScheduleInDays() + "], "
                + "maxScheduleWindow["                  + getMaxScheduleWindow() + "],"
                + "maxScheduleWindowTimDefinition["     + getMaxScheduleWindowTimDefinition() + "], "
                + "groupName["                          + getGroupName() + "], "
                + "description["                        + getDescription() + "], "
                + "availableForScheduling["             + getAvailableForScheduling() + "], "
                + "lastModifyUserId["                   + getLastModifyUserId() + "], "
                + "createUserId["                       + getCreateUserId() + "]";

        return buffer;
    }
}
