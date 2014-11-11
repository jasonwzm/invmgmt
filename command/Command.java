
package benchmark.invmgmt.command;

import benchmark.invmgmt.exception.CommandException;
import benchmark.invmgmt.exception.NoCommandNeededException;
import benchmark.invmgmt.helper.RequestHelper;



/**
 * Command
 * @author zwang
 * @version 1.0
 * @filename Command.java
 */
public interface Command {

	String execute (RequestHelper helper)  
		throws NoCommandNeededException, CommandException;

}
