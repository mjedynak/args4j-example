package pl.mjedynak;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.PrintStream;

public class App {

    private static Configuration configuration = new Configuration();
    private static FileCompressor fileCompressor = new FileCompressor();
    private static CmdLineParser parser = new CmdLineParser(configuration);
    private static PrintStream outputStream = System.out;

    public static void main(String[] args) {
        try {
            parser.parseArgument(args);
            fileCompressor.compressFile(configuration);
        } catch (CmdLineException e) {
            outputStream.println(e.getMessage());
            parser.printUsage(outputStream);
        }
    }
}
