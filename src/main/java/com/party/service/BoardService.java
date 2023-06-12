package com.party.service;

import com.party.entity.Board;
import com.party.mapper.BoardMapperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapperInterface boardMapperInterface;

    public List<Board> selectAll() {
        List<Board> boardList = boardMapperInterface.selectAll();
        Collections.reverse(boardList);
        return boardList;
    }

    public List<Board> selectWithPaging(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return boardMapperInterface.selectWithPaging(offset, pageSize);
    }

    public int getTotalPageCount(int pageSize) {
        int totalCount = boardMapperInterface.selectTotalCount();
        return (int) Math.ceil((double) totalCount / pageSize);
    }

    public int insert(Board board) {
        return boardMapperInterface.insert(board);
    }

    public int update(Board board) {
        return boardMapperInterface.update(board);
    }

    public int delete(Long id) {
        return boardMapperInterface.delete(id);
    }

    public Board selectOne(Long id) {
        return boardMapperInterface.selectOne(id);
    }

    public List<Board> searchBoards(String searchTerm) {
        return boardMapperInterface.searchBoards(searchTerm);
    }

    public List<Board> searchBoardsByTitle(String searchTerm) {
        return boardMapperInterface.searchBoardsByTitle(searchTerm);
    }

    public List<Board> searchBoardsByContent(String searchTerm) {
        return boardMapperInterface.searchBoardsByContent(searchTerm);
    }

    public List<Board> searchBoardsByTitleAndContent(String searchTerm) {
        return boardMapperInterface.searchBoardsByTitleAndContent(searchTerm);
    }


}