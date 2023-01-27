package hello.hellospring.res.model;

import hello.hellospring.mybatis.model.HugoBoardModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardPagingResModel implements Serializable {

    private static final long serialVersionUID = 112031915586146598L;

    private int totalCount;

    private List<HugoBoardModel> boardInfo;
}
