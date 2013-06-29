package pl.mjedynak;

import org.kohsuke.args4j.Option;

public class Configuration {
    private enum Priority {
        HIGH, LOW;
    }

    @Option(name = "-i", usage = "input file name", required = true)
    private String inputFileName;

    @Option(name = "-o", usage = "output file name", required = true)
    private String outputFileName;

    @Option(name = "-p", usage = "process priority")
    private Priority priority = Priority.HIGH;

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "inputFileName='" + inputFileName + '\'' +
                ", outputFileName='" + outputFileName + '\'' +
                ", priority=" + priority +
                '}';
    }
}
