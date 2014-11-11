
package benchmark.invmgmt.bo;

import benchmark.invmgmt.data.Schedule;
import benchmark.invmgmt.data.ScheduleDetail;
import benchmark.invmgmt.data.ScheduleDetails;
import benchmark.invmgmt.data.Schedules;
import benchmark.invmgmt.exception.ScheduleException;

/**
 *
 * @author zwang
 */
public interface ScheduleBO {
    public abstract Schedules getSchedules() throws ScheduleException;
    public abstract Schedule getSchedule(int aId) throws ScheduleException;
    public abstract Schedules getActiveSchedules() throws ScheduleException;
    public abstract Schedule getFullDetailByScheduleID(int aID) throws ScheduleException;
    public abstract Schedules getSchedulesByVirtualComponentID(int aID) throws ScheduleException;
    public abstract ScheduleDetails getFullDetailByVirtualComponentID(int aID) throws ScheduleException;
    public abstract boolean updateSchedule(String aUserID, ScheduleDetail aScheduleDetail) throws ScheduleException;
    public abstract boolean deleteSchedule(String aUserID, int aScheduleID) throws ScheduleException;
    public abstract int insertSchedule(String aUserID, ScheduleDetail aScheduleDetail) throws ScheduleException;
    public abstract boolean areThereScheduleConficts(ScheduleDetail aScheduleDetail) throws ScheduleException;    
}
