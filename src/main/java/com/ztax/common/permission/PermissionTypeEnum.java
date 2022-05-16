package com.ztax.common.permission;

public enum PermissionTypeEnum {

    DATA_COMPANY("DATA PERMISSION OF COMPANY", "公司级别数据权限","companyId"),

    OPERATION_USER("OPERATION PERMISSION OF USER", "用户级别操作权限","null");

    private String code;

    private String desc;

    private String column;

    PermissionTypeEnum(String code, String desc,String column) {
        this.code = code;
        this.desc = desc;
        this.column = column;
    }

}
