
package benchmark.invmgmt.service;

import benchmark.invmgmt.data.AppDefinition;
import benchmark.invmgmt.data.AppDefinitions;
import benchmark.invmgmt.exception.AppDefinitionException;

/**
 *
 * @author zwang
 */
public interface AppDefinitionService {
    
    public abstract boolean updateAppDefinition(AppDefinition aDef) throws AppDefinitionException;
    public abstract boolean deleteAppDefinition(AppDefinition aDef) throws AppDefinitionException;
    public abstract int insertAppDefinition(AppDefinition aDef) throws AppDefinitionException;
    public abstract AppDefinitions getAllAppDefinitions() throws AppDefinitionException;
    public abstract AppDefinition getAppDefinitionByKey(String appDefKey) throws AppDefinitionException;
}
