
package benchmark.invmgmt.bo;

import benchmark.invmgmt.dao.ScheduleGroupToVirtualComponentDAO;
import benchmark.invmgmt.data.ScheduleGroupToVirtualComponent;
import benchmark.invmgmt.data.ScheduleGroupToVirtualComponents;
import benchmark.invmgmt.exception.ScheduleGroupToVirtualComponentException;
import benchmark.invmgmt.exception.DataAccessException;

/**
 *
 * @author zwang
 */
public class ScheduleGroupToVirtualComponentBOImpl implements ScheduleGroupToVirtualComponentBO {

    public ScheduleGroupToVirtualComponentBOImpl() {
    }

    public ScheduleGroupToVirtualComponents getScheduleGroupToVirtualComponents() throws ScheduleGroupToVirtualComponentException {
        ScheduleGroupToVirtualComponents comps = null;

    try {
        ScheduleGroupToVirtualComponentDAO schedDAO = new ScheduleGroupToVirtualComponentDAO();
        comps = (ScheduleGroupToVirtualComponents) schedDAO.list();
        
        } catch (DataAccessException dae) {
            throw new ScheduleGroupToVirtualComponentException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupToVirtualComponentException(e);
        }
    
    return comps ;
}
public boolean updateScheduleGroupToVirtualComponent(ScheduleGroupToVirtualComponent aScheduleGroupToVirtualComponent) throws ScheduleGroupToVirtualComponentException {
        boolean updated = false;

        try {
            ScheduleGroupToVirtualComponentDAO schedDAO = new ScheduleGroupToVirtualComponentDAO();
            updated = schedDAO.update(aScheduleGroupToVirtualComponent);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupToVirtualComponentException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupToVirtualComponentException(e);
        }

        return updated;
    }

    public boolean deleteScheduleGroupToVirtualComponent(ScheduleGroupToVirtualComponent aScheduleGroupToVirtualComponent) throws ScheduleGroupToVirtualComponentException {
        boolean deleted = false;

        try {
            ScheduleGroupToVirtualComponentDAO schedDAO = new ScheduleGroupToVirtualComponentDAO();
            deleted = schedDAO.delete(aScheduleGroupToVirtualComponent);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupToVirtualComponentException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupToVirtualComponentException(e);
        }

        return deleted;
    }

    public int insertScheduleGroupToVirtualComponent(ScheduleGroupToVirtualComponent aScheduleGroupToVirtualComponent) throws ScheduleGroupToVirtualComponentException {
        int sgToVirtualComponentsInserted = 0;

try {
            ScheduleGroupToVirtualComponentDAO schedDAO = new ScheduleGroupToVirtualComponentDAO();
            sgToVirtualComponentsInserted = schedDAO.insert(aScheduleGroupToVirtualComponent);

        } catch (DataAccessException dae) {
            throw new ScheduleGroupToVirtualComponentException(dae);
        } catch (Exception e) {
            throw new ScheduleGroupToVirtualComponentException(e);
        }
        return sgToVirtualComponentsInserted;
    }
}
