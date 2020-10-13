package cn.itcast.domain.system;

import lombok.Data;

import java.io.Serializable;

/**
 * (SsModule)实体类
 *
 * @author makejava
 * @since 2020-10-13 20:20:25
 */
@Data
public class Module implements Serializable {
    private static final long serialVersionUID = 519395439026936617L;

    private String id;

    /**
     * 父模块的id
     */
    private String parentId;

    /**
     * 父模块的名称，冗余字段
     */
    private String parentName;

    /**
     * 模块名称
     */
    private String name;

    private Integer layerNum;

    private Integer isLeaf;

    private String ico;

    private String cpermission;

    private String curl;
    /**
     * 0 主菜单
     * 1 左侧菜单
     * 2按钮
     * 3 链接
     * 4 状态
     */
    private Integer ctype;
    /**
     * 1启用
     * 0停用
     */
    private Integer state;
    /**
     * 从属关系
     * 0.saas系统内部菜单
     * 1.租用企业菜单
     */
    private Integer belong;

    private String cwhich;

    private Integer quoteNum;

    private String remark;

    private Integer orderNo;


}