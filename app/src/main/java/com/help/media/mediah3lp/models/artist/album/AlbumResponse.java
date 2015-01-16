package com.help.media.mediah3lp.models.artist.album;

import com.google.gson.annotations.SerializedName;
import com.help.media.mediah3lp.models.artist.albums.Album;
import com.help.media.mediah3lp.models.artist.albums.TopAlbums;

/**
 * Created by alexey.bukin on 16.01.2015.
 */
public class AlbumResponse {
    @SerializedName("album")
    private com.help.media.mediah3lp.models.artist.album.Album mAlbum = new com.help.media.mediah3lp.models.artist.album.Album();

    public com.help.media.mediah3lp.models.artist.album.Album getAlbum() {
        return mAlbum;
    }
}
