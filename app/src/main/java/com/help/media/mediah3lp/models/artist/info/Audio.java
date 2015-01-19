package com.help.media.mediah3lp.models.artist.info;

import java.util.Comparator;

/**
 * Created by alexey.bukin on 17.12.2014.
 */
public class Audio {

        private final String artist;
//        private int genre = 0;

        public Audio(String artist) {
            this.artist = artist;
//            this.genre = genre;
        }

        public String getArtist() {
            return artist;
        }

//        public int getGenre() {
//            return genre;
//        }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Audio)) return false;

        Audio audio = (Audio) o;

        if (artist != null ? !artist.equals(audio.artist) : audio.artist != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return artist != null ? artist.hashCode() : 0;
    }

    public static class AudioComparator implements Comparator<Audio>{

        @Override
        public int  compare(Audio lhs, Audio rhs) {
            String stringL = lhs.getArtist() == null ? "" : lhs.getArtist().trim();
            String stringR = rhs.getArtist() == null ? "" : rhs.getArtist().trim();
            return stringL.compareTo(stringR);
        }
    }
}
