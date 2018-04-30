package com.genre.base.rss.impl

import com.genre.base.rss.RssMlbFeed
import org.springframework.stereotype.Component
import java.net.URL;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Component
class RssMlbFeedImpl implements RssMlbFeed {


    void readMlbRssFeed() {

        try {
            String url = "http://mlb.mlb.com/partnerxml/gen/news/rss/ana.xml"

            XmlReader reader = new XmlReader(new URL(url))

            SyndFeed feed = new SyndFeedInput().build(reader)
            System.out.println(feed.getTitle())
            System.out.println("***********************************")

            for (SyndEntry entry : feed.getEntries()) {
                System.out.println(entry)
                System.out.println("***********************************")
            }
            System.out.println("Done")

        } catch (Exception ex){
            System.out.println("CAUGHT EXCEPTION READING MLB RSS FEED: "+ex)
        }


    }
}