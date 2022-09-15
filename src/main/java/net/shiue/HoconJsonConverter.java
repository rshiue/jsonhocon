package net.shiue;

import com.typesafe.config.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * To convert a Json file to a Hocon file.
 * Or convert a Hocon file to a Json file.
 *
 * e.g.
 * ```
 * java -cp json-hocon.jar net.shiue.HoconJsonConverter HOCON /temp/fileA.json /temp/fileB.conf
 * ```
 * The destination file will be overwritten.
 */
public class HoconJsonConverter {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Invalid arguments. please pass desired format, and file paths( source and dest ) to proceed.");
            System.exit(1);
            return;
        }

        String desiredFormat = args[0];
        File source = new File(args[1]);
        String dest = args[2];

        if (!source.exists()) {
            System.out.println("The source file does not existed.");
            System.exit(1);
            return;
        }
        String out = "";

        if (desiredFormat.equalsIgnoreCase("hocon")) {
            System.out.println("starting to convert JSON to HOCON...");
            ConfigParseOptions options = ConfigParseOptions.defaults()
                    .setSyntax(ConfigSyntax.JSON)
                    .setAllowMissing(false);

            Config conf = ConfigFactory.parseFile(source, options);
            ConfigRenderOptions renderOptions = ConfigRenderOptions.concise().setJson(false).setFormatted(true);
            out = conf.root().render(renderOptions);
            System.out.println(out);

        } else if (desiredFormat.equalsIgnoreCase("json")) {
            System.out.println("starting to convert HOCON to JSON...");
            ConfigParseOptions options = ConfigParseOptions.defaults()
                    .setSyntax(ConfigSyntax.CONF)
                    .setAllowMissing(false);

            Config conf = ConfigFactory.parseFile(source, options);
            ConfigRenderOptions renderOptions = ConfigRenderOptions.concise().setJson(true).setFormatted(true).setComments(false);
            out = conf.root().render(renderOptions);
            System.out.println(out);
        } else {
            System.out.println("not supported desired format. Please specify `hocon` or `json` ");
            System.exit(1);
        }

        try (FileOutputStream fos = new FileOutputStream(dest);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)
        ) {

            for (String line : Collections.singletonList(out)) {
                writer.append(line);
                writer.newLine();
            }
        } catch (ConfigException exception) {
            System.out.print(exception.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println("error occurred when writing files.");
            System.exit(1);
        } catch (Exception error) {
            System.out.println("unknown error");
            System.exit(1);
            throw error;
        }


        System.exit(0);
    }
}
