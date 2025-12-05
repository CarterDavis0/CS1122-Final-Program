public class TrackInfo {
    /**
     * String user: The users id.
     */
    private String user = "";

    /**
     * String title: The title of the track.
     */
    private String title = "";

    /**
     * String artist: The artist's name.
     */
    private String artist = "";

    /**
     * String album: The name of the album in which the track exists.
     */
    private String album = "";

    /**
     * String genre: The genre of the track.
     */
    private String genre = "";

    /**
     * int rank: The rank for this track by the user.
     */
    private int rank = 0;

    /**
     * int plays: The number of times the user has played this track.
     */
    private int plays = 0;

    public TrackInfo(String user, String title, String artist, String album,
            String genre, int rank, int plays) {
        setUser(user);
        setTitle(title);
        setArtist(artist);
        setAlbum(album);
        setGenre(genre);
        setRank(rank);
        setPlays(plays);
    }

    public String getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public int getRank() {
        return rank;
    }

    public int getPlays() {
        return plays;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }
}
