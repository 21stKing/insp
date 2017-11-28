
package inventory_system;

/**
 *
 * @author tebogo
 */

public class Barcode  {
    
    private Integer barcodeid;
    private String barcode;
    private Integer imageid;

    public Barcode() {
    }
    
    /**
     * Barcode class constructor
     * @param barcodeid
     * @param barcode
     * @param imageid 
     */
    public Barcode(int barcodeid, String barcode, int imageid) {
        this.barcodeid = barcodeid;
        this.barcode = barcode;
        this.imageid = imageid;
    }
    public int getBarcodeid() {
        return barcodeid;
    }
    public void setBarcodeid(Integer barcodeid) {
        this.barcodeid = barcodeid;
    }
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public Integer getImageid() {
        return imageid;
    }
    public void setImageid(Integer imageid) {
        this.imageid = imageid;
    }
}
