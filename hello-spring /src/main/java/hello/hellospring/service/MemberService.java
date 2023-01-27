package hello.hellospring.service;

import hello.hellospring.mybatis.dao.HugoUserInfoDao;
import hello.hellospring.mybatis.model.HugoUserInfoModel;
import hello.hellospring.req.model.HugoLoginReqModel;
import hello.hellospring.req.model.HugoUserModifyReqModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private HugoUserInfoDao hugoUserInfoDao;

    /**
     * getHugoUserInfoList 가져오는 서비스
     * @return
     */
    public List<HugoUserInfoModel> findHugoUserList() {
        return hugoUserInfoDao.getHugoUserInfoList();
    }

    public Integer findCountHugoUserById(String id) {
        if (!"".equals(id)) {
            return hugoUserInfoDao.getCountHugoUserInfoById(id);
        }
        return 1;
    }

    public Boolean isExitsHugoUserById(String id) {
        boolean bl = false;
        int cnt = hugoUserInfoDao.getCountHugoUserInfoById(id);
        if (cnt > 0) {
            bl = true;
        }
        return bl;
    }

    @Transactional
    public void saveHugoUserInfo(HugoUserInfoModel hugoUserInfoModel) {
        //ID가 있을때만 저장 쿼리 수행
        if (!"".equals(hugoUserInfoModel.getId())) {
            hugoUserInfoDao.saveHugoUserInfo(hugoUserInfoModel);
        }
    }

    @Transactional
    public HugoUserInfoModel loginById(HugoLoginReqModel hugoUserInfoModel) {
        String userId = hugoUserInfoModel.getId();

        if(!userId.isEmpty()) {
            return hugoUserInfoDao.loginById(hugoUserInfoModel);
        } else {
            return null;
        }

    }

    public HugoUserInfoModel findHugoUserById(String id) {
        if (!"".equals(id)) {
            return hugoUserInfoDao.findUserById(id);
        } else {
            return null;
        }
    }

    public HugoUserInfoModel findHugoUserByIdAndPassword(String id, String password) {
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(password)) {
            return hugoUserInfoDao.loginByIdAndPassword(id, password);
        }
        return null;
    }

    public HugoUserInfoModel findHugoMemberInfoById(String id) {
        if (!"".equals(id)) {
            return hugoUserInfoDao.findHugoMemberInfoById(id);
        } else {
            return null;
        }
    }

    @Transactional
    public void updateHugoUserInfo(HugoUserModifyReqModel hugoUserModifyReqModel) {
        if (!"".equals(hugoUserModifyReqModel.getId())) {
            hugoUserInfoDao.updateHugoUserInfo(hugoUserModifyReqModel);
        }
    }

    @Transactional
    public void deleteHugoUserInfo(String id,String pwd) {
        hugoUserInfoDao.deleteHugoUserInfo(id,pwd);
    }

    /**
     * 닉네임 유효성 검사 서비스 
     * @param nickName
     * @return
     */
    public int getHugoUserInfoByNickName(String nickName) {
        return hugoUserInfoDao.getHugoUserInfoByNickName(nickName);
    }
}
