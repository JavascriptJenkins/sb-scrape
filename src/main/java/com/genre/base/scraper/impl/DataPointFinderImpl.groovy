package com.genre.base.scraper.impl

import com.genre.base.scraper.DatapointFinder
import com.genre.base.scraper.constants.ScrapeConstants
import com.genre.base.scraper.repo.objects.nhl.GoalieVO
import com.genre.base.scraper.repo.objects.nhl.NhlGameVO
import com.genre.base.utilities.SysUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DataPointFinderImpl implements DatapointFinder {

    @Autowired
    SysUtil sysUtil

    NhlGameVO getGoalieData(ArrayList datapoints){
        NhlGameVO nhlGameVO = new NhlGameVO()
        List<GoalieVO> goalieVOList = new ArrayList<>()
        nhlGameVO.setGoalieVOList(goalieVOList)

        int numberOfGoalies = 2
        int k = 0

        // this will execute 2 times and get 2 goalies information
        // out of datapoints from page
        while(k < numberOfGoalies){

            GoalieVO goalieVO = new GoalieVO()
            goalieVO.setCreateTimeStamp(new Date())

            // cycle thru all datapoints to find a goalie
            int i = 0
            for(String entry :datapoints){

                // only hydrate the nhl object one time
                if(k == 0){
                    setNhlGameProperties(nhlGameVO, entry, i)
                    goalieVO = setGoalieProperties(goalieVO, entry, i, k)
                    if(goalieVO.isHydrated){
                        break;
                    }
                } else {
                    goalieVO = setGoalieProperties(goalieVO, entry, i, k)
                    if(goalieVO.isHydrated){
                        break;
                    }
                }

                i++
            }
            //datapoints.eachWithIndex { String entry, int i ->

          //  }

            nhlGameVO.goalieVOList.add(goalieVO) // add the new hydrated goalievo
            k++ // increment k
        }


        return nhlGameVO
    }


    void setNhlGameProperties(NhlGameVO nhlGameVO, String entry, int i){
        // set nhl game properties
        if(entry.contains('at') && i <= 1 && sysUtil.isAlpha(entry)){
            nhlGameVO.setGameDesc(entry)
        }
        if(entry.contains('pm') || entry.contains('am')
                && i <= 2 && !sysUtil.isAlpha(entry)){
            nhlGameVO.setDateTimeOfGame(entry)
        }
    }

    GoalieVO setGoalieProperties(GoalieVO goalieVO, String entry, int i, int k){

        // k is the amount of goalies we need to parse out
        if(k == 0){
            // set 1st goalie's properties
            if(sysUtil.isAlpha(entry)
                    && (!entry.contains('Confirmed') && !entry.contains('Unconfirmed'))
                    && i <= 3){
                goalieVO.setName(entry)
            }

            if((entry.contains('Confirmed') || entry.contains('Unconfirmed'))
                    && i <= 6){
                goalieVO.setIsConfirmed(checkConfirmed(entry))
                goalieVO.setIsHydrated(true)
                return goalieVO
            }
        } else {
            // set 2nd goalie's properties
            if(sysUtil.isAlpha(entry)
                    && (!entry.contains('Confirmed') && !entry.contains('Unconfirmed'))
                    && i <= 16 &&
                    i > 10){
                goalieVO.setName(entry)
            }

            if((entry.contains('Confirmed') || entry.contains('Unconfirmed'))
                    && i <= 18 &&
                    i > 10){
                goalieVO.setIsConfirmed(checkConfirmed(entry))
                goalieVO.setIsHydrated(true)
                return goalieVO
            }
        }

        goalieVO.setIsHydrated(false)
        return goalieVO
    }

    static int checkConfirmed(String entry){
        // if goalie is confirmed, flip integer to true (1)
        if(ScrapeConstants.CONFIRMED.equals(entry)){
            return 1
        } else {
            return 0
        }
    }





}
