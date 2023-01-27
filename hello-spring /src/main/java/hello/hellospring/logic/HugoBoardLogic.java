package hello.hellospring.logic;

import hello.hellospring.enums.ErrorCodeEnum;
import hello.hellospring.mybatis.dao.HugoBoardDao;
import hello.hellospring.mybatis.model.HugoBoardLikeModel;
import hello.hellospring.mybatis.model.HugoBoardModel;
import hello.hellospring.mybatis.model.HugoBoardReplyDeclarationModel;
import hello.hellospring.mybatis.model.HugoBoardReplyModel;
import hello.hellospring.req.model.board.*;
import hello.hellospring.res.model.*;
import hello.hellospring.service.HugoBoardService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class HugoBoardLogic {

    @Autowired
    private HugoBoardService hugoBoardService;

    /**
     * Board 전체를 가져오는 로직
     * @return
     */
    public ApiResultObjectDto getAllHugoBoardLogic(final int startPage, final int listNumber) {
        int resultCode = 200;
        // Board List
        int start = 0;
        if (startPage > 0) {
            start = startPage * listNumber;
        }
        //총개수
        int boardTotalCount = hugoBoardService.getHugoBoardCount();
        //리스트
        List<HugoBoardModel> hugoBoardList = hugoBoardService.findHugoBoardList(start, listNumber);
        // 리스트가 없다면 예외처리
        if(hugoBoardList.size() == 0 || hugoBoardList.isEmpty()) {
            resultCode = 550;
        }

        BoardPagingResModel resModel = new BoardPagingResModel();
        resModel.setTotalCount(boardTotalCount);
        resModel.setBoardInfo(hugoBoardList);

        return ApiResultObjectDto.builder().
                resultCode(resultCode).result(resModel).build();
    }

    /**
     * 게시판 글쓰기 로직
     * @param reqModel
     * @return
     */
    @Transactional
    public ApiResultObjectDto writeHugoBoardLogic(HugoWriteBoardReqModel reqModel) {
        // 결과 코드 기본 ok
        int resultCode = HttpStatus.OK.value();
        // 결과 선언해줄 resultMap 선언
        Map<String, Object> resultMap = new HashMap<>();

        // 제목이 비었다면 에러처리
        if ("".equals(reqModel.getTitle())) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NOT_TITLE.code();
            log.error("제목이 입력되지 않았습니다");
        } else {
            // 제목이 있다면 입력 시작

            HugoBoardModel hugoBoardModel = new HugoBoardModel();
            hugoBoardModel.setTitle(reqModel.getTitle());
            hugoBoardModel.setContent(reqModel.getContent());
            hugoBoardModel.setEventPeriod(reqModel.getEventPeriod());
            hugoBoardModel.setId(reqModel.getId());
            hugoBoardModel.setOFile(reqModel.getOFile());
            hugoBoardModel.setWriteHeader(reqModel.getWriteHeader());
            hugoBoardModel.setBoarder(reqModel.getBoarder());

            try {
                hugoBoardService.writeHugoBoard(hugoBoardModel);
                log.debug("key >> {}",hugoBoardModel.getBoardIdx());
            }catch (Exception e) {
                log.error("error >> {}",e.toString());
            }
            resultMap.put("boardIdx", hugoBoardModel.getBoardIdx());
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    /**
     * 게시글 상세보기 로직
     * @param boardIdx
     * @param id
     * @return
     */
    public ApiResultObjectDto selectHugoBoard(Long boardIdx , String id) {
        // 결과 코드 기본 ok
        int resultCode = HttpStatus.OK.value();
        // 결과 선언해줄 resultMap 선언
        List<HugoBoardReplyModel> hugoBoardReplyModels = new ArrayList<>();

        if(boardIdx == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD_IDX.code();
            log.error("boardIdx 값을 입력해주세요");
        }
        //기본 게시판
        HugoBoardDetailResModel hugoBoardModel = hugoBoardService.selectHugoBoard(boardIdx);

        // 게시글 존재 여부 확인
        if (hugoBoardModel == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD_DETAIL.code();
            log.error("게시글이 존재하지 않습니다");
        } else {

            // 해당 게시글의 댓글 목록 가져오기
            hugoBoardReplyModels = hugoBoardService.listHugoBoardReplyByBoardIdx(boardIdx);

            if(hugoBoardReplyModels == null || hugoBoardReplyModels.size() == 0) {
                log.debug("댓글이 존재하지 않습니다");
            }
            else {
                hugoBoardModel.setReplyInfo(hugoBoardReplyModels);
            }
        }

        // 작성자와 조회하는 사람의 아이디가 같다면
        if(hugoBoardModel.getId().equals(id)) {
            log.debug("게시글 작성자는 조회수가 증가하지 않습니다");
        } else {
            hugoBoardService.updateVisitCount(boardIdx);
            log.debug("조회수 1 증가");
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(hugoBoardModel)
                .build();
    }

    /**
     * 게시글 삭제 로직
     * @param reqModel
     * @return
     */
    public ApiResultObjectDto deleteHugoBoard(HugoBoardDeleteReqModel reqModel) {
        // 결과 코드 기본 ok
        int resultCode = HttpStatus.OK.value();
        // 결과 선언해줄 resultMap 선언
        Map<String, Object> resultMap = new HashMap<>();

        HugoBoardDetailResModel hugoBoardModel = hugoBoardService.selectHugoBoard(reqModel.getBoardIdx());
        if(hugoBoardModel == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD_DETAIL.code();
            log.error("해당 번호의 게시글은 존재하지 않습니다");
        } else {
            // 작성자와 삭제 요청자가 같은 아이디 인지 확인
            if(hugoBoardModel.getId().equals(reqModel.getId())) {
                hugoBoardService.deleteHugoBoard(reqModel.getBoardIdx());
                //~~ 님 ~~ 번 글 삭제 처리되었습니다 결과 반환
                resultMap.put("id", reqModel.getId());
                resultMap.put("boardId", reqModel.getBoardIdx());
            } else {
                resultCode = ErrorCodeEnum.CUSTOM_ERROR_NOT_CORRECT_USER.code();
                log.error("작성자 본인만 삭제가 가능합니다");
            }
        }
        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    /**
     * 게시글 수정 로직
     * @param reqModel
     * @return
     */
    public ApiResultObjectDto updateHugoBoard(HugoUpdateBoardReqModel reqModel) {
        // 결과 코드 기본 ok
        int resultCode = HttpStatus.OK.value();
        // 결과 선언해줄 resModel 선언
        HugoUpdateBoardResModel resModel = new HugoUpdateBoardResModel();

        HugoBoardDetailResModel hugoBoardModel = hugoBoardService.selectHugoBoard(reqModel.getBoardIdx());
        if(hugoBoardModel == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD_DETAIL.code();
            log.error("해당 번호의 게시글은 존재하지 않습니다");
        } else {
            if("".equals(reqModel.getTitle())){
                resultCode = ErrorCodeEnum.CUSTOM_ERROR_NOT_TITLE.code();
                log.error("제목이 입력되지 않았습니다");
            } else {
                hugoBoardService.updateHugoBoard(reqModel);
                HugoBoardDetailResModel hugoBoard = hugoBoardService.selectHugoBoard(reqModel.getBoardIdx());

                resModel.setBoardIdx(hugoBoard.getBoardIdx());
                resModel.setId(hugoBoard.getId());
                resModel.setTitle(hugoBoard.getTitle());
            }
        }
        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resModel)
                .build();
    }

    /**
     * 게시글 좋아요 로직
     * @param reqModel
     * @return
     */
    public ApiResultObjectDto likeHugoBoard(HugoBoardLikeReqModel reqModel) {
        // 결과 코드 기본 ok
        int resultCode = HttpStatus.OK.value();
        // 결과 선언해줄 resultMap 선언
        Map<String, Object> resultMap = new HashMap<>();

        log.error("id = {}", reqModel.getId());

        if ("".equals(reqModel.getId())) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NOT_LOGIN.code();
            log.error("로그인 되어있지 않습니다");
        }

        if ("".equals(reqModel.getBoardIdx())) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD_IDX.code();
            log.error("게시판 번호를 입력해주세요");
        }

        HugoBoardDetailResModel hugoBoardModel = hugoBoardService.selectHugoBoard(reqModel.getBoardIdx());

        if(hugoBoardModel == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD_DETAIL.code();
            log.error("존재하지 않는 게시글");
        } else {
            // 좋아요 테이블 여부확인
            HugoBoardLikeModel likeTable = hugoBoardService.selectHugoBoardLike(reqModel.getId(), reqModel.getBoardIdx());

            // 좋아요 테이블이 없다면 만들기
            if (likeTable == null) {
                HugoBoardLikeModel hugoBoardLikeModel = new HugoBoardLikeModel();
                hugoBoardLikeModel.setBoardIdx(reqModel.getBoardIdx());
                hugoBoardLikeModel.setId(reqModel.getId());

                //좋아요 테이블 만들기
                hugoBoardService.insertLikeCountBoard(hugoBoardLikeModel);

                likeTable = hugoBoardService.selectHugoBoardLike(reqModel.getId(), reqModel.getBoardIdx());
            }

            // getLikeYN 이 0 이라면 좋아요 되어있지 않은상태
            if (likeTable.getLikeYN() == 0) {
                // LikeYN 1 로 변경
                hugoBoardService.updateLikeCountBoard(likeTable.getLikeIdx());
                // 좋아요 숫자 증가
                hugoBoardService.updateLikeCount(likeTable.getBoardIdx());

                // 변경 이후 likeTable 가져오기
                likeTable = hugoBoardService.selectHugoBoardLike(reqModel.getId(), reqModel.getBoardIdx());
                resultMap.put("likeTable", likeTable);
                resultMap.put("isLiked", true);
            } else {
                // LikeYN 0으로 변경
                hugoBoardService.updateDislikeCountBoard(likeTable.getLikeIdx());
                // 좋아요 숫자 감소
                hugoBoardService.updateDisLikeCount(likeTable.getBoardIdx());

                // 변경 이후 likeTable 가져오기
                likeTable = hugoBoardService.selectHugoBoardLike(reqModel.getId(), reqModel.getBoardIdx());
                resultMap.put("likeTable", likeTable);
                resultMap.put("isLiked", false);
            }
        }
        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    /**
     * 게시글 댓글 작성
     * @param reqModel
     * @return
     */
    public ApiResultObjectDto writeHugoBoardReply(HugoBoardReplyReqModel reqModel) {
        // 결과 코드 기본 ok
        int resultCode = HttpStatus.OK.value();
        // 결과 선언해줄 resultMap 선언
        Map<String, Object> resultMap = new HashMap<>();

        HugoBoardDetailResModel getHugoBoard = hugoBoardService.selectHugoBoard(reqModel.getBoardIdx());

        // 게시글 번호로 조회한 게시글이 없다면 게시글 존재 x 에러
        if (getHugoBoard == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD_DETAIL.code();
            log.error("게시글이 존재하지 않습니다");
        } else {
            // 게시글이 존재한다면 댓글 작성
            HugoBoardReplyModel hugoBoardReplyModel = new HugoBoardReplyModel();
            hugoBoardReplyModel.setBoardIdx(reqModel.getBoardIdx());
            hugoBoardReplyModel.setContent(reqModel.getContent());
            hugoBoardReplyModel.setNickName(reqModel.getNickName());

            hugoBoardService.writeHugoBoardReply(hugoBoardReplyModel);

            resultMap.put("boardIdx", hugoBoardReplyModel.getBoardReplyIdx());
            resultMap.put("boardReplyIdx", hugoBoardReplyModel.getBoardReplyIdx());
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    /**
     * 댓글 수정 로직
     * @param reqModel
     * @return
     */
    public ApiResultObjectDto updateHugoBoardReply(HugoBoardReplyUpdateReqModel reqModel) {
        // 결과 코드 기본 ok
        int resultCode = HttpStatus.OK.value();
        // 결과 선언해줄 resModel 선언
        HugoUpdateReplyResModel resModel = new HugoUpdateReplyResModel();

        // 댓글 번호로 댓글 가져오기
        HugoBoardReplyModel hugoBoardReplyModel = hugoBoardService.selectReply(reqModel.getBoardReplyIdx());

        // 댓글번호로 조회한 댓글이 없다면
        if (hugoBoardReplyModel == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD_REPLY.code();
            log.error("댓글이 존재하지 않습니다");
        } else {
            hugoBoardService.updateHugoBoardReply(reqModel);
            hugoBoardReplyModel = hugoBoardService.selectReply(reqModel.getBoardReplyIdx());

            resModel.setBoardIdx(hugoBoardReplyModel.getBoardIdx());
            resModel.setNickName(hugoBoardReplyModel.getNickName());
            resModel.setBoardReplyIdx(hugoBoardReplyModel.getBoardReplyIdx());
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resModel)
                .build();
    }

    /**
     * 댓글 삭제 로직
     * @param reqModel
     * @return
     */
    public ApiResultObjectDto deleteHugoBoardReply(HugoBoardReplyDeleteReqModel reqModel) {
        // resultCode 객체 선언
        int resultCode = HttpStatus.OK.value();
        // 결과값 담을 Map 선언
        Map<String, Object> resultMap = new HashMap<>();

        HugoBoardReplyModel hugoBoardReply = hugoBoardService.selectReply(reqModel.getBoardReplyIdx());

        if(hugoBoardReply == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD_REPLY.code();
            log.error("댓글이 존재하지 않습니다");
        } else {
            // 요청한 nickName 과 작성자 nickName 이 같은지
            if (reqModel.getNickName().equals(hugoBoardReply.getNickName())) {
                hugoBoardService.deleteReply(reqModel.getBoardReplyIdx());
                // ~~ 님 ~~ 번 댓글 삭제되었습니다
                resultMap.put("nickName", reqModel.getNickName());
                resultMap.put("replyIdx", reqModel.getBoardReplyIdx());
            } else {
                resultCode = ErrorCodeEnum.CUSTOM_ERROR_NOT_CORRECT_USER.code();
                log.error("작성자 본인만 삭제가 가능합니다");
            }
        }
        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    /**
     * 댓글 신고 로직
     * @param reqModel
     * @return
     */
    public ApiResultObjectDto declarationReply(HugoBoardReplyDeclarationReqModel reqModel) {
        // 결과값 기본 코드
        int resultCode = HttpStatus.OK.value();
        // 결과 저장할 HashMap
        Map<String, Object> resultMap = new HashMap<>();

        HugoBoardReplyModel isReplyOk = hugoBoardService.selectReply(reqModel.getBoardReplyIdx());

        // 댓글 존재 여부 확인
        if (isReplyOk == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD_REPLY.code();
            log.error("해당 댓글은 존재하지 않습니다");
        } else {
            // 존재하면 신고 접수
            HugoBoardReplyDeclarationModel declarationModel = new HugoBoardReplyDeclarationModel();
            declarationModel.setReason(reqModel.getReason());
            declarationModel.setId(reqModel.getId());
            declarationModel.setBoardReplyIdx(reqModel.getBoardReplyIdx());
            declarationModel.setContent(reqModel.getContent());

            hugoBoardService.declarationReply(declarationModel);
            resultMap.put("replyIdx", declarationModel.getBoardReplyIdx());
            resultMap.put("declarationIdx", declarationModel.getDeclarationIdx());
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    /**
     * 게시판 리스트 가져오기
     * @param startPage
     * @param listCount
     * @return
     */
    public ApiResultObjectDto getHugoBoardLists(int startPage, int listCount) {
        // 결과값 기본 코드
        int resultCode = HttpStatus.OK.value();

        // 페이지 시작 인자 번호
        int startNum = (startPage - 1) * listCount;

        List<HugoBoardModel> hugoBoardList = hugoBoardService.getHugoBoardLists(startNum, listCount);

        // 리스트가 없다면 예외처리
        if(hugoBoardList.size() == 0 || hugoBoardList.isEmpty()) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD.code();
            log.error("게시글이 없습니다");
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(hugoBoardList)
                .build();
    }

    /**
     * 페이지 하단 넘버 보여주는 API
     * @param page
     * @param countPage
     * @param listCount
     * @return
     */
    public ApiResultObjectDto getHugoBoardPage(int page, int countPage , int listCount) {
        // 결과값 기본 코드
        int resultCode = HttpStatus.OK.value();

        List<Integer> pageList = new ArrayList<>();

        // 총 게시글 수 가져오기
        int totalCount = hugoBoardService.getHugoBoardCount();
        if(totalCount == 0) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NULL_BOARD.code();
            log.error("게시글이 없습니다");
        }

        // 토탈페이지 숫자 구하기
        int totalPage = totalCount / listCount;
        // 나눠서 딱 떨아지지 않으면 한페이지 추가하기
        if (totalCount % listCount > 0) {
            totalPage++;
        }

        //총 페이지 수보다 큰 페이지를 원하면 마지막 페이지 보여주기
        if (totalPage < page) {
            page = totalPage;
        }

        // 시작페이지 번호 0
        int startPage = ((page - 1) / countPage) * countPage;
        // 마지막 페이지
        int endPage = startPage + countPage -1;
        // endPage 가 totalPage 보다 클경우 총 페이지까지만 보여주기
        if(totalPage < endPage) {
            endPage = totalPage;
        }

        // 보여줘야할 페이지 리스트 주입
        for (int i = startPage; i <= endPage; i++) {
            pageList.add(i);
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(pageList)
                .build();
    }


    /**
     * 좋아요 테이블 확인하기
     * @param boardIdx
     * @param id
     * @return
     */
    public ApiResultObjectDto getHugoBoardLike(Long boardIdx, String id) {
        // 결과값 기본 코드
        int resultCode = HttpStatus.OK.value();

        // 결과 반환할 resultMap
        Map<String, Object> resultMap = new HashMap<>();

        Long likeCount = hugoBoardService.getLikeCount(boardIdx);
        resultMap.put("likeCount", likeCount);

        // 좋아요 테이블 확인
        HugoBoardLikeModel likeTable = hugoBoardService.selectHugoBoardLike(id, boardIdx);

        // 없다면 생성
        if(likeTable == null) {
            HugoBoardLikeModel hugoBoardLikeModel = new HugoBoardLikeModel();
            hugoBoardLikeModel.setBoardIdx(boardIdx);
            hugoBoardLikeModel.setId(id);

            //좋아요 테이블 만들기
            hugoBoardService.insertLikeCountBoard(hugoBoardLikeModel);

            likeTable = hugoBoardService.selectHugoBoardLike(id, boardIdx);
        }
        resultMap.put("likeTable", likeTable);

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    public void setFileCode(String fileCode , Long boardIdx) {
        hugoBoardService.setFileCode(fileCode, boardIdx);
    }
}
