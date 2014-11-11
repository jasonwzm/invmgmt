
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleGroupToVirtualComponent extends BeanObject {

    private int virtualComponentId = -1;
    private int scheduleGroupId = -1;
    private String description = null;

    public ScheduleGroupToVirtualComponent() {
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
        String buffer = "virtualComponentId[" + getVirtualComponentId() + "], "
                + "scheduleGroupId[" + getScheduleGroupId() + "], "
                + "description[" + getDescription() + "], "
                + "lastModifyUserId[" + getLastModifyUserId() + "], "
                + "createUserId[" + getCreateUserId() + "]";

        return buffer;
    }
}
