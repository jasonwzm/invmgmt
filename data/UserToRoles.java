
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class UserToRoles extends BeanObjects {

    public void dump() {
        UserToRole uToRole;
        for (int i = 0; i < this.size(); i++) {
            uToRole = (UserToRole) this.get(i);
            System.out.println(uToRole.toString());
        }
    }
}
