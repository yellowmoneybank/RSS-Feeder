package com.projektarbeit.rss_feeder.control;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class Parser {

    private String feedXML;
    private ArrayList<Feed> items;

    public Parser(String feedXML) {
        this.feedXML = feedXML;
        try {
            items = extractItems(feedXML);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Feed> extractItems(String feedXML) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(feedXML);
        doc.getDocumentElement().normalize();

        NodeList itemList = doc.getElementsByTagName("item");

        ArrayList<Feed> itemsArrayList = new ArrayList<Feed>();
        for (int i = 0; i < itemList.getLength(); i++) {
            Node node = itemList.item(i);
            Feed feed = generateFeedFromNode(node);
            itemsArrayList.add(feed);

        }


        return itemsArrayList;
    }

    private Feed generateFeedFromNode(Node node) {
        Feed feed = null;
        NodeList childs = node.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            switch (childs.item(i).getNodeName()){

                // TODO Hier wird der Feed erstellt mit switch

            }
        }
        return feed;
    }


    public ArrayList<Feed> getItems() {
        return items;
    }
}
