
package benchmark.invmgmt.command;

import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.util.Messages;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.data.FormBean;

/**
 *
 * @author zwang
 */
public class Schedulegroup_to_virtualcomponentCommand extends AbstractCommand{
    
    private static LogHelper logger = new LogHelper(Schedulegroup_to_virtualcomponentCommand.class);
    
    public Schedulegroup_to_virtualcomponentCommand(){
        
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
        String SGtoVCMessage = null;
        FormBean objFormBean = null;
            if (helper != null) {
                objFormBean = helper.getFormBean();
                if (objFormBean.containsKey("ibm-view-comp") && objFormBean.getValue("ibm-view-comp").equals("View Components")){
                    if (objFormBean.getIntValue("radiosg")!=-1){
                        helper.setRequestAttribute("radiosg",objFormBean.getIntValue("radiosg"));
                        SGtoVCMessage = "Redirect Successful";
                        strPage = "schedulegroup_to_virtualcomponent";
                    }
                    else {
                        SGtoVCMessage = "Redirect Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Redirect Failed");
                    }
                }
            }
         return strPage;
        
    }
    
}
