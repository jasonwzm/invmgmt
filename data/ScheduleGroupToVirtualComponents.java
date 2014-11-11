
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleGroupToVirtualComponents extends BeanObjects {

    public void dump() {
        ScheduleGroupToVirtualComponent sgroup;
        for (int i = 0; i < this.size(); i++) {
            sgroup = (ScheduleGroupToVirtualComponent) this.get(i);
            System.out.println(sgroup.toString());
        }
    }
}
