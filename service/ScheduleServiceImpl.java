
package benchmark.invmgmt.service;

import benchmark.invmgmt.bo.ScheduleBOImpl;
import benchmark.invmgmt.data.Schedule;
import benchmark.invmgmt.data.ScheduleDetail;
import benchmark.invmgmt.data.ScheduleDetails;
import benchmark.invmgmt.data.Schedules;
import benchmark.invmgmt.exception.ScheduleException;
import benchmark.invmgmt.helper.LogHelper;

/**
 *
 * @author zwang
 */
public class ScheduleServiceImpl implements ScheduleService {

    /**
     * Logger
     */
    private static LogHelper logger = new LogHelper(ScheduleServiceImpl.class);

    public ScheduleServiceImpl() {
    }

    // Get Schedule(s)
    public Schedules getSchedules() throws ScheduleException {
        Schedules scheds = null;

        try {
            ScheduleBOImpl schedsBO = new ScheduleBOImpl();
            scheds = (Schedules) schedsBO.getSchedules();

        } catch (ScheduleException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return scheds;
    }

    // Get Schedule
    public Schedule getSchedule(int aId) throws ScheduleException {
        Schedule sched = null;
        try {
            ScheduleBOImpl schedsBO = new ScheduleBOImpl();
            sched = (Schedule) schedsBO.getSchedule(aId);

        } catch (ScheduleException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return sched;
    }

    // Get Active Schedule
    public Schedules getActiveSchedules() throws ScheduleException {
        Schedules scheds = null;

        try {
             ScheduleBOImpl schedsBO = new ScheduleBOImpl();
            scheds = (Schedules) schedsBO.getActiveSchedules();

         } catch (ScheduleException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return scheds;
    }
    
 public ScheduleDetail getFullDetailByScheduleID(int aID) throws ScheduleException {
         ScheduleDetail sched = null;
        
         try {
            ScheduleBOImpl schedBO = new ScheduleBOImpl();
            sched = (ScheduleDetail) schedBO.getFullDetailByScheduleID(aID);

          } catch (ScheduleException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleException(e);
        }
        
         return sched;
    }
 
    public Schedules getSchedulesByVirtualComponentID(int aID) throws ScheduleException {

        Schedules scheds = null;

        try {
             ScheduleBOImpl schedsBO = new ScheduleBOImpl();
            scheds = (Schedules) schedsBO.getSchedulesByVirtualComponentID(aID);

         } catch (ScheduleException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return scheds;
    }
    
    public ScheduleDetails getFullDetailByVirtualComponentID(int aID) throws ScheduleException {
        
        ScheduleDetails scheds = null;
        
         try {
            ScheduleBOImpl schedsBO = new ScheduleBOImpl();
            scheds = (ScheduleDetails) schedsBO.getFullDetailByVirtualComponentID(aID);

        } catch (ScheduleException dae) {
            throw new ScheduleException(dae);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }
        
         return scheds;
    }

    public boolean updateSchedule(String aUserID, ScheduleDetail aScheduleDetail) throws ScheduleException {
        boolean updated = false;

        try {
            ScheduleBOImpl schedsBO = new ScheduleBOImpl();
            updated = schedsBO.updateSchedule(aUserID, aScheduleDetail);

        } catch (ScheduleException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return updated;
    }

    public boolean deleteSchedule(String aUserID, int aScheduleID) throws ScheduleException {
        boolean deleted = false;

        try {
            ScheduleBOImpl schedBO = new ScheduleBOImpl();
            deleted = schedBO.deleteSchedule(aUserID, aScheduleID);

        } catch (ScheduleException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return deleted;
    }

    public int insertSchedule(String aUserID, ScheduleDetail aScheduleDetail) throws ScheduleException {
        int schedsInserted = 0;

        try {
            ScheduleBOImpl schedBO = new ScheduleBOImpl();
            schedsInserted = schedBO.insertSchedule(aUserID, aScheduleDetail);

        } catch (ScheduleException vex) {
            throw vex;
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return schedsInserted;
    }
}
