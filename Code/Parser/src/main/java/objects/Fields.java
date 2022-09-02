package objects;

/**
 * @author samin on 10/28/21
 * @project jparser
 */
public class Fields {

    int access;
    String fieldName;
    String descriptor;
    String signature;
    Object value;

    public Fields(int a, String f, String d, String s, Object v){

        this.access =a;
        this.fieldName = f;
        this.descriptor =d;
        this.signature =s;
        this.value =v;
    }

    public String getSignature(){
        return this.signature;
    }

    public String getDescriptor(){
        return this.descriptor;
    }

    public String getFieldName(){
        return this.fieldName;
    }
}
