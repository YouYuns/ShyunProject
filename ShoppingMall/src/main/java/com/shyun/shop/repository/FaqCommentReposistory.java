package com.shyun.shop.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shyun.shop.entity.FaqComment;

public interface FaqCommentReposistory extends JpaRepository<FaqComment, Long>{

	List<FaqComment> findAllByIdOrderByRegTimeDesc(Long fno);

	Object findAllByFaqIdOrderByRegTimeDesc(long fno);
	@Query("select fc from FaqComment fc inner join fc.faq f where f.id=:id order by fc.updateTime desc")
	List<FaqComment> findFaqComments(@Param("id") Long fno);


}
