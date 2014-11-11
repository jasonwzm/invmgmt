
package benchmark.invmgmt.data;

import java.util.Date;

/**
 *
 * @author zwang
 */
public class VirtualSearchComponent extends VirtualComponent {
    private boolean machineNameBeenSet = false;
    private boolean availStartDateBeenSet = false;
    private boolean availEndDateBeenSet = false;
    private boolean availForSchedulerBeenSet = false;
    private boolean numOfAvailablePartitionsBeenSet = false;
    private boolean descriptionBeenSet = false;
    private String startDateOperator = "NONE";
    private String endDateOperator = "NONE";
    private String numPartitionsOperator = "NONE";
    
    public void setMachineName(String machineName) {
        super.setMachineName(machineName);
        this.machineNameBeenSet = true;
    }
    
    public void setAvailStartDate(Date availStartDate) {
        super.setAvailStartDate(availStartDate);
        this.availStartDateBeenSet = true;
    }
    
    public void setAvailEndDate(Date availEndDate) {
        super.setAvailEndDate(availEndDate);
        this.availEndDateBeenSet = true;
    }
    
    public void setAvailForScheduler(char availForScheduler) {
        super.setAvailForScheduler(availForScheduler);
        this.availForSchedulerBeenSet = true;
    }
    
    public void setNumOfAvailablePartitions(int numOfAvailablePartitions) {
        super.setNumOfAvailablePartitions(numOfAvailablePartitions);
        this.numOfAvailablePartitionsBeenSet = true;
    }
    
    public void setDescription(String description) {
        super.setDescription(description);
        this.descriptionBeenSet = true;
    }

    /**
     * @return the startDateOperator
     */
    public String getStartDateOperator() {
        return startDateOperator;
    }

    /**
     * @param startDateOperator the startDateOperator to set
     */
    public void setStartDateOperator(String startDateOperator) {
        this.startDateOperator = startDateOperator;
    }

    /**
     * @return the endDateOperator
     */
    public String getEndDateOperator() {
        return endDateOperator;
    }

    /**
     * @param endDateOperator the endDateOperator to set
     */
    public void setEndDateOperator(String endDateOperator) {
        this.endDateOperator = endDateOperator;
    }

    /**
     * @return the numPartitionsOperator
     */
    public String getNumPartitionsOperator() {
        return numPartitionsOperator;
    }

    /**
     * @param numPartitionsOperator the numPartitionsOperator to set
     */
    public void setNumPartitionsOperator(String numPartitionsOperator) {
        this.numPartitionsOperator = numPartitionsOperator;
    }

    /**
     * @return the machineNameBeenSet
     */
    public boolean isMachineNameBeenSet() {
        return machineNameBeenSet;
    }

    /**
     * @return the availStartDateBeenSet
     */
    public boolean isAvailStartDateBeenSet() {
        return availStartDateBeenSet;
    }

    /**
     * @return the availEndDateBeenSet
     */
    public boolean isAvailEndDateBeenSet() {
        return availEndDateBeenSet;
    }

    /**
     * @return the availForSchedulerBeenSet
     */
    public boolean isAvailForSchedulerBeenSet() {
        return availForSchedulerBeenSet;
    }

    /**
     * @return the numOfAvailablePartitionsBeenSet
     */
    public boolean isNumOfAvailablePartitionsBeenSet() {
        return numOfAvailablePartitionsBeenSet;
    }

    /**
     * @return the descriptionBeenSet
     */
    public boolean isDescriptionBeenSet() {
        return descriptionBeenSet;
    }
}
