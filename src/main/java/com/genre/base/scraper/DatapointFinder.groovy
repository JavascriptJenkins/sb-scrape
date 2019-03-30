package com.genre.base.scraper

import com.genre.base.scraper.repo.objects.nhl.GoalieVO
import com.genre.base.scraper.repo.objects.nhl.NhlGameVO
import org.springframework.stereotype.Component

@Component
interface DatapointFinder {

    NhlGameVO getGoalieData(ArrayList datapoints)


}
