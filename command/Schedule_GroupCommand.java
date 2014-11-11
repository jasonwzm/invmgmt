
package benchmark.invmgmt.command;

import benchmark.invmgmt.data.FormBean;
import benchmark.invmgmt.data.ScheduleGroup;
import benchmark.invmgmt.exception.ScheduleGroupException;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.service.ScheduleGroupService;
import benchmark.invmgmt.service.ScheduleGroupServiceImpl;
import benchmark.invmgmt.util.Messages;


/**
 *
 * @author zwang
 */
public class Schedule_GroupCommand extends AbstractCommand {
    
    private static LogHelper logger = new LogHelper(Schedule_GroupCommand.class);
    
    public Schedule_GroupCommand(){
        
    }

    @Override
    protected boolean isToBeProcessed(String a_strRequestMethod) {
        boolean boolBeProcessed = false;
        if ((a_strRequestMethod != null)
                && ("post".equalsIgnoreCase(a_strRequestMethod))) { //$NON-NLS-1$
            boolBeProcessed = true;
        }
        return boolBeProcessed;
    }

    @Override
    protected boolean isToBeValidated(String a_strRequestMethod) {
       return true;
    }

    @Override
    protected String doProcess(RequestHelper helper) {
        String group_name = null;
        char available = (char)0;
        int max_future_sch = 0;
        int max_sch_win = 0;
        String unit = null;
        int max_sch_win_days = 0;
        int partition = 0;
        int containerid = 0;
        String description = null;
        int groupid = 0;
        String lastmodifieduser = null;
        String strPage = null;
        boolean boolGrpResult = false;
        FormBean objFormBean = null;
        String GrpMessage = null;
        ScheduleGroupService SGService;
        int rowsInserted = 0;
        
        try {
            if (helper != null) {
                //Get the form bean
                objFormBean = helper.getFormBean();
                if (objFormBean.containsKey("ibm-delete") && objFormBean.getValue("ibm-delete").equals("Delete")){
                    SGService = new ScheduleGroupServiceImpl();
                    groupid = objFormBean.getIntValue("Schedule_Group_ID");
                    boolGrpResult = SGService.deleteScheduleGroup(groupid);
                    if (boolGrpResult) {
                        GrpMessage = "Delete Successful";
                        strPage = "schedule_group";
                    }
                    else {
                        GrpMessage = "Delete Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Delete Failed");
                    }
                }
                else if (objFormBean.containsKey("ibm-add") && objFormBean.getValue("ibm-add").equals("Add")){
                SGService = new ScheduleGroupServiceImpl();
                ScheduleGroup sg = new ScheduleGroup();
                group_name = objFormBean.getValue("Group_Name");
                available = objFormBean.getValue("Available").charAt(0);
                max_future_sch = objFormBean.getIntValue("Schedule_Future");
                max_sch_win = objFormBean.getIntValue("Schedule_Window");
                unit = objFormBean.getValue("time_unit");
                partition = objFormBean.getIntValue("Available_Partitions");
                containerid = objFormBean.getIntValue("Scheduler_Container");
                description = objFormBean.getValue("Description");
                //groupid = objFormBean.getIntValue("Schedule_Group_ID");
                lastmodifieduser = objFormBean.getValue("Last_Modified_User");
                sg.setGroupName(group_name);
                sg.setAvailableForScheduling(available);
                sg.setMaxFutureScheduleInDays(max_future_sch);
                sg.setMaxScheduleWindow(max_sch_win);
                sg.setMaxScheduleWindowTimDefinition(unit);
                sg.setNumOfPartitions(partition);
                sg.setScheduleGroupContainerId(containerid);
                sg.setDescription(description);
                //sg.setId(groupid);
                sg.setLastModifyUserId(lastmodifieduser);
                rowsInserted = SGService.insertScheduleGroup(sg);
                if (rowsInserted == 1) {
                       GrpMessage = "Add Successful";
                       strPage = "schedule_group";
                    }
                    else {
                        GrpMessage = "Add Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Add Failed");
                    }
                }
                else {        
                SGService = new ScheduleGroupServiceImpl();
                ScheduleGroup sg = new ScheduleGroup();
                group_name = objFormBean.getValue("Group_Name");
                available = objFormBean.getValue("Available").charAt(0);
                max_future_sch = objFormBean.getIntValue("Schedule_Future");
                max_sch_win = objFormBean.getIntValue("Schedule_Window");
                unit = objFormBean.getValue("time_unit");
                partition = objFormBean.getIntValue("Available_Partitions");
                containerid = objFormBean.getIntValue("Scheduler_Container");
                description = objFormBean.getValue("Description");
                groupid = objFormBean.getIntValue("Schedule_Group_ID");
                lastmodifieduser = objFormBean.getValue("Last_Modified_User");
                sg.setGroupName(group_name);
                sg.setAvailableForScheduling(available);
                sg.setMaxFutureScheduleInDays(max_future_sch);
                sg.setMaxScheduleWindow(max_sch_win);
                sg.setMaxScheduleWindowTimDefinition(unit);
                sg.setNumOfPartitions(partition);
                sg.setScheduleGroupContainerId(containerid);
                sg.setDescription(description);
                sg.setId(groupid);
                sg.setLastModifyUserId(lastmodifieduser);
                boolGrpResult = SGService.updateScheduleGroup(sg);
                if (boolGrpResult) {
                       GrpMessage = "Update Successful";
                       strPage = "schedule_group";
                    }
                    else {
                        GrpMessage = "Update Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Update Failed");
                }
                }
            }
        }
        catch (ScheduleGroupException e) {
            logger.fatal("UpdateException:" + e.getMessage()); 
            helper.setRequestAttribute("errorMsg", Messages.getString("update_Failure")); 
            strPage = helper.getAction();
         }
         return strPage;
    }
    
}
