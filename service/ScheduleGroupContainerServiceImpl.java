
package benchmark.invmgmt.service;

import benchmark.invmgmt.bo.ScheduleGroupContainerBO;
import benchmark.invmgmt.bo.ScheduleGroupContainerBOImpl;
import benchmark.invmgmt.data.ScheduleGroupContainer;
import benchmark.invmgmt.data.ScheduleGroupContainers;
import benchmark.invmgmt.exception.ScheduleGroupContainerException;
import benchmark.invmgmt.helper.LogHelper;

/**
 *
 * @author zwang
 */
public class ScheduleGroupContainerServiceImpl implements ScheduleGroupContainerService {

    private static LogHelper logger = new LogHelper(VirtualComponentsServiceImpl.class);

    public ScheduleGroupContainerServiceImpl() {
    }

    public final ScheduleGroupContainers getScheduleGroupContainers() throws ScheduleGroupContainerException {
        ScheduleGroupContainers sGroupContainers = null;

        try {
            ScheduleGroupContainerBO sgContainerBO = new ScheduleGroupContainerBOImpl();
            sGroupContainers = sgContainerBO.getScheduleGroupContainers();
        } catch (ScheduleGroupContainerException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupContainerException(e);
        }

        return sGroupContainers;
    }

    public final ScheduleGroupContainer getScheduleGroupContainer(int aId) throws ScheduleGroupContainerException {
        ScheduleGroupContainer sGroupContainer = null;

        try {
            ScheduleGroupContainerBO sgContainerBO = new ScheduleGroupContainerBOImpl();
            sGroupContainer = sgContainerBO.getScheduleGroupContainer(aId);
        } catch (ScheduleGroupContainerException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupContainerException(e);
        }

        return sGroupContainer;
    }

    public final boolean updateScheduleGroupContainer(ScheduleGroupContainer sGroupContainer) throws ScheduleGroupContainerException {
        boolean updated = false;
        try {
            ScheduleGroupContainerBO sgContainerBO = new ScheduleGroupContainerBOImpl();
            updated = sgContainerBO.updateScheduleGroupContainer(sGroupContainer);
        } catch (ScheduleGroupContainerException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupContainerException(e);
        }

        return updated;
    }

    public final boolean deleteScheduleGroupContainer(int aId) throws ScheduleGroupContainerException {
        boolean deleted = false;
        try {
            ScheduleGroupContainerBO sgContainerBO = new ScheduleGroupContainerBOImpl();
            deleted = sgContainerBO.deleteScheduleGroupContainer(aId);
        } catch (ScheduleGroupContainerException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupContainerException(e);
        }

        return deleted;
    }

    public final int insertScheduleGroupContainer(ScheduleGroupContainer sGroupContainer) throws ScheduleGroupContainerException {
        int rowsInserted = 0;
        try {
            ScheduleGroupContainerBO sgContainerBO = new ScheduleGroupContainerBOImpl();
            rowsInserted = sgContainerBO.insertScheduleGroupContainer(sGroupContainer);
        } catch (ScheduleGroupContainerException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupContainerException(e);
        }

        return rowsInserted;
    }
}
