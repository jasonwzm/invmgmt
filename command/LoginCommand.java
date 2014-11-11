
package benchmark.invmgmt.command;

import benchmark.invmgmt.data.FormBean;
import benchmark.invmgmt.exception.LoginException;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.service.LoginService;
import benchmark.invmgmt.service.LoginServiceImpl;
import benchmark.invmgmt.util.Messages;

/**
 * LoginCommand
 *
 * @author zwang
 * @version 1.0
 * @filename LoginCommand.java
 * @package com.ibm.pnmap.command
 * @invariant $none
 */
public class LoginCommand extends AbstractCommand {

    /**
     * Logger
     */
    private static LogHelper logger = new LogHelper(LoginCommand.class);

    /**
     * getLoginPopup
     *
     * @param a_objRequestHelper
     * @return String
     */
    private static String getLoginPopup(
            RequestHelper a_objRequestHelper, String a_strRedirectPage) {
        //get the page that has to be redirected after login
        //set that page as attribute ti request so that after
        //logging he will be redirected to that page									
        a_objRequestHelper.setRequestAttribute("redirectPage", //$NON-NLS-1$
                a_strRedirectPage); //$NON-NLS-1$

        logger.debug("redirected page is " + //$NON-NLS-1$
                a_strRedirectPage); //$NON-NLS-1$

        //return the login popup result page
        return "loginpopupsuccess"; //$NON-NLS-1$							
    }

    /**
     * Automatically generated constructor: LoginCommand
     *
     * @post $none
     * @pre $none
     */
    public LoginCommand() {
    }

    /**
     * doProcess
     * @param helper
     * @return
     * @pre helper != null
     * @post $none
     */
    protected String doProcess(RequestHelper helper) {
        //init the parameters
        String strLoginMessage = null;
        String strUserId = null;
        String strPage = null;
        boolean boolLoginResult = false;
        FormBean objFormBean = null;
        LoginService objLoginService = null;

        try {
            if (helper != null) {
                //Get the form bean
                objFormBean = helper.getFormBean();

                // get the user id
                strUserId = objFormBean.getValue("userid"); 

                //delegate the business processing to the coresponding services
                objLoginService = new LoginServiceImpl();

                //authenticate using the service
                boolLoginResult = objLoginService.authenticate(
                        strUserId,
                        objFormBean.getValue("password")); 

                logger.debug("login result is=> " + boolLoginResult); 				

                //depending upon the result, take action
                if (boolLoginResult) {
                    helper.createSession(strUserId);
                    strLoginMessage = "login_Success"; //$NON-NLS-1$

                    //If this is login popup, we need to close and 
                    //redirect to the page user has clicked on	
                    if (objFormBean.getValue("popup") != null) { 
                        strPage = getLoginPopup(helper, objFormBean.getValue("redirectPage")); 
                    } else {
                        //strPage = "import";
                        strPage = "invmgmt_main";
                    }
                } else {
                    strLoginMessage = "login_InvalidCredentials";
                    //set the error message								
                    helper.setRequestAttribute("errorMsg", Messages.getString(strLoginMessage));
                }
                logger.debug("strLoginMessage => " + strLoginMessage);
                logger.debug("PAGE => " + strPage);
            }
        } catch (LoginException e) {
            logger.fatal("LoginException:" + e.getMessage()); 
            helper.setRequestAttribute("errorMsg", Messages.getString("login_Failure")); 
            strPage = helper.getAction();
        }
        //return the view name
        return strPage;
    }

    /**
     * isToBeProcessed
     * @param a_strRequestMethod
     * @return
     * @post $none
     * @pre $none
     */
    protected boolean isToBeProcessed(String a_strRequestMethod) {
        boolean boolBeProcessed = false;
        if ((a_strRequestMethod != null)
                && ("post".equalsIgnoreCase(a_strRequestMethod))) { //$NON-NLS-1$
            boolBeProcessed = true;
        }
        return boolBeProcessed;
    }

    /**
     * isToBeValidated
     * @param a_strRequestMethod
     * @return
     * @post $none
     * @pre $none
     */
    protected boolean isToBeValidated(String a_strRequestMethod) {
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