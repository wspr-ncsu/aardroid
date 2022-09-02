package objects;

import org.objectweb.asm.tree.LocalVariableNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author samin on 10/28/21
 * @project jparser
 */
public class Methods {

    int access;
    Class clazz;
    String methodName;
    String descriptor;
    String returnType;
    ArrayList<String> parameters;
    ArrayList<String> parameter_names;
    List<LocalVariableNode> local_variables;
    int parameterCount;
    String signature;

    public Methods(int a, Class c, String m, String d){

        this.access =a;
        this.clazz = c;
        this.methodName = m;
        //if(m.equals("<init>"))this.methodName = "init";
        this.descriptor =d;
        String[] temp = d.split("\\)");
        this.returnType = temp[1];
        this.signature = "L"+c.className+";."+this.methodName+":"+temp[0]+")"+temp[1];
        this.parameters = new ArrayList<String>();
        temp[0] = temp[0].substring(1);
        parseParameterString(temp[0]);



    }

    public String getSignature(){
        return this.signature;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getMethodName(){
        return this.methodName;
    }

    public int getAccessFlag(){
        return this.access;
    }

    public int getParameterCount(){
        return this.parameterCount;
    }

    public ArrayList<String> getParameters(){
        return this.parameters;
    }

    public ArrayList<String> getParameterNames(){
        return this.parameter_names;
    }

    public Class getClazz(){
        return this.clazz;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public List<LocalVariableNode> getLocal_variables() {
        return local_variables;
    }

    public void setLocalVariables (List<LocalVariableNode> locals){
        this.local_variables = locals;
    }

    public void setParameterNames(ArrayList<String> names){
        this.parameter_names = names;
    }

    private void parseParameterString(String str){

        String[] params = str.split(";");
        if(params[0].equals("")){
            parameterCount = 0;
        }
        else {
            parameterCount = params.length;
            for (int i =0; i < params.length; i++) {
                String s = params[i];
                if(s.charAt(0)=='L'){
                    parameters.add(s);
                }
                else{
                    char[]ch = s.toCharArray();

                    int startIndex =0;
                    int paranthesisCount = 0;

                    for (int j = 0; j < ch.length; j++) {



                        if(ch[j]=='L') break;

                        //handle [ symbol

                        else if(ch[j]=='['){
                            paranthesisCount++;
                        }

                        //for other primitive type we add that to our parameter list
                        else if(ch[j] == 'Z' || ch[j] == 'C' || ch[j] == 'B' || ch[j] == 'S' || ch[j] == 'I' || ch[j] == 'F'|| ch[j] == 'J' || ch[j] == 'D'){


                            if(paranthesisCount>0){
                                StringBuilder sb = new StringBuilder();
                                for (int k = 0; k < paranthesisCount; k++) {
                                    sb.append("[");
                                }
                                sb.append(String.valueOf(ch[j]));
                                parameters.add(sb.toString());
                            }
                            else{
                                parameters.add(String.valueOf(ch[j]));
                            }
                            parameterCount++;
                            paranthesisCount = 0;
                        }
                        startIndex++;
                    }
                    //add the remaining part as another parameter
                    String remaining  =s.substring(startIndex);
                    if(remaining.length()>0){
                        if(paranthesisCount>0){
                            StringBuilder sb = new StringBuilder();
                            for (int k = 0; k < paranthesisCount; k++) {
                                sb.append("[");
                            }
                            sb.append(remaining);
                            parameters.add(sb.toString());
                            paranthesisCount = 0;

                        }
                        else parameters.add(remaining);

                    }

                    if(parameterCount!=parameters.size())parameterCount =  parameters.size();
                }
            }
        }
    }
}
