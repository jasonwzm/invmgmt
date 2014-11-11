
package benchmark.invmgmt.command;

import benchmark.invmgmt.data.FormBean;
import benchmark.invmgmt.data.Schedule;
import benchmark.invmgmt.exception.ScheduleException;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.service.ScheduleServiceImpl;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zwang
 */
public class ScheduleCommand extends AbstractCommand{
    
    private static LogHelper logger = new LogHelper(ScheduleCommand.class);
    
    public ScheduleCommand(){
        
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
        String strPage = null;
        String scheduleMessage = null;
        FormBean objFormBean = null;
        Schedule schedule = null;
            if (helper != null) {
                objFormBean = helper.getFormBean();
                if (objFormBean.containsKey("schedule_delete") && objFormBean.getValue("schedule_delete").equals("delete")){
                    if (objFormBean.getIntValue("radioschedule")!=-1){
                        ScheduleServiceImpl scheduleService = new ScheduleServiceImpl();
                        try {
                            schedule = scheduleService.getSchedule(objFormBean.getIntValue("radioschedule"));
                        } catch (ScheduleException ex) {
                            helper.setRequestAttribute("errorMsg", "Schedule Get Failed");
                        }
                        try {
                            boolean deleted = scheduleService.deleteSchedule(objFormBean.getValue("userdelete"),schedule.getId());
                        } catch (ScheduleException ex) {
                            helper.setRequestAttribute("errorMsg", "Delete Failed");
                        }
                        scheduleMessage = "Redirect Successful";
                        strPage = "schedule";
                    }
                    else {
                        scheduleMessage = "Redirect Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Delete Failed");
                    }
                }
                else if (objFormBean.containsKey("schedule_add") && objFormBean.getValue("schedule_add").equals("add")){
                    strPage = "schedule";
                }
            }
         return strPage;
    }
    
}
