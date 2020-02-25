package unitTests;

import bowling.helpers.FileReaderHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;


@RunWith(JUnit4.class)
public class FileReaderHelperTest {

    FileReaderHelper readerHelper;

    @Before
    public void setup(){
        readerHelper = new FileReaderHelper();
    }

    @Test(expected = IOException.class)
    public void testInvalidArguments() throws Exception {
        String[] args = new String[0];
        readerHelper.readFromInputStream(args);

    }
}
