package decompiler;

import com.github.kwart.jd.JavaDecompiler;
import com.github.kwart.jd.input.JDInput;
import com.github.kwart.jd.input.ZipFileInput;
import com.github.kwart.jd.options.DecompilerOptions;
import com.github.kwart.jd.output.DirOutput;
import com.github.kwart.jd.output.JDOutput;

import java.io.File;

/**
 * @author samin on 11/17/21
 * @project aardroid
 */
public class JDCLI {

    public void decompile(String input_path, String output_path){
        JDInput input = new ZipFileInput(input_path);
        JDOutput output = new DirOutput(new File(output_path));
        JavaDecompiler decompiler = new JavaDecompiler(new DecompilerOptions() {

            @Override
            public boolean isSkipResources() {
                return true;
            }

            @Override
            public boolean isEscapeUnicodeCharacters() {
                return false;
            }

            @Override
            public boolean isDisplayLineNumbers() {
                return true;
            }

            @Override
            public boolean isParallelProcessingAllowed() {
                return true;
            }
        });
        input.decompile(decompiler, output);
    }
}
