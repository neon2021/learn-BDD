package com.neon2022.springmvc.restapi.valueobject;

import lombok.Data;

import java.util.Date;

/**
 * TODO: function description
 *
 * @author neon2021 on 2022/6/5
 */
@Data
public class ConversationVO {
    String name;
    Long fromPersonId;
    String message;
    Date sentDate;
    Long toPersonId;
}
