/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark.invmgmt.command;

import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.data.FormBean;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.service.ScheduleGroupContainerService;
import benchmark.invmgmt.service.ScheduleGroupContainerServiceImpl;
import java.util.Date;
import benchmark.invmgmt.data.ScheduleGroupContainer;
import benchmark.invmgmt.exception.ScheduleGroupContainerException;
import benchmark.invmgmt.util.Messages;

/**
 *
 * @author zwang
 */
public class Sch_grp_ctnrCommand extends AbstractCommand{
    
    private static LogHelper logger = new LogHelper(Sch_grp_ctnrCommand.class);
    
    public Sch_grp_ctnrCommand(){
        
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
        String container_name = null;
        char available = (char)0;
        int partition = 0;
        int schedulegrpid = 0;
        String lastmodifieduser = null;
        int schedulegroupcontainerid = 0;
        String strPage = null;
        boolean boolSGCResult = false;
        FormBean objFormBean = null;
        ScheduleGroupContainerService SGCService = null;
        String SGCMessage = null;
        int rowsInserted = 0;
        
         try {
            if (helper != null) {
                //Get the form bean
                objFormBean = helper.getFormBean();
                if (objFormBean.containsKey("ibm-delete") && objFormBean.getValue("ibm-delete").equals("Delete")){
                    SGCService = new ScheduleGroupContainerServiceImpl();
                    schedulegrpid = objFormBean.getIntValue("ScheduleGroupContainer_ID");
                    boolSGCResult = SGCService.deleteScheduleGroupContainer(schedulegrpid);
                    if (boolSGCResult) {
                        SGCMessage = "Delete Successful";
                        strPage = "sch_grp_ctnr";
                    }
                    else {
                        SGCMessage = "Delete Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Delete Failed");
                    }
                }
                else if (objFormBean.containsKey("ibm-add") && objFormBean.getValue("ibm-add").equals("Add")){
                    SGCService = new ScheduleGroupContainerServiceImpl();
                    ScheduleGroupContainer SGC = new ScheduleGroupContainer();
                    container_name = objFormBean.getValue("Container_Name");
                    
                    lastmodifieduser = objFormBean.getValue("Last_Modified_User");
                    schedulegroupcontainerid = objFormBean.getIntValue("ScheduleGroupContainer_ID");
                    SGC.setId(schedulegroupcontainerid);
                    SGC.setContainerName(container_name);
                    SGC.setLastModifyUserId(lastmodifieduser);
                    logger.debug("Container name is:"+SGC.getContainerName());
                    rowsInserted = SGCService.insertScheduleGroupContainer(SGC);
                    if (rowsInserted == 1) {
                       SGCMessage = "Add Successful";
                       strPage = "sch_grp_ctnr";
                    }
                    else {
                        SGCMessage = "Add Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Add Failed");
                    }
                }
                else {
                    SGCService = new ScheduleGroupContainerServiceImpl();
                    ScheduleGroupContainer SGC = new ScheduleGroupContainer();
                    container_name = objFormBean.getValue("Container_Name");
                    
                    lastmodifieduser = objFormBean.getValue("Last_Modified_User");
                    schedulegroupcontainerid = objFormBean.getIntValue("ScheduleGroupContainer_ID");
                    SGC.setId(schedulegroupcontainerid);
                    SGC.setContainerName(container_name);
                    SGC.setLastModifyUserId(lastmodifieduser);
                    logger.debug("container name is:"+SGC.getContainerName());
                    boolSGCResult = SGCService.updateScheduleGroupContainer(SGC);
                    if (boolSGCResult) {
                       SGCMessage = "Update Successful";
                       strPage = "sch_grp_ctnr";
                    }
                    else {
                        SGCMessage = "Update Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Update Failed");
                }
            }
            }
    
        }
         catch (ScheduleGroupContainerException e) {
            logger.fatal("LoginException:" + e.getMessage()); 
            helper.setRequestAttribute("errorMsg", Messages.getString("login_Failure")); 
            strPage = helper.getAction();
         }
         return strPage;
}
    
    public String toString() {
        return null;
    }
    
}
    
    
    