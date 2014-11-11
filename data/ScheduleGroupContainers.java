
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class ScheduleGroupContainers extends BeanObjects {

    public void dump() {
        ScheduleGroupContainer cont;
        for (int i = 0; i < this.size(); i++) {
            cont = (ScheduleGroupContainer) this.get(i);
            System.out.println(cont.toString());
        }
    }
}
