package com.party.service;

import com.party.dto.NoticeDto;
import com.party.entity.Notice;
import com.party.repository.NoticeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
// @Transactional
public class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    @DisplayName("게시물 등록 테스트")
    public void createNotice() {
        // Given
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNoTitle("Test Title");
        noticeDto.setNoContent("Test Content");

        // When
        noticeService.createNotice(noticeDto);

        // Then
        Optional<Notice> savedNoticeOptional = noticeRepository.findByNoTitle("Test Title");
        Assertions.assertTrue(savedNoticeOptional.isPresent());
        Notice savedNotice = savedNoticeOptional.get();
        Assertions.assertEquals(noticeDto.getNoTitle(), savedNotice.getNoTitle());
        Assertions.assertEquals(noticeDto.getNoContent(), savedNotice.getNoContent());
    }

    @Test
    @DisplayName("게시물 수정 테스트")
    public void updateNotice() {
        // Given
        Notice existingNotice = new Notice();
        existingNotice.setNoTitle("Existing Title");
        existingNotice.setNoContent("Existing Content");
        existingNotice.setRegdate(LocalDateTime.now());
        noticeRepository.save(existingNotice);

        Long noticeId = existingNotice.getId();

        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNoTitle("Updated Title");
        noticeDto.setNoContent("Updated Content");

        // When
        noticeService.updateNotice(noticeId, noticeDto);

        // Then
        Optional<Notice> updatedNoticeOptional = noticeRepository.findById(noticeId);
        Assertions.assertTrue(updatedNoticeOptional.isPresent());
        Notice updatedNotice = updatedNoticeOptional.get();
        Assertions.assertEquals(noticeDto.getNoTitle(), updatedNotice.getNoTitle());
        Assertions.assertEquals(noticeDto.getNoContent(), updatedNotice.getNoContent());
    }

    @Test
    @DisplayName("게시물 삭제 테스트")
    public void deleteNotice() {
        // Given
        Notice existingNotice = new Notice();
        existingNotice.setNoTitle("Existing Title");
        existingNotice.setNoContent("Existing Content");
        existingNotice.setRegdate(LocalDateTime.now());
        noticeRepository.save(existingNotice);

        Long noticeId = existingNotice.getId();

        // When
        noticeService.deleteNotice(noticeId);

        // Then
        Optional<Notice> deletedNoticeOptional = noticeRepository.findById(noticeId);
        Assertions.assertFalse(deletedNoticeOptional.isPresent());
    }

    @Test
    @DisplayName("게시물 조회 테스트")
    public void getNoticeById() {
        // Given
        Notice existingNotice = new Notice();
        existingNotice.setNoTitle("Existing Title");
        existingNotice.setNoContent("Existing Content");
        existingNotice.setRegdate(LocalDateTime.now());
        noticeRepository.save(existingNotice);

        Long noticeId = existingNotice.getId();

        // When
        Notice retrievedNotice = noticeService.getNoticeById(noticeId);

        // Then
        Assertions.assertNotNull(retrievedNotice);
        Assertions.assertEquals(existingNotice.getNoTitle(), retrievedNotice.getNoTitle());
        Assertions.assertEquals(existingNotice.getNoContent(), retrievedNotice.getNoContent());
    }
}
