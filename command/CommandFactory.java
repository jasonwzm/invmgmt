
package benchmark.invmgmt.command;

import benchmark.invmgmt.exception.CommandException;
import benchmark.invmgmt.exception.NoCommandNeededException;
import benchmark.invmgmt.validator.Validator;

/**
 * CommandFactory
 * @author zwang
 * @version 1.0
 * @filename CommandFactory.java
 */
public interface CommandFactory {

	/**
	 * createCommand
	 * @param action
	 * @return
	 * @throws NoCommandNeededException
	 * @throws CommandException
	 * Command
	 * @pre $none
	 * @post $none
	 */
	Command createCommand (String action)
		throws NoCommandNeededException, CommandException;

	/**
	 * createValidator
	 * Creates a Command instance for a specified action and a 
	 * specified set of properties
	 * @param action
	 * @return
	 * @throws NoCommandNeededException
	 * @throws CommandException
	 * Validator
	 * @pre $none
	 * @post $none
	 */
	Validator createValidator (String action)
		throws NoCommandNeededException, CommandException;
}
