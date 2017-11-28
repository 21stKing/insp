/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventory_system;


public class UnitMeasurement  {

    private Integer umid;
    private String umDescription;
    private int umState;

    public UnitMeasurement() {
    }

    public UnitMeasurement(Integer umid) {
        this.umid = umid;
    }

    public UnitMeasurement(Integer umid, String umDescription, int umState) {
        this.umid = umid;
        this.umDescription = umDescription;
        this.umState = umState;
    }

    public Integer getUmid() {
        return umid;
    }

    public void setUmid(Integer umid) {
        this.umid = umid;
    }

    public String getUmDescription() {
        return umDescription;
    }

    public void setUmDescription(String umDescription) {
        this.umDescription = umDescription;
    }

    public int getUmState() {
        return umState;
    }

    public void setUmState(int umState) {
        this.umState = umState;
    }
    
}
