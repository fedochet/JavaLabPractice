import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by roman on 23.08.2016.
 */
public class LexicalParser {

    private String prepareString(String s) {
        return s.trim().replaceAll(" ", "");
    }

    public List<String> parse(String equation) {
        String preparedString = prepareString(equation);

        List<String> result = new ArrayList<>();

        int position = 0;
        while (position!=preparedString.length()) {
            if (Character.isDigit(preparedString.charAt(position))) { // number
                int start = position;
                while (position!=preparedString.length() && (Character.isDigit(preparedString.charAt(position)) || preparedString.charAt(position) == '.')) {
                    position++;
                }

                result.add(preparedString.substring(start, position));
            } else { // operation or sign
                result.add(preparedString.substring(position, position+1));
                position++;
            }
        }

        return result;
    }
}
