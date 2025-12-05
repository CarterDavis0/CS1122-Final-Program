import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadData {

    private HashMap<String, ArrayList<TrackInfo>> userTrackMap;
    private ArrayList<String> usernames;

    public HashMap<String, ArrayList<TrackInfo>> getUserTrackMap() {
        return userTrackMap;
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public void setUserTrackMap(HashMap<String, ArrayList<TrackInfo>> value) {
        this.userTrackMap = value;
    }

    /**
     * This method adds a track to a Java Collections HashMap that associates
     * users to a list of tracks.
     *
     * @param map    A HashMap to insert a new track into
     * @param user   A string user name
     * @param rank   The rank of the track for this user
     * @param title  The title of the track
     * @param artist The artist's name who made the track
     * @param album  The album number for the track
     * @param genre  The genre of the track
     * @param plays  The number of times the user played the track
     */
    public void addTrack(HashMap<String, ArrayList<TrackInfo>> map, String user,
            int rank, String title, String artist, String album,
            String genre, int plays) {

        // If the user has no saved tracks, create a new ArrayList to store
        // their tracks; else, add to the existing ArrayList
        if (map.get(user) == null) {
            // Create new ArrayList and add the argument track
            ArrayList<TrackInfo> track = new ArrayList<>();
            track.add(new TrackInfo(user, title, artist, album, genre, rank, 
                plays));

            // Adds a TrackInfo object to the argument map
            map.put(user, track);
        } else {
            map.get(user).add(new TrackInfo(user, title, artist, album, genre, 
                rank, plays));
        }

    }

    /**
     * Given a filename read in all tracks from the file into the ~userTrackMap~
     * HashMap. Use the ~addTrack~ method above.
     *
     * @param filename The file name to read data from.
     */
    public void readInput(String filename) {

        userTrackMap = new HashMap<>();
        usernames = new ArrayList<>();

        String user;
        int rank;
        String title;
        String artist;
        String album;
        String genre;
        int plays;

        String line;
        String delimiter = ";";

        try (BufferedReader fileReader = new BufferedReader(new FileReader
            (filename))) {

            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                // Split the line by the delimiter
                String[] elements = line.split(delimiter);

                // Set each variable equal to the corresponding index within
                // the line of the CSV file
                user = elements[0];
                rank = Integer.parseInt(elements[1]);
                title = elements[2];
                artist = elements[3];

                // Column 6 is lowercase, so must look at column 7
                genre = elements[5];
                album = elements[6];
                plays = Integer.parseInt(elements[7]);

                // Add the username to the list of usernames, but avoid
                // duplicates
                if (!usernames.contains(user)) {
                    usernames.add(user);
                }

                // Add the track to the TrackMap
                addTrack(userTrackMap, user, rank, title, artist, album, genre, 
                    plays);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Generate a list of all artists associated with a given genre in
     * ~userTrackMap~
     *
     * @param genre The genre to generate a list for
     */
    public ArrayList<String> listGenreArtists(String genre) {

        // Avoid issues related to differences in formatting
        genre = genre.toUpperCase();

        // Create new ArrayList to store data
        ArrayList<String> artists = new ArrayList<>();
        String currentArtist;
        String currentGenre;

        for (int i = 0; i < userTrackMap.size(); i++) {
            for (int j = 0; j < userTrackMap.get(usernames.get(i)).size(); j++) 
                {
                // Set genre and artist for this iteration
                currentGenre = userTrackMap.get(usernames.get(i)).get(j).
                getGenre();
                currentArtist = userTrackMap.get(usernames.get(i)).get(j).
                getArtist();

                // If the genre matches the one you're looking for
                if (currentGenre.equals(genre)) {
                    // If the list doesn't already contain this artist
                    if (!artists.contains(currentArtist)) {
                        artists.add(currentArtist);
                    }
                }
            }
        }

        return artists;
    }

    public static void main(String[] args) {
        ReadData rd = new ReadData();
        // Do your testing here!

        rd.readInput("cs1122-2025.csv");
        System.out.println("Method completed. Map size = " + rd.userTrackMap.
        size());

        System.out.println("Album: " + rd.userTrackMap.get("EzI6psdcyE").
        get(0).getAlbum());

        System.out.println("Array size: " + rd.usernames.size());

        System.out.println(rd.listGenreArtists("metal"));

    }
}
