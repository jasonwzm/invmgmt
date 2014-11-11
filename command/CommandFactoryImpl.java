
package benchmark.invmgmt.command;

import benchmark.invmgmt.exception.CommandException;
import benchmark.invmgmt.exception.NoCommandNeededException;
import benchmark.invmgmt.helper.LogHelper;
import benchmark.invmgmt.util.MappingUtil;
import benchmark.invmgmt.util.StringUtil;
import benchmark.invmgmt.validator.Validator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * CommandFactoryImpl
 * @author zwang
 * @version 1.0
 * @filename CommandFactoryImpl.java
 */
public final class CommandFactoryImpl implements CommandFactory {
	/** Automatically generated javadoc for: COMMAND_MAP_SIZE */
    private static final int COMMAND_MAP_SIZE = 3;
    /**
	 * 
	 */
	private static LogHelper logger = new LogHelper(CommandFactory.class);
	private final Set ActionSetWithNoCommandObjects = new HashSet(2);

	private Map commands = null;

	/**
	 * CommandFactoryImpl - constructor
	 * @post $none
	 * @pre $none
	 */
	public CommandFactoryImpl () {
		StringUtil.addToSet(
			MappingUtil.getConfiguration("no_command_actions"), //$NON-NLS-1$
			ActionSetWithNoCommandObjects,
			"_command"); //$NON-NLS-1$ Suffix to add to identify command object 
		commands = new HashMap(COMMAND_MAP_SIZE);
	}

	/**
	* createCommand
	* Creates a Command instance for a specified action and a 
	* specified set of properties.
	 * @param action
	 * @return
	 * @throws NoCommandNeededException
	 * @throws CommandException
	 * @post $none
	 * @pre $none
	 */
	public final Command createCommand (String action)
		throws NoCommandNeededException, CommandException {
		logger.debug("action"+ action);
                
                return (Command) getActionObject(action);
	}

	/**
	 * createValidator
	 * Creates a Validator instance for a specified action and a 
	* specified set of properties.
	 * @param action
	 * @return
	 * @throws NoCommandNeededException
	 * @throws CommandException
	 * @post $none
	 * @pre $none
	 */
	public final Validator createValidator (String action)
		throws NoCommandNeededException, CommandException {
		return (Validator) getActionObject(action);
	}

	/**
	 * getActionObject
	 * returns the object that is stored in a collection class 
	 * if the object exists, in the collection then it returns the same
	 * else, it creates a new object and adds it to the collection
	 * returns a string that represents the key for the collection and
	 * which is also the name as in property file
	 * @param action
	 * @return
	 * @throws CommandException
	 * @throws NoCommandNeededException
	 * Object
	 */
	private Object getActionObject (String action)
		throws CommandException, NoCommandNeededException {
		//init the parameters
		Object objCommand = null;	
		//check if command object has to exist for this action
		if (ActionSetWithNoCommandObjects.contains(action)) {
			throw new NoCommandNeededException("No Command Needed");  //$NON-NLS-1$
		}
		//if command contains object, return the same	
		if (commands.containsKey(action)) {
			objCommand = commands.get(action);
		} else {
			try {
					commands.put(
					action,
					Class
						.forName(MappingUtil.getActionMapping(action))
						.newInstance());
				objCommand = commands.get(action);
			} catch (Exception e) {
				logger.error(
					"Command not instantitated for action=" + action //$NON-NLS-1$
						+ " and the cause is"  + e.getMessage()); //$NON-NLS-1$					
				throw new CommandException(
					"command not created for action= " + action,  //$NON-NLS-1$
					e);
			}
		}
                logger.debug("objCommand");
		return objCommand;
	}

	/**
	 * Automatically generated method: toString
	 *
	 * @return String
	 * @pre $none
	 * @post $none
	 */
	public String toString () {
		return null;
	}

}
