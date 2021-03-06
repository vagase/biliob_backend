package com.jannchie.biliob.credit.handle;

import com.jannchie.biliob.constant.CreditConstant;
import com.jannchie.biliob.constant.ResultEnum;
import com.jannchie.biliob.model.AuthorList;
import com.jannchie.biliob.model.User;
import com.jannchie.biliob.utils.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author Jannchie
 */
@Component
public class CreditHandle {
    private static final Logger logger = LogManager.getLogger();
    final MongoTemplate mongoTemplate;

    public CreditHandle(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ResponseEntity<Result<String>> createAuthorList(User user, CreditConstant creditConstant, String title) {
        AuthorList authorList = new AuthorList(title, user.getName());
        mongoTemplate.save(authorList);
        String param = String.format("%s (%s)", title, authorList.get("_id"));
        return getSuccessResponse(user, creditConstant, param);
    }


    public ResponseEntity deleteAuthorList(User user, CreditConstant creditConstant, ObjectId id) {
        return ResponseEntity.ok(null);
    }

    public ResponseEntity alwaysFail(User user, CreditConstant creditConstant) {
        return null;
    }


    private ResponseEntity<Result<String>> getSuccessResponse(User user, CreditConstant creditConstant, String message) {
        ResponseEntity<Result<String>> r;
        HashMap<String, Object> data = getResponseData(user, creditConstant, message);
        String msg = creditConstant.getMsg(message);
        logger.info(msg);
        Result<String> result = new Result<>(ResultEnum.SUCCEED, message);
        r = new ResponseEntity<>(result, HttpStatus.OK);
        return r;
    }

    private HashMap<String, Object> getResponseData(User user, CreditConstant creditConstant, String message) {
        HashMap<String, Object> data;
        data = new HashMap<>(3);
        data.put("exp", user.getExp() + creditConstant.getValue());
        data.put("credit", user.getCredit() + Math.abs(creditConstant.getValue()));
        data.put("msg", message);
        return data;
    }

    private HashMap<String, Object> getResponseData(User user, CreditConstant creditConstant) {
        HashMap<String, Object> data;
        data = new HashMap<>(3);
        data.put("exp", user.getExp() + creditConstant.getValue());
        data.put("credit", user.getCredit() + Math.abs(creditConstant.getValue()));
        return data;
    }

    public ResponseEntity<Result<String>> modifyUserName(User user, CreditConstant creditConstant, String newUserName) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(user.getId())),
                Update.update("nickName", newUserName),
                "user");
        return getSuccessResponse(user, creditConstant, newUserName);
    }
}
