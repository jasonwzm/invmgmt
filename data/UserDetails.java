
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class UserDetails extends BeanObjects {

    public void dump() {
        UserDetail ud;
        for (int i = 0; i < this.size(); i++) {
            ud = (UserDetail) this.get(i);
            System.out.println(ud.toString());
        }
    }
}
