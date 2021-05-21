package com.base.app.domain.diary;

import com.base.app.core.domain.base.CoreJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiaryRepository extends CoreJPAQueryDSLRepository<Diary, Long> {

    // diaryId로 해당 데이터들 가져옴
    Diary findAllByDiaryId(Long diaryId);
}
