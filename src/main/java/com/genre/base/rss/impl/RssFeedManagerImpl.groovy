package com.genre.base.rss.impl

import com.genre.base.rss.RssFeedManager
import com.genre.base.rss.RssMlbFeed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RssFeedManagerImpl implements RssFeedManager{

    @Autowired
    RssMlbFeed rssMlbFeed

    void readMlbRssFeed(){
        rssMlbFeed.readMlbRssFeed()
    }


}
