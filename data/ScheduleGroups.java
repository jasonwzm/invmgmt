
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleGroups extends BeanObjects {

    public void dump() {
        ScheduleGroup sgroup;
        for (int i = 0; i < this.size(); i++) {
            sgroup = (ScheduleGroup) this.get(i);
            System.out.println(sgroup.toString());
        }
    }
}
