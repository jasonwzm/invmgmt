
package benchmark.invmgmt.bo;

import benchmark.invmgmt.dao.ScheduleDAO;
import benchmark.invmgmt.dao.VirtualComponentDAO;
import benchmark.invmgmt.data.Schedule;
import benchmark.invmgmt.data.Schedules;
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
public class VirtualComponentBOImpl implements VirtualComponentBO {

    /**
     * Logger
     */
    private static LogHelper logger = new LogHelper(VirtualComponentBOImpl.class);

    public VirtualComponentBOImpl() {
    }

    /* Get the list of virtual components */
    public VirtualComponents getVirtualComponents() throws VirtualComponentsException {
        VirtualComponents vComponents = null;

        try {
            VirtualComponentDAO vComponentDao = new VirtualComponentDAO();
            vComponents = (VirtualComponents) vComponentDao.list(null);

        } catch (DataAccessException dae) {
            throw new VirtualComponentsException(dae);
        } catch (Exception e) {
            throw new VirtualComponentsException(e);
        }

        return vComponents;
    }

    /* Get a single virtual components given an id */
    public VirtualComponent getVirtualComponent(int aId) throws VirtualComponentsException {
        VirtualComponent vComponent = null;

        try {
            VirtualComponentDAO vComponentDao = new VirtualComponentDAO();
            vComponent = (VirtualComponent) vComponentDao.item(aId);

        } catch (DataAccessException dae) {
            throw new VirtualComponentsException(dae);
        } catch (Exception e) {
            throw new VirtualComponentsException(e);
        }

        return vComponent;
    }

    /* update a single virtual components given a virtual component */
    public boolean updateVirtualComponent(VirtualComponent aVirtComponent) throws VirtualComponentsException {
        boolean updated = false;

        try {
            VirtualComponentDAO vComponentDao = new VirtualComponentDAO();
            updated = vComponentDao.update(aVirtComponent);

        } catch (DataAccessException dae) {
            throw new VirtualComponentsException(dae);
        } catch (Exception e) {
            throw new VirtualComponentsException(e);
        }

        return updated;
    }

    /* delete a single virtual components given a virtual component id */
    public boolean deleteVirtualComponent(int aId) throws VirtualComponentsException {
        boolean deleted = false;
        VirtualComponent vc = new VirtualComponent();
        VirtualComponentDAO vComponentDao = new VirtualComponentDAO();
        try {

            // Check for schedules with this component.
            // IF NOthing comes back then continue with the VC Delete, else
            // throw VirtualComponentsExceptions and return from this function.
            Schedules schedsUsingVComp = null;
            ScheduleDAO schedDAO = new ScheduleDAO();
            schedsUsingVComp = (Schedules) schedDAO.listSchedulesByVirtualCompId(aId);
            if (schedsUsingVComp != null && schedsUsingVComp.size() > 0) {
                // ERROR - Schedules exist.  Throw exception message and exit.
                String msgString = "";
                String sString = "";
                Schedule sched;
                vc = (VirtualComponent) vComponentDao.item(aId);
                msgString = "Unable to delete Virtual Component " + vc.getMachineName() + "." + "\n" + "\n";
                msgString = msgString + "The following Schedules are using this component:" + "\n";
                for (int i = 0; i < schedsUsingVComp.size(); i++) {
                    sched = (Schedule) schedsUsingVComp.get(i);
                    if (sString == "") {
                        sString = sched.toStringUserMessage();
                    } else {
                        sString = sString + ", " + sched.toStringUserMessage();
                    }
                }  // end for loop
                throw new Exception(msgString + sString);
            } else {
                // DELETE - There are no eexisting schedules so we can proceed with VC delete.
                vc.setId(aId);
                deleted = vComponentDao.delete(vc);
            }


        } catch (DataAccessException dae) {
            throw new VirtualComponentsException(dae);
        } catch (Exception e) {
            throw new VirtualComponentsException(e);
        }

        return deleted;
    }

    /* insert a single virtual components given a virtual component id */
    public int insertVirtualComponent(VirtualComponent aVirtComponent) throws VirtualComponentsException {
        int rowsInserted = 0;

        try {
            VirtualComponentDAO vComponentDao = new VirtualComponentDAO();
            rowsInserted = vComponentDao.insert(aVirtComponent);
        } catch (DataAccessException dae) {
            throw new VirtualComponentsException(dae);
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
