package hello.hellospring.logic;

import hello.hellospring.enums.ErrorCodeEnum;
import hello.hellospring.framework.exception.BaseException;
import hello.hellospring.mybatis.model.HugoUserInfoModel;
import hello.hellospring.req.model.*;
import hello.hellospring.res.model.ApiResultObjectDto;
import hello.hellospring.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class MemberLogic {

    @Autowired
    private MemberService memberService;

    /**
     * 회원 전체를 가져오는 로직
     * @return
     */
    public ApiResultObjectDto getAllHugoMemberLogic() {
        int resultCode = 200;

        //사용자 리스트
        List<HugoUserInfoModel> userInfoModelList = memberService.findHugoUserList();
        // 리스트가 없으면 예외처리
        if (userInfoModelList.size() == 0 || userInfoModelList.isEmpty()) {
            //throw new BaseException(ErrorCodeEnum.CUSTOM_EMPTY_MEMBER_LIST.msg(), ErrorCodeEnum.CUSTOM_EMPTY_MEMBER_LIST);
            resultCode = ErrorCodeEnum.CUSTOM_EMPTY_MEMBER_LIST.code();
        }
        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(userInfoModelList)
                .build();
    }

    /**
     * 회원가입 비지니스 로직
     * @param reqModel
     * @return
     */
    @Transactional
    public ApiResultObjectDto saveHugoUserLogic(HugoUserSaveReqModel reqModel) {
        //결과값 선언 ( http ok code )
        int resultCode = HttpStatus.OK.value();
        //리턴해줄 object map 선언
        Map<String, String>resultMap = new HashMap<>();

        //회원 아이디가 비웠으면 551번 에러처리
        if ( "".equals(reqModel.getId()) ) {
            resultCode = 551;
        } else {
            //회원 아이디가 있으면 회원정보 저장하기 시작

            //존재하는 아이디가 있는지 개수 확인
            int userCount = memberService.findCountHugoUserById(reqModel.getId());

            //아이디가 존재하면 에러 코드 값 주입
            if (userCount > 0) {
                resultCode = ErrorCodeEnum.CUSTOM_ERROR_ID_OVERLAP.code();
                log.error("이미 존재하는 아이디 입니다. ID :: {}", reqModel.getId());
            }
            //아이디가 존재하지 않으므로 저장 로직 시작
            if (userCount == 0) {

                //닉네임 존재 여부 개수 확인
                int nickNameCount = memberService.getHugoUserInfoByNickName(reqModel.getNickName());

                // 존재하면 생성 불가 (Unique)
                if(nickNameCount > 0) {
                    resultCode = ErrorCodeEnum.CUSTOM_ERROR_NICKNAME_OVERLAP.code();
                    log.error("이미 존재하는 닉네임 입니다. nickName :: {}",reqModel.getNickName());
                }

                if (nickNameCount == 0) {
                    HugoUserInfoModel hugoUserInfoModel = new HugoUserInfoModel();
                    hugoUserInfoModel.setId(reqModel.getId());
                    hugoUserInfoModel.setPwd(reqModel.getPwd());
                    hugoUserInfoModel.setName(reqModel.getName());
                    hugoUserInfoModel.setNickName(reqModel.getNickName());
                    hugoUserInfoModel.setEmail(reqModel.getEmail());
                    hugoUserInfoModel.setBirthDay(reqModel.getBirthDay());
                    hugoUserInfoModel.setGender(reqModel.getGender());
                    hugoUserInfoModel.setCallNum(reqModel.getCallNum());
                    hugoUserInfoModel.setInterest(reqModel.getInterest());

                    try {
                        //memberService.saveHugoUserInfo(hugoUserInfoModel);
                        memberService.saveHugoUserInfo(hugoUserInfoModel);
                        log.error("key >> " + hugoUserInfoModel.getIdx());
                    } catch (Exception e) {
                        log.error("error >> "+ e.toString());
                    }
                    resultMap.put("savedUserId", reqModel.getId());
                }
            }
        }
        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    /**
     * 아이디 중복체크 API
     * @param id
     * @return
     */
    public ApiResultObjectDto duplicateMemberId(String id) {
        //결과값 선언 ( http ok code )
        int resultCode = HttpStatus.OK.value();
        //리턴해줄 object map 선언
        Map<String, Object>resultMap = new HashMap<>();

        log.info(id);

        //회원 아이디가 비워져 있으면 551번 에러처리
        if ("".equals(id) ) {
            resultCode = 551;
        } else {

            //존재하는 아이디가 있는지 개수 확인
            int userCount = memberService.findCountHugoUserById(id);

            //아이디가 존재하면 에러 코드 값 주입
            if (userCount > 0) {
                resultCode = ErrorCodeEnum.CUSTOM_ERROR_ID_OVERLAP.code();
                log.error("이미 존재하는 아이디 입니다. ID :: {}", id);
                resultMap.put("isChecked", false);
            }
            //아이디가 존재하지 않으면 success
            if (userCount == 0) {
                resultMap.put("isChecked", true);
            }
        }

        return ApiResultObjectDto.builder()
                .result(resultMap)
                .resultCode(resultCode)
                .build();
    }

    public ApiResultObjectDto loginByIdLogic(HugoLoginReqModel reqModel , HttpServletRequest request) {
        //결과값 선언 ( http ok code )
        int resultCode = HttpStatus.OK.value();
        //리턴해줄 object map 선언
        Map<String, String>resultMap = new HashMap<>();

        // reqModel 에 요청된 id 로 HugoUserInfoModel 객체를 reqUser 로불러옴
        HugoUserInfoModel reqUser = memberService.findHugoUserById(reqModel.getId());

        // reqUser 요청 유저가 있으면
        if(reqUser != null) {
            HugoUserInfoModel loginMember = memberService.loginById(reqModel);
            String reqId = reqUser.getId();
            String reqPwd = reqUser.getPwd();
            String loginId = loginMember.getId();
            String loginPwd = loginMember.getPwd();

            if(loginMember != null) {
                HttpSession session = request.getSession();
                // 세션에 요청 유저 정보 저장
                session.setAttribute("loginMember",reqUser);
                // 세션에 로그인 여부 저장
                session.setAttribute("isLogon",true);
                // hashMap 에 nickName 저장 - 로그인 결과 뿌려줄때 사용
                resultMap.put("nickName", reqUser.getNickName());
            } else {
                // pwd 가 맞지 않는다면
                resultCode = 511;
                log.error("패스워드를 확인하세요 Pwd : "+reqModel.getPwd());
            }
        } else {
            resultCode = 510;
            log.error("아이디를 확인하세요 id : "+reqModel.getId());
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    public ApiResultObjectDto loginLogic(HugoLoginReqModel reqModel) {
        //결과값 선언 ( http ok code )
        int resultCode = HttpStatus.OK.value();
        //리턴해줄 object map 선언
        Map<String, String>resultMap = new HashMap<>();

        HugoUserInfoModel findUser = memberService.findHugoUserByIdAndPassword(reqModel.getId(), reqModel.getPwd());

        //회원정보가 없거나, 비밀번호가 틀렸을때
        if (findUser == null) {
            log.error("회원정보가 없습니다. :: " + reqModel.getId());
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_LOGIN.code();
        }
        //정상적으로 회원정보가 있을때
        if (findUser != null) {
            log.debug("findUser {}", findUser.toString());
            //리턴할 사용자 닉네임 값 주입
            resultMap.put("NickName", findUser.getNickName());
        }
        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    @Deprecated
    public ApiResultObjectDto logoutByIdLogic(String id,HttpServletRequest request) {
        //결과값 선언 ( http ok code )
        int resultCode = HttpStatus.OK.value();
        Boolean isLogon = false;

        HttpSession session = request.getSession();
        isLogon = (Boolean)session.getAttribute("isLogon");

        if (isLogon == null) {
            log.info("=================== error =======================");
        }
        if(isLogon) {
            session.removeAttribute("loginMember");
            session.removeAttribute("isLogon");
        } else {
            resultCode = 599;
            log.error("logon 되어있지 않습니다");
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(id)
                .build();
    }

    public ApiResultObjectDto getHugoMemberInfo(String id) {
        //결과값 선언 ( http ok code )
        int resultCode = HttpStatus.OK.value();

        HugoUserInfoModel loginMember = memberService.findHugoMemberInfoById(id);

        if(loginMember == null) {
            resultCode = 550;
            log.error("해당 회원아이디는 존재하지 않습니다"+id);
            return null;
        }
        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(loginMember)
                .build();
    }

    public ApiResultObjectDto updateHugoMemberInfo(HugoUserModifyReqModel reqModel) {
        //결과값 선언 ( http ok code )
        int resultCode = HttpStatus.OK.value();
        //리턴해줄 object map 선언
        Map<String, String>resultMap = new HashMap<>();

        HugoUserInfoModel hugoMemberInfoById = memberService.findHugoMemberInfoById(reqModel.getId());
        log.debug("reqModel.getNickName() : {}",reqModel.getNickName());

        if(hugoMemberInfoById == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_USER_INFO_NULL.code();
            log.error("회원정보가 없습니다. :: " + reqModel.getId());
        } else {
            memberService.updateHugoUserInfo(reqModel);
            // 결과값 송출해줄 resultMap
            resultMap.put("nickName", reqModel.getNickName());
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    public ApiResultObjectDto deleteHugoUserInfo(HugoUserDeleteReqModel reqModel) {
        //결과값 선언 ( http ok code )
        int resultCode = HttpStatus.OK.value();
        //리턴해줄 object map 선언
        Map<String, String>resultMap = new HashMap<>();

        // 아이디로 회원 정보 가져오기
        HugoUserInfoModel findUser = memberService.findHugoMemberInfoById(reqModel.getId());

        // 회원 정보가 비었다면
        if(findUser == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_NOT_FOUND_USER.code();
            log.error("회원정보가 없습니다");
        } else {
            // id로 가져온 비밀번호와 reqModel 의 비밀번호가 같으면
            if(findUser.getPwd().equals(reqModel.getPwd())) {
                memberService.deleteHugoUserInfo(reqModel.getId(), reqModel.getPwd());
                resultMap.put("id", reqModel.getId());
            } else {
                resultCode = ErrorCodeEnum.CUSTOM_ERROR_NOT_FOUND_USER.code();
                log.error("아이디 혹은 패스워드를 확인하세요");
            }
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }

    //비밀번호 검증 API
    public ApiResultObjectDto pwdCheckById(HugoPwdCheckedReqModel reqModel) {
        //결과값 선언 ( http ok code )
        int resultCode = HttpStatus.OK.value();
        //리턴해줄 object map 선언
        Map<String, String>resultMap = new HashMap<>();

        HugoUserInfoModel hugoUserById = memberService.findHugoUserById(reqModel.getId());

        // 요청한 id 값으로 가져온 회원정보가 없다면
        if(hugoUserById == null) {
            resultCode = ErrorCodeEnum.CUSTOM_ERROR_USER_INFO_NULL.code();
            log.error("회원정보가 없습니다");
        } else {
            // 요청 모델의 비밀번호와 회원 비밀번호가 같다면 성공
            if (reqModel.getPwd().equals(hugoUserById.getPwd())) {
                resultMap.put("id", reqModel.getId());
            } else {
                // 다르다면
                resultCode = ErrorCodeEnum.CUSTOM_ERROR_NOT_FOUND_USER.code();
                log.error("아이디 혹은 패스워드를 확인해주세요");
            }
        }

        return ApiResultObjectDto.builder()
                .resultCode(resultCode)
                .result(resultMap)
                .build();
    }


}
