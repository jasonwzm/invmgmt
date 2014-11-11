
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class AppDefinitions extends BeanObjects {

    public void dump() {
        AppDefinition appDef;
        for (int i = 0; i < this.size(); i++) {
            appDef = (AppDefinition) this.get(i);
            System.out.println(appDef.toString());
        }
    }
}
