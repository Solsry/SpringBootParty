package com.party.controller;

import com.party.entity.Board;
import com.party.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;




    @GetMapping(value = {"/list", "/list/{page}"})
    public String selectAll(@PathVariable(required = false) Integer page,
                            @RequestParam(required = false) String searchTerm,
                            @RequestParam(required = false) String searchType,
                            Model model) {
        int pageSize = 10; // Number of items per page

        if (page == null) {
            page = 1; // Default page
        }

        List<Board> boardList;
        int totalPages;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            if (searchType != null && searchType.equals("Title")) {
                boardList = boardService.searchBoardsByTitle(searchTerm);
            } else if (searchType != null && searchType.equals("Content")) {
                boardList = boardService.searchBoardsByContent(searchTerm);
            } else {
                boardList = boardService.searchBoardsByTitleAndContent(searchTerm);
            }
            totalPages = 1; // No pagination for search results
        } else {
            boardList = boardService.selectWithPaging(page, pageSize);
            totalPages = boardService.getTotalPageCount(pageSize);
        }

        model.addAttribute("list", boardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "board/boardList02";
    }

    @GetMapping(value = {"/admin/list", "/admin/list/{page}"})
    public String selectAllAdmin(@PathVariable(required = false) Integer page,
                            @RequestParam(required = false) String searchTerm,
                            @RequestParam(required = false) String searchType,
                            Model model) {
        int pageSize = 10; // Number of items per page

        if (page == null) {
            page = 1; // Default page
        }

        List<Board> boardList;
        int totalPages;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            if (searchType != null && searchType.equals("Title")) {
                boardList = boardService.searchBoardsByTitle(searchTerm);
            } else if (searchType != null && searchType.equals("Content")) {
                boardList = boardService.searchBoardsByContent(searchTerm);
            } else {
                boardList = boardService.searchBoardsByTitleAndContent(searchTerm);
            }
            totalPages = 1; // No pagination for search results
        } else {
            boardList = boardService.selectWithPaging(page, pageSize);
            totalPages = boardService.getTotalPageCount(pageSize);
        }

        model.addAttribute("list", boardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "board/boardList";
    }

    @GetMapping(value = "/insert")
    public String showInsertForm(Model model) {
        System.out.println("게시물 등록");
        model.addAttribute("board", new Board());
        return "board/boardInsert";
    }

    @PostMapping(value = "/insert")
    public String processInsert(@ModelAttribute("board") Board board) {
        System.out.println("board: " + board);
        int cnt = boardService.insert(board);
        System.out.println("cnt: " + cnt);

        if (cnt == 1) {
            return "redirect:/board/list";
        } else {
            return "board/boardInsert";
        }
    }

    @GetMapping(value = "/detail/{id}")
    public String selectOne(@PathVariable("id") Long id, Model model) {
        Board board = boardService.selectOne(id);
        model.addAttribute("board", board);
        return "board/boardDetail";
    }

    @GetMapping(value = "/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Board board = boardService.selectOne(id);
        model.addAttribute("board", board);
        return "board/boardUpdate";
    }

    @PostMapping(value = "/update")
    public String processUpdate(Board board) {
        System.out.println("board : " + board);
        int cnt = boardService.update(board);
        System.out.println("cnt : " + cnt);

        if (cnt == 1) {
            return "redirect:/board/list";
        } else {
            return "board/boardUpdate";
        }
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        int cnt = boardService.delete(id);
        return "redirect:/board/list";
    }


}
