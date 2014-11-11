
package benchmark.invmgmt.bo;

import benchmark.invmgmt.data.ScheduleGroup;
import benchmark.invmgmt.data.ScheduleGroupDetail;
import benchmark.invmgmt.data.ScheduleGroups;
import benchmark.invmgmt.exception.ScheduleGroupException;

/**
 *
 * @author zwang
 */
public interface ScheduleGroupBO {

    public abstract ScheduleGroups getScheduleGroups() throws ScheduleGroupException;

    public abstract ScheduleGroups getScheduleGroupsByContainerId(int aContainerId) throws ScheduleGroupException;
    public abstract ScheduleGroups getAvailableScheduleGroupsByContainerId(int aContainerId) throws ScheduleGroupException;

    public abstract ScheduleGroup getScheduleGroup(int aId) throws ScheduleGroupException;

    public abstract ScheduleGroupDetail getScheduleFullDetail(int aId) throws ScheduleGroupException;
    public abstract ScheduleGroupDetail getAvailableScheduleFullDetail(int aId) throws ScheduleGroupException;

    public abstract boolean updateScheduleGroup(ScheduleGroup aScheduleGroup) throws ScheduleGroupException;

    public abstract boolean deleteScheduleGroup(int aId) throws ScheduleGroupException;

    public abstract int insertScheduleGroup(ScheduleGroup aScheduleGroup) throws ScheduleGroupException;
}
