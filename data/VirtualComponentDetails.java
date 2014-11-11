
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class VirtualComponentDetails extends BeanObjects {

    public void dump() {
        VirtualComponentDetail vc;
        for (int i = 0; i < this.size(); i++) {
            vc = (VirtualComponentDetail) this.get(i);
            System.out.println(vc.toString());
        }
    }
}
