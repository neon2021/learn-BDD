package unittest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * TODO: function description
 *
 * @author neon2021 on 2022/5/3
 */
public class StreamsTest {
    @Test
    public void test() {
        long howMany = 0;
        List<Integer> newList = new ArrayList<>();
        howMany = Stream.of(1, 2, 3, 4).peek(e -> newList.add(e)).count();
        System.out.println("howMany=" + howMany);
    }
}
