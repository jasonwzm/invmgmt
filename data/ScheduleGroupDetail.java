
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleGroupDetail extends ScheduleGroup {
    private ScheduleGroupToVirtualComponentDetails vCompsDetails = new ScheduleGroupToVirtualComponentDetails();
    private ScheduleGroupContainer schedGroupCont = new ScheduleGroupContainer();
    
    public ScheduleGroupDetail() {
    }

    /**
     * @return the vCompsDetails
     */
    public ScheduleGroupToVirtualComponentDetails getvCompsDetails() {
        return vCompsDetails;
    }

    /**
     * @param vCompsDetails the vCompsDetails to set
     */
    public void setvCompsDetails(ScheduleGroupToVirtualComponentDetails vCompsDetails) {
        this.vCompsDetails = vCompsDetails;
    }
    
    /**
     * @return the schedGroupCont
     */
    public ScheduleGroupContainer getSchedGroupCont() {
        return schedGroupCont;
    }

    /**
     * @param schedGroupCont the schedGroupCont to set
     */
    public void setSchedGroupCont (ScheduleGroupContainer schedGroupCont) {
        this.schedGroupCont = schedGroupCont;
    }
    public String toString() {
        String retStr = super.toString() + "[Not implemented]";
        return retStr;
    }
}
