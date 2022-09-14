package net.shiue;
import com.typesafe.config.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Converts a Json file to a Hocon file.
 *
 * e.g.
 * ```
 * java -jar thisJar.jar /temp/fileA.json /temp/fileB.conf
 * ```
 * The destination file will be overwritten.
 */
public class JsonFileToHoconFile
{
    public static void main( String[] args )
    {

        if(args.length < 2){
            System.out.println("Invalid arguments. please pass 2 file paths( source and dest ) to proceed.");
            System.exit(1);
            return;
        }

        File source = new File(args[0]);
        String dest = args[1];

        if(!source.exists()){
            System.out.println("The source file does not existed.");
            System.exit(1);
            return;
        }

        try{
            ConfigParseOptions options = ConfigParseOptions.defaults()
                    .setSyntax(ConfigSyntax.JSON)
                    .setAllowMissing(false);

            Config conf = ConfigFactory.parseFile(source, options);
            ConfigRenderOptions renderOptions = ConfigRenderOptions.concise().setJson(false).setFormatted(true);
            String out = conf.root().render(renderOptions);
            System.out.println(out);
            FileWriter writer = new FileWriter(dest, false);

            writer.write(out);
            writer.close();


        }catch (ConfigException exception){
            System.out.print(exception.getMessage());
            System.exit(1);
        }catch(IOException ioException){
            System.exit(1);
        }

        System.exit(0);

    }
}
