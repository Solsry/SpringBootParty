package com.party.service;

import com.party.dto.NoticeDto;
import com.party.entity.Notice;
import com.party.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 공지사항 등록
    public void createNotice(NoticeDto noticeDto) {
        Notice notice = new Notice();
        notice.setNoTitle(noticeDto.getNoTitle());
        notice.setNoContent(noticeDto.getNoContent());
        notice.setRegdate(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    // 공지사항 수정
    public void updateNotice(Long id, NoticeDto noticeDto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Notice ID"));

        notice.setNoTitle(noticeDto.getNoTitle());
        notice.setNoContent(noticeDto.getNoContent());
        noticeRepository.save(notice);
    }

    // 공지사항 삭제
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    // 페이징 처리를 포함한 공지사항 목록 조회
    public Page<Notice> getNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    // 공지사항 내용 검색
    public Page<Notice> searchNoticesByContent(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("regdate").descending());
        return noticeRepository.findByNoContentContaining(keyword, pageable);
    }

    // 공지사항 상세 조회
    public Notice getNoticeById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Notice ID"));
    }

}

