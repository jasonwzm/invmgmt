
package benchmark.invmgmt.command;

import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.util.Messages;

/*
 import com.ibm.pnmap.command.AbstractCommand;
 import com.ibm.pnmap.helper.LogHelper;
 import com.ibm.pnmap.helper.RequestHelper;
 import com.ibm.pnmap.util.Messages;
 */
/**
 * LogoutCommand
 *
 * @author zwang
 * @version 1.0
 * @filename LogoutCommand.java
 */
public class LogoutCommand extends AbstractCommand {

    /**
     * Logger
     */
    private static LogHelper logger = new LogHelper(LogoutCommand.class);

    /**
     * Automatically generated constructor: LogoutCommand
     *
     * @post $none
     * @pre $none
     */
    public LogoutCommand() {
    }

    /**
     * doProcess
     * @param helper
     * @return
     * @pre helper != null
     * @post $none
     */
    protected String doProcess(RequestHelper helper) {
        logger.debug("logout is called"); 
        if (helper != null) {
            helper.destroySession();
            helper.setRequestAttribute("errorMsg", Messages.getString("logout_success"));	 		
        }
        //return the view name
        return "index";		
    }

    /**
     * isToBeValidated
     * @param a_strRequestMethod
     * @return
     * @pre $none
     * @post $none
     */
    protected boolean isToBeValidated(String a_strRequestMethod) {
        return false;
    }

    /**
     * isToBeProcessed
     * @param a_strRequestMethod
     * @return
     * @post $none
     * @pre $none
     */
    protected boolean isToBeProcessed(String a_strRequestMethod) {
        return true;
    }

    /**
     * Automatically generated method: toString
     *
     * @return String
     * @pre $none
     * @post $none
     */
    public String toString() {
        return null;
    }
}
