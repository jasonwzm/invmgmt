
package benchmark.invmgmt.bo;

import benchmark.invmgmt.dao.ScheduleGroupDAO;
import benchmark.invmgmt.data.ScheduleGroup;
import benchmark.invmgmt.data.ScheduleGroupDetail;
import benchmark.invmgmt.data.ScheduleGroups;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.ScheduleGroupException;
import benchmark.invmgmt.helper.LogHelper;

/**
 *
 * @author zwang
 */
public class ScheduleGroupBOImpl implements ScheduleGroupBO {

    /**
     * Logger
     */
    private static LogHelper logger = new LogHelper(VirtualComponentBOImpl.class);

    public ScheduleGroupBOImpl() {
    }

    /* Get the list of schedule group */
    public ScheduleGroups getScheduleGroups() throws ScheduleGroupException {
        ScheduleGroups sGroups = null;

        try {
            ScheduleGroupDAO sGroupDao = new ScheduleGroupDAO();
            sGroups = (ScheduleGroups) sGroupDao.list();

        } catch (DataAccessException dae) {
            throw new ScheduleGroupException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroups;
    }

    /* Get the list of schedule group */
    public ScheduleGroups getScheduleGroupsByContainerId(int aContainerId) throws ScheduleGroupException {
        ScheduleGroups sGroups = null;

        try {
            ScheduleGroupDAO sGroupDao = new ScheduleGroupDAO();
            sGroups = (ScheduleGroups) sGroupDao.getScheduleGroupsByContainerId(aContainerId);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroups;
    }

    /* Get the list of schedule group */
    public ScheduleGroups getAvailableScheduleGroupsByContainerId(int aContainerId) throws ScheduleGroupException {
        ScheduleGroups sGroups = null;

        try {
            ScheduleGroupDAO sGroupDao = new ScheduleGroupDAO();
            sGroups = (ScheduleGroups) sGroupDao.getAvailableScheduleGroupsByContainerId(aContainerId);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroups;
    }

    /* Get a single schedule group given an id */
    public ScheduleGroup getScheduleGroup(int aId) throws ScheduleGroupException {
        ScheduleGroup sGroup = null;

        try {
            ScheduleGroupDAO sGroupDao = new ScheduleGroupDAO();
            sGroup = (ScheduleGroup) sGroupDao.listById(aId);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroup;
    }

    /* Get a single schedule group detail given an id */
    public ScheduleGroupDetail getScheduleFullDetail(int aId) throws ScheduleGroupException {
        ScheduleGroupDetail sGroupDetail = null;

        try {
            ScheduleGroupDAO sGroupDao = new ScheduleGroupDAO();
            sGroupDetail = (ScheduleGroupDetail) sGroupDao.listFullDetailById(aId);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroupDetail;
    }

    /* Get a single schedule group detail given an id */
    public ScheduleGroupDetail getAvailableScheduleFullDetail(int aId) throws ScheduleGroupException {
        ScheduleGroupDetail sGroupDetail = null;

        try {
            ScheduleGroupDAO sGroupDao = new ScheduleGroupDAO();
            sGroupDetail = (ScheduleGroupDetail) sGroupDao.listAvailableFullDetailById(aId);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return sGroupDetail;
    }
    

    /* update a single schedule group given a schedule group */
    public boolean updateScheduleGroup(ScheduleGroup aSchedGroup) throws ScheduleGroupException {
        boolean updated = false;

        try {
            ScheduleGroupDAO sGroupDao = new ScheduleGroupDAO();
            updated = sGroupDao.update(aSchedGroup);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return updated;
    }

    /* delete a single schedule group given a schedule group id */
    public boolean deleteScheduleGroup(int aId) throws ScheduleGroupException {
        boolean deleted = false;
        ScheduleGroup sGroup = new ScheduleGroup();
        sGroup.setId(aId);

        try {
            ScheduleGroupDAO sGroupDao = new ScheduleGroupDAO();

            deleted = sGroupDao.delete(sGroup);
        } catch (DataAccessException dae) {
            throw new ScheduleGroupException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return deleted;
    }

    /* insert a single schedule group given a schedule group */
    public int insertScheduleGroup(ScheduleGroup aSchedGroup) throws ScheduleGroupException {
        int rowsInserted = 0;

        try {
            ScheduleGroupDAO sGroupDao = new ScheduleGroupDAO();
            rowsInserted = sGroupDao.insert(aSchedGroup);
        } catch (DataAccessException dae) {
            throw new ScheduleGroupException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupException(e);
        }

        return rowsInserted;
    }
}
