
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleToScheduleGroups extends BeanObjects {

    public ScheduleToScheduleGroup findLinkComponent(int a_scheduleId, int a_scheduleGroupId, int a_virtualCompId) {
        ScheduleToScheduleGroup linkComp = null;
        ScheduleToScheduleGroup retComp = null;
        for (int i = 0; i < this.size(); i++) {
            linkComp = (ScheduleToScheduleGroup) this.get(i);
            if (a_scheduleId == linkComp.getSchedulId() &&
                    a_scheduleGroupId == linkComp.getScheduleGroupId() &&
                    a_virtualCompId == linkComp.getVirtualComponentId()) {
                
                retComp = linkComp;
                break;
            }
        }
        
        return retComp;
    }
    
    public void dump() {
        ScheduleToScheduleGroup linkComp;
        for (int i = 0; i < this.size(); i++) {
            linkComp = (ScheduleToScheduleGroup) this.get(i);
            System.out.println(linkComp.toString());
        }
    }
}
