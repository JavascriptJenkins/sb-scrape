package com.genre.base.scraper.repo

import com.genre.base.scraper.repo.objects.nhl.ScrapeAuditVO
import com.genre.base.scraper.repo.objects.nhl.ScrapeTypeVO
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface ScrapeTypeVORepo extends CrudRepository<ScrapeTypeVO, Long>{



}
