import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.awt.image.ImageWatched;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = null;
        try
        {
            fileReader = new FileReader("Web Developer Test.json");
            JSONArray contacts = (JSONArray) jsonParser.parse(fileReader);

            JSONArray sortedContacts = new JSONArray();
            List<JSONObject> tempList = new ArrayList<>();

            JSONArray citiesAndValidation = new JSONArray();

            for (Object each:contacts)
            {
                tempList.add((JSONObject) each);
            }

            Collections.sort(tempList, new Comparator<JSONObject>()
            {
                @Override
                public int compare(JSONObject o1, JSONObject o2)
                {
                    String str1 = (String) o1.get("fullName");
                    String str2 = (String) o2.get("fullName");

                    return str1.compareTo(str2);
                }
            });

            sortedContacts.addAll(tempList);
            int count = 0;

            //step 1
            for (Object each : sortedContacts)
            {
                JSONObject contact = (JSONObject) each;

                String fullName = (String) contact.get("fullName");
                String cityName = (String) contact.get("cityName");
                String phoneNumber = (String) contact.get("phoneNumber");
                String emailAddress = (String) contact.get("emailAddress");
                boolean phoneNumberValidation = isPhoneNumberValid(phoneNumber);
                boolean emailAddressValidation = isEmailAddressValid(emailAddress);


                System.out.println(fullName);

                if (phoneNumberValidation && emailAddressValidation)
                {
                    System.out.println("Valid");
                } else if (phoneNumberValidation || emailAddressValidation)
                {
                    if (phoneNumberValidation)
                    {
                        System.out.println("Email is invalid.");
                        count=1;
                    } else
                    {
                        System.out.println("Phone is invalid.");
                        count=1;
                    }
                } else
                {
                    System.out.println("Email and Phone are invalid.");
                    count = 2;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("city", count);
            }

        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }



    }

    public static boolean isPhoneNumberValid(String phoneNumber)
    {
        phoneNumber = phoneNumber.replaceAll(" ","");
        phoneNumber = phoneNumber.replaceAll("-", "").trim();

        return phoneNumber.length() == 10;
    }

    public static boolean isEmailAddressValid(String emailAddress)
    {
        return emailAddress.endsWith("@example.com");
    }
}
