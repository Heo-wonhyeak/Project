package hello.hellospring.controller.rest;

import hello.hellospring.enums.ErrorCodeEnum;
import hello.hellospring.framework.exception.BaseException;
import hello.hellospring.logic.MemberLogic;
import hello.hellospring.req.model.*;
import hello.hellospring.req.model.board.DuplicateIdReqModel;
import hello.hellospring.res.model.ApiResultObjectDto;
import hello.hellospring.res.model.TestResModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@CrossOrigin(value = "*")
@RequestMapping(value = "/api/member")
@RestController
public class MemberRestController {

    @Autowired
    private MemberLogic memberLogic;

    @GetMapping(value = "/list/all")
    public ResponseEntity<ApiResultObjectDto> getAllMemberList() {
        ApiResultObjectDto apiResultObjectDto = memberLogic.getAllHugoMemberLogic();
        return ResponseEntity.ok(apiResultObjectDto);
    }

    /**
     * 회원가입 API
     * @param reqModel
     * @return
     */
    @PostMapping(value = "/sign-up")
    public ResponseEntity<ApiResultObjectDto> saveMember(@Valid @RequestBody HugoUserSaveReqModel reqModel) {
        ApiResultObjectDto apiResultObjectDto = memberLogic.saveHugoUserLogic(reqModel);
        return ResponseEntity.ok(apiResultObjectDto);
    }

    /**
     * 로그인 API
     * @param reqModel
     * @return
     */
    @PostMapping(value = "/login")
    public ResponseEntity<ApiResultObjectDto> loginMember(@Valid @RequestBody HugoLoginReqModel reqModel) {
        //ApiResultObjectDto apiResultObjectDto = memberLogic.loginByIdLogic(reqModel, request);
        ApiResultObjectDto apiResultObjectDto = memberLogic.loginLogic(reqModel);
        return ResponseEntity.ok(apiResultObjectDto);
    }

    /**
     * 회원가입 아이디 중복체크
     *
     * TODO
     * 질문 사항
     * - @RequestParam("id") String id로 파라미터를 받고
     * ajax 에서 data : JSON.stringify() 로 값을 받았을 경우엔
     * 에러가 발생
     * reqModel 을 만들고 @RequestBody 를 사용하여 실행시 에러 해결
     *  - 한개의 파라미터를 post 로 받을 경우엔 reqModel 없이 간소화 가능한지
     *
     *  REPLY
     * -@RequestParam 을 사용하려면 ajax 에서 data를 JSON.stringify() 를 빼고 순수 data 형식으로 보내면 됨
     *  ex) data : { property1 : value1, property2 : value2, ...... }
     *  controller > @RequestParam(value = "property1") Object property1, ...)
     *
     * @param reqModel
     * @return
     */
    @PostMapping(value ="/duplicate")
    public ResponseEntity<ApiResultObjectDto> duplicateId(@RequestBody DuplicateIdReqModel reqModel) {
        return ResponseEntity.ok(memberLogic.duplicateMemberId(reqModel.getId()));
    }

    /**
     * 회원정보 API
     * @param id
     * @return
     */
    @GetMapping(value = "/info/{id}")
    public ResponseEntity<ApiResultObjectDto> getMemberInfo(@PathVariable String id) {
        ApiResultObjectDto apiResultObjectDto = memberLogic.getHugoMemberInfo(id);
        return ResponseEntity.ok(apiResultObjectDto);
    }

    /**
     * 회원 정보 수정 API
     * @param reqModel
     * @return
     */
    @PostMapping("/info/modify")
    public ResponseEntity<ApiResultObjectDto> updateMemberInfo(@RequestBody HugoUserModifyReqModel reqModel) {
        ApiResultObjectDto apiResultObjectDto = memberLogic.updateHugoMemberInfo(reqModel);
        return ResponseEntity.ok(apiResultObjectDto);
    }

    /**
     * 회원 탈퇴 API
     * @param reqModel
     * @return
     */
    @PostMapping(value = "/resign")
    public ResponseEntity<ApiResultObjectDto> deleteMemberInfo(@RequestBody HugoUserDeleteReqModel reqModel) {
        ApiResultObjectDto apiResultObjectDto = memberLogic.deleteHugoUserInfo(reqModel);
        return ResponseEntity.ok(apiResultObjectDto);
    }

    /**
     * 회원 비밀번호 검증 API
     * @param reqModel
     * @return
     */
    @PostMapping(value = "/checkPwd")
    public ResponseEntity<ApiResultObjectDto> checkedPwd(@RequestBody HugoPwdCheckedReqModel reqModel) {
        ApiResultObjectDto apiResultObjectDto = memberLogic.pwdCheckById(reqModel);
        return ResponseEntity.ok(apiResultObjectDto);
    }


    @Deprecated
    @GetMapping(value = "/logout/{id}")
    public ResponseEntity<ApiResultObjectDto> logoutMember(@PathVariable String id ,HttpServletRequest request) {
        ApiResultObjectDto apiResultObjectDto = memberLogic.logoutByIdLogic(id, request);
        return ResponseEntity.ok(apiResultObjectDto);
    }
}
