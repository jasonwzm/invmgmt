
package benchmark.invmgmt.service;

import benchmark.invmgmt.data.ScheduleGroupContainer;
import benchmark.invmgmt.data.ScheduleGroupContainers;
import benchmark.invmgmt.exception.ScheduleGroupContainerException;

/**
 *
 * @author zwang
 */
public interface ScheduleGroupContainerService {

    public abstract ScheduleGroupContainers getScheduleGroupContainers() throws ScheduleGroupContainerException;

    public abstract ScheduleGroupContainer getScheduleGroupContainer(int aId) throws ScheduleGroupContainerException;

    public abstract boolean updateScheduleGroupContainer(ScheduleGroupContainer sGroupContainer) throws ScheduleGroupContainerException;

    public abstract boolean deleteScheduleGroupContainer(int aId) throws ScheduleGroupContainerException;

    public abstract int insertScheduleGroupContainer(ScheduleGroupContainer sGroupContainer) throws ScheduleGroupContainerException;
}
