/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

/**
 *
 * @author Tebogo kekana - kinotech (Pty) Ltd.
 */

public class AddressType {

    private Integer addresstypeid;
    private String addressdescription;

    public AddressType() {
    }
    
    public AddressType(int address_type_id, String address_description) {
        this.addresstypeid = address_type_id;
        this.addressdescription = address_description;         
    }
    
    public AddressType(Integer addresstypeid) {
        this.addresstypeid = addresstypeid;
    }

    public AddressType(Integer addresstypeid, String addressdescription) {
        this.addresstypeid = addresstypeid;
        this.addressdescription = addressdescription;
    }

    public Integer getAddresstypeid() {
        return addresstypeid;
    }

    public void setAddresstypeid(Integer addresstypeid) {
        this.addresstypeid = addresstypeid;
    }

    public String getAddressdescription() {
        return addressdescription;
    }

    public void setAddressdescription(String addressdescription) {
        this.addressdescription = addressdescription;
    }
    
}
