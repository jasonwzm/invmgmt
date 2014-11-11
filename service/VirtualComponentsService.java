
package benchmark.invmgmt.service;

import benchmark.invmgmt.data.VirtualComponent;
import benchmark.invmgmt.data.VirtualComponents;
import benchmark.invmgmt.data.VirtualSearchComponent;
import benchmark.invmgmt.exception.VirtualComponentsException;

/**
 *
 * @author zwang
 */
public interface VirtualComponentsService {

    public abstract VirtualComponents getVirtualComponents() throws VirtualComponentsException;

    public abstract VirtualComponent getVirtualComponent(int aId) throws VirtualComponentsException;

    public abstract boolean updateVirtualComponent(VirtualComponent aVirtComponent) throws VirtualComponentsException;

    public abstract boolean deleteVirtualComponent(int aId) throws VirtualComponentsException;

    public abstract int insertVirtualComponent(VirtualComponent aVirtComponent) throws VirtualComponentsException;
    
     public abstract VirtualComponents search(VirtualSearchComponent aSearchComp) throws VirtualComponentsException;
}
