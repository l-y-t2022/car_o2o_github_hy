package cn.wolfcode.qo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JsonResult {
    private boolean success;
    private String msg;

    public JsonResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public JsonResult() {

    }
}
