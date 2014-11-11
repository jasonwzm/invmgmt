
package benchmark.invmgmt.service;

import benchmark.invmgmt.bo.ScheduleGroupBO;
import benchmark.invmgmt.bo.ScheduleGroupBOImpl;
import benchmark.invmgmt.data.ScheduleGroup;
import benchmark.invmgmt.data.ScheduleGroupDetail;
import benchmark.invmgmt.data.ScheduleGroups;
import benchmark.invmgmt.exception.ScheduleGroupException;
import benchmark.invmgmt.helper.LogHelper;

/**
 *
 * @author zwang
 */
public class ScheduleGroupServiceImpl implements ScheduleGroupService {

    private static LogHelper logger = new LogHelper(ScheduleGroupServiceImpl.class);

    public ScheduleGroupServiceImpl() {
    }

    public final ScheduleGroups getScheduleGroups() throws ScheduleGroupException {
        ScheduleGroups sGroups = null;

        try {
            ScheduleGroupBO sGroupBO = new ScheduleGroupBOImpl();
            sGroups = sGroupBO.getScheduleGroups();
        } catch (ScheduleGroupException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroups;
    }

    public final ScheduleGroups getScheduleGroupsByContainerId(int aContainerId) throws ScheduleGroupException {
        ScheduleGroups sGroups = null;

        try {
            ScheduleGroupBO sGroupBO = new ScheduleGroupBOImpl();
            sGroups = sGroupBO.getScheduleGroupsByContainerId(aContainerId);
        } catch (ScheduleGroupException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroups;
    }

    public final ScheduleGroups getAvailableScheduleGroupsByContainerId(int aContainerId) throws ScheduleGroupException {
        ScheduleGroups sGroups = null;

        try {
            ScheduleGroupBO sGroupBO = new ScheduleGroupBOImpl();
            sGroups = sGroupBO.getAvailableScheduleGroupsByContainerId(aContainerId);
        } catch (ScheduleGroupException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroups;
    }

    public final ScheduleGroup getScheduleGroup(int aId) throws ScheduleGroupException {
        ScheduleGroup sGroup = null;

        try {
            ScheduleGroupBO sGroupBO = new ScheduleGroupBOImpl();
            sGroup = sGroupBO.getScheduleGroup(aId);
        } catch (ScheduleGroupException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroup;
    }

    /* Get a single schedule group detail given an id */
    public ScheduleGroupDetail getScheduleFullDetail(int aId) throws ScheduleGroupException {
        ScheduleGroupDetail sGroupDetail = null;

        try {
            ScheduleGroupBOImpl sGroupBO = new ScheduleGroupBOImpl();
            sGroupDetail = (ScheduleGroupDetail) sGroupBO.getScheduleFullDetail(aId);

        } catch (ScheduleGroupException dae) {
            throw dae;
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroupDetail;
    }

    /* Get a single schedule group detail given an id */
    public ScheduleGroupDetail getAvailableScheduleFullDetail(int aId) throws ScheduleGroupException {
        ScheduleGroupDetail sGroupDetail = null;

        try {
            ScheduleGroupBOImpl sGroupBO = new ScheduleGroupBOImpl();
            sGroupDetail = (ScheduleGroupDetail) sGroupBO.getAvailableScheduleFullDetail(aId);

        } catch (ScheduleGroupException dae) {
            throw dae;
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroupDetail;
    }
    
    public final boolean updateScheduleGroup(ScheduleGroup sGroup) throws ScheduleGroupException {
        boolean updated = false;
        try {
            ScheduleGroupBO sGroupBO = new ScheduleGroupBOImpl();
            updated = sGroupBO.updateScheduleGroup(sGroup);
        } catch (ScheduleGroupException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return updated;
    }

    public final boolean deleteScheduleGroup(int aId) throws ScheduleGroupException {
        boolean deleted = false;
        try {
            ScheduleGroupBO sGroupBO = new ScheduleGroupBOImpl();
            deleted = sGroupBO.deleteScheduleGroup(aId);
        } catch (ScheduleGroupException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return deleted;
    }

    public final int insertScheduleGroup(ScheduleGroup sGroup) throws ScheduleGroupException {
        int rowsInserted = 0;
        try {
            ScheduleGroupBO sGroupBO = new ScheduleGroupBOImpl();
            rowsInserted = sGroupBO.insertScheduleGroup(sGroup);
        } catch (ScheduleGroupException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return rowsInserted;
    }
}
