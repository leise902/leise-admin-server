package com.leise.provider.admin.biz.sysmanage.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.leise.provider.admin.base.exception.AdminErrorCode;
import com.leise.provider.admin.biz.sysmanage.input.QueryMenuListInput;
import com.leise.provider.admin.biz.sysmanage.output.QueryMenuListOutput;
import com.leise.provider.admin.model.access.entity.RoleMenuRef;
import com.leise.provider.admin.model.access.entity.RoleUserRef;
import com.leise.provider.admin.model.access.service.RoleMenuRefService;
import com.leise.provider.admin.model.access.service.RoleUserRefService;
import com.leise.provider.admin.model.sysmanage.entity.SysMenuInfo;
import com.leise.provider.admin.model.sysmanage.service.SysMenuInfoService;
import com.leise.core.base.annotation.CRService;
import com.leise.core.base.service.AbstractService;

@CRService(name = "查询用户菜单列表", serviceId = "queryMenuList", type = "Q", value = "queryMenuList_1.0.0", version = "1.0.0")
public class QueryMenuListService extends AbstractService<QueryMenuListInput, QueryMenuListOutput> {

    /**
     * 串行号
     */
    private static final long serialVersionUID = 6097942355945258749L;

    @Autowired
    private RoleUserRefService roleUserRefService;

    @Autowired
    private RoleMenuRefService roleMenuRefService;

    @Autowired
    private SysMenuInfoService sysMenuInfoService;

    @Override
    protected QueryMenuListOutput doExecute(QueryMenuListInput input) throws Exception {
        // TODO Auto-generated method stub
        String userId = input.getUserId();

        List<RoleUserRef> roleUserRefList = roleUserRefService.searchByUserId(Long.valueOf(userId));
        // 无法找到用户的角色信息
        Assert.notEmpty(roleUserRefList, AdminErrorCode.ROLE_DATA_NOT_FOUND);
        List<Long> roleIds = roleUserRefList.stream().map(RoleUserRef::getRoleId).collect(Collectors.toList());
        List<RoleMenuRef> roleMenuRefList = roleMenuRefService.searchByRoleIds(roleIds);
        // 无法找到菜单信息
        Assert.notEmpty(roleMenuRefList, AdminErrorCode.MENU_DATA_NOT_FOUND);
        List<Long> menuIds = roleMenuRefList.stream().map(RoleMenuRef::getMenuId).collect(Collectors.toList());

        List<SysMenuInfo> menuInfoList = sysMenuInfoService.searchByMenuIds(menuIds);

        Map<String, List<SysMenuInfo>> menuListGroup = menuInfoList.stream().collect(
                Collectors.groupingBy(SysMenuInfo::getParentMenuCode));
        List<SysMenuInfo> userMenuList = menuListGroup.get("000000").stream().map(sysMenuInfo -> {
            sysMenuInfo.setChildren(menuListGroup.get(sysMenuInfo.getMenuCode()));
            return sysMenuInfo;
        }).collect(Collectors.toList());

        QueryMenuListOutput output = new QueryMenuListOutput();
        output.setUserId(userId);
        output.setList(userMenuList);
        return output;
    }
}
