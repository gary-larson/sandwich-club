package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to deal with JSON
 */
public class JsonUtils {

    /**
     * Method to convert JSON text into Sandwich class format
     * @param json text to be converted
     * @return converted Sandwich class object
     */
    public static Sandwich parseSandwichJson(String json) {
        // Declare and initialize Sandwich object
        Sandwich sandwich = new Sandwich();

        // Extract name JSON text from original JSON text
        String name = extractJson(json, "name");

        // populate Sandwich object from JSON objects
        sandwich.setMainName(extractString(name, "mainName"));
        sandwich.setAlsoKnownAs(extractList(name, "alsoKnownAs"));
        sandwich.setPlaceOfOrigin(extractString(json, "placeOfOrigin"));
        sandwich.setDescription(extractString(json, "description"));
        sandwich.setImage(extractString(json, "image"));
        sandwich.setIngredients(extractList(json, "ingredients"));

        return sandwich;
    }

    /**
     * Method to remove String value of item in question from JSON text
     * @param json text with information
     * @param target to retrieve value for
     * @return value
     */
    private static String extractString (String json, String target) {
        // Declare and intialize variable and objects
        String result = "";
        int position, start, end;
        String temp = "";

        // Get position of target
        position = json.indexOf(target);

        // If target exists continue
        if (position > 0) {

            // Determine bounds of target's value
            start = json.indexOf(':', position) + 2;
            end = json.indexOf('\"', start);

            // ignore \" combinations in JSON text
            while (end < json.length() && end > 0 && json.charAt(end - 1) == '\\') {
                end = json.indexOf('\"', end + 1);
            }

            // if value exists and is not empty continue
            if (end > start) {
                if (!(start < 2 || start > json.length() || end > json.length())) {

                    // extract value
                    result = json.substring(start, end);

                    // remove backslash from \"
                    result = result.replace("\\\"", "\"");
                }
            }
        }

        return result;
    }

    /**
     * Method to remove a list of strings values from JSON text
     * @param json text with information
     * @param target to retrieve values for
     * @return a list of strings
     */
    private static List<String> extractList (String json, String target) {
        // Declare and initializ variables and objects
        List<String> result = null;
        int position, start, end;
        String temp;

        // Get position of target
        position = json.indexOf(target);

        // If target exists continue
        if (position > 0) {

            // Determine bounds of target's value
            start = json.indexOf('[', position) + 1;
            end = json.indexOf(']', position);

            // if value exists and is not empty continue
            if (start != end) {
                if (!(start < 1 || start > json.length() || end < 0 || end > json.length())) {

                    // initialize result
                    result = new ArrayList<>();

                    // Get all values
                    temp = json.substring(start, end);

                    // seperate values into an array of Strings
                    String[] array = temp.split(",");

                    // loop through values and remove quotes
                    for (String item : array) {
                        temp = item.substring(1, item.length() - 1);

                        // Add values to list
                        result.add(temp);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Method to extract JSON text from JSON text
     * @param json text with information
     * @param target to retrieve JSON text
     * @return JSON text of target
     */
    private static String extractJson (String json, String target) {

        // Declare and initialize variables and objects
        int position, start, end;
        String result = "";

        // Get position of target
        position = json.indexOf(target);

        // If target exists continue
        if (position > 0) {
            // Determine bounds of target's JSON text
            start = json.indexOf('{', position) + 1;
            end = json.indexOf('}', position);

            // if value exists continue
            if (!(start < 1 || start > json.length() || end < 0 || end > json.length())) {

                //extract Value
                result = json.substring(start, end);
            }
        }

        return result;
    }
}
