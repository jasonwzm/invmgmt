
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleGroupForScheduleDetail extends ScheduleGroup {
    private ScheduleGroupContainer schedGroupCont = null;
    private VirtualComponentDetails virtComDetails = null;
    
    public ScheduleGroupForScheduleDetail(ScheduleGroup aSchedGroup) {
        this.setId(aSchedGroup.getId());
        this.setLastModifyUserId(aSchedGroup.getLastModifyUserId());
        this.setCreateUserId(aSchedGroup.getCreateUserId());
        this.setAvailableForScheduling(aSchedGroup.getAvailableForScheduling());
        this.setDescription(aSchedGroup.getDescription());
        this.setGroupName(aSchedGroup.getGroupName());
        this.setMaxFutureScheduleInDays(aSchedGroup.getMaxFutureScheduleInDays());
        this.setMaxScheduleWindow(aSchedGroup.getMaxScheduleWindow());
        this.setMaxScheduleWindowTimDefinition(aSchedGroup.getMaxScheduleWindowTimDefinition());
        this.setNumOfPartitions(aSchedGroup.getNumOfPartitions());
        this.setScheduleGroupContainerId(aSchedGroup.getScheduleGroupContainerId());
    }
    
    public ScheduleGroupForScheduleDetail() {}

    /**
     * @return the schedGroupCont
     */
    public ScheduleGroupContainer getSchedGroupCont() {
        if (schedGroupCont == null) {
            schedGroupCont = new ScheduleGroupContainer();
        }
        
        return schedGroupCont;
    }

    /**
     * @return the virtComDetails
     */
    public VirtualComponentDetails getVirtComDetails() {
        if (virtComDetails == null) {
            virtComDetails = new VirtualComponentDetails();
        }
        return virtComDetails;
    }
}
