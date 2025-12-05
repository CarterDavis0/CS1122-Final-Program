import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TrackRecommender {

    /**
     * Utility that reads the data for the track recommender.
     */
    private ReadData data = null;
    private int mapSize = 0;

    public TrackRecommender(String filename) {
        // Initialize ReadData
        data = new ReadData();
        data.readInput(filename);
        mapSize = data.getUserTrackMap().size();
    }

    /**
     * Calculates the similarity score between two users based on a field
     * and method. This method defaults to using genre and the Pearson
     * similarity algorithm.
     *
     * @param user1     The first user
     * @param user2     The second user
     * @param fieldName The field to base the similarity off of
     * @param method    The method to calculate similarity
     */
    public double calculateSimilarity(String user1, String user2,
            String fieldName, String method) {

        double distance = -1;

        // Index of a particular element in one of the unique element lists
        int index = 0;

        fieldName = fieldName.toUpperCase();
        method = method.toUpperCase();

        ArrayList<String> uniqueTitles = uniqueTitles();
        ArrayList<String> uniqueArtists = uniqueArtists();
        ArrayList<String> uniqueGenres = uniqueGenres();

        // The arrays that will be compared for similarity
        ArrayList<Double> array1 = new ArrayList<>();
        ArrayList<Double> array2 = new ArrayList<>();

        // Ensure that an index will never be out of bounds
        for (int i = 0; i < mapSize * 10; i++) {
            array1.add(i, 0.0);
            array2.add(i, 0.0);
        }

        // Input arrays
        ArrayList<TrackInfo> tracks1 = data.getUserTrackMap().get(user1);
        ArrayList<TrackInfo> tracks2 = data.getUserTrackMap().get(user2);

        // Decide which field is being used
        // Title
        if (fieldName.equals("TITLE")) {
            for (int i = 0; i < tracks1.size(); i++) {
                // Locate the element
                index = uniqueTitles.indexOf(tracks1.get(i).getTitle());
                // Increment the value at the corresponding index (will be
                // weighted more heavily if the same genre or artist appears
                // multiple times)
                array1.set(index, array1.get(index) + 1);
            }
            for (int i = 0; i < tracks2.size(); i++) {
                // Locate the element
                index = uniqueTitles.indexOf(tracks2.get(i).getTitle());
                // Increment the value at the corresponding index (will be
                // weighted more heavily if the same genre or artist appears
                // multiple times)
                array2.set(index, array2.get(index) + 1);
            }
            if (method.equals("EUCLIDEAN")) {
                distance = euclideanDistance(array1, array2);
            } else {
                distance = pearsonDistance(array1, array2);
            }

            // Artist
        } else if (fieldName.equals("ARTIST")) {
            for (int i = 0; i < tracks1.size(); i++) {
                // Locate the element
                index = uniqueArtists.indexOf(tracks1.get(i).getArtist());
                // Increment the value at the corresponding index (will be
                // weighted more heavily if the same genre or artist appears
                // multiple times)
                array1.set(index, array1.get(index) + 1);
            }
            for (int i = 0; i < tracks2.size(); i++) {
                // Locate the element
                index = uniqueArtists.indexOf(tracks2.get(i).getArtist());
                // Increment the value at the corresponding index (will be
                // weighted more heavily if the same genre or artist appears
                // multiple times)
                array2.set(index, array2.get(index) + 1);
            }
            if (method.equals("EUCLIDEAN")) {
                distance = euclideanDistance(array1, array2);
            } else {
                distance = pearsonDistance(array1, array2);
            }

            // Genre
        } else {
            for (int i = 0; i < tracks1.size(); i++) {
                // Locate the element
                index = uniqueGenres.indexOf(tracks1.get(i).getGenre());
                // Increment the value at the corresponding index (will be
                // weighted more heavily if the same genre or artist appears
                // multiple times)
                array1.set(index, array1.get(index) + 1);
            }
            for (int i = 0; i < tracks2.size(); i++) {
                // Locate the element
                index = uniqueGenres.indexOf(tracks2.get(i).getGenre());
                // Increment the value at the corresponding index (will be
                // weighted more heavily if the same genre or artist appears
                // multiple times)
                array2.set(index, array2.get(index) + 1);
            }
            if (method.equals("EUCLIDEAN")) {
                distance = euclideanDistance(array1, array2);
            } else {
                distance = pearsonDistance(array1, array2);
            }
        }

        return distance;
    }

    /**
     * This helper function creates an ArrayList of unique titles using the
     * instance HashMap.
     * 
     * @return An ArrayList with only unique song titles.
     */
    private ArrayList<String> uniqueTitles() {
        ArrayList<String> uniqueTitles = new ArrayList<>();
        String currentTitle;
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < data.getUserTrackMap().get(data.getUsernames().get(i)).size(); j++) {

                // Set title for this iteration
                currentTitle = data.getUserTrackMap().get(data.getUsernames().get(i)).get(j).getTitle();

                // If the title isn't already present in the list
                if (!uniqueTitles.contains(currentTitle)) {
                    uniqueTitles.add(currentTitle);
                }
            }
        }
        return uniqueTitles;
    }

    /**
     * This helper function creates an ArrayList of unique artists using the
     * instance HashMap.
     * 
     * @return An ArrayList with only unique artists.
     */
    private ArrayList<String> uniqueArtists() {
        ArrayList<String> uniqueArtists = new ArrayList<>();
        String currentArtist;
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < data.getUserTrackMap().get(data.getUsernames().get(i)).size(); j++) {

                // Set artist for this iteration
                currentArtist = data.getUserTrackMap().get(data.getUsernames().get(i)).get(j).getArtist();

                // If the artist isn't already present in the list
                if (!uniqueArtists.contains(currentArtist)) {
                    uniqueArtists.add(currentArtist);
                }
            }
        }
        return uniqueArtists;
    }

    /**
     * This helper function creates an ArrayList of unique genres using the
     * instance HashMap.
     * 
     * @return An ArrayList with only unique genres.
     */
    private ArrayList<String> uniqueGenres() {
        ArrayList<String> uniqueGenres = new ArrayList<>();
        String currentGenre;
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < data.getUserTrackMap().get(data.getUsernames().get(i)).size(); j++) {

                // Set genre for this iteration
                currentGenre = data.getUserTrackMap().get(data.getUsernames().get(i)).get(j).getGenre();

                // If the genre isn't already present in the list
                if (!uniqueGenres.contains(currentGenre)) {
                    uniqueGenres.add(currentGenre);
                }
            }
        }
        return uniqueGenres;
    }

    /**
     * Calculates all similarity scores between one user and all other users
     * based on a field and method.
     *
     * @param user      The user
     * @param fieldName The field to base the similarity off of
     * @param method    The method to calculate similarity
     */
    public HashMap<String, Double> calculateAllSimilarity(String user,
            String fieldName,
            String method) {

        HashMap<String, Double> similarity = new HashMap<>();
        String target;

        for (int i = 0; i < mapSize; i++) {
            target = data.getUsernames().get(i);
            // If the target and the user are not the same, add their score to
            // the HashMap
            if (!target.equals(user)) {
                similarity.put(target, calculateSimilarity(user, target,
                        fieldName, method));
            }
        }

        return similarity;
    }

    /**
     * Generates a playlist based on a user of a specified number of tracks.
     *
     * @param user           The user
     * @param fieldName      The field to base the similarity off of
     * @param method         The method to calculate similarity
     * @param numberoftracks The total number of tracks
     */
    public ArrayList<TrackInfo> makePlaylist(String user, String fieldName,
            String method,
            int numberOfTracks) {

        ArrayList<TrackInfo> playList = new ArrayList<>();

        HashMap<String, Double> similarityMap = calculateAllSimilarity(user,
                fieldName, method);

        // Copy elements to new list to avoid pointer issues
        ArrayList<String> targets = new ArrayList<>();
        for (int i = 0; i < data.getUsernames().size(); i++) {
            targets.add(data.getUsernames().get(i));
        }

        double max = 0; // Max similarity value
        double currentSimiliarity = 0;
        int tracksAdded = 0;
        int size = mapSize; // Number of targets
        String similarUser = null;

        while (tracksAdded < numberOfTracks) {
            for (int i = 0; i < size; i++) {
                if (!user.equals(targets.get(i))) {
                    // Sets the current similarity value to compare
                    currentSimiliarity = similarityMap.get(targets.get(i));
                    if (max < currentSimiliarity) {
                        max = currentSimiliarity;
                        similarUser = targets.get(i);
                    }
                }
            }

            // Add tracks to playlist
            try {
                for (int i = 0; i < data.getUserTrackMap().get(similarUser).size
                (); i++) {
                    playList.add(data.getUserTrackMap().get(similarUser).get
                    (i));
                    tracksAdded++;
                    if (tracksAdded == numberOfTracks) {
                        break;
                    }
                }
                // If numberOfTracks exceeds the total number of tracks, break 
                // out of the loop once all tracks have been added. This will 
                // be every track not submitted by the user.
            } catch (NullPointerException e) {
                break;
            }

            // No duplicates
            similarityMap.remove(similarUser);
            targets.remove(similarUser);

            size--; // Removed one user
            currentSimiliarity = 0; // Reset max value
            max = 0;
            similarUser = null;
        }

        return playList;
    }

    /**
     * Calculates the likelihood that two users share the same music tastes
     * using the Euclidean Distance algorithm.
     * 
     * @param array1
     * @param array2
     * @return a similarity score
     */
    public double euclideanDistance(ArrayList<Double> array1,
            ArrayList<Double> array2) {
        double sum = 0.0;
        for (int i = 0; i < array1.size(); i++) {
            sum += Math.pow((array1.get(i) - array2.get(i)), 2.0);
        }
        return 1.0 / (1.0 + Math.sqrt(sum));
    }

    /**
     * Calculates the likelihood that two users share the same music tastes
     * using the Pearson Distance algorithm.
     * 
     * @param array1
     * @param array2
     * @return a similarity score
     */
    public double pearsonDistance(ArrayList<Double> array1,
            ArrayList<Double> array2) {
        double mean1 = 0.0, mean2 = 0.0;
        for (int i = 0; i < array1.size(); i++) {
            mean1 += array1.get(i);
            mean2 += array2.get(i);
        }
        mean1 /= array1.size();
        mean2 /= array2.size();
        double sumXY = 0.0, sumX2 = 0.0, sumY2 = 0.0;
        for (int i = 0; i < array1.size(); i++) {
            sumXY += ((array1.get(i) - mean1) * (array2.get(i) - mean2));
            sumX2 += Math.pow(array1.get(i) - mean1, 2.0);
            sumY2 += Math.pow(array2.get(i) - mean2, 2.0);
        }
        return (1.0 + (sumXY / (Math.sqrt(sumX2) * Math.sqrt(sumY2)))) / 2.0;
    }

    public static void main(String[] args) {
        String file = "cs1122-2025.csv";
        TrackRecommender rec = new TrackRecommender(file);
        // TEST YOUR CODE HERE

        rec.data.readInput("cs1122-2025.csv");

        // Extra Credit: Command-Line UI
        Scanner input = new Scanner(System.in);
        boolean search = true;
        String user = "";
        String field = "";
        String method = "";
        int number = 0;

        System.out.print("To generate a playlist, enter a user ID. For a" 
        + " random ID, enter R: ");
        while (search) {
            user = input.next();
            if (rec.data.getUsernames().contains(user) || 
            user.equals("R")) {
                search = false;
            } else {
                System.out.print("\nUser ID not found. Please try again: ");
            }
        }

        // If the user wants to generate a random user ID
        int usersLength = rec.data.getUsernames().size();
        if (user.equals("R")) {
            user = rec.data.getUsernames().get((int)(Math.random() * 
            usersLength));
        }
        search = true;

        // Get field input
        System.out.println("\nSelect the field you would like to generate "
                + "the playlist based on: ");
        System.out.println("Song Title: Enter A");
        System.out.println("Artist: Enter B");
        System.out.println("Genre, Enter C");

        while (search) {
            field = input.next();
            if (field.toUpperCase().equals("A") || field.toUpperCase().equals("B") || field.equals("C")) {
                search = false;
            } else {
                System.out.print(field + " is not a valid selection. Please "
                        + "choose A, B, or C: ");
            }
        }
        search = true;

        // Figure out which field type to use
        if (field.equals("A")) {
            field = "title";
        } else if (field.equals("B")) {
            field = "artist";
        } else {
            field = "genre";
        }

        // Get method input
        System.out.println("\nSelect the method you would like to use to "
                + "generate the playlist: ");
        System.out.println("Euclidean: Enter A");
        System.out.println("Pearson: Enter B");

        while (search) {
            method = input.next();
            if (method.toUpperCase().equals("A") || method.toUpperCase().equals("B")) {
                search = false;
            } else {
                System.out.print(method + " is not a valid selection. Please "
                        + "choose Euclidean or Pearson: ");
            }
        }
        search = true;

        // Get number of tracks
        System.out.print("\nEnter the number of tracks that should be on the" +
                " playlist: ");

        while (search) {
            try {
                number = input.nextInt();
                search = false;
            } catch (InputMismatchException e) {
                System.out.print("The value entered is not an integer. "
                        + "Please try again: ");
                input.next();
            }
        }
        search = true;

        System.out.println("\nGenerating playlist...");

        ArrayList<TrackInfo> playList = rec.makePlaylist(user, field, method,
                number);

        System.out.println("\nYour playlist:");

        for (int i = 0; i < playList.size(); i++) {
            System.out.println((i + 1) + ". " + playList.get(i).getTitle() 
            + " by " + playList.get(i).getArtist());
        }

        input.close();

    }
}
