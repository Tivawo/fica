package Data;

import java.util.ArrayList;
import java.util.HashMap;

public class Genres {

    public String getGenre(int[] ints) {
        ArrayList<String> genreList = new ArrayList<>();

        for (int i : ints
        ) {
            switch (i) {
                case 28:
                    genreList.add("Action");
                    break;
                case 12:
                    genreList.add("Adventure");
                    break;
                case 16:
                    genreList.add("Animation");
                    break;
                case 35:
                    genreList.add("Comedy");
                    break;
                case 80:
                    genreList.add("Crime");
                    break;
                case 99:
                    genreList.add("Documentary");
                    break;
                case 18:
                    genreList.add("Drama");
                    break;
                case 10751:
                    genreList.add("Family");
                    break;
                case 14:
                    genreList.add("Fantasy");
                    break;
                case 36:
                    genreList.add("History");
                    break;
                case 27:
                    genreList.add("Horror");
                    break;
                case 10402:
                    genreList.add("Music");
                    break;
                case 9648:
                    genreList.add("Mystery");
                    break;
                case 10749:
                    genreList.add("Romance");
                    break;
                case 878:
                    genreList.add("Science Fiction");
                    break;
                case 10770:
                    genreList.add("TV Movie");
                    break;
                case 53:
                    genreList.add("Thriller");
                    break;
                case 10752:
                    genreList.add("War");
                    break;
                case 37:
                    genreList.add("Western");
                    break;

            }

        }
        return genreList.toString();
    }


}
