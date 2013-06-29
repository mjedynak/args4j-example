package pl.mjedynak;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kohsuke.args4j.CmdLineParser;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {
    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_FILE = "output.txt";
    private static final String INVALID_ARGUMENT = "-www";

    @Mock
    private FileCompressor fileCompressor;

    private App app = new App();

    @Before
    public void setUp() {
        setField(app, "fileCompressor", fileCompressor);
    }

    @Test
    public void shouldPopulateConfiguration() {
        // when
        app.main((String[]) asList("-i", INPUT_FILE, "-o", OUTPUT_FILE).toArray());

        // then
        Configuration configuration = (Configuration) getField(app, "configuration");
        assertThat(configuration.getInputFileName(), is(INPUT_FILE));
        assertThat(configuration.getOutputFileName(), is(OUTPUT_FILE));
    }

    @Test
    public void shouldInvokeFileCompressor() {
        // when
        app.main((String[]) asList("-i", INPUT_FILE, "-o", OUTPUT_FILE).toArray());

        // then
        verify(fileCompressor).compressFile(isA(Configuration.class));
    }
    
    @Test
    public void shouldPrintUsageWhenInvalidArguments() {
        // given
        Configuration configuration = (Configuration) getField(app, "configuration");
        CmdLineParser parser = spy(new CmdLineParser(configuration));
        setField(app, "parser", parser);
        String[] invalidArguments = (String[]) asList(INVALID_ARGUMENT, INPUT_FILE).toArray();

        // when
        app.main(invalidArguments);
        
        // then
        verify(parser).printUsage(isA(PrintStream.class));
    }

    @Test
    public void shouldPrintExceptionOnOutputStreamWhenInvalidArguments() {
        // given
        PrintStream outputStream = spy(System.out);
        setField(app, "outputStream", outputStream);
        String[] invalidArguments = (String[]) asList(INVALID_ARGUMENT, INPUT_FILE).toArray();

        // when
        app.main(invalidArguments);

        // then
        verify(outputStream).println("\"" + INVALID_ARGUMENT + "\" is not a valid option");
    }
}
