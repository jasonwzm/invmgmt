
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class VirtualComponents extends BeanObjects {

    public void dump() {
        VirtualComponent vc;
        for (int i = 0; i < this.size(); i++) {
            vc = (VirtualComponent) this.get(i);
            System.out.println(vc.toString());
        }
    }
}
