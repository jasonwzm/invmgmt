
package benchmark.invmgmt.command;

import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.data.FormBean;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.service.VirtualComponentsService;
import benchmark.invmgmt.service.VirtualComponentsServiceImpl;
import java.util.Date;
import benchmark.invmgmt.data.VirtualComponent;
import benchmark.invmgmt.exception.VirtualComponentsException;
import benchmark.invmgmt.util.Messages;

/**
 *
 * @author zwang
 */
public class Virtual_ComponentCommand extends AbstractCommand{
    
    private static LogHelper logger = new LogHelper(Virtual_ComponentCommand.class);
    
    public Virtual_ComponentCommand(){
        
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
        String machine_name = null;
        Date scheduler_start = null;
        Date scheduler_end = null;
        char available = (char)0;
        int partition = 0;
        String description = null;
        int machineid = 0;
        String lastmodifieduser = null;
        String strPage = null;
        boolean boolVCResult = false;
        FormBean objFormBean = null;
        VirtualComponentsService VCService = null;
        String VCMessage = null;
        int rowsInserted = 0;
        
         try {
            if (helper != null) {
                //Get the form bean
                objFormBean = helper.getFormBean();
                if (objFormBean.containsKey("ibm-delete") && objFormBean.getValue("ibm-delete").equals("Delete")){
                    VCService = new VirtualComponentsServiceImpl();
                    machineid = objFormBean.getIntValue("Machine_ID");
                    boolVCResult = VCService.deleteVirtualComponent(machineid);
                    if (boolVCResult) {
                        VCMessage = "Delete Successful";
                        strPage = "virtual_component";
                    }
                    else {
                        VCMessage = "Delete Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Delete Failed");
                    }
                }
                else if (objFormBean.containsKey("ibm-add") && objFormBean.getValue("ibm-add").equals("Add")){
                    VCService = new VirtualComponentsServiceImpl();
                    VirtualComponent vc = new VirtualComponent();
                    machine_name = objFormBean.getValue("Machine_Name");
                    scheduler_start = objFormBean.getDateValue("Scheduler_Start");
                    scheduler_end = objFormBean.getDateValue("Scheduler_End");
                    available = objFormBean.getValue("Available").charAt(0);
                    partition = objFormBean.getIntValue("Available_Partitions");
                    description = objFormBean.getValue("Description");
                    //machineid = objFormBean.getIntValue("Machine_ID");
                    lastmodifieduser = objFormBean.getValue("Last_Modified_User");
                    vc.setMachineName(machine_name);
                    vc.setAvailStartDate(scheduler_start);
                    vc.setAvailEndDate(scheduler_end);
                    vc.setAvailForScheduler(available);
                    vc.setNumOfAvailablePartitions(partition);
                    vc.setDescription(description);
                    //vc.setId(machineid);
                    vc.setLastModifyUserId(lastmodifieduser);
                    logger.debug("machine name is:"+vc.getMachineName());
                    rowsInserted = VCService.insertVirtualComponent(vc);
                    if (rowsInserted == 1) {
                       VCMessage = "Add Successful";
                       strPage = "virtual_component";
                    }
                    else {
                        VCMessage = "Add Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Add Failed");
                    }
                }
                else {
                    VCService = new VirtualComponentsServiceImpl();
                    VirtualComponent vc = new VirtualComponent();
                    machine_name = objFormBean.getValue("Machine_Name");
                    scheduler_start = objFormBean.getDateValue("Scheduler_Start");
                    scheduler_end = objFormBean.getDateValue("Scheduler_End");
                    available = objFormBean.getValue("Available").charAt(0);
                    partition = objFormBean.getIntValue("Available_Partitions");
                    description = objFormBean.getValue("Description");
                    machineid = objFormBean.getIntValue("Machine_ID");
                    lastmodifieduser = objFormBean.getValue("Last_Modified_User");
                    vc.setMachineName(machine_name);
                    vc.setAvailStartDate(scheduler_start);
                    vc.setAvailEndDate(scheduler_end);
                    vc.setAvailForScheduler(available);
                    vc.setNumOfAvailablePartitions(partition);
                    vc.setDescription(description);
                    vc.setId(machineid);
                    vc.setLastModifyUserId(lastmodifieduser);
                    logger.debug("machine name is:"+vc.getMachineName());
                    boolVCResult = VCService.updateVirtualComponent(vc);
                    if (boolVCResult) {
                       VCMessage = "Update Successful";
                       strPage = "virtual_component";
                    }
                    else {
                        VCMessage = "Update Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Update Failed");
                }
            }
            }
    
        }
         catch (VirtualComponentsException e) {
            logger.fatal("UpdateException:" + e.getMessage()); 
            helper.setRequestAttribute("errorMsg", Messages.getString("update_Failure")); 
            strPage = helper.getAction();
         }
         return strPage;
}
    
    public String toString() {
        return null;
    }
    
}
    
    
    