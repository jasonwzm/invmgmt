
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleDetail extends Schedule {
    private ScheduleGroupForScheduleDetails schedGroupDetails = null;

    public ScheduleDetail() {}
    
    public ScheduleDetail(Schedule aSched) {
        this.setBenchmarkId(aSched.getBenchmarkId());
        this.setComment(aSched.getComment());
        this.setCustomerName(aSched.getCustomerName());
        this.setEmails(aSched.getEmails());
        this.setProjectMgr(aSched.getProjectMgr());
        this.setScheduleEnddate(aSched.getScheduleEnddate());
        this.setScheduleStartDate(aSched.getScheduleStartDate());
        this.setScheduleStartHourNumber(aSched.getScheduleStartHourNumber());
        this.setScheduleStartNumHours(aSched.getScheduleStartNumHours());
        this.setId(aSched.getId());
        this.setLastModifyUserId(aSched.getLastModifyUserId());
        this.setCreateUserId(aSched.getCreateUserId());

    }
    
    /**
     * @return the schedGroupDetails
     */
    public ScheduleGroupForScheduleDetails getSchedGroupDetails() {
        if (schedGroupDetails == null) {
            schedGroupDetails = new ScheduleGroupForScheduleDetails();
        }
        return schedGroupDetails;
    }
       
}
