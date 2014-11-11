
package benchmark.invmgmt.service;

import benchmark.invmgmt.bo.ScheduleGroupToVirtualComponentBOImpl;
import benchmark.invmgmt.data.ScheduleGroupToVirtualComponent;
import benchmark.invmgmt.data.ScheduleGroupToVirtualComponents;
import benchmark.invmgmt.exception.ScheduleGroupToVirtualComponentException;

/**
 *
 * @author zwang
 */
public class ScheduleGroupToVirtualComponentServiceImpl implements ScheduleGroupToVirtualComponentService {

    public ScheduleGroupToVirtualComponentServiceImpl() {
    }

    public ScheduleGroupToVirtualComponents getScheduleGroupToVirtualComponents() throws ScheduleGroupToVirtualComponentException {
        ScheduleGroupToVirtualComponents comps = null;

    try {
        ScheduleGroupToVirtualComponentBOImpl schedBO = new ScheduleGroupToVirtualComponentBOImpl();
        comps = (ScheduleGroupToVirtualComponents) schedBO.getScheduleGroupToVirtualComponents();
        
        } catch (ScheduleGroupToVirtualComponentException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupToVirtualComponentException(e);
        }
    
    return comps ;
}
public boolean updateScheduleGroupToVirtualComponent(ScheduleGroupToVirtualComponent aScheduleGroupToVirtualComponent) throws ScheduleGroupToVirtualComponentException {
        boolean updated = false;

        try {
            ScheduleGroupToVirtualComponentBOImpl schedBO = new ScheduleGroupToVirtualComponentBOImpl();
            updated = schedBO.updateScheduleGroupToVirtualComponent(aScheduleGroupToVirtualComponent);

        } catch (ScheduleGroupToVirtualComponentException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupToVirtualComponentException(e);
        }

        return updated;
    }

    public boolean deleteScheduleGroupToVirtualComponent(ScheduleGroupToVirtualComponent aScheduleGroupToVirtualComponent) throws ScheduleGroupToVirtualComponentException {
        boolean deleted = false;

        try {
            ScheduleGroupToVirtualComponentBOImpl schedBO = new ScheduleGroupToVirtualComponentBOImpl();
            deleted = schedBO.deleteScheduleGroupToVirtualComponent(aScheduleGroupToVirtualComponent);

         } catch (ScheduleGroupToVirtualComponentException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupToVirtualComponentException(e);
        }

        return deleted;
    }

    public int insertScheduleGroupToVirtualComponent(ScheduleGroupToVirtualComponent aScheduleGroupToVirtualComponent) throws ScheduleGroupToVirtualComponentException {
        int sgToVirtualComponentsInserted = 0;

try {
            ScheduleGroupToVirtualComponentBOImpl schedBO = new ScheduleGroupToVirtualComponentBOImpl();
            sgToVirtualComponentsInserted = schedBO.insertScheduleGroupToVirtualComponent(aScheduleGroupToVirtualComponent);

        } catch (ScheduleGroupToVirtualComponentException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleGroupToVirtualComponentException(e);
        }
        return sgToVirtualComponentsInserted;
    }
}
