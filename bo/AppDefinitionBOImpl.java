package benchmark.invmgmt.bo;

import benchmark.invmgmt.dao.AppDefinitionDAO;
import benchmark.invmgmt.data.AppDefinition;
import benchmark.invmgmt.data.AppDefinitions;
import benchmark.invmgmt.exception.AppDefinitionException;
import benchmark.invmgmt.exception.DataAccessException;
import benchmark.invmgmt.helper.LogHelper;

/**
 *
 * @author zwang
 */
public class AppDefinitionBOImpl implements AppDefinitionBO{
    
    private static LogHelper logger = new LogHelper(AppDefinitionBOImpl.class);

    public AppDefinitionBOImpl() {
    }

    // updateAppDefinition - updates an AppDefinition object
    public boolean updateAppDefinition(AppDefinition aDef) throws AppDefinitionException {
        
       Boolean updated = false;
       
        try {
            AppDefinitionDAO appDefDAO = new AppDefinitionDAO();
            updated =  appDefDAO.update(aDef);

        } catch (DataAccessException dae) {
            throw new AppDefinitionException(dae);
        } catch (Exception e) {
            throw new AppDefinitionException(e);
        }
       
       return updated;
        
    }

    // deleteAppDefinition - deletes an AppDefinition object
    public boolean deleteAppDefinition(AppDefinition aDef) throws AppDefinitionException {
        Boolean deleted = false;

        try {
            AppDefinitionDAO appDefDAO = new AppDefinitionDAO();
            deleted = appDefDAO.delete(aDef);

        } catch (DataAccessException dae) {
            throw new AppDefinitionException(dae);
        } catch (Exception e) {
            throw new AppDefinitionException(e);
        }

        return deleted;
    }

    // insertAppDefinition - inserts an AppDefinition object
    public int insertAppDefinition(AppDefinition aDef) throws AppDefinitionException {
         int numInserted = 0;
       
        try {
            AppDefinitionDAO appDefDAO = new AppDefinitionDAO();
            numInserted =  appDefDAO.insert(aDef);

        } catch (DataAccessException dae) {
            throw new AppDefinitionException(dae);
        } catch (Exception e) {
            throw new AppDefinitionException(e);
        }
       
       return numInserted;
    }

    // getAllAppDefinitions - returna list of all AppDefinitions
    public AppDefinitions getAllAppDefinitions() throws AppDefinitionException {
         AppDefinitions appDefs = null;
       
        try {
            AppDefinitionDAO appDefDAO = new AppDefinitionDAO();
            appDefs =  (AppDefinitions) appDefDAO.list();

        } catch (DataAccessException dae) {
            throw new AppDefinitionException(dae);
        } catch (Exception e) {
            throw new AppDefinitionException(e);
        }
       
       return appDefs;
    }

    // getAppDefinitionByKey - returns an AppDefinition given and AddDefinition key.
    public AppDefinition getAppDefinitionByKey(String appDefKey) throws AppDefinitionException {
        AppDefinition appDef = null;
       
        try {
            AppDefinitionDAO appDefDAO = new AppDefinitionDAO();
            appDef =  (AppDefinition) appDefDAO.listByKey(appDefKey);

        } catch (DataAccessException dae) {
            throw new AppDefinitionException(dae);
        } catch (Exception e) {
            throw new AppDefinitionException(e);
        }
       
       return appDef;
    }
    
}
