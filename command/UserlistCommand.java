
package benchmark.invmgmt.command;

import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.data.FormBean;
import benchmark.invmgmt.data.Role;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.service.UserService;
import benchmark.invmgmt.service.UserServiceImpl;
import java.util.Date;
import benchmark.invmgmt.data.UserDetail;
import benchmark.invmgmt.exception.UserException;
import benchmark.invmgmt.exception.UserRoleException;
import benchmark.invmgmt.util.Messages;
import java.util.ArrayList;

/**
 *
 * @author zwang
 */
public class UserlistCommand extends AbstractCommand {

    private static LogHelper logger = new LogHelper(UserlistCommand.class);

    public UserlistCommand() {
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
        String userID = null;
        String userEmail = null;
        String userCountry = null;
        char userStatus = (char) 0;
        String userRoles[] = null;
        ArrayList al = new ArrayList();

        String lastmodifieduser = null;
        String strPage = null;
        boolean boolUDResult = false;
        FormBean objFormBean = null;
        UserService USService = null;
        String UDMessage = null;
        int rowsInserted = 0;

        if (helper != null) {
            //Get the form bean
            objFormBean = helper.getFormBean();
            if (objFormBean.containsKey("ibm-delete") && objFormBean.getValue("ibm-delete").equals("Delete")) {
                USService = new UserServiceImpl();
                userID = objFormBean.getValue("User_ID");
                try {
                    boolUDResult = USService.deleteUser(userID);
                    if (boolUDResult) {
                        UDMessage = "Delete Successful";
                        strPage = "userlist";
                    } else {
                        UDMessage = "Delete Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Delete Failed");
                    }
                } catch (UserException e) {
                    logger.fatal("UpdateException:" + e.getMessage());
                    helper.setRequestAttribute("errorMsg", Messages.getString("delete_Failure"));
                    strPage = helper.getAction();
                }
            } else if (objFormBean.containsKey("ibm-add") && objFormBean.getValue("ibm-add").equals("Add")) {
                USService = new UserServiceImpl();
                UserDetail ud = new UserDetail();
                userID = objFormBean.getValue("User_ID");
                userEmail = objFormBean.getValue("User_Email");
                userCountry = objFormBean.getValue("User_Country");
                userStatus = objFormBean.getValue("User_Status").charAt(0);
                userRoles = objFormBean.getMultiValues("User_Roles");
                
                for (int x = 0; x < userRoles.length; x++) {
                    Role r1 = new Role();
                    r1.setRole(userRoles[x].charAt(0));
        
                    al.add(r1);
                }              

                lastmodifieduser = objFormBean.getValue("Last_Modified_User");
                ud.setUserId(userID);
                ud.setEmail(userEmail);
                ud.setCountry(userCountry);
                ud.setActive(userStatus);
                ud.setRoles(al);
                ud.setLastModifyUserId(lastmodifieduser);
                logger.debug("User ID is: " + ud.getUserId());
                try {
                    rowsInserted = USService.insertUser(ud);

                    if (rowsInserted == 1) {
                        UDMessage = "Add Successful";
                        strPage = "userlist";
                    } else {
                        UDMessage = "Add Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Add Failed");
                    }
                } catch (UserException e) {
                    logger.fatal("UpdateException:" + e.getMessage());
                    helper.setRequestAttribute("errorMsg", Messages.getString("add_Failure"));
                    strPage = helper.getAction();
                }
            } else {
                USService = new UserServiceImpl();
                UserDetail ud = new UserDetail();
                userID = objFormBean.getValue("User_ID");
                userEmail = objFormBean.getValue("User_Email");
                userCountry = objFormBean.getValue("User_Country");
                userStatus = objFormBean.getValue("User_Status").charAt(0);
                userRoles = objFormBean.getMultiValues("User_Roles");
               
                for (int x = 0; x < userRoles.length; x++) {
                    Role r1 = new Role();
                    r1.setRole(userRoles[x].charAt(0));
        
                    al.add(r1);
                }              
        
                lastmodifieduser = objFormBean.getValue("Last_Modified_User");           
                
                ud.setUserId(userID);
                ud.setEmail(userEmail);
                ud.setCountry(userCountry);
                ud.setActive(userStatus);
                ud.setRoles(al);
                ud.setLastModifyUserId(lastmodifieduser);
                logger.debug("User ID is: " + ud.getUserId());
                try {
                    boolUDResult = USService.updateUser(ud);

                    if (boolUDResult) {
                       UDMessage = "Update Successful";
                       strPage = "userlist";
                    }
                    else {
                        UDMessage = "Update Failed";
                        //set the error message								
                        helper.setRequestAttribute("errorMsg", "Update Failed");
                    }

                } catch (UserException e) {
                    logger.fatal("UpdateException:" + e.getMessage());
                    helper.setRequestAttribute("errorMsg", Messages.getString("update_Failure"));
                    strPage = helper.getAction();
                }
            }


        }
        return strPage;
    }

    public String toString() {
        return null;
    }
}
