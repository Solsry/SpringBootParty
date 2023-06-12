package com.party.controller;

import com.party.dto.NoticeDto;
import com.party.entity.Notice;
import com.party.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/notice")
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 목록 조회 페이지
    @GetMapping(value = {"/admin/list", "/admin/list/{page}"})
    public String getNoticesAdmin(Model model, @PathVariable(name = "page", required = false) Integer page) {
        int size = 10; // 한 페이지에 보여줄 공지사항 수
        int currentPage = page != null ? page : 0; // 현재 페이지 (기본값: 0)

        Pageable pageable = PageRequest.of(currentPage, size, Sort.by("regdate").descending());
        Page<Notice> noticePage = noticeService.getNotices(pageable);

        model.addAttribute("notices", noticePage.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", noticePage.getTotalPages());

        return "/notice/noticeList";
    }

    @GetMapping(value = {"/list", "/list/{page}"})
    public String getNotices(Model model,
                             @PathVariable(name = "page", required = false) Integer page) {
        int size = 10; // 한 페이지에 보여줄 공지사항 수
        int currentPage = page != null ? page : 0; // 현재 페이지 (기본값: 0)

        Pageable pageable = PageRequest.of(currentPage, size, Sort.by("regdate").descending());
        Page<Notice> noticePage = noticeService.getNotices(pageable);

        model.addAttribute("notices", noticePage.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", noticePage.getTotalPages());

        return "/notice/noticeList02";
    }

    // 공지사항 등록 폼 페이지
    @GetMapping(value = "/new")
    public String showCreateForm(Model model) {
        System.out.println("등록 :)");
        model.addAttribute("noticeDto", new NoticeDto());
        return "/notice/noticeInsert";
    }

    // 공지사항 등록 처리
    @PostMapping("/new")
    public String createNotice(@ModelAttribute("noticeDto") @Valid NoticeDto noticeDto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "notice/noticeInsert";
        }
        noticeService.createNotice(noticeDto);
        return "redirect:/notice/list";
    }

    // 공지사항 상세 페이지
    @GetMapping(value = "/detail/{id}")
    public String getNoticeDetail(@PathVariable("id") Long id, Model model) {
        Notice notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "/notice/noticeDetail";
    }

    // 공지사항 수정 폼 페이지
    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Notice notice = noticeService.getNoticeById(id);
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNoTitle(notice.getNoTitle());
        noticeDto.setNoContent(notice.getNoContent());
        model.addAttribute("noticeDto", noticeDto);
        return "/notice/noticeUpdate";
    }

    // 공지사항 수정 처리
    @PostMapping(value = "/{id}/edit")
    public String updateNotice(@PathVariable("id") Long id,
                               @ModelAttribute("noticeDto") @Valid NoticeDto noticeDto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "notice/noticeUpdate";
        }
        noticeService.updateNotice(id, noticeDto);
        return "redirect:/notice/list";
    }

    // 공지사항 삭제 처리
    @RequestMapping(value = "/{id}/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteNotice(@PathVariable("id") Long id) {
        noticeService.deleteNotice(id);
        return "redirect:/notice/list";
    }

    // 공지사항 검색
    @GetMapping("/search")
    public String searchNotices(Model model,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Notice> noticePage;
        if (keyword != null && !keyword.isEmpty()) {
            noticePage = noticeService.searchNoticesByContent(keyword, page, size);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            noticePage = noticeService.getNotices(pageable);
        }
        model.addAttribute("notices", noticePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", noticePage.getTotalPages());

        // 이전 페이지 번호 계산
        int previousPage = page > 0 ? page - 1 : 0;
        model.addAttribute("previousPage", previousPage);

        return "/notice/noticeList02";
    }
}