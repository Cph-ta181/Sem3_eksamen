package Fetcher;
import com.google.gson.Gson;
import java.io.IOException;

import com.google.gson.GsonBuilder;


public class Fetcher {
    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

    }
}
