package com.z.bean;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by heyulong on 8/19/2016.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Bean {

    /**
     * status : 201
     * message : APP琚敤鎴疯嚜宸辩鐢紝璇峰湪鎺у埗鍙拌В绂�
     */

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
