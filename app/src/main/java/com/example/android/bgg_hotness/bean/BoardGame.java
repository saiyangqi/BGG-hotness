package com.example.android.bgg_hotness.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by Saiyang Qi on 11/9/17.
 * A Board Game Bean Class using the Builder Pattern.
 */

public class BoardGame implements Parcelable {
    private int gameId;
    private int yearPublished;
    private int hotnessRank;
    private int minPlayers;
    private int maxPlayers;
    private int minPlayTime;
    private int maxPlayTime;
    private int minAge;
    private double geekRating;
    private double communityAvgRating;
    private int numOfRaters;
    private String gameName;
    private String thumbnailUrl;
    private String imageUrl;
    private String description;

    public BoardGame(Builder builder) {
        this.gameId = builder.gameId;
        this.yearPublished = builder.yearPublished;
        this.hotnessRank = builder.hotnessRank;
        this.minPlayers = builder.minPlayers;
        this.maxPlayers = builder.maxPlayers;
        this.minPlayTime = builder.minPlayTime;
        this.maxPlayTime = builder.maxPlayTime;
        this.minAge = builder.minAge;
        this.geekRating = builder.geekRating;
        this.communityAvgRating = builder.communityAvgRating;
        this.numOfRaters = builder.numOfRaters;
        this.gameName = builder.gameName;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
    }

    protected BoardGame(Parcel in) {
        gameId = in.readInt();
        yearPublished = in.readInt();
        hotnessRank = in.readInt();
        minPlayers = in.readInt();
        maxPlayers = in.readInt();
        minPlayTime = in.readInt();
        maxPlayTime = in.readInt();
        minAge = in.readInt();
        geekRating = in.readDouble();
        communityAvgRating = in.readDouble();
        numOfRaters = in.readInt();
        gameName = in.readString();
        thumbnailUrl = in.readString();
        imageUrl = in.readString();
        description = in.readString();
    }

    public static final Creator<BoardGame> CREATOR = new Creator<BoardGame>() {
        @Override
        public BoardGame createFromParcel(Parcel in) {
            return new BoardGame(in);
        }

        @Override
        public BoardGame[] newArray(int size) {
            return new BoardGame[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(gameId);
        parcel.writeInt(yearPublished);
        parcel.writeInt(hotnessRank);
        parcel.writeInt(minPlayers);
        parcel.writeInt(maxPlayers);
        parcel.writeInt(minPlayTime);
        parcel.writeInt(maxPlayTime);
        parcel.writeInt(minAge);
        parcel.writeDouble(geekRating);
        parcel.writeDouble(communityAvgRating);
        parcel.writeInt(numOfRaters);
        parcel.writeString(gameName);
        parcel.writeString(thumbnailUrl);
        parcel.writeString(imageUrl);
        parcel.writeString(description);
    }

    public static class Builder{
        private int gameId;
        private int yearPublished;
        private int hotnessRank;
        private int minPlayers;
        private int maxPlayers;
        private int minPlayTime;
        private int maxPlayTime;
        private int minAge;
        private double geekRating;
        private double communityAvgRating;
        private int numOfRaters;
        private String gameName;
        private String thumbnailUrl;
        private String imageUrl;
        private String description;

        public Builder gameId(int gameId) {
            this.gameId = gameId;
            return this;
        }
        public Builder yearPublished(int yearPublished) {
            this.yearPublished = yearPublished;
            return this;
        }
        public Builder hotnessRank(int hotnessRank) {
            this.hotnessRank = hotnessRank;
            return this;
        }
        public Builder minPlayers(int minPlayers) {
            this.minPlayers = minPlayers;
            return this;
        }
        public Builder maxPlayers(int maxPlayers) {
            this.maxPlayers = maxPlayers;
            return this;
        }
        public Builder minPlayTime(int minPlayTime) {
            this.minPlayTime = minPlayTime;
            return this;
        }
        public Builder maxPlayTime(int maxPlayTime) {
            this.maxPlayTime = maxPlayTime;
            return this;
        }
        public Builder minAge(int minAge) {
            this.minAge = minAge;
            return this;
        }
        public Builder geekRating(double geekRating) {
            this.geekRating = geekRating;
            return this;
        }
        public Builder communityAvgRating(double communityAvgRating) {
            this.communityAvgRating = communityAvgRating;
            return this;
        }
        public Builder numOfRaters(int numOfRaters) {
            this.numOfRaters = numOfRaters;
            return this;
        }
        public Builder gameName(String gameName) {
            this.gameName = gameName;
            return this;
        }
        public Builder thumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }
        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public BoardGame build() {
            return new BoardGame(this);
        }
    }

    public int getGameId() {
        return gameId;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public int getHotnessRank() {
        return hotnessRank;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayTime() {
        return minPlayTime;
    }

    public int getMaxPlayTime() {
        return maxPlayTime;
    }

    public int getMinAge() {
        return minAge;
    }

    public double getGeekRating() {
        return geekRating;
    }

    public double getCommunityAvgRating() {
        return communityAvgRating;
    }

    public int getNumOfRaters() {
        return numOfRaters;
    }

    public String getGameName() {
        return gameName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,
                "id: %d\nname: %s\nhotnessRank: %d\nurl: %s\nyear: %d\nplayerCount: %d-%d\n" +
                "playTime Range: %d-%d\nminAge: %d\ndescription: %s",
                gameId, gameName, hotnessRank, imageUrl, yearPublished, minPlayers, maxPlayers,
                minPlayTime, maxPlayTime, minAge, description + "...");
    }
}
