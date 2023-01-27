package hello.hellospring.service;

import hello.hellospring.mybatis.dao.HugoBoardDao;
import hello.hellospring.mybatis.model.HugoBoardLikeModel;
import hello.hellospring.mybatis.model.HugoBoardModel;
import hello.hellospring.mybatis.model.HugoBoardReplyDeclarationModel;
import hello.hellospring.mybatis.model.HugoBoardReplyModel;
import hello.hellospring.req.model.board.HugoBoardReplyUpdateReqModel;
import hello.hellospring.req.model.board.HugoUpdateBoardReqModel;
import hello.hellospring.res.model.HugoBoardDetailResModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HugoBoardService {

    @Autowired
    private HugoBoardDao hugoBoardDao;

    /**
     * test 용
     * @return
     */
    public List<HugoBoardModel> findHugoBoardList(int start, int end) {
        return hugoBoardDao.getHugoBoardList(start, end);
    }

    /**
     * 게시판 글쓰기 서비스
     * @param hugoBoardModel
     */
    public void writeHugoBoard(HugoBoardModel hugoBoardModel) {
        // 제목이 입력되었을때 게시글 작성
        if(!"".equals(hugoBoardModel.getTitle())) {
            hugoBoardDao.writeHugoBoard(hugoBoardModel);
        }
    }

    /**
     * 게시판 상세보기 서비스
     * @param boardIdx
     * @return
     */
    public HugoBoardDetailResModel selectHugoBoard(Long boardIdx) {
        HugoBoardDetailResModel hugoBoardModel = hugoBoardDao.selectHugoBoard(boardIdx);
        return hugoBoardModel;
    }

    /**
     * 게시글 삭제 서비스
     * @param boardIdx
     */
    public void deleteHugoBoard(Long boardIdx) {
        hugoBoardDao.deleteHugoBoard(boardIdx);
    }

    /**
     * 게시글 업데이트 서비스
     * @param reqModel
     */
    public void updateHugoBoard(HugoUpdateBoardReqModel reqModel) {
        hugoBoardDao.updateHugoBoard(reqModel);
    }

    /**
     * 게시글 조회수 증가 서비스
     * @param boardIdx
     */
    public void updateVisitCount(Long boardIdx) {
        hugoBoardDao.updateVisitCount(boardIdx);
    }

    /**
     * 좋아요 테이블 만들기 서비스
     * @param hugoBoardLikeModel
     */
    public void insertLikeCountBoard(HugoBoardLikeModel hugoBoardLikeModel) {
        hugoBoardDao.insertLikeCountBoard(hugoBoardLikeModel);
    }

    /**
     * 좋아요시 테이블 상태변경 서비스
     * @param likeIdx
     */
    public void updateLikeCountBoard(Long likeIdx) {
        hugoBoardDao.updateLikeCountBoard(likeIdx);
    }

    /**
     * 좋아요 취소시 테이블 상태변경 서비스
     * @param likeIdx
     */
    public void updateDislikeCountBoard(Long likeIdx) {
        hugoBoardDao.updateDislikeCountBoard(likeIdx);
    }

    /**
     * 좋아요 수 증가 서비스
     * @param boardIdx
     */
    public void updateLikeCount(Long boardIdx) {
        hugoBoardDao.updateLikeCount(boardIdx);
    }

    /**
     * 좋아요 수 감소 서비스
     * @param boardIdx
     */
    public void updateDisLikeCount(Long boardIdx) {
        hugoBoardDao.updateDisLikeCount(boardIdx);
    }

    /**
     * id 와 boardIdx 로 좋아요 테이블 존재 여부 확인 서비스
     * @param id
     * @param boardIdx
     */
    public HugoBoardLikeModel selectHugoBoardLike(String id, Long boardIdx) {
        return hugoBoardDao.selectHugoBoardLike(id, boardIdx);
    }

    /**
     * 댓글 쓰기 서비스
     * @param hugoBoardReplyModel
     */
    public void writeHugoBoardReply(HugoBoardReplyModel hugoBoardReplyModel) {
        hugoBoardDao.writeHugoBoardReply(hugoBoardReplyModel);
    }

    /**
     * 게시글 댓글 리스트 가져오기 서비스
     * @param boardIdx
     * @return
     */
    public List<HugoBoardReplyModel> listHugoBoardReplyByBoardIdx(Long boardIdx) {
        return hugoBoardDao.listHugoBoardReplyByBoardIdx(boardIdx);
    }

    /**
     * 댓글 수정 서비스
     * @param reqModel
     */
    public void updateHugoBoardReply(HugoBoardReplyUpdateReqModel reqModel) {
        hugoBoardDao.updateHugoBoardReply(reqModel);
    }

    /**
     * 댓글 번호로 댓글 가져오기 서비스
     * @param boardReplyIdx
     * @return
     */
    public HugoBoardReplyModel selectReply(Long boardReplyIdx) {
        return hugoBoardDao.selectReply(boardReplyIdx);
    }

    /**
     * 댓글 삭제 서비스
     * @param boardReplyIdx
     */
    public void deleteReply(Long boardReplyIdx) {
        hugoBoardDao.deleteReply(boardReplyIdx);
    }

    /**
     * 댓글 신고 서비스
     * @param hugoBoardReplyDeclarationModel
     */
    public void declarationReply(HugoBoardReplyDeclarationModel hugoBoardReplyDeclarationModel) {
        hugoBoardDao.declarationReply(hugoBoardReplyDeclarationModel);
    }

    /**
     * 게시판 리스트 불러오는 API
     * @param startNum
     * @param listCount
     * @return
     */
    public List<HugoBoardModel> getHugoBoardLists(int startNum, int listCount) {
        return hugoBoardDao.getHugoBoardLists(startNum, listCount);
    }

    /**
     * 페이징을 위한 게시글 총 숫자 가져오기
     * @return
     */
    public Integer getHugoBoardCount() {
        return hugoBoardDao.getHugoBoardCount();
    }

    /**
     * 게시글 좋아요 숫자 가져오기
     * @param boardIdx
     * @return
     */
    public Long getLikeCount(Long boardIdx) {
        return hugoBoardDao.getLikeCount(boardIdx);
    }

    /**
     * 파일 코드 세팅하기
     * @param fileCode
     * @param boardIdx
     */
    public void setFileCode(String fileCode, Long boardIdx) {
        hugoBoardDao.setFileCode(fileCode, boardIdx);
    }
}
