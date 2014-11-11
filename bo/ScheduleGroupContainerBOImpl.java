
package benchmark.invmgmt.bo;

import benchmark.invmgmt.dao.ScheduleGroupContainerDAO;
import benchmark.invmgmt.data.ScheduleGroupContainer;
import benchmark.invmgmt.data.ScheduleGroupContainers;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.ScheduleGroupContainerException;
import benchmark.invmgmt.helper.LogHelper;

/**
 *
 * @author zwang
 */
public class ScheduleGroupContainerBOImpl implements ScheduleGroupContainerBO {

    /**
     * Logger
     */
    private static LogHelper logger = new LogHelper(VirtualComponentBOImpl.class);

    public ScheduleGroupContainerBOImpl() {
    }

    /* Get the list of schedule group containers */
    public ScheduleGroupContainers getScheduleGroupContainers() throws ScheduleGroupContainerException {
        ScheduleGroupContainers sGroupContainers = null;

        try {
            ScheduleGroupContainerDAO sGroupContainersDao = new ScheduleGroupContainerDAO();
            sGroupContainers = (ScheduleGroupContainers) sGroupContainersDao.list();

        } catch (DataAccessException dae) {
            throw new ScheduleGroupContainerException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupContainerException(e);
        }

        return sGroupContainers;
    }

    /* Get a single schedule group container given an id */
    public ScheduleGroupContainer getScheduleGroupContainer(int aId) throws ScheduleGroupContainerException {
        ScheduleGroupContainer sGroupContainer = null;

        try {
            ScheduleGroupContainerDAO sGroupContainerDao = new ScheduleGroupContainerDAO();
            sGroupContainer = (ScheduleGroupContainer) sGroupContainerDao.listById(aId);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupContainerException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupContainerException(e);
        }

        return sGroupContainer;
    }

    /* update a single schedule group container given a schedule group container */
    public boolean updateScheduleGroupContainer(ScheduleGroupContainer aSchedGroupContainer) throws ScheduleGroupContainerException {
        boolean updated = false;

        try {
            ScheduleGroupContainerDAO sGroupContainerDao = new ScheduleGroupContainerDAO();
            updated = sGroupContainerDao.update(aSchedGroupContainer);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupContainerException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupContainerException(e);
        }

        return updated;
    }

    /* delete a single schedule group container given a schedule group container id */
    public boolean deleteScheduleGroupContainer(int aId) throws ScheduleGroupContainerException {
        boolean deleted = false;
        ScheduleGroupContainer sGroupContainer = new ScheduleGroupContainer();
        sGroupContainer.setId(aId);

        try {
            ScheduleGroupContainerDAO sGroupContainerDao = new ScheduleGroupContainerDAO();

            deleted = sGroupContainerDao.delete(sGroupContainer);
        } catch (DataAccessException dae) {
            throw new ScheduleGroupContainerException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupContainerException(e);
        }

        return deleted;
    }

    /* insert a single schedule group container given a schedule group container */
    public int insertScheduleGroupContainer(ScheduleGroupContainer aSchedGroupContainer) throws ScheduleGroupContainerException {
        int rowsInserted = 0;

        try {
            ScheduleGroupContainerDAO sGroupContainerDao = new ScheduleGroupContainerDAO();
            rowsInserted = sGroupContainerDao.insert(aSchedGroupContainer);
        } catch (DataAccessException dae) {
            throw new ScheduleGroupContainerException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupContainerException(e);
        }

        return rowsInserted;
    }
}
