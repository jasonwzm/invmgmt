/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark.invmgmt.bo;

import benchmark.invmgmt.dao.ScheduleDAO;
import benchmark.invmgmt.dao.UserDetailDAO;
import benchmark.invmgmt.data.Role;
import benchmark.invmgmt.data.Schedule;
import benchmark.invmgmt.data.ScheduleDetail;
import benchmark.invmgmt.data.ScheduleDetails;
import benchmark.invmgmt.data.ScheduleGroupForScheduleDetail;
import benchmark.invmgmt.data.ScheduleGroupForScheduleDetails;
import benchmark.invmgmt.data.ScheduleToScheduleGroup;
import benchmark.invmgmt.data.Schedules;
import benchmark.invmgmt.data.UserDetail;
import benchmark.invmgmt.data.VirtualComponentDetail;
import benchmark.invmgmt.data.VirtualComponentDetails;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.exception.ScheduleException;
import benchmark.invmgmt.helper.EmailHelper;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.util.ApplicationConstants;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author zwang
 */
public class ScheduleBOImpl implements ScheduleBO {

    /**
     * Logger
     */
    private static LogHelper logger = new LogHelper(ScheduleBOImpl.class);

    public ScheduleBOImpl() {
    }

    // Get Schedule(s)
    public Schedules getSchedules() throws ScheduleException {
        Schedules scheds = null;

        try {
            ScheduleDAO schedsDAO = new ScheduleDAO();
            scheds = (Schedules) schedsDAO.list(null);

        } catch (DataAccessException dae) {
            throw new ScheduleException(dae);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return scheds;
    }

    // Get Schedule
    public Schedule getSchedule(int aId) throws ScheduleException {
        Schedule sched = null;
        try {
            ScheduleDAO schedsDAO = new ScheduleDAO();
            sched = (Schedule) schedsDAO.listById(aId);

        } catch (DataAccessException dae) {
            throw new ScheduleException(dae);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return sched;
    }

    // Get Active Schedules
    public Schedules getActiveSchedules() throws ScheduleException {
        Schedules scheds = null;

        try {
            ScheduleDAO schedsDAO = new ScheduleDAO();
            scheds = (Schedules) schedsDAO.getActiveSchedules();

        } catch (DataAccessException dae) {
            throw new ScheduleException(dae);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return scheds;
    }

    public Schedule getFullDetailByScheduleID(int aID) throws ScheduleException {
        Schedule sched = null;

        try {
            ScheduleDAO schedDAO = new ScheduleDAO();
            sched = (Schedule) schedDAO.listFullDetailByScheduleId(aID);

        } catch (DataAccessException dae) {
            throw new ScheduleException(dae);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return sched;
    }

    public Schedules getSchedulesByVirtualComponentID(int aID) throws ScheduleException {

        Schedules scheds = null;

        try {
            ScheduleDAO schedsDAO = new ScheduleDAO();
            scheds = (Schedules) schedsDAO.listSchedulesByVirtualCompId(aID);

        } catch (DataAccessException dae) {
            throw new ScheduleException(dae);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return scheds;
    }

    public ScheduleDetails getFullDetailByVirtualComponentID(int aID) throws ScheduleException {
        ScheduleDetails scheds = null;

        try {
            ScheduleDAO schedsDAO = new ScheduleDAO();
            scheds = (ScheduleDetails) schedsDAO.listFullDetailByVirtualCompId(aID);

        } catch (DataAccessException dae) {
            throw new ScheduleException(dae);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return scheds;
    }

    // updateSchedule - updates schedule given the currents userid and a ScheduleDetail object.
    public boolean updateSchedule(String aUserID, ScheduleDetail aScheduleDetail) throws ScheduleException {
        boolean updated = false;
        boolean authorizedToUpdate = false;
        boolean thereAreConflicts = false;
        UserDetail userDetail = null;
        ArrayList roles = null;
        Role role = null;
        String schedCreator = "";
        String eMsg = "";
        boolean emailSent = false;

        try {

            // Get the schedule object.
            ScheduleDAO schedDAO = new ScheduleDAO();

            if (aScheduleDetail != null) {

                // AUTHORIZATION - Check Authorization to Update
                schedCreator = aScheduleDetail.getCreateUserId().trim();
                if (aUserID.compareToIgnoreCase(schedCreator) == 0) {
                    authorizedToUpdate = true;
                } else {
                    UserDetailDAO userDetailDAO = new UserDetailDAO();
                    userDetail = (UserDetail) userDetailDAO.getUserByName(aUserID);
                    roles = userDetail.getRoles();
                    for (int i = 0; i < roles.size(); i++) {
                        role = (Role) roles.get(i);
                        if (role.getRole() == 'A' || role.getRole() == 'S') {
                            authorizedToUpdate = true;
                            break;
                        }
                    }  // end for
                }

                // CONFLICTS - Check for Conflicts - STAY TUNED
                if (areThereScheduleConficts(aScheduleDetail)) {
                    thereAreConflicts = true;
                }  // End Conflict Check

                // UPDATE - the schedule if authorized and there are no conflicts
                if (authorizedToUpdate && !thereAreConflicts) {
                    updated = schedDAO.update(aScheduleDetail);
                    if (updated) {
                        // Delete successful - Send notification
                        emailSent = EmailHelper.sendScheduleEmail(ApplicationConstants.SCHEDULE_UPDATE, aUserID, aScheduleDetail);
                    }
                } // Else, throw exception. See if authorization error first.
                else if (!authorizedToUpdate) {
                    // NOT AUTHORIZED - throw exception
                    eMsg = "User [" + aUserID + "] not authorized to update schedule.";
                    throw new Exception(eMsg);
                } // Else, must be a conflict condition so we throw conflict
                else {
                    // CONFLICTS EXIST - throw exception
                    eMsg = "Unable to update schedule due to conflicts.";
                    throw new Exception(eMsg);
                }

            } // Sched Detail  was not null

        } catch (DataAccessException dae) {
            throw new ScheduleException(dae);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }
        return updated;
    }

    public boolean deleteSchedule(String aUserID, int aScheduleID) throws ScheduleException {
        boolean authorizedToDelete = false;
        boolean deleted = false;
        Schedule sched = null;
        ScheduleDetail schedDetail = null;
        UserDetail userDetail = null;
        ArrayList roles = null;
        Role role = null;
        String schedCreator = "";
        boolean emailSent = false;

        try {

            // Get the schedule object.
            ScheduleDAO schedDAO = new ScheduleDAO();
            sched = (Schedule) schedDAO.listById(aScheduleID);
            schedDetail = (ScheduleDetail) schedDAO.listFullDetailByScheduleId(aScheduleID);

            if (sched != null) {

                // AUTHORIZATION - Check for authorization to delete
                schedCreator = sched.getCreateUserId().trim();
                if (aUserID.compareToIgnoreCase(schedCreator) == 0) {
                    authorizedToDelete = true;
                } else {
                    UserDetailDAO userDetailDAO = new UserDetailDAO();
                    userDetail = (UserDetail) userDetailDAO.getUserByName(aUserID);
                    roles = userDetail.getRoles();
                    for (int i = 0; i < roles.size(); i++) {
                        role = (Role) roles.get(i);
                        if (role.getRole() == 'A' || role.getRole() == 'S') {
                            authorizedToDelete = true;
                            break;
                        }
                    }
                }  // End checking authorizations

                // DELETE -  the sched if OK, else throw exception.
                if (authorizedToDelete) {
                    deleted = schedDAO.delete(sched);
                    if (deleted) {
                        // Delete successful - Send notification
                        emailSent = EmailHelper.sendScheduleEmail(ApplicationConstants.SCHEDULE_DELETE, aUserID, schedDetail);
                    }
                } else {
                    // NOT AUTHORIZED to delete - throw exception
                    String eMsg = "User [" + aUserID + "] not authorized to delete schedule.";
                    throw new Exception(eMsg);
                } // if authorizedToDelete

            } // Sched was not null

        } catch (DataAccessException dae) {
            throw new ScheduleException(dae);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }
        return deleted;
    }

    // insertSchedule - inserts a schedule given the current user's id and a ScheduleDetail.
    // returns the ScheduleID as integer
    public int insertSchedule(String aUserID, ScheduleDetail aScheduleDetail) throws ScheduleException {
        int scheduleID = -1;
        boolean thereAreConflicts = false;
        UserDetail userDetail = null;
        ArrayList roles = null;
        Role role = null;
        String schedCreator = "";
        int seqNum = 0;
        boolean emailSent = false;

        try {

            // Get the schedule object.
            ScheduleDAO schedDAO = new ScheduleDAO();

            if (aScheduleDetail != null) {

                // CONFLICTS - Check for Conflicts - STAY TUNED
                if (areThereScheduleConficts(aScheduleDetail)) {
                    thereAreConflicts = true;
                }  // End Conflict Check

                // INSERT - if there are no conflicts do the insert
                if (!thereAreConflicts) {
                    // Perform the insert
                    scheduleID = schedDAO.insert(aScheduleDetail);

                    // Insert successful - Send notification
                    emailSent = EmailHelper.sendScheduleEmail(ApplicationConstants.SCHEDULE_NEW, aUserID, aScheduleDetail);

                } else {
                    // NOT AUTHORIZED to insert - throw exception
                    String eMsg = "Unable to insert schedule due to conflicts.";
                    throw new Exception(eMsg);
                }

            } // Sched was not null

        } catch (DataAccessException dae) {
            throw new ScheduleException(dae);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return scheduleID;
    }

    public boolean areThereScheduleConficts(ScheduleDetail aScheduleDetail) throws ScheduleException {

        String eMsg = "";
        int numRequested = 0;
        boolean thereAreConflicts = false;
        Date schedStart = aScheduleDetail.getScheduleStartDate();
        Date schedEnd = aScheduleDetail.getScheduleEnddate();
        int vID = 0;
        ScheduleGroupForScheduleDetail schedGroupDetail = null;
        ScheduleDetails schedDetails = null;
        VirtualComponentDetails virtComponentDetails = null;
        VirtualComponentDetail virtComponentDetail = null;

        try {

            // GET what i need to loop through s g 4 s d
            ScheduleGroupForScheduleDetails schedGroupForSchedDetails = aScheduleDetail.getSchedGroupDetails();

            // LOOP THROUGH V COMPONENTS
            if (schedGroupForSchedDetails != null) {

                ScheduleDAO schedDAO = new ScheduleDAO();

                for (int j = 0; j < schedGroupForSchedDetails.size(); j++) {
                    schedGroupDetail = (ScheduleGroupForScheduleDetail) schedGroupForSchedDetails.get(j);

                    // Get the VID
                    virtComponentDetails = schedGroupDetail.getVirtComDetails();
                    if (virtComponentDetails != null && virtComponentDetails.size() > 0) {
                        for (int k = 0; k < virtComponentDetails.size(); k++) {
                            virtComponentDetail = (VirtualComponentDetail) virtComponentDetails.get(k);

                            // Get virtual component ID
                            vID = virtComponentDetail.getId();

                            // Get the number partitions requests.
                            numRequested = virtComponentDetail.getUsedPartitions();

                            // Call to the DB layer to get list of "possible" schedule conflicts.
                            schedDetails = schedDAO.getPossibleScheduleConflicts(schedStart, schedEnd, vID, ApplicationConstants.SCHEDULE_NEW);

                            // We have a list of Schedule Details with possible conflicts, now call method to probe the
                            // virtual components to look for component level conflicts.
                            thereAreConflicts = areThereVirtualComponentConflicts(numRequested, schedDetails);
                        }
                    }
                }

            }  // schedGroupforSchedDetails is not null

        } catch (ScheduleException se) {
            throw new ScheduleException(se);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        // if we get this far just all is well so we return false. (note:  ret intialized to false)
        return thereAreConflicts;

    }

    private boolean areThereVirtualComponentConflicts(int numRequested, ScheduleDetails schedDetailsWithPossibleConflicts) throws ScheduleException {

        ScheduleDetail schedDetailLoc = null;
        ScheduleGroupForScheduleDetails schedGroupForDetailsLoc = null;
        ScheduleGroupForScheduleDetail schedGroupForDetailLoc = null;
        VirtualComponentDetails virtComponentDetailsLoc = null;
        VirtualComponentDetail virtComponentDetailLoc = null;
        int numAvailable = 0;
        int numAllocated = 0;
        boolean thereIsError = false;
        String eMsg = "";

        try {
            // Process Conflicts
            if (schedDetailsWithPossibleConflicts != null) {
                for (int x = 0; x < schedDetailsWithPossibleConflicts.size(); x++) {

                    schedDetailLoc = (ScheduleDetail) schedDetailsWithPossibleConflicts.get(x);
                    schedGroupForDetailsLoc = schedDetailLoc.getSchedGroupDetails();

                    if (schedGroupForDetailsLoc != null) {
                        for (int y = 0; y < schedGroupForDetailsLoc.size(); y++) {

                            schedGroupForDetailLoc = (ScheduleGroupForScheduleDetail) schedGroupForDetailsLoc.get(y);
                            virtComponentDetailsLoc = schedGroupForDetailLoc.getVirtComDetails();
                            if (virtComponentDetailsLoc != null && virtComponentDetailsLoc.size() > 0) {
                                for (int z = 0; z < virtComponentDetailsLoc.size(); z++) {
                                    virtComponentDetailLoc = (VirtualComponentDetail) virtComponentDetailsLoc.get(z);
                                    if (z == 0) {
                                        numAvailable = virtComponentDetailLoc.getNumOfAvailablePartitions();
                                    }
                                    
                                    // Update my eMsg  Container, Bmid, PM, NumPartitionsUsed 
                                    //eMsg = eMsg + schedGroupForDetailLoc.getBenchmarkId();
                                    eMsg = "There was a virtual component conflict!";

                                    // Count up the number Allocated.
                                    numAllocated = numAllocated + virtComponentDetailLoc.getUsedPartitions();
                                }
                                
                                // Now that we have added up what has been allocated and know how many are available
                                // we can determine if we have enough available to accomidate this request.
                                if (numAllocated+numRequested > numAvailable) {
                                    thereIsError = true;
                                }
                            }
                        }
                    }
                    // If there was an error then throw exception, else return false and exit.
                    if (thereIsError) {
                        throw new Exception(eMsg);
                    } 
                }
            } // Input not null
          
        } catch (ScheduleException se) {
            throw new ScheduleException(se);
        } catch (Exception e) {
            throw new ScheduleException(e);
        }

        return thereIsError;
    }
}  // End of Class
