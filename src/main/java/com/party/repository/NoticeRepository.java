package com.party.repository;

import com.party.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 페이징 처리된 공지사항 목록 조회
    Page<Notice> findAll(Pageable pageable);

    // 제목으로 공지사항 조회
    Optional<Notice> findByNoTitle(String noTitle);

    // 공지사항 내용으로 검색
    Page<Notice> findByNoContentContaining(String keyword, Pageable pageable);
}
