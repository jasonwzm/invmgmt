
package benchmark.invmgmt.service;

import benchmark.invmgmt.bo.VirtualComponentBO;
import benchmark.invmgmt.bo.VirtualComponentBOImpl;
import benchmark.invmgmt.dao.VirtualComponentDAO;
import benchmark.invmgmt.data.VirtualComponent;
import benchmark.invmgmt.data.VirtualComponents;
import benchmark.invmgmt.data.VirtualSearchComponent;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.VirtualComponentsException;
import benchmark.invmgmt.helper.LogHelper;

/**
 *
 * @author zwang
 */
public final class VirtualComponentsServiceImpl implements VirtualComponentsService {

    private static LogHelper logger = new LogHelper(VirtualComponentsServiceImpl.class);

    public VirtualComponentsServiceImpl() {
    }

    public final VirtualComponents getVirtualComponents() throws VirtualComponentsException {
        VirtualComponents vComponents = null;

        try {
            VirtualComponentBO vcBO = new VirtualComponentBOImpl();
            vComponents = vcBO.getVirtualComponents();
        } catch (VirtualComponentsException vex) {
            throw vex;
        } catch (Exception e) {
            throw new VirtualComponentsException(e);
        }

        return vComponents;
    }

    public final VirtualComponent getVirtualComponent(int aId) throws VirtualComponentsException {
        VirtualComponent vComponent = null;

        try {
            VirtualComponentBO vcBO = new VirtualComponentBOImpl();
            vComponent = vcBO.getVirtualComponent(aId);
        } catch (VirtualComponentsException vex) {
            throw vex;
        } catch (Exception e) {
            throw new VirtualComponentsException(e);
        }

        return vComponent;
    }

    public final boolean updateVirtualComponent(VirtualComponent aVirtComponent) throws VirtualComponentsException {
        boolean updated = false;
        try {
            VirtualComponentBO vcBO = new VirtualComponentBOImpl();
            updated = vcBO.updateVirtualComponent(aVirtComponent);
        } catch (VirtualComponentsException vex) {
            throw vex;
        } catch (Exception e) {
            throw new VirtualComponentsException(e);
        }

        return updated;
    }

    public final boolean deleteVirtualComponent(int aId) throws VirtualComponentsException {
        boolean deleted = false;
        try {
            VirtualComponentBO vcBO = new VirtualComponentBOImpl();
            deleted = vcBO.deleteVirtualComponent(aId);
        } catch (VirtualComponentsException vex) {
            throw vex;
        } catch (Exception e) {
            throw new VirtualComponentsException(e);
        }

        return deleted;
    }
    
    public final int insertVirtualComponent(VirtualComponent aVirtComponent) throws VirtualComponentsException {
        int rowsInserted = 0;
        try {
            VirtualComponentBO vcBO = new VirtualComponentBOImpl();
            rowsInserted = vcBO.insertVirtualComponent(aVirtComponent);
        } catch (VirtualComponentsException vex) {
            throw vex;
        } catch (Exception e) {
            throw new VirtualComponentsException(e);
        }

        return rowsInserted;
    }
    
       /* Get the list of virtual components */
    public VirtualComponents search(VirtualSearchComponent aSearchComp) throws VirtualComponentsException {
        VirtualComponents vComponents = null;

        try {
            VirtualComponentDAO vComponentDao = new VirtualComponentDAO();
            vComponents = (VirtualComponents) vComponentDao.search(aSearchComp);

        } catch (DataAccessException dae) {
            throw new VirtualComponentsException(dae);
        } catch (Exception e) {
            throw new VirtualComponentsException(e);
        }

        return vComponents;
    }
}
