package hello.hellospring.mybatis.dao;

import hello.hellospring.mybatis.model.HugoBoardLikeModel;
import hello.hellospring.mybatis.model.HugoBoardModel;
import hello.hellospring.mybatis.model.HugoBoardReplyDeclarationModel;
import hello.hellospring.mybatis.model.HugoBoardReplyModel;
import hello.hellospring.req.model.board.HugoBoardReplyUpdateReqModel;
import hello.hellospring.req.model.board.HugoUpdateBoardReqModel;
import hello.hellospring.res.model.HugoBoardDetailResModel;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Mapper
public interface HugoBoardDao {

    /**
     * 단순 리스트 가져오기 - 초기 테스트용
     *
     * @return
     */
    @Select("select * \n" +
            "\tfrom (select @rownum:=@rownum+1 AS ROWNUM, A.*\n" +
            "\t\tfrom dev.HUGO_BOARD A, (SELECT @rownum := 0) TMP\n" +
            "\t\torder by board_idx asc) SUB\n" +
            "order by SUB.ROWNUM desc limit #{start} , #{end}")
    List<HugoBoardModel> getHugoBoardList(@Param("start") int start, @Param("end") int end);

    /**
     * 게시판 글쓰기
     *
     * @param hugoBoardModel
     */
    @Insert("insert into HUGO_BOARD (title,content,event_period,id,ofile,write_header,boarder) \n" +
            "values (#{title } , #{content , jdbcType=VARCHAR}, #{eventPeriod , jdbcType=VARCHAR}, " +
            "#{id } ,#{oFile , jdbcType=VARCHAR},#{writeHeader,jdbcType=VARCHAR},#{boarder})")
    @Options(useGeneratedKeys = true, keyProperty = "boardIdx", keyColumn = "boardIdx")
    void writeHugoBoard(HugoBoardModel hugoBoardModel);

    /**
     * 게시글 상세보기
     *
     * @param boardIdx
     * @return
     */
    @Select("select * from HUGO_BOARD where board_idx = #{boardIdx}")
    HugoBoardDetailResModel selectHugoBoard(Long boardIdx);

    /**
     * 게시글 삭제
     *
     * @param boardIdx
     */
    @Delete("delete from HUGO_BOARD where board_idx = #{boardIdx}")
    void deleteHugoBoard(Long boardIdx);

    /**
     * 게시글 수정
     *
     * @param reqModel
     */
    @Update("update hugo_board " +
            "set " +
            "title = #{title }, " +
            "content = #{content , jdbcType=VARCHAR}, " +
            "event_period = #{eventPeriod , jdbcType=VARCHAR}, " +
            "ofile = #{oFile , jdbcType=VARCHAR}, " +
            "write_header = #{writeHeader,jdbcType=VARCHAR}, " +
            "boarder = #{boarder} " +
            " where board_idx = #{boardIdx}")
    void updateHugoBoard(HugoUpdateBoardReqModel reqModel);


    /**
     * 게시글 조횟수 증가
     *
     * @param boardIdx
     */
    @Update("update hugo_board " +
            "set " +
            "visit_count = visit_count+1 " +
            "where board_idx = #{boardIdx}")
    void updateVisitCount(Long boardIdx);

    /**
     * 좋아요 테이블 만들기
     *
     * @param likeModel
     */
    @Insert("insert into hugo_board_like (board_idx, id) " +
            "values (#{boardIdx} , #{id} )")
    void insertLikeCountBoard(HugoBoardLikeModel likeModel);

    /**
     * 좋아요시 테이블 상태변경
     *
     * @param likeIdx
     */
    @Update("update hugo_board_like set " +
            "like_yn = 1 where like_idx = #{like_idx}")
    void updateLikeCountBoard(Long likeIdx);

    /**
     * 좋아요 취소시 테이블 상태변경
     *
     * @param likeIdx
     */
    @Update("update hugo_board_like set " +
            "like_yn = 0 where like_idx = #{like_idx}")
    void updateDislikeCountBoard(Long likeIdx);

    /**
     * 좋아요 수 증가
     *
     * @param boardIdx
     */
    @Update("update hugo_board " +
            "set " +
            "like_count = like_count+1 " +
            "where board_idx = #{boardIdx}")
    void updateLikeCount(Long boardIdx);

    /**
     * 좋아요 수 감소
     *
     * @param boardIdx
     */
    @Update("update hugo_board " +
            "set " +
            "like_count = like_count-1 " +
            "where board_idx = #{boardIdx}")
    void updateDisLikeCount(Long boardIdx);

    /**
     * id 와 boardIdx 로 좋아요 테이블 존재 여부 확인
     *
     * @param id
     * @param boardIdx
     * @return
     */
    @Select("select * from hugo_board_like where id = #{id } and board_idx = #{boardIdx }")
    HugoBoardLikeModel selectHugoBoardLike(@Param("id") String id, @Param("boardIdx") Long boardIdx);

    /**
     * 댓글 쓰기
     *
     * @param hugoBoardReplyModel
     */
    @Insert("insert into hugo_board_reply (nick_name , content, board_idx) " +
            "values (#{nickName },#{content }, #{boardIdx})")
    @Options(useGeneratedKeys = true, keyProperty = "boardReplyIdx", keyColumn = "boardReplyIdx")
    void writeHugoBoardReply(HugoBoardReplyModel hugoBoardReplyModel);

    /**
     * 게시글 댓글 가져오기
     *
     * @param boardIdx
     * @return
     */
    @Select("select * from hugo_board_reply where board_idx = #{boardIdx }")
    List<HugoBoardReplyModel> listHugoBoardReplyByBoardIdx(Long boardIdx);

    /**
     * 게시글 댓글 업데이트
     *
     * @param hugoBoardReplyUpdateReqModel
     */
    @Update("update hugo_board_reply " +
            "set content = #{content } where board_reply_idx = #{boardReplyIdx }")
    void updateHugoBoardReply(HugoBoardReplyUpdateReqModel hugoBoardReplyUpdateReqModel);

    /**
     * 댓글 번호로 댓글 가져오기
     *
     * @param boardReplyIdx
     * @return
     */
    @Select("select * from hugo_board_reply where board_reply_idx = #{boardReplyIdx }")
    HugoBoardReplyModel selectReply(Long boardReplyIdx);

    /**
     * 댓글 삭제
     *
     * @param boardReplyIdx
     */
    @Delete("delete from hugo_board_reply where board_reply_idx = #{boardReplyIdx }")
    void deleteReply(Long boardReplyIdx);

    /**
     * 댓글 신고
     *
     * @param hugoBoardReplyDeclarationModel
     */
    @Insert("insert into hugo_declaration (reason,id,board_reply_idx,content) " +
            "values (#{reason} , #{id} , #{boardReplyIdx } , #{content , jdbcType = VARCHAR })")
    void declarationReply(HugoBoardReplyDeclarationModel hugoBoardReplyDeclarationModel);

    /**
     * 페이지 리스트 가져오기
     *
     * @param startNum
     * @param listCount
     * @return
     */
    @Select("select * from dev.HUGO_BOARD limit #{startNum },#{listCount }")
    List<HugoBoardModel> getHugoBoardLists(@Param("startNum") int startNum, @Param("listCount") int listCount);

    /**
     * 전체 게시판 숫자 가져오기
     *
     * @return
     */
    @Select("select count(*) from dev.HUGO_BOARD")
    Integer getHugoBoardCount();

    @Select("select like_count from dev.hugo_board where board_idx = #{boardIdx }")
    Long getLikeCount(Long boardIdx);


    @Update("update dev.HUGO_BOARD set file_code = #{fileCode } where board_idx = #{boardIdx }")
    void setFileCode(@Param("fileCode") String fileCode, @Param("boardIdx") Long boardIdx);

}
