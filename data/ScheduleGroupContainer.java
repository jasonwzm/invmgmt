
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleGroupContainer extends BeanObject {
    private String containerName = null;

    public ScheduleGroupContainer() {
    }
    
    /**
     * @return the containerName
     */
    public String getContainerName() {
        return containerName;
    }

    /**
     * @param containerName the containerName to set
     */
    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }
    
    public String toString() {
        String buffer = "id[" + getId() + "], "
                + "containerName[" + getContainerName() + "], "
                + "lastModifyUserId[" + getLastModifyUserId() + "], "
                + "createUserId[" + getCreateUserId() + "]";

        return buffer;
    }
}
