import com.example.JavaJokesCorey;

import org.junit.Test;


/**
 * Created by cmbar on 11/21/2015.
 */
public class JJokesCoreyTest {

    @Test
    public void test() {
        JavaJokesCorey joke = new JavaJokesCorey();
        assert joke.tellJavaJoke().length() != 0;
    }
}
