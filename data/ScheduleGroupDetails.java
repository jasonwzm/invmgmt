
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleGroupDetails extends BeanObjects {

    public void dump() {
        ScheduleGroupDetail scheduleDetail;
        for (int i = 0; i < this.size(); i++) {
            scheduleDetail = (ScheduleGroupDetail) this.get(i);
            System.out.println(scheduleDetail.toString());
        }
    }
}
