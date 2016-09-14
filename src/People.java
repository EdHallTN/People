import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Reads a csv file and maps country name to a list of people who are from that country.
 * Then, for each list sorts by last name.
 */

public class People {

    public static HashMap<String, ArrayList<Person>> personMap = new HashMap<>();
    public static ArrayList<Person> personList = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        File f = new File("people.csv");
        Scanner fileScanner = new Scanner(f);
        String line;

        while (fileScanner.hasNext()) {
            line = fileScanner.nextLine();
            while (line.startsWith("id,first_name")) {
                line = fileScanner.nextLine();
            }
            String[] columns = line.split(",");
            Person person = new Person(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5]);
            String keyValue = person.getCountry();
            if (personMap.containsKey(keyValue)) {
                personList = personMap.get(keyValue);
                final boolean add = personList.add(person);
                Collections.sort(personList);
            } else {
                personList = new ArrayList<>();
                personList.add(person);
                personMap.put(keyValue, personList);
                Collections.sort(personList);
            }
        }

        System.out.println(personMap);
        writeFile("People.json", personMap.toString());
    }

    static void writeFile(String fileName, String fileContent) throws IOException {
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.include(String.valueOf(personMap)).serialize(fileContent);
        File People = new File(fileName);
        FileWriter fw = new FileWriter(People);
        fw.write(json);
        fw.close();
    }

}
