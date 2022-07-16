package com.lq.pwd.model.redis;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class InviteCode {
    private Integer count;
    private List<String> usrList = new ArrayList<>();
}
