package com.example.android.bgg_hotness.utils;

import android.util.Xml;

import com.example.android.bgg_hotness.bean.BoardGame;

import org.apache.commons.text.StringEscapeUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saiyang Qi on 11/9/17.
 * A helper class that takes in either the hotness rank xml file or a single game details xml file,
 * and parses the needed info to construct and return a list of BoardGame objects.
 */

public class XmlDataUtils {
    public static List<BoardGame> parseHotnessXmlData(String xmlData) throws XmlPullParserException, IOException {
        List<BoardGame> boardGameList = new ArrayList<>(50);
        if (xmlData == null) {
            return boardGameList;
        }
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new StringReader(xmlData));
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.getName().equals("item")) {
                BoardGame boardGame = parseSingleHotnessItem(parser);
                boardGameList.add(boardGame);
            }
            parser.next();
            eventType = parser.getEventType();
        }
        return boardGameList;
    }

    public static BoardGame parseSingleBoardGameData(String xmlData) throws XmlPullParserException, IOException {
        int gameId = 0;
        int yearPublished = 0;
        int minPlayers = 0;
        int maxPlayers = 0;
        int minPlayTime = 0;
        int maxPlayTime = 0;
        int minAge = 0;
        double geekRating = 0;
        double communityAvgRating = 0;
        int numOfRaters = 0;
        String gameName = null;
        String imageUrl = null;
        String description = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new StringReader(xmlData));
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                switch (parser.getName()) {
                    case "item":
                        gameId = Integer.parseInt(parser.getAttributeValue(null, "id"));
                        break;
                    case "image":
                        parser.next();
                        imageUrl = parser.getText();
                        break;
                    case "name":
                        if (parser.getAttributeValue(null, "type").equals("primary"))
                            gameName = parser.getAttributeValue(null, "value");
                        break;
                    case "description":
                        parser.next();
                        description = parser.getText();
                        description = StringEscapeUtils.unescapeXml(description);
                        description = StringEscapeUtils.unescapeHtml4(description);
                        break;
                    case "yearpublished":
                        yearPublished = Integer.parseInt(parser.getAttributeValue(0));
                        break;
                    case "minplayers":
                        minPlayers = Integer.parseInt(parser.getAttributeValue(0));
                        break;
                    case "maxplayers":
                        maxPlayers = Integer.parseInt(parser.getAttributeValue(0));
                        break;
                    case "minplaytime":
                        minPlayTime = Integer.parseInt(parser.getAttributeValue(0));
                        break;
                    case "maxplaytime":
                        maxPlayTime = Integer.parseInt(parser.getAttributeValue(0));
                        break;
                    case "minage":
                        minAge = Integer.parseInt(parser.getAttributeValue(0));
                        break;
                    case "bayesaverage":
                        geekRating = Double.parseDouble(parser.getAttributeValue(0));
                        break;
                    case "average":
                        communityAvgRating = Double.parseDouble(parser.getAttributeValue(0));
                        break;
                    case "usersrated":
                        numOfRaters = Integer.parseInt(parser.getAttributeValue(0));
                        break;
                    default:
                        break;
                }
            }
            parser.next();
            eventType = parser.getEventType();
        }
        BoardGame boardGame = new BoardGame.Builder()
                .gameId(gameId)
                .yearPublished(yearPublished)
                .minPlayers(minPlayers)
                .maxPlayers(maxPlayers)
                .minPlayTime(minPlayTime)
                .maxPlayTime(maxPlayTime)
                .minAge(minAge)
                .geekRating(geekRating)
                .communityAvgRating(communityAvgRating)
                .numOfRaters(numOfRaters)
                .gameName(gameName)
                .imageUrl(imageUrl)
                .description(description)
                .build();
        return boardGame;
    }

    private static BoardGame parseSingleHotnessItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        int gameId = 0;
        int hotnessRank = 0;
        int yearPublished = 0;
        String thumbnailUrl = null;
        String gameName = null;
        int eventType = parser.getEventType();
        String tagName = parser.getName();
        while (!(eventType == XmlPullParser.END_TAG && tagName.equals("item"))) {
            if (eventType == XmlPullParser.START_TAG) {
                switch (tagName) {
                    case "item":
                        gameId = Integer.parseInt(parser.getAttributeValue(null, "id"));
                        hotnessRank = Integer.parseInt(parser.getAttributeValue(null, "rank"));
                        break;
                    case "thumbnail":
                        thumbnailUrl = parser.getAttributeValue(0);
                        break;
                    case "name":
                        gameName = parser.getAttributeValue(0);
                        break;
                    case "yearpublished":
                        yearPublished = Integer.parseInt(parser.getAttributeValue(0));
                        break;
                    default:
                        break;
                }
            }
            parser.next();
            eventType = parser.getEventType();
            tagName = parser.getName();
        }
        BoardGame boardGame = new BoardGame.Builder()
                .gameId(gameId)
                .hotnessRank(hotnessRank)
                .thumbnailUrl(thumbnailUrl)
                .gameName(gameName)
                .yearPublished(yearPublished)
                .build();
        return boardGame;
    }
}
