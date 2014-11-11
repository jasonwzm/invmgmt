
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 * This class will extend the schedule group to virtual component class
 * by also including the virtual component
 */
public class ScheduleGroupToVirtualComponentDetail extends ScheduleGroupToVirtualComponent {
    
    private VirtualComponent virtComponent = new VirtualComponent();

    /**
     * @return the virtComponent
     */
    public VirtualComponent getVirtComponent() {
        return virtComponent;
    }

    /**
     * @param virtComponent the virtComponent to set
     */
    public void setVirtComponent(VirtualComponent virtComponent) {
        this.virtComponent = virtComponent;
    }
    
}
