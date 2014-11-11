
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class Schedules extends BeanObjects {

    public void dump() {
        Schedule schedule;
        for (int i = 0; i < this.size(); i++) {
            schedule = (Schedule) this.get(i);
            System.out.println(schedule.toString());
        }
    }
}
