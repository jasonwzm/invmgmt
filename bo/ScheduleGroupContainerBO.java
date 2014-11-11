
package benchmark.invmgmt.bo;

import benchmark.invmgmt.data.ScheduleGroupContainer;
import benchmark.invmgmt.data.ScheduleGroupContainers;
import benchmark.invmgmt.exception.ScheduleGroupContainerException;

/**
 *
 * @author zwang
 */
public interface ScheduleGroupContainerBO {

    public abstract ScheduleGroupContainers getScheduleGroupContainers() throws ScheduleGroupContainerException;

    public abstract ScheduleGroupContainer getScheduleGroupContainer(int aId) throws ScheduleGroupContainerException;

    public abstract boolean updateScheduleGroupContainer(ScheduleGroupContainer aScheduleGroupContainer) throws ScheduleGroupContainerException;

    public abstract boolean deleteScheduleGroupContainer(int aId) throws ScheduleGroupContainerException;

    public abstract int insertScheduleGroupContainer(ScheduleGroupContainer aScheduleGroupContainer) throws ScheduleGroupContainerException;
}
