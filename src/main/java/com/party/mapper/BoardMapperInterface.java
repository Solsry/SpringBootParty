package com.party.mapper;

import com.party.entity.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapperInterface {

    @Select("SELECT * FROM boards")
    List<Board> selectAll();

    @Insert("INSERT INTO boards(writer, title, content, regdate, updateDate) " +
            "VALUES(#{writer}, #{title}, #{content}, #{regdate}, #{updateDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Board board);

    @Select("SELECT * FROM boards WHERE id = #{id}")
    Board selectOne(Long id);

    @Update("UPDATE boards SET writer = #{writer}, title = #{title}, content = #{content}, " +
            "updateDate = #{updateDate} WHERE id = #{id}")
    int update(Board board);

    @Delete("DELETE FROM boards WHERE id = #{id}")
    int delete(Long id);

    @Select("SELECT * FROM boards ORDER BY id DESC LIMIT #{offset}, #{pageSize}")
    List<Board> selectWithPaging(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(*) FROM boards")
    int selectTotalCount();

    @Select("SELECT * FROM boards WHERE writer LIKE CONCAT('%', #{searchTerm}, '%') OR " +
            "title LIKE CONCAT('%', #{searchTerm}, '%') OR content LIKE CONCAT('%', #{searchTerm}, '%')")
    List<Board> searchBoards(@Param("searchTerm") String searchTerm);

    @Select("SELECT * FROM boards WHERE title LIKE CONCAT('%', #{searchTerm}, '%')")
    List<Board> searchBoardsByTitle(@Param("searchTerm") String searchTerm);

    @Select("SELECT * FROM boards WHERE content LIKE CONCAT('%', #{searchTerm}, '%')")
    List<Board> searchBoardsByContent(@Param("searchTerm") String searchTerm);

    @Select("SELECT * FROM boards WHERE title LIKE CONCAT('%', #{searchTerm}, '%') OR " +
            "content LIKE CONCAT('%', #{searchTerm}, '%')")
    List<Board> searchBoardsByTitleAndContent(@Param("searchTerm") String searchTerm);
}