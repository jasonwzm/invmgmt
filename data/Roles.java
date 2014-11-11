
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class Roles extends BeanObjects {

    public void dump() {
        Role role;
        for (int i = 0; i < this.size(); i++) {
            role = (Role) this.get(i);
            System.out.println(role.toString());
        }
    }
}
