
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleToScheduleGroup extends BeanObject {

    private int scheduleGroupId = -1;
    private int virtualComponentId = -1;
    private int schedulId = -1;
    private int numOfPartitions = 1;

    /**
     * @return the scheduleGroupId
     */
    public int getScheduleGroupId() {
        return scheduleGroupId;
    }

    /**
     * @param scheduleGroupId the scheduleGroupId to set
     */
    public void setScheduleGroupId(int scheduleGroupId) {
        this.scheduleGroupId = scheduleGroupId;
    }

    /**
     * @return the virtualComponentId
     */
    public int getVirtualComponentId() {
        return virtualComponentId;
    }

    /**
     * @param virtualComponentId the virtualComponentId to set
     */
    public void setVirtualComponentId(int virtualComponentId) {
        this.virtualComponentId = virtualComponentId;
    }

    /**
     * @return the schedulId
     */
    public int getSchedulId() {
        return schedulId;
    }

    /**
     * @param schedulId the schedulId to set
     */
    public void setSchedulId(int schedulId) {
        this.schedulId = schedulId;
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

    public String toString() {
        String buffer = "scheduleGroupId[" + getScheduleGroupId() + "], "
                + "virtualComponentId[" + getVirtualComponentId() + "], "
                + "schedulId[" + getSchedulId() + "], "
                + "numOfPartitions[" + getNumOfPartitions() + "], "
                + "lastModifyUserId[" + getLastModifyUserId() + "], "
                + "createUserId[" + getCreateUserId() + "]";

        return buffer;
    }
}
