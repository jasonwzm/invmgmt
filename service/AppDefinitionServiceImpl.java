
package benchmark.invmgmt.service;

import benchmark.invmgmt.bo.AppDefinitionBOImpl;
import benchmark.invmgmt.data.AppDefinition;
import benchmark.invmgmt.data.AppDefinitions;
import benchmark.invmgmt.exception.AppDefinitionException;

/**
 *
 * @author zwang
 */
public class AppDefinitionServiceImpl implements AppDefinitionService {

    // updateAppDefintion - updates an AppDefinition object
    public boolean updateAppDefinition(AppDefinition aDef) throws AppDefinitionException {
        Boolean updated = false;

        try {
            AppDefinitionBOImpl appDefBO = new AppDefinitionBOImpl();
            updated = appDefBO.updateAppDefinition(aDef);

        } catch (AppDefinitionException ade) {
            throw new AppDefinitionException(ade);
        } catch (Exception e) {
            throw new AppDefinitionException(e);
        }

        return updated;
    }

    // deleteAppDefinition - deletes an AppDefinition object
    public boolean deleteAppDefinition(AppDefinition aDef) throws AppDefinitionException {
        Boolean deleted = false;

        try {
            AppDefinitionBOImpl appDefBO = new AppDefinitionBOImpl();
            deleted = appDefBO.deleteAppDefinition(aDef);

        } catch (AppDefinitionException ade) {
            throw new AppDefinitionException(ade);
        } catch (Exception e) {
            throw new AppDefinitionException(e);
        }

        return deleted;
    }

    // insertAppDefinition - inserts an AppDefinition object
    public int insertAppDefinition(AppDefinition aDef) throws AppDefinitionException {
        int numInserted = 0;

        try {
            AppDefinitionBOImpl appDefBO = new AppDefinitionBOImpl();
            numInserted = appDefBO.insertAppDefinition(aDef);

        } catch (AppDefinitionException ade) {
            throw new AppDefinitionException(ade);
        } catch (Exception e) {
            throw new AppDefinitionException(e);
        }

        return numInserted;
    }

    // getAllAppDefinitions - returna list of all AppDefinitions
    public AppDefinitions getAllAppDefinitions() throws AppDefinitionException {
        AppDefinitions appDefs = null;

        try {
            AppDefinitionBOImpl appDefBO = new AppDefinitionBOImpl();
            appDefs = (AppDefinitions) appDefBO.getAllAppDefinitions();

        } catch (AppDefinitionException ade) {
            throw new AppDefinitionException(ade);
        } catch (Exception e) {
            throw new AppDefinitionException(e);
        }

        return appDefs;
    }
    
    // getAppDefinitionByKey - returns an AppDefinition given and AddDefinition key.
    public AppDefinition getAppDefinitionByKey(String appDefKey) throws AppDefinitionException {
        AppDefinition appDef = null;

        try {
            AppDefinitionBOImpl appDefBO = new AppDefinitionBOImpl();
            appDef = (AppDefinition) appDefBO.getAppDefinitionByKey(appDefKey);

        } catch (AppDefinitionException ade) {
            throw new AppDefinitionException(ade);
        } catch (Exception e) {
            throw new AppDefinitionException(e);
        }

        return appDef;
    }
}
