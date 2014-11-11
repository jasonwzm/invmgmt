
package benchmark.invmgmt.data;

/**
 *
 * @author zwang
 */
public class VirtualComponentDetail extends VirtualComponent {
    private int usedPartitions = 0;

    public VirtualComponentDetail(VirtualComponent aVirtual) {
        this.setMachineName(aVirtual.getMachineName());
        this.setAvailStartDate(aVirtual.getAvailStartDate());
        this.setAvailEndDate(aVirtual.getAvailEndDate());
        this.setAvailForScheduler(aVirtual.getAvailForScheduler());
        this.setNumOfAvailablePartitions(aVirtual.getNumOfAvailablePartitions());
        this.setDescription(aVirtual.getDescription());
        this.setId(aVirtual.getId());
        this.setLastModifyUserId(aVirtual.getLastModifyUserId());
        this.setCreateUserId(aVirtual.getCreateUserId());
    }
    
    public VirtualComponentDetail() {}
    
    /**
     * @return the usedPartitions
     */
    public int getUsedPartitions() {
        return usedPartitions;
    }

    /**
     * @param usedPartitions the usedPartitions to set
     */
    public void setUsedPartitions(int usedPartitions) {
        this.usedPartitions = usedPartitions;
    }
    
    public String toString() {
        String retStr = super.toString() + "usedPartitions[" + usedPartitions + "]";
        return retStr;
    }
}
