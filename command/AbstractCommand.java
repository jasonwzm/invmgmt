
package benchmark.invmgmt.command;

import benchmark.invmgmt.exception.CommandException;
import benchmark.invmgmt.exception.NoCommandNeededException;
import benchmark.invmgmt.helper.RequestHelper;
import benchmark.invmgmt.validator.Validator;


/**
 * AbstractCommand
 * @author zwang
 * @version 1.0
 * @filename AbstractCommand.java
 */
public abstract class AbstractCommand implements Command {

	public final String execute (RequestHelper helper)
		throws NoCommandNeededException, CommandException {
		String page = null;
		String strReturnPage = null;
		if (helper != null) {
			//check if this has to be processed
			if (isToBeProcessed(helper.getRequestMethod())) {
				//check if this has to be validated
				if (isToBeValidated(helper.getRequestMethod())) {
					//if this has to be validated then validate this by getting the right validator
					Validator validator = helper.getValidator();
					//now, validate this
					if (!validator.validate(helper.getFormBean())) {
						page =
							getValidationFailurePage(
								helper,
								validator.getErrorMessage());
					}
				}
				//if validation succeeds, then process the rest
				//page will not be null if validation fails
				if (page == null) {
					strReturnPage = doProcess(helper);
					if (strReturnPage == null) {
						page = helper.getAction();
					} else {
						page = strReturnPage;
					}
				}
			} else {
				page = helper.getAction(); //$NON-NLS-1$
			}
		} else {
			//if helper passed in null, then retun to login page
			page = "login"; //$NON-NLS-1$
		}
		//set the action to the helper
		helper.setAction(page);
		//set the form bean to hold the form values
		helper.setRequestAttribute("beanForm", //$NON-NLS-1$
			helper.getFormBean());
		//return the page
		return page;
	}

	/**
	 * getValidationFailurePage
	 * @param helper
	 * @param a_strErrorMessage
	 * @return
	 * String
	 */
	private String getValidationFailurePage (
		RequestHelper helper,
		String a_strErrorMessage) {
		//if validation fails, then return the current page itself
		String page = helper.getAction(); //$NON-NLS-1$

		//set the error message
		helper.setRequestAttribute("errorMsg", //$NON-NLS-1$
		a_strErrorMessage);

		//if there need for any pre-population before displaying the error, do that
		if (isToBeProcessed("get")) { //$NON-NLS-1$
			helper.setRequestMethod("get"); //$NON-NLS-1$
			doProcess(helper);
		}

		return page;
	}

	/**
	 * isToBeProcessed
	 * should be overridden by the implemented class 
	 * command object should decide if it needs to process the request
	 * @param a_strRequestMethod
	 * @return
	 * boolean
	 * @post $none
	 * @pre $none
	 */
	protected abstract boolean isToBeProcessed(String a_strRequestMethod);
	/**
	 * isToBeValidated
	 * should be overridden by the implemented class
	 * command object should decide if it has to be validated
	 * @param a_strRequestMethod
	 * @return
	 * boolean
	 * @pre $none
	 * @post $none
	 */
	protected abstract boolean isToBeValidated(String a_strRequestMethod);
	/**
	* doProcess
	* abstract method that is to be implemented by the command objects
	 * @param helper
	 * @return
	 * String
	 * @pre $none
	 * @post $none
	 */
	protected abstract String doProcess(RequestHelper helper);

	/**
	 * Automatically generated method: toString
	 *
	 * @return String
	 * @post $none
	 * @pre $none
	 */
	public String toString () {
		return null;
	}
}
