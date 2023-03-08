package org.hsbc.ProbabilisticRandomGen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    List<List<String>> Names = Arrays.asList(Arrays.asList("Saket", "Trevor"), Arrays.asList("John", "Michael"),
            Arrays.asList("Shawn", "Franklin"), Arrays.asList("Johnty", "Sean"));

        /* Created a “List of List of type String” i.e. List&lt;List&lt;String&gt;&gt;
           Stored names into the list
         */

    List<String> Start = Names.stream().flatMap(FirstName -> FirstName.stream()).filter(s -> s.startsWith("S"))
            .collect(Collectors.toList());

        /* Converted it into Stream and filtered
            out the names which start with 'S'
         */

      // System.out.println(Start);

}
