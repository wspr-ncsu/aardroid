package parser;

/**
 * @author samin on 12/1/21
 * @project aardroid
 */
public class Utils {

    public static String splitCamelCase(String  input)
    {
        StringBuilder sb = new StringBuilder();
        for (String w : input.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            //System.out.println(w);
            sb.append(w+" ");
        }
        //System.out.println(sb);
        return sb.toString();

    }

    public static String processLiteral(String input){

        String output  = "";
        if(input.contains("_")){
            output  = input.replace("_","");

        }
        else{
            input =  input.replaceAll("[0-9]","");
            output = splitCamelCase(input);
        }

        output = output.toLowerCase();
        output =  output.replace(" ","");
        //System.out.println(output);
        return output;
    }

    public static String processSynonym(String input){

        String output  = "";

        output =  input.replaceAll("[0-9]","");
        output = output.toLowerCase();
        output =  output.replace(" ","");
        //System.out.println(output);
        return output;
    }
}
