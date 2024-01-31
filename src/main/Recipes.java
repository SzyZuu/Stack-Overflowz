package main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Recipes {

    int currentlyUsedCardIds = 9;
    int calcCardIds = currentlyUsedCardIds +1;                  // +1, cos otherwise it'll break
    int[][] recipe = new int[calcCardIds][calcCardIds];         //for now always set to the highest ID

    public void readRecipes() {
        for(int i = 0; i < calcCardIds; i++){                    //default to zero
            for(int a = 0; a < calcCardIds; a++){
                recipe[i][a] = 0;
            }
        }
        JSONParser jsonParser = new JSONParser();               //initialize parser

        String dir = Paths.get("res", "crafting").toString();

        try{
            List<Path> paths = Files.walk(Paths.get(dir),1) //by mentioning max depth as 1 it will only traverse immediate level
                    .filter(Files::isRegularFile)
                    .filter(path-> path.getFileName().toString().endsWith(".json")) // fetch only the files which are ending with .JSON
                    .collect(Collectors.toList());
            for(Path path : paths) {
                System.out.println(path);           //temp debug
                try {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(path.toString()));    //read files
                    System.out.println(jsonObject.get("firstID"));                               //temp debug
                    recipe[Integer.parseInt(jsonObject.get("firstID").toString())][Integer.parseInt(jsonObject.get("secondID").toString())] = Integer.parseInt(jsonObject.get("resultID").toString());
                } catch (ParseException | IOException e){
                    e.printStackTrace();                                             //error handling ig
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
