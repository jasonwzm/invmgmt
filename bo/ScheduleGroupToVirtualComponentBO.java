package benchmark.invmgmt.bo;

import benchmark.invmgmt.data.ScheduleGroupToVirtualComponent;
import benchmark.invmgmt.data.ScheduleGroupToVirtualComponents;
import benchmark.invmgmt.exception.ScheduleGroupToVirtualComponentException;

/**
 *
 * @author zwang
 */
public interface ScheduleGroupToVirtualComponentBO {
    public abstract ScheduleGroupToVirtualComponents getScheduleGroupToVirtualComponents() throws ScheduleGroupToVirtualComponentException;
    
    public abstract boolean updateScheduleGroupToVirtualComponent(ScheduleGroupToVirtualComponent aScheduleGroupToVirtualComponent) throws ScheduleGroupToVirtualComponentException;
    public abstract boolean deleteScheduleGroupToVirtualComponent(ScheduleGroupToVirtualComponent aScheduleGroupToVirtualComponent) throws ScheduleGroupToVirtualComponentException;
    public abstract int insertScheduleGroupToVirtualComponent(ScheduleGroupToVirtualComponent aScheduleGroupToVirtualComponent) throws ScheduleGroupToVirtualComponentException;
    
}
